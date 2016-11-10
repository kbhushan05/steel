package com.demo.steel.domain;

import com.demo.steel.domain.SteelOrder.Type;

public class SteelOrderFactory {

	public static SteelOrder createBasicSteelOrder(){
		SteelOrder order = new SteelOrder();
		return order;
	}
	
	public static SteelOrder createFhtSteelOrder(SteelOrder parent){
		SteelOrder order = new SteelOrder();
		order.setType(Type.FHT);
		order.setParentOrder(parent);
		
		order.setPoNumber(parent.getPoNumber());
		order.setForgerSupplierCode(parent.getForgerSupplierCode());
		order.setAlreadyAvailableSteelTonage(parent.getAlreadyAvailableSteelTonage());
		order.setNewSteelToBuy(parent.getNewSteelToBuy());
		order.setSteelTonage(parent.getSteelTonage());
		order.setSteelHeatNumber(parent.getSteelHeatNumber());
		
		order.setPartManifacturingDetails(parent.getPartManifacturingDetails());
		
		order.setSupplier(parent.getSupplier());
		order.setMill(parent.getMill());
		
		return order;
	}
}
