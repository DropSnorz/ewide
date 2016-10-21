package fr.univ_lyon1.etu.ewide.Model;

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

    @Column(name="name", unique=true, nullable=false)
    protected String name;

    @Column(name="compiler")
    protected String compiler;

    @Lob
    @Column(name="description")
    protected String description;

    @Column(name="linkRepo", unique=true, nullable=false)
    protected String linkRepo;

    @Column(name="fileTree", unique=true, nullable=false)
    protected String fileTree;

    @Column(name="linkMakefile", unique=true, nullable=false)
    protected String linkMakefile;

    @OneToMany(mappedBy="project")
    protected Collection<Role> roles;

    @OneToMany(mappedBy="project")
    protected List<Message> messages;

    @OneToMany(mappedBy="project")
    protected List<Task> tasks;

    @OneToMany(mappedBy="project")
    protected List<File> files;


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

	public String getLinkRepo() {
		return linkRepo;
	}

	public void setLinkRepo(String linkRepo) {
		this.linkRepo = linkRepo;
	}

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

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}
    
    
}
