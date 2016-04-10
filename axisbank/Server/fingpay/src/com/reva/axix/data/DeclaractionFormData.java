package com.reva.axix.data;

public class DeclaractionFormData {
	private int id;
	private String data;
	private String remarks;
	private long timesatmp;
	private String merchantRegisrationRefNumber;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
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

	public String getMerchantRegisrationRefNumber() {
		return merchantRegisrationRefNumber;
	}

	public void setMerchantRegisrationRefNumber(
			String merchantRegisrationRefNumber) {
		this.merchantRegisrationRefNumber = merchantRegisrationRefNumber;
	}

}
