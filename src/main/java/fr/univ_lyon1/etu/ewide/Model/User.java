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
        @NamedQuery(name="User.getByName",
                query = "SELECT u FROM User u WHERE u.pseudo=:name"),
        @NamedQuery(name="User.getMail",
                query = "SELECT u.mail FROM User u")
})
public class User {

    @Column(name = "userID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    protected int userID;

    @Column(name="pseudo", unique=true, nullable=false)
    protected String pseudo;

    @Column(name="mail", unique=true, nullable=false)
    protected String mail;

    @Column(name="pwd", nullable=false)
    protected String pwd;

    @OneToMany(mappedBy="user")
    protected Collection<Role> roles;

    @ElementCollection
    @CollectionTable(name = "Message", joinColumns = {@JoinColumn(name="userID")})
    protected List<Message> messages;

    @ElementCollection
    @CollectionTable(name = "Task", joinColumns = {@JoinColumn(name="userID")})
    protected List<Task> tasks;

    @ElementCollection
    @CollectionTable(name = "Version", joinColumns = {@JoinColumn(name="userID")})
    protected List<Version> versions;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
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
