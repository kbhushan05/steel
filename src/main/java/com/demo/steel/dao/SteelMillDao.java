package com.demo.steel.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.steel.domain.SteelMill;
@Repository
public class SteelMillDao extends GenericDao<SteelMill, String>{

	@Override
	public Class<SteelMill> getClazz() {
		return SteelMill.class;
	}
	
	public List<SteelMill> getSteelMills(int supplierCode){
		return getAllEqualTo(new String[]{"supplier"}, new Object[]{supplierCode});
	}

}
