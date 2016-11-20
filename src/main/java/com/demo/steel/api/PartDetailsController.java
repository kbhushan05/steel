package com.demo.steel.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parts")
public class PartDetailsController {
	private static final Logger logger = LoggerFactory.getLogger(PartDetailsController.class);
	@Autowired
	private PartDetailsApiService service;
	
	@RequestMapping(method = RequestMethod.GET,produces="application/json")
	public List<Integer> getParts(String supplierName){
		logger.debug("received request for parts for supplier "+ supplierName);
		List<Integer> ints = service.getParts(supplierName);
		logger.debug("response generated successfully.");
		return ints;
	}
}
