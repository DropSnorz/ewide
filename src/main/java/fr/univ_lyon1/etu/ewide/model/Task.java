package fr.univ_lyon1.etu.ewide.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Collection;

/**
 * Created by Maud on 17/10/2016.
 */
@Entity
@Table(name="Task")
public class Task {

    @Column(name = "taskID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    protected int taskID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projectID", referencedColumnName="projectID")
    protected Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userID", referencedColumnName="userID")
    protected User user;

    @Column(name="type", nullable=false)
    protected String type;

    @Column(name="state", nullable=false)
    protected String state;

    @Lob
    @Column(name="text", nullable=false)
    protected String text;

    /*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taskID")
    protected Task referencedTask;*/

    @Column(name="date", nullable=false)
    protected Date date;

    public Task(){
    	
    	
    }
    
    
    public Task(Project project, User user, String type, String state, String text, Date date) {
		super();
		this.project = project;
		this.user = user;
		this.type = type;
		this.state = state;
		this.text = text;
		this.date = date;
	}


	public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    /*
    public Task getReferecenredTask() {
        return referencedTask;
    }
    public void setReferecenredTask(Task referencedTask) {
        this.referencedTask = referencedTask;
    }
    */

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
