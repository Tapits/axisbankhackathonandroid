package com.revamobile.custom.adfileutils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.webkit.MimeTypeMap;

import com.tapits.axismerchantservices.utils.Constants;
import com.tapits.axismerchantservices.utils.Globals;
import com.tapits.axismerchantservices.utils.Utils;

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
public class FileUtils {

	/**
	 * Copy InputStream to OutputStream method
	 * 
	 * @param is
	 * @param os
	 */

	public static void copyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception e) {
			Utils.logD(e.toString());
		}
	}

	/**
	 * Decode image file method
	 * 
	 * @param file
	 *            Decode Image File
	 * @return Bitmap
	 */
	public static Bitmap decodeFile(File file, boolean isResize) {
		try {
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;

			if (Constants.isUsingFileEncryption)
				BitmapFactory.decodeStream(
						FileEncryption.decrypt(new FileInputStream(file)),
						null, o);
			else
				BitmapFactory.decodeStream(new FileInputStream(file), null, o);

			int REQUIRED_SIZE = 120;

			if (!isResize
					&& (Globals.screenWidth > 0 && Globals.screenHeight > 0)) {
				if (Globals.screenWidth > Globals.screenHeight)
					REQUIRED_SIZE = Globals.screenHeight;
				else
					REQUIRED_SIZE = Globals.screenWidth;
			}

			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			// if (isResize) {
			while (true) {

				if (width_tmp / 2 < REQUIRED_SIZE
						|| height_tmp / 2 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}
			// }
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			// Log.d("SWATHI", "Scale " + scale);
			if (Constants.isUsingFileEncryption)
				return BitmapFactory.decodeStream(
						FileEncryption.decrypt(new FileInputStream(file)),
						null, o2);
			else
				return BitmapFactory.decodeStream(new FileInputStream(file),
						null, o2);
		} catch (FileNotFoundException e) {
			Utils.logD(e.toString());
		} catch (Exception e) {

			e.printStackTrace();
		}

		return null;
	}

	/**
	 * InputStream To String convert method
	 * 
	 * @param is
	 *            InputStream to convert
	 * @return InputStream
	 * @throws Exception
	 */

	public static String inputStreamToString(InputStream is) {
		String str = "";
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		try {
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}
			br.close();
		} catch (IOException e) {
			Utils.logD(e.toString());
		}

		str = sb.toString();

		// Utils.printLog("InputStream To String : " + str);
		return str;
	}

	/**
	 * String To InputStream convert method
	 * 
	 * @param str
	 *            String to convert
	 * @return String
	 */
	public static InputStream stringToInputStream(String str) {
		try {
			return new ByteArrayInputStream(str.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			Utils.logD(e.toString());
		}
		return new ByteArrayInputStream(str.getBytes());

	}

	public static String getMimeType(String fileName) {

		String mimeType = "";

		String fileExtension = fileName.substring(
				fileName.lastIndexOf(".") + 1, fileName.length());
		// mimeType = "application/octet-stream";
		mimeType = "text/plain";
		String typ = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
				fileExtension);
		if (typ != null)
			mimeType = typ;

		return mimeType;
	}

	public static String getBase64FromBitmap(Bitmap bitmap) throws Throwable {
		String base64Img = null;

		if (bitmap != null) {
			try {
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.JPEG, 50,
						byteArrayOutputStream);
				byte[] byteArray = byteArrayOutputStream.toByteArray();

				base64Img = Base64.encodeToString(byteArray, Base64.DEFAULT);
			} catch (Exception e) {
				throw e;
			}

		}
		return base64Img;
	}

	public static String getBase64FromFileName(String name) throws Throwable {
		String base64Img = null;

		File file = new File(name);

		Bitmap bitmap = decodeFile(file, false);
		// Bitmap bitmap = BitmapFactory.decodeFile(name);

		if (bitmap != null) {
			try {
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.JPEG, 70,
						byteArrayOutputStream);
				byte[] byteArray = byteArrayOutputStream.toByteArray();

				base64Img = Base64.encodeToString(byteArray, Base64.DEFAULT);
			} catch (Exception e) {
				throw e;
			}

		}
		return base64Img;
	}

	public static Bitmap getBitmapFromBase64(String base64Img) throws Throwable {
		Bitmap bitmap = null;

		try {
			byte[] imageAsBytes = Base64.decode(base64Img.getBytes(),
					Base64.DEFAULT);

			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length,
					opts);
			BitmapFactory.Options thumbopts = new BitmapFactory.Options();
			thumbopts.inSampleSize = 4;

			bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0,
					imageAsBytes.length, thumbopts);

			// bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0,
			// imageAsBytes.length);
		} catch (Exception e) {
			throw e;
		}

		return bitmap;
	}

}