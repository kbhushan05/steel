package com.demo.steel.security.service;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.steel.security.dao.SessionDao;
import com.demo.steel.security.domain.Session;
import com.demo.steel.security.domain.User;

@Service
public class SessionService {

	private static final Logger logger = LoggerFactory.getLogger(SessionService.class);
	
	@Autowired
	private SessionDao dao;
	
	@Transactional
	public Session create(String token, User user){
		logger.debug("creating session for user "+ user);
		Session session = new Session();
		session.setToken(token);
		session.setUser(user);
		int pk = dao.save(session);
		session.setPrimaryKey(pk);
		logger.debug("Session created successfully.");
		return session;
	}
	
	@Transactional
	public boolean isActive(String token){
		return dao.getSessionFor(token)== null ? false : true;
	}
	@Transactional
	public Session get(String token){
		return dao.getSessionFor(token);
	}
	
	@Transactional
	public Session getForUser(User user){
		return dao.getSessionForUser(user);
	}
	
	@Transactional
	public void remove(User user){
		Session s = dao.getSessionForUser(user);
		dao.delete(s);
	}

	public SessionDao getDao() {
		return dao;
	}

	public void setDao(SessionDao dao) {
		this.dao = dao;
	}
	
}
