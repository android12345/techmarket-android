package com.youdao.techmarket.plugin;

import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;

import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.youdao.techmarket.utils.CommUtils;

/**
 * 应用更新的插件
 * @author fengxue
 *
 */
public class CheckVersion extends CordovaPlugin {
	
	private final static String UPDATE = "checkVersion" ;
	private Handler handler = new Handler(Looper.getMainLooper()){
		public void handleMessage(android.os.Message msg) {
			
			switch (msg.what) {
			case 0:
				CommUtils.dissDialog() ;
				break;

			default:
				break;
			}
		};
		
	} ;

	@Override
	public boolean execute(String action, JSONArray args,
			 final CallbackContext callbackContext) throws JSONException {
		if(UPDATE.equals(action)){
			CommUtils.showProgressDialog(cordova.getActivity(), "查检更新", "正在检查，请稍候!");
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					Looper.prepare() ;
					SystemClock.sleep(2000) ;
					update(callbackContext) ;
					handler.sendMessage(Message.obtain(handler, 0, null));
					Looper.loop() ;
				}
			}).start() ;
		//	update(callbackContext) ;
			return true ;
		}
		return false ;
	}

	private void update(final CallbackContext callbackContext) {
		//com.umeng.common.Log.LOG = true;
		UmengUpdateAgent.update(this.cordova.getActivity());
		UmengUpdateAgent.setUpdateAutoPopup(false);
		UmengUpdateAgent.setUpdateOnlyWifi(false) ;
		UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
			@Override
			public void onUpdateReturned(int updateStatus,
					UpdateResponse updateInfo) {
				
				switch (updateStatus) {
				case 0: // has update
					//handler.sendMessage(Message.obtain(handler, 0, null));
					UmengUpdateAgent.showUpdateDialog(cordova.getActivity(),
							updateInfo);
					break;
				case 1: // has no update
					//handler.sendMessage(Message.obtain(handler, 0, null));
					CommUtils.showMessage("没有新版本", cordova.getActivity()) ;
					callbackContext.success("没有新版本") ;
					break;
				case 2: // none wifi
					//handler.sendMessage(Message.obtain(handler, 0, null));
					CommUtils.showMessage("没有wifi连接， 只在wifi下更新", cordova.getActivity()) ;
					callbackContext.success("没有wifi连接， 只在wifi下更新") ;
					break;
				case 3: // time out
					//handler.sendMessage(Message.obtain(handler, 0, null));
					CommUtils.showMessage("超时", cordova.getActivity()) ;
					callbackContext.success("超时") ;
					break;

				}
			}
		});
	}
}
