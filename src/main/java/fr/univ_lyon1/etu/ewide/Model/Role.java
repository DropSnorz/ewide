package fr.univ_lyon1.etu.ewide.Model;

import javax.persistence.*;

/**
 * Created by Maud on 17/10/2016.
 */
@Entity
@Table(name="Role")
@NamedQueries({
        @NamedQuery(name="Role.getUsersIDByProject",
                    query="SELECT r.userID FROM Role r WHERE r.projectID=:projectID"),
        @NamedQuery(name="Role.getProjectIDByUser",
                    query = "SELECT r.projectID FROM Role r WHERE r.userID=:userID")
})
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "roleID")
    protected int roleID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User", referencedColumnName="userID")
    protected User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Project", referencedColumnName="projectID")
    protected Project project;

    @Column(name="role")
    protected String role;

}
