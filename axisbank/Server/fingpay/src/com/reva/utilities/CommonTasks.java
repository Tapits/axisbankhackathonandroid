package com.reva.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.persistence.tools.file.FileUtil;

import com.google.gson.Gson;
import com.reva.connectionmanager.ManageTransaction;
//import com.reva.data.APS;
//import com.reva.data.IOSPushMasgData;
//import com.reva.data.IosPushJsonFormat;
//import com.reva.jpa.IosProxyPushMsg;

public class CommonTasks {

	public static boolean check(String... args) {
		boolean result = true;

		for (String arg : args) {
			if (arg == null || arg.trim().length() == 0) {
				result = false;
				break;
			}
		}
		return result;
	}
	@SuppressWarnings("deprecation")
	public static String toBase64(String pic,int serviceId) {

//		String extension = FilesUtil.getProperty("ImageExtension");
		String path = FilesUtil.getProperty("ImagePath");

		// path = new File(".").getCanonicalPath() +"/" + path;

		// System.out.println("NEW PATH: " + path);
		File fileImg = new File(path);
		fileImg.mkdirs();
		//
//		pic = URLDecoder.decode(pic);

		byte[] imgBytes = org.apache.commons.codec.binary.Base64.decodeBase64(pic);
		String fileName = (new SimpleDateFormat(serviceId+"_yyyyMMddhhmmssSSSS").format(new Date()) + "." + FilesUtil.getProperty("ImageExtension")).trim();

		fileImg = null;
		fileImg = new File(path + fileName);

		if (fileImg.exists()) {
			fileImg = null;
			fileImg = new File(path + fileName);
		}

		FileOutputStream fOut;
		try {
			fOut = new FileOutputStream(fileImg);
			fOut.write(imgBytes);
			fOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		System.out.println("Output file saved: " + fileImg.getAbsolutePath());
		return fileName;
	}
	
	public static String getBase64(String imageName) {
		File file = new File(FilesUtil.getProperty("ImagePath") + imageName);
		String imageDataString = null;
		try{
			// Reading a Image file from file system
            FileInputStream imageInFile = new FileInputStream(file);
            byte imageData[] = new byte[(int) file.length()];
            imageInFile.read(imageData);
            // Converting Image byte array into Base64 String
            imageDataString = Base64.encodeBase64String(imageData);
            imageInFile.close();
            System.out.println("Image Successfully Manipulated!");
        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        }
		return imageDataString;
	}
	public static boolean SendAPKDownLinkToPhone(String merchantPhoneNumber,int mId) {
		String apkDownloadMsg = FilesUtil.getProperty("apkDownloadMsg");
		return sendSMS(merchantPhoneNumber, apkDownloadMsg);
	}
	
	public static boolean SendMerchantRegOTP(String phoneNumber, int otp) {
		String regMessage = FilesUtil.getProperty("regMessage").replace("<myOTP>", String.valueOf(otp));
		return sendSMS(phoneNumber, regMessage);
	}
	
	public static boolean sendSMS(String mobileNumber, String message){
		HttpURLConnection myURLConnection = null;
		try {
			String requestUrl = FilesUtil.getProperty("smsURL")+"?method="+FilesUtil.getProperty("smsMethod")+"&api_key="+FilesUtil.getProperty("smsApikey")+"&to="+mobileNumber+"&sender="+FilesUtil.getProperty("smsSender")+"&message="+message+"&format="+FilesUtil.getProperty("smsFormat")+"&custom="+FilesUtil.getProperty("custom")+"&flash="+FilesUtil.getProperty("flash");
//			String requestUrl = "http://map-alerts.smsalerts.biz/api/v3/index.php?method=sms&api_key=A48f4d37b3b980d753438bf5b40c5a0e5&to="+mobileNumber+"&sender=JPSCHL&message="+message+"&format=json&custom=1,2&flash=0";
		    URL myURL = new URL(requestUrl);
		    myURLConnection = (HttpURLConnection) myURL.openConnection();
		    myURLConnection.connect();
		    System.out.println("Sending Status "+myURLConnection.getResponseCode()+"  "+myURLConnection.getResponseMessage());
		    return true;
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
	      myURLConnection.disconnect();
		}
		return false;
	}
	
	public static int generatePin() throws Exception {
		Random generator = new Random();
		generator.setSeed(System.currentTimeMillis());
		  
		int num = generator.nextInt(99999) + 99999;
		if (num < 100000 || num > 999999) {
		num = generator.nextInt(99999) + 99999;
		if (num < 100000 || num > 999999) {
		throw new Exception("Unable to generate PIN at this time..");
		}
		}
		return num;
		}
	
	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static SecureRandom rnd = new SecureRandom();
	public static String getRegisrationRefNumber() {
		int len = 10;
		  StringBuilder sb = new StringBuilder( len );
		   for( int i = 0; i < len; i++ ) 
		      sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
		   return sb.toString();
	}
	
	public static void main(String[] args){
		System.out.println("RegisrationRefNumber : "+getRegisrationRefNumber());
	}
	
	/*public static void SendCampaignPush(String modelM, ManageTransaction manageTransaction) throws JsonGenerationException, JsonMappingException, IOException {
//		String message = new ObjectMapper().writeValueAsString(modelM);
		System.out.println("SendCampaignPush Message : "+modelM);
//		List<String> datas = manageTransaction.createQuery("SELECT d.pushToken FROM CDeviceInfo d",String.class).getResultList();
		//Android Push Messages
		List<String> datas = manageTransaction.createNativeQuery("SELECT d.push_token FROM real_ex.c_device_info d where d.device_brand != 'Apple'").getResultList();
		int l = 1000;
		 List<List<String>> parts = new ArrayList<List<String>>();
		    final int N = datas.size();
		    for (int i = 0; i < N; i += l) {
		        parts.add(new ArrayList<String>(datas.subList(i, Math.min(N, i + l))));
		    }
		    System.out.println("SendCampaignPush to All Android devices List Size : "+datas.size());
		    for(List<String> p:parts){
		    	SendMessage.sendMessage(p, modelM);
		    }
		 //IOS Push Messages
		    
		  //For IOS
		    
//		    ObjectMapper mapper = new ObjectMapper();
			
			IosProxyPushMsg pushMsg = new IosProxyPushMsg();
//			pushMsg.setDeviceId(customerQuestion.getCDeviceInfo().getDeviceId());
			pushMsg.setJson(modelM);
			pushMsg.setStatus(0);
			pushMsg.setTimestamp(new Date());
			pushMsg.setTypeOfMsg(1);
			manageTransaction.saveObject(pushMsg);
			
			String url = FilesUtil.getProperty("iphonemsgURL");
			url = url+pushMsg.getId();
			
			IosPushJsonFormat jsonFormat = new IosPushJsonFormat();
			jsonFormat.setAlert("Sumdhura sent you a message");
			jsonFormat.setType(1);
			jsonFormat.setUrl(url);
			
			IOSPushMasgData data = new IOSPushMasgData();
			APS aps = new APS();
			aps.setAlert("Sumdhura sent you a message");
			aps.setBadge("1");
			aps.setSound("default");
			aps.setType(1);
			aps.setUrl(url);
			data.setAps(aps);
			
			String msg =new Gson().toJson(data);
			System.out.println("Apple Message : "+msg);
		    
		    
		    
		    List<String> ios_datas = manageTransaction.createNativeQuery("SELECT d.push_token FROM real_ex.c_device_info d where d.device_brand = 'Apple'").getResultList();
			int ios_l = 1000;
			 List<List<String>> ios_parts = new ArrayList<List<String>>();
			    final int ios_N = datas.size();
			    for (int i = 0; i < ios_N; i += ios_l) {
			    	ios_parts.add(new ArrayList<String>(ios_datas.subList(i, Math.min(ios_N, i + ios_l))));
			    }
			    System.out.println("SendCampaignPush to All IOS devices List Size : "+ios_datas.size());
			    for(List<String> p:ios_parts){
			    	SendIOSMessage.IOS_sendMessage(p, msg,"Campaign Management");
			    }
			    System.out.println("IOS push message : "+modelM);
		    SendIOSMessage.IOS_sendMessage(ios_datas, msg,"Campaign Management");
		    
	}*/
}
