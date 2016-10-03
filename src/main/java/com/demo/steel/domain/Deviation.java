package com.demo.steel.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Deviation {
	
	@Id
	private int cilDevitionNumber;
	private String requesterName;
	private Date requestDate;
	private String partDescription;
	private String rmSection;
	private String rmGarde;
	private float quantityForDeviation;
	private String delivaryAffected;
	private String description;
	private String attachmentName;
	@Lob
	private byte[] attachment;
	
	public int getCilDevitionNumber() {
		return cilDevitionNumber;
	}
	public void setCilDevitionNumber(int cilDevitionNumber) {
		this.cilDevitionNumber = cilDevitionNumber;
	}
	public String getRequesterName() {
		return requesterName;
	}
	public void setRequesterName(String requesterName) {
		this.requesterName = requesterName;
	}
	public Date getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public String getPartDescription() {
		return partDescription;
	}
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}
	public String getRmSection() {
		return rmSection;
	}
	public void setRmSection(String rmSection) {
		this.rmSection = rmSection;
	}
	public String getRmGarde() {
		return rmGarde;
	}
	public void setRmGarde(String rmGarde) {
		this.rmGarde = rmGarde;
	}
	public float getQuantityForDeviation() {
		return quantityForDeviation;
	}
	public void setQuantityForDeviation(float quantityForDeviation) {
		this.quantityForDeviation = quantityForDeviation;
	}
	public String getDelivaryAffected() {
		return delivaryAffected;
	}
	public void setDelivaryAffected(String delivaryAffected) {
		this.delivaryAffected = delivaryAffected;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAttachmentName() {
		return attachmentName;
	}
	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}
	public byte[] getAttachment() {
		return attachment;
	}
	public void setAttachment(byte[] attachment) {
		this.attachment = attachment;
	}

}
