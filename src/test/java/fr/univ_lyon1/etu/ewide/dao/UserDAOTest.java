package fr.univ_lyon1.etu.ewide.dao;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import fr.univ_lyon1.etu.ewide.model.User;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
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
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;


import static org.assertj.core.api.Assertions.assertThat;

//import static org.junit.Assert.*;

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
    public void shouldCreateOrUpdate() throws Exception {
   /*     User u = new User();
        User u2;

        u.setUsername("Pseudo");
        u.setMail("mail@mail.com");
        u.setPwd("password");
        u2 = dao.createOrUpdate("mail@mail.com","Pseudo","password");

        assertEquals(u.getUsername(), u2.getUsername());
        assertEquals(u.getMail(), u2.getMail());
        assertEquals(u.getPwd(), u2.getPwd());*/
    }

}