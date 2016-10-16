package com.demo.steel.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.steel.dao.SteelHeatNoDao;
import com.demo.steel.dao.SteelOrderDao;
import com.demo.steel.dao.SteelVerificationCheckDao;
import com.demo.steel.dao.SupplierDao;
import com.demo.steel.dao.VerificationCheckDao;
import com.demo.steel.domain.Deviation;
import com.demo.steel.domain.PartManifacturingDetails;
import com.demo.steel.domain.PartNoDetails;
import com.demo.steel.domain.SteelOrder;
import com.demo.steel.domain.SteelOrder.Status;
import com.demo.steel.domain.SteelVerificationCheck;
import com.demo.steel.domain.Supplier;
import com.demo.steel.domain.VerificationCheck;
import com.demo.steel.domain.VerificationCheck.Type;
import com.demo.steel.dto.PartManifacturingDetailsDto.PartDetailsStatus;
import com.demo.steel.security.dao.DeviationDao;

@Service
public class SteelOrderService {
	
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
		Supplier supplier = getSupplier(supplierName);
		List<VerificationCheck> vcs = getVerificationCheckDao().get(Type.BASIC);
		List<SteelVerificationCheck> checkList = new ArrayList<>();
		for(VerificationCheck vc : vcs){
			SteelVerificationCheck check = new SteelVerificationCheck();
			check.setVerificationCheck(vc);
			checkList.add(check);
		}
		
		SteelOrder order = new SteelOrder();
		order.setStatus(Status.NEW);
		order.setSupplier(supplier);
		order.setId(generateOrderId(supplier));
		order.setVerificationCheck(checkList);
		Set<PartManifacturingDetails> partManifacturingDetails = new HashSet<>();
		for(PartNoDetails part : supplier.getPartNos()){
			PartManifacturingDetails details = new PartManifacturingDetails();
			details.setPartDetails(part);
			partManifacturingDetails.add(details);
		}
		order.setPartManifacturingDetails(partManifacturingDetails);
		Deviation dev = new Deviation();
		List<Deviation> devs = new ArrayList<>();
		devs.add(dev);
		order.setDeviation(devs);
		return order;
	}
	
	@Transactional
	public SteelOrder createNewFhtOrder(String orderId){
		
		SteelOrder fhtOrder = getSteelOrderDao().get(orderId);
		if(fhtOrder.getStatus() != SteelOrder.Status.APPROVED){
			throw new IllegalArgumentException("Invalid Order state.");
		}
		
		List<VerificationCheck> vcs = getVerificationCheckDao().get(Type.FHT);
		List<SteelVerificationCheck> checkList = new ArrayList<>();
		for(VerificationCheck vc : vcs){
			SteelVerificationCheck check = new SteelVerificationCheck();
			check.setVerificationCheck(vc);
			checkList.add(check);
		}
		fhtOrder.setVerificationCheck(checkList);
		fhtOrder.getPartManifacturingDetails().size();
		Iterator <PartManifacturingDetails> itr = fhtOrder.getPartManifacturingDetails().iterator();
		while(itr.hasNext()){
			PartManifacturingDetails part = itr.next();
			if(part.getStatus() == PartManifacturingDetails.Status.UNCHECKED){
				itr.remove();
			}
		}
		
		Deviation dev = new Deviation();
		dev.setRequestDate(new Date());
		dev.setType(Deviation.Type.FHTV);
		List<Deviation> devs = new ArrayList<>();
		devs.add(dev);
		fhtOrder.setDeviation(devs);
		return fhtOrder;
	}
	
	@Transactional
	public SteelOrder saveOrder(SteelOrder order){
		order.setStatus(SteelOrder.Status.SAVED);
		String key = getSteelOrderDao().save(order);
		order.setId(key);
		return order;
	}
	
	@Transactional
	public SteelOrder submitOrder(SteelOrder order){
		/*if(!isValidNewSteelToBuy(order)){
			throw new IllegalAddException("New steel to buy exceeds limit");
		}*/
		order = getSteelOrderDao().reattachEntity(order);
		Iterator<PartManifacturingDetails> itrs = order.getPartManifacturingDetails().iterator();
		while(itrs.hasNext()){
			PartManifacturingDetails details = itrs.next();
			if(details.getStatus() == PartManifacturingDetails.Status.UNCHECKED){
				itrs.remove();
			}
		}
		order.setStatus(SteelOrder.Status.SUBMITTED);
		return getSteelOrderDao().update(order);
	}
	
