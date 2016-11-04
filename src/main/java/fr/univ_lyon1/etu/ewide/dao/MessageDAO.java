package fr.univ_lyon1.etu.ewide.dao;

import java.sql.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.univ_lyon1.etu.ewide.model_temp.Message;
import fr.univ_lyon1.etu.ewide.model_temp.Project;
import fr.univ_lyon1.etu.ewide.model_temp.User;

/**
 * Created by Firas on 18/10/2016.
 */

@Repository
public class MessageDAO {
	
	@Autowired
	protected EntityManagerFactory entityManagerFactory;
	@PersistenceContext
	protected EntityManager em;
	
	/**
     * Renvoie une list de tous les Messages de chat dans un projet
     * @return liste des Messages ou null s'il n'existe pas
     */
	public List<Message> getMessagesByProject(String project) {
      TypedQuery<Message> query =
      em.createNamedQuery("File.getMessagesByProject", Message.class)
      				.setParameter("project",project);
      List<Message> results = query.getResultList();
      if(results.isEmpty()){
          return null;
      }else{
          return results;
      }
	}
	
	
	/**
     * Envoyer une message sur le group chat d'un projet
     * @param user
     * @param project
     * @param text
     * @param date
     * @return le message créé
     */
    public Message sendMessage(User user, Project project, String text, Date date ) {
      Message m = new Message();
      m.setUser(user);
      m.setProject(project);
      m.setText(text);
      m.setDate(date);
      em.persist(m);;
      return m;
    }
	
	
}
