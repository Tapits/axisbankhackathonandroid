package com.reva.data;

public class PaymentGateWayData {
	private int id;
	private int activeFlag;
	private String commet;
	private String paymentGateImage;
	private String paymentGateName;
	private long timestamp;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(int activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String getCommet() {
		return commet;
	}

	public void setCommet(String commet) {
		this.commet = commet;
	}

	public String getPaymentGateImage() {
		return paymentGateImage;
	}

	public void setPaymentGateImage(String paymentGateImage) {
		this.paymentGateImage = paymentGateImage;
	}

	public String getPaymentGateName() {
		return paymentGateName;
	}

	public void setPaymentGateName(String paymentGateName) {
		this.paymentGateName = paymentGateName;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

}
