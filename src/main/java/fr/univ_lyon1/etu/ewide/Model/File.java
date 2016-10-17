package fr.univ_lyon1.etu.ewide.Model;

import javax.persistence.*;

/**
 * Created by Maud on 17/10/2016.
 */
public class File {

    @Column(name = "fileID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    int fileID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projectID")
    Project project;

    @Column(name="name")
    String name;

    @Column(name="path")
    String path;

    @Column(name = "type")
    String type;

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
