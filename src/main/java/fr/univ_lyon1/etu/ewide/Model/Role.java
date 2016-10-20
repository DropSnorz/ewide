package fr.univ_lyon1.etu.ewide.Model;

import javax.persistence.*;

/**
 * Created by Maud on 17/10/2016.
 */
@Entity
@Table(name="Role")
//TODO Fix queries
@NamedQueries({
       /* @NamedQuery(name="Role.getUsersIDByProject",
                    query="SELECT r.userID FROM Role r WHERE r.projectID=:projectID"), */
        @NamedQuery(name="Role.getProjectIDByUser",
                    query = "SELECT r.project FROM Role r WHERE r.user = :user") 
})
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "roleID")
    protected int roleID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userID", referencedColumnName="userID")
    protected User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projectID", referencedColumnName="projectID")
    protected Project project;

    @Column(name="role")
    protected String role;

}
