package com.demo.steel.service;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.steel.dao.ReportDao;
import com.demo.steel.dao.SteelVerificationCheckDao;
import com.demo.steel.domain.Report;
import com.demo.steel.domain.SteelVerificationCheck;

@Service
public class ReportService {

	private static final Logger logger = LoggerFactory.getLogger(ReportService.class);
	
	@Autowired
	private SteelVerificationCheckDao steelVerificationDao;
	@Autowired
	private ReportDao reportDao;
	
	@Transactional
	public void uploadReport(int verificationCheckId, String filename ,byte[] bytes, String mimeType){
		SteelVerificationCheck verificationCheck = steelVerificationDao.get(verificationCheckId);
		verificationCheck.setFilename(filename);
		steelVerificationDao.update(verificationCheck);
		
		logger.debug("creating Report object.");
		Report report = new Report();
		report.setFilename(filename);
		report.setMimeType(mimeType);
		report.setData(bytes);
		report.setSteelVerificationCheckId(verificationCheck.getId());
		
		reportDao.save(report);
		logger.debug("Report saved successfully.");
	}

	@Transactional
	public SteelVerificationCheck downloadReport(int verificationCheckId) {
		SteelVerificationCheck verificationCheck = steelVerificationDao.get(verificationCheckId);
		Report report = reportDao.getReport(verificationCheck);
		Hibernate.initialize(report.getData());
		verificationCheck.setReport(report);
		logger.debug("found report successfully.");
		return verificationCheck;
	}
}
