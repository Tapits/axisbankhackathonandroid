package com.reva.axix.data;

public class MerchantRegisrationIdData {

	private int id;
	private String merchantName;
	private String merchantPhoneNumber;
	private String emailId;
	private int domainNameValidation;
	private String merchantRegisrationRefNumber;
	private int aadharValidation;
	private int panValidationStatus;
	private String registrationType;
	private String remarks;
	private int securityCheck;
	private int status;
	private int tanValidationStatus;
	private long timestamp;
	private long updatedTimestamp;
	private int keywordsVerification;
	private int highandlowriskbusinesscheck;
	private int socialCheck;

	public int getWebsiteVerifications() {
		return websiteVerifications;
	}

	public void setWebsiteVerifications(int websiteVerifications) {
		this.websiteVerifications = websiteVerifications;
	}

	private int currentaccountbalancetransferverification;
	private int websiteVerifications;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantPhoneNumber() {
		return merchantPhoneNumber;
	}

	public void setMerchantPhoneNumber(String merchantPhoneNumber) {
		this.merchantPhoneNumber = merchantPhoneNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public int getDomainNameValidation() {
		return domainNameValidation;
	}

	public void setDomainNameValidation(int domainNameValidation) {
		this.domainNameValidation = domainNameValidation;
	}

	public String getMerchantRegisrationRefNumber() {
		return merchantRegisrationRefNumber;
	}

	public void setMerchantRegisrationRefNumber(
			String merchantRegisrationRefNumber) {
		this.merchantRegisrationRefNumber = merchantRegisrationRefNumber;
	}

	public int getAadharValidation() {
		return aadharValidation;
	}

	public void setAadharValidation(int aadharValidation) {
		this.aadharValidation = aadharValidation;
	}

	public String getRegistrationType() {
		return registrationType;
	}

	public void setRegistrationType(String registrationType) {
		this.registrationType = registrationType;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getSecurityCheck() {
		return securityCheck;
	}

	public void setSecurityCheck(int securityCheck) {
		this.securityCheck = securityCheck;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public long getUpdatedTimestamp() {
		return updatedTimestamp;
	}

	public void setUpdatedTimestamp(long updatedTimestamp) {
		this.updatedTimestamp = updatedTimestamp;
	}

	public int getKeywordsVerification() {
		return keywordsVerification;
	}

	public void setKeywordsVerification(int keywordsVerification) {
		this.keywordsVerification = keywordsVerification;
	}

	public int getHighandlowriskbusinesscheck() {
		return highandlowriskbusinesscheck;
	}

	public void setHighandlowriskbusinesscheck(int highandlowriskbusinesscheck) {
		this.highandlowriskbusinesscheck = highandlowriskbusinesscheck;
	}

	public int getCurrentaccountbalancetransferverification() {
		return currentaccountbalancetransferverification;
	}

	public void setCurrentaccountbalancetransferverification(
			int currentaccountbalancetransferverification) {
		this.currentaccountbalancetransferverification = currentaccountbalancetransferverification;
	}

	public int getPanValidationStatus() {
		return panValidationStatus;
	}

	public void setPanValidationStatus(int panValidationStatus) {
		this.panValidationStatus = panValidationStatus;
	}

	public int getTanValidationStatus() {
		return tanValidationStatus;
	}

	public void setTanValidationStatus(int tanValidationStatus) {
		this.tanValidationStatus = tanValidationStatus;
	}

	public int getSocialCheck() {
		return socialCheck;
	}

	public void setSocialCheck(int socialCheck) {
		this.socialCheck = socialCheck;
	}
}
