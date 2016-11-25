package fr.univ_lyon1.etu.ewide.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.univ_lyon1.etu.ewide.dao.UserDAO;
import fr.univ_lyon1.etu.ewide.model.User;
import fr.univ_lyon1.etu.ewide.service.AuthenticationUserService;

@Transactional
@Controller
public class ChangePassword {
	
	@Autowired
	UserDAO userDAO;

	@Autowired
	AuthenticationUserService authenticationUserService;
	
    @Autowired
    PasswordEncoder passwordEncoder;
	
	/**
	 * @return view to change the password
	 */
	@RequestMapping(value="/changepwd", method = RequestMethod.GET)
	public String getchangepwd (){
		return "changepwd";
		
	}
	
	/**
	 * Change the password in the database
	 * @param lastpwd (String)
	 * @param newpwd (String)
	 * @return (Map) with the answer
	 */
	@ResponseBody
	@RequestMapping(value="/changepwd", method = RequestMethod.POST)
	public Map<String, String> changepwd(@RequestParam String lastpwd,@RequestParam String  newpwd){
		//return success and error 
		Map<String,String> mapResponse = new HashMap<String, String>();
		User user = authenticationUserService.getCurrentUser();
	 	
	 	//if the last password is great
	 	if(passwordEncoder.matches(lastpwd,user.getPwd())){
	 		user.setPwd(newpwd);
	 		userDAO.updateUser(user);
	 		mapResponse.put("success","Your password was successfully changed");
	 		
	 	}
	 	//else send error 
	 	else{
	 		mapResponse.put("error","Your actual password does not match ");
	 	}
	 	

		return mapResponse;
	}

}