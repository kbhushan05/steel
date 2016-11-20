package com.demo.steel.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.steel.api.ErrorCode;
import com.demo.steel.api.InvalidInputException;
import com.demo.steel.dao.DeviationDao;
import com.demo.steel.dao.SteelHeatNoDao;
import com.demo.steel.dao.SteelOrderDao;
import com.demo.steel.dao.SteelVerificationCheckDao;
import com.demo.steel.dao.SupplierDao;
import com.demo.steel.dao.VerificationCheckDao;
import com.demo.steel.domain.Deviation;
import com.demo.steel.domain.PartManifacturingDetail;
import com.demo.steel.domain.PartNoDetails;
import com.demo.steel.domain.SteelOrder;
import com.demo.steel.domain.SteelOrder.State;
import com.demo.steel.domain.SteelOrderFactory;
import com.demo.steel.domain.SteelVerificationCheck;
import com.demo.steel.domain.Supplier;
import com.demo.steel.domain.VerificationCheck;
import com.demo.steel.domain.VerificationCheck.Type;
import com.demo.steel.util.StringUtil;

@Service
public class SteelOrderService {
	
	private final static Logger logger = LoggerFactory.getLogger(SteelOrderService.class);
	private final int MAX_LIMIT = 30;
	private static final Random ORDER_ID_GENERATOR = new Random();
	
	@Autowired
	private SupplierDao supplierDao;
	@Autowired
	private SteelVerificationCheckDao steelVerificationCheackDao;
	@Autowired
	private SteelOrderDao steelOrderDao;
	@Autowired
	private VerificationCheckDao verificationCheckDao;
	@Autowired
	private DeviationDao deviationDao;
	@Autowired
	private SteelHeatNoDao steelHeatNoDao;
	
	@Transactional
	public SteelOrder createNewOrder(String supplierName){
		logger.debug("creating new order for supplier "+ supplierName);
		SteelOrder order = SteelOrderFactory.createBasicSteelOrder();
		
		Supplier supplier = getSupplier(supplierName);
		if(supplier == null){
			logger.debug("No supplier found for name "+ supplierName);
			InvalidInputException ex =  new InvalidInputException(ErrorCode.SUPPLIER_NOT_FOUND,"Supplier "+supplierName+" does not exists.");
			throw ex;
		}
		order.setSupplier(supplier);
		logger.debug(supplier.getName() + " supplier is set to order.");
		order.setId(generateOrderId(supplier));
		
		List<VerificationCheck> vcs = verificationCheckDao.get(Type.BASIC);
		logger.debug(vcs.size()+" verification checks of type BASIC found for supplier.");
		List<SteelVerificationCheck> checkList = new ArrayList<>();
		for(VerificationCheck vc : vcs){
			SteelVerificationCheck check = new SteelVerificationCheck();
			check.setVerificationCheck(vc);
			checkList.add(check);
		}
		order.setVerificationChecks(checkList);
		logger.debug("all verification checks set successfully.");
		
		Set<PartManifacturingDetail> partManifacturingDetails = new HashSet<>();
		logger.debug(supplier.getPartNos().size()+" parts found for the supplier.");
		for(PartNoDetails part : supplier.getPartNos()){
			PartManifacturingDetail details = new PartManifacturingDetail();
			details.setPartDetails(part);
			partManifacturingDetails.add(details);
		}
		order.setPartManifacturingDetails(partManifacturingDetails);
		logger.debug("all part manifacturing details are set to order.");
		Deviation dev = new Deviation();
		order.setDeviation(dev);
		logger.debug("new deviation is set to order.");
		return order;
	}
	
	@Transactional
	public SteelOrder createNewOrderFrom(String orderId){
		logger.debug("creating new FHT order from order "+ orderId);
		SteelOrder parent = steelOrderDao.get(orderId);
		if(parent.getState() != SteelOrder.State.APPROVED){
			throw new InvalidInputException(ErrorCode.INVALID_STATE_FHT,"Invalid order state "+parent.getState()+". Approve order before creating FHT order.");
		}
		
		SteelOrder fhtOrder = SteelOrderFactory.createFhtSteelOrder(parent);
		fhtOrder.setId(generateOrderId(fhtOrder.getSupplier()));
		fhtOrder.setStatus(State.NEW);
		List<VerificationCheck> vcs = verificationCheckDao.get(Type.FHT);
		logger.debug(vcs.size()+" verification checks of type FHT found.");
		List<SteelVerificationCheck> checkList = new ArrayList<>();
		for(VerificationCheck vc : vcs){
			SteelVerificationCheck check = new SteelVerificationCheck();
			check.setVerificationCheck(vc);
			checkList.add(check);
		}
		fhtOrder.setVerificationChecks(checkList);
		logger.debug("all verification checks set successfully.");
		
		Deviation dev = new Deviation();
		dev.setRequestDate(new Date());
		dev.setType(Deviation.Type.FHT);
		fhtOrder.setDeviation(dev);
		return fhtOrder;
	}
	
