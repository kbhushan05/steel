package com.demo.steel.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.steel.dao.ReportDao;
import com.demo.steel.dao.SteelVerificationCheckDao;
import com.demo.steel.domain.Report;
import com.demo.steel.domain.SteelVerificationCheck;

@Service
public class ReportService {

	@Autowired
	private SteelVerificationCheckDao steelVerificationDao;
	@Autowired
	private ReportDao reportDao;
	
	@Transactional
	public void uploadReport(int verificationCheckId, String filename ,byte[] bytes, String mimeType){
		SteelVerificationCheck verificationCheck = steelVerificationDao.get(verificationCheckId);
		verificationCheck.setFilename(filename);
		verificationCheck.setMimeType(mimeType);
		steelVerificationDao.update(verificationCheck);
		
		Report report = new Report();
		report.setData(bytes);
		report.setSteelVerificationCheck(verificationCheck);
		
		reportDao.save(report);
	}

	@Transactional
	public SteelVerificationCheck downloadReport(int verificationCheckId) {
		SteelVerificationCheck verificationCheck = steelVerificationDao.get(verificationCheckId);
		Report report = reportDao.getReport(verificationCheck);
		Hibernate.initialize(report.getData());
		verificationCheck.setReport(report);
		return verificationCheck;
	}
}
