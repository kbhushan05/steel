package com.demo.steel.security.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.steel.security.domain.Session;
import com.demo.steel.security.domain.User;

@Repository
public class SessionDao extends SecurityGeneric<Session, Integer>{

	@Override
	public Class<Session> getClazz() {
		return Session.class;
	}
	
	public Session getSessionFor(String token){
		List<Session> sessions = getAllEqualTo(new String[]{"token"}, new Object[]{token});
		if(sessions == null || sessions.isEmpty()){
			return null;
		}
		return sessions.get(0);
	}

	public Session getSessionForUser(User user){
		List<Session> session = getAllEqualTo(new String[]{"user"}, new Object[]{user});
		if(session == null || session.isEmpty()){
			return null;
		}
		return session.get(0);
	}
}
