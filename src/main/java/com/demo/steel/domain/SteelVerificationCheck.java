package com.demo.steel.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity(name="steelverificationcheck")
public class SteelVerificationCheck {
	
	public enum Status{
		CHECKED, UNCHECKED;
	}
	
	@Id
	@GeneratedValue
	private int id;
	
	private String remark;
	@Enumerated(EnumType.STRING)
	private Status status = Status.UNCHECKED;
	private String filename;
	
	@ManyToOne
	private SteelOrder order;
	@ManyToOne
	private VerificationCheck verificationCheck;
	@Transient
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
	public SteelOrder getOrder() {
		return order;
	}
	public void setOrder(SteelOrder order) {
		this.order = order;
	}
	public int getId() {
		return id;
	}
	public void setId(int Id) {
		this.id = Id;
	}
	public VerificationCheck getVerificationCheck() {
		return verificationCheck;
	}
	public void setVerificationCheck(VerificationCheck verificationCheck) {
		this.verificationCheck = verificationCheck;
	}
	public Report getReport() {
		return report;
	}
	public void setReport(Report report) {
		this.report = report;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof SteelVerificationCheck) {
			SteelVerificationCheck that = (SteelVerificationCheck) obj;
			if (this.getVerificationCheck().getName()
					.equals(that.getVerificationCheck().getName())) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return this.getVerificationCheck().getName().hashCode();
	}
	
	@Override
	public String toString() {
		return "SteelVerificationCheck [primarykey=" + id + ", remark="
				+ remark + ", status=" + status + ", filename=" + filename
				+ "]";
	}
		
}
