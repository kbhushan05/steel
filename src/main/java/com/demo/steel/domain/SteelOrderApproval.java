package com.demo.steel.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity(name="steelorderapproval")
public class SteelOrderApproval {

	@Id
	@GeneratedValue
	private int id;
	
	@Lob
	@Column(length = 11000000)
	private byte[] data;
	
	private String filename;
	private String mimeType;
	
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
	@Override
	public String toString() {
		return "SteelOrderApproval [id=" + id + ", filename=" + filename
				+ ", mimeType=" + mimeType + "]";
	}
	
}
