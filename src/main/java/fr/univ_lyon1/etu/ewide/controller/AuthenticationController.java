package fr.univ_lyon1.etu.ewide.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.univ_lyon1.etu.ewide.Model.User;
import fr.univ_lyon1.etu.ewide.dao.UserDAO;
import fr.univ_lyon1.etu.ewide.form.RegisterForm;
import fr.univ_lyon1.etu.ewide.service.AuthenticationUserService;

@Transactional
@Controller
public class AuthenticationController {

	@Autowired
	UserDAO userDAO;

	@Autowired
	AuthenticationUserService authenticationUserService;

	@RequestMapping(value ="/login",  method = RequestMethod.GET)
	public String connexion() {
		return "login";
	}


	@RequestMapping(value="/logout", method = RequestMethod.POST)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){    
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout";
	}

	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage2 (HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){    
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout";
	}

	@RequestMapping(value="/register", method = RequestMethod.POST)
	public String PostRegister(@ModelAttribute("registerForm") @Valid final RegisterForm form,
			final BindingResult bindingResult, final Model model,
			HttpServletRequest request) {

		if (bindingResult.hasErrors()) {

			return "login";

		}
		else{
			if(userDAO.getUserByEmail(form.getEmail())!= null){
				bindingResult.rejectValue("email", "email_already_used", "Email already used");
				return "login";

			}
			else if(userDAO.getUserByUsername(form.getUsername())!= null){
				bindingResult.rejectValue("username", "username_already_used", "Username already used");

				return "login";

			}
			else{

				User user = userDAO.createOrUpdate(form.getEmail(), form.getUsername(), form.getPassword());
				authenticationUserService.doLogin(user.getMail(), user.getPwd(), request);
				return "redirect:/dashboard";
			}
		}
	}

}