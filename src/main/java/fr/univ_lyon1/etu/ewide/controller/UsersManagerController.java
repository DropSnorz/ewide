package fr.univ_lyon1.etu.ewide.controller;

import java.io.IOException;
import java.util.List;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.univ_lyon1.etu.ewide.Model.Role;
import fr.univ_lyon1.etu.ewide.Model.User;
import fr.univ_lyon1.etu.ewide.dao.ProjectDAO;
import fr.univ_lyon1.etu.ewide.dao.UserDAO;


@Controller
@RequestMapping(value="/project/{projectID}")
public class UsersManagerController {
	
	 @Autowired
	 public  UserDAO usersDAO;
	 
	 
	 @RequestMapping(value ="/usersManager", method = RequestMethod.GET)
	 	public ModelAndView usersManager(ModelMap Model) throws IOException{
	 		System.out.println("ON doit bind avec la view");
	        List<Role> listUsers = usersDAO.getAllUsersByProjectID(1);
	        ModelAndView model = new ModelAndView("usersmanager");
	        model.addObject("listUsers", listUsers);
	        model.setViewName("usersmanager");
	        return model;
	    }
	

}