package com.tapits.axismerchantservices;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.IntentCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tapits.axismerchantservices.utils.Globals;
import com.tapits.axismerchantservices.utils.Utils;

public class VerificationScreen extends Activity {
	private Context context;

	private ProgressBar panPb, aadPb, webPb, keyPb, riskPb, domPb, accPb,
			socPb;
	private ImageView panIv, aadIv, webIv, keyIv, riskIv, domIv, accIv, socIv;

	private CountDownTimer panCd, aadCd, webCd, keyCd, riskCd, domCd, accCd,
			socCd;
	private final long startTime = 2 * 1000;
	private final long interval = 1 * 1000;

	private Button doneBtn;

	int count = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.verification_screen);

		context = VerificationScreen.this;

		TextView refTv = (TextView) findViewById(R.id.tv_ref_number);
		if (Utils.isValidString(Globals.refId))
			refTv.setText("Reference Number : " + Globals.refId);

		panPb = (ProgressBar) findViewById(R.id.pb_pan);
		aadPb = (ProgressBar) findViewById(R.id.pb_aadhaar);
		webPb = (ProgressBar) findViewById(R.id.pb_website);
		keyPb = (ProgressBar) findViewById(R.id.pb_key);
		riskPb = (ProgressBar) findViewById(R.id.pb_risk);
		domPb = (ProgressBar) findViewById(R.id.pb_domain);
		accPb = (ProgressBar) findViewById(R.id.pb_acc);
		socPb = (ProgressBar) findViewById(R.id.pb_social);

		panIv = (ImageView) findViewById(R.id.iv_pan);
		aadIv = (ImageView) findViewById(R.id.iv_aadhaar);
		webIv = (ImageView) findViewById(R.id.iv_website);
		keyIv = (ImageView) findViewById(R.id.iv_key);
		riskIv = (ImageView) findViewById(R.id.iv_risk);
		domIv = (ImageView) findViewById(R.id.iv_domain);
		accIv = (ImageView) findViewById(R.id.iv_acc);
		socIv = (ImageView) findViewById(R.id.iv_social);

		panCd = new MyCountDownTimer(startTime, interval);
		panCd.start();

		doneBtn = (Button) findViewById(R.id.btn_done);
		doneBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				goHome();
			}
		});
	}

	public class MyCountDownTimer extends CountDownTimer {
		public MyCountDownTimer(long startTime, long interval) {
			super(startTime, interval);
		}

		@Override
		public void onFinish() {
			int f;
			switch (count) {
			case 1:
				panCd.cancel();
				panPb.setVisibility(View.GONE);
				f = Globals.mData.getPanValidationStatus();
				if (f == 1)
					panIv.setImageResource(R.drawable.right_icon);
				else
					panIv.setImageResource(R.drawable.cancle_icon);
				count++;
				aadCd = new MyCountDownTimer(startTime, interval);
				aadCd.start();
				aadPb.setVisibility(View.VISIBLE);
				break;

			case 2:
				aadCd.cancel();
				aadPb.setVisibility(View.GONE);
				f = Globals.mData.getAadharValidation();
				if (f == 1)
					aadIv.setImageResource(R.drawable.right_icon);
				else
					aadIv.setImageResource(R.drawable.cancle_icon);
				count++;
				webCd = new MyCountDownTimer(startTime, interval);
				webCd.start();
				webPb.setVisibility(View.VISIBLE);
				break;

			case 3:
				webCd.cancel();
				webPb.setVisibility(View.GONE);
				f = Globals.mData.getWebsiteVerifications();
				if (f == 1)
					webIv.setImageResource(R.drawable.right_icon);
				else
					webIv.setImageResource(R.drawable.cancle_icon);
				count++;
				keyCd = new MyCountDownTimer(startTime, interval);
				keyCd.start();
				keyPb.setVisibility(View.VISIBLE);
				break;

			case 4:
				keyCd.cancel();
				keyPb.setVisibility(View.GONE);
				f = Globals.mData.getKeywordsVerification();
				if (f == 1)
					keyIv.setImageResource(R.drawable.right_icon);
				else
					keyIv.setImageResource(R.drawable.cancle_icon);
				count++;
				riskCd = new MyCountDownTimer(startTime, interval);
				riskCd.start();
				riskPb.setVisibility(View.VISIBLE);
				break;

			case 5:
				riskCd.cancel();
				riskPb.setVisibility(View.GONE);
				f = Globals.mData.getHighandlowriskbusinesscheck();
				if (f == 1)
					riskIv.setImageResource(R.drawable.right_icon);
				else
					riskIv.setImageResource(R.drawable.cancle_icon);
				count++;
				domCd = new MyCountDownTimer(startTime, interval);
				domCd.start();
				domPb.setVisibility(View.VISIBLE);
				break;

			case 6:
				domCd.cancel();
				domPb.setVisibility(View.GONE);
				f = Globals.mData.getDomainNameValidation();
				if (f == 1)
					domIv.setImageResource(R.drawable.right_icon);
				else
					domIv.setImageResource(R.drawable.cancle_icon);
				accCd = new MyCountDownTimer(startTime, interval);
				count++;
				accCd.start();
				accPb.setVisibility(View.VISIBLE);
				break;

			case 7:
				accCd.cancel();
				accPb.setVisibility(View.GONE);
				f = Globals.mData
						.getCurrentaccountbalancetransferverification();
				if (f == 1)
					accIv.setImageResource(R.drawable.right_icon);
				else
					accIv.setImageResource(R.drawable.cancle_icon);
				socCd = new MyCountDownTimer(startTime, interval);
				count++;
				socCd.start();
				socPb.setVisibility(View.VISIBLE);
				break;

			case 8:
				socCd.cancel();
				socPb.setVisibility(View.GONE);
				f = Globals.mData.getSocialCheck();
				if (f == 1)
					socIv.setImageResource(R.drawable.right_icon);
				else
					socIv.setImageResource(R.drawable.cancle_icon);
				break;

			default:
				break;
			}

		}

		@Override
		public void onTick(long millisUntilFinished) {
			Utils.logD("" + millisUntilFinished);
		}
	}

	private void goHome() {
		Intent intent = new Intent(context, HomeScreen.class);
		ComponentName cn = intent.getComponent();
		Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
		startActivity(mainIntent);
		finish();

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

}
