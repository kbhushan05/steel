package com.demo.steel.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class PartNoDetails {
	
	@Id
	private int partNo;
	@ManyToOne
	private Supplier supplier;
	@OneToOne
	private SteelHeatNo steelHeatNo;
	
	public int getPartNo() {
		return partNo;
	}
	public void setPartNo(int partNo) {
		this.partNo = partNo;
	}
	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	
}
