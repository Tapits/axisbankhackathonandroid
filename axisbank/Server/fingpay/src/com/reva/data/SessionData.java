package com.reva.data;

public class SessionData {
	private String sessionId;
	private int id;
	private int activeFlag;
	private String merchantAddress1;
	private String merchantAddress2;
	private String merchantAddress3;
	private String merchantLogo;
	private String merchantName;
	private int merchantPin;
	private String merchantId;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

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

	public String getMerchantAddress1() {
		return merchantAddress1;
	}

	public void setMerchantAddress1(String merchantAddress1) {
		this.merchantAddress1 = merchantAddress1;
	}

	public String getMerchantAddress2() {
		return merchantAddress2;
	}

	public void setMerchantAddress2(String merchantAddress2) {
		this.merchantAddress2 = merchantAddress2;
	}

	public String getMerchantAddress3() {
		return merchantAddress3;
	}

	public void setMerchantAddress3(String merchantAddress3) {
		this.merchantAddress3 = merchantAddress3;
	}

	public String getMerchantLogo() {
		return merchantLogo;
	}

	public void setMerchantLogo(String merchantLogo) {
		this.merchantLogo = merchantLogo;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public int getMerchantPin() {
		return merchantPin;
	}

	public void setMerchantPin(int merchantPin) {
		this.merchantPin = merchantPin;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
}
