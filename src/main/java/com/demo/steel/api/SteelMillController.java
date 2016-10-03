package com.demo.steel.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.steel.domain.SteelMill;
import com.demo.steel.service.SteelMillService;

@RestController
@RequestMapping("/steelMeels")
public class SteelMillController {
	
	@Autowired
	private SteelMillService steelMillService;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<SteelMill> getAll(@RequestParam String supplierName){
		return getSteelMillService().getSteelMills(supplierName);
	}

	public SteelMillService getSteelMillService() {
		return steelMillService;
	}

	public void setSteelMillService(SteelMillService steelMillService) {
		this.steelMillService = steelMillService;
	}
	
}
