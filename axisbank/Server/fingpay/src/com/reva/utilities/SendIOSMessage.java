package com.reva.utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;

import javapns.Push;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.notification.Payload;
import javapns.notification.PayloadPerDevice;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class SendIOSMessage {

	public static Result sendMessage(String pushToken, String message) {
		Result result = null;
		Sender sender = new Sender(FilesUtil.getProperty("GcmIOSKey"));
		Message msg = new Message.Builder().addData("message", message).build();
		try {
			result = sender.send(msg, pushToken, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static MulticastResult sendMessage(List<String> pushTokens,
			String message) {
		MulticastResult result = null;
		Sender sender = new Sender(FilesUtil.getProperty("GcmIOSKey"));
		Message msg = new Message.Builder().addData("message", message).build();
		try {
			result = sender.send(msg, pushTokens, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static boolean IOS_sendMessage(String pushToken, String message,String type) {

		List<String> pushTokens = new ArrayList<String>();
		pushTokens.add(pushToken);

		return SendIOSMessage.IOS_sendMessage(pushTokens, message,type);
	}

	public static boolean IOS_sendMessage(List<String> pushTokens,String message, String type) {
		boolean result = false;
		System.out.println("In IOS_sendMessage");
		try {
			BasicConfigurator.resetConfiguration();
		    BasicConfigurator.configure();
			List<PayloadPerDevice> allDevices = new ArrayList<PayloadPerDevice>();
			Payload payload = new Payload(message) {
			};
			for(String pushToken : pushTokens){
				PayloadPerDevice payloadPerDevice = new PayloadPerDevice(payload, pushToken);
				allDevices.add(payloadPerDevice);
			}
//			payload.addCustomDictionary("badge", 10);
//			payload.addCustomDictionary("alert", message);
//			payload.addCustomDictionary("sound", "cat.caf");
//			payload.addCustomDictionary("type", type);
//			PushedNotifications push = Push.payload(payload, FilesUtil.getProperty("iosapnscetificatefile"), FilesUtil.getProperty("iosapnscetificatefilePassword"), false, pushTokens);
			// PushedNotifications push = Push.alert(message, "mobioffercert.p12", "reva1234", false, pushTokens);
//			PushedNotifications push = Push.combined(message, 1, "default",	"/home/childfirst/cerft/CertificatesApnsDevelopment.p12", "1234", false, pushTokens);
			
			try {
				boolean prodectionFlag = Boolean.parseBoolean(FilesUtil.getProperty("iosapnsprodectionflag"));
				System.out.println("prodectionFlag : "+prodectionFlag);
				result = Push.payloads(FilesUtil.getProperty("iosapnscetificatefile"), FilesUtil.getProperty("iosapnscetificatefilePassword"), prodectionFlag,allDevices).get(0).isSuccessful();
			} catch (CommunicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (KeystoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
//			PushedNotifications push = Push.combined(message, 10, "cat.caf",	FilesUtil.getProperty("iosapnscetificatefile"), FilesUtil.getProperty("iosapnscetificatefilePassword"), false, pushTokens);
//			result = push.get(0).isSuccessful();
			System.out.println("PushedNotifications Result : "+result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = false;
		}
		return result;
	}
  /* public static boolean IOS_sendMessage_Devices(String PushToken, String message) throws JSONException, InvalidDeviceTokenFormatException{
		
		
		
		
		List<IosDevicesToPush> iosDevicesToPush = new ArrayList<IosDevicesToPush>();
		iosDevicesToPush.add(new IosDevicesToPush(deviceId, pushToken, "ButtonLabel", message));
				
		return IOS_sendMessage_Devices(iosDevicesToPush, message, "ButtonLabel");
	}
	
	public static boolean IOS_sendMessage_Devices(List<IosDevicesToPush> iosDevices, String message, String buttonLabel) throws JSONException, InvalidDeviceTokenFormatException{
		
		Boolean result = null; 
		
		ManageTransaction mt = new ManageTransaction();
		
		List<PayloadPerDevice> allDevices = new ArrayList<PayloadPerDevice>();
		
		for(IosDevicesToPush iosDevice : iosDevices){
			
			int deviceId = iosDevice.getDeviceId();
			
			DeviceIdPushToken deviceIdPushToken = mt.find(DeviceIdPushToken.class, deviceId);
			IosAppBadge iosAppBadge = mt.find(IosAppBadge.class, deviceId);
			
			if(deviceIdPushToken==null || iosAppBadge==null)
				continue;
			
			String pushToken = deviceIdPushToken.getPushtoken();
			
			 * Incrementing the badge count
			 
			Integer badgeCount = iosAppBadge.getBadgeCount();
			badgeCount++;
			mt.begin();
				iosAppBadge.setBadgeCount(badgeCount);
			mt.commit();
			
			Payload payload = new Payload(new Gson().toJson(new IosPushJsonFormat(new IosPushJsonFormatAps(badgeCount, "default", new IosPushJsonFormatAlert(message, buttonLabel))))) {
			};
			PayloadPerDevice payloadPerDevice = new PayloadPerDevice(payload, pushToken);
			allDevices.add(payloadPerDevice);
		}
		
		try {
			result = Push.payloads("mobioffercert.p12", "reva1234", false,allDevices).get(0).isSuccessful();
		} catch (CommunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeystoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mt.close();
		return result;
		
	}*/
}