/*	private boolean isValidNewSteelToBuy(SteelOrder order){
		SteelHeatNo steelHeatNo = getSteelHeatNoDao().get(order.getSteelHeatNumber());
		if(steelHeatNo == null) throw new IllegalArgumentException("Invalid Steel Heat No.");
		float sumOfTotalTonnage = getSteelOrderDao().getSumOfTotalSteelTonageForHeatNo(steelHeatNo);
		float diff = steelHeatNo.getTonnage() - sumOfTotalTonnage;
		return order.getNewSteelToBuy() <= diff && order.getNewSteelToBuy() >= 0? true : false; 
	}*/
	
	@Transactional
	public void submitFhtOrder(SteelOrder order){
		order = getSteelOrderDao().reattachEntity(order);
		/*if(!isValidNewSteelToBuy(order)){
			throw new IllegalAddException("New steel to buy exceeds limit");
		}*/
		
		if(order.getStatus() != SteelOrder.Status.FHTV_NEW){
			throw new IllegalArgumentException("Invalid Order state.");
		}
		Iterator<PartManifacturingDetails> itrs = order.getPartManifacturingDetails().iterator();
		while(itrs.hasNext()){
			PartManifacturingDetails details = itrs.next();
			if(details.getStatus() == PartManifacturingDetails.Status.UNCHECKED){
				itrs.remove();
			}
		}
		order.setStatus(Status.FHTV_SUBMITTED);
		getSteelOrderDao().update(order);
	}
	
	@Transactional
	public void approveFhtvOrder(SteelOrder order){
		if(order.getStatus() != SteelOrder.Status.FHTV_SUBMITTED){
			throw new IllegalArgumentException("Invalid Order state.");
		}
		order.setStatus(Status.FHTV_APPROVED);
		getSteelOrderDao().update(order);
	}
	
	@Transactional
	public void rejectFhtvOrder(SteelOrder order){
		if(order.getStatus() != SteelOrder.Status.FHTV_SUBMITTED){
			throw new IllegalArgumentException("Invalid Order state.");
		}
		order.setStatus(Status.FHTV_REJECTED);
		getSteelOrderDao().update(order);
	}
	
	@Transactional
	public void updateOrder(SteelOrder order) {
		order.setStatus(Status.SAVED);
		getSteelOrderDao().update(order);
	}

	@Transactional
	public List<SteelOrder> getAll() {
		return getSteelOrderDao().getMinimalOrder();
	}
	
	@Transactional
	public SteelOrder approveOrder(SteelOrder order){
	//	SteelOrder order = getEagerlyLoadedOrder(orderId);
		if(order.getStatus() != Status.SUBMITTED){
			throw new IllegalStateException("Illegal state for Order " + order.getStatus());
		}
		order.setStatus(Status.APPROVED);
		getSteelOrderDao().update(order);
		return order;
	}

	@Transactional
	public SteelOrder rejectOrder(SteelOrder order){
	//	SteelOrder order = getEagerlyLoadedOrder(orderId);
		if(order.getStatus() != Status.SUBMITTED){
			throw new IllegalStateException("Illegal state for Order " + order.getStatus());
		}
		order.setStatus(Status.REJECTED);
		getSteelOrderDao().update(order);
		return order;
	}
	
	@Transactional
	public SteelOrder getOrder(String orderId) {
		SteelOrder order = getEagerlyLoadedOrder(orderId);
		if(order.getStatus() == SteelOrder.Status.FHTV_SUBMITTED || order.getStatus() == SteelOrder.Status.FHTV_APPROVED || order.getStatus() == SteelOrder.Status.FHTV_REJECTED){
			Iterator<SteelVerificationCheck> itr = order.getVerificationCheck().iterator();
			while(itr.hasNext()){
				SteelVerificationCheck check = itr.next();
				if(check.getVerificationCheck().getType() == VerificationCheck.Type.BASIC){
					itr.remove();
				}
			}
			
			Iterator<Deviation> itrD = order.getDeviation().iterator();
			while(itrD.hasNext()){
				Deviation dev = itrD.next();
				if(dev.getType() == Deviation.Type.BASIC){
					itrD.remove();
				}
			}
		}
		return order;
	}

	private SteelOrder getEagerlyLoadedOrder(String orderId) {
		SteelOrder order = getSteelOrderDao().get(orderId);
		order.getPartManifacturingDetails().size();
		order.getVerificationCheck().size();
		order.getDeviation().size();
		return order;
	}

	public SupplierDao getSupplierDao() {
		return supplierDao;
	}

	public void setSupplierDao(SupplierDao dao) {
		this.supplierDao = dao;
	}

	public SteelVerificationCheckDao getSteelVerificationCheackDao() {
		return steelVerificationCheackDao;
	}

	public void setSteelVerificationCheackDao(
			SteelVerificationCheckDao steelVerificationCheackDao) {
		this.steelVerificationCheackDao = steelVerificationCheackDao;
	}
	
	public SteelOrderDao getSteelOrderDao() {
		return steelOrderDao;
	}

	public void setSteelOrderDao(SteelOrderDao steelOrderDao) {
		this.steelOrderDao = steelOrderDao;
	}
	
	private String generateOrderId(Supplier supplier){
		int min = 10000;
		int max = 1000000;
		long id= ORDER_ID_GENERATOR.nextInt((max-min)+1)+min;
		String strId = String.valueOf(id < 0 ? id * -1 : id);
		return strId+"-"+supplier.getCode()+"-"+supplier.getName();
	}
	
	private Supplier getSupplier(String name){
		return getSupplierDao().get(name);
	}

	public VerificationCheckDao getVerificationCheckDao() {
		return verificationCheckDao;
	}

	public void setVerificationCheckDao(VerificationCheckDao verificationCheckDao) {
		this.verificationCheckDao = verificationCheckDao;
	}

	public DeviationDao getDeviationDao() {
		return deviationDao;
	}

	public void setDeviationDao(DeviationDao deviationDao) {
		this.deviationDao = deviationDao;
	}

	public SteelHeatNoDao getSteelHeatNoDao() {
		return steelHeatNoDao;
	}

	public void setSteelHeatNoDao(SteelHeatNoDao steelHeatNoDao) {
		this.steelHeatNoDao = steelHeatNoDao;
	}

	@Transactional
	public List<SteelOrder> getOrderForStatus(String status){
		status = status.toUpperCase();
		Status st = Enum.valueOf(SteelOrder.Status.class, status);
		return getSteelOrderDao().getSteelOrderForStatus(st);
	}
}
