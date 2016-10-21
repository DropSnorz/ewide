package fr.univ_lyon1.etu.ewide.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;







import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import fr.univ_lyon1.etu.ewide.Model.Project;
import fr.univ_lyon1.etu.ewide.Model.Role;
import fr.univ_lyon1.etu.ewide.Model.User;
import fr.univ_lyon1.etu.ewide.dao.ProjectDAO;
import fr.univ_lyon1.etu.ewide.dao.RoleDAO;
import fr.univ_lyon1.etu.ewide.dao.UserDAO;

@Transactional
@Controller
@RequestMapping(value="/project/{projectID}")
public class UsersManagerController {
	
	 @Autowired
	 public  UserDAO usersDAO;
	 
	 @Autowired
	 RoleDAO roleDAO;
	 
	 @Autowired
	 ProjectDAO projectDAO;
	 
	 public static List<String> roles=Arrays.asList("","MANAGER","DEVELOPER","REPORTER"); 
	 
	 
	 /**
	  * donne la liste des membres d'un projet
	  * @param Model 
	  * @return la vue usersmanager
	  * @throws IOException
	  */
	 @RequestMapping(value ="/users_manager", method = RequestMethod.GET)
	 	public ModelAndView usersManager(ModelMap Model, @PathVariable("projectID") int projectID) throws IOException{
	     
		 	List<User> listUsers = usersDAO.getAllUsersByProjectID(projectID);
	       
	        
	        ModelAndView model = new ModelAndView("usersmanager");
	        
	        //liste des roles connus de la bdd
	        model.addObject("roles",roles);
	        //TODO add userRole
	        
	        //liste des utilisateurs avec leur role
	        model.addObject("listUsers", listUsers);
	        model.setViewName("usersmanager");
	        return model;
	    }
	 
	 /**
	  * modifie les roles qui ont été changés 
	  * @param allRequestParams (HashMap<String, String>)
	  * @param projectID (int)
	  * @return 
	  */
	 @RequestMapping(value="/users_manager",method=RequestMethod.POST)
	 public String changeRoles(@RequestParam HashMap<String,String> allRequestParams,@PathVariable("projectID") int projectID){
		 	allRequestParams.remove("_csrf");
		  	for(Map.Entry<String, String>node : allRequestParams.entrySet()){
		  		User user=usersDAO.getUserByUsername(node.getKey());
		  		System.out.println(node.getKey());
		  		System.out.println(user);
		  		Project project=projectDAO.getProjectById(projectID);
		  		System.out.println(project);
		  		roleDAO.createOrUpdate(user, project, node.getValue());
		  	}
	 
	 
		  	//TODO return validation
		    return "usermanager";
	 }
	

}