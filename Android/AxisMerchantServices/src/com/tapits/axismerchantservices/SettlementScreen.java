package com.tapits.axismerchantservices;

import java.util.Date;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.custom.CustomDialog;
import com.tapits.axismerchantservices.data.AxisBankUtils;
import com.tapits.axismerchantservices.data.GeneralResponse;
import com.tapits.axismerchantservices.data.SettlementFormData;
import com.tapits.axismerchantservices.utils.Globals;
import com.tapits.axismerchantservices.utils.HttpRequest;
import com.tapits.axismerchantservices.utils.Utils;

public class SettlementScreen extends Activity {
	private Context context;

	private EditText accEt;
	private TextView textView;

	private RadioGroup accRg;

	private Button nextBtn;

	private LinearLayout verLayout;
	private EditText amountEt;
	private Button verifyBtn, doneBtn;

	private TextView verifiedTv;

	private CustomDialog errDlg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settlement_form);

		context = SettlementScreen.this;

		TextView refTv = (TextView) findViewById(R.id.tv_ref_number);
		if (Utils.isValidString(Globals.refId))
			refTv.setText("Reference Number : " + Globals.refId);

		accEt = (EditText) findViewById(R.id.et_current_acc);

		accRg = (RadioGroup) findViewById(R.id.rg_acc);

		textView = (TextView) findViewById(R.id.tv_text);
		textView.setVisibility(View.GONE);

		verifyBtn = (Button) findViewById(R.id.btn_verify);
		verifyBtn.setOnClickListener(listener);

		verLayout = (LinearLayout) findViewById(R.id.layout_verf);
		amountEt = (EditText) findViewById(R.id.et_amount);
		doneBtn = (Button) findViewById(R.id.btn_done);
		doneBtn.setOnClickListener(listener);

		verifiedTv = (TextView) findViewById(R.id.tv_verified);

		accRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.rb_no) {
					textView.setText("Your application Reference Number is "
							+ Globals.refId
							+ "\n Please note it down for future reference");
					textView.setVisibility(View.VISIBLE);
				} else {
					textView.setVisibility(View.GONE);
				}
			}
		});

		nextBtn = (Button) findViewById(R.id.btn_next);
		nextBtn.setOnClickListener(listener);
	}

	private View.OnClickListener listener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_next:
				Utils.dissmissKeyboard(accEt);

				String s1 = accEt.getText().toString().trim();

				SettlementFormData formData = new SettlementFormData();

				if (Utils.isValidString(s1))
					formData.setCurrentAccountNumber(s1);

				if (accRg.getCheckedRadioButtonId() == R.id.rb_yes)
					formData.setValidationFlag(1);
				else
					formData.setValidationFlag(2);

				if (Utils.isValidString(Globals.refId))
					formData.setMerchantRegisrationId(Globals.refId);

				formData.setTimesatmp(new Date().getTime());

				formData.setMode(String.valueOf(Globals.type));

				new SubmitTask().execute(formData);

				break;

			case R.id.btn_verify:
				verLayout.setVisibility(View.VISIBLE);
				break;

			case R.id.btn_done:
				verifiedTv.setVisibility(View.VISIBLE);
				break;

			default:
				break;
			}
		}
	};

	class SubmitTask extends AsyncTask<SettlementFormData, Object, Object> {

		@Override
		protected void onPreExecute() {
			Utils.dismissProgressDialog();
			Globals.lastErrMsg = "";
			Utils.getProgressDialog(context);
		}

		@Override
		protected Object doInBackground(SettlementFormData... params) {
			try {

				String url = AxisBankUtils.getSettlementUrl();
				SettlementFormData formData = params[0];

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
				Intent intent = new Intent(context, DeclarationScreen.class);
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
			errDlg = new CustomDialog(SettlementScreen.this,
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
