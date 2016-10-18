package fr.univ_lyon1.etu.ewide.dao;

import javax.persistence.EntityManager;

/**
 * Created by Firas ODEH on 17/10/2016.
 */

public class RoleDAO {
	
	protected EntityManager em;
	
	public RoleDAO(EntityManager em) {
        this.em = em;
    }
	
}
