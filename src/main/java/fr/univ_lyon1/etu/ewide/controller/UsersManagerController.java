package fr.univ_lyon1.etu.ewide.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import fr.univ_lyon1.etu.ewide.dao.ProjectDAO;
import fr.univ_lyon1.etu.ewide.dao.RoleDAO;
import fr.univ_lyon1.etu.ewide.dao.UserDAO;
import fr.univ_lyon1.etu.ewide.model.Project;
import fr.univ_lyon1.etu.ewide.model.Role;
import fr.univ_lyon1.etu.ewide.model.User;
import fr.univ_lyon1.etu.ewide.service.AuthenticationUserService;

@Transactional
@Controller
@RequestMapping(value="/project")
public class UsersManagerController extends BaseProjectController{
	
	@Autowired
	AuthenticationUserService authenticationUserService;
	
	 @Autowired
	 public  UserDAO usersDAO;
	 
	 @Autowired
	 RoleDAO roleDAO;
	 
	 @Autowired
	 ProjectDAO projectDAO;
	 
	 public static List<String> roles=Arrays.asList("MANAGER","DEVELOPER","REPORTER"); 
	 
	 
	 /**
	  * gives the list of the users with their role
	  * @param Model 
	  * @return view usersmanager
	  * @throws IOException
	  */
	 @RequestMapping(value ="/{projectId}/users_manager", method = RequestMethod.GET)
	 @PreAuthorize("@userRoleService.isMember(#projectID)")
	 public ModelAndView usersManager(ModelMap Model, @PathVariable("projectId") int projectID) throws IOException{
	     
		 	List<User> listUsers = usersDAO.getAllUsersByProjectID(projectID);
		 	User user = authenticationUserService.getCurrentUser();
		 	Role role=roleDAO.getRoleByUserIdAndProjectId(user.getUserID(), projectID);
	        ModelAndView model = new ModelAndView("usersmanager");
	        
	        //set of roles that exist (for the select)
	        model.addObject("roles",roles);
	        
	        //role of the actual user (to display or not deleted button)
	        model.addObject("userrole",role.getRole());
	          
	        //list of users with their role
	        model.addObject("listUsers", listUsers);
	        model.setViewName("usersmanager");
			model.addObject("projectId",projectID);
	        return model;
	    }
	 

	 /**
	  * to change the roles of the users
	  *	if the role is null, user will be deleted from the project
	  * @param allRequestParams (HashMap<String,String>)
	  * @param projectID (int)
	  * @return
	  */
	 @RequestMapping(value="/{projectId}/users_manager",method=RequestMethod.POST)
	 @PreAuthorize("@userRoleService.isManager(#projectID)")
	 public ModelAndView changeRoles(@RequestParam HashMap<String,String> allRequestParams,@PathVariable("projectId") int projectID){
		 	allRequestParams.remove("_csrf");
		 
		 	ModelAndView model = new ModelAndView("usersmanager");
		 
		 	User user1 = authenticationUserService.getCurrentUser();
		 	Role role=roleDAO.getRoleByUserIdAndProjectId(user1.getUserID(), projectID);
	        
	        //set of roles that exist (for the select)
	        model.addObject("roles",roles);
	        
	        //role of the actual user (to display or not deleted button)
	        model.addObject("userrole",role.getRole());
	        
	        
		 	//must as at less a manager
		 	if(allRequestParams.containsValue("MANAGER")){
			  	for(Map.Entry<String, String>node : allRequestParams.entrySet()){
			  		User user=usersDAO.getUserByUsername(node.getKey());
			  		Project project=projectDAO.getProjectById(projectID);
			  		//user can be deleted
			  		if(node.getValue().equals("null")|| node.getValue()==null){
			  			roleDAO.deleteRole(user,project);
			  		}
			  		//user can be updated
			  		else{
			  			roleDAO.updateRole(user, project, node.getValue());
			  		}
			  	}
			  	model.addObject("success", "The users have been updated.");

		 	}
		 	//there isn't any manager 
		 	else{
		 		//get the roles again
		 		model.addObject("error", "The project must have at less a manager.");
	
		 	}
		 	List<User> listUsers = usersDAO.getAllUsersByProjectID(projectID);
	        model.addObject("listUsers", listUsers);
			model.addObject("projectId",projectID);
	        model.setViewName("usersmanager");
			return model;
	 }
	 
	 /**
	  * display users that can be adding to the project
	  * @param username (String)
	  * @return (ArrayList<User>) list of names
	  */
	 @RequestMapping(value="/{projectId}/users",method=RequestMethod.GET)
	 @ResponseBody
	 public ArrayList<User> autocompleteusers(@RequestParam String username){
		 User user = authenticationUserService.getCurrentUser();
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
	 @RequestMapping(value="/{projectId}/adduser", method=RequestMethod.POST)
	 @ResponseBody
	 @PreAuthorize("@userRoleService.isManager(#projectID)")
	 public User adduser(@RequestParam String username, @PathVariable("projectId") int projectID, Role role){
		 User user = usersDAO.getUserByUsername(username);
		 Project project=projectDAO.getProjectById(projectID);
		 		 
		 if(!(roleDAO.getProjectIDByUser(user)!=null &&roleDAO.getProjectIDByUser(user).contains(project))){
			 role.setUser(user);
			 roleDAO.createRole(user, project,"DEVELOPER");
			 if(roleDAO.getProjectIDByUser(user).contains(project)){
				 return user;
			 }
		 }
		 return null;
	 }
	 
	

}