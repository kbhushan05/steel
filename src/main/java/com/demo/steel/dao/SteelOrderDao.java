package com.demo.steel.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.steel.domain.SteelOrder;

@Repository
public class SteelOrderDao extends GenericDao<SteelOrder, Long> {

	@Override
	public Class<SteelOrder> getClazz() {
		return SteelOrder.class;
	}
	
	public List<SteelOrder> getMinimalOrder(){
		String[] columns = new String[]{"id", "supplier", "date", "status"};
		return getMinimalPresentation(columns);
	}

}
