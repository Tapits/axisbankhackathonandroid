package com.reva.connectionmanager;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ManageConnection {
	
	protected EntityManagerFactory emf = null;
	private static String persitenceName="fingpay";
	
	ManageConnection(){
		emf =  Persistence.createEntityManagerFactory(persitenceName);
	}
	
	public EntityManagerFactory getEntityManagerFactory(){
		return emf;
	}
}


