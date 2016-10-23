package fr.univ_lyon1.etu.ewide.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fr.univ_lyon1.etu.ewide.service.AuthenticationUserService;



@Controller
public class DefaultController {
	
	@Autowired
	AuthenticationUserService authenticationUserService;
	
	@RequestMapping(value ={"/","index"},  method = RequestMethod.GET)
	public String getIndex() {
		
		if(authenticationUserService.isCurrentUserLogged()){
			return "redirect:/dashboard";
		}
		
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//	    if (auth.isAuthenticated()){    
//	    	
//	    }
	    return "index";
	}
	
	
	
}


