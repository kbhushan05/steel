package com.demo.steel.dao;

import org.springframework.stereotype.Repository;

import com.demo.steel.domain.PartNoDetails;

@Repository
public class PartNoDetailsDao extends GenericDao<PartNoDetails, Integer>{

	@Override
	public Class<PartNoDetails> getClazz() {
		return PartNoDetails.class;
	}

	public PartNoDetails getPartNoDetails(int heatNumber, String supplier){
		return getEqualTo(new String[]{"heatNumber","supplier"}, new Object[]{heatNumber,supplier});
	}
}
