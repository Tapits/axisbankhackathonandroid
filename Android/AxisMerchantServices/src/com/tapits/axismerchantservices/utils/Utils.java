package com.tapits.axismerchantservices.utils;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.tapits.axismerchantservices.R;
import com.tapits.axismerchantservices.SplashScreen;

public class Utils {

	private static final ObjectMapper mapper = new ObjectMapper();

	public static Object parseResponse(InputStream is, Class<?> classOfT)
			throws Exception {
		try {

			return mapper.readValue(is, classOfT);

		} catch (JsonParseException e) {
			Globals.lastErrMsg = Constants.PARSEDISPMSG;
			throw new FingPayException(Constants.PARSEDISPMSG,
					Constants.PARSEDETMSG);
		} catch (Exception e) {
			Globals.lastErrMsg = e.toString();
			throw e;
		}
	}

	public static Object parseResponse(HttpResponse response, Class<?> classOfT)
			throws Exception {
		try {

			InputStream is = response.getEntity().getContent();
			return mapper.readValue(is, classOfT);

		} catch (JsonParseException e) {
			Globals.lastErrMsg = Constants.PARSEDISPMSG;
			throw new FingPayException(Constants.PARSEDISPMSG,
					Constants.PARSEDETMSG);
		} catch (Exception e) {
			Globals.lastErrMsg = e.toString();
			throw e;
		}
	}

	public static void dissmissKeyboard(View v) {
		InputMethodManager imm = (InputMethodManager) v.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
	}

	public static void showKeyboard(View v) {
		InputMethodManager imm = (InputMethodManager) v.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
	}

	public static void showLongToast(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}

	public static void logE(String msg) {
		Log.e(Constants.LOG_TAG, msg);
	}

	public static void logI(String msg) {
		Log.i(Constants.LOG_TAG, msg);
	}

	public static void logD(String msg) {
		Log.d(Constants.LOG_TAG, msg);
	}

	public static String getCurrentTime() {
		return System.currentTimeMillis() + "";
	}

