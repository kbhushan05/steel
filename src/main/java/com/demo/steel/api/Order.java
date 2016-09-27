package com.demo.steel.api;

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

@RestController
@RequestMapping("/orders")
public class Order {
	@Autowired
	private RestApiService service;
	
	@RequestMapping(method=RequestMethod.POST,consumes="application/json")
	@ResponseStatus(code = HttpStatus.CREATED)
	public void saveOrder(@RequestBody(required = true)SteelOrderDto orderDto){
		if(orderDto.getStatus().equals("SUBMITTED")){
			getService().submitOrder(orderDto);
		}else{
			getService().saveOrder(orderDto);
		}
	}
	
	@RequestMapping(method=RequestMethod.PUT,consumes="application/json")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void updateOrder(@RequestBody(required = true)SteelOrderDto orderDto){
		getService().updateOrder(orderDto);
	}

	@RequestMapping(method=RequestMethod.GET,path="/new",produces="application/json")
	@ResponseStatus(code = HttpStatus.OK)
	public SteelOrderDto createNewOrder(){
		return getService().createNewOrder("Bhushan");
	}
	
	@RequestMapping(method=RequestMethod.GET,produces="application/json")
	public List<SteelOrderDto> getAll(){
		return getService().getAll();
	}
	
	@RequestMapping(path="/{orderId}", method=RequestMethod.PUT)
	public SteelOrderDto executeAction(@PathVariable long orderId, @RequestParam(required =false) String action){
		SteelOrderDto orderDto = null;
		
		switch (action) {
		case "approve":
			orderDto = getService().approveOrder(orderId);
			break;
		case "reject":
			orderDto = getService().rejectOrder(orderId);
			break;
		default:
			break;
		}
		
		return orderDto;
	}
	
	@RequestMapping(path="/{orderId}", method=RequestMethod.GET)
	public SteelOrderDto get(@PathVariable long orderId){
			return getService().getOrder(orderId);
	}
	
	public RestApiService getService() {
		return service;
	}

	public void setService(RestApiService service) {
		this.service = service;
	}

}
