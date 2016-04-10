package com.tapits.axismerchantservices;

import org.apache.http.HttpResponse;
import org.codehaus.jackson.map.ObjectMapper;

import com.custom.CustomDialog;
import com.tapits.axismerchantservices.data.AxisBankUtils;
import com.tapits.axismerchantservices.data.GeneralResponse;
import com.tapits.axismerchantservices.data.GetMerchantRegisrationRefNumberData;
import com.tapits.axismerchantservices.utils.Globals;
import com.tapits.axismerchantservices.utils.HttpRequest;
import com.tapits.axismerchantservices.utils.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class OfflineScreen extends Activity {
	private Context context;

	private CheckBox debitCb, creditCb, netCb, mobileCb;

	private Button nextBtn;

	private CustomDialog errDlg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.debit_credit);

		context = OfflineScreen.this;

		TextView refTv = (TextView) findViewById(R.id.tv_ref_number);
		if (Utils.isValidString(Globals.refId))
			refTv.setText("Reference Number : " + Globals.refId);

		debitCb = (CheckBox) findViewById(R.id.cb_debit);
		creditCb = (CheckBox) findViewById(R.id.cb_credit);
		netCb = (CheckBox) findViewById(R.id.cb_net_banking);
		mobileCb = (CheckBox) findViewById(R.id.cb_mobile_app);

		nextBtn = (Button) findViewById(R.id.btn_next);
		nextBtn.setOnClickListener(listener);
	}

	private View.OnClickListener listener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_next:
				GetMerchantRegisrationRefNumberData mData = new GetMerchantRegisrationRefNumberData();
				mData.setRegType(2);
				if (creditCb.isChecked())
					mData.setCreaditCardFlag(1);
				else
					mData.setCreaditCardFlag(2);

				if (debitCb.isChecked())
					mData.setDebitCardFlag(1);
				else
					mData.setDebitCardFlag(2);

				if (netCb.isChecked())
					mData.setNetBakingFlag(1);
				else
					mData.setNetBakingFlag(2);

				if (mobileCb.isChecked())
					mData.setMobileAppilication(1);
				else
					mData.setMobileAppilication(2);

				new RefTask().execute(mData);

				break;

			default:
				break;
			}
		}
	};

	class RefTask extends
			AsyncTask<GetMerchantRegisrationRefNumberData, Object, Object> {

		@Override
		protected void onPreExecute() {
			Utils.dismissProgressDialog();
			Globals.lastErrMsg = "";
			Utils.getProgressDialog(context);
		}

		@Override
		protected Object doInBackground(
				GetMerchantRegisrationRefNumberData... params) {
			try {

				String url = AxisBankUtils.getRefNumUrl();
				GetMerchantRegisrationRefNumberData mData = params[0];

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
			errDlg = new CustomDialog(OfflineScreen.this, Globals.lastErrMsg,
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
