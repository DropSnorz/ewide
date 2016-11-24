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
	 * return a list of all the tasks on a project
	 * @param  projectId the project we work on
	 * @return result  task list with all the tasks or an empty one if no task.
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

	/**
	 * Give the closed (inactive) tasks or the opened (active) tasks by project
	 * @param projectId  the project we work on
	 * @param active  Must take the value "active" or "inactive"
	 * @return the list of opened/closed tasks
	 */
	public List<Task> getTasksByProjectIdAndState(int projectId, String active) {

		try {
			TypedQuery<Task> query;
			if(active != null && active.equalsIgnoreCase("active")){
				query = em.createQuery("SELECT t FROM Task t JOIN FETCH  t.user WHERE t.project.projectID = :projectId "
						+ "AND NOT t.state = 'Closed' ORDER BY t.date desc", Task.class);
			}
			else if(active != null && active.equalsIgnoreCase("inactive")){
				query = em.createQuery("SELECT t FROM Task t JOIN FETCH  t.user WHERE t.project.projectID = :projectId "
						+ "AND t.state = 'Closed' ORDER BY t.date desc", Task.class);
			}
			else{
				query = em.createQuery("SELECT t FROM Task t JOIN FETCH  t.user WHERE t.project.projectID = :projectId ORDER BY t.date desc", Task.class);

			}
			query.setParameter("projectId", projectId);
			List<Task> result = query.getResultList();
			return result;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new ArrayList<Task>();
		}
	}

	/**
	 * Give the closed (inactive) tasks or the opened (active) tasks by project and user
	 * @param projectId  the project we work on
	 * @param userId the user which tasks we want to know about
	 * @param active  Must take the value "active" or "inactive"
	 * @return the list of opened/closed tasks for this userId
	 */
	public List<Task> getTasksByProjectIdAndOwnerId(int projectId, int userId, String active) {


		try {
			TypedQuery<Task> query;
			if(active != null && active.equalsIgnoreCase("active")){
				query = em.createQuery("SELECT t FROM Task t WHERE t.project.projectID = :projectId "
						+ "AND t.user.userID = :userId AND NOT t.state = 'Closed' ORDER BY t.date desc", Task.class);
			}
			else if(active != null && active.equalsIgnoreCase("inactive")){
				query = em.createQuery("SELECT t FROM Task t WHERE t.project.projectID = :projectId "
						+ "AND t.user.userID = :userId AND t.state= 'Closed' ORDER BY t.date desc", Task.class);
			}
			else{
				query = em.createQuery("SELECT t FROM Task t WHERE t.project.projectID = :projectId "
						+ "AND t.user.userID = :userId ORDER BY t.date desc", Task.class);
				
			}
			query.setParameter("projectId", projectId);
			query.setParameter("userId", userId);
			List<Task> result = query.getResultList();
			return result;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new ArrayList<Task>();
		}

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
	
	public Task createOrUpdate(Task task){
		
		em.merge(task);
		return task;
	}

}
