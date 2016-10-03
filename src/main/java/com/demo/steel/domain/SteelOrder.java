package com.demo.steel.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class SteelOrder {
	
	public enum Status{
		NEW, SAVED, APPROVED, REJECTED, SUBMITTED, FHTV_SUMBITTED;
	}
	
	@Id
	private long id;
	@ManyToOne
	private SteelMill mill;
	@ManyToOne
	private Supplier supplier;
	private Date date;
	private float totalTonage;
	private String remark;
	private String cilStatus;
	private String cilRemark;
	private int poNumber;
	private String steelMill;
	private String comments;
	
	@Enumerated(EnumType.STRING)
	private Status status = Status.NEW;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="order",orphanRemoval=true)
	private Set<PartManifacturingDetails> partManifacturingDetails = new HashSet<>();
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="order")
	private Set<SteelVerificationCheck> verificationCheck = new HashSet<>();
	@OneToOne
	private Deviation deviation;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public SteelMill getMill() {
		return mill;
	}
	public void setMill(SteelMill mill) {
		this.mill = mill;
	}
	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public float getTotalTonage() {
		return totalTonage;
	}
	public void setTotalTonage(float totalTonage) {
		this.totalTonage = totalTonage;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCilStatus() {
		return cilStatus;
	}
	public void setCilStatus(String cilStatus) {
		this.cilStatus = cilStatus;
	}
	public String getCilRemark() {
		return cilRemark;
	}
	public void setCilRemark(String cilRemark) {
		this.cilRemark = cilRemark;
	}
	public int getPoNumber() {
		return poNumber;
	}
	public void setPoNumber(int poNumber) {
		this.poNumber = poNumber;
	}
	public Set<PartManifacturingDetails> getPartManifacturingDetails() {
		return Collections.unmodifiableSet(partManifacturingDetails);
	}
	public void setPartManifacturingDetails(
			Collection<PartManifacturingDetails> partManifacturingDetails) {
		this.partManifacturingDetails = new HashSet<>(partManifacturingDetails);
	}
	public Set<SteelVerificationCheck> getVerificationCheck() {
		return Collections.unmodifiableSet(verificationCheck);
	}
	public void setVerificationCheck(
			Collection<SteelVerificationCheck> verificationCheck) {
		this.verificationCheck = new HashSet<>(verificationCheck);
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getSteelMill() {
		return steelMill;
	}
	public void setSteelMill(String steelMill) {
		this.steelMill = steelMill;
	}
	public Deviation getDeviation() {
		return deviation;
	}
	public void setDeviation(Deviation deviation) {
		this.deviation = deviation;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
}
