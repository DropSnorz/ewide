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

    /** Test : Get a version by its git ID.
     *  Not null : check
     *  Columns expected : check
     */
    @Test
    public void shouldGetVersionByGitID() throws Exception {
        User u = daoU.getUserByUsername("zoidberg");
        Project p = daoP.getProjectById(1);
        assertThat(u).isNotNull();
        assertThat(p).isNotNull();

        Version v = dao.getVersionByGitID("1");
        assertThat(v).isNotNull();
        assertThat(v.getVersionID()).isEqualTo(0);
        assertThat(v.getUser()).isEqualTo(u);
        assertThat(v.getProject()).isEqualTo(p);
    }


    /** Test : Get the latest version of a project.
     *  Not null : check
     *  version expected : check
     */
    @Test
    public void shouldGetProjectLatestVersionNumber() throws Exception {
        Project p = daoP.getProjectById(2);
        int lastVersion = dao.getProjectLatestVersionNumber(p);
        assertThat(lastVersion).isNotNull();
        assertThat(lastVersion).isEqualTo(10);
    }

    /** Test : Create a version auto-incremented with project et user model.
     *  Not null : check
     *  Columns expected : check
     */
    @Test
    public void shouldCreate() throws Exception {
        User u = daoU.getUserByUsername("fry");
        Project p = daoP.getProjectById(1);
        assertThat(u).isNotNull();
        assertThat(p).isNotNull();

        Version v = dao.create("IdCommit", p,u);
        assertThat(v).isNotNull();
        assertThat(v.getVersion()).isEqualTo("IdCommit");
        assertThat(v.getUser()).isEqualTo(u);
        assertThat(v.getProject()).isEqualTo(p);
    }

    /** Test : Create a version 0 with project et user model.
     *  Not null : check
     *  Columns expected : check
     */
    @Test
    public void shouldCreate1() throws Exception {
        User u = daoU.getUserByUsername("fry");
        Project p = daoP.getProjectById(1);
        assertThat(u).isNotNull();
        assertThat(p).isNotNull();

        Version v = dao.create("IdCommit", p,u,0);
        assertThat(v).isNotNull();
        assertThat(v.getVersionID()).isEqualTo(0);
        assertThat(v.getVersion()).isEqualTo("IdCommit");
        assertThat(v.getUser()).isEqualTo(u);
        assertThat(v.getProject()).isEqualTo(p);
    }

    /**
     * Test : Get all the versions of the desired project
     * Not null : check
     * size expected : check
     */
    @Test
    public void shouldGetAllVersionsByProject() throws Exception {
        Project p = daoP.getProjectById(1);
        List<Version> list = dao.getAllVersionsByProject(p);
        assertThat(list).isNotNull();
        assertThat(list).hasSize(3);
    }

}