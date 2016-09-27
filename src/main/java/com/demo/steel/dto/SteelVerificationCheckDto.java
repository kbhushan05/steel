package com.demo.steel.dto;

public class SteelVerificationCheckDto{
	
	private int id;
	private String name;
	private String status;
	private String remark;
	private byte[] attachment;
	private String attachmentName;
	
	public SteelVerificationCheckDto(){
		
	}
	
	public SteelVerificationCheckDto(String requirement, String status,
			String remark, byte[] attachment, String attachmentName) {
		super();
		this.name = requirement;
		this.status = status;
		this.remark = remark;
		this.attachment = attachment;
		this.attachmentName = attachmentName;
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

	public byte[] getAttachment() {
		return attachment;
	}

	public void setAttachment(byte[] attachment) {
		this.attachment = attachment;
	}

	public String getAttachmentName() {
		return attachmentName;
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

}