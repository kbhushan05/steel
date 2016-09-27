package com.demo.steel.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class SteelVerificationCheck{
	
	public enum Status{
		CHECKED, UNCHECKED;
	}
	
	@Id
	@GeneratedValue
	private int primarykey;
	
	private String remark;
	@Enumerated(EnumType.STRING)
	private Status status = Status.UNCHECKED;
	private String filename;
	@Lob
	private byte[] file;
	@ManyToOne
	private SteelOrder order;
	@ManyToOne
	private VerificationCheck verificationCheck;
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public byte[] getFile() {
		return file;
	}
	public void setFile(byte[] file) {
		this.file = file;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public SteelOrder getOrder() {
		return order;
	}
	public void setOrder(SteelOrder order) {
		this.order = order;
	}
	public int getPrimarykey() {
		return primarykey;
	}
	public void setPrimarykey(int primarykey) {
		this.primarykey = primarykey;
	}
	public VerificationCheck getVerificationCheck() {
		return verificationCheck;
	}
	public void setVerificationCheck(VerificationCheck verificationCheck) {
		this.verificationCheck = verificationCheck;
	}
	
}
