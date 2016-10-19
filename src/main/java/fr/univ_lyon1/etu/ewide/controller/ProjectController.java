package fr.univ_lyon1.etu.ewide.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.univ_lyon1.etu.ewide.Model.Project;
import fr.univ_lyon1.etu.ewide.Model.Role;
import fr.univ_lyon1.etu.ewide.Model.User;
import fr.univ_lyon1.etu.ewide.dao.ProjectDAO;
import fr.univ_lyon1.etu.ewide.service.AuthenticationUserService;
@Transactional
@Controller
public class ProjectController {
	
	@Autowired
	 public  ProjectDAO projectDAO;
	
	@Autowired
	AuthenticationUserService authenticationUserSerive;


	 
	 @RequestMapping(value ="/dashboard", method = RequestMethod.GET)
	 	public ModelAndView home(ModelMap Model) throws IOException{
		 
	 		User user = authenticationUserSerive.getCurrentUser();
	 		System.out.println(user.getMail());
	 		Hibernate.initialize(user.getRoles());

	        List<Project> listProject = new ArrayList<Project>();
	        
	        ModelAndView model = new ModelAndView("dashboard");
	        model.addObject("projectList", listProject);
	        model.setViewName("dashboard");
	        return model;
	    }
	

}
