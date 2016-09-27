package com.demo.steel.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.demo.steel.domain.VerificationCheck;

@Repository
public class VerificationCheckDao extends GenericDao<VerificationCheck,String>{

	@Override
	public Class<VerificationCheck> getClazz() {
		return VerificationCheck.class;
	}
	
	@SuppressWarnings("unchecked")
	public List<? extends VerificationCheck> getDefaultVerificationCheck(){
		Session session = getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(VerificationCheck.class);
		List<? extends VerificationCheck> checks = (List<? extends VerificationCheck>)criteria.list();
		return checks;
	}

}
