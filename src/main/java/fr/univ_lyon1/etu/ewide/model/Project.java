package fr.univ_lyon1.etu.ewide.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

/**
 * Created by Maud on 17/10/2016.
 */
@Entity
@Table(name="Project")
@NamedQueries({
	@NamedQuery(name = "Project.getAll", query = "Select c FROM Project c"),
	@NamedQuery(name="Project.getProjectById", query="SELECT p FROM Project p WHERE p.projectID=:projectID"),
})
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "projectID")
    protected int projectID;

    @Column(name="name", nullable=false)
    protected String name;

    @Column(name="compiler")
    protected String compiler;

    @Lob
    @Column(name="description")
    protected String description;

    @Column(name="fileTree", unique=true, nullable=false)
    protected String fileTree;

    @Column(name="linkMakefile", unique=true)
    protected String linkMakefile;

    //@OneToMany(mappedBy="project")
    @OneToMany(fetch = FetchType.EAGER, mappedBy="project", cascade = CascadeType.ALL)
    protected Collection<Role> roles;

    @OneToMany(mappedBy="project")
    protected List<Message> messages;

    @OneToMany(mappedBy="project")
    protected List<Task> tasks;

    @Lob
    @Column(name="wiki")
    protected String wiki;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/*public String getLinkRepo() {
		return linkRepo;
	}

	public void setLinkRepo(String linkRepo) {
		this.linkRepo = linkRepo;
	}*/

	public String getFileTree() {
		return fileTree;
	}

	public void setFileTree(String fileTree) {
		this.fileTree = fileTree;
	}

	public String getLinkMakefile() {
		return linkMakefile;
	}

	public void setLinkMakefile(String linkMakefile) {
		this.linkMakefile = linkMakefile;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

    public String getWiki() {
        return wiki;
    }

    public void setWiki(String wiki) {
        this.wiki = wiki;
    }
    
    
}
