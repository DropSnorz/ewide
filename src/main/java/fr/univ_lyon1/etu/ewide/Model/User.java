package fr.univ_lyon1.etu.ewide.Model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Maud on 17/10/2016.
 */
@Entity
@Table(name = "USER")
public class User {

    @Column(name = "userID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    int userID;

    @Column(name="pseudo")
    String pseudo;

    @Column(name="email")
    String mail;

    @Column(name="pwd")
    String pwd;

    @OneToMany(mappedBy="user")
    protected Collection<Role> roles;

    @OneToMany(mappedBy = "user")
    protected Collection<Message> messages;

    @OneToMany(mappedBy = "user")
    protected Collection<Task> tasks;

    @OneToMany(mappedBy = "user")
    protected Collection<Version> versions;

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
