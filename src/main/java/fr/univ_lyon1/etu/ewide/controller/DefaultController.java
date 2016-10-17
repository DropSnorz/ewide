package fr.univ_lyon1.etu.ewide.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class DefaultController {


	@RequestMapping(value ={"/","index"},  method = RequestMethod.GET)
	public String getIndex() {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//	    if (auth != null){    
//	        return "dashboard";
//	    }
	    return "index";
	}
	@RequestMapping(value ="/dashboard",  method = RequestMethod.GET)
	public String getDashboard() {
		return "dashboard";
	}
	@RequestMapping(value ="/ide",  method = RequestMethod.GET)
	public String getIde() {
		return "ide";
	}
}


