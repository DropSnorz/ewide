package fr.univ_lyon1.etu.ewide.controller;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.univ_lyon1.etu.ewide.dao.ProjectDAO;
import fr.univ_lyon1.etu.ewide.dao.TaskDAO;
import fr.univ_lyon1.etu.ewide.form.TaskForm;
import fr.univ_lyon1.etu.ewide.model.Project;
import fr.univ_lyon1.etu.ewide.model.Task;
import fr.univ_lyon1.etu.ewide.model.User;
import fr.univ_lyon1.etu.ewide.service.AuthenticationUserService;

@Controller
@Transactional
@RequestMapping(value="/project/{projectId}/task")
public class TaskController {

	@Autowired
	private TaskDAO taskDAO;
	
	@Autowired
	private ProjectDAO projectDAO;
	
	@Autowired
	AuthenticationUserService authenticationUserService;
	

	@PreAuthorize("@userRoleService.isMember(#projectId)")
	@RequestMapping(value="")
	public ModelAndView taskList(@PathVariable("projectId") int projectId){

		ModelAndView model = new ModelAndView("task/task-list");
		List<Task> taskList = taskDAO.getTasksByProjectId(projectId);
		model.addObject("taskList",taskList);
		model.addObject("projectId",projectId);

		return model;
	}
	
	
	//task Create

	@PreAuthorize("@userRoleService.isMember(#projectId)")
	@RequestMapping(value="/create", method = RequestMethod.GET)
	public ModelAndView getTaskCreate(@PathVariable("projectId") int projectId){

		ModelAndView model = new ModelAndView("task/task-create");

		return model;
	}

	@PreAuthorize("@userRoleService.isMember(#projectId)")
	@RequestMapping(value="/create", method = RequestMethod.POST)
	public ModelAndView postTaskCreate(@PathVariable("projectId") int projectId, 
			@ModelAttribute("taskForm") @Valid final TaskForm form,
			final BindingResult bindingResult, final Model model, RedirectAttributes redirectAttributes){

		
		ModelAndView view = new ModelAndView("task/task-create");

		if(bindingResult.hasErrors()){
			view.setViewName("task/task-create");
			view.addObject("taskText", form.getTaskText());
			view.addObject("taskType", form.getTaskType());
			view.addObject("taskState", form.getTaskState());

			return view;

		}
		
		User user = authenticationUserService.getCurrentUser();
		Project project = projectDAO.getProjectById(projectId);
		Task task = new Task(project, user, form.getTaskType(), form.getTaskState(), form.getTaskText(),form.getTaskDescription(), new Date());
		
		taskDAO.createTask(task);
		
        redirectAttributes.addFlashAttribute("SUCCESS_MESSAGE", "Task successfully created !");
		view.setViewName("redirect:../task/");

		return view;
	}
	
	//task Edit
	
	
	@PreAuthorize("@userRoleService.isMember(#projectId)")
	@RequestMapping(value="/{taskId}/edit", method = RequestMethod.GET)
	public ModelAndView getTaskEdit(@PathVariable("projectId") int projectId,
			@PathVariable("taskId") int taskId){

		ModelAndView model = new ModelAndView("task/task-edit");
		Task task = taskDAO.getTaskById(taskId);
		
		model.addObject("task", task);
		

		return model;
	}

	@PreAuthorize("@userRoleService.isMember(#projectId)")
	@RequestMapping(value="/{taskId}/edit", method = RequestMethod.POST)
	public ModelAndView postTaskEdit(@PathVariable("projectId") int projectId, 
			@PathVariable("taskId") int taskId,
			@ModelAttribute("taskForm") @Valid final TaskForm form,
			final BindingResult bindingResult, final Model model,
			RedirectAttributes redirectAttributes){

		
		ModelAndView view = new ModelAndView("task/task-edit");

		if(bindingResult.hasErrors()){
			view.setViewName("task/task-edit");
			view.addObject("taskText", form.getTaskText());
			view.addObject("taskDescription", form.getTaskDescription());
			view.addObject("taskType", form.getTaskType());
			view.addObject("taskState", form.getTaskState());

			return view;

		}
		
		Task task = taskDAO.getTaskById(taskId);
		task.setText(form.getTaskText());
		task.setDescription(form.getTaskDescription());
		task.setType(form.getTaskType());
		task.setState(form.getTaskState());
			
		taskDAO.createOrpdate(task);
		
		redirectAttributes.addFlashAttribute("SUCCESS_MESSAGE", "Task successfully updated !");
		view.setViewName("redirect:../../task/");

		return view;
	}
	
	// Task delete
	
	@PreAuthorize("@userRoleService.isMember(#projectId)")
	@RequestMapping(value="/{taskId}/delete", method = RequestMethod.GET)
	public ModelAndView getTaskDelete(@PathVariable("projectId") int projectId,
			@PathVariable("taskId") int taskId){

		ModelAndView model = new ModelAndView("task/task-delete");
		Task task = taskDAO.getTaskById(taskId);
		model.addObject("task", task);

		return model;
	}
	
	@PreAuthorize("@userRoleService.isMember(#projectId)")
	@RequestMapping(value="/{taskId}/delete", method = RequestMethod.POST)
	public ModelAndView postTaskDelete(@PathVariable("projectId") int projectId,
			@PathVariable("taskId") int taskId, RedirectAttributes redirectAttributes){

		ModelAndView model = new ModelAndView("redirect:../../task");
		taskDAO.deleteTask(taskId);
		
		redirectAttributes.addFlashAttribute("SUCCESS_MESSAGE", "Task successfully deleted !");
		return model;
	}
	

}


