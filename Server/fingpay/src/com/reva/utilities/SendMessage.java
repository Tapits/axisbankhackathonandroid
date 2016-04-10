package com.reva.utilities;

import java.io.IOException;
import java.util.List;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class SendMessage {
	private static String gcm_app_key = FilesUtil.getProperty("gcm.app_key");

	public static Result sendMessage(String pushToken, String message) {
		Result result = null;
		Sender sender = new Sender(gcm_app_key);
		Message msg = new Message.Builder().addData("message", message).build();
		try {
			result = sender.send(msg, pushToken, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static MulticastResult sendMessage(List<String> pushTokens,String message) {
		MulticastResult result = null;
		Sender sender = new Sender(gcm_app_key);
		Message msg = new Message.Builder().addData("message", message).build();
		try {
			result = sender.send(msg, pushTokens, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}