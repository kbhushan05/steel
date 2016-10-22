package com.demo.steel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.steel.dao.SteelOrderApprovalDao;
import com.demo.steel.dao.SteelOrderDao;
import com.demo.steel.domain.SteelOrder;
import com.demo.steel.domain.SteelOrderApproval;

@Service
public class SteelOrderApprovalService {

	@Autowired
	private SteelOrderDao steelOrderDao;
	@Autowired
	private SteelOrderApprovalDao steelOrderApprovalDao;
	
	@Transactional
	public void uploadReport(String orderId, String filename ,byte[] bytes, String mimeType){
		SteelOrder order = steelOrderDao.load(orderId);
		SteelOrderApproval approval = new SteelOrderApproval();
		approval.setData(bytes);
		approval.setFilename(filename);
		approval.setMimeType(mimeType);
		Integer key = steelOrderApprovalDao.save(approval);
		approval.setId(key);
		
		order.setSteelOrderApproval(approval);
		steelOrderDao.update(order);
		
	}

	@Transactional
	public SteelOrderApproval downloadReport(String orderId) {
		SteelOrder order = steelOrderDao.load(orderId);
		order.getSteelOrderApproval().getFilename();
		return order.getSteelOrderApproval(); 
	}
}
