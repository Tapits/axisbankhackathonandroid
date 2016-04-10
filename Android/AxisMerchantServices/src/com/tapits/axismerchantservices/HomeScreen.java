package com.tapits.axismerchantservices;

import org.apache.http.HttpResponse;
import org.codehaus.jackson.map.ObjectMapper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.custom.CustomDialog;
import com.tapits.axismerchantservices.data.AxisBankUtils;
import com.tapits.axismerchantservices.data.GeneralResponse;
import com.tapits.axismerchantservices.data.GetMerchantRegisrationRefNumberData;
import com.tapits.axismerchantservices.utils.Globals;
import com.tapits.axismerchantservices.utils.HttpRequest;
import com.tapits.axismerchantservices.utils.Utils;

public class HomeScreen extends Activity implements OnClickListener {
	private Context context;

	private Button onlineBtn, offlineBtn, appBtn, aggBtn;

	private CustomDialog errDlg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_screen);

		context = HomeScreen.this;

		onlineBtn = (Button) findViewById(R.id.btn_online_reg);
		onlineBtn.setOnClickListener(this);

		offlineBtn = (Button) findViewById(R.id.btn_offline_reg);
		offlineBtn.setOnClickListener(this);

		appBtn = (Button) findViewById(R.id.btn_complete_form);
		appBtn.setOnClickListener(this);

		aggBtn = (Button) findViewById(R.id.btn_aggregators_area);
		aggBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		Globals.refId = "";
		switch (v.getId()) {
		case R.id.btn_online_reg:
			Globals.type = 1;
			// intent = new Intent(context, BasicInfoScreen.class);
			new RefTask().execute();
			break;

		case R.id.btn_offline_reg:
			Globals.type = 2;
			intent = new Intent(context, OfflineScreen.class);
			startActivity(intent);
			break;

		case R.id.btn_complete_form:
			intent = new Intent(context, StatusScreen.class);
			startActivity(intent);
			break;

		case R.id.btn_aggregators_area:
			intent = new Intent(context, AggregatorScreen.class);
			startActivity(intent);
			break;

		default:
			break;
		}

	}

	class RefTask extends AsyncTask<Object, Object, Object> {

		@Override
		protected void onPreExecute() {
			Utils.dismissProgressDialog();
			Globals.lastErrMsg = "";
			Utils.getProgressDialog(context);
		}

		@Override
		protected Object doInBackground(Object... params) {
			try {

				String url = AxisBankUtils.getRefNumUrl();
				GetMerchantRegisrationRefNumberData mData = new GetMerchantRegisrationRefNumberData();
				mData.setRegType(1);
				mData.setCreaditCardFlag(2);
				mData.setDebitCardFlag(2);
				mData.setMobileAppilication(2);
				mData.setNetBakingFlag(2);

				if (Utils.isValidString(url)) {

					ObjectMapper mapper = new ObjectMapper();
					String data = mapper.writeValueAsString(mData);

					if (Utils.isValidString(data)) {
						HttpResponse response = HttpRequest.postData(url, data);
						if (response != null) {
							GeneralResponse registrationResponse = (GeneralResponse) Utils
									.parseResponse(response,
											GeneralResponse.class);
							if (registrationResponse != null) {
								if (registrationResponse.isStatus()) {
									Utils.logD(registrationResponse.toString());
									Globals.refId = registrationResponse
											.getData().toString();
								} else {
									Globals.lastErrMsg = registrationResponse
											.getMessage();
								}
							} else {
								Globals.lastErrMsg = "Response is null";
							}
						} else {
							Globals.lastErrMsg = "Response is null";
						}
					}
				}

			} catch (Exception e) {
				if (!Utils.isValidString(Globals.lastErrMsg))
					Globals.lastErrMsg = e.toString();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Object result) {
			if (showErrorDialog()) {
				Intent intent = new Intent(context, BasicInfoScreen.class);
				startActivity(intent);
			}
			Utils.dismissProgressDialog();
			super.onPostExecute(result);
		}
	}

	@SuppressWarnings("deprecation")
	private boolean showErrorDialog() {
		boolean isNotErr = true;
		if (Globals.lastErrMsg != null && Globals.lastErrMsg.length() > 0) {
			errDlg = new CustomDialog(HomeScreen.this, Globals.lastErrMsg,
					false);
			errDlg.setTitle(getString(R.string.alert_dialog_title));
			errDlg.setCancelable(false);
			Globals.lastErrMsg = "";
			Utils.dismissProgressDialog();
			isNotErr = false;
			errDlg.show();
		}
		return isNotErr;
	}

}
