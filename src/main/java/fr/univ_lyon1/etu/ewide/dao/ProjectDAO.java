package fr.univ_lyon1.etu.ewide.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import fr.univ_lyon1.etu.ewide.Model.Project;

/**
 * Created by Firas ODEH on 17/10/2016.
 */

public class ProjectDAO {
	protected EntityManager em;
	
	public ProjectDAO(EntityManager em) {
        this.em = em;
    }
	
	/**
     * Renvoie une list de tous les Projets dans le system
     * @return liste des projets ou null s'il n'existe pas
     */
	public List<Project> getAllProjects() {
      TypedQuery<Project> query =
      em.createNamedQuery("Project.getAllProjects", Project.class);
      List<Project> results = query.getResultList();
      if(results.isEmpty()){
          return null;
      }else{
          return results;
      }
  }
}
