package com.demo.steel.dao;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.demo.steel.domain.VerificationCheck;
import com.demo.steel.domain.VerificationCheck.Type;

@Repository
public class VerificationCheckDao extends GenericDao<VerificationCheck,String>{

	private static final Logger logger = LoggerFactory.getLogger(VerificationCheckDao.class);
	
	@Override
	public Class<VerificationCheck> getClazz() {
		return VerificationCheck.class;
	}
	
	@SuppressWarnings("unchecked")
	public List<? extends VerificationCheck> getDefaultVerificationCheck(){
		logger.debug("fetching all verification checks.");
		Session session = getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(VerificationCheck.class);
		List<? extends VerificationCheck> checks = (List<? extends VerificationCheck>)criteria.list();
		return checks;
	}
	
	public List<VerificationCheck> get(Type type){
		logger.debug("fetching all verification checks of type " + type);
		List<VerificationCheck> checks = getAllEqualTo(new String[]{"type"}, new Object[]{type});
		if(checks == null || checks.isEmpty()){
			return Collections.emptyList();
		}
		return checks;
	}

}
