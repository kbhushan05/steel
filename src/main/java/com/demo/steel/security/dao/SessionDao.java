package com.demo.steel.security.dao;

import org.springframework.stereotype.Repository;

import com.demo.steel.security.domain.Session;

@Repository
public class SessionDao extends SecurityGeneric<Session, Integer>{

	@Override
	public Class<Session> getClazz() {
		return Session.class;
	}
	
	public Session getSessionFor(String token){
		return getEqualTo(new String[]{"token"}, new Object[]{token});
	}

}
