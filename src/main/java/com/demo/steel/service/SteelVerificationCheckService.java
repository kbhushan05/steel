package com.demo.steel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.steel.dao.SteelVerificationCheckDao;
import com.demo.steel.domain.SteelVerificationCheck;

@Service
public class SteelVerificationCheckService {

	@Autowired
	private SteelVerificationCheckDao steelVerificationDao;
	
	@Transactional
	public void uploadReport(int verificationCheckId, String filename ,byte[] bytes, String mimeType){
		SteelVerificationCheck verificationCheck = steelVerificationDao.get(verificationCheckId);
		verificationCheck.setFilename(filename);
		verificationCheck.setFile(bytes);
		verificationCheck.setMimeType(mimeType);
		steelVerificationDao.update(verificationCheck);
	}

	@Transactional
	public SteelVerificationCheck downloadReport(int verificationCheckId) {
		SteelVerificationCheck verificationCheck = steelVerificationDao.get(verificationCheckId);
		verificationCheck.getFile();
		return verificationCheck;
	}
	
}
