package fr.univ_lyon1.etu.ewide.Model;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

/**
 * Created by Maud on 17/10/2016.
 */
@Entity
@Table(name = "User")

@NamedQueries({
        @NamedQuery(name="User.getAll",
                query="SELECT u FROM User u"),
        @NamedQuery(name="User.getByUsername",
                query = "SELECT u FROM User u WHERE u.username=:username"),
        @NamedQuery(name="User.getMail",
                query = "SELECT u.mail FROM User u"),
        @NamedQuery(name="User.getUserByEmail",
                query = "SELECT u.mail FROM User u WHERE u.mail=:email"),
        @NamedQuery(name="User.getUsersByProjectID",
                query="SELECT u FROM User u join u.roles r "
                		+ "WHERE r.project.projectID=:projectID and r.user.userID = u.userID"),
})
public class User {

    @Column(name = "userID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    protected int userID;

    @Column(name="pseudo", unique=true, nullable=false)
    protected String username;

    @Column(name="email", unique=true, nullable=false)
    protected String mail;

    @Column(name="pwd", nullable=false)
    protected String pwd;

    @OneToMany(fetch = FetchType.EAGER, mappedBy="user", cascade = CascadeType.ALL)
    protected Collection<Role> roles;


    @OneToMany(mappedBy="user")
    protected List<Message> messages;

    @OneToMany(mappedBy="user")
    protected List<Task> tasks;

    @OneToMany(mappedBy="user")
    protected List<Version> versions;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    
    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
    
    
}
