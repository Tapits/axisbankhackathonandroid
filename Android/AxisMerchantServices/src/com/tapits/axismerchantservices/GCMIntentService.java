package com.tapits.axismerchantservices;

import android.content.Context;
import android.content.Intent;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;
import com.tapits.axismerchantservices.gcm.GCMUtils;
import com.tapits.axismerchantservices.utils.Constants;
import com.tapits.axismerchantservices.utils.Globals;
import com.tapits.axismerchantservices.utils.Utils;

public class GCMIntentService extends GCMBaseIntentService {

	public static final String EXTRA_MESSAGE = "message";

	public GCMIntentService() {
		super(GCMUtils.SENDER_ID);
	}

	@Override
	protected void onRegistered(Context context, String registrationId) {
		Utils.logD("Device registered: regId = " + registrationId);
		Globals.registrationId = registrationId;
		// dataSource = new DataSource(context);
		// String id = dataSource.shardPreferences
		// .getValue(Constants.DEVICE_ID_PREF);
		// int mId = -1;
		// if (Globals.merchantMasterData != null)
		// mId = Globals.merchantMasterData.getId();
		//
		// if (!Utils.isValidString(id) && mId != -1) {
		Intent intent = new Intent(context, DevRegService.class);
		startService(intent);
		// }
	}

	@Override
	protected void onUnregistered(Context context, String registrationId) {
		GCMRegistrar.setRegisteredOnServer(context, false);
	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		String receivedMessage = null;

		try {
			receivedMessage = intent.getExtras().getString(
					Constants.FABRICEXPERT_EXTRA_MESSAGE);
			if (Utils.isValidString(receivedMessage)) {
				Utils.generateMessage(context, receivedMessage);
			}

		} catch (Exception e) {
			Utils.logE("Received Message Error : " + e.toString());
		}

	}

	@Override
	protected void onDeletedMessages(Context context, int total) {
		Utils.logD("Received deleted messages notification");
	}

	@Override
	public void onError(Context context, String errorId) {
		Utils.logE("Received error: " + errorId);
	}

	@Override
	protected boolean onRecoverableError(Context context, String errorId) {
		Utils.logE("Received recoverable error: " + errorId);
		return super.onRecoverableError(context, errorId);
	}

}
