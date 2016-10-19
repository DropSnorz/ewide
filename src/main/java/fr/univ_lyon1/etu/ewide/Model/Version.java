package fr.univ_lyon1.etu.ewide.Model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Maud on 17/10/2016.
 */
@Entity
@Table(name="VERSION")
public class Version {

    @Column(name = "versionID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    int versionID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userID")
    User user;


    //File file;

    @Column(name = "version")
    int version;

    @Column(name = "date")
    Date date;

    @Column(name="text")
    @Lob
    String text;

}
