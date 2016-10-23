package fr.univ_lyon1.etu.ewide.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.univ_lyon1.etu.ewide.Model.Project;
import fr.univ_lyon1.etu.ewide.Model.Role;
import fr.univ_lyon1.etu.ewide.Model.User;

import fr.univ_lyon1.etu.ewide.dao.ProjectDAO;
import fr.univ_lyon1.etu.ewide.service.AuthenticationUserService;
import fr.univ_lyon1.etu.ewide.service.UserRoleService;
import fr.univ_lyon1.etu.ewide.dao.RoleDAO;

@Transactional
@Controller
public class ProjectController {
	
	 
	@Autowired
	AuthenticationUserService authenticationUserSerive;
	

	@Autowired	
	public RoleDAO roleDAO;
	 
	 @RequestMapping(value ="/dashboard", method = RequestMethod.GET)
	 	public ModelAndView home(ModelMap Model) throws IOException{
		 
	 		User user = authenticationUserSerive.getCurrentUser();
	        	 		
	        List<Project> listProject = roleDAO.getProjectIDByUser(user);
	        
	        ModelAndView model = new ModelAndView("dashboard");
	        model.addObject("projectList", listProject);
	        model.setViewName("dashboard");
	        return model;
	    }
	 
	 @PreAuthorize("@roleService.isMember()")
	 @RequestMapping(value ="/newproject", method = RequestMethod.GET)
	 	public String getNewProject()	{
		 	return "new_project";
	 }
	 
	 
	 // Cette fonction permet de créer un projet au sein de la base de donnée
	 // Elle affecte également les utilisateurs liées au projet à leurs rôles

		@Autowired
		 public  ProjectDAO projectDAO;
		

	 @RequestMapping(value ="/newproject", method = RequestMethod.POST)
	 public String addProject (@RequestParam("projectName")String projectName,
			 @RequestParam("projectDesc")String projectDesc,
			
			 Project project,
			 Role role,
			   ModelMap model) {
		 User user = authenticationUserSerive.getCurrentUser();
		 // Affectation des attributs du projet
		 project.setName(projectName);
		 project.setName(projectDesc);
		 project.setFileTree(projectName);
		 project.setLinkRepo(projectName);
		 project.setLinkMakefile(projectName);
		 // Création du projet dans la BDD
		 projectDAO.createProject(project);
		 //Affectation du créateur du projet  au role de manager
		 role.setUser(user);
		 roleDAO.createRole(user, project);
		
		 //  Fonction fonctionnelle mais non terminée
			     
		 	return "redirect:/dashboard";
			     
	 }
	
		
	 @RequestMapping(value = {"/project/{project.name}"}, method = RequestMethod.GET)
	 public String getProjectByName(){
		 return "ide";
	 }
}
