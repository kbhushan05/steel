package com.demo.steel.security.dao;

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

public abstract class SecurityGeneric <T, K extends Serializable>{

	private static final Logger logger = LoggerFactory.getLogger(SecurityGeneric.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	public T get(K key){
		logger.debug("fetching entity "+ getClazz()+"from primary key.");
		Session session = getSessionFactory().getCurrentSession();
		T t = (T)session.get(getClazz(),key);
		return t;
	}
	
	@SuppressWarnings("unchecked")
	public K save(T t){
		logger.debug("saving entity "+ getClazz());
		Session session = getSessionFactory().getCurrentSession();
		K k = (K)session.save(t);
		return k;
	}

	public void update(T t){
		logger.debug("updating entity "+ getClazz());
		Session session = getSessionFactory().getCurrentSession();
		session.saveOrUpdate(t);
	}
	
	public boolean isExists(K key){
		logger.debug("verifying entity "+ getClazz());
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
		logger.debug("fetching entity "+ getClazz()+" for criteria " + Arrays.toString(columnNames)+ " values " + Arrays.toString(values));
		Session session = getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(getClazz());
		for(int i=0; i < columnNames.length; i++){
			criteria.add(Restrictions.eq(columnNames[i], values[i]));	
		}
		T t = (T)criteria.uniqueResult();
		return t;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getAll(){
		logger.debug("fetching all entities.");
		Session session = getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(getClazz());
		List<T> list = (List<T>)criteria.list();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	protected List<T> getMinimalPresentation(String...columns){
		logger.debug("fetching proxy for entity "+ getClazz());
		ProjectionList projectionList = Projections.projectionList();
		for(String col : columns){
			projectionList.add(Projections.property(col),col);
		}
		
		Session session = getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(getClazz());
		criteria.setProjection(projectionList)
		.setResultTransformer(Transformers.aliasToBean(getClazz()));
		
		List<T> list = (List<T>)criteria.list();
		return list;
	}
	
	public void delete(T t){
		logger.debug("deleting entity "+ getClazz());
		Session session = getSessionFactory().getCurrentSession();
		session.delete(t);
	}
	@SuppressWarnings("unchecked")
	protected List<T> getAllEqualTo(String[] columnNames, Object[] values){
		logger.debug("fetching all entities "+ getClazz()+" for criteria " + Arrays.toString(columnNames)+ " values " + Arrays.toString(values));
		Session session = getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(getClazz());
		for(int i=0; i < columnNames.length; i++){
			criteria.add(Restrictions.eq(columnNames[i], values[i]));	
		}
		List<T> list = (List<T>)criteria.list();
		return list;
	}
}
