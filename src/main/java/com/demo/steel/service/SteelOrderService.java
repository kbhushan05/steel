package com.demo.steel.service;

import java.util.ArrayList;
import java.util.HashSet;
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
import com.demo.steel.domain.PartManifacturingDetails;
import com.demo.steel.domain.PartNoDetails;
import com.demo.steel.domain.SteelOrder;
import com.demo.steel.domain.SteelOrder.Status;
import com.demo.steel.domain.SteelVerificationCheck;
import com.demo.steel.domain.Supplier;
import com.demo.steel.domain.VerificationCheck;

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
	
	@Transactional
	public SteelOrder createNewOrder(String supplierName){
		Supplier supplier = getSupplier(supplierName);
		List<VerificationCheck> vcs = getVerificationCheckDao().getAll();
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
		return order;
	}
	
	@Transactional
	public void saveOrder(SteelOrder order){
		order.setStatus(SteelOrder.Status.SAVED);
		getSteelOrderDao().save(order);
	}
	
	@Transactional
	public void submitOrder(SteelOrder order){
		
		order.getPartManifacturingDetails().removeIf(
				item -> item.getStatus() != PartManifacturingDetails.Status.CHECKED
				);
		order.setStatus(SteelOrder.Status.SUBMITTED);
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
		long id= ORDER_ID_GENERATOR.nextLong();
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

	@Transactional
	public void submitFhtv(SteelOrder order) {
		order.setStatus(Status.FHTV_SUMBITTED);
		getSteelOrderDao().update(order);
	}
	
}
