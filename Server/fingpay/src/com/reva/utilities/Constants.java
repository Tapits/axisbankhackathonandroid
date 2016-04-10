package com.reva.utilities;

import com.google.gson.JsonElement;
import com.reva.data.GeneralResponse;

public class Constants {

	public static final String CONTENT_TYPE_JSON = "application/json";
	public static final String CONTENT_TYPE_CSV = "text/csv";
	public static final String CHARACTER_ENCODING_UTF_8 = "UTF-8";

	public static final String SHARE_APP_URL_ANDROID = "SHARE_APP_URL_ANDROID";
	public static final String SHARE_APP_URL_IOS = "SHARE_APP_URL_IOS";
	public static final String SHARE_APP_URL_PC = "SHARE_APP_URL_PC";

	public final static String URL_PARAMETER_ACTION_VALUE_CREATE_UPDATE = "create";
	public final static String URL_PARAMETER_ACTION_VALUE_DELETE = "delete";
	public final static String URL_PARAMETER_ACTION_VALUE_SUBMIT = "submit";

	public final static String URL_PARAMETER_ACTION_VALUE_GET = "get";
	public final static String URL_PARAMETER_ACTION_VALUE_RESET = "reset";
	public final static String URL_PARAMETER_ACTION_VALUE_ADD = "add";

	public final static String ERROR_SHARE_APP_URL = "Share url not available";

	public final static String ERROR_UNKNOWN = "Unknown";
	public final static String ERROR_URL_PARAMETER = "Missing request parameter(s)";
	public final static String ERROR_PARSING_REQUEST_DATA = "Error parsing request data ";
	public final static String ERROR_REQUEST_DATA_NOT_VALID = "Request data not valid";

	public final static String ERROR_URL_ACTION_PARAMETER = "Action missing in the request";
	public final static String ERROR_INVAILD_ACTION = "Invalid action";

	public final static String ERROR_RECORD_NOT_FOUND = "Record not found";
	public final static String ERROR_REMOVE_RECORD = "Problem in deleting record";

	public final static String ERROR_URL_PASSWORD_PARAMETER = "Password missing in the request";

	public static final String IMAGE_PATH = "";
	public static final boolean TRUE = true;
	public static final boolean FALSE = false;
	public static final String ACTIVATION_FAILURE = "Activation code is not valid";
	public static final String CORRECT_SESSION_NO_USER_SESSION_AVAILABLE = "No User Session availabel";
	public static final String CORRECT_SESSION_USER_SESSION_CLEARED = "User Session Cleared";
	public static final String ERRORS_SESSION_NOT_CLEARED = "Some error occured in session clearance";
	public static final String UESR_TYPE = "usertype";
	public static final String REQUEST_COMPLETED = "Request Completed";
	public static final String ERRORS_EXCEPTION_IN_SERVER = "Exception in server";
	public static final String ERROR_REQUEST_ITEM_TYPE_NOT_VALID = "Item type not valid";
	public static final String URL_PARAMETER_FROM_DATE = "fromDate";
	public static final String URL_PARAMETER_TO_DATE = "toDate";

	public static final String SUCCESSFUL = "successful";
	public static final String FAILED = "failed";
	public static final String ERROR_INCOMPLETE_DATA = "Incomplete data sent to the server";
	public static final String ERROR_NO_DATA = "No Devices Available for Requested Data";
	public static final String NO_DATA_AVAILABLE = "No data available for the requested data.";
	public static final Object ADS = "AD";
	public static final Object NEWS = "NEWS";
	public static final String ERROR_USER_AUTHENTICATION = "Incorrect username or password";
	public static final String ERROR_AUTHENTICATION = "Incorrect merchantId or pin";
	public static final String NO_LOCATION = "Location not available";
	public static final String ERROR_NO_DATA_AVAILABLE = "No Data Available";
	public static final String ERROR_INVAILD_MERCHANT_ID = "Invalid Merchant id";
	public static final String ERROR_INVAILD_MERCHANT_PIN = "Invalid Merchant Pin";
	private static final String ERROR_INVAILD_SESSION = "Signout/Session Expired";
	public static final String ERROR_INVAILD_MERCHANT_DATA = "Invalid Merchant Data";
	public static final String ERRORS_TRANSACTION_EXCEPTION = "Transaction Failed";
	public static final String ERRORS_INVALID_TRANSACTION = "Invalid Transaction";
	public static final String DES_ALG = "DESede/CBC/PKCS5Padding";
	public static final String DES_KEY = "DESede";
	public static final String ERROR_TRANSACTION_LIMIT = "Transaction amount limit exceeded";
	public static final String OTP_SUCCESS = "OTP is successfully sent to your registered mobile number";
	public static final String OTP_FAIL = "Error while sending OTP";
	public static final String ERROR_WRONG_OTP = "Please check your OTP and retry";	
	public static final String ERROR_INVAILD_MERCHANT_PHONE = "Please enter your registered mobile number";
	
	
	public static final long CORRECT_STATUS_CODE = 10000;
	public static final long ERROR_TRANSACTION_LIMIT_STATUS_CODE = 10001;
	public static final long INVALID_STATUS_CODE = 10002;
	public static final long SERVER_TIME_OUT_STATUS_CODE = 10003;
	public static final long EXCEPTION_IN_SERVER_STATUS_CODE = 10004;
	public static final long ERROR_INCOMPLETE_DATA_STATUS_CODE = 10005;
	public static final long ERROR_INVAILD_SESSION_STATUS_CODE = 10006;
	public static final long OTP_SUCCESS_STATUS_CODE = 10007;
	public static final long OTP_FAIL_STATUS_CODE = 10008;
	public static final long ERROR_WRONG_OTP_STATUS = 10009;
	
	public static GeneralResponse INVALID_SEESION = new GeneralResponse(TRUE, ERROR_INVAILD_SESSION, ERROR_INVAILD_SESSION_STATUS_CODE,null);
}
