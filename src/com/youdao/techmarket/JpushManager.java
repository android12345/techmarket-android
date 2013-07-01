package com.youdao.techmarket;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.youdao.techmarket.utils.HttpClientUtils;
import com.youdao.techmarket.utils.ServicesHolder;

/**
 * 向服务器发送极光有关信息的api
 *
 */
public class JpushManager {

	private static JpushManager instance;
	
	private JpushManager(){} ;
	public static JpushManager getInstance() {
		if (instance == null)
			instance = new JpushManager();
		return instance;
	}
	
	/**
	 * 
	 * @param context
	 * @param appid 应用ID
	 * @param uuid 手机的唯一标识
	 * @param uid　用户ID
	 * @param appversion 应用版本
	 * @param sdkversion　SDK版本　
	 * @param latitude　纬度
	 * @param longitude 精度
	 * @param operators 运营间
	 * @param phonebrands　手机品牌
	 * @param handler
	 */
	public void jpushSendData(Context context, String appid, String uuid,String uid ,String appversion,String sdkversion,String latitude,String longitude,String operators,String  phonebrands,
			AsyncHttpResponseHandler handler) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("caid", appid) ;
		params.put("udid", uuid) ;
		params.put("cuid", uid) ;
		params.put("versions", appversion) ;
		params.put("sys_versions", sdkversion) ;
		params.put("latitude", latitude) ;
		params.put("longitude", longitude) ;
		params.put("operator", operators) ;
		params.put("brand", phonebrands) ;
		
		HttpClientUtils.post(ServicesHolder.api(ServicesHolder.PUSH_ANDROID_ADD), params, context, handler) ;

	}
	
	/**
	 * 给服务器传用户id
	 * @param context
	 * @param cuid
	 * @param uuid 手机的唯一标识
	 * @param appid 应用ID
	 * @param handler
	 */
	public void sendUid(Context context ,String cuid,String uuid,String appid ,AsyncHttpResponseHandler handler){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cuid", cuid) ;
		params.put("udid", uuid) ;
		params.put("caid", appid) ;
		HttpClientUtils.post(ServicesHolder.api(ServicesHolder.PUSH_ANDROID_ADD), params, context, handler) ;
	}
}
