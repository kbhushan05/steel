package com.demo.steel.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class GenericDao <T, K extends Serializable> {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	public T get(K key){
		Session session = getSessionFactory().getCurrentSession();
		T t = (T)session.get(getClazz(),key);
		return t;
	}
	
	@SuppressWarnings("unchecked")
	public K save(T t){
		Session session = getSessionFactory().getCurrentSession();
		K k = (K)session.save(t);
		return k;
	}

	public void update(T t){
		Session session = getSessionFactory().getCurrentSession();
		session.saveOrUpdate(t);
	}
	
	public boolean isExists(K key){
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
		Session session = getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(getClazz());
		List<T> list = (List<T>)criteria.list();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	protected List<T> getMinimalPresentation(String...columns){
		
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
}
