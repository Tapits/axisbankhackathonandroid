package com.tapits.axismerchantservices;

import java.io.File;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.codehaus.jackson.map.ObjectMapper;

import android.app.Activity;
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
import com.tapits.axismerchantservices.data.MerchantRegistrationFromData;
import com.tapits.axismerchantservices.utils.Globals;
import com.tapits.axismerchantservices.utils.HttpRequest;
import com.tapits.axismerchantservices.utils.Utils;

public class BasicInfoScreen extends Activity {
	private Context context;

	private EditText legalNameEt, marketingNameEt, dateEt, panEt, addEt,
			cityEt, stateEt, pinEt, monthsEt, mobileEt, emailEt;
	private Spinner constSp, modeSp;

	private RadioGroup existingRg;
	private LinearLayout existingAcqLayout;

	private ImageView shopIv;

	private Button okBtn, nextBtn;

	private boolean isExisting = true;

	private final int CAPTURE_PHOTO = 0;
	private final String PHOTO_TEMP = "shopphoto_temp";

	private String photoBase64 = "";

	private CustomDialog errDlg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.basic_details);

		context = BasicInfoScreen.this;

		legalNameEt = (EditText) findViewById(R.id.et_legal_name);
		marketingNameEt = (EditText) findViewById(R.id.et_marketing_name);
		dateEt = (EditText) findViewById(R.id.et_date_of_incorp);
		panEt = (EditText) findViewById(R.id.et_pan_number);
		addEt = (EditText) findViewById(R.id.et_install_add);
		cityEt = (EditText) findViewById(R.id.et_city);
		stateEt = (EditText) findViewById(R.id.et_state);
		pinEt = (EditText) findViewById(R.id.et_pincode);
		monthsEt = (EditText) findViewById(R.id.et_months);
		mobileEt = (EditText) findViewById(R.id.et_mobile);
		emailEt = (EditText) findViewById(R.id.et_email);

		constSp = (Spinner) findViewById(R.id.sp_constitution);
		modeSp = (Spinner) findViewById(R.id.sp_payment_mode);

		existingRg = (RadioGroup) findViewById(R.id.rg_existing_acquirer);
		existingRg
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						if (checkedId == R.id.rb_yes) {
							isExisting = true;
							existingAcqLayout.setVisibility(View.VISIBLE);
						} else {
							isExisting = false;
							existingAcqLayout.setVisibility(View.GONE);
						}
					}
				});

		existingAcqLayout = (LinearLayout) findViewById(R.id.layout_existing_acquirer);

		shopIv = (ImageView) findViewById(R.id.iv_shop);

		okBtn = (Button) findViewById(R.id.btn_ok);
		okBtn.setOnClickListener(listener);

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
			case R.id.btn_ok:
				takePicture(CAPTURE_PHOTO, PHOTO_TEMP);
				break;

			case R.id.btn_next:
				Utils.dissmissKeyboard(legalNameEt);

				String s1 = legalNameEt.getText().toString().trim();
				String s2 = marketingNameEt.getText().toString().trim();
				String s3 = dateEt.getText().toString().trim();
				String s4 = panEt.getText().toString().trim();
				String s5 = addEt.getText().toString().trim();
				String s6 = cityEt.getText().toString().trim();
				String s7 = stateEt.getText().toString().trim();
				String s8 = pinEt.getText().toString().trim();
				String s9 = monthsEt.getText().toString().trim();
				String s10 = constSp.getSelectedItem().toString();
				String s11 = modeSp.getSelectedItem().toString();
				String s12 = mobileEt.getText().toString().trim();
				String s13 = emailEt.getText().toString().trim();

				MerchantRegistrationFromData formData = new MerchantRegistrationFromData();

				if (Utils.isValidString(s1))
					formData.setLegalName(s1);

				if (Utils.isValidString(s2))
					formData.setMarketingNameOrChargeName(s2);

				if (Utils.isValidString(s3)) {
					Date d = Utils.getDate(s3, "dd/MM/yyyy");
					if (d != null)
						formData.setDateOfAccountOpening(d.getTime());
				}

				if (Utils.isValidString(s4))
					formData.setPan(s4);

				if (Utils.isValidString(s5))
					formData.setAddress(s5);

				if (Utils.isValidString(s6))
					formData.setCity(s6);

				if (Utils.isValidString(s7))
					formData.setState(s7);

				if (Utils.isValidString(s8) && s8.length() == 6)
					formData.setPinCode(s8);

				if (Utils.isValidString(s12) && s12.length() == 10)
					formData.setMobileNumber(s12);

				if (Utils.isValidString(s13) && Utils.isEmailValid(s13))
					formData.setEmailAddress(s13);

				if (isExisting) {
					// if (Utils.isValidString(s9))
					formData.setExistingAcquirer("Yes");
				} else
					formData.setExistingAcquirer("No");

				if (Utils.isValidString(s10))
					formData.setConstitution(s10);

				if (Utils.isValidString(s11))
					formData.setPaymentMode(s11);

				if (Utils.isValidString(photoBase64))
					formData.setShopImages(photoBase64);

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

	class SubmitTask extends
			AsyncTask<MerchantRegistrationFromData, Object, Object> {

		@Override
		protected void onPreExecute() {
			Utils.dismissProgressDialog();
			Globals.lastErrMsg = "";
			Utils.getProgressDialog(context);
		}

		@Override
		protected Object doInBackground(MerchantRegistrationFromData... params) {
			try {

				String url = AxisBankUtils.getSaveMerchUrl();
				MerchantRegistrationFromData formData = params[0];

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
				Intent intent = new Intent(context, OwnerShipFormScreen.class);
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
			errDlg = new CustomDialog(BasicInfoScreen.this, Globals.lastErrMsg,
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
					shopIv.setVisibility(View.VISIBLE);
					shopIv.setImageBitmap(bitmap);

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
