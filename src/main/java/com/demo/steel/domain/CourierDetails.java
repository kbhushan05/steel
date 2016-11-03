package com.demo.steel.domain;

import java.util.Date;

import javax.persistence.Embeddable;

@Embeddable
public class CourierDetails {

	private String courierCompany;
	private Date courierDeliveryDate;
	private String courierReceiptName;

	public String getCourierCompany() {
		return courierCompany;
	}
	public void setCourierCompany(String courierCompany) {
		this.courierCompany = courierCompany;
	}
	public Date getCourierDeliveryDate() {
		return courierDeliveryDate;
	}
	public void setCourierDeliveryDate(Date courierDeliveryDate) {
		this.courierDeliveryDate = courierDeliveryDate;
	}
	public String getCourierReceiptName() {
		return courierReceiptName;
	}
	public void setCourierReceiptName(String courierReceiptName) {
		this.courierReceiptName = courierReceiptName;
	}
	@Override
	public String toString() {
		return "CourierDetails [courierCompany=" + courierCompany
				+ ", courierDeliveryDate=" + courierDeliveryDate
				+ ", courierReceiptName=" + courierReceiptName + "]";
	}
}
