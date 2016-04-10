package com.reva.axix.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the declaraction_form database table.
 * 
 */
@Entity
@Table(name="declaraction_form")
@NamedQuery(name="DeclaractionForm.findAll", query="SELECT d FROM DeclaractionForm d")
public class DeclaractionForm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String data;

	private String remarks;

	@Temporal(TemporalType.TIMESTAMP)
	private Date timesatmp;

	//bi-directional many-to-one association to MerchantRegisrationId
	@ManyToOne
	@JoinColumn(name="merchant_regisration_ref_number")
	private MerchantRegisrationId merchantRegisrationId;

	public DeclaractionForm() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getData() {
		return this.data;
	}

	public void setData(String data) {
		this.data = data;
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

	public MerchantRegisrationId getMerchantRegisrationId() {
		return this.merchantRegisrationId;
	}

	public void setMerchantRegisrationId(MerchantRegisrationId merchantRegisrationId) {
		this.merchantRegisrationId = merchantRegisrationId;
	}

}