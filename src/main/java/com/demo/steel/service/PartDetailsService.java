package com.demo.steel.service;

import java.util.Collections;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.steel.domain.PartNoDetails;
import com.demo.steel.domain.Supplier;
import com.demo.steel.util.StringUtil;

@Service
public class PartDetailsService {

	private static Logger logger = LoggerFactory.getLogger(PartDetailsService.class);
	
	@Autowired
	private SupplierService supplierService;
	
	@Transactional
	public Set<PartNoDetails> getParts(String supplierName){
		if(StringUtil.isEmpty(supplierName)){
			logger.debug("null supplier found, returning empty collection.");
			return Collections.emptySet();
		}
		Supplier supplier = supplierService.getSupplier(supplierName);
		logger.debug(supplier.getPartNos().size()+" parts found for supplier "+ supplier.getName());
		return supplier.getPartNos();
	}
	
}
