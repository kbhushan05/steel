package com.demo.steel.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.steel.domain.SteelOrder;
import com.demo.steel.dto.SteelOrderDto;
import com.demo.steel.service.MailNotificationService;
import com.demo.steel.service.SteelOrderService;

@Service
public class RestApiService {

	@Autowired
	private SteelOrderService steelOrderService;
	@Autowired
	private MailNotificationService mailService;
	
	public SteelOrderDto createNewOrder(String supplierName){
		
		SteelOrder order = getSteelOrderService().createNewOrder(supplierName);
		DtoDomainConvertor conv = new DtoDomainConvertor();
		SteelOrderDto orderDto = conv.createDto(order);
		return orderDto;
	}
	
	public void saveOrder(SteelOrderDto orderDto){
		DtoDomainConvertor conv = new DtoDomainConvertor();
		SteelOrder order = conv.createOrder(orderDto);
		getSteelOrderService().saveOrder(order);
	}
	
	public void submitOrder(SteelOrderDto orderDto){
		DtoDomainConvertor conv = new DtoDomainConvertor();
		SteelOrder order = conv.createOrder(orderDto);
		getSteelOrderService().submitOrder(order);
		getMailService().sendEmail("admin@company.com","Steel Order updates"," Steel Order " + order.getId()+" submitted.");
	}

	public SteelOrderService getSteelOrderService() {
		return steelOrderService;
	}

	public void setSteelOrderService(SteelOrderService steelOrderService) {
		this.steelOrderService = steelOrderService;
	}

	public void updateOrder(SteelOrderDto orderDto) {
		DtoDomainConvertor conv = new DtoDomainConvertor();
		SteelOrder order = conv.createOrder(orderDto);
		getSteelOrderService().updateOrder(order);
	}

	public List<SteelOrderDto> getAll() {
		List<SteelOrder> orders = getSteelOrderService().getAll();
		DtoDomainConvertor conv = new DtoDomainConvertor();
		return conv.createDtos(orders);
	}
	
	public SteelOrderDto approveOrder(long orderId){
		SteelOrder order = getSteelOrderService().approveOrder(orderId);
		DtoDomainConvertor conv = new DtoDomainConvertor();
		SteelOrderDto orderDto = conv.createDto(order);
		getMailService().sendEmail(order.getSupplier().getEmail(),"Steel Order updates"," Steel Order " + order.getId()+" approved.");
		return orderDto;
	}
	
	public SteelOrderDto rejectOrder(long orderId){
		SteelOrder order = getSteelOrderService().rejectOrder(orderId);
		DtoDomainConvertor conv = new DtoDomainConvertor();
		SteelOrderDto orderDto = conv.createDto(order);
		return orderDto;
	}
	
	public SteelOrderDto getOrder(long orderId){
		SteelOrder order = getSteelOrderService().getOrder(orderId);
		DtoDomainConvertor conv = new DtoDomainConvertor();
		SteelOrderDto orderDto = conv.createDto(order);
		return orderDto;
	}

	public MailNotificationService getMailService() {
		return mailService;
	}

	public void setMailService(MailNotificationService mailService) {
		this.mailService = mailService;
	}
	
}
