package fr.univ_lyon1.etu.ewide.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.univ_lyon1.etu.ewide.dao.MessageDAO;
import fr.univ_lyon1.etu.ewide.dao.RoleDAO;
import fr.univ_lyon1.etu.ewide.model.Message;
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
	
	
	 @RequestMapping(value ="/{projectID}/messages", method = RequestMethod.GET)
	 @PreAuthorize("@userRoleService.isMember(#projectID)")
	 public String  getMessages(RedirectAttributes redirectAttributes, @PathVariable("projectID") int projectID) throws IOException{
		 ModelAndView model = new ModelAndView("chat");
		 
		 //to compare if it's the connected user
		 User user = authenticationUserSerive.getCurrentUser();
		 redirectAttributes.addFlashAttribute("user",user);
		    
		// model.addObject("user",user);
		 
		 
		 List<Message> messageslist= messageDAO.getMessagesByProject(projectID,10);
		 redirectAttributes.addFlashAttribute("messagesList", messageslist);
		// model.addObject("messagesList",messageslist);

	     return "chat";
	 }

	
}
