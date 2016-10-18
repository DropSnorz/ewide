package fr.univ_lyon1.etu.ewide.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.univ_lyon1.etu.ewide.dao.ProjectDAOImpl;
import fr.univ_lyon1.etu.ewide.Model.Project;



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
	/*
	@RequestMapping(value ="/dashboard",  method = RequestMethod.GET)
	public String getDashboard() {
		return "dashboard";
	}*/
	@RequestMapping(value ="/ide",  method = RequestMethod.GET)
	public String getIde() {
		return "ide";
	}
	
	@Autowired
	 private ProjectDAOImpl projectDAO;

	 
	 @RequestMapping(value ="/dashboard", method = RequestMethod.GET)
	 	public ModelAndView home(ModelMap Model) throws IOException{
	 		System.out.println("ON doit bind avec la view");
	        List<Project> listProject = projectDAO.list();
	      
	        ModelAndView model = new ModelAndView("dashboard");
	        model.addObject("projectList", listProject);
	        model.setViewName("dashboard");
	        return model;
	    }
	
}


