package com.reva.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the phone_number_otp database table.
 * 
 */
@Entity
@Table(name="phone_number_otp")
@NamedQuery(name="PhoneNumberOtp.findAll", query="SELECT p FROM PhoneNumberOtp p")
public class PhoneNumberOtp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="phone_number")
	private String phoneNumber;

	@Column(name="active_flag")
	private int activeFlag;

	private int otp;

	private String remarks;

	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	public PhoneNumberOtp() {
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getActiveFlag() {
		return this.activeFlag;
	}

	public void setActiveFlag(int activeFlag) {
		this.activeFlag = activeFlag;
	}

	public int getOtp() {
		return this.otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

}