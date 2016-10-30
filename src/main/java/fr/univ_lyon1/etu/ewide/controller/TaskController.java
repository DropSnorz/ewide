package fr.univ_lyon1.etu.ewide.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.univ_lyon1.etu.ewide.Model.Task;
import fr.univ_lyon1.etu.ewide.dao.TaskDAO;

@Controller
@RequestMapping(value="/project/{projectId}/task")
public class TaskController {

	@Autowired
	private TaskDAO taskDAO;
	
	@PreAuthorize("@userRoleService.isMember(#projectId)")
	@RequestMapping(value="")
	public ModelAndView taskList(@PathVariable("projectId") int projectId){


		ModelAndView model = new ModelAndView("task/task-list");
		List<Task> taskList = taskDAO.getTasksByProjectId(projectId);
		model.addObject("taskList",taskList);
		model.addObject("projectId",projectId);

		
		return model;
	}
}
