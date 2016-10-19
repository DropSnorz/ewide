package fr.univ_lyon1.etu.ewide.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

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
	
	public List<Project> getProjectIDByUser() {
			User test = new User();
			test.setUserID(2);
	      TypedQuery<Project> query =
	      em.createNamedQuery("Role.getProjectIDByUser",Project.class);
	      	
	      List<Project> results = query.setParameter("user", test).getResultList();
	      System.out.println(results.getClass().toString());
	      if(results.isEmpty()){
	          return null;
	      }else{
	          return results;
	      }
	  }
	
}
