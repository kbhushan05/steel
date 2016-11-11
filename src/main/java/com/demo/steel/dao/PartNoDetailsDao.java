package com.demo.steel.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.steel.domain.PartNoDetails;
import com.demo.steel.domain.Supplier;

@Repository
public class PartNoDetailsDao extends GenericDao<PartNoDetails, Integer>{

	@Override
	public Class<PartNoDetails> getClazz() {
		return PartNoDetails.class;
	}

	@SuppressWarnings("unchecked")
	public List<PartNoDetails> filterBy(Supplier supplier){
		return (List<PartNoDetails>)getEqualTo(new String[]{"supplier"}, new Object[]{supplier.getCode()});
	}
}
