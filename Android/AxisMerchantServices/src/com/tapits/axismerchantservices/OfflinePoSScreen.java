package com.tapits.axismerchantservices;

import java.util.Date;

import org.apache.http.HttpResponse;
import org.codehaus.jackson.map.ObjectMapper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.custom.CustomDialog;
import com.tapits.axismerchantservices.data.AxisBankUtils;
import com.tapits.axismerchantservices.data.GeneralResponse;
import com.tapits.axismerchantservices.data.PosOfflineFormData;
import com.tapits.axismerchantservices.utils.Globals;
import com.tapits.axismerchantservices.utils.HttpRequest;
import com.tapits.axismerchantservices.utils.Utils;

public class OfflinePoSScreen extends Activity {
	private Context context;

	private Spinner posSp, staSp, freqSp, formatSp; // , vasSp;

	private Button nextBtn;

	private CustomDialog errDlg;

	private CheckBox dccCb, contactCb, emiCB, motoCb, standingCb, cashCb,
			othersCb;

	private EditText othersEt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.offline_pos);

		context = OfflinePoSScreen.this;

		othersEt = (EditText) findViewById(R.id.et_others);

		TextView refTv = (TextView) findViewById(R.id.tv_ref_number);
		if (Utils.isValidString(Globals.refId))
			refTv.setText("Reference Number : " + Globals.refId);

		posSp = (Spinner) findViewById(R.id.sp_type_of_pos);
		staSp = (Spinner) findViewById(R.id.sp_type_of_sta);
		freqSp = (Spinner) findViewById(R.id.sp_freq);
		formatSp = (Spinner) findViewById(R.id.sp_format);
		// vasSp = (Spinner) findViewById(R.id.sp_vas);

		nextBtn = (Button) findViewById(R.id.btn_next);
		nextBtn.setOnClickListener(listener);

		dccCb = (CheckBox) findViewById(R.id.cb_dcc);
		contactCb = (CheckBox) findViewById(R.id.cb_contactless);
		emiCB = (CheckBox) findViewById(R.id.cb_emi);
		motoCb = (CheckBox) findViewById(R.id.cb_moto);
		standingCb = (CheckBox) findViewById(R.id.cb_standing_instructions);
		cashCb = (CheckBox) findViewById(R.id.cb_cashpos);
		othersCb = (CheckBox) findViewById(R.id.cb_others);

		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		othersCb = (CheckBox) findViewById(R.id.cb_others);

		othersCb.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked)
					othersEt.setVisibility(View.VISIBLE);
				else
					othersEt.setVisibility(View.GONE);
			}

		});

	}

	private View.OnClickListener listener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_next:

				Utils.dissmissKeyboard(othersEt);

				String s3 = posSp.getSelectedItem().toString();
				String s4 = staSp.getSelectedItem().toString();
				String s5 = freqSp.getSelectedItem().toString();
				String s6 = formatSp.getSelectedItem().toString();
				// String s7 = vasSp.getSelectedItem().toString();

				String s7 = "";

				if (dccCb.isChecked()) {
					s7 = getString(R.string.dcc);
				}

				if (contactCb.isChecked()) {
					if (Utils.isValidString(s7))
						s7 = s7 + "," + getString(R.string.contactless);
					else
						s7 = getString(R.string.contactless);
				}

				if (emiCB.isChecked()) {
					if (Utils.isValidString(s7))
						s7 = s7 + "," + getString(R.string.emi);
					else
						s7 = getString(R.string.emi);
				}

				if (motoCb.isChecked()) {
					if (Utils.isValidString(s7))
						s7 = s7 + "," + getString(R.string.moto);
					else
						s7 = getString(R.string.moto);
				}

				if (standingCb.isChecked()) {
					if (Utils.isValidString(s7))
						s7 = s7 + ","
								+ getString(R.string.standing_instructions);
					else
						s7 = getString(R.string.standing_instructions);
				}

				if (cashCb.isChecked()) {
					if (Utils.isValidString(s7))
						s7 = s7 + "," + getString(R.string.cashpos);
					else
						s7 = getString(R.string.cashpos);
				}

				if (othersCb.isChecked()) {
					String o = othersEt.getText().toString().trim();
					if (Utils.isValidString(s7)) {
						if (Utils.isValidString(o))
							s7 = s7 + "," + o;
					} else {
						if (Utils.isValidString(o))
							s7 = o;
					}
				}

				PosOfflineFormData formData = new PosOfflineFormData();

				if (Utils.isValidString(s3))
					formData.setTypeOfPos(s3);

				if (Utils.isValidString(s4))
					formData.setStatement(s4);

				if (Utils.isValidString(s5))
					formData.setFrequency(s5);

				if (Utils.isValidString(s6))
					formData.setFormat(s6);

				if (Utils.isValidString(s7))
					formData.setVas(s7);

				if (Utils.isValidString(Globals.refId))
					formData.setMerchantRegisrationId(Globals.refId);

				formData.setTimestamp(new Date().getTime());

				new SubmitTask().execute(formData);

				break;

			default:
				break;
			}
		}
	};

	class SubmitTask extends AsyncTask<PosOfflineFormData, Object, Object> {

		@Override
		protected void onPreExecute() {
			Utils.dismissProgressDialog();
			Globals.lastErrMsg = "";
			Utils.getProgressDialog(context);
		}

		@Override
		protected Object doInBackground(PosOfflineFormData... params) {
			try {

				String url = AxisBankUtils.getsaveOfflineUrl();
				PosOfflineFormData formData = params[0];

				if (Utils.isValidString(url)) {

					ObjectMapper mapper = new ObjectMapper();
					String data = mapper.writeValueAsString(formData);

					if (Utils.isValidString(data)) {
						HttpResponse response = HttpRequest.postData(url, data);
						if (response != null) {
							GeneralResponse registrationResponse = (GeneralResponse) Utils
									.parseResponse(response,
											GeneralResponse.class);
							if (registrationResponse != null) {
								if (registrationResponse.isStatus()) {
									Utils.logD(registrationResponse.toString());
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
				Intent intent = new Intent(context, SettlementScreen.class);
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
			errDlg = new CustomDialog(OfflinePoSScreen.this,
					Globals.lastErrMsg, false);
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
