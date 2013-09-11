package com.youdao.techmarket.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

/**
 * 
 *         使用 Android Asynchronous Http Client 组件，实现http网络访问，cookie保存等功能。
 */
public class HttpClientUtils {

	private HttpClientUtils() {

	}

	private static final String TAG = "HttpClientUtils";

	private static AsyncHttpClient asyncClient = new AsyncHttpClient();

	/**
	 * 发起post网络访问
	 * 
	 * @param url
	 * @param context
	 * @param responseHandler
	 */
	public static void post(String url, Context context, AsyncHttpResponseHandler responseHandler) {
		post(url, null, context,  responseHandler);
	}

	/**
	 * 发起post网络访问
	 * 
	 * @param url
	 *            访问地址
	 * @param params
	 *            携带的参数
	 * @param context
	 * @param isUseCache
	 *            是否使用本地json缓存
	 * @param responseHandler
	 *            回调接口
	 */
	public static void post(String url, Map<String, Object> params, Context context, AsyncHttpResponseHandler responseHandler) {
		if (null == context) {
			Log.e(TAG, "必须传入Context参数。");
			return;
		}

		PersistentCookieStore cookieStore = new PersistentCookieStore(context);
		asyncClient.setCookieStore(cookieStore);

		RequestParams requestParams = null;
		if (null != params) {
			requestParams = new RequestParams();
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				if (entry.getValue() instanceof File) {
					try {
						requestParams.put(entry.getKey(), (File) entry.getValue());
					} catch (FileNotFoundException e) {
						Log.e(TAG, e.toString());
					}
				} else if (entry.getValue() instanceof String) {
					requestParams.put(entry.getKey(), (String) entry.getValue());
				}
			}
		}
		//Log.d(TAG, url + "?" + requestParams);
		asyncClient.post(context, url, requestParams , responseHandler);
	}

	/**
	 * 发起get请求访问
	 * @param context
	 * @param url 请求的地址
	 * @param params 请求参数
	 * @param responseHandler 回调接口
	 */
	public static void get(Context context, String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		if (null == context) {
			Log.e(TAG, "必须传入Context参数。");
			return;
		}

		PersistentCookieStore cookieStore = new PersistentCookieStore(context);
		asyncClient.setCookieStore(cookieStore);
		asyncClient.get(url, params, responseHandler);
	}

	/**
	 * 取消连接
	 * 
	 * @param context
	 */
	public static void cancel(Context context) {
		asyncClient.cancelRequests(context, true);
	}

}
