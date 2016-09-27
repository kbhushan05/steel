package com.demo.steel.dao;

import org.springframework.stereotype.Repository;

import com.demo.steel.domain.SteelVerificationCheck;
@Repository
public class SteelVerificationCheckDao extends GenericDao<SteelVerificationCheck, Integer>{

	@Override
	public Class<SteelVerificationCheck> getClazz() {
		return SteelVerificationCheck.class;
	}

}
