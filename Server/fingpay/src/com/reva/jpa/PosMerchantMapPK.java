package com.reva.jpa;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the pos_merchant_map database table.
 * 
 */
@Embeddable
public class PosMerchantMapPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="device_id", insertable=false, updatable=false)
	private int deviceId;

	@Column(name="merchant_id", insertable=false, updatable=false)
	private int merchantId;

	public PosMerchantMapPK() {
	}
	public int getDeviceId() {
		return this.deviceId;
	}
	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}
	public int getMerchantId() {
		return this.merchantId;
	}
	public void setMerchantId(int merchantId) {
		this.merchantId = merchantId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PosMerchantMapPK)) {
			return false;
		}
		PosMerchantMapPK castOther = (PosMerchantMapPK)other;
		return 
			(this.deviceId == castOther.deviceId)
			&& (this.merchantId == castOther.merchantId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.deviceId;
		hash = hash * prime + this.merchantId;
		
		return hash;
	}
}