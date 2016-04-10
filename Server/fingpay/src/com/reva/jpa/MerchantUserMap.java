package com.reva.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the merchant_user_map database table.
 * 
 */
@Entity
@Table(name="merchant_user_map")
@NamedQuery(name="MerchantUserMap.findAll", query="SELECT m FROM MerchantUserMap m")
public class MerchantUserMap implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	//bi-directional many-to-one association to MerchantMaster
	@ManyToOne
	@JoinColumn(name="merchant_id")
	private MerchantMaster merchantMaster;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	public MerchantUserMap() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public MerchantMaster getMerchantMaster() {
		return this.merchantMaster;
	}

	public void setMerchantMaster(MerchantMaster merchantMaster) {
		this.merchantMaster = merchantMaster;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}