package com.demo.steel.dao;

import org.springframework.stereotype.Repository;

import com.demo.steel.domain.Supplier;

@Repository
public class SupplierDao extends GenericDao<Supplier, Integer>{

	@Override
	public Class<Supplier> getClazz() {
		return Supplier.class;
	}
	
	public Supplier get(String name){
		return getEqualTo(new String[]{"name"}, new Object[]{name});
	}
	

}
