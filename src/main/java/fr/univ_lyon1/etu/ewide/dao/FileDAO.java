package fr.univ_lyon1.etu.ewide.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.univ_lyon1.etu.ewide.model.File;
import fr.univ_lyon1.etu.ewide.model.Project;
import fr.univ_lyon1.etu.ewide.model.Role;

/**
 * Created by Firas on 18/10/2016.
 */

@Repository
public class FileDAO {
	
	@Autowired
	protected EntityManagerFactory entityManagerFactory;
	@PersistenceContext
	protected EntityManager em;
	
	
	/**
     * Renvoie une list de tous les Fichiers dans un projet
     * @return liste des fichiers ou null s'il n'existe pas
     */
	public List<File> getFilesByProject(Project project) {
      try {
    	  TypedQuery<File> query =
		  em.createQuery("SELECT f FROM File f"
		  		+ "  WHERE f.project=:project",File.class);

		  query.setParameter("project", project);
		
		  return query.getResultList();
      } catch (Exception e) {
		return null;
      }
	      
	}
	
	/**
     * Cr��e un nouvel fichier
     * @param name
     * @param path
     * @param type
     * @param project
     * @return le fichier cr��
     */
    public File createFile(String name, String path, String type, Project project) {
      File f = new File();
      f.setName(name);
      f.setPath(path);
      f.setType(type);
      f.setProject(project);
      em.merge(f);
      return f;
    }
    
    /**
     * mis � jour un nouvel fichier
     * @param fileID
     * @param name
     * @param path
     * @param type
     * @param project
     * @return le fichier mis � jour
     */
    public File UpdateFile(int fileID, String name, String path, String type, Project project) {
      File f = new File();
      f.setFileID(fileID);
      f.setName(name);
      f.setPath(path);
      f.setType(type);
      f.setProject(project);
      em.merge(f);
      return f;
    }
	
	
	
}
