package com.demo.steel.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.demo.steel.dto.SteelOrderDto;
import com.demo.steel.util.StringUtil;

@RestController
@RequestMapping("/orders")
public class OrderController {
	@Autowired
	private SteelOrderApiService service;
	
	@RequestMapping(method=RequestMethod.POST,consumes="application/json")
	@ResponseStatus(code = HttpStatus.CREATED)
	public SteelOrderDto saveOrder(@RequestBody(required = true)SteelOrderDto orderDto){
		if(orderDto.getStatus().equals("SUBMITTED")){
			return getService().submitOrder(orderDto);
		}else{
			return getService().saveOrder(orderDto);
		}
	}
	
	@RequestMapping(method=RequestMethod.PUT,consumes="application/json")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void updateOrder(@RequestBody(required = true)SteelOrderDto orderDto){
		getService().updateOrder(orderDto);
	}

	@RequestMapping(method=RequestMethod.GET,produces="application/json",path="/new")
	@ResponseStatus(code = HttpStatus.OK)
	public SteelOrderDto createNewOrder(@RequestParam String supplierName){
		return getService().createNewOrder(supplierName);
	}
	
	@RequestMapping(method=RequestMethod.GET,produces="application/json")
	public List<SteelOrderDto> getAll(@RequestParam(required = false) String supplierName, @RequestParam(required = false) String status){
		String statusArray[] = new String[0];
		List<SteelOrderDto> statusFilter = new ArrayList<>();
		if(!StringUtil.isEmpty(status)){
			statusArray = status.split(",");
		}
		
		List<SteelOrderDto> all = getService().getAll();
		
		if(!StringUtil.isEmpty(supplierName)&& ! StringUtil.isEmpty(status)){
			List<SteelOrderDto> onlySupplier = filterBySupplierName(all, supplierName);
			for(String s : statusArray){
				statusFilter.addAll(filterByStatus(onlySupplier, s));
			}
			return statusFilter;
		}
		
		if(!StringUtil.isEmpty(supplierName)){
			return filterBySupplierName(all, supplierName);
		}
		
		if(!StringUtil.isEmpty(status)){
			for(String s : statusArray){
				statusFilter.addAll(filterByStatus(all, s));
			}
			return statusFilter;
		}
		
		return all;
	}
	
	private List<SteelOrderDto> filterBySupplierName(List<SteelOrderDto> list, String supplierName){
		List<SteelOrderDto> filtered = new ArrayList<>();
		for(SteelOrderDto dto : list){
			if(dto.getSupplierName().equals(supplierName)){
				filtered.add(dto);
			}
		}
		return filtered;
	}
	
	private List<SteelOrderDto> filterByStatus(List<SteelOrderDto> list, String status){
		List<SteelOrderDto> filtered = new ArrayList<>();
		for(SteelOrderDto dto : list){
			if(dto.getStatus().equalsIgnoreCase(status)){
				filtered.add(dto);
			}
		}
		return filtered;
	}
	
	@RequestMapping(method=RequestMethod.PUT,path="/{orderId}")
	public SteelOrderDto executeAction(@PathVariable String orderId, @RequestParam(required =false) String action,@RequestBody SteelOrderDto orderDto){
		
		switch (action) {
		case "approve":
			orderDto = getService().approveOrder(orderDto);
			break;
		case "reject":
			orderDto = getService().rejectOrder(orderDto);
			break;
		default:
			break;
		}
		return orderDto;
	}
	
	@RequestMapping(method=RequestMethod.GET, path="/{orderId}/fht")
	public SteelOrderDto createNewFhtOrder(@PathVariable String orderId){
		return getService().createNewFhtOrder(orderId);
	}
	
	@RequestMapping(method=RequestMethod.POST, path="/{orderId}/fht")
	public SteelOrderDto submitFhtOrder(@PathVariable String orderId, @RequestBody SteelOrderDto orderDto){
		return getService().submitFhtOrder(orderDto);
	}
	
	@RequestMapping(method=RequestMethod.PUT, path="/{orderId}/fht/approve")
	public void approveFhtOrder(@PathVariable String orderId, @RequestBody SteelOrderDto orderDto){
		getService().approveFhtOrder(orderDto);
	}
	
	@RequestMapping(method=RequestMethod.PUT, path="/{orderId}/fht/reject")
	public void rejectFhtOrder(@PathVariable String orderId, @RequestBody SteelOrderDto orderDto){
		getService().rejectFhtOrder(orderDto);
	}
	
	@RequestMapping(method=RequestMethod.GET,path="/{orderId}")
	public SteelOrderDto get(@PathVariable String orderId){
			return getService().getOrder(orderId);
	}
	
	public SteelOrderApiService getService() {
		return service;
	}

	public void setService(SteelOrderApiService service) {
		this.service = service;
	}

}
