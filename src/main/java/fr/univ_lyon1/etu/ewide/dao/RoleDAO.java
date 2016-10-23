package fr.univ_lyon1.etu.ewide.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import fr.univ_lyon1.etu.ewide.Model.Project;
import fr.univ_lyon1.etu.ewide.Model.Role;
import fr.univ_lyon1.etu.ewide.Model.User;

/**
 * Created by Firas ODEH on 17/10/2016.
 */
@Repository
public class RoleDAO {
	@PersistenceContext
	protected EntityManager em;
	
	public List<Project> getProjectIDByUser(User user) {
			
	      TypedQuery<Project> query =
	      em.createNamedQuery("Role.getProjectIDByUser",Project.class);
	      	
	      List<Project> results = query.setParameter("user", user).getResultList();

	      if(results.isEmpty()){
	          return null;
	      }else{
	          return results;
	      }
	  }
	
	public Role getRoleByUserIdAndProjectId(int userId, int projectId) {
		
	      try {
			TypedQuery<Role> query =
			  em.createQuery("SELECT r FROM Role r"
			  		+ " WHERE r.user.userID=:userId AND r.project.projectID=:projectId",Role.class);
			  
			  query.setParameter("userId", userId);
			  query.setParameter("projectId", projectId);
			  	
			  Role role = query.getSingleResult();
			  return role;
		} catch (Exception e) {
			
			return null;
		}
	      
	     
	  }
	
	// Permet de créer des rôles en fonction du projet et de l'utilisateur
	@Transactional(propagation = Propagation.REQUIRED)
    public void createRole(User user, Project project) {
		
		Role role = new Role();
		role.setProject(project);
		
		role.setUser(user);
		role.setRole("Manager");

      em.persist(role);
    }
	
}
