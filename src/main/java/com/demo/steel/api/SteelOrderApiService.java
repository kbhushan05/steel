package com.demo.steel.api;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.steel.domain.SteelMill;
import com.demo.steel.domain.SteelOrder;
import com.demo.steel.domain.SteelOrderApproval;
import com.demo.steel.domain.Supplier;
import com.demo.steel.dto.SteelOrderDto;
import com.demo.steel.service.MailNotificationService;
import com.demo.steel.service.SteelMillService;
import com.demo.steel.service.SteelOrderApprovalService;
import com.demo.steel.service.SteelOrderService;
import com.demo.steel.service.SupplierService;
import com.demo.steel.util.StringUtil;

@Service
public class SteelOrderApiService {

	private static final Logger logger = LoggerFactory.getLogger(SteelOrderApiService.class);
	private DtoDomainConvertor conv = new DtoDomainConvertor();
	@Autowired
	private SteelOrderService steelOrderService;
	@Autowired
	private MailNotificationService mailService;
	@Autowired
	private SteelMillService steelMillService;
	@Autowired
	private SteelOrderApprovalService steelOrderApprovalService;
	@Autowired
	private SupplierService supplierService;
	
	public SteelOrderDto createNewOrder(String supplierName){
		logger.debug("create new order workflow started.");
		SteelOrder order = steelOrderService.createNewOrder(supplierName);
		logger.debug("new order created.");
		SteelOrderDto orderDto = conv.convertFrom(order);
		orderDto.setSteelMills(getSteelMills());
		logger.debug("new order workflow successful.");
		return orderDto;
	}
	
	public SteelOrderDto createNewFhtOrder(String orderId){
		logger.debug("create new FHT order workflow started.");
		SteelOrder order = steelOrderService.createNewOrderFrom(orderId);
		logger.debug("new FHT order created.");
		SteelOrderDto orderDto = conv.convertFrom(order);
		logger.debug("new FHT order workflow successful.");
		return orderDto;
	}

	public SteelOrderDto saveOrder(SteelOrderDto orderDto){
		logger.debug("save order workflow started.");
		SteelOrder order = conv.convertFrom(orderDto);
		SteelOrder savedOrder = steelOrderService.saveOrder(order);
		logger.debug( order.getId()+" order saved.");
		SteelOrderDto dto = conv.convertFrom(savedOrder);
		logger.debug("save order workflow successful.");
		return dto;
	}

	public SteelOrderDto updateOrder(SteelOrderDto orderDto) {
		logger.debug("update order workflow started.");
		SteelOrder order = conv.convertFrom(orderDto);
		order = steelOrderService.updateOrder(order);
		logger.debug( order.getId()+" order updated.");
		logger.debug("update order workflow successful.");
		return conv.convertFrom(order);
	}

	public SteelOrderDto submitOrder(SteelOrderDto orderDto){
		logger.debug("submit order workflow started.");
		SteelOrder order = conv.convertFrom(orderDto);
		SteelOrder submittedOrder = steelOrderService.submitOrder(order);
		logger.debug( order.getId()+" order submitted.");
		mailService.sendEmail("admncil@gmail.com","Steel Order updates"," Steel Order " + order.getId()+" submitted by Supplier "+ order.getSupplier().getName());
		SteelOrderDto dto = conv.convertFrom(submittedOrder);
		logger.debug("submit order workflow successful.");
		return dto;
	}

	public SteelOrderDto approveOrder(SteelOrderDto orderDto){
		logger.debug("approve order workflow started.");
		SteelOrder order = conv.convertFrom(orderDto);
		SteelOrder approvedOrder = steelOrderService.approveOrder(order);
		logger.debug( order.getId()+" order approved.");
		SteelOrderDto approveOrderDto = conv.convertFrom(approvedOrder);
		mailService.sendEmail(approvedOrder.getSupplier().getEmail(),"Steel Order updates"," Steel Order " + approvedOrder.getId()+" approved.");
		logger.debug("approve order workflow successful.");
		return approveOrderDto;
	}

