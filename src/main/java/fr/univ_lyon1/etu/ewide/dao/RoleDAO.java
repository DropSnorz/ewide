package fr.univ_lyon1.etu.ewide.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import fr.univ_lyon1.etu.ewide.Model.File;
import fr.univ_lyon1.etu.ewide.Model.Project;
import fr.univ_lyon1.etu.ewide.Model.Role;
import fr.univ_lyon1.etu.ewide.Model.User;

/**
 * Created by Firas ODEH on 17/10/2016.
 */
@Repository
public class RoleDAO {
	@Autowired
	protected EntityManagerFactory entityManagerFactory;
	
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
	
	
	/**
	 * donne l'id du role en fonction d'un utilisateur et d'un projet
	 * @param user (User)
	 * @param project (Project)
	 * @return 0 si le Role n'existe pas 
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public Role searchRoleByUserAndProject(User user,Project project){
		 TypedQuery<Role> query =
			      em.createNamedQuery("Role.getRoleIDByUserAndProject",Role.class);
		 query.setParameter("user",user)
		 	   .setParameter("project",project);
		 List<Role> results = query.getResultList();
		 if(results.isEmpty()){
	          return null;
	      }else{
	          return results.get(0);
	      }
	}
	/**
	 * cr�er ou modifie le role 
	 * @param user (User)
	 * @param project (Project)
	 * @param role_name (String)
	 */
	public void updateRole(User user, Project project, String role_name){
		Role role=this.searchRoleByUserAndProject(user, project);
		if(role!=null){
			role.setRole(role_name);
			em.merge(role);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void createRole(User user, Project project, String roleName) {
       Role role = new Role();
       role.setProject(project);          
       role.setUser(user);
       role.setRole(roleName);
       em.persist(role);

	}
}	
