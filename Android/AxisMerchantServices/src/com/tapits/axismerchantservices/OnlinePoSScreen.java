package com.tapits.axismerchantservices;

import java.io.File;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.codehaus.jackson.map.ObjectMapper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.custom.CustomDialog;
import com.revamobile.lib.eums.FileType;
import com.revamobile.lib.fileutils.FileCache;
import com.tapits.axismerchantservices.data.AxisBankUtils;
import com.tapits.axismerchantservices.data.GeneralResponse;
import com.tapits.axismerchantservices.data.OnlinePosFormData;
import com.tapits.axismerchantservices.utils.Globals;
import com.tapits.axismerchantservices.utils.HttpRequest;
import com.tapits.axismerchantservices.utils.Utils;

public class OnlinePoSScreen extends Activity {
	private Context context;

	private EditText websiteEt, paymentEt, betaEt;
	private Spinner langSp, freqSp, formatSp; // cardSp, vasSp;

	private RadioGroup webRg;
	private ImageView planIv;

	private LinearLayout noWebLayout;

	private Button nextBtn, uploadBtn;

	private CustomDialog errDlg;

	private boolean exists = true;

	private final int CAPTURE_PHOTO = 0;
	private final String PHOTO_TEMP = "plan_temp";

	private String photoBase64 = "";

	private CheckBox visaCb, masterCb, maestroCb, dccCb, contactCb, emiCB,
			motoCb, standingCb, cashCb, othersCb;