	public SteelOrderDto rejectOrder(SteelOrderDto orderDto){
		logger.debug("reject order workflow started.");
		SteelOrder order = conv.convertFrom(orderDto);
		SteelOrder rejectedOrder = steelOrderService.rejectOrder(order);
		logger.debug( order.getId()+" order rejected.");
		SteelOrderDto rejectedOrderDto = conv.convertFrom(rejectedOrder);
		mailService.sendEmail(rejectedOrder.getSupplier().getEmail(),"Steel Order updates"," Steel Order " + rejectedOrder.getId()+" rejected.");
		logger.debug("reject order workflow successful.");
		return rejectedOrderDto;
	}

	public List<SteelOrderDto> getAll() {
		List<SteelOrder> orders = steelOrderService.getAll();
		return conv.convertSteelOrders(orders);
	}
	
	public List<SteelOrderDto> getFilteredBySupplierName(String supplierName){
		logger.debug("filtering order by supplier "+ supplierName);
		if(StringUtil.isEmpty(supplierName)){
			logger.debug("supplier name is empty, returning empty list.");
			return Collections.emptyList();
		}
		Supplier supplier = supplierService.getSupplier(supplierName);
		if(supplier == null){
			throw new InvalidInputException(ErrorCode.SUPPLIER_NOT_FOUND, "Supplier doesnot exists.");
		}
		logger.debug("supplier found.");
		List<SteelOrder> orders = steelOrderService.filterBy(supplier);
		logger.debug(orders.size()+" orders found.");
		List<SteelOrderDto> dtos = conv.convertSteelOrders(orders);
		return dtos;
	}
	
	public List<SteelOrderDto> getFilteredByStatus(String state){
		logger.debug("filtering order by status "+ state);
		if(StringUtil.isEmpty(state)){
			logger.debug("status is empty, returning empty list");
			Collections.emptyList();
		}
		SteelOrder.State orderState = Enum.valueOf(SteelOrder.State.class, state.toUpperCase());
		List<SteelOrder> orders = steelOrderService.filterBy(orderState);
		logger.debug(orders.size()+" orders found.");
		List<SteelOrderDto> dtos = conv.convertSteelOrders(orders);
		return dtos;
	}
	
	public List<SteelOrderDto> getFilteredBy(String supplierName, String state){
		logger.debug("filtering order by supplier and status "+ supplierName +", "+ state);
		if(StringUtil.isEmpty(supplierName)|| StringUtil.isEmpty(state)){
			logger.debug("supplier name or status is empty, returning empty list.");
			return Collections.emptyList();
		}
		Supplier supplier = supplierService.getSupplier(supplierName);
		if(supplier == null){
			throw new InvalidInputException(ErrorCode.SUPPLIER_NOT_FOUND, "Supplier doesnot exists.");
		}
		SteelOrder.State orderState = Enum.valueOf(SteelOrder.State.class, state.toUpperCase());
		List<SteelOrder> orders = steelOrderService.filterBy(supplier,orderState);
		logger.debug(orders.size()+" orders found.");
		List<SteelOrderDto> dtos = conv.convertSteelOrders(orders);
		return dtos;
	}
	
	public SteelOrderDto getOrder(String orderId){
		SteelOrder order = steelOrderService.getOrder(orderId);
		SteelOrderDto orderDto = conv.convertFrom(order);
		orderDto.setSteelMills(getSteelMills());
		logger.debug("read order workflow successful.");
		return orderDto;
	}

	public void uploadApprovalReport(String orderId, String filename, String mimeType, byte[] data){
		steelOrderApprovalService.uploadReport(orderId, filename, data, mimeType);
	}
	
	public SteelOrderApproval downloadApprovalReport(String orderId){
		return steelOrderApprovalService.downloadReport(orderId);
	}

	public List<SteelOrderDto> getOrderPage(int page, int totalEntriesPerPage, String supplierName) {
		logger.debug("starting pagination workflow.");
		Supplier supplier = supplierService.getSupplier(supplierName);
		if(supplier == null){
			throw new InvalidInputException(ErrorCode.SUPPLIER_NOT_FOUND, "supplier does not exists.");
		}
		
		if(page < 0 || totalEntriesPerPage < 0){
			throw new InvalidInputException(ErrorCode.INVALID_PAGE, "invalid input passed for pagination.");
		}
		List<SteelOrder> orders = steelOrderService.getOrderPage(page,totalEntriesPerPage, supplier);
		List<SteelOrderDto> dtos = conv.convertSteelOrders(orders);
		logger.debug("returning "+dtos.size()+" orders.");
		return dtos;
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
	
}
