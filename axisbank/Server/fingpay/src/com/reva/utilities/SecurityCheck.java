package com.reva.utilities;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.reva.data.DeviceRegistrationData;

public class SecurityCheck {

	public static boolean checkInt(String data) {
		try {
			Integer i = Integer.valueOf(data);
			if (i instanceof Integer) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public static boolean checkUserName(String userName) {
		Pattern p = Pattern.compile("^[A-Za-z. ]+$");
		Matcher m = p.matcher(userName);
		boolean b = m.find();
		if (b) {
			System.out.println("There is no special userName.");
			return true;
		} else {
			System.out.println("There is a special character userName ");
			return false;
		}
	}

	public static boolean checkUserId(String userId) {
		Pattern p = Pattern.compile("^[A-Za-z0-9@._]+$");
		Matcher m = p.matcher(userId);
		boolean b = m.find();
		if (b) {
			System.out.println("There is no special userId.");
			return true;
		} else {
			System.out.println("There is a special character userId ");
			return false;
		}
	}

	public static boolean checkMobileNumber(String mobileNumber) {
		Pattern p = Pattern.compile("^[0-9+ -]+$");
		Matcher m = p.matcher(mobileNumber);
		boolean b = m.find();
		if (b) {
			System.out.println("There is no special mobileNumber.");
			return true;
		} else {
			System.out.println("There is a special character mobileNumber ");
			return false;
		}
	}

	public static boolean checkAddress(String location) {
		Pattern p = Pattern.compile("^[a-zA-Z0-9-/_.:, ]+$");
		Matcher m = p.matcher(location);
		boolean b = m.find();
		if (b) {
			System.out.println("There is no special location");
			return true;
		} else {
			System.out.println("There is a special character location");
			return false;
		}
	}

	public static boolean checkEmail(String emailId) {
		Pattern p = Pattern.compile("^[a-zA-Z0-9@._]+$");
		Matcher m = p.matcher(emailId);
		boolean b = m.find();
		if (b) {
			System.out.println("There is no special emailId");
			return true;
		} else {
			System.out.println("There is a special character emailId");
			return false;
		}
	}

	public static boolean checkInt(int data) {
		try {
			Integer i = Integer.valueOf(data);
			if (i instanceof Integer) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public static boolean merchantId(String merchantId) {
		Pattern p = Pattern.compile("^[A-Za-z0-9 ]+$");
		Matcher m = p.matcher(merchantId);
		boolean b = m.find();
		if (b) {
			System.out.println("There is no special merchantId");
			return true;
		} else {
			System.out.println("There is a special character merchantId");
			return false;
		}
	}

	public static boolean merchantPin(int merchantPin) {
		try {
			Integer i = Integer.valueOf(merchantPin);
			if (i instanceof Integer) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public static boolean checkDescription(String description) {
		Pattern p = Pattern.compile("^[0-9A-Za-z,. ]+$");
		Matcher m = p.matcher(description);
		boolean b = m.find();
		if (b) {
			System.out.println("There is no special description");
			return true;
		} else {
			System.out.println("There is a special character description");
			return false;
		}
	}

	public static boolean checkProjectName(String projectName) {
		if (!CommonTasks.check(projectName)) {
			System.out.println("There is no special empty projectName : "
					+ projectName);
			return true;
		}
		Pattern p = Pattern.compile("^[0-9A-Za-z,\\- ]+$");
		Matcher m = p.matcher(projectName);
		boolean b = m.find();
		if (b) {
			System.out.println("There is no special projectName");
			return true;
		} else {
			System.out.println("There is a special character projectName : "
					+ projectName);
			return false;
		}
	}

	public static boolean checkComments(String comments) {
		Pattern p = Pattern.compile("^[0-9A-Za-z,. ]+$");
		Matcher m = p.matcher(comments);
		boolean b = m.find();
		if (b) {
			System.out.println("There is no special char in comments.");
			return true;
		} else {
			System.out
					.println("There is a special character in my string in comments ");
			return false;
		}
	}

	public static boolean checkSearch(String search) {
		Pattern p = Pattern.compile("^[A-Za-z0-9- ]+$");
		Matcher m = p.matcher(search);
		boolean b = m.find();
		if (b) {
			System.out.println("There is no special search");
			return true;
		} else {
			System.out.println("There is a special character search");
			return false;
		}
	}

	// public static boolean getMimeType(byte[] attachment) throws IOException,
	// MagicParseException, MagicMatchNotFoundException, MagicException {
	// /*InputStream is = new BufferedInputStream(new
	// ByteArrayInputStream(attachment));
	// String mimeType = URLConnection.guessContentTypeFromStream(is);*/
	// /*MagicMatch match = Magic.getMagicMatch(attachment);
	// String mimeType = match.getMimeType();
	// System.out.println("Attachment uploaded : "+mimeType);*/
	//
	// ByteArrayInputStream bai = new ByteArrayInputStream(attachment);
	// ContentHandler contenthandler = new BodyContentHandler();
	// Metadata metadata = new Metadata();
	// Parser parser = new AutoDetectParser();
	// try {
	// parser.parse(bai, contenthandler, metadata);
	//
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (SAXException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (TikaException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// String mimeType = metadata.get(Metadata.CONTENT_TYPE);
	// // System.out.println("Attachment Mime: " +
	// metadata.get(Metadata.CONTENT_TYPE));
	// switch(mimeType){
	// case "image/png":
	// case "image/jpeg":
	// case "application/gif":
	// case "application/x-tika-ooxml":
	// case "application/pdf": return true;
	// default : return false;
	// }
	//
	// // return mimeType;
	// }

	public static boolean checkFileExtension(String attachmentName) {
		String ext = "";
		String[] split = attachmentName.split("\\.");
		if (split.length == 2) {
			ext = split[1];
			System.out.println("FileExtension : " + ext);
			switch (ext.toLowerCase()) {
			case "doc":
			case "docx":
			case "pdf":
			case "png":
			case "jpeg":
			case "jpg":
			case "gif":
				return true;
			default:
				return false;
			}
		} else {
			return false;
		}
	}

	public static String getSalt() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2016);
		calendar.setTimeZone(TimeZone.getTimeZone("IST"));
		long salt = calendar.getTimeInMillis();
		return String.valueOf(salt);
	}

	public static boolean checkDate(String date) {
		Pattern p = Pattern.compile("^[0-9:/ ]+$");
		Matcher m = p.matcher(date);
		boolean b = m.find();
		if (b) {
			System.out.println("There is no special char in date.");
			return true;
		} else {
			System.out
					.println("There is a special character in my string in date : "
							+ date);
			return false;
		}
	}

	public static boolean checkRoleName(String roleName) {
		Pattern p = Pattern.compile("^[A-Za-z ]+$");
		Matcher m = p.matcher(roleName);
		boolean b = m.find();
		if (b) {
			System.out.println("There is no special char in roleName.");
			return true;
		} else {
			System.out
					.println("There is a special character in my string in roleName ");
			return false;
		}
	}

	public static boolean checkSession(HttpSession session) {
		try {
			return (boolean) session.getAttribute("isLogin");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void disableCache(HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
	}

	public static boolean checkDeviceRegistrationData(
			DeviceRegistrationData modelM) {
		if (!checkIMEI(modelM.getImei())) {
			return false;
		}
		if (!checkMACADDR(modelM.getMacAddr())) {
			return false;
		}
		if (!checkMAKE(modelM.getMake())) {
			return false;
		}
		if (!checkMODEL(modelM.getModel())) {
			return false;
		}
		if (!checkPT(modelM.getPushToken())) {
			return false;
		}
		if (!checkInt(modelM.getMerchantId())) {
			return false;
		}
		if (!checkRT(modelM.getRegisteredTimestamp())) {
			return false;
		}
		return true;
	}

	private static boolean checkRT(long registeredTimestamp) {
		try {
			Long i = Long.valueOf(registeredTimestamp);
			if (i instanceof Long) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	private static boolean checkPT(String pushToken) {
		Pattern p = Pattern.compile("^[_0-9A-Za-z- ]+$");
		Matcher m = p.matcher(pushToken);
		boolean b = m.find();
		if (b) {
			System.out.println("There is no special char in pushToken.");
			return true;
		} else {
			System.out
					.println("There is a special character in my string in pushToken ");
			return false;
		}
	}

	private static boolean checkMODEL(String model) {
		Pattern p = Pattern.compile("^[_0-9A-Za-z-  ]+$");
		Matcher m = p.matcher(model);
		boolean b = m.find();
		if (b) {
			System.out.println("There is no special char in model.");
			return true;
		} else {
			System.out
					.println("There is a special character in my string in model ");
			return false;
		}
	}

	private static boolean checkMAKE(String make) {
		Pattern p = Pattern.compile("^[0-9A-Za-z_. ]+$");
		Matcher m = p.matcher(make);
		boolean b = m.find();
		if (b) {
			System.out.println("There is no special char in make.");
			return true;
		} else {
			System.out
					.println("There is a special character in my string in make ");
			return false;
		}
	}

	private static boolean checkMACADDR(String macAddr) {
		Pattern p = Pattern.compile("^[0-9A-Za-z:]+$");
		Matcher m = p.matcher(macAddr);
		boolean b = m.find();
		if (b) {
			System.out.println("There is no special char in macAddr.");
			return true;
		} else {
			System.out
					.println("There is a special character in my string in macAddr ");
			return false;
		}
	}

	private static boolean checkIMEI(String imei) {
		Pattern p = Pattern.compile("^[0-9]+$");
		Matcher m = p.matcher(imei);
		boolean b = m.find();
		if (b) {
			System.out.println("There is no special char in imei.");
			return true;
		} else {
			System.out
					.println("There is a special character in my string in imei ");
			return false;
		}
	}

	public static String SHA512(String value) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = null;
		byte[] hash = null;
		md = MessageDigest.getInstance("SHA-512");
		hash = md.digest(value.getBytes("ISO-8859-1"));
		String h = convertToHex(hash);
		return h;
	}

	private static String convertToHex(byte[] raw) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < raw.length; i++) {
			sb.append(Integer.toString((raw[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}
	
	public static HttpSession getSession(HttpServletRequest request){
//		String jsessionId = request.getHeader("TOKEN");
		/*Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			System.out.println("Header Name: " + headerName);
			String headerValue = request.getHeader(headerName);
			System.out.print("    Header Value: " + headerValue);
			if(headerValue.contains("JSESSIONID")){
				jsessionId = headerValue.split("=")[1];
				break;
			}
		}*/
		return HttpSessionCollector.find(request.getHeader("TOKEN"));
	}
}
