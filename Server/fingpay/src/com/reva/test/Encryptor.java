package com.reva.test;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class Encryptor {
	
	private static String key = "fingpayfingpayfi"; // 128 bit key
	private static String initVector = "Reva1234Reva1234"; // 16 bytes IV

	 public static String encrypt(String value) {
	        try {
	            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
	            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

	            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

	            byte[] encrypted = cipher.doFinal(value.getBytes());
	            System.out.println("encrypted string: "+ Base64.encodeBase64String(encrypted));

	            return Base64.encodeBase64String(encrypted);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }

	        return null;
	    }

	    public static String decrypt(String encrypted) {
	        try {
	            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
	            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

	            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

	            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

	            return new String(original);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }

	        return null;
	    }

	    public static void main(String[] args) {
	        String ecript = encrypt("{\"activeStatus\":0,\"deviceId\":0,\"imei\":\"352405063160548\",\"macAddr\":\"38:2D:D1:38:CD:74\",\"make\":\"samsung\",\"merchantId\":1,\"model\":\"SM-T231\",\"pushToken\":\"APA91bG7EuJ29BBdEx70qK_IzLfoYz3ez4zTV5GGJ9wi3VhICtQgpFraSrajgpwf09AoNXCkzBKWdGbq9ig24ZatrcHV5gwJWKreQ0bfcxdYMpBhGnTBtPjmYoeaRA-2jBH-Tcu8eOKz\",\"registeredTimestamp\":0}");

	        String decript = decrypt(ecript);
	        System.out.println("ecript : "+ecript);
	        System.out.println("decript : "+decript);
	    }
	}
