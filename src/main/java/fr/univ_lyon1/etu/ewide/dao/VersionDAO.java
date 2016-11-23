package fr.univ_lyon1.etu.ewide.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.univ_lyon1.etu.ewide.model.Project;
import fr.univ_lyon1.etu.ewide.model.User;
import fr.univ_lyon1.etu.ewide.model.Version;

/**
 * Created by Steven on 14/11/2016.
 */


@Repository
public class VersionDAO {
	
	@Autowired
	protected EntityManagerFactory entityManagerFactory;
	
	@PersistenceContext
	public EntityManager em;
	
	
	/**
     * Search for a Version using its git (not user-friendly) ID 
     * @param commitID: git ID
     * @return the desired Version or null if non-existing.
     */
	public Version getVersionByGitID(String commitID) {
		try {
			TypedQuery<Version> query = em.createQuery("SELECT v FROM Version v join FETCH v.user WHERE v.version=:commitID", Version.class)
					.setParameter("commitID", commitID);
			return query.getSingleResult();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		/*try {
			return em.find(Version.class, commitID);
		} catch (Exception e) {
			return null;
		}*/
    
    }
	
	/**
     * Use this function whether you need to know which is the latest version of the project
     * @param projectID Searched project
     * @return The latest version number of the project
     */
	public int getProjectLatestVersionNumber(Project projectID) {
		//SELECT p FROM Project p WHERE p.projectID=:projectID
		//SELECT VERSIONID from VERSION where PROJECTID = 2 order by versionid desc  limit 1 
		
		try {
			TypedQuery<Version> query = em.createQuery("SELECT v FROM Version v WHERE v.project=:projectID ORDER BY v.versionID DESC", Version.class)
					.setParameter("projectID", projectID).setMaxResults(1);
			return query.getSingleResult().getVersionID();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return -1;
		}
	}
	
			
	/**
     * Creates a new version entry in the base
     * @param commitID Unique GIT ID
     * @param project Associated project
     * @return the newly created Version, null if the commitID already exists.
     */
    public Version create(String commitID, Project project, User u) {
    	if(em.find(Version.class, commitID) == null) {
	    	Version v = new Version();
    		int lastVersion = getProjectLatestVersionNumber(project);
			v.setVersion(commitID);
    		v.setUser(u);
			v.setProject(project);
			v.setVersionID(lastVersion+1);
			
			
			em.merge(v);  // save in base
			

			return v;
		
    	}
    	return null;
    }
    
    /**
     * Creates a new version entry in the base, but using a specific versionID 
     * /! CAN CREATE DUPLICATE VERSIONID ENTRIES, THE ATTRIBUTE NOT BEING UNIQUE IN OUR DATABASE
     * @param commitID Unique GIT ID
     * @param project Associated project
     * @param versionID Forced version ID
     * @return the newly created Version, null if the commitID already exists.
     */
    public Version create(String commitID, Project project,  User u, int versionID) {
    		Version v = new Version();
    		
    		v.setVersion(commitID);
    		v.setUser(u);
			v.setProject(project);
			v.setVersionID(versionID);
	    		
	    	try {
				em.merge(v);  // save in base
				return v;
	    	 } catch (Exception e) {
	    		 System.out.println(e.getMessage());
	    		 return null;
	    	 }

			
    }
    
    /**
     * Returns all the versions of the desired project
     * @param projectID (int)
     * @return (List<Version>) 
     */
    public List<Version> getAllVersionsByProject(Project project){
    	TypedQuery<Version> query =
    			em.createQuery("SELECT v FROM Version v join FETCH v.user "
    					+ "WHERE v.project = :projectID ORDER BY versionID DESC", Version.class)
    					.setParameter("projectID", project);
    		      List<Version> results = query.getResultList();
    		      if(results.isEmpty()){
    		          return null;
    		      }else{
    		          return results;
    		      }
    }

}
