package com.demo.steel.dao;

import org.springframework.stereotype.Repository;

import com.demo.steel.domain.Report;
import com.demo.steel.domain.SteelVerificationCheck;

@Repository
public class ReportDao extends GenericDao<Report, Integer>{

	@Override
	public Class<Report> getClazz() {
		return Report.class;
	}
	
	public Report getReport(SteelVerificationCheck check){
		Report report = check.getReport();
		report.getData();
		return report;
	}
	
}
