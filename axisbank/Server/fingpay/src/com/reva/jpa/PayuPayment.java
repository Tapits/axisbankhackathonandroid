package com.reva.jpa;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;

/**
 * The persistent class for the payu_payments database table.
 * 
 */
@Entity
@Table(name = "payu_payments")
@NamedQuery(name = "PayuPayment.findAll", query = "SELECT p FROM PayuPayment p")
public class PayuPayment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String request;

	private String response;

	@Column(name = "ext_order_id")
	private String extOrderIid;

	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	public PayuPayment() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRequest() {
		return this.request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getResponse() {
		return this.response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getExtOrderIid() {
		return extOrderIid;
	}

	public void setExtOrderIid(String extOrderIid) {
		this.extOrderIid = extOrderIid;
	}

}