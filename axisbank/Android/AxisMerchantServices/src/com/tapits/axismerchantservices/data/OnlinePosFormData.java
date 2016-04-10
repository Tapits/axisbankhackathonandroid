package com.tapits.axismerchantservices.data;

public class OnlinePosFormData {

	private int id;
	private String card;
	private String format;
	private String frequency;
	private String interfaceLanguage;
	private String paymentUrl;
	private String reamarks;
	private long timestamp;
	private String vas;
	private String wibsiteUrl;
	private String merchantRegisrationId;
	private int urlExitFlag;
	private String betaURL;
	private String document;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
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

	public String getInterfaceLanguage() {
		return interfaceLanguage;
	}

	public void setInterfaceLanguage(String interfaceLanguage) {
		this.interfaceLanguage = interfaceLanguage;
	}

	public String getPaymentUrl() {
		return paymentUrl;
	}

	public void setPaymentUrl(String paymentUrl) {
		this.paymentUrl = paymentUrl;
	}

	public String getReamarks() {
		return reamarks;
	}

	public void setReamarks(String reamarks) {
		this.reamarks = reamarks;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getVas() {
		return vas;
	}

	public void setVas(String vas) {
		this.vas = vas;
	}

	public String getWibsiteUrl() {
		return wibsiteUrl;
	}

	public void setWibsiteUrl(String wibsiteUrl) {
		this.wibsiteUrl = wibsiteUrl;
	}

	public String getMerchantRegisrationId() {
		return merchantRegisrationId;
	}

	public void setMerchantRegisrationId(String merchantRegisrationId) {
		this.merchantRegisrationId = merchantRegisrationId;
	}

	public int getUrlExitFlag() {
		return urlExitFlag;
	}

	public void setUrlExitFlag(int urlExitFlag) {
		this.urlExitFlag = urlExitFlag;
	}

	public String getBetaURL() {
		return betaURL;
	}

	public void setBetaURL(String betaURL) {
		this.betaURL = betaURL;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	@Override
	public String toString() {
		return "OnlinePosFormData [id=" + id + ", card=" + card + ", format="
				+ format + ", frequency=" + frequency + ", interfaceLanguage="
				+ interfaceLanguage + ", paymentUrl=" + paymentUrl
				+ ", reamarks=" + reamarks + ", timestamp=" + timestamp
				+ ", vas=" + vas + ", wibsiteUrl=" + wibsiteUrl
				+ ", merchantRegisrationId=" + merchantRegisrationId
				+ ", urlExitFlag=" + urlExitFlag + ", betaURL=" + betaURL
				+ ", document=" + document + "]";
	}

}
