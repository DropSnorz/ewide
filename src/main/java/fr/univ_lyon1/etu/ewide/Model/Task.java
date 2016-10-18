package fr.univ_lyon1.etu.ewide.Model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

/**
 * Created by Maud on 17/10/2016.
 */
@Entity
@Table(name="TASK")
public class Task {

    @Column(name = "taskID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    int taskID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projectID")
    protected Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userID")
    protected User user;

    @Column(name="type")
    String type;

    @Column(name="state")
    String state;

    @Column(name="text")
    String text;

    @Column(name="referencedTask")
    int referecenredTask;

    @Column(name="date")
    Date date;



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

    public int getReferecenredTask() {
        return referecenredTask;
    }

    public void setReferecenredTask(int referecenredTask) {
        this.referecenredTask = referecenredTask;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
