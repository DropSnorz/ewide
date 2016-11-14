package fr.univ_lyon1.etu.ewide.model;

import java.util.List;

import javax.persistence.*;

import fr.univ_lyon1.etu.ewide.model.Version;

/**
 * Created by Maud on 17/10/2016.
 */
@Entity
@Table(name="File")
public class File {

    @Column(name = "fileID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    protected int fileID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projectID", referencedColumnName="projectID")
    protected Project project;
    
    /*@OneToMany(mappedBy="file")
    protected List<Version> versions;*/

    @Column(name="name", nullable=false)
    protected String name;

    @Column(name="path", unique=true, nullable=false)
    protected String path;

    @Column(name = "type", nullable=false)
    protected String type;

    public int getFileID() {
        return fileID;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
