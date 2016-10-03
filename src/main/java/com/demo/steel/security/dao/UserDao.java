package com.demo.steel.security.dao;

import org.springframework.stereotype.Repository;

import com.demo.steel.security.domain.User;

@Repository
public class UserDao extends SecurityGeneric<User, String>{

	@Override
	public Class<User> getClazz() {
		return User.class;
	}

}
