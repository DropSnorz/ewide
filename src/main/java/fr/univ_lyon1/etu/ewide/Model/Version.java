package fr.univ_lyon1.etu.ewide.Model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Maud on 17/10/2016.
 */
@Entity
@Table(name="Version")
public class Version {

    @Column(name = "versionID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    protected int versionID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userID", referencedColumnName="userID")
    protected User user;

    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fileID", referencedColumnName="fileID")
    protected File file;
    

    @Column(name = "version", nullable=false)
    protected int version;

    @Column(name = "date", nullable=false)
    protected Date date;

    @Lob
    @Column(name="text", nullable=false)
    protected String text;

}
