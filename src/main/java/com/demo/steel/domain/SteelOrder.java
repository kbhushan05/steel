package com.demo.steel.domain;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity(name="steelorder")
public class SteelOrder {
	
	public enum State{
		NEW, SAVED, APPROVED, REJECTED, SUBMITTED;
	}
	public enum Type{
		BASIC, FHT;
	}
	
	@Id
	private String id;
	private Date date;
	private String remark;
	private int poNumber;
	private String comments;
	private String steelHeatNumber;
	private float alreadyAvailableSteelTonage;
	private float newSteelToBuy;
	private float steelTonage;
	private String refStandard;
	private String forgerSupplierCode;
	@Enumerated(EnumType.STRING)
	private Type type = Type.BASIC;
	@Enumerated(EnumType.STRING)
	private State state = State.NEW;
	@Embedded
	private CilDetails cilDetails;
	@Embedded
	private CourierDetails courierDetails;
	
	@ManyToOne
	private SteelMill mill;
	
	@ManyToOne
	private Supplier supplier;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="order",orphanRemoval=true)
	private Set<PartManifacturingDetail> partManifacturingDetails;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="order")
	private Set<SteelVerificationCheck> verificationChecks;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy="order")
	private Deviation deviation;
	
	@OneToOne(fetch = FetchType.LAZY)
	private SteelOrder parentOrder;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getPoNumber() {
		return poNumber;
	}
	public void setPoNumber(int poNumber) {
		this.poNumber = poNumber;
	}
	public State getState() {
		return state;
	}
	public void setStatus(State status) {
		this.state = status;
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
	public String getSteelHeatNumber() {
		return steelHeatNumber;
	}
	public void setSteelHeatNumber(String steelHeatNumber) {
		this.steelHeatNumber = steelHeatNumber;
	}
	public float getAlreadyAvailableSteelTonage() {
		return alreadyAvailableSteelTonage;
	}
	public void setAlreadyAvailableSteelTonage(float alreadyAvailableSteelTonage) {
		this.alreadyAvailableSteelTonage = alreadyAvailableSteelTonage;
	}
	public float getNewSteelToBuy() {
		return newSteelToBuy;
	}
	public void setNewSteelToBuy(float newSteelToBuy) {
		this.newSteelToBuy = newSteelToBuy;
	}
	public float getSteelTonage() {
		return steelTonage;
	}
	public void setSteelTonage(float steelTonage) {
		this.steelTonage = steelTonage;
	}
	public String getRefStandard() {
		return refStandard;
	}
	public void setRefStandard(String refStandard) {
		this.refStandard = refStandard;
	}
	public String getForgerSupplierCode() {
		return forgerSupplierCode;
	}
	public void setForgerSupplierCode(String forgerSupplierCode) {
		this.forgerSupplierCode = forgerSupplierCode;
	}
	public CilDetails getCilDetails() {
		return cilDetails;
	}
	public void setCilDetails(CilDetails cilDetails) {
		this.cilDetails = cilDetails;
	}
	public CourierDetails getCourierDetails() {
		return courierDetails;
	}
	public void setCourierDetails(CourierDetails courierDetails) {
		this.courierDetails = courierDetails;
	}
	public Set<PartManifacturingDetail> getPartManifacturingDetails() {
		return partManifacturingDetails;
	}
	public void setPartManifacturingDetails(
			Collection<PartManifacturingDetail> partManifacturingDetails) {
		this.partManifacturingDetails = new HashSet<>();
		this.partManifacturingDetails.addAll(partManifacturingDetails);
	}
	public void setPartManifacturingDetails(Set<PartManifacturingDetail> partManifacturingDetails){
		this.partManifacturingDetails = new HashSet<>(partManifacturingDetails);
	}
	public Set<SteelVerificationCheck> getVerificationChecks() {
		return verificationChecks;
	}
	public void setVerificationChecks(
			Collection<SteelVerificationCheck> verificationChecks) {
		this.verificationChecks = new HashSet<SteelVerificationCheck>();
		this.verificationChecks.addAll(verificationChecks);
	}
	public void setVerificationChecks(
			Set<SteelVerificationCheck> verificationChecks){
		this.verificationChecks = new HashSet<>(verificationChecks);
	}
	public SteelOrder getParentOrder() {
		return parentOrder;
	}
	public void setParentOrder(SteelOrder parentOrder) {
		this.parentOrder = parentOrder;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "SteelOrder [id=" + id + ", mill=" + mill + ", date=" + date
				+ ", remark=" + remark
				+ ", poNumber=" + poNumber + ", comments=" + comments
				+ ", steelHeatNumber=" + steelHeatNumber
				+ ", alreadyAvailableSteelTonage="
				+ alreadyAvailableSteelTonage + ", newSteelToBuy="
				+ newSteelToBuy + ", steelTonage=" + steelTonage
				+ ", refStandard=" + refStandard + ", forgerSupplierCode="
				+ forgerSupplierCode + ", status=" + state + "]";
	}
	
}
