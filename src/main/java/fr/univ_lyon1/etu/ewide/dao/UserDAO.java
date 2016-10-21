package fr.univ_lyon1.etu.ewide.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;


import fr.univ_lyon1.etu.ewide.Model.Project;
import fr.univ_lyon1.etu.ewide.Model.Role;
import fr.univ_lyon1.etu.ewide.Model.User;

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
     * Renvoie l'utilisateur correspondant � cet email
     * @param email l'email de l'utilisateur
     * @return l'utilisateur ou null s'il n'existe pas
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
     * Cr��e un nouvel utilisateur ou met � jour son pseudo
     * @param email email de l'utilisateur
     * @param username pseudo de l'utilisateur
     * @param password mot de passe de l'utilisateur
     * @return l'utilisateur cr�� ou mis � jour
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
	 * Cr��e un nouvel utilisateur ou met � jour son pseudo
	 * @param user
	 * @return l'utilisateur cr�� ou mis � jour
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
    
    public List<Role> getAllUsersByProjectID(int projectID){
    	TypedQuery<Role> query =
    		      em.createNamedQuery("User.getUsersByProjectID", Role.class)
    		      .setParameter("projectID", projectID);;
    		      List<Role> results = query.getResultList();
    		      if(results.isEmpty()){
    		          return null;
    		      }else{
    		          return results;
    		      }
    			}



}
