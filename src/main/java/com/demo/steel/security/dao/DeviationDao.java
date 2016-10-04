package com.demo.steel.security.dao;

import org.springframework.stereotype.Repository;

import com.demo.steel.dao.GenericDao;
import com.demo.steel.domain.Deviation;

@Repository
public class DeviationDao extends GenericDao<Deviation, Integer> {

	@Override
	public Class<Deviation> getClazz() {
		return Deviation.class;
	}

}
