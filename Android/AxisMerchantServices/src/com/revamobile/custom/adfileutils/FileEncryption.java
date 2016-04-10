package com.revamobile.custom.adfileutils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.io.IOUtils;
import org.kobjects.base64.Base64;

/**
 * 
 * Developed by Ashish Das
 * 
 * Email: adas@revamobile.com ,adas@revasolutions.com
 * 
 * Date: October 27, 2012
 * 
 * All code (c) 2011 Reva Tech Solutions (India) Private Limited (Reva Mobile)
 * 
 * All rights reserved
 * 
 * 
 */
public class FileEncryption {

	private static byte[] sharedkey = { 0x0B, 0x07, 0x02, 0x04, 0x08, 0x01,
			0x01, 0x02, 0x03, 0x05, 0x07, 0x0B, 0x0D, 0x11, 0x12, 0x11, 0x0D,
			0x05, 0x07, 0x0B, 0x0D, 0x11, 0x02, 0x03 };

	private static byte[] sharedvector = { 0x01, 0x02, 0x03, 0x05, 0x07, 0x0B,
			0x0D, 0x11 };

	/**
	 * String Encryption
	 * 
	 * @param plaintext
	 * @return String
	 * @throws Exception
	 */
	public static String encrypt(String plaintext) throws Exception {
		Cipher c = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(sharedkey, "DESede"),
				new IvParameterSpec(sharedvector));
		byte[] encrypted = c.doFinal(plaintext.getBytes("UTF-8"));
		return Base64.encode(encrypted);
	}

	/**
	 * String Decryption
	 * 
	 * @param ciphertext
	 * @return String
	 * @throws Exception
	 */
	public static String decrypt(String ciphertext) throws Exception {
		Cipher c = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(sharedkey, "DESede"),
				new IvParameterSpec(sharedvector));
		byte[] decrypted = c.doFinal(Base64.decode(ciphertext));
		return new String(decrypted, "UTF-8");
	}

	/**
	 * InputStream Encryption
	 * 
	 * @param plainInputStream
	 * @return InputStream
	 * @throws Exception
	 */
	public static InputStream encrypt(InputStream plainInputStream)
			throws Exception {
		Cipher c = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(sharedkey, "DESede"),
				new IvParameterSpec(sharedvector));
		byte[] encrypted = c.doFinal(IOUtils.toByteArray(plainInputStream));
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
	 * byte[] Decryption
	 * 
	 * @param cipherInputStream
	 * @return InputStream
	 * @throws Exception
	 */
	public static InputStream decrypt(byte[] cipherByteArr) throws Exception {
		Cipher c = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(sharedkey, "DESede"),
				new IvParameterSpec(sharedvector));
		byte[] decrypted = c.doFinal(Base64.decode(new String(cipherByteArr)));
		return new ByteArrayInputStream(decrypted);
	}

}
