package fr.univ_lyon1.etu.ewide.dao;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import fr.univ_lyon1.etu.ewide.model.Project;
import fr.univ_lyon1.etu.ewide.model.Task;
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

import java.sql.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Isabelle on 21/10/16.
 *  Test class for TaskDAO
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/test/test-context.xml"})
@Transactional
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class })
@DatabaseSetup(value="/dataset_test.xml")
public class TaskDAOTest {

    /**
     *  DAOs for tests, autowired with Spring
     */
    @Autowired
    TaskDAO dao;

    @Autowired
    ProjectDAO daoP;

    @Autowired
    UserDAO daoU;

    @Before
    public void setUp() {
        // Do nothing because there's no set up operation to do
    }

    @After
    public void tearDown() {
        // Do nothing because there's no tear down operation to do
    }

    /** Test : Get the task with its ID.
     *  Not null : check
     *  Columns expected : check
     */
    @Test
    public void shouldGetTaskById() {
        Task t = dao.getTaskById(1);
        assertThat(t).isNotNull();
        assertThat(t.getText()).isEqualTo("Add project name in pages");
        assertThat(t.getType()).isEqualTo("TODO");
        assertThat(t.getState()).isEqualTo("New");
    }

    /** Test : Get the tasks of a project.
     *  Not null : check
     *  Number of task expected : check
     *  Result expected when empty : check
     */
    @Test
    public void shouldGetTasksByProjectId() {
        List<Task> list = dao.getTasksByProjectId(1);
        assertThat(list).isNotNull();
        assertThat(list).hasSize(6);

        list = dao.getTasksByProjectId(3);
        assertThat(list).isNotNull();
        assertThat(list).isEmpty();
    }

    /** Test : Get the tasks active/inactive on a project.
     *  Not null : check
     *  Number of task opened expected : check
     *  Number of task closed expected : check
     */
    @Test
    public void shouldGetTasksByProjectIdAndState() {
        List<Task> list = dao.getTasksByProjectIdAndState(1, "active");
        assertThat(list).isNotNull();
        assertThat(list).hasSize(5);

        list = dao.getTasksByProjectIdAndState(1, "inactive");
        assertThat(list).isNotNull();
        assertThat(list).hasSize(1);
    }

    /** Test : Get the tasks active/inactive on a project for an user.
     *  Not null : check
     *  Number of task opened expected : check
     *  Number of task closed expected : check
     */
    @Test
    public void shouldGetTasksByProjectIdAndOwnerId() {
        List<Task> list = dao.getTasksByProjectIdAndOwnerId(1, 3,"active");
        assertThat(list).isNotNull();
        assertThat(list).hasSize(2);

        list = dao.getTasksByProjectIdAndOwnerId(1, 3,"inactive");
        assertThat(list).isNotNull();
        assertThat(list).hasSize(1);
    }

    /** Test : Create a Task.
     *  Not null : check
     *  Number of project expected : check
     */
    @Test
    public void shouldCreateTask() {
        Task t = new Task();
        Project p = daoP.getProjectById(1);
        User u = daoU.getUserByUsername("zoidberg");
        assertThat(p).isNotNull();
        assertThat(u).isNotNull();

        Date d = new Date(1);
        t.setProject(p);
        t.setDate(d);
        t.setState("New");
        t.setText("Faire des JUnit");
        t.setUser(u);
        t.setType("TODO");

        Task result = dao.createTask(t);
        assertThat(result).isNotNull();
        assertThat(result.getText()).isEqualTo("Faire des JUnit");

        // Nombre total de taches pour le projet 1 est à 7
        List<Task> list = dao.getTasksByProjectId(1);
        assertThat(list).isNotNull();
        assertThat(list).hasSize(7);
    }

    /** Test : Create or Update a Task.
     *  Not null : check
     *  Number of project expected for create: check
     *  Number of project expected for update: check
     */
    @Test
    public void shouldCreateOrUpdate() {
        String testText = "Faire des Junit";
        Project p = daoP.getProjectById(1);
        User u = daoU.getUserByUsername("zoidberg");
        assertThat(p).isNotNull();
        assertThat(u).isNotNull();

        Task t = new Task();
        Date d = new Date(1);
        t.setProject(p);
        t.setDate(d);
        t.setState("New");
        t.setText(testText);
        t.setUser(u);
        t.setType("TODO");

        Task result = dao.createOrUpdate(t);
        assertThat(result).isNotNull();
        assertThat(result.getText()).isEqualTo(testText);

        // Nombre total de taches pour le projet 1 est à 7
        List<Task> list = dao.getTasksByProjectId(1);
        assertThat(list).isNotNull();
        assertThat(list).hasSize(7);

        t.setTaskID(1);
        result = dao.createOrUpdate(t);
        assertThat(result).isNotNull();
        assertThat(result.getText()).isEqualTo(testText);

        // Nombre total de taches pour le projet 1 est toujours à 7
        list = dao.getTasksByProjectId(1);
        assertThat(list).isNotNull();
        assertThat(list).hasSize(7);
    }

    /** Test : Delete a task.
     *  Not null : check
     *  Number of project expected : check
     */
    @Test
    public void shouldDeleteTaskByTask() {
        Task t = dao.getTaskById(1);
        dao.deleteTask(t);

        // Nombre total de taches pour le pojet un est à 5
        List<Task> list = dao.getTasksByProjectId(1);
        assertThat(list).isNotNull();
        assertThat(list).hasSize(5);

    }

    /** Test : Get the projects of an user.
     *  Not null : check
     *  Number of project expected : check
     */
    @Test
    public void shouldDeleteTaskById() {
        dao.deleteTask(1);

        // Nombre total de taches pour le pojet un est à 5
        List<Task> list = dao.getTasksByProjectId(1);
        assertThat(list).isNotNull();
        assertThat(list).hasSize(5);
    }

}