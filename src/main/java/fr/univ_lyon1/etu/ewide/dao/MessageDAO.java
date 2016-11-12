package fr.univ_lyon1.etu.ewide.dao;

import java.sql.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.univ_lyon1.etu.ewide.model.Message;
import fr.univ_lyon1.etu.ewide.model.Project;
import fr.univ_lyon1.etu.ewide.model.User;

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
     * to display the chat, we need all the messages
     * @return list of the messages of the project
     */
	public List<Message> getMessagesByProject(int projectID, int limit) {
      TypedQuery<Message> query =
      em.createNamedQuery("Message.getMessagesByProject", Message.class)
      				.setParameter("projectID",projectID)
      				.setMaxResults(limit);
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
