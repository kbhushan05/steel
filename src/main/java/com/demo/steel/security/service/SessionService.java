package com.demo.steel.security.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.steel.security.dao.SessionDao;
import com.demo.steel.security.domain.Session;
import com.demo.steel.security.domain.User;

@Service
public class SessionService {

	@Autowired
	private SessionDao dao;
	
	@Transactional
	public Session create(String token, User user){
		Session session = new Session();
		session.setToken(token);
		session.setUser(user);
		int pk = dao.save(session);
		session.setPrimaryKey(pk);
		return session;
	}
	
	@Transactional
	public boolean isActive(String token){
		return dao.getSessionFor(token)== null ? false : true;
	}
	
	@Transactional
	public void remove(String token){
		Session s = dao.getSessionFor(token);
		dao.delete(s);
	}

	public SessionDao getDao() {
		return dao;
	}

	public void setDao(SessionDao dao) {
		this.dao = dao;
	}
	
}
