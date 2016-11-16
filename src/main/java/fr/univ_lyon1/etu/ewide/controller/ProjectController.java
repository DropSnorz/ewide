package fr.univ_lyon1.etu.ewide.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import fr.univ_lyon1.etu.ewide.dao.FileDAO;
import fr.univ_lyon1.etu.ewide.dao.ProjectDAO;
import fr.univ_lyon1.etu.ewide.service.AuthenticationUserService;
import fr.univ_lyon1.etu.ewide.service.GitService;
import fr.univ_lyon1.etu.ewide.service.UserRoleService;
import fr.univ_lyon1.etu.ewide.dao.RoleDAO;
import fr.univ_lyon1.etu.ewide.model.Project;
import fr.univ_lyon1.etu.ewide.model.Role;
import fr.univ_lyon1.etu.ewide.model.User;

@Transactional
@Controller
public class ProjectController {
	
	 
	@Autowired
	AuthenticationUserService authenticationUserSerive;
	

	@Autowired	
	public RoleDAO roleDAO;
	/*
	 * Display all projects of the user connected
	 */
	@RequestMapping(value ="/dashboard", method = RequestMethod.GET)
		public ModelAndView home(ModelMap Model) throws IOException{
	 		User user = authenticationUserSerive.getCurrentUser();
	        	 		
	        List<Project> listProject = roleDAO.getProjectIDByUser(user);
	        
	        ModelAndView model = new ModelAndView("dashboard");
	        model.addObject("projectList", listProject);
	        model.setViewName("dashboard");
	        return model;
	    }
	 /*
	  * Display the screen of projet
	  */
	 @RequestMapping(value ="/newproject", method = RequestMethod.GET)
	 	public String getNewProject()	{
		 	return "new_project";
	 }
	 
	 
	 /* 
	  * This function allow to create project in the database
	  * Also create a new git repository 
	 */

		@Autowired
		 public  ProjectDAO projectDAO;
		

	 @RequestMapping(value ="/newproject", method = RequestMethod.POST)
	 public String addProject (@RequestParam("projectName")String projectName,
			 @RequestParam("projectDesc")String projectDesc,
			 Project project,
			 Role role,
			   ModelMap model) {
		 User user = authenticationUserSerive.getCurrentUser();
		 GitService git = new GitService();
		 // Assign many attributes
		 project.setName(projectName);
		 project.setDescription(projectDesc);
		 project.setFileTree(projectName);
		 
		 
		 /*project.setLinkRepo(projectName);*/
		 project.setLinkMakefile(projectName);
		 // Create the project in the database
		 projectDAO.createProject(project);
		 // Create the git repository
		 try {
			git.gitCreate(project.getProjectID());
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
		 // Assign the creator of this project to the role manager
		 role.setUser(user);
		 roleDAO.createRole(user, project,"MANAGER");
		
			     
		 	return "redirect:/dashboard";
			     
	 }
	 
	 @Autowired
		public  FileDAO fileDAO;
	 /*
	  * Display the editor
	  */
     @RequestMapping(value = {"/project/{projectID}"}, method = RequestMethod.GET)
     public ModelAndView getProjectByName(@PathVariable("projectID") int projectID){
    	 ModelAndView model = new ModelAndView("dashboard");
    	 Project project = new Project();
    	 project = projectDAO.getProjectById(projectID);
    	// List<File> file = fileDAO.getFilesByProject(project);
    	
    	 model.addObject("project", project);
    	 model.setViewName("ide");
    	 return model;
             

     }
     /**
      * This function return a JSON file whose permit to create the file tree
      * @param projectID
      * @return
      */
    
     @RequestMapping(value = {"/project/{projectID}/files"},method = RequestMethod.GET, produces="application/json")
     public @ResponseBody String getProjectByNameJSON(@PathVariable("projectID") int projectID){
    	
    	 Project project = new Project();
    	 project = projectDAO.getProjectById(projectID);
    	 File[] files = new File("GitRepos/"+projectID).listFiles();
    	 ArrayList<String> test = new ArrayList<String>();
    	 JSONArray jarr = new JSONArray();
    	 jarr = tree(files);
    	 
    	 return jarr.toString();
    	
     }
     
     public JSONArray tree(File files[]){
    	 ArrayList<String> liste = new ArrayList<String>() ;
    	 JSONArray jarr = new JSONArray();
    	 for (File file: files){
    		 if (file.isDirectory()){
    			 if (!file.getName().equals(".git")){
    				 JSONObject jobj = new JSONObject();
        	    	 jobj.put("id", file.getPath());
        	    	 jobj.put("text", file.getName());
        	    	 
    				 //tree(file.listFiles());
    				jobj.put("children", tree(file.listFiles()));
    				 jarr.put(jobj);
    			 }
    				 
    			 
    		 }else{
    		 if (!file.getName().equals(".git")){
    			 liste.add(file.getName());
    			 JSONObject jobj = new JSONObject();
    			 jobj.put("id", file.getPath());
    	    	 jobj.put("text", file.getName());
    	    	 jobj.put("icon", "jstree-file");
				 jarr.put(jobj);
    		 }
    		 }
    		 
    		 
    	 }
    	 
    	 
    	// System.out.println(jarr.toString());
    	 return jarr;
    	 //return listeFichier;
    	 //System.out.println(liste);
    
     }

	

}
