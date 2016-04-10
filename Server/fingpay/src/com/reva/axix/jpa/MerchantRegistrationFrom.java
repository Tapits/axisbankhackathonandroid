package com.reva.axix.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the merchant_registration_from database table.
 * 
 */
@Entity
@Table(name="merchant_registration_from")
@NamedQuery(name="MerchantRegistrationFrom.findAll", query="SELECT m FROM MerchantRegistrationFrom m")
public class MerchantRegistrationFrom implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String city;

	private String constitution;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_of_account_opening")
	private Date dateOfAccountOpening;

	@Column(name="email_address")
	private String emailAddress;

	@Column(name="existing_acquirer")
	private String existingAcquirer;

	@Column(name="legal_name")
	private String legalName;

	@Column(name="marketing_name_or_charge_name")
	private String marketingNameOrChargeName;

	@Column(name="mobile_number")
	private String mobileNumber;

	private String pan;

	private int PAN_verificaton;

	@Column(name="payment_mode")
	private String paymentMode;

	@Column(name="pin_code")
	private String pinCode;

	@Column(name="registrastion_remarks")
	private String registrastionRemarks;

	@Column(name="shop_images")
	private String shopImages;

	private String state;

	private String tan;

	private int TAN_verification;

	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	//bi-directional many-to-one association to MerchantRegisrationId
	@ManyToOne
	@JoinColumn(name="merchant_regisration_ref_number")
	private MerchantRegisrationId merchantRegisrationId;

	public MerchantRegistrationFrom() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getConstitution() {
		return this.constitution;
	}

	public void setConstitution(String constitution) {
		this.constitution = constitution;
	}

	public Date getDateOfAccountOpening() {
		return this.dateOfAccountOpening;
	}

	public void setDateOfAccountOpening(Date dateOfAccountOpening) {
		this.dateOfAccountOpening = dateOfAccountOpening;
	}

	public String getEmailAddress() {
		return this.emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getExistingAcquirer() {
		return this.existingAcquirer;
	}

	public void setExistingAcquirer(String existingAcquirer) {
		this.existingAcquirer = existingAcquirer;
	}

	public String getLegalName() {
		return this.legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public String getMarketingNameOrChargeName() {
		return this.marketingNameOrChargeName;
	}

	public void setMarketingNameOrChargeName(String marketingNameOrChargeName) {
		this.marketingNameOrChargeName = marketingNameOrChargeName;
	}

	public String getMobileNumber() {
		return this.mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPan() {
		return this.pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public int getPAN_verificaton() {
		return this.PAN_verificaton;
	}

	public void setPAN_verificaton(int PAN_verificaton) {
		this.PAN_verificaton = PAN_verificaton;
	}

	public String getPaymentMode() {
		return this.paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getPinCode() {
		return this.pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getRegistrastionRemarks() {
		return this.registrastionRemarks;
	}

	public void setRegistrastionRemarks(String registrastionRemarks) {
		this.registrastionRemarks = registrastionRemarks;
	}

	public String getShopImages() {
		return this.shopImages;
	}

	public void setShopImages(String shopImages) {
		this.shopImages = shopImages;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTan() {
		return this.tan;
	}

	public void setTan(String tan) {
		this.tan = tan;
	}

	public int getTAN_verification() {
		return this.TAN_verification;
	}

	public void setTAN_verification(int TAN_verification) {
		this.TAN_verification = TAN_verification;
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public MerchantRegisrationId getMerchantRegisrationId() {
		return this.merchantRegisrationId;
	}

	public void setMerchantRegisrationId(MerchantRegisrationId merchantRegisrationId) {
		this.merchantRegisrationId = merchantRegisrationId;
	}

}