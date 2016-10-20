package fr.univ_lyon1.etu.ewide.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.List;

import fr.univ_lyon1.etu.ewide.Model.Project;
import fr.univ_lyon1.etu.ewide.Model.Task;
import org.springframework.stereotype.Repository;

/**
 * Created by Firas on 18/10/2016.
 */

@Repository
public class TaskDAO {

	@Autowired
	protected EntityManagerFactory entityManagerFactory;
	@PersistenceContext
	protected EntityManager em;
	
	
	/**
     * Renvoie une list de tous les tasks d'un projet
     * @return liste des tasks ou null s'il n'existe pas
     */
	public List<Task> getTasksByProject(String project) {
      TypedQuery<Task> query =
      em.createNamedQuery("Task.getTasksByProject", Task.class)
      				.setParameter("project",project);
      List<Task> results = query.getResultList();
      if(results.isEmpty()){
          return null;
      }else{
          return results;
      }
	}
	
	/**
     * Créée une nouvelle task
     * @param type
     * @param state
     * @param text
     * @param project
     * @param date
     * @return la task créé
     */
    public Task create(String type, String state, String text, Project project, Date date) {
      Task t = new Task();
      t.setType(type);
      t.setState(state);
      t.setText(text);
      t.setProject(project);
      t.setDate(date);
      em.merge(t);
      return t;
    }
    
    /**
     * mis à jour une task
     * @param taskID
     * @param type
     * @param state
     * @param text
     * @param referencedTask
     * @param date
     * @return la task mis à jour
     */
    public Task Update(int taskID, String type, String state, String text, Project project, Date date) {
      Task t = new Task();
      t.setTaskID(taskID);
      t.setType(type);
      t.setState(state);
      t.setText(text);
      t.setProject(project);
      t.setDate(date);
      em.merge(t);
      return t;
    }
    
}
