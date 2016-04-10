package com.reva.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the pos_merchant_map database table.
 * 
 */
@Entity
@Table(name="pos_merchant_map")
@NamedQuery(name="PosMerchantMap.findAll", query="SELECT p FROM PosMerchantMap p")
public class PosMerchantMap implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PosMerchantMapPK id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="status_date")
	private Date statusDate;

	//bi-directional many-to-one association to PosDevice
	@ManyToOne
	@JoinColumn(name="device_id")
	private PosDevice posDevice;

	//bi-directional many-to-one association to MerchantMaster
	@ManyToOne
	@JoinColumn(name="merchant_id")
	private MerchantMaster merchantMaster;

	public PosMerchantMap() {
	}

	public PosMerchantMapPK getId() {
		return this.id;
	}

	public void setId(PosMerchantMapPK id) {
		this.id = id;
	}

	public Date getStatusDate() {
		return this.statusDate;
	}

	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
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