	private EditText othersEt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.online_pos);

		context = OnlinePoSScreen.this;

		TextView refTv = (TextView) findViewById(R.id.tv_ref_number);
		if (Utils.isValidString(Globals.refId))
			refTv.setText("Reference Number : " + Globals.refId);

		websiteEt = (EditText) findViewById(R.id.et_website);
		paymentEt = (EditText) findViewById(R.id.et_payment_url);
		betaEt = (EditText) findViewById(R.id.et_beta_url);

		othersEt = (EditText) findViewById(R.id.et_others);

		// cardSp = (Spinner) findViewById(R.id.sp_card);
		langSp = (Spinner) findViewById(R.id.sp_lang);
		freqSp = (Spinner) findViewById(R.id.sp_freq);
		formatSp = (Spinner) findViewById(R.id.sp_format);
		// vasSp = (Spinner) findViewById(R.id.sp_vas);

		noWebLayout = (LinearLayout) findViewById(R.id.layout_no_website);

		webRg = (RadioGroup) findViewById(R.id.rg_website);
		planIv = (ImageView) findViewById(R.id.iv_plan);

		uploadBtn = (Button) findViewById(R.id.btn_upload);
		uploadBtn.setOnClickListener(listener);

		webRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.rb_yes) {
					exists = true;
					noWebLayout.setVisibility(View.GONE);
				} else {
					exists = false;
					noWebLayout.setVisibility(View.VISIBLE);
				}
			}
		});

		nextBtn = (Button) findViewById(R.id.btn_next);
		nextBtn.setOnClickListener(listener);

		visaCb = (CheckBox) findViewById(R.id.cb_visa);
		masterCb = (CheckBox) findViewById(R.id.cb_mastercard);
		maestroCb = (CheckBox) findViewById(R.id.cb_maestro);

		dccCb = (CheckBox) findViewById(R.id.cb_dcc);
		contactCb = (CheckBox) findViewById(R.id.cb_contactless);
		emiCB = (CheckBox) findViewById(R.id.cb_emi);
		motoCb = (CheckBox) findViewById(R.id.cb_moto);
		standingCb = (CheckBox) findViewById(R.id.cb_standing_instructions);
		cashCb = (CheckBox) findViewById(R.id.cb_cashpos);
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
				Utils.dissmissKeyboard(websiteEt);

				String s1 = websiteEt.getText().toString().trim();
				String s2 = paymentEt.getText().toString().trim();
				// String s3 = cardSp.getSelectedItem().toString();
				String s4 = langSp.getSelectedItem().toString();
				String s5 = freqSp.getSelectedItem().toString();
				String s6 = formatSp.getSelectedItem().toString();
				// String s7 = vasSp.getSelectedItem().toString();

				String s8 = betaEt.getText().toString().trim();

				String s3 = "",
				s7 = "";

				if (visaCb.isChecked())
					s3 = getString(R.string.visa);

				if (masterCb.isChecked()) {
					if (Utils.isValidString(s3))
						s3 = s3 + "," + getString(R.string.mastercard);
					else
						s3 = getString(R.string.mastercard);
				}

				if (maestroCb.isChecked()) {
					if (Utils.isValidString(s3))
						s3 = s3 + "," + getString(R.string.maestro);
					else
						s3 = getString(R.string.maestro);
				}

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

				OnlinePosFormData formData = new OnlinePosFormData();

				if (Utils.isValidString(s1))
					formData.setWibsiteUrl(s1);

				if (Utils.isValidString(s2))
					formData.setPaymentUrl(s2);

				if (Utils.isValidString(s3))
					formData.setCard(s3);

				if (Utils.isValidString(s4))
					formData.setInterfaceLanguage(s4);

				if (Utils.isValidString(s5))
					formData.setFrequency(s5);

				if (Utils.isValidString(s6))
					formData.setFormat(s6);

				if (Utils.isValidString(s7))
					formData.setVas(s7);

				if (!exists) {
					formData.setUrlExitFlag(2);
					if (Utils.isValidString(s8))
						formData.setBetaURL(s8);
					if (Utils.isValidString(photoBase64))
						formData.setDocument(photoBase64);
				} else
					formData.setUrlExitFlag(1);

				if (Utils.isValidString(Globals.refId))
					formData.setMerchantRegisrationId(Globals.refId);

				formData.setTimestamp(new Date().getTime());

				new SubmitTask().execute(formData);

				break;

			case R.id.btn_upload:
				takePicture(CAPTURE_PHOTO, PHOTO_TEMP);
				break;

			default:
				break;
			}
		}
	};

	class SubmitTask extends AsyncTask<OnlinePosFormData, Object, Object> {

		@Override
		protected void onPreExecute() {
			Utils.dismissProgressDialog();
			Globals.lastErrMsg = "";
			Utils.getProgressDialog(context);
		}

		@Override
		protected Object doInBackground(OnlinePosFormData... params) {
			try {

				String url = AxisBankUtils.getSaveOnlineUrl();
				OnlinePosFormData formData = params[0];

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
			errDlg = new CustomDialog(OnlinePoSScreen.this, Globals.lastErrMsg,
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

	private void takePicture(int requestCode, String fileName) {
		Uri imageUri = Uri.fromFile(getSavedFile(context, fileName));
		Intent intent = createIntentForCamera(imageUri);
		startActivityForResult(intent, requestCode);
	}

	private Intent createIntentForCamera(Uri imageUri) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
		return intent;
	}

	public static FileCache getFileCache(Context context) {
		return new FileCache(context, "Images", FileType.IMAGE);
	}

	public static File getSavedFile(Context context, String fileName) {
		FileCache fileCache = getFileCache(context);
		File file = null;
		if (fileCache != null) {
			file = fileCache.getFile(fileName);
		}
		return file;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != Activity.RESULT_OK)
			return;

		Bitmap bitmap = null;
		switch (requestCode) {
		case CAPTURE_PHOTO:
			try {

				bitmap = getBitmap(PHOTO_TEMP);
				if (bitmap != null) {
					try {
						photoBase64 = com.revamobile.custom.adfileutils.FileUtils
								.getBase64FromBitmap(bitmap);
					} catch (Throwable e) {
						Utils.logE(e.getMessage());
						e.printStackTrace();
					}
					planIv.setVisibility(View.VISIBLE);
					planIv.setImageBitmap(bitmap);

				} else {
					Utils.logE("PHOTO_TEMP Bitmap is null");
				}

			} catch (Exception e) {
				Utils.logE("PHOTO_TEMP ERROR : " + e.toString());
			}

			break;

		}
	}

	private Bitmap getBitmap(String fileName) {
		Bitmap bitmap = null;
		try {
			if (fileName != null && fileName.length() > 0) {
				File file = getSavedFile(context, fileName);
				if (file != null && file.exists()) {
					Uri uri = Uri.parse(file.getAbsolutePath());

					BitmapFactory.Options opts = new BitmapFactory.Options();
					opts.inJustDecodeBounds = true;
					BitmapFactory.decodeFile(uri.getPath(), opts);
					BitmapFactory.Options thumbopts = new BitmapFactory.Options();
					thumbopts.inSampleSize = 4;

					bitmap = BitmapFactory.decodeFile(uri.getPath(), thumbopts);
					// previewImage.setImageBitmap(bmp);
				}
			}

		} catch (Exception e) {
			Utils.logE(e.toString());
			e.printStackTrace();
		}

		return bitmap;
	}
}
