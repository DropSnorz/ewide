package fr.univ_lyon1.etu.ewide.Model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Maud on 17/10/2016.
 */
@Entity
@Table(name="Message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "messageID")
    protected int messageID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User", referencedColumnName="userID")
    protected User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Project", referencedColumnName="projectID")
    protected Project project;

    @Lob
    @Column(name="text", nullable=false)
    protected String text;

    @Column(name="date", nullable=false)
    protected Date date;

    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
