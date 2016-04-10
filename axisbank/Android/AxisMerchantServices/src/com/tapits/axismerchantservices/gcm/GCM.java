package com.tapits.axismerchantservices.gcm;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import com.google.android.gcm.GCMRegistrar;
import com.tapits.axismerchantservices.utils.Globals;
import com.tapits.axismerchantservices.utils.Utils;

public class GCM {

	private Context context;

	public AsyncTask<Void, Void, Void> mRegisterTask;

	public GCM(Context context) {
		super();
		this.context = context;
		invokeGoogleCloudMessaging();

	}

	private void invokeGoogleCloudMessaging() {

		if (isValidVersion()) {
			try {
				GCMRegistrar.checkDevice(context);
				GCMRegistrar.checkManifest(context);

				Globals.registrationId = GCMRegistrar
						.getRegistrationId(context);
				if (Globals.registrationId.equals("")) {
					GCMRegistrar.register(context, GCMUtils.SENDER_ID);
					try {
						wait(1000);
					} catch (Exception e) {
					}
				}

				if (Utils.isValidString(Globals.registrationId)) {
					Utils.logD("Reg Id : " + Globals.registrationId);
				}

			} catch (Exception e) {
				Utils.logE(e.toString());
			}

		} else
			Utils.logE("GCM Check for Build.VERSION.SDK_INT > Build.VERSION_CODES.ECLAIR_MR1 : "
					+ isValidVersion());
	}

	private boolean isValidVersion() {
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ECLAIR_MR1)
			return true;
		return false;
	}

	public void destroy() {

		if (isValidVersion()) {

			if (mRegisterTask != null)
				mRegisterTask.cancel(true);

			try {
				GCMRegistrar.onDestroy(context);

			} catch (Exception e) {
				e.printStackTrace();

			}

		}

	}

}
