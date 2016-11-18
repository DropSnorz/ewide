package fr.univ_lyon1.etu.ewide.model;

public class Version {

	
	public int versionID;

    public String user;

    public String version;

    public String date;

    public String comment;
    
    public String content;
    
    public Version(int versionID, String User, String version, String date, String comment, String content){
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

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
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
    
    

}
