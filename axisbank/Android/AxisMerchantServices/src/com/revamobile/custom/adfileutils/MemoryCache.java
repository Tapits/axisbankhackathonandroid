package com.revamobile.custom.adfileutils;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.graphics.Bitmap;
import android.util.Log;

import com.tapits.axismerchantservices.utils.Constants;

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
public class MemoryCache {

	private static final String TAG = Constants.LOG_TAG;
	private Map<String, Bitmap> cache = Collections
			.synchronizedMap(new LinkedHashMap<String, Bitmap>(10, 1.5f, true));

	/**
	 * Last argument true for LRU ordering
	 */
	private long size = 0;// current allocated size
	private long limit = 1000000;// max memory in bytes

	/**
	 * Create MemroyCache
	 * 
	 * use 25% of available heap size
	 */
	public MemoryCache() {

		setLimit(Runtime.getRuntime().maxMemory() / 4);
	}

	/**
	 * set MemoryCache Limit
	 * 
	 * @param newlimit
	 */
	public void setLimit(long newlimit) {
		limit = newlimit;
		Log.i(TAG, "MemoryCache will use up to " + limit / 1024. / 1024. + "MB");
	}

	/**
	 * get Image in Memory Cache
	 * 
	 * @param id
	 * @return Bitmap
	 */
	public Bitmap get(String id) {
		try {
			if (!cache.containsKey(id))
				return null;
			// NullPointerException sometimes happen here
			// http://code.google.com/p/osmdroid/issues/detail?id=78
			return cache.get(id);
		} catch (NullPointerException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * add id and image Memory Cache
	 * 
	 * @param id
	 * @param bitmap
	 */
	public void put(String id, Bitmap bitmap) {
		try {
			if (cache.containsKey(id))
				size -= getSizeInBytes(cache.get(id));
			cache.put(id, bitmap);
			size += getSizeInBytes(bitmap);
			checkSize();
		} catch (Throwable th) {
			th.printStackTrace();
		}
	}

	/**
	 * get Memory Cache size
	 */
	private void checkSize() {
		Log.i(TAG, "cache size=" + size + " length=" + cache.size());
		if (size > limit) {
			Iterator<Entry<String, Bitmap>> iter = cache.entrySet().iterator();
			// least recently accessed item will be the first one iterated

			while (iter.hasNext()) {
				Entry<String, Bitmap> entry = iter.next();
				size -= getSizeInBytes(entry.getValue());
				iter.remove();
				if (size <= limit)
					break;
			}
			Log.i(TAG, "Clean cache. New size " + cache.size());
		}
	}

	/**
	 * clear Memory Cache data
	 */
	public void clear() {
		try {
			// NullPointerException sometimes happen here
			// http://code.google.com/p/osmdroid/issues/detail?id=78
			cache.clear();
			size = 0;
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * get Bitmap Size in Byte
	 * 
	 * @param bitmap
	 * @return
	 */
	long getSizeInBytes(Bitmap bitmap) {
		if (bitmap == null)
			return 0;
		return bitmap.getRowBytes() * bitmap.getHeight();
	}
}