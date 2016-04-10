package com.reva.data;

public class PayUData {
	private String payUURL;
	private String contentType;
	private String authorization;
	private PayURequestData payURequestData;

	public String getPayUURL() {
		return payUURL;
	}

	public void setPayUURL(String payUURL) {
		this.payUURL = payUURL;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getAuthorization() {
		return authorization;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}

	public PayURequestData getPayURequestData() {
		return payURequestData;
	}

	public void setPayURequestData(PayURequestData payURequestData) {
		this.payURequestData = payURequestData;
	}

}
