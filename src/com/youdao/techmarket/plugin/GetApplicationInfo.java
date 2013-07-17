package com.youdao.techmarket.plugin;

import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.youdao.techmarket.R;
import com.youdao.techmarket.api.JpushManager;
import com.youdao.techmarket.utils.DeviceUtils;

/**
 * 得到用户uid，及设备信息，并上传服务器的插件　
 * 
 * @author JunJun
 * 
 */
public class GetApplicationInfo extends CordovaPlugin {

	private static final String GETINFO = "getApplicationInfo";


	@Override
	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {
		final String uid = args.getString(0);

		if (action.equals(GETINFO)) {

			if (!("".equals(uid)) || uid != null) {
				postDatatoServer(uid);
				return true;
			}
		}

		return false;
	}

	/**
	 * 取得数据并上传到服务器
	 * 
	 * @param uid
	 */
	private void postDatatoServer(String uid) {
		// provider = new LocationProvider(cordova.getActivity()) ;
		// LocationBean station = provider.getLocation() ;
//
		final String uuid = DeviceUtils.getUUID(cordova.getActivity());
		final String app_id = cordova.getActivity().getResources()
				.getString(R.string.app_id);
//		final String appversion = CommUtils.getVersionCode(cordova
//				.getActivity());
//		final String sdkversion = CommUtils.getAndroidSDKVersion();
//
//		final String operators = CommUtils.getProvidersName(cordova
//				.getActivity());
//		final String phonebrands = CommUtils.getPhoneBrand();
//		final String latitude = null;// station.getLatitude() ;
//		final String longitude = null; // station.getLongitude() ;
		
		
		JpushManager.getInstance().sendUid(cordova.getActivity(), uid, uuid,app_id, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject arg0) {
				super.onSuccess(arg0);
				//Log.d("******************************:", arg0.toString()) ;
			}
		}) ;

//		JpushManager.getInstance().jpushSendData(cordova.getActivity(), app_id,
//				uuid, uid, appversion, sdkversion, latitude, longitude,
//				operators, phonebrands, new JsonHttpResponseHandler() {
//					@Override
//					public void onSuccess(JSONArray object) {
//						super.onSuccess(object);
//
//					}
//				});

	}
}
