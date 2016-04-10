package com.reva.axix.data;

public class SettlementFormData {

	private int id;
	private String currentAccountNumber;
	private String mode;
	private String remarks;
	private long timesatmp;
	private int validationFlag;
	private String merchantRegisrationId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCurrentAccountNumber() {
		return currentAccountNumber;
	}

	public void setCurrentAccountNumber(String currentAccountNumber) {
		this.currentAccountNumber = currentAccountNumber;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public long getTimesatmp() {
		return timesatmp;
	}

	public void setTimesatmp(long timesatmp) {
		this.timesatmp = timesatmp;
	}

	public int getValidationFlag() {
		return validationFlag;
	}

	public void setValidationFlag(int validationFlag) {
		this.validationFlag = validationFlag;
	}

	public String getMerchantRegisrationId() {
		return merchantRegisrationId;
	}

	public void setMerchantRegisrationId(String merchantRegisrationId) {
		this.merchantRegisrationId = merchantRegisrationId;
	}
}
