package fr.univ_lyon1.etu.ewide.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

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
}
