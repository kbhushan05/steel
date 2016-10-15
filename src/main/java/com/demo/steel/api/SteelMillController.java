package com.demo.steel.api;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.steel.dto.SteelMillDto;

@RestController
@RequestMapping("/steelmills")
public class SteelMillController {
	
	@Autowired
	private SteelOrderApiService service;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<SteelMillDto> getAll(){
		return Collections.emptyList();
	}

	public SteelOrderApiService getService() {
		return service;
	}

	public void setService(SteelOrderApiService service) {
		this.service = service;
	}
	
}
