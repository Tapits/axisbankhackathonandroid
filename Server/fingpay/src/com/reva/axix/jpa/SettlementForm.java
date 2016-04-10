package com.reva.axix.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the settlement_form database table.
 * 
 */
@Entity
@Table(name="settlement_form")
@NamedQuery(name="SettlementForm.findAll", query="SELECT s FROM SettlementForm s")
public class SettlementForm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="current_account_number")
	private String currentAccountNumber;

	private String mode;

	private String remarks;

	@Temporal(TemporalType.TIMESTAMP)
	private Date timesatmp;

	@Column(name="validation_flag")
	private int validationFlag;

	//bi-directional many-to-one association to MerchantRegisrationId
	@ManyToOne
	@JoinColumn(name="merchant_regisration_ref_number")
	private MerchantRegisrationId merchantRegisrationId;

	public SettlementForm() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCurrentAccountNumber() {
		return this.currentAccountNumber;
	}

	public void setCurrentAccountNumber(String currentAccountNumber) {
		this.currentAccountNumber = currentAccountNumber;
	}

	public String getMode() {
		return this.mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getTimesatmp() {
		return this.timesatmp;
	}

	public void setTimesatmp(Date timesatmp) {
		this.timesatmp = timesatmp;
	}

	public int getValidationFlag() {
		return this.validationFlag;
	}

	public void setValidationFlag(int validationFlag) {
		this.validationFlag = validationFlag;
	}

	public MerchantRegisrationId getMerchantRegisrationId() {
		return this.merchantRegisrationId;
	}

	public void setMerchantRegisrationId(MerchantRegisrationId merchantRegisrationId) {
		this.merchantRegisrationId = merchantRegisrationId;
	}

}