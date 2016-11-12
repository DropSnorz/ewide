package fr.univ_lyon1.etu.ewide.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TaskForm {


	@NotNull
	@Size(min=2, max=45)
	private String taskText;

	@Size(max=2000)
	private String taskDescription;
	
	@NotNull
	@Size(min=2, max=45)
	private String taskState;

	@NotNull
	@Size(min=2, max=45)
	private String taskType;

	public String getTaskText() {
		return taskText;
	}

	public void setTaskText(String taskText) {
		this.taskText = taskText;
	}
	

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDesription) {
		this.taskDescription = taskDesription;
	}

	public String getTaskState() {
		return taskState;
	}

	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	
	
}
