package fr.univ_lyon1.etu.ewide.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import fr.univ_lyon1.etu.ewide.model_temp.Project;
import fr.univ_lyon1.etu.ewide.model_temp.Task;

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
	 * @return liste des tasks ou liste vide s'il n'existe pas
	 */
	public List<Task> getTasksByProjectId(int projectId) {


		try {
			TypedQuery<Task> query = em.createQuery("SELECT t FROM Task t JOIN FETCH  t.user WHERE t.project.projectID = :projectId", Task.class);
			query.setParameter("projectId", projectId);
			List<Task> result = query.getResultList();
			return result;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new ArrayList<Task>();
		}



	}

/**
 * Cr��e une nouvelle task
 * @param type
 * @param state
 * @param text
 * @param project
 * @param date
 * @return la task cr��
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
 * mis � jour une task
 * @param taskID
 * @param type
 * @param state
 * @param text
 * @param referencedTask
 * @param date
 * @return la task mis � jour
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
