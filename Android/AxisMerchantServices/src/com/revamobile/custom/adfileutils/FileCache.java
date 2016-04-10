package com.revamobile.custom.adfileutils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Base64;

import com.tapits.axismerchantservices.utils.Constants;
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
public class FileCache {

	public static final String FOLDER_NAME = "Axis Merchant Services";

	public static final String textTyp = "text";
	public static final String imageTyp = "image";
	public static final String audioTyp = "audio";
	public static final String videoTyp = "video";
	public static final String userAnswerImageType = "user answer";

	private File mainDir;
	private File childDir;
	private File subDir;

	/**
	 * FileCache class
	 * 
	 * @param context
	 * @param folderName
	 * @param fileType
	 */

	public FileCache(Context context, String folderName, String fileType) {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
			mainDir = new File(
					android.os.Environment.getExternalStorageDirectory(),
					FOLDER_NAME);
		else
			mainDir = context.getCacheDir();
		if (!mainDir.exists())
			mainDir.mkdirs();

		if (Utils.isValidString(folderName) && Utils.isValidString(fileType)) {
			if (Constants.isUsingFileEncryption)
				folderName = String.valueOf(folderName.hashCode());

			childDir = new File(mainDir, folderName);
			if (!childDir.exists())
				childDir.mkdirs();

			subDir = new File(childDir, fileType);
			if (!subDir.exists())
				subDir.mkdirs();
		} else if (Utils.isValidString(folderName)) {
			if (Constants.isUsingFileEncryption)
				folderName = String.valueOf(folderName.hashCode());
			subDir = new File(mainDir, folderName);
			if (!subDir.exists())
				subDir.mkdirs();

		} else if (Utils.isValidString(fileType)) {
			subDir = new File(mainDir, fileType);
			if (!subDir.exists())
				subDir.mkdirs();

		} else
			subDir = mainDir;

	}

	/**
	 * file save method
	 * 
	 * @param is
	 * @param file
	 * @throws IOException
	 */
	public void saveFile(InputStream is, File file) throws IOException {
		byte[] byteArr = null;
		InputStream inputStream = null;

		if (Constants.isUsingFileEncryption)
			byteArr = IOUtils.toByteArray(is);

		if (file.exists()) {
			file.delete();
		}
		try {
			file.createNewFile();
		} catch (IOException e) {

		}

		if (file.exists()) {
			// file.setReadOnly();

			OutputStream os = new FileOutputStream(file);

			if (Constants.isUsingFileEncryption) {
				try {
					inputStream = FileEncryption.encrypt(byteArr);
				} catch (Exception e) {
					inputStream = null;
				}
				FileUtils.copyStream(inputStream, os);
			} else
				FileUtils.copyStream(is, os);

			os.close();
		}

	}

	/**
	 * Get File method
	 * 
	 * @param name
	 * @return File
	 */
	public File getFile(String name) {
		String filename = name;
		// if (Constants.isUseingFileEncryption)
		filename = String.valueOf(name.hashCode());

		File file = new File(subDir, filename);
		// file.setWritable(true);
		return file;

	}

	/**
	 * Delete File method
	 * 
	 * @param name
	 */
	public void deleteFile(String name) {
		File f = getFile(name);
		if (f != null)
			f.delete();
	}

	/**
	 * Clear all data in sub dir folder method
	 */
	public void clear() {
		clearFile(subDir);
	}

	private void clearFile(File file) {
		File[] files = file.listFiles();
		if (files == null)
			return;
		for (File f : files) {
			if (f != null) {
				if (f.isDirectory()) {
					clearFile(f);
				} else
					f.delete();
			}
		}
	}

	/**
	 * Get Text File
	 * 
	 * @param name
	 * @return InputStream
	 */
	public InputStream getTextFile(String name) {
		File file = getFile(name);
		InputStream is = null;
		try {
			if (Constants.isUsingFileEncryption)
				is = FileEncryption.decrypt(new FileInputStream(file));
			else
				is = new FileInputStream(file);
		} catch (Exception e) {
			is = null;
		}
		if (is != null) {
			return is;
		}
		return null;
	}

