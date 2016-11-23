package fr.univ_lyon1.etu.ewide.model;

/**
 * Created by Steven on 14/11/2016.
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="Version")
public class Version {

	@Column(name = "version")
    @Id
    protected String version;
	
	@Column(name="versionID", nullable=false) 
	protected int versionID;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userID", referencedColumnName="userID")
	protected User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projectID", referencedColumnName="projectID")
	protected Project project;
	
	@Transient 
    protected String date;
    
    @Transient 
    protected String comment;
    
    @Transient 
    protected String content;
    
    public Version(){}
    
    public Version(int versionID, User User, String version, String date, String comment, String content){
    	this.versionID = versionID;
    	this.user = User;
    	this.version = version;
    	this.date = date;
    	this.comment = comment;
    	this.content = content;
    }

	public int getVersionID() {
		return versionID;
	}

	public void setVersionID(int versionID) {
		this.versionID = versionID;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	
    
    

}
