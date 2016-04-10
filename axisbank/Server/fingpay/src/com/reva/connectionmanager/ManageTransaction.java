package com.reva.connectionmanager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class ManageTransaction extends ManageConnection{

	/**
	 * 
	 */
	public ManageTransaction() {
		
		super();
		this.entityManager = emf.createEntityManager();
		this.transaction = this.entityManager.getTransaction();
	}

	/**
	 * @param entityManager
	 * @param transaction
	 */
	public ManageTransaction(EntityManager entityManager,EntityTransaction transaction) {
		super();
		this.entityManager = entityManager;
		this.transaction = transaction;
	}

	private EntityManager entityManager;
	private EntityTransaction transaction;

	/**
	 * @return the entityManager
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * @param entityManager the entityManager to set
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * @return the transaction
	 */
	public EntityTransaction getTransaction() {
		return transaction;
	}

	/**
	 * @param transaction the transaction to set
	 */
	public void setTransaction(EntityTransaction transaction) {
		this.transaction = transaction;
	}

	/**
	 * @param primaryKeyValue value to be searched for
	 * @param Class<T> object to be type casted in
	 */
	public <T> T find(Object primaryKeyValue,Class<T> type){
		return this.find(type, primaryKeyValue);
	}
	/**
	 * @param Class<T> object to be type casted in
	 * @param primaryKeyValue value to be searched for
	 */
	public <T> T find(Class<T> type,Object primaryKeyValue){
		return this.getEntityManager().find(type, primaryKeyValue);
	}
	
	
	public Query createNativeQuery(String nativeQuery){
		return this.getEntityManager().createNativeQuery(nativeQuery);
	}
	
	public Query createQuery(String query){
		return this.getEntityManager().createQuery(query);
	}
	
	
	public <T> TypedQuery<T>  createQuery(Class<T> type,String jpaQuery){
		return this.createQuery(jpaQuery, type);
	}
	public <T> TypedQuery<T>  createQuery(String jpaQuery,Class<T> type){
		return this.getEntityManager().createQuery(jpaQuery, type);
	}
	
	public void close(){
		//calling my closeEntityManager()
		this.closeEntityManager();
	}
	private void closeEntityManager(){
		
		EntityManager 			tempEntityManager = this.getEntityManager();
		EntityManagerFactory 	tempEntityManagerFactory = this.getEntityManagerFactory();
		
		if(tempEntityManager!=null && tempEntityManager.isOpen()){
			this.getEntityManager().close();
		}
		if(tempEntityManagerFactory!=null && tempEntityManagerFactory.isOpen()){
			this.getEntityManagerFactory().close();
		}
	}
	
	public void flush(){
		this.getEntityManager().flush();
	}
	
	public void begin(){
		if(!this.getTransaction().isActive())
			this.getTransaction().begin();
	}
	
	public void commit(){
		this.begin();  //I think being should be there otherwise we are getting exceptions
		this.flush();
		this.getTransaction().commit();
	}
	
	public void persist(Object objToPersist){
		this.getEntityManager().persist(objToPersist);
	}
	
	public void remove(Object objToRemove){
		this.getEntityManager().remove(objToRemove);
	}
	
	public int executeUpdate(String updateQuery){
		return this.getEntityManager().createQuery(updateQuery).executeUpdate();
	}
	
	public void saveObject(Object objectToSave){
		this.begin();
			this.getEntityManager().persist(objectToSave);
		this.commit();
	}
	
	public void updateObject(Object objectToSave){
		this.begin();
			this.getEntityManager().merge(objectToSave);
		this.commit();
	}
	
	public void saveObjectAndClose(Object objectToSave){
		this.begin();
			this.getEntityManager().persist(objectToSave);
		this.commit();

		this.close();
	}
}
