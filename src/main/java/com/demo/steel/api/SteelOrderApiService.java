package com.demo.steel.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.steel.domain.SteelMill;
import com.demo.steel.domain.SteelOrder;
import com.demo.steel.domain.SteelOrder.Status;
import com.demo.steel.domain.SteelOrderApproval;
import com.demo.steel.dto.SteelOrderDto;
import com.demo.steel.service.MailNotificationService;
import com.demo.steel.service.SteelMillService;
import com.demo.steel.service.SteelOrderApprovalService;
import com.demo.steel.service.SteelOrderService;

@Service
public class SteelOrderApiService {

	@Autowired
	private SteelOrderService steelOrderService;
	@Autowired
	private MailNotificationService mailService;
	@Autowired
	private SteelMillService steelMillService;
	@Autowired
	private SteelOrderApprovalService steelOrderApprovalService;
	
	public SteelOrderDto createNewOrder(String supplierName){
		
		SteelOrder order = getSteelOrderService().createNewOrder(supplierName);
		DtoDomainConvertor conv = new DtoDomainConvertor();
		SteelOrderDto orderDto = conv.createDto(order);
		orderDto.setSteelMills(getSteelMills());
		return orderDto;
	}
	
	public SteelOrderDto createNewFhtOrder(String orderId){
		
		SteelOrder order = getSteelOrderService().createNewFhtOrder(orderId);
		order.setStatus(Status.FHTV_NEW);
		order.setComments("");
		order.setCilComment("");
		DtoDomainConvertor conv = new DtoDomainConvertor();
		SteelOrderDto orderDto = conv.createDto(order);
		return orderDto;
	}
	
	public SteelOrderDto submitFhtOrder(SteelOrderDto orderDto){
		DtoDomainConvertor conv = new DtoDomainConvertor();
		SteelOrder order = conv.createOrder(orderDto);
		SteelOrder submitted = getSteelOrderService().submitFhtOrder(order);
		SteelOrderDto dto = conv.createDto(submitted);
		getMailService().sendEmail("admncil@gmail.com","Steel FHT Order updates"," Steel Order " + order.getId()+" submitted FHT by Supplier "+ order.getSupplier().getName());
		return dto;
	}
	
	public void approveFhtOrder(SteelOrderDto orderDto){
		DtoDomainConvertor conv = new DtoDomainConvertor();
		SteelOrder order = conv.createOrder(orderDto);
		getSteelOrderService().approveFhtvOrder(order);
		getMailService().sendEmail(order.getSupplier().getEmail(),"Steel FHT Order updates"," Steel Order " + order.getId()+" FH Treatement approved.");
	}
	
	public void rejectFhtOrder(SteelOrderDto orderDto){
		DtoDomainConvertor conv = new DtoDomainConvertor();
		SteelOrder order = conv.createOrder(orderDto);
		getSteelOrderService().rejectFhtvOrder(order);
		getMailService().sendEmail(order.getSupplier().getEmail(),"Steel FHT Order updates"," Steel Order " + order.getId()+" FH Treatment rejected.");
	}

	private String[] getSteelMills() {
		List<SteelMill> mills = steelMillService.getAll();
		String[] millsArry = new String[mills.size()];
		int i =0;
		for(SteelMill mill : mills){
			millsArry[i] = mill.getName();
			i++;
		}
		return millsArry;
	}
	
	public SteelOrderDto saveOrder(SteelOrderDto orderDto){
		DtoDomainConvertor conv = new DtoDomainConvertor();
		SteelOrder order = conv.createOrder(orderDto);
		SteelOrder savedOrder = getSteelOrderService().saveOrder(order);
		return conv.createDto(savedOrder);
	}
	
	public SteelOrderDto submitOrder(SteelOrderDto orderDto){
		DtoDomainConvertor conv = new DtoDomainConvertor();
		SteelOrder order = conv.createOrder(orderDto);
		SteelOrder submittedOrder = getSteelOrderService().submitOrder(order);
		getMailService().sendEmail("admncil@gmail.com","Steel Order updates"," Steel Order " + order.getId()+" submitted by Supplier "+ order.getSupplier().getName());
		return conv.createDto(submittedOrder);
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
	
	public SteelOrderDto approveOrder(SteelOrderDto orderDto){
		DtoDomainConvertor conv = new DtoDomainConvertor();
		SteelOrder order = conv.createOrder(orderDto);
		SteelOrder approvedOrder = getSteelOrderService().approveOrder(order);
		SteelOrderDto approveOrderDto = conv.createDto(approvedOrder);
		getMailService().sendEmail(approvedOrder.getSupplier().getEmail(),"Steel Order updates"," Steel Order " + approvedOrder.getId()+" approved.");
		return approveOrderDto;
	}
	
	public SteelOrderDto rejectOrder(SteelOrderDto orderDto){
		DtoDomainConvertor conv = new DtoDomainConvertor();
		SteelOrder order = conv.createOrder(orderDto);
		SteelOrder rejectedOrder = getSteelOrderService().rejectOrder(order);
		SteelOrderDto rejectedOrderDto = conv.createDto(rejectedOrder);
		return rejectedOrderDto;
	}
	
	public SteelOrderDto getOrder(String orderId){
		SteelOrder order = getSteelOrderService().getOrder(orderId);
		DtoDomainConvertor conv = new DtoDomainConvertor();
		SteelOrderDto orderDto = conv.createDto(order);
		orderDto.setSteelMills(getSteelMills());
		return orderDto;
	}

	public void uploadReport(String orderId, String filename, String mimeType, byte[] data){
		steelOrderApprovalService.uploadReport(orderId, filename, data, mimeType);
	}
	
	public SteelOrderApproval download(String orderId){
		return steelOrderApprovalService.downloadReport(orderId);
	}
	public MailNotificationService getMailService() {
		return mailService;
	}

	public void setMailService(MailNotificationService mailService) {
		this.mailService = mailService;
	}

	public List<SteelOrderDto> getOrdersFromStatus(String status){
		DtoDomainConvertor convertor = new DtoDomainConvertor();
		List<SteelOrderDto> dtos = convertor.createDtos(getSteelOrderService().getOrderForStatus(status));
		return dtos;
	}
	
	public SteelMillService getSteelMillService() {
		return steelMillService;
	}

	public void setSteelMillService(SteelMillService steelMillService) {
		this.steelMillService = steelMillService;
	}
	
}
