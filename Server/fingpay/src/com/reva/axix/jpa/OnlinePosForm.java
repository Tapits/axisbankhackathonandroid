package com.reva.axix.jpa;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the online_pos_form database table.
 * 
 */
@Entity
@Table(name="online_pos_form")
@NamedQuery(name="OnlinePosForm.findAll", query="SELECT o FROM OnlinePosForm o")
public class OnlinePosForm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String card;

	private String format;

	private String frequency;

	@Column(name="interface_language")
	private String interfaceLanguage;

	@Column(name="payment_url")
	private String paymentUrl;

	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	private String vas;

	@Column(name="wibsite_url")
	private String wibsiteUrl;
	
	private String reamarks;

	@Column(name = "url_exit_flag")
	private int urlExitFlag;

	@Column(name = "beta_url")
	private String betaURL;
	
	private String document;
	
	//bi-directional many-to-one association to MerchantRegisrationId
	@ManyToOne
	@JoinColumn(name="merchant_regisration_ref_number")
	private MerchantRegisrationId merchantRegisrationId;

	public OnlinePosForm() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCard() {
		return this.card;
	}

	public void setCard(String card) {
		this.card = card;
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

	public String getInterfaceLanguage() {
		return this.interfaceLanguage;
	}

	public void setInterfaceLanguage(String interfaceLanguage) {
		this.interfaceLanguage = interfaceLanguage;
	}

	public String getPaymentUrl() {
		return this.paymentUrl;
	}

	public void setPaymentUrl(String paymentUrl) {
		this.paymentUrl = paymentUrl;
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getVas() {
		return this.vas;
	}

	public void setVas(String vas) {
		this.vas = vas;
	}

	public String getWibsiteUrl() {
		return this.wibsiteUrl;
	}

	public void setWibsiteUrl(String wibsiteUrl) {
		this.wibsiteUrl = wibsiteUrl;
	}

	public MerchantRegisrationId getMerchantRegisrationId() {
		return this.merchantRegisrationId;
	}

	public void setMerchantRegisrationId(MerchantRegisrationId merchantRegisrationId) {
		this.merchantRegisrationId = merchantRegisrationId;
	}
	
	public String getReamarks() {
		return reamarks;
	}

	public void setReamarks(String reamarks) {
		this.reamarks = reamarks;
	}

	public int getUrlExitFlag() {
		return urlExitFlag;
	}

	public void setUrlExitFlag(int urlExitFlag) {
		this.urlExitFlag = urlExitFlag;
	}

	public String getBetaURL() {
		return betaURL;
	}

	public void setBetaURL(String betaURL) {
		this.betaURL = betaURL;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}
}