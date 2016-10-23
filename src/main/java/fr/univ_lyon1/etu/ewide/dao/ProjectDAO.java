package fr.univ_lyon1.etu.ewide.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import fr.univ_lyon1.etu.ewide.Model.Project;

/**
 * Created by Firas ODEH on 17/10/2016.
 */

@Repository
public class ProjectDAO {
	
	@PersistenceContext
	protected EntityManager em;
	
	
	/**
     * Renvoie une list de tous les Projets dans le system
     * @return liste des projets ou null s'il n'existe pas
     */
	public List<Project> getAllProjects() {
		
      TypedQuery<Project> query =
      em.createNamedQuery("Project.getAll", Project.class);
      List<Project> results = query.getResultList();
      if(results.isEmpty()){
          return null;
      }else{
          return results;
      }
	}
	
	/**
     * Cr��e un nouvel projet
     * @param name
     * @param compiler
     * @return le projet cr��
     */
	@Transactional(propagation = Propagation.REQUIRED)
    public void createProject(Project project) {	
      em.persist(project);   
    }
	
	public Project getProjectById(String idProject){
		try {
			return em.find(Project.class, idProject);
		} catch (Exception e) {
			return null;
		}
	}
    
    /**
     * mis � jour un projet 
     * @param projectID
     * @param name
     * @param compiler
     * @return le projet mis � jour
     */
    public Project UpdateFile(int projectID, String name, String compiler) {
      Project p = new Project();
      p.setProjectID(projectID);
      p.setName(name);
      p.setCompiler(compiler);
      em.merge(p);
      return p;
    }
}
