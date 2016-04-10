package com.tapits.axismerchantservices.data;

public class MerchantRegistrationFromData {
	private int id;
	private String city;
	private String constitution;
	private long dateOfAccountOpening;
	private String emailAddress;
	private String existingAcquirer;
	private String legalName;
	private String marketingNameOrChargeName;
	private String mobileNumber;
	private String pan;
	private int PAN_verificaton;
	private String paymentMode;
	private String pinCode;
	private String registrastionRemarks;
	private String shopImages;
	private String state;
	private String tan;
	private int TAN_verification;
	private long timestamp;
	private String merchantRegisrationId;
	private String address;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getConstitution() {
		return constitution;
	}

	public void setConstitution(String constitution) {
		this.constitution = constitution;
	}

	public long getDateOfAccountOpening() {
		return dateOfAccountOpening;
	}

	public void setDateOfAccountOpening(long dateOfAccountOpening) {
		this.dateOfAccountOpening = dateOfAccountOpening;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getExistingAcquirer() {
		return existingAcquirer;
	}

	public void setExistingAcquirer(String existingAcquirer) {
		this.existingAcquirer = existingAcquirer;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public String getMarketingNameOrChargeName() {
		return marketingNameOrChargeName;
	}

	public void setMarketingNameOrChargeName(String marketingNameOrChargeName) {
		this.marketingNameOrChargeName = marketingNameOrChargeName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public int getPAN_verificaton() {
		return PAN_verificaton;
	}

	public void setPAN_verificaton(int pAN_verificaton) {
		PAN_verificaton = pAN_verificaton;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getRegistrastionRemarks() {
		return registrastionRemarks;
	}

	public void setRegistrastionRemarks(String registrastionRemarks) {
		this.registrastionRemarks = registrastionRemarks;
	}

	public String getShopImages() {
		return shopImages;
	}

	public void setShopImages(String shopImages) {
		this.shopImages = shopImages;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTan() {
		return tan;
	}

	public void setTan(String tan) {
		this.tan = tan;
	}

	public int getTAN_verification() {
		return TAN_verification;
	}

	public void setTAN_verification(int tAN_verification) {
		TAN_verification = tAN_verification;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getMerchantRegisrationId() {
		return merchantRegisrationId;
	}

	public void setMerchantRegisrationId(String merchantRegisrationId) {
		this.merchantRegisrationId = merchantRegisrationId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "MerchantRegistrationFromData [id=" + id + ", city=" + city
				+ ", constitution=" + constitution + ", dateOfAccountOpening="
				+ dateOfAccountOpening + ", emailAddress=" + emailAddress
				+ ", existingAcquirer=" + existingAcquirer + ", legalName="
				+ legalName + ", marketingNameOrChargeName="
				+ marketingNameOrChargeName + ", mobileNumber=" + mobileNumber
				+ ", pan=" + pan + ", PAN_verificaton=" + PAN_verificaton
				+ ", paymentMode=" + paymentMode + ", pinCode=" + pinCode
				+ ", registrastionRemarks=" + registrastionRemarks
				+ ", shopImages=" + shopImages + ", state=" + state + ", tan="
				+ tan + ", TAN_verification=" + TAN_verification
				+ ", timestamp=" + timestamp + ", merchantRegisrationId="
				+ merchantRegisrationId + ", address=" + address + "]";
	}

}
