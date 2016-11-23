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

import javax.persistence.TypedQuery;
import java.sql.Date;
import java.util.ArrayList;
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
     *  DAO for tests, autowired with Spring
     */
    @Autowired
    TaskDAO dao;

    @Autowired
    ProjectDAO daoP;

    @Autowired
    UserDAO daoU;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void shouldGetTaskById() throws Exception {
        Task t = dao.getTaskById(1);
        assertThat(t).isNotNull();
        assertThat(t.getText()).isEqualTo("Add project name in pages");
        assertThat(t.getState()).isEqualTo("New");
    }

    @Test
    public void shouldGetTasksByProject() throws Exception {
        List<Task> list = dao.getTasksByProjectId(1);
        assertThat(list).isNotNull();
        assertThat(list).hasSize(6);
    }

   /* @Test
    public void shouldGetTasksByProjectIdAndState() throws Exception {
        List<Task> list = dao.getTasksByProjectIdAndState(1, "New");
        assertThat(list).isNotNull();
        assertThat(list).hasSize(3);
    }*/

    /*@Test
    public void shouldGetTasksByProjectIdAndOwnerId() throws Exception {
        List<Task> list = dao.getTasksByProjectIdAndOwnerId(1, 1,"New");
        assertThat(list).isNotNull();
        assertThat(list).hasSize(2);
    }*/

    @Test
    public void shouldCreateTask() throws Exception {
        Task t = new Task();
        Project p = daoP.getProjectById(1);
        User u = daoU.getUserByUsername("zoidberg");
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

        // Nombre total de taches pour le pojet un est à 7
        List<Task> list = dao.getTasksByProjectId(1);
        assertThat(list).isNotNull();
        assertThat(list).hasSize(7);
    }

    @Test
    public void shouldCreateOrUpdate() throws Exception {

    }

    @Test
    public void shouldUpdateTask() throws Exception {

    }

    @Test
    public void shouldDeleteTaskByTask() throws Exception {
        Task t = dao.getTaskById(1);
        dao.deleteTask(t);

        // Nombre total de taches pour le pojet un est à 5
        List<Task> list = dao.getTasksByProjectId(1);
        assertThat(list).isNotNull();
        assertThat(list).hasSize(5);

    }

    @Test
    public void shouldDeleteTaskById() throws Exception {
        dao.deleteTask(1);

        // Nombre total de taches pour le pojet un est à 5
        List<Task> list = dao.getTasksByProjectId(1);
        assertThat(list).isNotNull();
        assertThat(list).hasSize(5);

    }
/*
    public Task createOrUpdate(Task task){

        em.merge(task);
        return task;
    }
    public Task Update(int taskID, String type, String state, String text, Project project, Date date) {
        Task t = new Task();
        t.setTaskID(taskID);
        t.setType(type);
        t.setState(state);
        t.setText(text);
        t.setProject(project);
        t.setDate(date);
        em.merge(t);
        return t;
    }*/

}