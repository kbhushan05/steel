package com.demo.steel.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

@Entity(name="report")
public class Report {

	@Id
	@GeneratedValue
	private int id;
	@OneToOne(fetch=FetchType.LAZY)
	private SteelVerificationCheck steelVerificationCheck;
	
	@Lob
	private byte[] data;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public SteelVerificationCheck getSteelVerificationCheck() {
		return steelVerificationCheck;
	}
	public void setSteelVerificationCheck(
			SteelVerificationCheck steelVerificationCheck) {
		this.steelVerificationCheck = steelVerificationCheck;
	}
	
}
