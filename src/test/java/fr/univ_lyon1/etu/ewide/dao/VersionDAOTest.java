package fr.univ_lyon1.etu.ewide.dao;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import fr.univ_lyon1.etu.ewide.model.Project;
import fr.univ_lyon1.etu.ewide.model.User;
import fr.univ_lyon1.etu.ewide.model.Version;
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
 * Created by kylaste on 22/11/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/test/test-context.xml"})
@Transactional
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class })
@DatabaseSetup(value="/dataset_test.xml")
public class VersionDAOTest {

    @Autowired
    VersionDAO dao;

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
    public void shouldGetVersionByGitID() throws Exception {
        Version v = dao.getVersionByGitID("1");
        assertThat(v).isNotNull();
        assertThat(v.getVersionID()).isEqualTo(0);
    }
/*
    @Test
    public void shouldGetVersionByProjectIDAndVersionID() throws Exception {
        Version v = dao.getVersionByProjectIDAndVersionID(2,10);
        assertThat(v).isNotNull();
        assertThat(v.getUser().getUserID()).isEqualTo(2);
    }*/

    @Test
    public void shouldGetProjectLatestVersionNumber() throws Exception {
        Project p = daoP.getProjectById(2);
        int lastVersion = dao.getProjectLatestVersionNumber(p);
        assertThat(lastVersion).isNotNull();
        assertThat(lastVersion).isEqualTo(10);
    }

    @Test
    public void shouldCreate() throws Exception {
        User u = daoU.getUserByUsername("fry");
        Project p = daoP.getProjectById(1);
        Version v = dao.create("IdCommit", p,u);
        assertThat(v).isNotNull();
        assertThat(v.getVersion()).isEqualTo("IdCommit");
        assertThat(v.getUser()).isEqualTo(u);
        assertThat(v.getProject()).isEqualTo(p);
    }

    @Test
    public void shouldCreate1() throws Exception {
        User u = daoU.getUserByUsername("fry");
        Project p = daoP.getProjectById(1);
        Version v = dao.create("IdCommit", p,u,0);
        assertThat(v).isNotNull();
        assertThat(v.getVersion()).isEqualTo("IdCommit");
        assertThat(v.getUser()).isEqualTo(u);
        assertThat(v.getProject()).isEqualTo(p);
    }

}