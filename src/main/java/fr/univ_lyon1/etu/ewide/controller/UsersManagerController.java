package fr.univ_lyon1.etu.ewide.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	 
	 
	 @RequestMapping(value ="/usersManager", method = RequestMethod.GET)
	 	public ModelAndView usersManager(ModelMap Model, @PathVariable("projectID") int projectID) throws IOException{
	     
		 	List<User> listUsers = usersDAO.getAllUsersByProjectID(projectID);
	       
	        ModelAndView model = new ModelAndView("usersmanager");
	        //TODO add userRole
	        
	        model.addObject("listUsers", listUsers);
	        model.setViewName("usersmanager");
	        return model;
	    }
	
	 /**
	  * modifie les roles qui ont �t� chang�s 
	  * @param allRequestParams (HashMap<String, String>)
	  * @param projectID (int)
	  * @return 
	  */
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