package com.tapits.axismerchantservices.utils;

import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import android.util.Log;

public class HttpRequest {

	private static final String CONTENT_TYPE = "application/json";

	public static InputStream getInputStreamFromUrl(String url)
			throws FingPayException {
		InputStream content = null;
		Utils.logD("URL : " + url);
		try {
			HttpGet httpGet = new HttpGet(url);
			// httpGet.setHeader(header)
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = httpclient.execute(httpGet);
			content = response.getEntity().getContent();
		} catch (UnknownHostException e) {
			Utils.logD(e.getMessage());
			throw new FingPayException(Constants.DEVICE_CONNECTIVITY, "");
		} catch (Exception e) {
			Utils.logD(e.getMessage());
			throw new FingPayException(Constants.NETWORK_UNAVAILABLE, "");
		}
		return content;
	}

	public static InputStream getInputStreamFromUrl(String urlPath,
			List<NameValuePair> nameValuePairs) throws FingPayException {

		InputStream content = null;

		Utils.logD("URL : " + urlPath);

		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(urlPath);
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			content = response.getEntity().getContent();

		} catch (UnknownHostException e) {
			Utils.logD(e.getMessage());
			throw new FingPayException(Constants.DEVICE_CONNECTIVITY, "");
		} catch (Exception e) {
			Log.d(Constants.LOG_TAG, e.getMessage());
			Globals.lastErrMsg = Constants.NETWORK_UNAVAILABLE;
			throw new FingPayException(Constants.NETWORK_UNAVAILABLE, "");
		}

		return content;
	}

	public static HttpResponse postData(String restAPIPath,
			List<NameValuePair> data) throws FingPayException {

		Utils.logD("URL : " + restAPIPath);
		Utils.logD("Data : " + data);

		try {

			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(restAPIPath);
			httpPost.setEntity(new UrlEncodedFormEntity(data));
			return httpclient.execute(httpPost);

		} catch (UnknownHostException e) {
			Utils.logD(e.getMessage());
			throw new FingPayException(Constants.DEVICE_CONNECTIVITY, "");
		} catch (Exception e) {
			Log.d(Constants.LOG_TAG, e.getMessage());
			Globals.lastErrMsg = Constants.NETWORK_UNAVAILABLE;
			throw new FingPayException(Constants.NETWORK_UNAVAILABLE, "");

		}

	}

	public static HttpResponse postData(String restAPIPath, String data)
			throws FingPayException {

		Utils.logD("URL : " + restAPIPath);
		Utils.logD("Data : " + data);

		try {

			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(restAPIPath);
			httpPost.setEntity(new StringEntity(data, "UTF8"));
			httpPost.setHeader("Content-type", "application/json");

			return httpclient.execute(httpPost);

		} catch (UnknownHostException e) {
			Utils.logD(e.getMessage());
			throw new FingPayException(Constants.DEVICE_CONNECTIVITY, "");
		} catch (Exception e) {
			Log.d(Constants.LOG_TAG, e.getMessage());
			Globals.lastErrMsg = Constants.NETWORK_UNAVAILABLE;
			throw new FingPayException(Constants.NETWORK_UNAVAILABLE, "");
		}

	}

