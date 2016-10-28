package com.demo.steel.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity(name="steelverificationcheck")
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
	private String mimeType;
	@ManyToOne
	private SteelOrder order;
	@ManyToOne
	private VerificationCheck verificationCheck;
	
	@OneToOne(mappedBy="steelVerificationCheck",fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	private Report report;
	
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
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public Report getReport() {
		return report;
	}
	public void setReport(Report report) {
		this.report = report;
	}
	@Override
	public String toString() {
		return "SteelVerificationCheck [primarykey=" + primarykey + ", remark="
				+ remark + ", status=" + status + ", filename=" + filename
				+ ", mimeType=" + mimeType + "]";
	}
		
}
