package com.reva.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the pos_device database table.
 * 
 */
@Entity
@Table(name = "pos_device")
@NamedQuery(name = "PosDevice.findAll", query = "SELECT p FROM PosDevice p")
public class PosDevice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "device_id")
	private int deviceId;

	@Column(name = "active_status")
	private int activeStatus;

	private String imei;

	@Column(name = "mac_addr")
	private String macAddr;

	private String make;

	private String model;

	@Column(name = "push_token")
	private String pushToken;

	@Column(name = "unique_key")
	private String uniqueKey;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "registered_timestamp")
	private Date registeredTimestamp;

	// bi-directional many-to-one association to DeviceApkVersion
	@OneToMany(mappedBy = "posDevice")
	private List<DeviceApkVersion> deviceApkVersions;

	// bi-directional many-to-one association to PosMerchantMap
	@OneToMany(mappedBy = "posDevice")
	private List<PosMerchantMap> posMerchantMaps;

	// bi-directional many-to-one association to TransactionDetail
	@OneToMany(mappedBy = "posDevice")
	private List<TransactionDetail> transactionDetails;

	public PosDevice() {
	}

	public int getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public int getActiveStatus() {
		return this.activeStatus;
	}

	public void setActiveStatus(int activeStatus) {
		this.activeStatus = activeStatus;
	}

	public String getImei() {
		return this.imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getMacAddr() {
		return this.macAddr;
	}

	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
	}

	public String getMake() {
		return this.make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getPushToken() {
		return this.pushToken;
	}

	public void setPushToken(String pushToken) {
		this.pushToken = pushToken;
	}

	public Date getRegisteredTimestamp() {
		return this.registeredTimestamp;
	}

	public void setRegisteredTimestamp(Date registeredTimestamp) {
		this.registeredTimestamp = registeredTimestamp;
	}

	public List<DeviceApkVersion> getDeviceApkVersions() {
		return this.deviceApkVersions;
	}

	public void setDeviceApkVersions(List<DeviceApkVersion> deviceApkVersions) {
		this.deviceApkVersions = deviceApkVersions;
	}

	public DeviceApkVersion addDeviceApkVersion(
			DeviceApkVersion deviceApkVersion) {
		getDeviceApkVersions().add(deviceApkVersion);
		deviceApkVersion.setPosDevice(this);

		return deviceApkVersion;
	}

	public DeviceApkVersion removeDeviceApkVersion(
			DeviceApkVersion deviceApkVersion) {
		getDeviceApkVersions().remove(deviceApkVersion);
		deviceApkVersion.setPosDevice(null);

		return deviceApkVersion;
	}

	public List<PosMerchantMap> getPosMerchantMaps() {
		return this.posMerchantMaps;
	}

	public void setPosMerchantMaps(List<PosMerchantMap> posMerchantMaps) {
		this.posMerchantMaps = posMerchantMaps;
	}

	public PosMerchantMap addPosMerchantMap(PosMerchantMap posMerchantMap) {
		getPosMerchantMaps().add(posMerchantMap);
		posMerchantMap.setPosDevice(this);

		return posMerchantMap;
	}

	public PosMerchantMap removePosMerchantMap(PosMerchantMap posMerchantMap) {
		getPosMerchantMaps().remove(posMerchantMap);
		posMerchantMap.setPosDevice(null);

		return posMerchantMap;
	}

	public List<TransactionDetail> getTransactionDetails() {
		return this.transactionDetails;
	}

	public void setTransactionDetails(List<TransactionDetail> transactionDetails) {
		this.transactionDetails = transactionDetails;
	}

	public TransactionDetail addTransactionDetail(
			TransactionDetail transactionDetail) {
		getTransactionDetails().add(transactionDetail);
		transactionDetail.setPosDevice(this);

		return transactionDetail;
	}

	public TransactionDetail removeTransactionDetail(
			TransactionDetail transactionDetail) {
		getTransactionDetails().remove(transactionDetail);
		transactionDetail.setPosDevice(null);

		return transactionDetail;
	}

	public String getUniqueKey() {
		return uniqueKey;
	}

	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}

}