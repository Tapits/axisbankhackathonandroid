package com.tapits.axismerchantservices.data;

public class MerchantResponse {
	private boolean status;
	private String message;
	private MerchantRegisrationIdData data;
	private long statusCode;

	public MerchantResponse() {
		super();
	}

	public MerchantResponse(boolean status, String message,
			MerchantRegisrationIdData data, long statusCode) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
		this.statusCode = statusCode;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MerchantRegisrationIdData getData() {
		return data;
	}

	public void setData(MerchantRegisrationIdData data) {
		this.data = data;
	}

	public long getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(long statusCode) {
		this.statusCode = statusCode;
	}

	@Override
	public String toString() {
		return "MerchantResponse [status=" + status + ", message=" + message
				+ ", data=" + data + ", statusCode=" + statusCode + "]";
	}

}
