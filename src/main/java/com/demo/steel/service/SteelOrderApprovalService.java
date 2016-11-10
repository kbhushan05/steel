package com.demo.steel.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.steel.dao.SteelOrderApprovalDao;
import com.demo.steel.dao.SteelOrderDao;
import com.demo.steel.domain.SteelOrder;
import com.demo.steel.domain.SteelOrderApproval;

@Service
public class SteelOrderApprovalService {

	private static final Logger logger = LoggerFactory.getLogger(SteelOrderApprovalService.class);
	
	@Autowired
	private SteelOrderDao steelOrderDao;
	@Autowired
	private SteelOrderApprovalDao steelOrderApprovalDao;
	
	@Transactional
	public void uploadReport(String orderId, String filename ,byte[] bytes, String mimeType){
		SteelOrder order = steelOrderDao.load(orderId);
		logger.debug("creating approval report.");
		SteelOrderApproval approval = new SteelOrderApproval();
		approval.setData(bytes);
		approval.setFilename(filename);
		approval.setMimeType(mimeType);
		Integer key = steelOrderApprovalDao.save(approval);
		approval.setId(key);
		
		steelOrderDao.update(order);
		logger.debug("approval report uploaded successfully.");
	}

	@Transactional
	public SteelOrderApproval downloadReport(String orderId) {
		SteelOrderApproval approval = steelOrderApprovalDao.getOrderpprovalForOrder(orderId);
		logger.debug("report downloaded "+ approval.getFilename()+" "+approval.getMimeType());
		return approval;
	}
}