	public static void showToast(Context context, String msg) {

		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	public static boolean isValidString(String str) {
		if (str != null) {
			str = str.trim();
			if (str.length() > 0)
				return true;
		}
		return false;
	}

	public static boolean isValidArrayList(ArrayList<?> list) {
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	public static boolean isEmailValid(CharSequence email) {
		return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
	}

	public static void softKeybordHide(Context context) {
		try {
			((Activity) context).getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		} catch (Exception e) {
		}
	}

	public static void exit(Context context) {
		try {
			((Activity) context).getIntent().addFlags(
					Intent.FLAG_ACTIVITY_CLEAR_TOP
							| Intent.FLAG_ACTIVITY_NO_HISTORY);
			((Activity) context).finish();
		} catch (Exception e) {
		}
		System.exit(0);
	}

	public static boolean getConnectivityStatus(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean isConnected = activeNetwork != null
				&& activeNetwork.isConnected();
		return isConnected;
	}

	public static int getDisplaySize() {
		if (Globals.screenHeight > Globals.screenWidth)
			return Globals.screenWidth;
		else
			return Globals.screenHeight;
	}

	public static void checkScreenSize(Context context) {

		Display display = ((Activity) context).getWindowManager()
				.getDefaultDisplay();

		DisplayMetrics displayMetrics = new DisplayMetrics();

		display.getMetrics(displayMetrics);

		Globals.screenWidth = displayMetrics.widthPixels;
		Globals.screenHeight = displayMetrics.heightPixels;

		Utils.logI("Screen Width : " + Globals.screenWidth + " And Height : "
				+ Globals.screenHeight);

		float xdpi = displayMetrics.xdpi;
		float ydpi = displayMetrics.ydpi;

		double width = Math.pow(Globals.screenWidth / xdpi, 2);
		double height = Math.pow(Globals.screenHeight / ydpi, 2);

		Utils.logI("xdpi : " + xdpi + "ydpi : " + ydpi + "Width : " + width
				+ " And Height : " + height);

		Globals.screenInches = Math.sqrt(width + height);

		Utils.logD("Screen inches : " + Globals.screenInches + "");

	}

	private static ProgressDialog dialog;

	public static ProgressDialog getProgressDialog(Context context) {
		dismissProgressDialog();
		dialog = new ProgressDialog(context, R.style.StyledDialog);

		// dialog.setMessage("Loading. Please wait...");
		SpannableString msg = new SpannableString("Loading. Please wait...");
		msg.setSpan(new ForegroundColorSpan(Color.WHITE), 0, msg.length(), 0);
		dialog.setMessage(msg);
		dialog.setIndeterminateDrawable(context.getResources().getDrawable(
				R.anim.progress_animation));
		dialog.setCancelable(false);
		dialog.show();
		return dialog;
	}

	public static ProgressDialog getProgressDialog(Context context,
			String message) {
		dismissProgressDialog();
		dialog = new ProgressDialog(context); // , R.style.StyledDialog);

		SpannableString msg = new SpannableString(message);
		msg.setSpan(new ForegroundColorSpan(Color.BLACK), 0, msg.length(), 0);

		dialog.setMessage(msg);
		dialog.setIndeterminateDrawable(context.getResources().getDrawable(
				R.anim.progress_animation));
		dialog.setCancelable(false);
		dialog.show();
		return dialog;
	}

	public static void dismissProgressDialog() {
		if (dialog != null && dialog.isShowing() == true)
			dialog.dismiss();
	}

	public static Float convertDpToPixel(float dp, Context context) {
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		Float px = dp * (metrics.densityDpi / 160f);
		return px;
	}

	public static Float convertPixelsToDp(float px, Context context) {
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		Float dp = px / (metrics.densityDpi / 160f);
		return dp;
	}

	public static String getUniqueDeviceId(Context c) {
		WifiManager wifiMan = (WifiManager) c
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInf = wifiMan.getConnectionInfo();
		String macAddr = wifiInf.getMacAddress();
		if (macAddr == null)
			macAddr = "";
		// String androidId = Secure.getString(c.getContentResolver(),
		// Secure.ANDROID_ID);
		// if (androidId == null)
		// androidId = "";
		String unique = macAddr; // + androidId;
		return unique;
	}

	// public static void showSimpleAlert(Activity activity, String msg) {
	// CustomDialog alert = new CustomDialog(activity, msg, false);
	// alert.setTitle(activity.getResources().getString(
	// R.string.alert_dialog_title));
	// alert.show();
	// }

	public static String getDate(Date d, String format) {
		String date = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		date = dateFormat.format(d);
		return date;
	}

	public static Date getDate(String d, String format) {
		Date date = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		try {
			date = dateFormat.parse(d);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	public static String getFormattedPrice(double price) {
		String p = "";
		DecimalFormat decimalFormat = new DecimalFormat(Constants.PRICE_FORMAT);
		p = decimalFormat.format(price);
		return p;
	}

	public static boolean checkForGPS(Context context) {
		boolean isGPSEnabled = false;
		LocationManager manager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			isGPSEnabled = false;
		} else {
			isGPSEnabled = true;
		}

		return isGPSEnabled;
	}

	public static double roundTwoDecimals(double d) {
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		return Double.valueOf(twoDForm.format(d));
	}

	@SuppressWarnings("deprecation")
	public static void generateNotification(final Context context,
			final String message) {
		if (context != null && message != null) {

			int icon = R.drawable.ic_launcher;
			long when = System.currentTimeMillis();
			String title = context.getString(R.string.app_name);

			Intent intent = new Intent(context, SplashScreen.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.putExtra(Constants.FABRICEXPERT_EXTRA_MESSAGE, message);

			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
					intent, PendingIntent.FLAG_UPDATE_CURRENT);

			Notification notification = new Notification(icon, message, when);
			notification.setLatestEventInfo(context, title
					+ " sent you a message", message, pendingIntent);
			notification.defaults = Notification.DEFAULT_ALL;
			notification.flags |= Notification.FLAG_AUTO_CANCEL;

			NotificationManager notificationManager = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);
			notificationManager.notify(0, notification);

			// Utils.logD("GenerateNotification() successfully, Message : "
			// + message);

		} else {
			Utils.logE(message);
		}
	}

	public static void generateMessage(Context context, String receivedMessage) {
		Utils.generateNotification(context, receivedMessage);

		try {
			context.sendBroadcast(new Intent().setAction(
					Constants.FABRICEXPERT_DISPLAY_MESSAGE_ACTION).putExtra(
					Constants.FABRICEXPERT_EXTRA_MESSAGE, receivedMessage));
		} catch (Exception e) {
			Utils.logE(e.toString());
		}

	}

}
