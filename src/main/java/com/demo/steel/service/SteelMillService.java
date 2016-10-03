package com.demo.steel.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.demo.steel.domain.SteelMill;
import com.demo.steel.dto.SteelMillDao;

@Service
public class SteelMillService {

	private SteelMillDao dao;
	
	public List<SteelMill> getSteelMills(String supplierName){
		return dao.getSteelMills(supplierName);
	}
}
