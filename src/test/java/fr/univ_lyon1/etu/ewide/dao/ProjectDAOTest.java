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
    public void setUp() {
        // Do nothing because there's no set up operation to do
    }

    @After
    public void tearDown() {
        // Do nothing because there's no tear down operation to do
    }

    /** Test : Get all the projects.
     *  Not null : check
     *  Number of project expected : check
     */
    @Test
    public void shouldGetAllProjects() {
        List<Project> list = dao.getAllProjects();
        assertThat(list).isNotNull();
        assertThat(list).hasSize(3);
    }

    /** Test : Create a project with a Project model.
     *  Number of project expected : check
     */
    @Test
    public void shouldCreateProject() {
        Project p = new Project();
        p.setName("Project ++");
        p.setDescription("Je suis une description");
        p.setCompiler("compile");
        p.setWiki("Ahah");

        dao.createProject(p);

        List<Project> list = dao.getAllProjects();
        assertThat(list).isNotNull();
        assertThat(list).hasSize(4);
    }

    /** Test : Get project by its ID.
     *  Not null : check
     *  Columns expected : check
     */
    @Test
    public void shouldGetProjectById() {
        Project p;
        p = dao.getProjectById(1);

        assertThat(p).isNotNull();
        assertThat(p.getProjectID()).isEqualTo(1);
        assertThat(p.getName()).isEqualTo("Projet MULTIMIF");
        assertThat(p.getCompiler()).isEqualTo("compiler");
        assertThat(p.getDescription()).isEqualTo("unknow");
        assertThat(p.getWiki()).isEqualTo("wiki");
    }

    /** Test : Update the wiki of a project.
     *  Change observed in the project : check
     */
    @Test
    public void shouldUpdateWiki() {
        String test = "Je suis un nouveau wiki";
        Project p = dao.getProjectById(1);
        assertThat(p.getWiki()).isNotEqualTo(test);

        dao.updateWiki(p.getProjectID(), test);
        assertThat(p.getWiki()).isEqualTo(test);
    }

    /** Test : Change the compiler
     *  Change observed in the project : check
     */
    @Test
    public void shouldSetCompiler() {
        String test = "Je suis un nouveau compiler";
        Project p = dao.getProjectById(1);
        assertThat(p.getCompiler()).isNotEqualTo(test);

        dao.setCompiler(p, test);
        assertThat(p.getCompiler()).isEqualTo(test);
    }
}