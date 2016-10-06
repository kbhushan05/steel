package com.demo.steel.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.demo.steel.security.dao.DeviationDao;

@Service
public class SteelOrderService {
	
	private static final Random ORDER_ID_GENERATOR = new Random();
	private static final Random CIL_NUMBER_GENERATOR = new Random();
	
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
		order.setId(generateOrderId());
		order.setVerificationCheck(checkList);
		Set<PartManifacturingDetails> partManifacturingDetails = new HashSet<>();
		for(PartNoDetails part : supplier.getPartNos()){
			PartManifacturingDetails details = new PartManifacturingDetails();
			details.setPartDetails(part);
			partManifacturingDetails.add(details);
		}
		order.setPartManifacturingDetails(partManifacturingDetails);
		Deviation dev = new Deviation();
		dev.setCilDevitionNumber(generateCilNumber());
		order.setDeviation(dev);
		return order;
	}
	
	@Transactional
	public SteelOrder createNewFhtOrder(long orderId){
		
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
		return fhtOrder;
	}
	
	@Transactional
	public void saveOrder(SteelOrder order){
		order.setStatus(SteelOrder.Status.SAVED);
		getSteelOrderDao().save(order);
	}
	
	@Transactional
	public void submitOrder(SteelOrder order){
		order = getSteelOrderDao().reattachEntity(order);
		Iterator<PartManifacturingDetails> itrs = order.getPartManifacturingDetails().iterator();
		while(itrs.hasNext()){
			PartManifacturingDetails details = itrs.next();
			if(details.getStatus() == PartManifacturingDetails.Status.UNCHECKED){
				itrs.remove();
			}
		}
		order.setStatus(SteelOrder.Status.SUBMITTED);
		getSteelOrderDao().update(order);
	}
	
	@Transactional
	public void submitFhtOrder(SteelOrder order){
		if(order.getStatus() != SteelOrder.Status.APPROVED){
			throw new IllegalArgumentException("Invalid Order state.");
		}
		order.setStatus(Status.FHTV_SUBMITTED);
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
	public SteelOrder approveOrder(long orderId){
		SteelOrder order = getEagerlyLoadedOrder(orderId);
		if(order.getStatus() != Status.SUBMITTED){
			throw new IllegalStateException("Illegal state for Order " + order.getStatus());
		}
		order.setStatus(Status.APPROVED);
		getSteelOrderDao().update(order);
		return order;
	}

	@Transactional
	public SteelOrder rejectOrder(long orderId){
		SteelOrder order = getEagerlyLoadedOrder(orderId);
		if(order.getStatus() != Status.SUBMITTED){
			throw new IllegalStateException("Illegal state for Order " + order.getStatus());
		}
		order.setStatus(Status.REJECTED);
		getSteelOrderDao().update(order);
		return order;
	}
	
	@Transactional
	public SteelOrder getOrder(long orderId) {
		SteelOrder order = getEagerlyLoadedOrder(orderId);
		if(order.getStatus() == SteelOrder.Status.FHTV_SUBMITTED){
			Iterator<SteelVerificationCheck> itr = order.getVerificationCheck().iterator();
			while(itr.hasNext()){
				SteelVerificationCheck check = itr.next();
				if(check.getVerificationCheck().getType() == VerificationCheck.Type.BASIC){
					itr.remove();
				}
			}
		}
		return order;
	}

	private SteelOrder getEagerlyLoadedOrder(long orderId) {
		SteelOrder order = getSteelOrderDao().get(orderId);
		order.getPartManifacturingDetails().size();
		order.getVerificationCheck().size();
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
	
	private long generateOrderId(){
		int min = 10000;
		int max = 1000000;
		long id= ORDER_ID_GENERATOR.nextInt((max-min)+1)+min;
		return id < 0 ? id * -1 : id;
	}
	
	private int generateCilNumber(){
		int min = 10000;
		int max = 1000000;
		int id= CIL_NUMBER_GENERATOR.nextInt((max-min)+1)+min;
		return id < 0 ? id * -1 : id;
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

	@Transactional
	public List<SteelOrder> getOrderForStatus(String status){
		status = status.toUpperCase();
		Status st = Enum.valueOf(SteelOrder.Status.class, status);
		return getSteelOrderDao().getSteelOrderForStatus(st);
	}
}
