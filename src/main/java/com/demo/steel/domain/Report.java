package com.demo.steel.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity(name="report")
public class Report {

	@Id
	@GeneratedValue
	private int id;
	private int steelVerificationCheckId;
	private String filename;
	private String mimeType;
	
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
	public int getSteelVerificationCheckId() {
		return steelVerificationCheckId;
	}
	public void setSteelVerificationCheckId(int steelVerificationCheckId) {
		this.steelVerificationCheckId = steelVerificationCheckId;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	
}
