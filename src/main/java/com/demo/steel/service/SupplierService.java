package com.demo.steel.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.steel.dao.SupplierDao;
import com.demo.steel.domain.Supplier;

@Service
public class SupplierService {

	@Autowired
	private SupplierDao supplierDao;
	
	@Transactional
	public Supplier getSupplier(String supplierName){
		return supplierDao.get(supplierName);
	}
}
