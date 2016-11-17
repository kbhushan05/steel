package com.demo.steel.dao;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class GenericDao <T, K extends Serializable> {
	
	private static final Logger logger = LoggerFactory.getLogger(GenericDao.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	public T get(K key){
		logger.debug("fetching entity "+ getClazz()+" by primary key "+ key);
		Session session = getSessionFactory().getCurrentSession();
		T t = (T)session.get(getClazz(),key);
		return t;
	}
	
	@SuppressWarnings("unchecked")
	public K save(T t){
		logger.debug("Saving entity "+ getClazz());
		Session session = getSessionFactory().getCurrentSession();
		K k = (K)session.save(t);
		return k;
	}

	public T update(T t){
		logger.debug("updating entity"+ getClazz()+" "+t);
		Session session = getSessionFactory().getCurrentSession();
		session.update(t);
		return t;
	}
	
	public boolean isExists(K key){
		logger.debug("verifying "+ getClazz()+" key "+ key);
		return get(key)== null ? false : true;
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public abstract Class<T> getClazz();
	
	@SuppressWarnings("unchecked")
	protected T getEqualTo(String[] columnNames, Object[] values){
		logger.debug("fetching for entity "+ getClazz()+" entry for criteria "+ Arrays.toString(columnNames)+" values "+Arrays.toString(values));
		Session session = getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(getClazz());
		for(int i=0; i < columnNames.length; i++){
			criteria.add(Restrictions.eq(columnNames[i], values[i]));	
		}
		T t = (T)criteria.uniqueResult();
		return t;
	}
	
	@SuppressWarnings("unchecked")
	protected List<T> getAllEqualTo(String[] columnNames, Object[] values){
		logger.debug("fetching for entity "+ getClazz()+" all entries for criteria "+ Arrays.toString(columnNames)+" values "+Arrays.toString(values));
		Session session = getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(getClazz());
		for(int i=0; i < columnNames.length; i++){
			criteria.add(Restrictions.eq(columnNames[i], values[i]));	
		}
		List<T> list = (List<T>)criteria.list();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	protected List<T> getAllEqualTo(String[] columnNames, Object[] values,String...returnedColumns){
		logger.debug("fetching for entity "+ getClazz()+" all entries for criteria "+ Arrays.toString(columnNames)+" values "+Arrays.toString(values));
		ProjectionList projectionList = Projections.projectionList();
		
		for(String col : returnedColumns){
			projectionList.add(Projections.property(col),col);
		}
		
		Session session = getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(getClazz());
		for(int i=0; i < columnNames.length; i++){
			criteria.add(Restrictions.eq(columnNames[i], values[i]));	
		}
		criteria.setProjection(projectionList)
		.setResultTransformer(Transformers.aliasToBean(getClazz()));
		
		List<T> list = (List<T>)criteria.list();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getAll(){
		logger.debug("fetching all entries for entity "+ getClazz());
		Session session = getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(getClazz());
		List<T> list = (List<T>)criteria.list();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	protected List<T> getMinimalPresentation(int firstResult, int fetchSize, String...columns){
		logger.debug("fetching proxy for entity "+ getClazz()+ "for values "+Arrays.toString(columns));
		ProjectionList projectionList = Projections.projectionList();
		for(String col : columns){
			projectionList.add(Projections.property(col),col);
		}
		
		Session session = getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(getClazz());
		criteria.setProjection(projectionList)
		.setResultTransformer(Transformers.aliasToBean(getClazz()));
		if(firstResult >=0 && fetchSize > 0){
			criteria.setFirstResult(firstResult)
			.setFetchSize(fetchSize);
		}
		List<T> list = (List<T>)criteria.list();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public T merge(T t){
		logger.debug("reattaching entity "+ getClazz());
		Session session = getSessionFactory().getCurrentSession();
		return (T)session.merge(t);
	}
	
	@SuppressWarnings("unchecked")
	public T load(K key){
		logger.debug("loading entity "+ getClazz()+" for key "+ key);
		Session session = getSessionFactory().getCurrentSession();
		return (T)session.load(getClazz(), key);
	}
	
	public int getTotalCount(){
		Session session = getSessionFactory().getCurrentSession();
		ProjectionList list = Projections.projectionList();
		Criteria criteria = session.createCriteria(getClazz());
		criteria.setProjection(list.add(Projections.rowCount()));
		return (int)criteria.uniqueResult();
	}
}
