package fr.univ_lyon1.etu.ewide.dao;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import fr.univ_lyon1.etu.ewide.model.User;

import com.github.springtestdbunit.annotation.DatabaseSetup;
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
 * Test class for UserDao
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/test/test-context.xml")
@Transactional
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class })
@DatabaseSetup(value="/dataset_test.xml")
public class UserDAOTest {

    /**
     *  DAO for tests, autowired with Spring
     */
    @Autowired
    private UserDAO dao;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void shouldGetUserByEmail() throws Exception {
        User searchResults = dao.getUserByEmail("zoidberg@ewide.com");
        assertThat(searchResults.getUserID()).isEqualTo(1);
    }

    @Test
    public void shouldGetUserByUsername() throws Exception {
        User searchResults = dao.getUserByUsername("zoidberg");
        assertThat(searchResults.getUserID()).isEqualTo(1);
    }

    @Test
    public void shouldCreateUser() throws Exception {
        User u, u2;
        u2 = dao.createUser("mailil@mail.com","Pseudodo","password");
        u = dao.createUser("mail@mail.com","Pseudo","password");

        assertThat(u2.getUserID()).isNotEqualTo(u.getUserID());
        assertThat(u.getUsername()).isEqualTo("Pseudo");
        assertThat(u.getMail()).isEqualTo("mail@mail.com");
        assertThat(u.getPwd()).isEqualTo("password");
    }

    @Test
    public void shouldUpdateUser() throws Exception {
        User u = new User();
        User u2= new User();

        u.setUserID(1);
        u.setUsername("Pseudo");
        u.setMail("mail@mail.com");
        u.setPwd("password");

        u = dao.updateUser(u);

        assertThat(u.getUserID()).isEqualTo(1);
        assertThat(u.getUsername()).isNotEqualTo("zoidgerg");
        assertThat(u.getUsername()).isEqualTo("Pseudo");
        assertThat(u.getMail()).isNotEqualTo("zoidberg@ewide.com");
        assertThat(u.getMail()).isEqualTo("mail@mail.com");

    }

    @Test
    public void shouldGetAllUsersByProjectID() throws Exception {
        List<User> list = dao.getAllUsersByProjectID(1);
        assertThat(list).hasSize(2);
    }

    @Test
    public void shouldGetUsersStartedwith() throws Exception {
        User u = dao.getUserByEmail("fry@ewide.com");
        List<User> list = dao.getUsersStartedwith("zoid", u);
        assertThat(list).hasSize(1);
    }

}