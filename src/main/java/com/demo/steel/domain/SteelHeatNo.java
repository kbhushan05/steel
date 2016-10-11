package com.demo.steel.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SteelHeatNo {

	@Id
	private String heatNo;
	private float tonnage;
	
	public String getHeatNo() {
		return heatNo;
	}
	public void setHeatNo(String heatNo) {
		this.heatNo = heatNo;
	}
	public float getTonnage() {
		return tonnage;
	}
	public void setTonnage(float tonnage) {
		this.tonnage = tonnage;
	}
	
}
