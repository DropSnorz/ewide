package fr.univ_lyon1.etu.ewide.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import fr.univ_lyon1.etu.ewide.model.User;

import javax.persistence.Persistence;

import static org.junit.Assert.*;

/**
 * Created by Maud on 18/10/2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/test/test-context.xml"})
@TransactionConfiguration(transactionManager="transactionManager")
@Transactional
public class UserDAOTest {

    @Autowired
    UserDAO dao;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void shouldGetUserByEmail() throws Exception {

    }

    @Test
    public void shouldCreateOrUpdate() throws Exception {

        User u = new User();
        User u2;

        u.setUsername("Pseudo");
        u.setMail("mail@mail.com");
        u.setPwd("password");
        u2 = dao.createOrUpdate("mail@mail.com","Pseudo","password");

        assertEquals(u.getUsername(), u2.getUsername());
        assertEquals(u.getMail(), u2.getMail());
        assertEquals(u.getPwd(), u2.getPwd());
    }

}