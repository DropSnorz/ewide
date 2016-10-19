package fr.univ_lyon1.etu.ewide.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.univ_lyon1.etu.ewide.Model.Project;
import fr.univ_lyon1.etu.ewide.dao.ProjectDAO;

@Controller
public class ProjectController {
	
	@Autowired
	 public  ProjectDAO projectDAO;

	 
	 @RequestMapping(value ="/dashboard", method = RequestMethod.GET)
	 
	 	public ModelAndView home(ModelMap Model) throws IOException{
	 		System.out.println("ON doit bind avec la view");
	        List<Project> listProject = projectDAO.getAllProjects();
	        ModelAndView model = new ModelAndView("dashboard");
	        model.addObject("projectList", listProject);
	        model.setViewName("dashboard");
	        return model;
	    }
	

}
