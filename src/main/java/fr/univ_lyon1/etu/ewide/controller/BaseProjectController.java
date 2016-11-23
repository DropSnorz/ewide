package fr.univ_lyon1.etu.ewide.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import fr.univ_lyon1.etu.ewide.dao.ProjectDAO;
import fr.univ_lyon1.etu.ewide.model.Project;

/**
 * Provides usefull functions for projects-related controllers
 *
 */
@Controller
public class BaseProjectController {
	
	@Autowired
	ProjectDAO projectDAO;
	
	@ModelAttribute("project")
	public Project addProject(@PathVariable("projectId") int projectId){
		
		return projectDAO.getProjectById(projectId);
	}

}
