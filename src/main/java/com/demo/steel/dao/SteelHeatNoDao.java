package com.demo.steel.dao;

import org.springframework.stereotype.Repository;

import com.demo.steel.domain.SteelHeatNo;

@Repository
public class SteelHeatNoDao extends GenericDao<SteelHeatNo, String>{

	@Override
	public Class<SteelHeatNo> getClazz() {
		return SteelHeatNo.class;
	}

	
}
