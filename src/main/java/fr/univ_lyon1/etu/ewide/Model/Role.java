package fr.univ_lyon1.etu.ewide.Model;

import javax.persistence.*;

/**
 * Created by Maud on 17/10/2016.
 */
@Entity
@Table(name="Role")
//TODO Fix queries
@NamedQueries({
    		//query="SELECT r FROM Role r, User u join r.user rRole join u.roles uRole WHERE r.project=:projectID "),
    		query="SELECT r FROM Role r, User u WHERE r.project.projectID=:projectID and r.user.userID = u.userID"),
        @NamedQuery(name="Role.getProjectIDByUser",
                    query = "SELECT r.project FROM Role r WHERE r.user = :user") 
})
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "roleID")
    protected int roleID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userID", referencedColumnName="userID")
    protected User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projectID", referencedColumnName="projectID")
    protected Project project;

    @Column(name="role")
    protected String role;

	public int getRoleID() {
		return roleID;
	}

	public void setRoleID(int roleID) {
		this.roleID = roleID;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPseudo(){
		return this.user.getPseudo();
	}
    
}
