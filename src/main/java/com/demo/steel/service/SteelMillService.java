package com.demo.steel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.steel.dao.SteelMillDao;
import com.demo.steel.dao.SupplierDao;
import com.demo.steel.domain.SteelMill;

@Service
public class SteelMillService {

	@Autowired
	private SteelMillDao dao;
	@Autowired
	private SupplierDao supplierDao;
	
	@Transactional
	public List<SteelMill> getAll(){
		return dao.getAll();
	}

	public SupplierDao getSupplierDao() {
		return supplierDao;
	}

	public void setSupplierDao(SupplierDao supplierDao) {
		this.supplierDao = supplierDao;
	}
	
}
