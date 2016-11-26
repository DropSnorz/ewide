package fr.univ_lyon1.etu.ewide.dao;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import fr.univ_lyon1.etu.ewide.model.Project;
import fr.univ_lyon1.etu.ewide.model.Role;
import fr.univ_lyon1.etu.ewide.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Mathide on 18/10/2016.
 *  Test class for RoleDao
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/test/test-context.xml"})
@Transactional
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class })
@DatabaseSetup(value="/dataset_test.xml")
public class RoleDAOTest {

    /**
     *  DAO for tests, autowired with Spring
     */
    @Autowired
    RoleDAO dao;

    @Autowired
    UserDAO daoU;

    @Autowired
    ProjectDAO daoP;


    @Before
    public void setUp() {
        // Do nothing because there's no set up operation to do
    }

    @After
    public void tearDown() {
        // Do nothing because there's no tear down operation to do
    }

    /** Test : Get the projects of an user.
     *  Not null : check
     *  Number of project expected : check
     */
    @Test
    public void shouldGetProjectIDByUser() {
        User u;
        u = daoU.getUserByEmail("fry@ewide.com");
        assertThat(u).isNotNull();
        assertThat(u.getUserID()).isEqualTo(2);

        Project p;
        p = daoP.getProjectById(1);
        assertThat(p).isNotNull();

        List<Project> list = dao.getProjectIDByUser(u);
        assertThat(list).isNotNull();
        assertThat(list).hasSize(2);
    }

    /** Test : Get the role of an user on a project, by IDs.
     *  Not null : check
     *  Columns expected : check
     */
    @Test
    public void shouldGetRoleByUserIdAndProjectId() {
        User u = daoU.getUserByUsername("fry");
        Project p = daoP.getProjectById(1);
        assertThat(p).isNotNull();
        assertThat(u).isNotNull();

        Role r = dao.getRoleByUserIdAndProjectId(2,1);

        assertThat(r).isNotNull();

        assertThat(r.getRoleID()).isEqualTo(3);
        assertThat(r.getUser()).isEqualTo(u);
        assertThat(r.getProject()).isEqualTo(p);
        assertThat(r.getUsername()).isEqualTo("fry");
        assertThat(r.getRole()).isEqualTo("DEVELOPER");
    }

    /** Test : Search the role of an user on a project, by models.
     *  Not null : check
     *  Columns expected : check
     */
    @Test
    public void shouldSearchRoleByUserAndProject() {
        User u = daoU.getUserByUsername("fry");
        Project p = daoP.getProjectById(1);
        assertThat(p).isNotNull();
        assertThat(u).isNotNull();

        Role r = dao.searchRoleByUserAndProject(u, p);
        assertThat(r).isNotNull();
        assertThat(r.getRoleID()).isEqualTo(3);
        assertThat(r.getUsername()).isEqualTo("fry");
        assertThat(r.getRole()).isEqualTo("DEVELOPER");
    }

    /** Test : Update the role.
     *  Changed expected : check
     */
    @Test
    public void shouldUpdateRole() {
        String testDeveloper = "DEVELOPER";
        String testManager = "MANAGER";
        User u = daoU.getUserByUsername("zoidberg");
        Project p = daoP.getProjectById(1);
        assertThat(u).isNotNull();
        assertThat(p).isNotNull();

        Role r = dao.searchRoleByUserAndProject(u, p);
        assertThat(r.getRole()).isEqualTo(testManager);
        assertThat(r.getRole()).isNotEqualTo(testDeveloper);

        dao.updateRole(u, p, testDeveloper);
        assertThat(r.getRole()).isEqualTo(testDeveloper);
        assertThat(r.getRole()).isNotEqualTo(testManager);

    }

    /** Test : Create a role.
     *  Not null : check
     *  Columns expected : check
     */
    @Test
    public void shouldCreateRole() {
        String testManager = "MANAGER";
        User u = daoU.getUserByUsername("bender");
        Project p = daoP.getProjectById(2);
        assertThat(u).isNotNull();
        assertThat(p).isNotNull();

        Role r = dao.searchRoleByUserAndProject(u, p);
        assertThat(r).isNull();

        dao.createRole(u, p, testManager);

        r = dao.searchRoleByUserAndProject(u, p);
        assertThat(r).isNotNull();
        assertThat(r.getRole()).isEqualTo(testManager);
        assertThat(r.getUser()).isEqualTo(u);
        assertThat(r.getProject()).isEqualTo(p);
    }

    /** Test : Delete role.
     *  the role returns null : check
     */
    @Test
    public void shouldDeleteRole() {
        User u = daoU.getUserByUsername("fry");
        Project p = daoP.getProjectById(2);
        assertThat(u).isNotNull();
        assertThat(p).isNotNull();

        Role r = dao.searchRoleByUserAndProject(u, p);
        assertThat(r).isNotNull();

        dao.deleteRole(u, p);
        r = dao.searchRoleByUserAndProject(u, p);
        assertThat(r).isNull();
    }
}