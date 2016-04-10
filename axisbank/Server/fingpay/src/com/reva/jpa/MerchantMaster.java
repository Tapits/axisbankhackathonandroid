package com.reva.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the merchant_master database table.
 * 
 */
@Entity
@Table(name="merchant_master")
@NamedQuery(name="MerchantMaster.findAll", query="SELECT m FROM MerchantMaster m")
public class MerchantMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="active_flag")
	private int activeFlag;

	@Column(name="merchant_address1")
	private String merchantAddress1;

	@Column(name="merchant_address2")
	private String merchantAddress2;

	@Column(name="merchant_address3")
	private String merchantAddress3;
	
	@Column(name="merchant_phone_number")
	private String merchantPhoneNumber;

	@Column(name="merchant_logo")
	private String merchantLogo;

	@Column(name="merchant_name")
	private String merchantName;

	@Column(name="merchant_pin")
	private int merchantPin;
	
	@Column(name="merchant_id")
	private String merchantId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	//bi-directional many-to-one association to CityMaster
	@ManyToOne
	@JoinColumn(name="merchant_city")
	private CityMaster cityMaster;

	//bi-directional many-to-one association to StateMaster
	@ManyToOne
	@JoinColumn(name="merchant_state")
	private StateMaster stateMaster;

	//bi-directional many-to-one association to MerchantUserMap
	@OneToMany(mappedBy="merchantMaster")
	private List<MerchantUserMap> merchantUserMaps;

	//bi-directional many-to-one association to PosMerchantMap
	@OneToMany(mappedBy="merchantMaster")
	private List<PosMerchantMap> posMerchantMaps;

	//bi-directional many-to-one association to TransactionDetail
	@OneToMany(mappedBy="merchantMaster")
	private List<TransactionDetail> transactionDetails;

	public MerchantMaster() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getActiveFlag() {
		return this.activeFlag;
	}

	public void setActiveFlag(int activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String getMerchantAddress1() {
		return this.merchantAddress1;
	}

	public void setMerchantAddress1(String merchantAddress1) {
		this.merchantAddress1 = merchantAddress1;
	}

	public String getMerchantAddress2() {
		return this.merchantAddress2;
	}

	public void setMerchantAddress2(String merchantAddress2) {
		this.merchantAddress2 = merchantAddress2;
	}

	public String getMerchantAddress3() {
		return this.merchantAddress3;
	}

	public void setMerchantAddress3(String merchantAddress3) {
		this.merchantAddress3 = merchantAddress3;
	}

	public String getMerchantPhoneNumber() {
		return merchantPhoneNumber;
	}

	public void setMerchantPhoneNumber(String merchantPhoneNumber) {
		this.merchantPhoneNumber = merchantPhoneNumber;
	}

	public String getMerchantLogo() {
		return this.merchantLogo;
	}

	public void setMerchantLogo(String merchantLogo) {
		this.merchantLogo = merchantLogo;
	}

	public String getMerchantName() {
		return this.merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public int getMerchantPin() {
		return this.merchantPin;
	}

	public void setMerchantPin(int merchantPin) {
		this.merchantPin = merchantPin;
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public CityMaster getCityMaster() {
		return this.cityMaster;
	}

	public void setCityMaster(CityMaster cityMaster) {
		this.cityMaster = cityMaster;
	}

	public StateMaster getStateMaster() {
		return this.stateMaster;
	}

	public void setStateMaster(StateMaster stateMaster) {
		this.stateMaster = stateMaster;
	}

	public List<MerchantUserMap> getMerchantUserMaps() {
		return this.merchantUserMaps;
	}

	public void setMerchantUserMaps(List<MerchantUserMap> merchantUserMaps) {
		this.merchantUserMaps = merchantUserMaps;
	}
	
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public MerchantUserMap addMerchantUserMap(MerchantUserMap merchantUserMap) {
		getMerchantUserMaps().add(merchantUserMap);
		merchantUserMap.setMerchantMaster(this);

		return merchantUserMap;
	}

	public MerchantUserMap removeMerchantUserMap(MerchantUserMap merchantUserMap) {
		getMerchantUserMaps().remove(merchantUserMap);
		merchantUserMap.setMerchantMaster(null);

		return merchantUserMap;
	}

	public List<PosMerchantMap> getPosMerchantMaps() {
		return this.posMerchantMaps;
	}

	public void setPosMerchantMaps(List<PosMerchantMap> posMerchantMaps) {
		this.posMerchantMaps = posMerchantMaps;
	}

	public PosMerchantMap addPosMerchantMap(PosMerchantMap posMerchantMap) {
		getPosMerchantMaps().add(posMerchantMap);
		posMerchantMap.setMerchantMaster(this);

		return posMerchantMap;
	}

	public PosMerchantMap removePosMerchantMap(PosMerchantMap posMerchantMap) {
		getPosMerchantMaps().remove(posMerchantMap);
		posMerchantMap.setMerchantMaster(null);

		return posMerchantMap;
	}

	public List<TransactionDetail> getTransactionDetails() {
		return this.transactionDetails;
	}

	public void setTransactionDetails(List<TransactionDetail> transactionDetails) {
		this.transactionDetails = transactionDetails;
	}

	public TransactionDetail addTransactionDetail(TransactionDetail transactionDetail) {
		getTransactionDetails().add(transactionDetail);
		transactionDetail.setMerchantMaster(this);

		return transactionDetail;
	}

	public TransactionDetail removeTransactionDetail(TransactionDetail transactionDetail) {
		getTransactionDetails().remove(transactionDetail);
		transactionDetail.setMerchantMaster(null);

		return transactionDetail;
	}

}