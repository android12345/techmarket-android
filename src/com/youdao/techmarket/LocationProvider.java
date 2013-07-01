package com.youdao.techmarket;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.youdao.techmarket.utils.CommUtils;
import com.youdao.techmarket.utils.DeviceUtils;

public class LocationProvider {
	public static final String TAG = "LocationProvider";

	private static LocationClient mLocationClient = null;

	private static Context context;

	public LocationProvider(Context context) {
		super();
		LocationProvider.context = context;
	}

	/**
	 * 设置参数和开启获取定位
	 */
	public void startLocation() {
		BDLocationListener listener = new BDLocationListener() {

			@Override
			public void onReceiveLocation(BDLocation location) {
				/*
				 * if (location.getCity() == null) { int type =
				 * mLocationClient.requestLocation(); }
				 */

				// Log.d("*************************:", location.getLatitude()
				// + "," + location.getLongitude());

				final String app_id = context.getResources().getString(
						R.string.app_id);
				// Toast.makeText(context, "appid is "+app_id, 0).show() ;
				JpushManager.getInstance().jpushSendData(context, app_id,
						DeviceUtils.getUUID(context), "",
						CommUtils.getVersionCode(context),
						CommUtils.getAndroidSDKVersion(),
						location.getLatitude() + "",
						location.getLongitude() + "",
						CommUtils.getProvidersName(context),
						CommUtils.getPhoneBrand(),
						new JsonHttpResponseHandler() {
							@Override
							public void onSuccess(JSONObject arg0) {
								super.onSuccess(arg0);
//								Log.d("LocationProvider.sendData :", arg0.toString());
							}
						});

				stopListener();
			}

			@Override
			public void onReceivePoi(BDLocation arg0) {
				// return
			}
		};

		mLocationClient = new LocationClient(context);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型为bd09ll
		option.setPriority(LocationClientOption.NetWorkFirst); // 设置网络优先
		option.setProdName("youdao"); // 设置产品线名称
		option.disableCache(true);
		
		mLocationClient.setLocOption(option);
		mLocationClient.registerLocationListener(listener);

		mLocationClient.start();// 将开启与获取位置分开，就可以尽量的在后面的使用中获取到位置
	}

	/**
	 * 停止，减少资源消耗
	 */
	public void stopListener() {
		if (mLocationClient != null && mLocationClient.isStarted()) {
			mLocationClient.stop();
			mLocationClient = null;
		}
	}

	/**
	 * 更新位置
	 */
	public void updateListener() {
		if (mLocationClient != null && mLocationClient.isStarted()) {
			mLocationClient.requestLocation();
		}
	}

}