	/**
	 * Save text file
	 * 
	 * @param value
	 * @param name
	 * @throws IOException
	 */
	public void saveTextFile(String value, String name) throws IOException {
		File file = getFile(name);
		InputStream is = FileUtils.stringToInputStream(value);
		saveFile(is, file);
	}

	/**
	 * Save Image from a url
	 * 
	 * @param url
	 * @throws Throwable
	 */
	public void saveImageToHttp(String url, boolean isResize) throws Throwable {
		getBitmapToHttp(url, isResize);
	}

	/**
	 * Save Bitmap File
	 * 
	 * @param bitmap
	 * @param name
	 * @return
	 */
	public Uri saveBitmapFile(Bitmap bitmap, String name) {
		Uri uri = null;
		OutputStream outStream = null;

		File file = getFile(name);

		if (file.exists()) {
			file.delete();
		}
		try {
			uri = Uri.fromFile(file);
			outStream = new FileOutputStream(file);
			// bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
			outStream.flush();
			outStream.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return uri;
	}

	/**
	 * Get Bitmap From url
	 * 
	 * @param url
	 * @return
	 * @throws Throwable
	 */
	public Bitmap getBitmapToHttp(String url, boolean isResize)
			throws Throwable {
		if (url == null || url.length() == 0) {
			Utils.logD("Image url is Null Or length is zero");
			return null;
		}
		File file = getFile(url);
		Bitmap b = FileUtils.decodeFile(file, isResize);
		if (b != null)
			return b;
		try {
			Bitmap bitmap = null;

			URL imageUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) imageUrl
					.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			conn.setInstanceFollowRedirects(true);
			InputStream is = conn.getInputStream();
			saveFile(is, file);
			bitmap = FileUtils.decodeFile(file, isResize);
			return bitmap;
		} catch (Throwable ex) {
			throw ex;
		}
	}

	public Bitmap getBitmapToFilePath(String filePath, boolean isResize)
			throws Throwable {
		if (filePath == null || filePath.length() == 0) {
			Utils.logD("Image File Path is Null Or length is zero");
			return null;
		}
		File file = new File(filePath);
		// Log.e("SWATHI", "Image File Path : " + filePath);
		Bitmap bitmap = FileUtils.decodeFile(file, isResize);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
		if (bitmap != null)
			return bitmap;

		return null;
	}

	public Bitmap getBitmapToBase64String(String base64String, boolean isResize)
			throws Throwable {
		if (base64String == null || base64String.length() == 0) {
			Utils.logD("Base64 String Image is Null Or length is zero");
			return null;
		}
		File file = getFile(base64String);
		Bitmap b = FileUtils.decodeFile(file, isResize);
		if (b != null)
			return b;
		try {

			Bitmap bitmap = null;
			byte[] byteArr = Base64.decode(base64String, Base64.DEFAULT);
			InputStream is = new ByteArrayInputStream(byteArr);
			saveFile(is, file);
			bitmap = FileUtils.decodeFile(file, isResize);

			return bitmap;
		} catch (Throwable ex) {
			Utils.logE(ex.toString());
			throw ex;
		}
	}

	public String getBase64StringToName(String base64String, boolean isResize)
			throws Throwable {
		String base64img = null;
		if (base64String == null || base64String.length() == 0) {
			Utils.logD("Base64 String Image is Null Or length is zero");
		} else {
			File file = getFile(base64String);
			Bitmap b = FileUtils.decodeFile(file, isResize);
			if (b != null) {
				try {

					ByteArrayOutputStream stream = new ByteArrayOutputStream();
					b.compress(Bitmap.CompressFormat.PNG, 100, stream);
					byte[] byteArray = stream.toByteArray();

					base64img = Base64
							.encodeToString(byteArray, Base64.DEFAULT);

					if (Utils.isValidString(base64img)) {
						base64img = base64img.trim();
						base64img = base64img.replace("\n", "");
					}

				} catch (Throwable ex) {
					throw ex;
				}
			}
		}
		return base64img;

	}
}
