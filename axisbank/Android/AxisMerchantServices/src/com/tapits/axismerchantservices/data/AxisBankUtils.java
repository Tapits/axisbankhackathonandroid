package com.tapits.axismerchantservices.data;

public class AxisBankUtils {
	public static final String baseUrl = "http://23.92.60.217:8080/fingpay";

	public static final String getRefUrlTag = "/getMerchantRegisrationRefNumber";
	public static final String saveDevRegUrlTag = "/saveDeviceRegistration";
	public static final String saveMerchUrlTag = "/saveMerchantRegistrationFromData";
	public static final String saveOwnerUrlTag = "/saveOwnershipDetails";
	public static final String saveofflineUrlTag = "/SavePosOfflineFormData";
	public static final String saveOnlineUrlTag = "/savePosOnlineFormData";
	public static final String saveDeclUrlTag = "/saveDeclaractionFormData";
	public static final String saveSettlementTag = "/saveSettlementFormData";

	public static String getRefNumUrl() {
		return baseUrl + getRefUrlTag;
	}

	public static String getSaveDevRegUrl() {
		return baseUrl + saveDevRegUrlTag;
	}

	public static String getSaveMerchUrl() {
		return baseUrl + saveMerchUrlTag;
	}

	public static String getSaveOwnerUrl() {
		return baseUrl + saveOwnerUrlTag;
	}

	public static String getsaveOfflineUrl() {
		return baseUrl + saveofflineUrlTag;
	}

	public static String getSaveOnlineUrl() {
		return baseUrl + saveOnlineUrlTag;
	}

	public static String getSaveDeclUrl() {
		return baseUrl + saveDeclUrlTag;
	}

	public static String getSettlementUrl() {
		return baseUrl + saveSettlementTag;
	}

}
