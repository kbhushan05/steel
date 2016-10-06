package com.demo.steel.dao;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.demo.steel.domain.VerificationCheck;
import com.demo.steel.domain.VerificationCheck.Type;

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
	
	public List<VerificationCheck> get(Type type){
		List<VerificationCheck> checks = getAllEqualTo(new String[]{"type"}, new Object[]{type});
		if(checks == null || checks.isEmpty()){
			return Collections.emptyList();
		}
		return checks;
	}

}
