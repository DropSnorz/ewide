package fr.univ_lyon1.etu.ewide.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
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
	 */
	@RequestMapping(value ="/{projectID}/configCompiler", method = RequestMethod.POST)
	@PreAuthorize("@userRoleService.isMember(#projectID)")
	public @ResponseBody int  postMessages(@RequestParam String compiler, @PathVariable("projectID") int projectID) {
		Project project=projectDAO.getProjectById(projectID);
		projectDAO.setCompiler(project, compiler);
		return 1;
	}

	/**
	 * Return the actual compiler(-tool) of the project
	 * @param projectID
	 * @return
	 */
	@RequestMapping(name = "getCompiler", value = "/{projectID}/compiler", method = RequestMethod.GET)
	@PreAuthorize("@userRoleService.isMember(#projectID)")
	public @ResponseBody String getCompiler(@PathVariable("projectID") int projectID) {
		Project project = projectDAO.getProjectById(projectID);
		String compiler = project.getCompiler();
		return compiler;
	}
	
	
	/**
	 * this function will return the error or success of the compilation
	 * @param projectID (int)
	 * @return (String)
	 */
	//TODO
	/*
	@RequestMapping(value ="/{projectID}/compile", method = RequestMethod.GET)
	@PreAuthorize("@userRoleService.isMember(#projectID)")
	public @ResponseBody String compile(@PathVariable("projectID") int projectID){
		//get the compiler of the project
		Project project=projectDAO.getProjectById(projectID);
		String compiler = project.getCompiler();
		//get the path
		String folder=project.getFileTree();
		
		System.out.println(folder);

	    Process proc = Runtime.getRuntime().exec();

	        // Read the output

	        BufferedReader reader =  
	              new BufferedReader(new InputStreamReader(proc.getInputStream()));

	        String line = "";
	        while((line = reader.readLine()) != null) {
	            System.out.print(line + "\n");
	        }

	        proc.waitFor();   

	    }
		return null;
	}
	*/
	
	
}
