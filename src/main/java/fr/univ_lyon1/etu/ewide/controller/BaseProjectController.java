package fr.univ_lyon1.etu.ewide.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Provides usefull functions for projects-related controllers
 *
 */
@Controller
public class BaseProjectController {
	
	@ModelAttribute("projectId")
	public int addProjectId(@PathVariable("projectId") int projectId) {
		
		return projectId;
	}

}
