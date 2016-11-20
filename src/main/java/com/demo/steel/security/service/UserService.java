package com.demo.steel.security.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.steel.security.dao.UserDao;
import com.demo.steel.security.domain.User;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;

	@Transactional
	public User get(String username){
		return userDao.get(username);
	}

}
