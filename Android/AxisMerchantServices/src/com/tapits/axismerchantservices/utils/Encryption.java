package com.tapits.axismerchantservices.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.kobjects.base64.Base64;

public class Encryption {
	private static byte[] sharedkey = { 0x01, 0x02, 0x03, 0x05, 0x07, 0x0B,
			0x0D, 0x11, 0x12, 0x11, 0x0D, 0x0B, 0x07, 0x02, 0x04, 0x08, 0x01,
			0x02, 0x03, 0x05, 0x07, 0x0B, 0x0D, 0x11 };

	private static byte[] sharedvector = { 0x01, 0x02, 0x03, 0x05, 0x07, 0x0B,
			0x0D, 0x11 };

	public static String encrypt(String plaintext) throws Exception {
		Cipher c = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(sharedkey, "DESede"),
				new IvParameterSpec(sharedvector));
		byte[] encrypted = c.doFinal(plaintext.getBytes("UTF-8"));
		return Base64.encode(encrypted);
	}

	public static String decrypt(String ciphertext) throws Exception {
		Cipher c = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(sharedkey, "DESede"),
				new IvParameterSpec(sharedvector));
		byte[] decrypted = c.doFinal(Base64.decode(ciphertext));
		return new String(decrypted, "UTF-8");
	}

}
