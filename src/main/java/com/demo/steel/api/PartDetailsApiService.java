package com.demo.steel.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.steel.domain.PartNoDetails;
import com.demo.steel.service.PartDetailsService;
import com.demo.steel.service.SupplierService;
import com.demo.steel.util.StringUtil;

@Service
public class PartDetailsApiService {

	private static final Logger logger = LoggerFactory.getLogger(PartDetailsApiService.class);
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private PartDetailsService partDetailsService;
	
	public List<Integer> getParts(String supplierName){
		logger.debug("get parts for supplier workflow started.");
		if(StringUtil.isEmpty(supplierName)){
			throw new InvalidInputException(ErrorCode.SUPPLIER_NOT_FOUND, supplierName +" Supplier not found.");
		}
		Collection<PartNoDetails> parts = partDetailsService.getParts(supplierName);
		List<Integer> ints = new ArrayList<>(parts.size());
		for(PartNoDetails part : parts){
			ints.add(part.getPartNo());
		}
		logger.debug("get parts for supplier workflow successful.");
		return ints;
	}
}
