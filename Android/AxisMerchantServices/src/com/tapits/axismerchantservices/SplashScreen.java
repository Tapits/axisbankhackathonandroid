package com.tapits.axismerchantservices;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.tapits.axismerchantservices.gcm.GCM;
import com.tapits.axismerchantservices.utils.Constants;

public class SplashScreen extends Activity {
	private Context context;
	private ImageView splashImage;

	private AsyncTask task;
	private boolean isAnimEnded = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);

		context = SplashScreen.this;

		GCM gcm = new GCM(context);

		setAllStrings();

		splashImage = (ImageView) findViewById(R.id.iv_splash_screen);

		Animation animation = AnimationUtils.loadAnimation(context,
				R.anim.splash_screen);
		splashImage.setVisibility(View.VISIBLE);
		splashImage.setAnimation(animation);

		task = new AsyncTaskProgress().execute();
		isAnimEnded = true;
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				isAnimEnded = false;
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				isAnimEnded = true;
				if (task == null)
					goNext();
			}
		});

	}

	public class AsyncTaskProgress extends AsyncTask<Void, Integer, Void> {
		int progress;

		@Override
		protected void onPreExecute() {
			progress = 0;
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {

			} catch (Exception e) {
			}

			while (progress < Constants.maxProgressRange) {
				progress = progress + 5;
				publishProgress(progress);
				SystemClock.sleep(100);
			}

			return null;
		}

		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(Void result) {
			goNext();
			task = null;
		}
	}

	private void goNext() {
		if (isAnimEnded) {
			Intent intent = new Intent(context, HomeScreen.class);
			startActivity(intent);
			finish();
		}
	}

	public void setAllStrings() {

		Resources res = getResources();

		Constants.NETWORK_UNAVAILABLE = res.getString(R.string.network_error);

		Constants.PROBLEM_WITH_NETWORK = res
				.getString(R.string.problem_with_network);

		Constants.SERVER_ERROR = res.getString(R.string.server_error);
		Constants.SERVER_NOT_REACHABLE = res
				.getString(R.string.server_not_reachable);

		Constants.PARSEDISPMSG = res.getString(R.string.parse_display_msg);
		Constants.PARSEDETMSG = res.getString(R.string.parse_detailed_msg);

		Constants.CANCEL = res.getString(R.string.cancel_btn);

		Constants.LOADING = res.getString(R.string.loading);

		Constants.OK = res.getString(R.string.ok_btn);

		Constants.pDialogTitle = res.getString(R.string.progress_dialog_title);
		Constants.pDialogMsg = res.getString(R.string.progress_dialog_msg);

		Constants.aDialogTitle = res.getString(R.string.alert_dialog_title);

		Constants.DEVICE_CONNECTIVITY = res
				.getString(R.string.device_connectivity);

	}

}
