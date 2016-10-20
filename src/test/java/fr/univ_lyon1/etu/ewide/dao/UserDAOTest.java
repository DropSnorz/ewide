package fr.univ_lyon1.etu.ewide.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by Maud on 18/10/2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/test/test-context.xml")
public class UserDAOTest {
    @Before
    public void setUp() throws Exception {
        UserDAO user;
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void shouldGetUserByEmail() throws Exception {

    }

    @Test
    public void shouldCreateOrUpdate() throws Exception {
        assertEquals("failure - strings are not equal", "test", "test");
    }

}