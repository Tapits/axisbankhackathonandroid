package com.reva.axix.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the merchant_regisration_ids database table.
 * 
 */
@Entity
@Table(name = "merchant_regisration_ids")
@NamedQuery(name = "MerchantRegisrationId.findAll", query = "SELECT m FROM MerchantRegisrationId m")
public class MerchantRegisrationId implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "domain_name_validation")
	private int domainNameValidation;

	@Column(name = "merchant_regisration_ref_number")
	private String merchantRegisrationRefNumber;

	@Column(name = "aadhar_validation")
	private String aadharValidation;

	private int PAN_validation_status;

	@Column(name = "registration_type")
	private String registrationType;

	private String remarks;

	@Column(name = "security_check")
	private int securityCheck;

	private int status;

	private int TAN_validation_status;

	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_timestamp")
	private Date updatedTimestamp;

	// bi-directional many-to-one association to DeclaractionForm
	@OneToMany(mappedBy = "merchantRegisrationId", fetch=FetchType.EAGER)
	private List<DeclaractionForm> declaractionForms;

	// bi-directional many-to-one association to MerchantRegistrationFrom
	@OneToMany(mappedBy = "merchantRegisrationId", fetch=FetchType.EAGER)
	private List<MerchantRegistrationFrom> merchantRegistrationFroms;

	// bi-directional many-to-one association to OnlinePosForm
	@OneToMany(mappedBy = "merchantRegisrationId", fetch=FetchType.EAGER)
	private List<OnlinePosForm> onlinePosForms;

	// bi-directional many-to-one association to OwnershipDetail
	@OneToMany(mappedBy = "merchantRegisrationId", fetch=FetchType.EAGER)
	private List<OwnershipDetail> ownershipDetails;

	// bi-directional many-to-one association to PosOfflineForm
	@OneToMany(mappedBy = "merchantRegisrationId", fetch=FetchType.EAGER)
	private List<PosOfflineForm> posOfflineForms;

	// bi-directional many-to-one association to SettlementForm
	@OneToMany(mappedBy = "merchantRegisrationId", fetch=FetchType.EAGER)
	private List<SettlementForm> settlementForms;

	public MerchantRegisrationId() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDomainNameValidation() {
		return this.domainNameValidation;
	}

	public void setDomainNameValidation(int domainNameValidation) {
		this.domainNameValidation = domainNameValidation;
	}

	public String getMerchantRegisrationRefNumber() {
		return this.merchantRegisrationRefNumber;
	}

	public void setMerchantRegisrationRefNumber(
			String merchantRegisrationRefNumber) {
		this.merchantRegisrationRefNumber = merchantRegisrationRefNumber;
	}

	public String getAadharValidation() {
		return aadharValidation;
	}

	public void setAadharValidation(String aadharValidation) {
		this.aadharValidation = aadharValidation;
	}

	public int getPAN_validation_status() {
		return this.PAN_validation_status;
	}

	public void setPAN_validation_status(int PAN_validation_status) {
		this.PAN_validation_status = PAN_validation_status;
	}

	public String getRegistrationType() {
		return this.registrationType;
	}

	public void setRegistrationType(String registrationType) {
		this.registrationType = registrationType;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getSecurityCheck() {
		return this.securityCheck;
	}

	public void setSecurityCheck(int securityCheck) {
		this.securityCheck = securityCheck;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getTAN_validation_status() {
		return this.TAN_validation_status;
	}

	public void setTAN_validation_status(int TAN_validation_status) {
		this.TAN_validation_status = TAN_validation_status;
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Date getUpdatedTimestamp() {
		return this.updatedTimestamp;
	}

	public void setUpdatedTimestamp(Date updatedTimestamp) {
		this.updatedTimestamp = updatedTimestamp;
	}

	public List<DeclaractionForm> getDeclaractionForms() {
		return this.declaractionForms;
	}

	public void setDeclaractionForms(List<DeclaractionForm> declaractionForms) {
		this.declaractionForms = declaractionForms;
	}

	public DeclaractionForm addDeclaractionForm(
			DeclaractionForm declaractionForm) {
		getDeclaractionForms().add(declaractionForm);
		declaractionForm.setMerchantRegisrationId(this);

		return declaractionForm;
	}

	public DeclaractionForm removeDeclaractionForm(
			DeclaractionForm declaractionForm) {
		getDeclaractionForms().remove(declaractionForm);
		declaractionForm.setMerchantRegisrationId(null);

		return declaractionForm;
	}

	public List<MerchantRegistrationFrom> getMerchantRegistrationFroms() {
		return this.merchantRegistrationFroms;
	}

	public void setMerchantRegistrationFroms(
			List<MerchantRegistrationFrom> merchantRegistrationFroms) {
		this.merchantRegistrationFroms = merchantRegistrationFroms;
	}

	public MerchantRegistrationFrom addMerchantRegistrationFrom(
			MerchantRegistrationFrom merchantRegistrationFrom) {
		getMerchantRegistrationFroms().add(merchantRegistrationFrom);
		merchantRegistrationFrom.setMerchantRegisrationId(this);

		return merchantRegistrationFrom;
	}

	public MerchantRegistrationFrom removeMerchantRegistrationFrom(
			MerchantRegistrationFrom merchantRegistrationFrom) {
		getMerchantRegistrationFroms().remove(merchantRegistrationFrom);
		merchantRegistrationFrom.setMerchantRegisrationId(null);

		return merchantRegistrationFrom;
	}

	public List<OnlinePosForm> getOnlinePosForms() {
		return this.onlinePosForms;
	}

	public void setOnlinePosForms(List<OnlinePosForm> onlinePosForms) {
		this.onlinePosForms = onlinePosForms;
	}

	public OnlinePosForm addOnlinePosForm(OnlinePosForm onlinePosForm) {
		getOnlinePosForms().add(onlinePosForm);
		onlinePosForm.setMerchantRegisrationId(this);

		return onlinePosForm;
	}

	public OnlinePosForm removeOnlinePosForm(OnlinePosForm onlinePosForm) {
		getOnlinePosForms().remove(onlinePosForm);
		onlinePosForm.setMerchantRegisrationId(null);

		return onlinePosForm;
	}

	public List<OwnershipDetail> getOwnershipDetails() {
		return this.ownershipDetails;
	}

	public void setOwnershipDetails(List<OwnershipDetail> ownershipDetails) {
		this.ownershipDetails = ownershipDetails;
	}

	public OwnershipDetail addOwnershipDetail(OwnershipDetail ownershipDetail) {
		getOwnershipDetails().add(ownershipDetail);
		ownershipDetail.setMerchantRegisrationId(this);

		return ownershipDetail;
	}

	public OwnershipDetail removeOwnershipDetail(OwnershipDetail ownershipDetail) {
		getOwnershipDetails().remove(ownershipDetail);
		ownershipDetail.setMerchantRegisrationId(null);

		return ownershipDetail;
	}

	public List<PosOfflineForm> getPosOfflineForms() {
		return this.posOfflineForms;
	}

	public void setPosOfflineForms(List<PosOfflineForm> posOfflineForms) {
		this.posOfflineForms = posOfflineForms;
	}

	public PosOfflineForm addPosOfflineForm(PosOfflineForm posOfflineForm) {
		getPosOfflineForms().add(posOfflineForm);
		posOfflineForm.setMerchantRegisrationId(this);

		return posOfflineForm;
	}

	public PosOfflineForm removePosOfflineForm(PosOfflineForm posOfflineForm) {
		getPosOfflineForms().remove(posOfflineForm);
		posOfflineForm.setMerchantRegisrationId(null);

		return posOfflineForm;
	}

	public List<SettlementForm> getSettlementForms() {
		return this.settlementForms;
	}

	public void setSettlementForms(List<SettlementForm> settlementForms) {
		this.settlementForms = settlementForms;
	}

	public SettlementForm addSettlementForm(SettlementForm settlementForm) {
		getSettlementForms().add(settlementForm);
		settlementForm.setMerchantRegisrationId(this);

		return settlementForm;
	}

	public SettlementForm removeSettlementForm(SettlementForm settlementForm) {
		getSettlementForms().remove(settlementForm);
		settlementForm.setMerchantRegisrationId(null);

		return settlementForm;
	}

}