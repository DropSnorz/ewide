package fr.univ_lyon1.etu.ewide.Model;

import javax.persistence.*;

/**
 * Created by Maud on 17/10/2016.
 */
@Entity
@Table(name="ROLE")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "roleID")
    int roleID;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userID")
    protected User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projectID")
    protected Project project;

    @Column(name="role")
    String role;

}
