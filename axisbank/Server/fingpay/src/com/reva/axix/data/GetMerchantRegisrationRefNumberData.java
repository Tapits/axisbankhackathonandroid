package com.reva.axix.data;

public class GetMerchantRegisrationRefNumberData {
	private int regType;// 1 = online, 2 = offline
	private int debitCardFlag;// 1 = yes, 2 = no
	private int creaditCardFlag;
	private int netBakingFlag;
	private int mobileAppilication;

	public int getRegType() {
		return regType;
	}

	public void setRegType(int regType) {
		this.regType = regType;
	}

	public int getDebitCardFlag() {
		return debitCardFlag;
	}

	public void setDebitCardFlag(int debitCardFlag) {
		this.debitCardFlag = debitCardFlag;
	}

	public int getCreaditCardFlag() {
		return creaditCardFlag;
	}

	public void setCreaditCardFlag(int creaditCardFlag) {
		this.creaditCardFlag = creaditCardFlag;
	}

	public int getNetBakingFlag() {
		return netBakingFlag;
	}

	public void setNetBakingFlag(int netBakingFlag) {
		this.netBakingFlag = netBakingFlag;
	}

	public int getMobileAppilication() {
		return mobileAppilication;
	}

	public void setMobileAppilication(int mobileAppilication) {
		this.mobileAppilication = mobileAppilication;
	}
}
