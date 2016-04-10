package com.custom;

/**
 * 
 * Developed by Swathi Lolla
 * 
 * Email: slolla@revamobile.com
 * 
 * Date: March 29, 2016
 * 
 * All code (c) 2011 Reva Tech Solutions (India) Private Limited (Reva Mobile)
 * 
 * All rights reserved
 * 
 * 
 */

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.tapits.axismerchantservices.R;

public class CustomDialog extends Dialog {
	private TextView messageTv;
	private Button okBtn;
	private String msg;
	private boolean close;
	Activity activity;

	public CustomDialog(Activity a, String message, boolean isClose) {
		super(a, R.style.CustomAlertDialog);
		activity = a;
		msg = message;
		close = isClose;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.custom_dialog);

		messageTv = (TextView) findViewById(R.id.tv_message);
		messageTv.setText(msg);

		okBtn = (Button) findViewById(R.id.btn_ok);
		okBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
				if (close)
					activity.finish();
			}
		});

	}

}