	@Transactional
	public SteelOrder saveOrder(SteelOrder order){
		if(order.getState() != SteelOrder.State.NEW){
			throw new InvalidInputException(ErrorCode.INVALID_STATE_SAVE,"Invalid order state "+order.getState()+". Order cannot be saved.");
		}
		if(order.getMill() == null){
			throw new InvalidInputException(ErrorCode.STEELMILL_NOT_FOUND, "Order is not well formed. Steel mill is null.");
		}
		order.setStatus(SteelOrder.State.SAVED);
		String key = steelOrderDao.save(order);
		order.setId(key);
		return order;
	}
	
	@Transactional
	public SteelOrder updateOrder(SteelOrder order) {
		if(order.getState() != SteelOrder.State.SAVED){
			throw new InvalidInputException(ErrorCode.INVALID_STATE_UPDATE,"Invalid order state "+order.getState()+". Save before update.");
		}
		return steelOrderDao.update(order);
	}

	@Transactional
	public SteelOrder submitOrder(SteelOrder order){
		if(order.getState() != SteelOrder.State.SAVED){
			throw new InvalidInputException(ErrorCode.INVALID_STATE_SUBMIT,"Invalid order state "+order.getState()+". Save before submit.");
		}
		
		Iterator<PartManifacturingDetail> itrs = order.getPartManifacturingDetails().iterator();
		int count = 0;
		while(itrs.hasNext()){
			PartManifacturingDetail detail = itrs.next();
			if(detail.getStatus() == PartManifacturingDetail.Status.UNCHECKED){
				count++;
				itrs.remove();
			}
		}
		logger.debug("removed "+ count+" unchecked PartManifacturingDetails of FHT.");
		order.setStatus(SteelOrder.State.SUBMITTED);
		return steelOrderDao.merge(order);
	}
	
	@Transactional
	public SteelOrder approveOrder(SteelOrder order){
		if(order.getState() != State.SUBMITTED){
			throw new InvalidInputException(ErrorCode.INVALID_STATE_APPROVE,"Invalid order state "+order.getState()+". Submit before approve.");
		}
		order.setStatus(State.APPROVED);
		steelOrderDao.update(order);
		return order;
	}

	@Transactional
	public SteelOrder rejectOrder(SteelOrder order){
		if(order.getState() != State.SUBMITTED){
			throw new InvalidInputException(ErrorCode.INVALID_STATE_REJECT,"Invalid order state "+order.getState()+". Submit before reject.");
		}
		order.setStatus(State.REJECTED);
		steelOrderDao.update(order);
		return order;
	}
	
	@Transactional
	public List<SteelOrder> getAll() {
		return steelOrderDao.getOrderProxies();
	}

	@Transactional
	public SteelOrder getOrder(String orderId) {
		return steelOrderDao.get(orderId);
	}
	
	@Transactional
	public List<SteelOrder> filterBy(State state){
		return steelOrderDao.filterBy(state);
	}
	
	@Transactional
	public List<SteelOrder> filterBy(Supplier supplier){
		if(supplier == null || StringUtil.isEmpty(supplier.getName())){
			return Collections.emptyList();
		}
		return steelOrderDao.filterBy(supplier);
	}
	
	@Transactional
	public List<SteelOrder> filterBy(Supplier supplier, State state){
		if(supplier == null || StringUtil.isEmpty(supplier.getName())){
			return Collections.emptyList();
		}
		List<SteelOrder> orders = steelOrderDao.filterBy(supplier, state);
		if(orders == null){
			return Collections.emptyList();
		}
		return orders;
	}

	private String generateOrderId(Supplier supplier){
		int min = 10000;
		int max = 1000000;
		long id= ORDER_ID_GENERATOR.nextInt((max-min)+1)+min;
		String strId = String.valueOf(id < 0 ? id * -1 : id);
		return strId+"-"+supplier.getCode()+"-"+supplier.getName();
	}
	
	private Supplier getSupplier(String name){
		return supplierDao.get(name);
	}

	@Transactional
	public List<SteelOrder> getOrderPage(int page, int limit,Supplier supplier) {
		int total = limit;
		if(limit > MAX_LIMIT || limit <=0){
			total = MAX_LIMIT;
		}
		if(page <= 0){
			page = 1;
		}
		int startIndex = (page -1)*total;
		logger.debug("Pagination start index "+ startIndex +" total orders "+ limit);
		return steelOrderDao.getAll(startIndex, total, supplier);
	}
	
	@Transactional
	public List<SteelOrder> getOrderPage(int page, int limit) {
		return getOrderPage(page, limit, null);
	}
}

/*	private boolean isValidNewSteelToBuy(SteelOrder order){
SteelHeatNo steelHeatNo = getSteelHeatNoDao().get(order.getSteelHeatNumber());
if(steelHeatNo == null) throw new IllegalArgumentException("Invalid Steel Heat No.");
float sumOfTotalTonnage = getSteelOrderDao().getSumOfTotalSteelTonageForHeatNo(steelHeatNo);
float diff = steelHeatNo.getTonnage() - sumOfTotalTonnage;
return order.getNewSteelToBuy() <= diff && order.getNewSteelToBuy() >= 0? true : false; 
}*/