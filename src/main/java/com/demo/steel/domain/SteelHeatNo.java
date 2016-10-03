package com.demo.steel.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class SteelHeatNo {

	@Id
	private int heatNo;
	@ManyToOne(fetch=FetchType.LAZY)
	private Supplier supplier;
	
	public int getHeatNo() {
		return heatNo;
	}
	public void setHeatNo(int heatNo) {
		this.heatNo = heatNo;
	}
	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	
}
