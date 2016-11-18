package fr.univ_lyon1.etu.ewide.dao;

import fr.univ_lyon1.etu.ewide.model.Project;
import fr.univ_lyon1.etu.ewide.model.Role;
import fr.univ_lyon1.etu.ewide.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

/**
 * Created by Mathide on 18/10/2016.
 *  Test class for RoleDao
 *//*
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/test/test-context.xml"})
@TransactionConfiguration(transactionManager="transactionManager")
@Transactional
public class RoleDAOTest {

    /**
     *  DAO for tests, autowired with Spring
     */
 /*   @Autowired
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

        u = daoU.getUserByEmail("plop@plop.com");

        Project p = daoP.getProjectById(1);

        Role r = dao.searchRoleByUserAndProject(u,p);

    }

    @Test
    public void shouldSearchRoleByUserAndProject() throws Exception {

    }

    @Test
    public void shouldCreateOrUpdate() throws Exception {
        Role r = new Role();
        Role r2;
        User u = new User();
        Project p = new Project();

        u.setUsername("Pseudo");
        u.setMail("mail@mail.com");
        u.setPwd("password");

        r.setRoleID(1);
        r.setUser(u);
        r.setProject(p);
        r.setRole("Manager");
        dao.createOrUpdate(u, p,"Manager");*/
      //  r2 = dao.searchRoleByUserAndProject(u,p);

       /* assertEquals(r.getUser(), r2.getUser());
        assertEquals(r.getProject(), r2.getProject());
        assertEquals(r.getRole(), r2.getRole());

    }


}*/