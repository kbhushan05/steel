package com.demo.steel.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
	    uniqueConstraints=
	        @UniqueConstraint(columnNames={"order_id", "partDetails_partNo"})
	)
public class PartManifacturingDetails {
	
	public enum Status{
		CHECKED, UNCHECKED;
	}
	@Id
	@GeneratedValue
	private int primaryKey;
	@ManyToOne
	private PartNoDetails partDetails;
	@ManyToOne
	private SteelOrder order;
	private float partWeight;
	private float cutWeight;
	private int partNo;
	private Status status = Status.UNCHECKED;
	
	public int getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(int primaryKey) {
		this.primaryKey = primaryKey;
	}
	public PartNoDetails getPartDetails() {
		return partDetails;
	}
	public void setPartDetails(PartNoDetails partDetails) {
		this.partDetails = partDetails;
	}
	public SteelOrder getOrder() {
		return order;
	}
	public void setOrder(SteelOrder order) {
		this.order = order;
	}
	public float getPartWeight() {
		return partWeight;
	}
	public void setPartWeight(float partWeight) {
		this.partWeight = partWeight;
	}
	public float getCutWeight() {
		return cutWeight;
	}
	public void setCutWeight(float cutWeight) {
		this.cutWeight = cutWeight;
	}
	public int getPartNo() {
		return partNo;
	}
	public void setPartNo(int partNo) {
		this.partNo = partNo;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
}
