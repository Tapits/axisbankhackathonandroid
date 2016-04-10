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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.custom.CustomDialog;
import com.revamobile.lib.eums.FileType;
import com.revamobile.lib.fileutils.FileCache;
import com.tapits.axismerchantservices.data.AxisBankUtils;
import com.tapits.axismerchantservices.data.GeneralResponse;
import com.tapits.axismerchantservices.data.OwnershipDetailData;
import com.tapits.axismerchantservices.utils.Globals;
import com.tapits.axismerchantservices.utils.HttpRequest;
import com.tapits.axismerchantservices.utils.Utils;

public class OwnerShipFormScreen extends Activity {
	private Context context;

	private EditText nameEt, mobileEt, panEt, landEt, addEt, pinEt, aadhaarEt;
	private ImageView photoIv;

	private Button okBtn, nextBtn;

	private final int CAPTURE_PHOTO = 0;
	private final String PHOTO_TEMP = "photo_temp";

	private String photoBase64 = "";

	private CustomDialog errDlg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ownership_form);

		context = OwnerShipFormScreen.this;

		TextView refTv = (TextView) findViewById(R.id.tv_ref_number);
		if (Utils.isValidString(Globals.refId))
			refTv.setText("Reference Number : " + Globals.refId);

		nameEt = (EditText) findViewById(R.id.et_owner_name);
		mobileEt = (EditText) findViewById(R.id.et_mobile);
		panEt = (EditText) findViewById(R.id.et_pan);
		landEt = (EditText) findViewById(R.id.et_land_number);
		addEt = (EditText) findViewById(R.id.et_res_add);
		pinEt = (EditText) findViewById(R.id.et_pin);
		aadhaarEt = (EditText) findViewById(R.id.et_aadhaar);

		photoIv = (ImageView) findViewById(R.id.iv_photo);

		okBtn = (Button) findViewById(R.id.btn_ok);
		okBtn.setOnClickListener(listener);

		nextBtn = (Button) findViewById(R.id.btn_next);
		nextBtn.setOnClickListener(listener);

	}

	private View.OnClickListener listener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_ok:
				takePicture(CAPTURE_PHOTO, PHOTO_TEMP);
				break;

			case R.id.btn_next:
				Utils.dissmissKeyboard(nameEt);

				String s1 = nameEt.getText().toString().trim();
				String s2 = mobileEt.getText().toString().trim();
				String s3 = landEt.getText().toString().trim();
				String s4 = panEt.getText().toString().trim();
				String s5 = addEt.getText().toString().trim();
				String s6 = pinEt.getText().toString().trim();
				String s7 = aadhaarEt.getText().toString().trim();

				OwnershipDetailData formData = new OwnershipDetailData();

				if (Utils.isValidString(s1))
					formData.setNameOfOwnerPartnerOrDirector(s1);

				if (Utils.isValidString(s2) && s2.length() == 10)
					formData.setMobileNumber(s2);

				if (Utils.isValidString(s3) && s3.length() == 10)
					formData.setLandlineNumber(s3);

				if (Utils.isValidString(s4))
					formData.setPan(s4);

				if (Utils.isValidString(s5))
					formData.setResidentialAddress(s5);

				if (Utils.isValidString(s6) && s6.length() == 6)
					formData.setPinCode(s6);

				if (Utils.isValidString(s7) && s7.length() == 12)
					formData.setAadharCard(s7);

				if (Utils.isValidString(photoBase64))
					formData.setPhotoUploaded(photoBase64);

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

	class SubmitTask extends AsyncTask<OwnershipDetailData, Object, Object> {

		@Override
		protected void onPreExecute() {
			Utils.dismissProgressDialog();
			Globals.lastErrMsg = "";
			Utils.getProgressDialog(context);
		}

		@Override
		protected Object doInBackground(OwnershipDetailData... params) {
			try {

				String url = AxisBankUtils.getSaveOwnerUrl();
				OwnershipDetailData formData = params[0];

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
				Intent intent = null;
				if (Globals.type == 1)
					intent = new Intent(context, OnlinePoSScreen.class);
				else if (Globals.type == 2)
					intent = new Intent(context, OfflinePoSScreen.class);

				if (intent != null)
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
			errDlg = new CustomDialog(OwnerShipFormScreen.this,
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
					photoIv.setVisibility(View.VISIBLE);
					photoIv.setImageBitmap(bitmap);

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
