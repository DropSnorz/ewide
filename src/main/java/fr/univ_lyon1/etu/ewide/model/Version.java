package fr.univ_lyon1.etu.ewide.model;

public class Version {

	
	public int versionID;

    public String user;

    public String version;

    public String date;

    public String comment;
    
    public Version(int versionID, String User, String version, String date, String comment){
    	this.versionID = versionID;
    	this.user = User;
    	this.version = version;
    	this.date = date;
    	this.comment = comment;
    }

}
