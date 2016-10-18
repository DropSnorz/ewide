package fr.univ_lyon1.etu.ewide.Model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Maud on 17/10/2016.
 */
@Entity
@Table(name="Project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "projectID")
    int projectID;

    @Column(name="name")
    String name;

    @Column(name="compiler")
    String compiler;

    @OneToMany(mappedBy="project")
    protected Collection<Role> roles;

    @OneToMany(mappedBy = "project")
    protected Collection<Message> messages;

    @OneToMany(mappedBy = "project")
    protected Collection<Task> tasks;

    @OneToMany(mappedBy = "project")
    protected Collection<File> files;


    /**
     * retourne l'id du projet
     * @return int
     */
    public int getProjectID() {
        return projectID;
    }

    /**
     * note l'id du projet
     * @param projectID int
     */
    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    /**
     * retourne le nom du projet
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * note le nom du projet
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * retourne le type de compiler
     * @return String
     */
    public String getCompiler() {
        return compiler;
    }

    /**
     * note le type de compiler
     * @param compiler String
     */
    public void setCompiler(String compiler) {
        this.compiler = compiler;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
}
