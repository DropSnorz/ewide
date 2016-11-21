package fr.univ_lyon1.etu.ewide.dao;

import fr.univ_lyon1.etu.ewide.model.Project;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * Created by Mathilde on 18/10/2016.
 *  Test class for ProjectDao
 *//*
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/test/test-context.xml"})
@TransactionConfiguration(transactionManager="transactionManager")
@Transactional
public class ProjectDAOTest {

    /**
     *  DAO for tests, autowired with Spring
     *  Test class for ProjectDAO
    *//*
    @Autowired
    ProjectDAO dao;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }
/*
    @Test
    public void shouldGetAllProjects() throws Exception {

    }

    @Test
    public void shouldCreateProject() {
        Project p = new Project();
        p.setProjectID(20);
        p.setName("Project ++");
        p.setLinkRepo("Repertoire, par ici");
        p.setFileTree("L'arboresence est ici");
        p.setDescription("Je suis une description");
        p.setCompiler("compile");

        dao.createProject(p);
    }

    @Test
    public void shouldUpdateFile(){

    }

    @Test
    public void shouldGetProjectById(){
        Project p;
        p = dao.getProjectById(1);
    }
}*/