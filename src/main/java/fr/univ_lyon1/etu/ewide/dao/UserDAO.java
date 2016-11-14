package fr.univ_lyon1.etu.ewide.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import fr.univ_lyon1.etu.ewide.model.Project;
import fr.univ_lyon1.etu.ewide.model.Role;
import fr.univ_lyon1.etu.ewide.model.User;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Firas on 17/10/2016.
 */

@Repository
public class UserDAO {
	
	
	@Autowired
	protected EntityManagerFactory entityManagerFactory;
	
	
	@PersistenceContext
	protected EntityManager em;
	
	/**
     * gets a User by his email
     * @param email (String)
     * @return (User) 
     */
	public User getUserByEmail(String email) {
		
		
        try {
			TypedQuery<User> query = em.createQuery("SELECT user FROM User user "
					+ "WHERE user.mail = :email_value", User.class)
					.setParameter("email_value", email);
			return query.getSingleResult();
		} catch (Exception e) {

			return null;
		}
    
    }
	/**
	 * gets a User by username
	 * @param username (String)
	 * @return (User)
	 */
	public User getUserByUsername(String username){
		 try {
				TypedQuery<User> query = em.createNamedQuery("User.getByUsername", User.class);
				query.setParameter("username", username);
				return query.getSingleResult();
			} catch (Exception e) {

				return null;
			}
	}
			
	/**
     * Creates or updates a User
     * @param email (String)
     * @param username (String)
     * @param password (String)
     * @return (User) created or updated User
     */
    public User createOrUpdate(String email, String username, String password) {
      User u = new User();
      u.setUsername(username);
      u.setMail(email);
      u.setPwd(password);
      em.merge(u);
      return u;
    }

    /**
     * gives the Users of a project 
     * @param projectID (int)
     * @return (List<User>)
     */
    public List<User> getAllUsersByProjectID(int projectID){
    	TypedQuery<User> query =
    		      em.createNamedQuery("User.getUsersByProjectID", User.class)
    		      .setParameter("projectID", projectID);
    		      List<User> results = query.getResultList();
    		      if(results.isEmpty()){
    		          return null;
    		      }else{
    		          return results;
    		      }
    			}

	/**
	 * Creates or updates a User in the database
	 * @param (User) 
	 * @return (User) created or updated User
	 */
	public User createOrUpdate(User user) {
		if(em.find(User.class, user.getUsername())!= null)
		{
			em.getTransaction().begin();
			em.merge(user); // mise a jour
			em.getTransaction().commit();
		}
		else
		{
			em.getTransaction().begin();
			em.persist(user);  // persiste
			em.getTransaction().commit();
		}
		return user;
	}
	
	/**
	 * return the list of the Users that start with the string name
	 * @param name : searched name 
	 * @param user : actual connected user
	 * @return
	 */
	public List<User> getUsersStartedwith(String name,User user){
	      try {
				TypedQuery<User> query =
				  em.createQuery("SELECT u FROM User u"
				  		+ " WHERE username LIKE :name and username NOT LIKE :user",User.class);
				  query.setParameter("name","%"+ name+"%");
				  query.setParameter("user",user.getUsername());
				  return (List<User>)query.getResultList();
			} catch (Exception e) {
				
				return null;
			}
	}
	
}
