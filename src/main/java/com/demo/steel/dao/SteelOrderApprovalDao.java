package com.demo.steel.dao;

import org.springframework.stereotype.Repository;

import com.demo.steel.domain.SteelOrderApproval;

@Repository
public class SteelOrderApprovalDao extends GenericDao<SteelOrderApproval, Integer>{

	@Override
	public Class<SteelOrderApproval> getClazz() {
		return SteelOrderApproval.class;
	}

}
