package fr.univ_lyon1.etu.ewide.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.univ_lyon1.etu.ewide.dao.ProjectDAO;
import fr.univ_lyon1.etu.ewide.model.Project;
import fr.univ_lyon1.etu.ewide.service.AuthenticationUserService;

@Transactional
@Controller
@RequestMapping(value="/project")
public class CompilerController {
	
	@Autowired
	ProjectDAO projectDAO;
	 
	@Autowired
	AuthenticationUserService authenticationUserSerive;
	
	
	/**
	 * add the command to the compiler project attribute
	 * @param compiler (String)
	 * @param projectID (int)
	 * @return 1 
	 * @throws IOException
	 */
	@RequestMapping(value ="/{projectID}/configCompiler", method = RequestMethod.POST)
	@PreAuthorize("@userRoleService.isMember(#projectID)")
	public @ResponseBody int  postMessages(@RequestParam String compiler, @PathVariable("projectID") int projectID) throws IOException{
		Project project=projectDAO.getProjectById(projectID);
		projectDAO.setCompiler(project, compiler);
		return 1;
	}
	
	
	
	
	
}
