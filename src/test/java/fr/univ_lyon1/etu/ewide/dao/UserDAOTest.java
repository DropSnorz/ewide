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

    private String emailTest = "zoidberg@ewide.com";
    private String stringTest = "$2a$10$WzSOqXRoTXA7QkIdhP3hl.2R85JSgG2nGYltG3HC0DftrefgFH5o6";
    private String pseudoTest = "zoidberg";
    private String changePseudoTest = "Pseudo";
    private String changeStringTest = "password";
    private String changeEmailTest = "mail@mail.com";

    @Before
    public void setUp() {
        // Do nothing because there's no set up operation to do
    }

    @After
    public void tearDown() {
        // Do nothing because there's no tear down operation to do
    }

    /**  Test : Get the User with his email
     *  Not null : check
     *  All the columns are OK
     */
    @Test
    public void shouldGetUserByEmail() {
        User searchResults = dao.getUserByEmail(emailTest);
        assertThat(searchResults).isNotNull();
        assertThat(searchResults.getUserID()).isEqualTo(1);
        assertThat(searchResults.getUsername()).isEqualTo(pseudoTest);
        assertThat(searchResults.getMail()).isEqualTo(emailTest);
        assertThat(searchResults.getPwd()).isEqualTo(stringTest);
    }

    /** Test : Get the User with his username
     *  Not null : check
     *  All the columns are OK : check
     */
    @Test
    public void shouldGetUserByUsername() {
        User searchResults = dao.getUserByUsername(pseudoTest);
        assertThat(searchResults).isNotNull();
        assertThat(searchResults.getUserID()).isEqualTo(1);
        assertThat(searchResults.getUsername()).isEqualTo(pseudoTest);
        assertThat(searchResults.getMail()).isEqualTo(emailTest);
        assertThat(searchResults.getPwd()).isEqualTo(stringTest);
    }

    /** Test : Create an user with Email, username and password
     *  Not null : check
     *  2 users created doesn't have the same ID : check
     *  All the columns given are OK : check
     */
    @Test
    public void shouldCreateUser() {
        User u = dao.createUser(changeEmailTest,changePseudoTest,changeStringTest);
        User u2 = dao.createUser("mail2@mail.com","Pseudo2","password2");

        assertThat(u).isNotNull();
        assertThat(u2).isNotNull();

        assertThat(u2.getUserID()).isNotEqualTo(u.getUserID());

        assertThat(u.getUsername()).isEqualTo(changePseudoTest);
        assertThat(u.getMail()).isEqualTo(changeEmailTest);
        assertThat(u.getPwd()).isEqualTo(changeStringTest);

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
    public void shouldUpdateUser() {
        User u = new User();
        User u2 = dao.getUserByUsername(pseudoTest);

        u.setUserID(1);
        u.setUsername(changePseudoTest);
        u.setMail(changeEmailTest);
        u.setPwd(changeStringTest);

         u = dao.updateUser(u);

        assertThat(u).isNotNull();
        assertThat(u2).isNotNull();

        assertThat(u.getUserID()).isEqualTo(u2.getUserID());
        assertThat(u2.getUsername()).isNotEqualTo(pseudoTest);
        assertThat(u2.getUsername()).isEqualTo(changePseudoTest);
        assertThat(u2.getMail()).isNotEqualTo(emailTest);
        assertThat(u2.getMail()).isEqualTo(changeEmailTest);
        assertThat(u2.getPwd()).isNotEqualTo(stringTest);
        assertThat(u2.getPwd()).isEqualTo(changeStringTest);
    }

    /** Test : Get all user of a project by its ID
     *  Not null : check
     *  Return the number of users expected : check
     */
    @Test
    public void shouldGetAllUsersByProjectID() {
        List<User> list = dao.getAllUsersByProjectID(1);

        assertThat(list).isNotNull();
        assertThat(list).hasSize(2);
    }

    /** Test : Get users that start with a certain string without consider one user specified.
     *  Not null : check
     *  Number of users expected : check
     */
    @Test
    public void shouldGetUsersStartedwith() {
        User u = dao.getUserByEmail(emailTest);
        List<User> list = dao.getUsersStartedwith("zoid", u);
        assertThat(list).isNotNull();
        assertThat(list).hasSize(1);

        u = dao.getUserByEmail("fry@ewide.com");
        list = dao.getUsersStartedwith("zoid", u);
        assertThat(list).isNotNull();
        assertThat(list).hasSize(2);
    }

}