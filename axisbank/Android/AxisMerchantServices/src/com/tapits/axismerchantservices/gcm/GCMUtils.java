package com.tapits.axismerchantservices.gcm;

import com.tapits.axismerchantservices.utils.Utils;

public class GCMUtils {

	public static final String SENDER_ID = "533952286538";

	public static final String gcmRegistrationStatus = "GCMRegistrationStatus";
	public static final String gcmRegistrateredStatus = "GCMRegistrateredStatus";

	public static boolean isVaildData(String referenceName, String data) {

		if (Utils.isValidString(data))
			return true;
		else {
			return false;
		}
	}

}
