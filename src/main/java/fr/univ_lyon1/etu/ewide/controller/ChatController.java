package fr.univ_lyon1.etu.ewide.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.univ_lyon1.etu.ewide.dao.MessageDAO;
import fr.univ_lyon1.etu.ewide.dao.ProjectDAO;
import fr.univ_lyon1.etu.ewide.dao.RoleDAO;
import fr.univ_lyon1.etu.ewide.model.Message;
import fr.univ_lyon1.etu.ewide.model.Project;
import fr.univ_lyon1.etu.ewide.model.User;
import fr.univ_lyon1.etu.ewide.service.AuthenticationUserService;


@Transactional
@Controller
@RequestMapping(value="/project")
public class ChatController {
	
	@Autowired
	AuthenticationUserService authenticationUserSerive;
	
	 @Autowired
	 MessageDAO messageDAO;
	 
	 @Autowired
	 ProjectDAO projectDAO;
	
	
	 /**
	  * get the 50th last messages of the chat
	  * @param projectID (int) : id of the actual project
	  * @return chat
	  * @throws IOException
	  */
	 @RequestMapping(value ="/{projectID}/messages", method = RequestMethod.GET)
	 @PreAuthorize("@userRoleService.isMember(#projectID)")
	 public @ResponseBody ModelAndView  getMessages(@PathVariable("projectID") int projectID) throws IOException{
		 ModelAndView model = new ModelAndView("chat");
		 
		 //to compare if it's the connected user
		 User user = authenticationUserSerive.getCurrentUser();
		 model.addObject("user", user); 		 
		 
		 List<Message> messageslist= messageDAO.getMessagesByProject(projectID,50);
		 model.addObject("messagesList",messageslist);

	     return model;
	 }
	 
	 
	 /**
	  * send a new message 
	  * @param projectID (int)
	  * @return list of messages
	  * @throws IOException
	  */
	 @RequestMapping(value ="/{projectID}/message", method = RequestMethod.POST)
	 @PreAuthorize("@userRoleService.isMember(#projectID)")
	 public @ResponseBody ModelAndView  postMessages(@RequestParam String message, @PathVariable("projectID") int projectID) throws IOException{
		
		 Project project=projectDAO.getProjectById(projectID);
		 User user = authenticationUserSerive.getCurrentUser();
		 
		// create a java calendar instance
		 Calendar calendar = Calendar.getInstance();
		 java.util.Date currentDate = calendar.getTime();
		 java.sql.Date date = new java.sql.Date(currentDate.getTime());
		 messageDAO.sendMessage(user,project,message,date);
		 
		 
		 //--- return view --//
		 ModelAndView model = new ModelAndView("chat");
		 
		 //to compare if it's the connected user
		 model.addObject("user", user); 		 
		 
		 List<Message> messageslist= messageDAO.getMessagesByProject(projectID,50);
		 model.addObject("messagesList",messageslist);

	     return model;
	 }
	 
	 

	
}
