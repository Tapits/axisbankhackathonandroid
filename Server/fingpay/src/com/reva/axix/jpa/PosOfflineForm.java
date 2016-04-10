package com.reva.axix.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the pos_offline_form database table.
 * 
 */
@Entity
@Table(name="pos_offline_form")
@NamedQuery(name="PosOfflineForm.findAll", query="SELECT p FROM PosOfflineForm p")
public class PosOfflineForm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="format_")
	private String format;

	private String frequency;

	private String remarks;

	private String statement;

	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	@Column(name="type_of_material")
	private String typeOfMaterial;

	@Column(name="type_of_pos")
	private String typeOfPos;

	private String vas;

	//bi-directional many-to-one association to MerchantRegisrationId
	@ManyToOne
	@JoinColumn(name="merchant_regisration_ref_number")
	private MerchantRegisrationId merchantRegisrationId;

	public PosOfflineForm() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFormat() {
		return this.format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getFrequency() {
		return this.frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getStatement() {
		return this.statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getTypeOfMaterial() {
		return this.typeOfMaterial;
	}

	public void setTypeOfMaterial(String typeOfMaterial) {
		this.typeOfMaterial = typeOfMaterial;
	}

	public String getTypeOfPos() {
		return this.typeOfPos;
	}

	public void setTypeOfPos(String typeOfPos) {
		this.typeOfPos = typeOfPos;
	}

	public String getVas() {
		return this.vas;
	}

	public void setVas(String vas) {
		this.vas = vas;
	}

	public MerchantRegisrationId getMerchantRegisrationId() {
		return this.merchantRegisrationId;
	}

	public void setMerchantRegisrationId(MerchantRegisrationId merchantRegisrationId) {
		this.merchantRegisrationId = merchantRegisrationId;
	}

}