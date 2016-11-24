package fr.univ_lyon1.etu.ewide.dao;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import fr.univ_lyon1.etu.ewide.model.Message;
import fr.univ_lyon1.etu.ewide.model.Project;
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
 *  Test class for MessageDao
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/test/test-context.xml"})
@Transactional
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class })
@DatabaseSetup(value="/dataset_test.xml")
public class MessageDAOTest {

    /**
     * DAO for tests, autowired with Spring
     */
    @Autowired
    MessageDAO dao;

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

    /** Test : Get all the Messages of a Project, with a limited number of Message.
     *  Not null : check
     *  Number of project expected : check
     */
    @Test
    public void shouldGetMessagesByProject() throws Exception {
        List<Message> list = dao.getMessagesByProject(1, 25);
        assertThat(list).isNotNull();
        assertThat(list).hasSize(2);

    }

    /** Test : Set a message in a projects.
     *  Not null : check
     *  Number of message expected : check
     */
    @Test
    public void shouldSendMessage() throws Exception {
        User u = daoU.getUserByUsername("fry");
        Project p = daoP.getProjectById(1);
        Date d = new Date(1);

        dao.sendMessage(u, p, "Bonjour", d);
        List<Message> list = dao.getMessagesByProject(1, 25);
        assertThat(list).isNotNull();
        assertThat(list).hasSize(3);
    }
}
