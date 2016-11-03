package com.demo.steel.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CilDetails {

	private String cilStatus;
	@Column(length=1000)
	private String cilRemark;
	@Column(length=1000)
	private String cilComment;
	
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
	public String getCilComment() {
		return cilComment;
	}
	public void setCilComment(String cilComment) {
		this.cilComment = cilComment;
	}
	@Override
	public String toString() {
		return "CilDetails [cilStatus=" + cilStatus + ", cilRemark="
				+ cilRemark + ", cilComment=" + cilComment + "]";
	}
	
}
