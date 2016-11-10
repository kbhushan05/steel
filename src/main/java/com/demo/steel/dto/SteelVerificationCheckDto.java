package com.demo.steel.dto;


public class SteelVerificationCheckDto{
	
	private int id;
	private int verificationCheckId;
	private String name;
	private String status;
	private String remark;
	private String attachmentName;
	private String testName;
	private String type;

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAttachmentName() {
		return attachmentName;
	}
	public int getVerificationCheckId() {
		return verificationCheckId;
	}
	public void setVerificationCheckId(int verificationCheckId) {
		this.verificationCheckId = verificationCheckId;
	}
	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
}