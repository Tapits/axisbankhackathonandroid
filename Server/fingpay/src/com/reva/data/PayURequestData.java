package com.reva.data;

import java.util.List;

public class PayURequestData {
	private String notifyUrl;
	private String customerIp;
	private String merchantPosId;
	private String description;
	private String currencyCode;
	private String totalAmount;
	private String extOrderId;
	private Buyer buyer;
	private List<Products> products;

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getCustomerIp() {
		return customerIp;
	}

	public void setCustomerIp(String customerIp) {
		this.customerIp = customerIp;
	}

	public String getMerchantPosId() {
		return merchantPosId;
	}

	public void setMerchantPosId(String merchantPosId) {
		this.merchantPosId = merchantPosId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getExtOrderId() {
		return extOrderId;
	}

	public void setExtOrderId(String extOrderId) {
		this.extOrderId = extOrderId;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public List<Products> getProducts() {
		return products;
	}

	public void setProducts(List<Products> products) {
		this.products = products;
	}

}
