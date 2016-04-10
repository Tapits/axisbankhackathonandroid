package com.reva.data;

public class TransactionInitiateData {
	// AES encryption key = fingpay
	private int merchantId;
	private int deviceId;
	private long currentTime;
	private String deviceChecksum;// SHA512(device_id + merchant_id + imei + mac
									// + current time stamp in millis)
	private String customerMobileNumber;// AES encrypted customer_mobile_number
	private String customerPin;// AES encrypted customer_pin
	private String amount;// AES encrypted amount
	private String customerFingerPrint;// AES encrypted customer_finger_print
	private String merchantTxnId;// auto generated sequence on mobile
	private String datachecksum;// SHA512 ( merchant_tx_id + device_id +
								// merchant_id + customer_mobile_number +
								// customer_pin + amount + customer_finger_print
								// + currenttime in millis)
	private boolean retryFlag;// as false (in case of retry send true)
	private double latitude;
	private double longitude;
	

	public int getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(int merchantId) {
		this.merchantId = merchantId;
	}

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public long getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(long currentTime) {
		this.currentTime = currentTime;
	}

	public String getDeviceChecksum() {
		return deviceChecksum;
	}

	public void setDeviceChecksum(String deviceChecksum) {
		this.deviceChecksum = deviceChecksum;
	}

	public String getCustomerMobileNumber() {
		return customerMobileNumber;
	}

	public void setCustomerMobileNumber(String customerMobileNumber) {
		this.customerMobileNumber = customerMobileNumber;
	}

	public String getCustomerPin() {
		return customerPin;
	}

	public void setCustomerPin(String customerPin) {
		this.customerPin = customerPin;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCustomerFingerPrint() {
		return customerFingerPrint;
	}

	public void setCustomerFingerPrint(String customerFingerPrint) {
		this.customerFingerPrint = customerFingerPrint;
	}

	public String getMerchantTxnId() {
		return merchantTxnId;
	}

	public void setMerchantTxnId(String merchantTxnId) {
		this.merchantTxnId = merchantTxnId;
	}

	public String getDatachecksum() {
		return datachecksum;
	}

	public void setDatachecksum(String datachecksum) {
		this.datachecksum = datachecksum;
	}

	public boolean isRetryFlag() {
		return retryFlag;
	}

	public void setRetryFlag(boolean retryFlag) {
		this.retryFlag = retryFlag;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

}
