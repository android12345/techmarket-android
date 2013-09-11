/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */

package com.youdao.techmarket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.cordova.Config;
import org.apache.cordova.DroidGap;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;
import com.youdao.techmarket.utils.DeviceUtils;
import com.youdao.techmarket.utils.NetWorkUtils;

public class techmarket extends DroidGap implements OnClickListener {

	private BroadcastReceiver connectionReceiver;
//	private LocationProvider locationProvider =  null ;

	private static Handler handler = new Handler() { // 主线程
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				Editor editor = preferences.edit();
				editor.putString("versionName", (String) msg.obj);
				editor.commit();
				break;

			default:
				break;
			}
		};
	};

	protected Dialog guideDialog;

	private int splashId = 0;// 是否有spalsh界面的标志

	private static SharedPreferences preferences;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.cordova.DroidGap#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		setFullScreen();
		super.onCreate(savedInstanceState);
		Log.d("设备的UUID:", DeviceUtils.getUUID(this));
		super.setIntegerProperty("loadUrlTimeoutValue", 10000); 
		//JpushReceiver.activity = this;

		Intent intent = this.getIntent();
        
//        locationProvider =  new LocationProvider(this) ;
//        
//        locationProvider.startLocation() ;

		displaySplash();

		displayGuide();

		addNetWorkReceiver();

		registerWeixin();
	//	super.setStringProperty("errorUrl", "file:///android_asset/www/error.html");
		// 取得JpushReceiver　传过来的附加字段的值
		String uricontent = intent.getStringExtra("uri");
		Log.d(TAG, "推送消息要打开的地址:" + Config.getStartUrl() + "?" + uricontent);
		String url = Config.getStartUrl() + "?"
				+ (uricontent == null ? "" : uricontent);

		if (splashId != 0) {// 如果设置了splash，这里就设置spalsh运行时间，没有则不设置
			super.loadUrl(url, 120000);
		} else {
			super.loadUrl(url);
		}
		// cancelFullscreen();

	}

	/**
	 * Removes the Dialog that displays the splash screen
	 */
	public void removeSplashScreen() {
		super.removeSplashScreen();
		this.runOnUiThread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				cancelFullscreen();
			}
			
		});
	}

	/**
	 * 取得网络类型并注册广播
	 */
	private void addNetWorkReceiver() {
		// 注册一个广播监听器
		connectionReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				ConnectivityManager connectMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
				NetworkInfo mobNetInfo = connectMgr
						.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
				NetworkInfo wifiNetInfo = connectMgr
						.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

				if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
					// unconnect network
					// mNetWorkState = NetworkUtils.NETWORN_NONE;
					NetWorkUtils.setNetWorkDialog(techmarket.this);
				}

				// else {
				// if (mobNetInfo.isConnected()) {
				// mNetWorkState = NetworkUtils.NETWORN_MOBILE;
				// } else if (wifiNetInfo.isConnected()) {
				// mNetWorkState = NetworkUtils.NETWORN_WIFI;
				// }
				// // connect network
				// }
			}

		};

		setRegisterReceiver(connectionReceiver);
	}

	/**
	 * 首先判断drawable目录下是否有名为spalsh.png的图片，如果有就设置spalsh ，如果没有就不设置
	 */
	private void displaySplash() {

		splashId = getResources().getIdentifier("splash", "drawable",
				this.getPackageName());
		if (splashId != 0) {
			super.setIntegerProperty("splashscreen", splashId);
		}
	}

	/**
	 * 取得引导图片的方法 1,是第一次打开，2，目录中有guide的图片
	 * 
	 * @throws IOException
	 */

	private void displayGuide() {

		preferences = getSharedPreferences("currentVersion", MODE_PRIVATE);

		String cacheVersionName = preferences.getString("versionName", "");
		String versionName = getVersion();

		// Log.d(TAG, cacheVersionName + "//" + versionName);

		if (!cacheVersionName.equals(versionName)) {

			// Log.d(TAG, "equals = " + !versionName.equals(cacheVersionName));

			handler.sendMessage(Message.obtain(handler, 1, versionName));

			List<Integer> guideImageIds = getGuideImages();

			if (guideImageIds.size() > 0) {

				creatGuideLayout(guideImageIds);

			}
		}

	}

	// 设置全屏
	public void setFullScreen() {
		// application.setLocationOption();
		getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);// 清除FLAG
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

	}

	/**
	 * 取消全屏方法
	 */
	public void cancelFullscreen() {
		final WindowManager.LayoutParams attrs = getWindow().getAttributes();
		attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setAttributes(attrs);
		getWindow()
				.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

	}

	/**
	 * 创建新手引导布局在root上
	 */
	private void creatGuideLayout(List<Integer> images) {

		guideDialog = new Dialog(this,
				android.R.style.Theme_Translucent_NoTitleBar);
		// check to see if the splash screen should be full screen
		if ((getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) == WindowManager.LayoutParams.FLAG_FULLSCREEN) {
			guideDialog.getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
//		Guide guide = new Guide(this, images, this, guideDialog);
//		View view = guide.getView();
//		guideDialog.setContentView(view);
//		guideDialog.setCancelable(false);
//		guideDialog.show();
	}

	/**
	 * 取得当前应用的版本名
	 * 
	 * @return
	 */
	private String getVersion() {

		PackageManager manager = this.getPackageManager();
		try {
			PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
			return info.versionName; // 版本名，versionCode同理
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void onClick(View v) {
		// 新手引导消失
		if (guideDialog != null && guideDialog.isShowing()) {
			guideDialog.dismiss();
			guideDialog = null;
		}
	}

	/**
	 * 
	 * @return 返回引导图片的资源ID
	 */
	private List<Integer> getGuideImages() {
		List<Integer> images = new ArrayList<Integer>();
		// String[] ss = new String[]{};
		for (int i = 1; i < 10; i++) {
			int guideImageId = getResources().getIdentifier("guide_" + i,
					"drawable", this.getPackageName());
			if (guideImageId != 0) {
				images.add(guideImageId);
			}
		}
		return images;
	}

	/**
	 * 取消网络检测广播
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(connectionReceiver);

	}

	/**
	 * 注册网络检测广播
	 * 
	 * @param receiver
	 */
	private void setRegisterReceiver(BroadcastReceiver receiver) {
		this.connectionReceiver = receiver;
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(connectionReceiver, intentFilter);
	}

	/**
	 * 创建和注册AppID到微信
	 * 
	 * @param context
	 */
	public void registerWeixin() {
		String wxAppID = this.getResources().getString(R.string.weixin_key);
		if (wxAppID != null && !wxAppID.equals("") && !wxAppID.equals("wxkey")) {
			IWXAPI api = WXAPIFactory.createWXAPI(this, wxAppID);
			Log.d(TAG, "registerWeixin " + wxAppID);
			boolean result = api.registerApp(wxAppID);
			Log.d(TAG, "weixin register " + result);
			// api.handleIntent(((Activity) this).getIntent(),
			// new IWXAPIEventHandler() {
			// @Override
			// public void onResp(BaseResp arg0) {
			// SendAuth.Resp resp = (SendAuth.Resp) arg0;
			// System.out.println(resp.userName);
			// }
			//
			// @Override
			// public void onReq(BaseReq arg0) {
			// }
			// });
		}
	}
	public void onResume() {
	    super.onResume();
	    MobclickAgent.onResume(this);
	}
	public void onPause() {
	    super.onPause();
	    MobclickAgent.onPause(this);
	}
//	@Override
//	public void onReceivedError(int arg0, String arg1, String arg2) {
//		
//	}
	
}