//	public static Object postTransactionData(String restAPIPath, String data,
//			String header, Context context) throws FingPayException {
//
//		Utils.logD("URL : " + restAPIPath);
//		Utils.logD("Data : " + data);
//
//		try {
//
//			HttpClient httpclient = new DefaultHttpClient();
//			HttpPost httpPost = new HttpPost(restAPIPath);
//			httpPost.setEntity(new StringEntity(data, "UTF8"));
//			httpPost.setHeader("Content-type", "application/json");
//
//			httpPost.addHeader("TOKEN", header);
//
//			HttpResponse response = httpclient.execute(httpPost);
//
//			if (response != null) {
//				int statusCode = response.getStatusLine().getStatusCode();
//				Utils.logD("Status Code : " + statusCode);
//				if (statusCode == 200) {
//					return Utils.parseResponse(response,
//							TransactionResponse.class);
//				} else if (statusCode == 420) {
//					DataSource dataSource = new DataSource(context);
//					String pin = dataSource.shardPreferences
//							.getValue(Constants.MPIN);
//					String id = dataSource.shardPreferences
//							.getValue(Constants.MERCHANT_ID);
//					if (Utils.isValidString(pin) && Utils.isValidString(id)) {
//						MpinData model = new MpinData(Integer.parseInt(id),
//								Integer.parseInt(pin));
//						String url = FingPayUtils.getLoginUrl();
//						if (Utils.isValidString(url) && model != null) {
//
//							ObjectMapper mapper = new ObjectMapper();
//							String mData = mapper.writeValueAsString(model);
//
//							if (Utils.isValidString(mData)) {
//								HttpResponse res = postData(url, mData);
//								if (res != null) {
//									Header[] headers = res.getAllHeaders();
//									if (headers != null) {
//										for (Header h : headers) {
//											if (h.getName()
//													.equals("Set-Cookie")) {
//												String arr = h.getValue()
//														.split(";")[0];
//												arr = arr.replace(
//														"JSESSIONID=", "");
//												Utils.logD(arr);
//												dataSource.shardPreferences
//														.set(Constants.SESSION_ID,
//																arr);
//												break;
//											}
//										}
//									}
//								}
//							}
//						}
//
//						HttpPost httppost = new HttpPost(restAPIPath);
//						httppost.setEntity(new StringEntity(data, "UTF8"));
//						httppost.setHeader("Content-type", "application/json");
//
//						String sessionId = dataSource.shardPreferences
//								.getValue(Constants.SESSION_ID);
//						if (Utils.isValidString(sessionId))
//							httppost.addHeader("TOKEN", sessionId);
//
//						HttpResponse httpResponse = httpclient
//								.execute(httppost);
//
//						if (httpResponse != null) {
//							int statuscode = httpResponse.getStatusLine()
//									.getStatusCode();
//							Utils.logD("Status Code : " + statuscode);
//							if (statuscode == 200) {
//								return Utils.parseResponse(httpResponse,
//										TransactionResponse.class);
//							}
//						}
//					}
//				} else if (statusCode > 500) {
//					Globals.lastErrMsg = Constants.NETWORK_UNAVAILABLE;
//				}
//			}
//
//			return httpclient.execute(httpPost);
//
//		} catch (UnknownHostException e) {
//			Utils.logD(e.getMessage());
//			throw new FingPayException(Constants.DEVICE_CONNECTIVITY, "");
//		} catch (Exception e) {
//			Log.d(Constants.LOG_TAG, e.getMessage());
//			Globals.lastErrMsg = Constants.NETWORK_UNAVAILABLE;
//			throw new FingPayException(Constants.NETWORK_UNAVAILABLE, "");
//		}
//
//	}

	public static Object postData(String restAPIPath, String data,
			Class classOfT) throws FingPayException {

		HttpResponse response;

		Utils.logD("URL : " + restAPIPath);
		Utils.logD("Data : " + data);

		try {

			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(restAPIPath);
			httpPost.setEntity(new StringEntity(data, "UTF-8"));

			httpPost.setHeader("Content-type", CONTENT_TYPE);

			response = httpclient.execute(httpPost);

			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode != 200) {

				if (statusCode == 400 || statusCode < 500) {
					// ErrorResponse errorResponse = (ErrorResponse) Utils
					// .parseResponse(response, ErrorResponse.class);
					// if (errorResponse != null) {
					// Globals.lastErrMsg = errorResponse.getError();
					// }

				} else if (statusCode > 500) {
					Globals.lastErrMsg = Constants.SERVER_ERROR;
					throw new FingPayException("",
							Constants.SERVER_NOT_REACHABLE);
				}

				return null;
			} else {
				return Utils.parseResponse(response.getEntity().getContent(),
						classOfT);
			}

		} catch (UnknownHostException e) {
			Utils.logD(e.getMessage());
			throw new FingPayException(Constants.DEVICE_CONNECTIVITY, "");
		} catch (Exception e) {
			if (!Utils.isValidString(Globals.lastErrMsg)) {
				Globals.lastErrMsg = Constants.NETWORK_UNAVAILABLE;
			}
			throw new FingPayException(Constants.NETWORK_UNAVAILABLE, "");
		}

	}

}
