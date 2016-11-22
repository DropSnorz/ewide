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
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void shouldGetProjectIDByUser() throws Exception {
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

    @Test
    public void shouldGetRoleByUserIdAndProjectId() throws Exception {
        Role r = dao.getRoleByUserIdAndProjectId(2,1);
        assertThat(r).isNotNull();
        assertThat(r.getRoleID()).isEqualTo(3);
        assertThat(r.getUsername()).isEqualTo("fry");
        assertThat(r.getRole()).isEqualTo("DEVELOPER");
    }

    @Test
    public void shouldSearchRoleByUserAndProject() throws  Exception {
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

    @Test
    public void shouldUpdateRole() throws Exception {
        User u = daoU.getUserByUsername("zoidberg");
        Project p = daoP.getProjectById(1);
        assertThat(u).isNotNull();
        assertThat(p).isNotNull();

        Role r = dao.searchRoleByUserAndProject(u, p);
        assertThat(r.getRole()).isEqualTo("MANAGER");
        assertThat(r.getRole()).isNotEqualTo("DEVELOPER");

        dao.updateRole(u, p, "DEVELOPER");
        assertThat(r.getRole()).isEqualTo("DEVELOPER");
        assertThat(r.getRole()).isNotEqualTo("MANAGER");

    }

    @Test
    public void shouldCreateRole() throws Exception {
        User u = daoU.getUserByUsername("bender");
        Project p = daoP.getProjectById(2);
        assertThat(u).isNotNull();
        assertThat(p).isNotNull();

        Role r = dao.searchRoleByUserAndProject(u, p);
        assertThat(r).isNull();

        dao.createRole(u, p, "MANAGER");
        r = dao.searchRoleByUserAndProject(u, p);
        assertThat(r).isNotNull();
    }

    @Test
    public void shouldDeleteRole() throws Exception {
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