package com.reva.axix.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the ownership_details database table.
 * 
 */
@Entity
@Table(name = "ownership_details")
@NamedQuery(name = "OwnershipDetail.findAll", query = "SELECT o FROM OwnershipDetail o")
public class OwnershipDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "email_id")
	private String emailId;

	@Column(name = "landline_number")
	private String landlineNumber;

	@Column(name = "mobile_number")
	private String mobileNumber;

	@Column(name = "name_of_owner_partner_or_director")
	private String nameOfOwnerPartnerOrDirector;

	@Column(name = "aadhar_card")
	private String aadharCard;

	private String pan;

	private int PAN_verificaton;

	@Column(name = "photo_uploaded")
	private String photoUploaded;

	@Column(name = "pin_code")
	private String pinCode;

	private String remarks;

	@Column(name = "residential_address")
	private String residentialAddress;

	private String tan;

	private int TAN_verification;

	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	// bi-directional many-to-one association to MerchantRegisrationId
	@ManyToOne
	@JoinColumn(name = "merchant_regisration_ref_number")
	private MerchantRegisrationId merchantRegisrationId;

	public OwnershipDetail() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmailId() {
		return this.emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getLandlineNumber() {
		return this.landlineNumber;
	}

	public void setLandlineNumber(String landlineNumber) {
		this.landlineNumber = landlineNumber;
	}

	public String getMobileNumber() {
		return this.mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getNameOfOwnerPartnerOrDirector() {
		return this.nameOfOwnerPartnerOrDirector;
	}

	public void setNameOfOwnerPartnerOrDirector(
			String nameOfOwnerPartnerOrDirector) {
		this.nameOfOwnerPartnerOrDirector = nameOfOwnerPartnerOrDirector;
	}

	public String getPan() {
		return this.pan;
	}

	public String getAadharCard() {
		return aadharCard;
	}

	public void setAadharCard(String aadharCard) {
		this.aadharCard = aadharCard;
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

	public String getPhotoUploaded() {
		return this.photoUploaded;
	}

	public void setPhotoUploaded(String photoUploaded) {
		this.photoUploaded = photoUploaded;
	}

	public String getPinCode() {
		return this.pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getResidentialAddress() {
		return this.residentialAddress;
	}

	public void setResidentialAddress(String residentialAddress) {
		this.residentialAddress = residentialAddress;
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

	public void setMerchantRegisrationId(
			MerchantRegisrationId merchantRegisrationId) {
		this.merchantRegisrationId = merchantRegisrationId;
	}
}