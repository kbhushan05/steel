package com.demo.steel.api;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demo.steel.domain.SteelOrderApproval;
import com.demo.steel.dto.SteelOrderDto;
import com.demo.steel.util.StringUtil;

@RestController
@RequestMapping("/orders")
public class SteelOrderController {
	
	private static final Logger logger = LoggerFactory.getLogger(SteelOrderController.class);
	
	@Autowired
	private SteelOrderApiService service;
	
	@RequestMapping(method=RequestMethod.POST,consumes="application/json")
	@ResponseStatus(code = HttpStatus.CREATED)
	public SteelOrderDto saveOrder(@RequestBody(required = true)SteelOrderDto orderDto){
		logger.debug("received request to save order "+ orderDto.getOrderId());
		SteelOrderDto dto = service.saveOrder(orderDto);
		logger.debug("response generated successfully.");
		return dto;
	}
	
	@RequestMapping(method=RequestMethod.PUT,consumes="application/json")
	@ResponseStatus(code = HttpStatus.OK)
	public SteelOrderDto updateOrder(@RequestBody(required = true)SteelOrderDto orderDto){
		logger.debug("received request to update order "+ orderDto.getOrderId());
		SteelOrderDto dto = service.updateOrder(orderDto);
		logger.debug("response generated successfully.");
		return dto;
	}

	@RequestMapping(method=RequestMethod.GET,path="/{orderId}")
	public SteelOrderDto get(@PathVariable String orderId){
		logger.debug("received request to read order "+ orderId);
		SteelOrderDto dto = service.getOrder(orderId);
		logger.debug("response generated successfully");
		return dto;
	}

	@RequestMapping(method=RequestMethod.PUT,path="/{orderId}/{action}")
	public SteelOrderDto executeAction(@PathVariable String orderId,
			@PathVariable String action,
			@RequestBody SteelOrderDto orderDto) {
		logger.debug("received request to " + action + " order " + orderId);
		SteelOrderDto dto = null;
		switch (action) {
		case "submit":
			dto = service.submitOrder(orderDto);
			break;
		case "approve":
			dto = service.approveOrder(orderDto);
			break;
		case "reject":
			dto = service.rejectOrder(orderDto);
			break;
		default:
			throw new InvalidInputException(ErrorCode.INVALID_ACTION, "This action is not supported.");
		}
		logger.debug("response generated successfully for action "+ action);
		return dto;
	}
	
	@RequestMapping(method=RequestMethod.POST,consumes="application/json",path="/new")
	@ResponseStatus(code = HttpStatus.OK)
	public SteelOrderDto createNewOrder(@RequestParam String supplierName){
		logger.debug("received request to create new order.");
		SteelOrderDto dto = service.createNewOrder(supplierName);
		logger.debug("response generated successfully.");
		return dto;
	}

	@RequestMapping(method=RequestMethod.POST,consumes="application/json",path="/new/{orderId}")
	public SteelOrderDto createNewFhtOrder(@PathVariable String orderId){
		logger.debug("received request to create FHT order.");
		SteelOrderDto dto = service.createNewFhtOrder(orderId);
		logger.debug("response generated successfully.");
		return dto;
	}
	
	@RequestMapping(method=RequestMethod.GET,consumes="application/json")
	public List<SteelOrderDto> getAll(@RequestParam(required = false) String supplierName, @RequestParam(required = false) String state){
		logger.debug("received request to all orders for supplier "+ supplierName + " and status "+state);
		
		if(!StringUtil.isEmpty(supplierName)&& !StringUtil.isEmpty(state)){
			List<SteelOrderDto> dtos = service.getFilteredBy(supplierName, state);
			logger.debug("response generated for filter supplier and state.");
			return dtos;
		}
		
		if(!StringUtil.isEmpty(supplierName)){
			List<SteelOrderDto> dtos = service.getFilteredBySupplierName(supplierName);
			logger.debug("response generated for filter supplier name.");
			return dtos;
		}
		
		if(!StringUtil.isEmpty(state)){
			List<SteelOrderDto> dtos = service.getFilteredByStatus(state);
			logger.debug("response generated for filter state.");
			return dtos;
		}
		
		List<SteelOrderDto> dtos = service.getAll();
		logger.debug("response generated without filter.");
		return dtos;
	}
	
	@RequestMapping(method = RequestMethod.GET,params={"page","limit","supplierName"})
	public List<SteelOrderDto> getOrderPage(@RequestParam int page,@RequestParam("limit") int totalEntries,@RequestParam String supplierName){
		logger.debug("received pagination request for page " + page +" limit "+totalEntries);
		List<SteelOrderDto> dtos = service.getOrderPage(page, totalEntries,supplierName);
		logger.debug("response generated successfully, returning "+ dtos.size()+" entries.");
		return dtos;
	}

	@RequestMapping(method=RequestMethod.POST, consumes="multipart/form-data", path="/{orderId}/report")
	public void uploadFile(@PathVariable String orderId, @RequestPart MultipartFile mFile){
		logger.debug("received request to upload approval.");
		try {
			service.uploadApprovalReport(orderId, mFile.getOriginalFilename(),mFile.getContentType(),mFile.getBytes());
		} catch (IOException e) {
			throw new SystemException(ErrorCode.INTERNAL_ERROR,e.getMessage(), e);
		}
	}
	
	@RequestMapping(method=RequestMethod.GET,path="/{orderId}/report")
	public ResponseEntity<byte[]> downloadReport(@PathVariable String orderId) throws IOException{
		SteelOrderApproval approval = service.downloadApprovalReport(orderId);
		byte[] data = approval.getData();
		logger.debug("got report data.");
		return ResponseEntity.ok()
				.contentLength(data.length)
				.contentType(MediaType.valueOf(approval.getMimeType()))
				.header("Content-Disposition", "attachment; filename="+ approval.getFilename())
				.body(data);
	}

}
