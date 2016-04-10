package com.reva.jpa;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Table(name="user")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id")
	private int userId;

	@Column(name="active_flag")
	private int activeFlag;

	private String password;

	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	@Column(name="user_type")
	private int userType;

	private String username;

	//bi-directional many-to-one association to MerchantUserMap
	@OneToMany(mappedBy="user")
	private List<MerchantUserMap> merchantUserMaps;

	public User() {
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getActiveFlag() {
		return this.activeFlag;
	}

	public void setActiveFlag(int activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public int getUserType() {
		return this.userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<MerchantUserMap> getMerchantUserMaps() {
		return this.merchantUserMaps;
	}

	public void setMerchantUserMaps(List<MerchantUserMap> merchantUserMaps) {
		this.merchantUserMaps = merchantUserMaps;
	}

	public MerchantUserMap addMerchantUserMap(MerchantUserMap merchantUserMap) {
		getMerchantUserMaps().add(merchantUserMap);
		merchantUserMap.setUser(this);

		return merchantUserMap;
	}

	public MerchantUserMap removeMerchantUserMap(MerchantUserMap merchantUserMap) {
		getMerchantUserMaps().remove(merchantUserMap);
		merchantUserMap.setUser(null);

		return merchantUserMap;
	}

}