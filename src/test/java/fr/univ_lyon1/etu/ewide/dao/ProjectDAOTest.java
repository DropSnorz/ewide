package fr.univ_lyon1.etu.ewide.dao;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import fr.univ_lyon1.etu.ewide.model.Project;
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
 * Created by Mathilde on 18/10/2016.
 *  Test class for ProjectDao
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/test/test-context.xml"})
@Transactional
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class })
@DatabaseSetup(value="/dataset_test.xml")
public class ProjectDAOTest {

    /**
     * DAO for tests, autowired with Spring
     * Test class for ProjectDAO
     */
    @Autowired
    ProjectDAO dao;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void shouldGetAllProjects() throws Exception {
        List<Project> list = dao.getAllProjects();
        assertThat(list).hasSize(3);
    }

    @Test
    public void shouldCreateProject() throws Exception {
        Project p = new Project();
        p.setName("Project ++");
        p.setDescription("Je suis une description");
        p.setCompiler("compile");
        p.setWiki("Ahah");

        dao.createProject(p);
        List<Project> list = dao.getAllProjects();
        assertThat(list).hasSize(4);
    }

    @Test
    public void shouldGetProjectById() throws Exception {
        Project p;
        p = dao.getProjectById(1);

        assertThat(p).isNotNull();
        assertThat(p.getProjectID()).isEqualTo(1);
        assertThat(p.getName()).isEqualTo("Projet MULTIMIF");
    }

    @Test
    public void shouldUpdateFile() throws Exception {
        Project p = dao.getProjectById(1);

        assertThat(p.getName()).isNotEqualTo("Je suis un nouveau nom");
        assertThat(p.getCompiler()).isNotEqualTo("Compilation!");

        dao.UpdateFile(p.getProjectID(), "Je suis un nouveau nom", "Compilation!");

        assertThat(p.getName()).isEqualTo("Je suis un nouveau nom");
        assertThat(p.getCompiler()).isEqualTo("Compilation!");
    }

    @Test
    public void shouldUpdateWiki() throws Exception {
        Project p = dao.getProjectById(1);
        assertThat(p.getWiki()).isNotEqualTo("Je suis un nouveau wiki");

        dao.updateWiki(p.getProjectID(), "Je suis un nouveau wiki");

        assertThat(p.getWiki()).isEqualTo("Je suis un nouveau wiki");
    }

    @Test
    public void shouldSetCompiler() throws Exception {
        Project p = dao.getProjectById(1);

        assertThat(p.getCompiler()).isNotEqualTo("Je suis un nouveau compiler");

        dao.setCompiler(p, "Je suis un nouveau compiler");

        assertThat(p.getCompiler()).isEqualTo("Je suis un nouveau compiler");
    }
}