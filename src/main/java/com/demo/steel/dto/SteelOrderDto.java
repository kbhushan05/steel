package com.demo.steel.dto;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class SteelOrderDto {
	private final String DELIM_SUPPLIER_ID = "-";
	
	private long orderId;
	@SuppressWarnings("unused")
	private String supplierId;
	private String supplierName;
	private int supplierCode;
	private long poNumber;
	private String steelMill;
	private long steelHeatNumber;
	private float alreadyAvailableSteelTonage;
	private float newSteelToBuy;
	private float steelTonage;
	private String refStandard;
	private String forgerSupplierCode;
	private String status;
	private Date date;
	private String supplierEmail;
	
	private Set<SteelVerificationCheckDto> checkList = new HashSet<>(20);
	private Set<PartManifacturingDetailsDto> partDetails = new HashSet<>();
	
	public void addVerificationCheck(String req, String status, String remark, byte[] attachment, String attachmentName){
		SteelVerificationCheckDto check = new SteelVerificationCheckDto(req,status,remark,attachment,attachmentName);
		checkList.add(check);
	}
	public void addPartDetails(int number, float weight, float cutWeight, int noOfParts){
		partDetails.add(new PartManifacturingDetailsDto(number, weight, cutWeight, noOfParts));
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public String getSupplierId() {
		return String.join(DELIM_SUPPLIER_ID, String.valueOf(getSupplierCode()), getSupplierName());
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public long getPoNumber() {
		return poNumber;
	}
	public void setPoNumber(long poNumber) {
		this.poNumber = poNumber;
	}
	public String getSteelMill() {
		return steelMill;
	}
	public void setSteelMill(String steelMill) {
		this.steelMill = steelMill;
	}
	public long getSteelHeatNumber() {
		return steelHeatNumber;
	}
	public void setSteelHeatNumber(long steelHeatNumber) {
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
	public Set<SteelVerificationCheckDto> getCheckList() {
		return checkList;
	}
	public void setCheckList(Set<SteelVerificationCheckDto> checkList) {
		this.checkList = checkList;
	}
	public Set<PartManifacturingDetailsDto> getPartDetails() {
		return Collections.unmodifiableSet(partDetails);
	}
	public void setPartDetails(Set<PartManifacturingDetailsDto> partDetails) {
		this.partDetails = partDetails;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public int getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(int supplierCode) {
		this.supplierCode = supplierCode;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getSupplierEmail() {
		return supplierEmail;
	}
	public void setSupplierEmail(String supplierEmail) {
		this.supplierEmail = supplierEmail;
	}
	
}
