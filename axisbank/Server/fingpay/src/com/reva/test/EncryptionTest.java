package com.reva.test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.io.IOUtils;
import org.kobjects.base64.Base64;

public class EncryptionTest {
	private static byte[] sharedkey = { 0x01, 0x02, 0x03, 0x05, 0x07, 0x0B,
		   0x0D, 0x11, 0x12, 0x11, 0x0D, 0x0B, 0x07, 0x02, 0x04, 0x08, 0x01,
		   0x02, 0x03, 0x05, 0x07, 0x0B, 0x0D, 0x11 };

		 private static byte[] sharedvector = { 0x01, 0x02, 0x03, 0x05, 0x07, 0x0B,
		   0x0D, 0x11 };
		 
	public static String encrypt(String plaintext) throws Exception {
		Cipher c = Cipher.getInstance("AES");
		c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(sharedkey, "AES"),
				new IvParameterSpec(sharedvector));
		byte[] encrypted = c.doFinal(plaintext.getBytes("UTF-8"));
		return Base64.encode(encrypted);
	}

//	public static String decrypt(String ciphertext) throws Exception {
//		Cipher c = Cipher.getInstance("AES");
//		c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(sharedkey, "AES"),
//				new IvParameterSpec(sharedvector));
//		byte[] decrypted = c.doFinal(Base64.decode(ciphertext));
//		return new String(decrypted, "UTF-8");
//	}
	
	public static String decrypt(String ciphertext) throws Exception {
		  Cipher c = Cipher.getInstance("AES");
		  c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(sharedkey, "AES"),
		    new IvParameterSpec(sharedvector));
		  byte[] decrypted = c.doFinal(Base64.decode(ciphertext));
		  return new String(decrypted, "UTF-8");
		 }

	/**
	 * byte[] Encryption
	 * 
	 * @param plainByteArr
	 * @return InputStream
	 * @throws Exception
	 */
	public static InputStream encrypt(byte[] plainByteArr) throws Exception {
		Cipher c = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(sharedkey, "DESede"),
				new IvParameterSpec(sharedvector));
		byte[] encrypted = c.doFinal(plainByteArr);
		return new ByteArrayInputStream(Base64.encode(encrypted).getBytes());
	}

	/**
	 * InputStream Decryption
	 * 
	 * @param cipherInputStream
	 * @return InputStream
	 * @throws Exception
	 */
	public static InputStream decrypt(InputStream cipherInputStream)
			throws Exception {
		Cipher c = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(sharedkey, "DESede"),
				new IvParameterSpec(sharedvector));
		byte[] decrypted = c.doFinal(Base64.decode(new String(IOUtils
				.toByteArray(cipherInputStream))));
		return new ByteArrayInputStream(decrypted);
	}
	
	 public static void main(String[] args) {
			try {
//				String ecript = encrypt("EUFcE/1lH/1YeR8IWrozow==");
				String decript = decrypt("EUFcE/1lH/1YeR8IWrozow==");
//				System.out.println("ecript : "+ecript);
				System.out.println("decript : "+decript);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	    }

}
