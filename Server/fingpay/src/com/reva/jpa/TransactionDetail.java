package com.reva.jpa;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the transaction_details database table.
 * 
 */
@Entity
@Table(name = "transaction_details")
@NamedQuery(name = "TransactionDetail.findAll", query = "SELECT t FROM TransactionDetail t")
public class TransactionDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private BigDecimal amount;

	@Column(name = "bank_txn_id")
	private String bankTxnId;

	@Column(name = "customer_pin")
	private int customerPin;

	@Lob
	@Column(name = "finger_print")
	private String fingerPrint;

	private double latitude;

	private double longitude;

	@Column(name = "merchant_txn_id")
	private String merchantTxnId;

	@Column(name = "mobile_number")
	private String mobileNumber;

	@Column(name = "request_ip")
	private String requestIp;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "request_timestamp")
	private Date requestTimestamp;

	@Column(name = "retry_flag")
	private int retryFlag;

	@Column(name = "status_message")
	private String statusMessage;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "status_timestamp")
	private Date statusTimestamp;

	@Column(name = "txn_status")
	private int txnStatus;

	// bi-directional many-to-one association to PosDevice
	@ManyToOne
	@JoinColumn(name = "device_id")
	private PosDevice posDevice;

	// bi-directional many-to-one association to MerchantMaster
	@ManyToOne
	@JoinColumn(name = "merchant_id")
	private MerchantMaster merchantMaster;

	public TransactionDetail() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getBankTxnId() {
		return this.bankTxnId;
	}

	public void setBankTxnId(String bankTxnId) {
		this.bankTxnId = bankTxnId;
	}

	public int getCustomerPin() {
		return this.customerPin;
	}

	public void setCustomerPin(int customerPin) {
		this.customerPin = customerPin;
	}

	public String getFingerPrint() {
		return this.fingerPrint;
	}

	public void setFingerPrint(String fingerPrint) {
		this.fingerPrint = fingerPrint;
	}

	public double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getMerchantTxnId() {
		return this.merchantTxnId;
	}

	public void setMerchantTxnId(String merchantTxnId) {
		this.merchantTxnId = merchantTxnId;
	}

	public String getMobileNumber() {
		return this.mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getRequestIp() {
		return this.requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	public Date getRequestTimestamp() {
		return this.requestTimestamp;
	}

	public void setRequestTimestamp(Date requestTimestamp) {
		this.requestTimestamp = requestTimestamp;
	}

	public int getRetryFlag() {
		return this.retryFlag;
	}

	public void setRetryFlag(int retryFlag) {
		this.retryFlag = retryFlag;
	}

	public String getStatusMessage() {
		return this.statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public Date getStatusTimestamp() {
		return this.statusTimestamp;
	}

	public void setStatusTimestamp(Date statusTimestamp) {
		this.statusTimestamp = statusTimestamp;
	}

	public int getTxnStatus() {
		return this.txnStatus;
	}

	public void setTxnStatus(int txnStatus) {
		this.txnStatus = txnStatus;
	}

	public PosDevice getPosDevice() {
		return this.posDevice;
	}

	public void setPosDevice(PosDevice posDevice) {
		this.posDevice = posDevice;
	}

	public MerchantMaster getMerchantMaster() {
		return this.merchantMaster;
	}

	public void setMerchantMaster(MerchantMaster merchantMaster) {
		this.merchantMaster = merchantMaster;
	}

}