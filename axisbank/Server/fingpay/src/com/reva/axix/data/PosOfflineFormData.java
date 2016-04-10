package com.reva.axix.data;

public class PosOfflineFormData {

	private int id;
	private String format;
	private String frequency;
	private String remarks;
	private String statement;
	private long timestamp;
	private String typeOfMaterial;
	private String typeOfPos;
	private String vas;
	private String merchantRegisrationId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getTypeOfMaterial() {
		return typeOfMaterial;
	}

	public void setTypeOfMaterial(String typeOfMaterial) {
		this.typeOfMaterial = typeOfMaterial;
	}

	public String getTypeOfPos() {
		return typeOfPos;
	}

	public void setTypeOfPos(String typeOfPos) {
		this.typeOfPos = typeOfPos;
	}

	public String getVas() {
		return vas;
	}

	public void setVas(String vas) {
		this.vas = vas;
	}

	public String getMerchantRegisrationId() {
		return merchantRegisrationId;
	}

	public void setMerchantRegisrationId(String merchantRegisrationId) {
		this.merchantRegisrationId = merchantRegisrationId;
	}
}
