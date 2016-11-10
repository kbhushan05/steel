package com.demo.steel.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.demo.steel.domain.SteelOrderApproval;

@Repository
public class SteelOrderApprovalDao extends GenericDao<SteelOrderApproval, Integer>{

	private static final Logger logger = LoggerFactory.getLogger(SteelOrderApprovalDao.class);
	@Override
	public Class<SteelOrderApproval> getClazz() {
		return SteelOrderApproval.class;
	}

	public SteelOrderApproval getOrderpprovalForOrder(String orderId){
		logger.debug("fetching entity "+SteelOrderApproval.class + "for orderId "+orderId);
		return getEqualTo(new String[]{"orderId"},new Object[]{orderId});
	}
}
