package fr.univ_lyon1.etu.ewide.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class DefaultController {


	@RequestMapping(value ="/",  method = RequestMethod.GET)
	public String getIndex() {


		return "index";
	}
}


