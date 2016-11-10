package com.demo.steel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.steel.dao.SteelVerificationCheckDao;

@Service
public class SteelVerificationCheckService {

	@Autowired
	private SteelVerificationCheckDao steelVerificationDao;
	
	
}
