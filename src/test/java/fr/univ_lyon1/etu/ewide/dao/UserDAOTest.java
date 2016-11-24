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

    /**  Test : Get the User with his email
     *  Not null : check
     *  All the columns are OK
     */
    @Test
    public void shouldGetUserByEmail() throws Exception {
        User searchResults = dao.getUserByEmail("zoidberg@ewide.com");
        assertThat(searchResults).isNotNull();
        assertThat(searchResults.getUserID()).isEqualTo(1);
        assertThat(searchResults.getUsername()).isEqualTo("zoidberg");
        assertThat(searchResults.getMail()).isEqualTo("zoidberg@ewide.com");
        assertThat(searchResults.getPwd()).isEqualTo("$2a$10$WzSOqXRoTXA7QkIdhP3hl.2R85JSgG2nGYltG3HC0DftrefgFH5o6");
    }

    /** Test : Get the User with his username
     *  Not null : check
     *  All the columns are OK : check
     */
    @Test
    public void shouldGetUserByUsername() throws Exception {
        User searchResults = dao.getUserByUsername("zoidberg");
        assertThat(searchResults).isNotNull();
        assertThat(searchResults.getUserID()).isEqualTo(1);
        assertThat(searchResults.getUsername()).isEqualTo("zoidberg");
        assertThat(searchResults.getMail()).isEqualTo("zoidberg@ewide.com");
        assertThat(searchResults.getPwd()).isEqualTo("$2a$10$WzSOqXRoTXA7QkIdhP3hl.2R85JSgG2nGYltG3HC0DftrefgFH5o6");
    }

    /** Test : Create an user with Email, username and password
     *  Not null : check
     *  2 users created doesn't have the same ID : check
     *  All the columns given are OK : check
     */
    @Test
    public void shouldCreateUser() throws Exception {
        User u, u2;
        u = dao.createUser("mail@mail.com","Pseudo","password");
        u2 = dao.createUser("mail2@mail.com","Pseudo2","password2");

        assertThat(u).isNotNull();
        assertThat(u2).isNotNull();

        assertThat(u2.getUserID()).isNotEqualTo(u.getUserID());

        assertThat(u.getUsername()).isEqualTo("Pseudo");
        assertThat(u.getMail()).isEqualTo("mail@mail.com");
        assertThat(u.getPwd()).isEqualTo("password");

        assertThat(u2.getUsername()).isEqualTo("Pseudo2");
        assertThat(u2.getMail()).isEqualTo("mail2@mail.com");
        assertThat(u2.getPwd()).isEqualTo("password2");
    }

    /** Test : Update an user
     *  Not null : check
     *  The user updates still has the same ID : check
     *  All the columns given are OK : check
     */
    @Test
    public void shouldUpdateUser() throws Exception {
        User u = new User();
        User u2 = dao.getUserByUsername("zoidberg");

        u.setUserID(1);
        u.setUsername("Pseudo");
        u.setMail("mail@mail.com");
        u.setPwd("password");

         u = dao.updateUser(u);

        assertThat(u).isNotNull();
        assertThat(u2).isNotNull();

        assertThat(u.getUserID()).isEqualTo(u2.getUserID());
        assertThat(u2.getUsername()).isNotEqualTo("zoidgerg");
        assertThat(u2.getUsername()).isEqualTo("Pseudo");
        assertThat(u2.getMail()).isNotEqualTo("zoidberg@ewide.com");
        assertThat(u2.getMail()).isEqualTo("mail@mail.com");
        assertThat(u2.getPwd()).isNotEqualTo("$2a$10$WzSOqXRoTXA7QkIdhP3hl.2R85JSgG2nGYltG3HC0DftrefgFH5o6");
        assertThat(u2.getPwd()).isEqualTo("password");
    }

    /** Test : Get all user of a project by its ID
     *  Not null : check
     *  Return the number of users expected : check
     */
    @Test
    public void shouldGetAllUsersByProjectID() throws Exception {
        List<User> list = dao.getAllUsersByProjectID(1);

        assertThat(list).isNotNull();
        assertThat(list).hasSize(2);
    }

    /** Test : Get users that start with a certain string without consider one user specified.
     *  Not null : check
     *  Number of users expected : check
     */
    @Test
    public void shouldGetUsersStartedwith() throws Exception {
        User u = dao.getUserByEmail("zoidberg@ewide.com");
        List<User> list = dao.getUsersStartedwith("zoid", u);
        assertThat(list).isNotNull();
        assertThat(list).hasSize(1);

        u = dao.getUserByEmail("fry@ewide.com");
        list = dao.getUsersStartedwith("zoid", u);
        assertThat(list).isNotNull();
        assertThat(list).hasSize(2);
    }

}