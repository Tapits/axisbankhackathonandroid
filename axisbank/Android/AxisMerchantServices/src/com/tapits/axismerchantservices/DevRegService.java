package com.tapits.axismerchantservices;

/**
 * 
 * Developed by Swathi Lolla
 * 
 * Email: slolla@revamobile.com
 * 
 * Date: March 29, 2016
 * 
 * All code (c) 2011 Reva Tech Solutions (India) Private Limited (Reva Mobile)
 * 
 * All rights reserved
 * 
 * 
 */

import java.util.Date;

import javax.sql.DataSource;

import org.apache.http.HttpResponse;
import org.codehaus.jackson.map.ObjectMapper;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.tapits.axismerchantservices.data.AxisBankUtils;
import com.tapits.axismerchantservices.data.DeviceRegisterData;
import com.tapits.axismerchantservices.data.GeneralResponse;
import com.tapits.axismerchantservices.utils.Globals;
import com.tapits.axismerchantservices.utils.HttpRequest;
import com.tapits.axismerchantservices.utils.Utils;

public class DevRegService extends IntentService {
	private DataSource dataSource;

	public DevRegService() {
		super("DevRegService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		new RegDevice().execute();
	}

	class RegDevice extends AsyncTask<Object, Object, Object> {
		@Override
		protected void onPreExecute() {
			Globals.lastErrMsg = "";
		}

		@Override
		protected Object doInBackground(Object... params) {

			try {

				DeviceRegisterData model = new DeviceRegisterData();
				model.setPushToken(Globals.registrationId);
				model.setMacAddr(Utils
						.getUniqueDeviceId(getApplicationContext()));
				model.setMake(Build.MANUFACTURER);
				model.setModel(Build.MODEL);

				TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
				String imei = telephonyManager.getDeviceId();
				if (Utils.isValidString(imei))
					model.setImei(imei);

				model.setRegisteredTimestamp(new Date().getTime());

				ObjectMapper mapper = new ObjectMapper();
				String data = mapper.writeValueAsString(model);
				if (Utils.isValidString(data)) {
					HttpResponse response = HttpRequest.postData(
							AxisBankUtils.getSaveDevRegUrl(), data);
					if (response != null) {
						GeneralResponse deviceRegistrationResponse = (GeneralResponse) Utils
								.parseResponse(response, GeneralResponse.class);
						if (deviceRegistrationResponse != null
								&& deviceRegistrationResponse.isStatus()) {
							Utils.logD(deviceRegistrationResponse.toString());
						} else {
							String msg = deviceRegistrationResponse
									.getMessage();
							if (Utils.isValidString(msg))
								Utils.logE(msg);
						}
					}
				}

			} catch (Exception e) {
				Utils.logE(e.toString());
			}

			return null;
		}

		@Override
		protected void onPostExecute(Object result) {

			super.onPostExecute(result);
		}
	}

}
