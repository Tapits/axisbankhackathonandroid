package com.revamobile.custom.adfileutils;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.tapits.axismerchantservices.utils.Utils;

/**
 * 
 * Developed by Ashish Das
 * 
 * Email: adas@revamobile.com Email: adas@revasolutions.com
 * 
 * Date: October 27, 2012
 * 
 * All code (c) 2011 Reva Tech Solutions (India) Private Limited (Reva Mobile)
 * 
 * All rights reserved
 * 
 * 
 */
public class ImageLoader {

	public MemoryCache memoryCache = new MemoryCache();
	public FileCache fileCache;
	private Map<ImageView, String> imageViews = Collections
			.synchronizedMap(new WeakHashMap<ImageView, String>());
	public ExecutorService executorService;
	public String folderName;
	public Context context;

	// public final int resId = R.drawable.hourglass;

	/**
	 * Create ImageLoader
	 * 
	 * @param context
	 * @param folderName
	 */

	public ImageLoader(Context context, String folderName) {
		super();
		setContext(context);
		setFolderName(folderName);
		setFileCache(false);
		executorService = Executors.newFixedThreadPool(5);
		clearCache();
	}

	public ImageLoader(Context context) {
		super();
		setContext(context);
		setFolderName(null);
		setFileCache(false);
		executorService = Executors.newFixedThreadPool(5);
		clearCache();
	}

	public void setFileCache(boolean isUserAnswer) {
		String fileTyp = FileCache.imageTyp;
		if (isUserAnswer)
			fileTyp = FileCache.userAnswerImageType;

		this.fileCache = new FileCache(context, getFolderName(), fileTyp);
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	/**
	 * Display image in loader
	 * 
	 * @param url
	 * @param imageView
	 * @param progressBar
	 */
	public void DisplayImage(String url, ImageView imageView,
			ProgressBar progressBar, boolean isResize) {
		Utils.logD("URL : " + url);
		imageViews.put(imageView, url);
		Bitmap bitmap = memoryCache.get(url);
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
			imageView.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.GONE);

		} else {
			queuePhoto(url, false, false, imageView, progressBar, isResize);
			// imageView.setImageResource(resId);
			progressBar.setVisibility(View.VISIBLE);

		}
	}

	public void DisplayBase64Image(String base64Image, ImageView imageView,
			ProgressBar progressBar, boolean isResize) {
		imageViews.put(imageView, base64Image);
		Bitmap bitmap = memoryCache.get(base64Image);
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
			imageView.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.GONE);

		} else {
			queuePhoto(base64Image, true, false, imageView, progressBar,
					isResize);
			// imageView.setImageResource(resId);
			progressBar.setVisibility(View.VISIBLE);

		}
	}

	public void DisplayImageFile(String filePath, ImageView imageView,
			ProgressBar progressBar, boolean isResize) {
		imageViews.put(imageView, filePath);
		Bitmap bitmap = memoryCache.get(filePath);
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
			imageView.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.GONE);

		} else {
			queuePhoto(filePath, false, true, imageView, progressBar, isResize);
			// imageView.setImageResource(resId);
			progressBar.setVisibility(View.VISIBLE);

		}
	}

	/**
	 * start thread to getting bitmap
	 * 
	 * @param url
	 * @param imageView
	 * @param progressBar
	 */
	private void queuePhoto(String url, boolean isBase64Img,
			boolean isFilePath, ImageView imageView, ProgressBar progressBar,
			boolean isResize) {
		PhotoToLoad p = new PhotoToLoad(url, imageView, progressBar,
				isBase64Img, isFilePath);
		try {
			executorService.submit(new PhotosLoader(p, imageView, isResize));
		} catch (OutOfMemoryError e) {
		}
	}

	/**
	 * 
	 * Photo To load object class
	 * 
	 */
	private class PhotoToLoad {
		public String url;
		public ImageView imageView;
		public ProgressBar progressBar;
		public boolean isBase64Img;
		public boolean isFilePath;

		public PhotoToLoad(String url, ImageView imageView,
				ProgressBar progressBar, boolean isBase64Img, boolean isFilePath) {
			super();
			this.url = url;
			this.imageView = imageView;
			this.progressBar = progressBar;
			this.isBase64Img = isBase64Img;
			this.isFilePath = isFilePath;
		}

	}

	/**
	 * 
	 * Used to getting bitmap in the thread
	 * 
	 */
	class PhotosLoader implements Runnable {
		PhotoToLoad photoToLoad;
		ImageView imageView;
		boolean isResize;

		PhotosLoader(PhotoToLoad photoToLoad, ImageView imageView,
				boolean isResize) {
			this.photoToLoad = photoToLoad;
			this.imageView = imageView;
			this.isResize = isResize;
		}

		@Override
		public void run() {
			if (imageViewReused(photoToLoad))
				return;
			Bitmap bmp = null;
			try {
				if (photoToLoad.isFilePath) {
					bmp = fileCache.getBitmapToFilePath(photoToLoad.url,
							isResize);
				} else if (photoToLoad.isBase64Img) {
					bmp = fileCache.getBitmapToBase64String(photoToLoad.url,
							isResize);
				} else {
					bmp = fileCache.getBitmapToHttp(photoToLoad.url, isResize);
				}
			} catch (Throwable e) {
				if (e instanceof OutOfMemoryError)
					memoryCache.clear();
				bmp = null;
			}

			memoryCache.put(photoToLoad.url, bmp);

			if (imageViewReused(photoToLoad))
				return;

			BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);
			Activity a = (Activity) photoToLoad.imageView.getContext();
			a.runOnUiThread(bd);

		}
	}

	boolean imageViewReused(PhotoToLoad photoToLoad) {
		String tag = imageViews.get(photoToLoad.imageView);
		if (tag == null || !tag.equals(photoToLoad.url))
			return true;
		return false;
	}

	/**
	 * Used to display bitmap in the UI thread
	 * 
	 * 
	 */
	class BitmapDisplayer implements Runnable {
		Bitmap bitmap;
		PhotoToLoad photoToLoad;

		public BitmapDisplayer(Bitmap b, PhotoToLoad p) {
			bitmap = b;
			photoToLoad = p;
		}

		@Override
		public void run() {
			if (imageViewReused(photoToLoad))
				return;
			if (bitmap != null) {
				photoToLoad.imageView.setImageBitmap(bitmap);
				photoToLoad.imageView.setVisibility(View.VISIBLE);
				photoToLoad.progressBar.setVisibility(View.GONE);
			} else {
				// photoToLoad.imageView.setImageResource(resId);
				photoToLoad.progressBar.setVisibility(View.GONE);
			}
		}
	}

	public void clearCache() {
		memoryCache.clear();
		fileCache.clear();
	}

}
