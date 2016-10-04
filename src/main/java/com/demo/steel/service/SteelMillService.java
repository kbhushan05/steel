package com.demo.steel.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.steel.dao.SupplierDao;
import com.demo.steel.domain.SteelMill;
import com.demo.steel.domain.Supplier;
import com.demo.steel.dto.SteelMillDao;

@Service
public class SteelMillService {

	@Autowired
	private SteelMillDao dao;
	@Autowired
	private SupplierDao supplierDao;
	
	@Transactional
	public List<SteelMill> getSteelMills(String supplierName){
		Supplier supplier = supplierDao.get(supplierName);
		supplier.getSteelMills().size();
		List<SteelMill> mills = new ArrayList<>(supplier.getSteelMills());
		return mills;
	}

	public SupplierDao getSupplierDao() {
		return supplierDao;
	}

	public void setSupplierDao(SupplierDao supplierDao) {
		this.supplierDao = supplierDao;
	}
	
}
