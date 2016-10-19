package com.demo.steel.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.steel.dao.SteelVerificationCheckDao;
import com.demo.steel.domain.SteelVerificationCheck;

@Service
public class SteelVerificationCheckService {

	@Autowired
	private SteelVerificationCheckDao steelVerificationDao;
	
	
}
