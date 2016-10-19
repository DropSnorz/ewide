package fr.univ_lyon1.etu.ewide.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.univ_lyon1.etu.ewide.Model.User;

/**
 * Created by Firas ODEH on 17/10/2016.
 */

@Repository
public class UserDAO {
	
	@Autowired
	protected EntityManagerFactory entityManagerFactory;
	
	protected EntityManager em;
	
	/**
     * Renvoie l'utilisateur correspondant à cet email
     * @param email l'email de l'utilisateur
     * @return l'utilisateur ou null s'il n'existe pas
     */
	public User getUserByEmail(String email) {
        TypedQuery<User> query =
        em.createNamedQuery("User.getUserByEmail", User.class)
                .setParameter("email",email);
        List<User> results = query.getResultList();
        if(results.isEmpty()){
            return null;
        }else{
            return results.get(0);
        }
    }
	
	/**
     * Créée un nouvel utilisateur ou met à jour son pseudo
     * @param email
     * @param pseudo
     * @return l'utilisateur créé ou lis à jour
     */
    public User createOrUpdate(String email, String pseudo, String password) {
      User u = new User();
      u.setPseudo(pseudo);
      u.setMail(email);
      u.setPwd(password);
      em.merge(u);
      return u;
    }
    
    
	
}
