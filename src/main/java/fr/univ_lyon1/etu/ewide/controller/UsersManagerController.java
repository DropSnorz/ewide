package fr.univ_lyon1.etu.ewide.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;







import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import fr.univ_lyon1.etu.ewide.Model.Project;
import fr.univ_lyon1.etu.ewide.Model.Role;
import fr.univ_lyon1.etu.ewide.Model.User;
import fr.univ_lyon1.etu.ewide.dao.ProjectDAO;
import fr.univ_lyon1.etu.ewide.dao.RoleDAO;
import fr.univ_lyon1.etu.ewide.dao.UserDAO;
import fr.univ_lyon1.etu.ewide.service.AuthenticationUserService;

@Transactional
@Controller
@RequestMapping(value="/project")
public class UsersManagerController {
	
	@Autowired
	AuthenticationUserService authenticationUserSerive;
	
	 @Autowired
	 public  UserDAO usersDAO;
	 
	 @Autowired
	 RoleDAO roleDAO;
	 
	 @Autowired
	 ProjectDAO projectDAO;
	 
	 public static List<String> roles=Arrays.asList("","MANAGER","DEVELOPER","REPORTER"); 
	 
	 
	 /**
	  * gives the list of the users with their role
	  * @param Model 
	  * @return view usersmanager
	  * @throws IOException
	  */
	 @RequestMapping(value ="/{projectID}/users_manager", method = RequestMethod.GET)
	 	public ModelAndView usersManager(ModelMap Model, @PathVariable("projectID") int projectID) throws IOException{
	     
		 	List<User> listUsers = usersDAO.getAllUsersByProjectID(projectID);
		 	User user = authenticationUserSerive.getCurrentUser();
		 	Role role=roleDAO.getRoleByUserIdAndProjectId(user.getUserID(), projectID);
	        ModelAndView model = new ModelAndView("usersmanager");
	        
	        //set of roles that exist (for the select)
	        model.addObject("roles",roles);
	        
	        //role of the actual user (to display or not deleted button)
	        model.addObject("userrole",role.getRole());
	          
	        //list of users with their role
	        model.addObject("listUsers", listUsers);
	        model.setViewName("usersmanager");
	        return model;
	    }
	 

	 /**
	  * to change the roles of the users
	  *	if the role is null, user will be deleted from the project
	  * @param allRequestParams (HashMap<String,String>)
	  * @param projectID (int)
	  * @return
	  */
	 //TODO delete a User
	 @RequestMapping(value="/{projectID}/users_manager",method=RequestMethod.POST)
	 public String changeRoles(@RequestParam HashMap<String,String> allRequestParams,@PathVariable("projectID") int projectID){
		 	allRequestParams.remove("_csrf");
		 	//must as at less a manager
		 	if(allRequestParams.containsValue("MANAGER")){
			  	for(Map.Entry<String, String>node : allRequestParams.entrySet()){
			  		User user=usersDAO.getUserByUsername(node.getKey());
			  		Project project=projectDAO.getProjectById(projectID);
			  		if(node.getValue().equals("null")|| node.getValue()==null){
			  			roleDAO.deleteRole(user,project);
			  		}
			  		else{
			  			roleDAO.updateRole(user, project, node.getValue());
			  		}
			  	}
		 	}
		 	else{
		 		//return an error 
		 	}

	 
		  	//TODO return validation
		    return "usermanager";
	 }
	 
	 /**
	  * display users that can be adding to the project
	  * @param username (String)
	  * @return (ArrayList<User>) list of names
	  */
	 @RequestMapping(value="/users",method=RequestMethod.GET)
	 @ResponseBody
	 public ArrayList<User> autocompleteusers(@RequestParam String username){
		 User user = authenticationUserSerive.getCurrentUser();
		 ArrayList<User> listUsers = (ArrayList<User>) usersDAO.getUsersStartedwith(username,user);
		 return  listUsers;
		 
	 }
	 
	 
	 /**
	  * add a user to the project if it doesn't exist
	  * @param username (String)
	  * @param projectID (int)
	  * @param role (model)
	  * @return
	  */
	 //TODO
	 @RequestMapping(value="/{projectID}/adduser", method=RequestMethod.POST)
	 @ResponseBody
	 //TODO return not finished
	 public User adduser(@RequestParam String username, @PathVariable("projectID") int projectID, Role role){
		 User user = usersDAO.getUserByUsername(username);
		 Project project=projectDAO.getProjectById(projectID);
		 		 
		 if(!(roleDAO.getProjectIDByUser(user)!=null &&roleDAO.getProjectIDByUser(user).contains(project))){
			 role.setUser(user);
			 roleDAO.createRole(user, project,"DEVELOPER");
			 if(!roleDAO.getProjectIDByUser(user).contains(project)){
				 return user;
			 }
		 }
		 return null;
	 }
	 
	

}