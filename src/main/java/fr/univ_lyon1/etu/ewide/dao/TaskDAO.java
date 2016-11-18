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

import fr.univ_lyon1.etu.ewide.model.Project;
import fr.univ_lyon1.etu.ewide.model.Task;

/**
 * Created by Firas on 18/10/2016.
 */

@Repository
public class TaskDAO {


	@PersistenceContext
	protected EntityManager em;


	
	public Task getTaskById(int taskId){
		
		try {
			Task task = em.find(Task.class, taskId);
			return task;
		} catch (Exception e) {
			
			return null;
		}
	}
	/**
	 * Renvoie une list de tous les tasks d'un projet
	 * @return liste des tasks ou liste vide s'il n'existe pas
	 */
	public List<Task> getTasksByProjectId(int projectId) {


		try {
			TypedQuery<Task> query = em.createQuery("SELECT t FROM Task t JOIN FETCH  t.user WHERE t.project.projectID = :projectId ORDER BY t.date desc", Task.class);
			query.setParameter("projectId", projectId);
			List<Task> result = query.getResultList();
			return result;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new ArrayList<Task>();
		}
	}
	
	public List<Task> getTasksByProjectIdAndOwnerId(int projectId, int userId) {


		try {
			TypedQuery<Task> query = em.createQuery("SELECT t FROM Task t WHERE t.project.projectID = :projectId "
					+ "AND t.user.userID = :userId ORDER BY t.date desc", Task.class);
			query.setParameter("projectId", projectId);
			query.setParameter("userId", userId);
			List<Task> result = query.getResultList();
			return result;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new ArrayList<Task>();
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
	
	public Task createTask(Task task) {
		
		em.persist(task);
		return task;
	}
	
	public void deleteTask(Task task) {
		
		em.remove(task);
		
	}
	
	public void deleteTask(int taskId) {
		
		Task task = em.find(Task.class, taskId);
		em.remove(task);
		
	}
	
	public Task createOrpdate(Task task){
		
		em.merge(task);
		return task;
	}
	


	/**
	 * mise à jour une task
	 * @param taskID
	 * @param type
	 * @param state
	 * @param text
	 * @param referencedTask
	 * @param date
	 * @return la task mise à jour
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
