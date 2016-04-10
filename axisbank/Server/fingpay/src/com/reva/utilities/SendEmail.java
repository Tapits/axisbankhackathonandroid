package com.reva.utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.FileUtils;

public class SendEmail {

	public static void SendMail(String model, String eMailId) {

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmSSSS");
		File file = new File(FilesUtil.getProperty("filePath") + "Question_"	+ format.format(new Date()) + ".txt");
		String path = file.getAbsolutePath();
		System.out.println("File Path:" + path);
		try {
			FileUtils.writeStringToFile(file, model);
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<String> mailList = new ArrayList<String>();

		String mailIds = FilesUtil.getProperty("toMailIds");

		if (mailIds.contains(",")) {
			String[] mailId = mailIds.split(",");
			for (String id : mailId) {
				mailList.add(id);
			}
		} else {
			mailList.add(mailIds);
		}
		
//		mailList.add(mailIds);
//		mailList.add(eMailId);
		
		boolean status = false;
		try {
			status = SendEmail.sendEmails(mailList, path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Sending mails Status:" + status);
	}

	public static boolean sendEmails(List<String> mailList, String path) {

		String subject = FilesUtil.getProperty("subject");

		// Sender's email ID needs to be mentioned
		String from = FilesUtil.getProperty("mailusername");
																// //
																// "pharmadealhyd@yahoo.com";
		String pass = FilesUtil.getProperty("mailpassword");//
																// "pd12#$56";
		// Recipient's email ID needs to be mentioned.
		String host = "smtp.mail.yahoo.com";

		StringBuilder builder = new StringBuilder();

		// for (String p : paths) {
		// builder.append(FileUtils.readFileToString(new File(p)));
		// }

		try {
			builder.append(FileUtils.readFileToString(new File(path)));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}

		// Get system properties
		Properties properties = System.getProperties();
		// Setup mail server
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.user", from);
		properties.put("mail.smtp.password", pass);
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.debug.auth", "true");

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.

			String[] mailIds = mailList.toArray(new String[mailList.size()]);
			InternetAddress[] address = new InternetAddress[mailIds.length];
			for (int i = 0; i < mailIds.length; i++) {
				System.out.println("Sending Mail to " + mailIds[i]);
				try {
					address[i] = new InternetAddress(mailIds[i]);
				} catch (AddressException e) {
					e.printStackTrace();
				}
			}

			message.addRecipients(Message.RecipientType.TO, address);

			// Set Subject: header field
			message.setSubject(subject);

			// Now set the actual message
			message.setText(builder.toString());

			// Send message
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			System.out.println("Sent message successfully....");
			return true;
		} catch (MessagingException mex) {
			mex.printStackTrace();
			return false;
		}

		/*
		 * String subject = "Pharmadeal OrderNo:"+orderId;
		 * 
		 * boolean result = false;
		 * 
		 * Properties props = new Properties(); props.put("mail.smtp.host",
		 * "smtp.gmail.com"); props.put("mail.smtp.socketFactory.port", "465");
		 * props
		 * .put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory"
		 * ); props.put("mail.smtp.auth", "true"); props.put("mail.smtp.port",
		 * "465");
		 * 
		 * Session session = Session.getDefaultInstance(props, new
		 * javax.mail.Authenticator() { protected PasswordAuthentication
		 * getPasswordAuthentication() { return new
		 * PasswordAuthentication(ConfigPropertiesUtil
		 * .getProperty("mailusername"),
		 * ConfigPropertiesUtil.getProperty("mailpassword")); } });
		 * 
		 * String[] mailIds = mailList.toArray(new String[mailList.size()]);
		 * InternetAddress[] address = new InternetAddress[mailIds.length];
		 * for(int i =0; i< mailIds.length; i++) {
		 * System.out.println("Sending Mail to " + mailIds[i]); try { address[i]
		 * = new InternetAddress(mailIds[i]); } catch (AddressException e) {
		 * e.printStackTrace(); } }
		 * 
		 * try {
		 * 
		 * Message message = new MimeMessage(session);
		 * message.setRecipients(Message.RecipientType.TO, address);
		 * message.setFrom(new
		 * InternetAddress(ConfigPropertiesUtil.getProperty("sendingmail")));
		 * 
		 * if(ConfigPropertiesUtil.getProperty("CC").trim().length()!=0){
		 * message.setRecipient(Message.RecipientType.CC,new
		 * InternetAddress(ConfigPropertiesUtil.getProperty("CC"))); }
		 * 
		 * if(ConfigPropertiesUtil.getProperty("BCC").trim().length()!=0){
		 * message.addRecipient(Message.RecipientType.BCC, new
		 * InternetAddress(ConfigPropertiesUtil.getProperty("BCC")));
		 * 
		 * }
		 * 
		 * message.setSubject(subject);
		 * message.setText(ConfigPropertiesUtil.getProperty("Text"));
		 * 
		 * MimeBodyPart messageBodyPart = new MimeBodyPart();
		 * 
		 * Multipart multipart = new MimeMultipart();
		 * 
		 * messageBodyPart = new MimeBodyPart();
		 * messageBodyPart.attachFile(path);
		 * 
		 * 
		 * multipart.addBodyPart(messageBodyPart);
		 * 
		 * message.setContent(multipart);
		 * 
		 * Transport.send(message);
		 * 
		 * System.out.println("Done"); result=true;
		 * 
		 * } catch (MessagingException e) { e.printStackTrace(); } catch
		 * (IOException e) { e.printStackTrace(); } // }
		 * 
		 * return result;
		 */
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

	public static void SendVcRegistrationMail(String model) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmSSSS");
		File file = new File(FilesUtil.getProperty("filePath") + "VCRegistration_"	+ format.format(new Date()) + ".txt");
		String path = file.getAbsolutePath();
		System.out.println("File Path:" + path);
		try {
			FileUtils.writeStringToFile(file, model);
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<String> mailList = new ArrayList<String>();

		String mailIds = FilesUtil.getProperty("toMailIds");

		if (mailIds.contains(",")) {
			String[] mailId = mailIds.split(",");
			for (String id : mailId) {
				mailList.add(id);
			}
		} else {
			mailList.add(mailIds);
		}
		
//		mailList.add(mailIds);
//		mailList.add(eMailId);
		
		boolean status = false;
		try {
			status = SendEmail.sendVcRegistrationEmails(mailList, path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Sending mails Status:" + status);
		
	}

	private static boolean sendVcRegistrationEmails(List<String> mailList,
			String path) {
		String subject = FilesUtil.getProperty("registration_subject");

		// Sender's email ID needs to be mentioned
		String from = FilesUtil.getProperty("mailusername");
																// //
																// "pharmadealhyd@yahoo.com";
		String pass = FilesUtil.getProperty("mailpassword");//
																// "pd12#$56";
		// Recipient's email ID needs to be mentioned.
		String host = "smtp.mail.yahoo.com";

		StringBuilder builder = new StringBuilder();

		// for (String p : paths) {
		// builder.append(FileUtils.readFileToString(new File(p)));
		// }

		try {
			builder.append(FileUtils.readFileToString(new File(path)));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}

		// Get system properties
		Properties properties = System.getProperties();
		// Setup mail server
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.user", from);
		properties.put("mail.smtp.password", pass);
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.debug.auth", "true");

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.

			String[] mailIds = mailList.toArray(new String[mailList.size()]);
			InternetAddress[] address = new InternetAddress[mailIds.length];
			for (int i = 0; i < mailIds.length; i++) {
				System.out.println("Sending Mail to " + mailIds[i]);
				try {
					address[i] = new InternetAddress(mailIds[i]);
				} catch (AddressException e) {
					e.printStackTrace();
				}
			}

			message.addRecipients(Message.RecipientType.TO, address);

			// Set Subject: header field
			message.setSubject(subject);

			// Now set the actual message
			message.setText(builder.toString());

			// Send message
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			System.out.println("Sent message successfully....");
			return true;
		} catch (MessagingException mex) {
			mex.printStackTrace();
			return false;
		}
	}

	public static void SendVcLeadRegistrationMail(String msgData) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmSSSS");
		File file = new File(FilesUtil.getProperty("filePath") + "VCLeadRegistration_"	+ format.format(new Date()) + ".txt");
		String path = file.getAbsolutePath();
		System.out.println("File Path:" + path);
		try {
			FileUtils.writeStringToFile(file, msgData);
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<String> mailList = new ArrayList<String>();

		String mailIds = FilesUtil.getProperty("toMailIds");

		if (mailIds.contains(",")) {
			String[] mailId = mailIds.split(",");
			for (String id : mailId) {
				mailList.add(id);
			}
		} else {
			mailList.add(mailIds);
		}
		
//		mailList.add(mailIds);
//		mailList.add(eMailId);
		
		boolean status = false;
		try {
			status = SendEmail.sendVcLeadRegistrationEmails(mailList, path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Sending mails Status:" + status);
		
		
	}

	private static boolean sendVcLeadRegistrationEmails(List<String> mailList,
			String path) {
		String subject = FilesUtil.getProperty("registration_lead_subject");

		// Sender's email ID needs to be mentioned
		String from = FilesUtil.getProperty("mailusername");
																// //
																// "pharmadealhyd@yahoo.com";
		String pass = FilesUtil.getProperty("mailpassword");//
																// "pd12#$56";
		// Recipient's email ID needs to be mentioned.
		String host = "smtp.mail.yahoo.com";

		StringBuilder builder = new StringBuilder();

		// for (String p : paths) {
		// builder.append(FileUtils.readFileToString(new File(p)));
		// }

		try {
			builder.append(FileUtils.readFileToString(new File(path)));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}

		// Get system properties
		Properties properties = System.getProperties();
		// Setup mail server
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.user", from);
		properties.put("mail.smtp.password", pass);
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.debug.auth", "true");

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.

			String[] mailIds = mailList.toArray(new String[mailList.size()]);
			InternetAddress[] address = new InternetAddress[mailIds.length];
			for (int i = 0; i < mailIds.length; i++) {
				System.out.println("Sending Mail to " + mailIds[i]);
				try {
					address[i] = new InternetAddress(mailIds[i]);
				} catch (AddressException e) {
					e.printStackTrace();
				}
			}

			message.addRecipients(Message.RecipientType.TO, address);

			// Set Subject: header field
			message.setSubject(subject);

			// Now set the actual message
			message.setText(builder.toString());

			// Send message
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			System.out.println("Sent message successfully....");
			return true;
		} catch (MessagingException mex) {
			mex.printStackTrace();
			return false;
		}
	}
}
