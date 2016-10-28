package com.demo.steel.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.demo.steel.domain.Report;
import com.demo.steel.domain.SteelVerificationCheck;

@Repository
public class ReportDao extends GenericDao<Report, Integer>{

	private static final Logger logger = LoggerFactory.getLogger(ReportDao.class);
	
	@Override
	public Class<Report> getClazz() {
		return Report.class;
	}
	
	public Report getReport(SteelVerificationCheck check){
		logger.debug("fetching report for verification check "+ check.getPrimarykey());
		Report report = check.getReport();
		report.getData();
		return report;
	}
	
}
