package com.reva.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the payment_gate_ways database table.
 * 
 */
@Entity
@Table(name="payment_gate_ways")
@NamedQuery(name="PaymentGateWay.findAll", query="SELECT p FROM PaymentGateWay p")
public class PaymentGateWay implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="active_flag")
	private int activeFlag;

	private String commet;

	@Column(name="payment_gate_image")
	private String paymentGateImage;

	@Column(name="payment_gate_name")
	private String paymentGateName;

	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	public PaymentGateWay() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getActiveFlag() {
		return this.activeFlag;
	}

	public void setActiveFlag(int activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String getCommet() {
		return this.commet;
	}

	public void setCommet(String commet) {
		this.commet = commet;
	}

	public String getPaymentGateImage() {
		return this.paymentGateImage;
	}

	public void setPaymentGateImage(String paymentGateImage) {
		this.paymentGateImage = paymentGateImage;
	}

	public String getPaymentGateName() {
		return this.paymentGateName;
	}

	public void setPaymentGateName(String paymentGateName) {
		this.paymentGateName = paymentGateName;
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

}