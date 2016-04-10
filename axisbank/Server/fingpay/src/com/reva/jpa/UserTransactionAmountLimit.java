package com.reva.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the user_transaction_amount_limit database table.
 * 
 */
@Entity
@Table(name="user_transaction_amount_limit")
@NamedQuery(name="UserTransactionAmountLimit.findAll", query="SELECT u FROM UserTransactionAmountLimit u")
public class UserTransactionAmountLimit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="phone_number")
	private String phoneNumber;

	@Column(name="active_flag")
	private int activeFlag;

	@Column(name="day_limit")
	private BigDecimal dayLimit;

	@Column(name="transaction_limit")
	private BigDecimal transactionLimit;

	public UserTransactionAmountLimit() {
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

	public BigDecimal getDayLimit() {
		return this.dayLimit;
	}

	public void setDayLimit(BigDecimal dayLimit) {
		this.dayLimit = dayLimit;
	}

	public BigDecimal getTransactionLimit() {
		return this.transactionLimit;
	}

	public void setTransactionLimit(BigDecimal transactionLimit) {
		this.transactionLimit = transactionLimit;
	}

}