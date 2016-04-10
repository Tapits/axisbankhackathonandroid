package com.reva.data;

public class GeneralResponse {
	private boolean status;
	private String message;
	private Object data;
	private long statusCode;

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

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	public long getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(long statusCode) {
		this.statusCode = statusCode;
	}

	public GeneralResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GeneralResponse(boolean status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public GeneralResponse(boolean status, String message, Object data) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public GeneralResponse(boolean status, String message, long statusCode, Object data) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
		this.statusCode = statusCode;
	}

	@Override
	public String toString() {
		return "GeneralResponse [status=" + status + ", message=" + message
				+ ", data=" + data + "]";
	}

}
