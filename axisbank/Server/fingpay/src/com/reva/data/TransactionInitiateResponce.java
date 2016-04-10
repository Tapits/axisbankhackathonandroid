package com.reva.data;

public class TransactionInitiateResponce {
	private String statusCode;
	private String statusMessage;
	private String bankTxnId;// (in case of success)

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public String getBankTxnId() {
		return bankTxnId;
	}

	public void setBankTxnId(String bankTxnId) {
		this.bankTxnId = bankTxnId;
	}

	public TransactionInitiateResponce(String statusCode, String statusMessage,
			String bankTxnId) {
		super();
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
		this.bankTxnId = bankTxnId;
	}

}
