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
import android.widget.RadioGroup;
import android.widget.TextView;

import com.custom.CustomDialog;
import com.tapits.axismerchantservices.data.AxisBankUtils;
import com.tapits.axismerchantservices.data.DeclaractionFormData;
import com.tapits.axismerchantservices.data.MerchantResponse;
import com.tapits.axismerchantservices.utils.Globals;
import com.tapits.axismerchantservices.utils.HttpRequest;
import com.tapits.axismerchantservices.utils.Utils;

public class DeclarationScreen extends Activity {
	private Context context;

	private RadioGroup decRg;

	private Button nextBtn;

	private CustomDialog errDlg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.declaration_form);

		context = DeclarationScreen.this;

		decRg = (RadioGroup) findViewById(R.id.rg_declaration);

		nextBtn = (Button) findViewById(R.id.btn_next);
		nextBtn.setOnClickListener(listener);

		TextView refTv = (TextView) findViewById(R.id.tv_ref_number);
		if (Utils.isValidString(Globals.refId))
			refTv.setText("Reference Number : " + Globals.refId);
	}

	private View.OnClickListener listener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_next:

				if (decRg.getCheckedRadioButtonId() == R.id.rb_disagree)
					Utils.showToast(context, "Please Agree to proceed further");
				else {
					DeclaractionFormData formData = new DeclaractionFormData();

					if (Utils.isValidString(Globals.refId))
						formData.setMerchantRegisrationRefNumber(Globals.refId);

					formData.setTimesatmp(new Date().getTime());

					new SubmitTask().execute(formData);

				}

				break;

			default:
				break;
			}
		}
	};

	class SubmitTask extends AsyncTask<DeclaractionFormData, Object, Object> {

		@Override
		protected void onPreExecute() {
			Utils.dismissProgressDialog();
			Globals.lastErrMsg = "";
			Utils.getProgressDialog(context);
		}

		@Override
		protected Object doInBackground(DeclaractionFormData... params) {
			try {

				String url = AxisBankUtils.getSaveDeclUrl();
				DeclaractionFormData formData = params[0];

				if (Utils.isValidString(url)) {

					ObjectMapper mapper = new ObjectMapper();
					String data = mapper.writeValueAsString(formData);

					if (Utils.isValidString(data)) {
						HttpResponse response = HttpRequest.postData(url, data);
						if (response != null) {
							MerchantResponse registrationResponse = (MerchantResponse) Utils
									.parseResponse(response,
											MerchantResponse.class);
							if (registrationResponse != null) {
								if (registrationResponse.isStatus()) {
									Utils.logD(registrationResponse.toString());
									Globals.mData = registrationResponse
											.getData();
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
				if (Globals.mData != null) {
					Intent intent = new Intent(context,
							VerificationScreen.class);
					startActivity(intent);
				}
			}
			Utils.dismissProgressDialog();
			super.onPostExecute(result);
		}
	}

	@SuppressWarnings("deprecation")
	private boolean showErrorDialog() {
		boolean isNotErr = true;
		if (Globals.lastErrMsg != null && Globals.lastErrMsg.length() > 0) {
			errDlg = new CustomDialog(DeclarationScreen.this,
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
