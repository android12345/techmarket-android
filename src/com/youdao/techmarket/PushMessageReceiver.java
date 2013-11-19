package com.youdao.techmarket;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.baidu.android.pushservice.PushConstants;
import com.youdao.techmarket.utils.LogUtil;

/**
 * Push消息处理receiver
 */
public class PushMessageReceiver extends BroadcastReceiver {
	/** TAG to Log */
	public static final String TAG = PushMessageReceiver.class.getSimpleName();

	AlertDialog.Builder builder;
	
	private SharedPreferences preferences ;

	/**
	 * 
	 * @param context
	 *            Context
	 * @param intent
	 *            接收的intent
	 */
	@Override
	public void onReceive(final Context context, Intent intent) {

		LogUtil.d(TAG, ">>> Receive intent: \r\n" + intent);
		
		preferences = context.getSharedPreferences("push_user", context.MODE_PRIVATE) ;

		if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) { //这里是获取消息
			//获取消息内容
			String message = intent.getExtras().getString(
					PushConstants.EXTRA_PUSH_MESSAGE_STRING);

			//消息的用户自定义内容读取方式
			LogUtil.i(TAG, "onMessage: " + message);

			//用户在此自定义处理消息,以下代码为demo界面展示用
//			Intent responseIntent = null;
//			responseIntent = new Intent(Utils.ACTION_MESSAGE);
//			responseIntent.putExtra(Utils.EXTRA_MESSAGE, message);
//			responseIntent.setClass(context, PushDemoActivity.class);
//			responseIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			context.startActivity(responseIntent);

		} else if (intent.getAction().equals(PushConstants.ACTION_RECEIVE)) {
			//处理绑定等方法的返回数据
			//PushManager.startWork()的返回值通过PushConstants.METHOD_BIND得到

			String method1 = intent.getStringExtra("method") ;
			if (PushConstants.METHOD_BIND.equals(method1)) {
				LogUtil.d("########################################","123456789456465") ;
			}
			
			//获取方法
			final String method = intent
					.getStringExtra(PushConstants.EXTRA_METHOD);
			//方法返回错误码。若绑定返回错误（非0），则应用将不能正常接收消息。
			//绑定失败的原因有多种，如网络原因，或access token过期。
			//请不要在出错时进行简单的startWork调用，这有可能导致死循环。
			//可以通过限制重试次数，或者在其他时机重新调用来解决。
			LogUtil.d("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@", method) ;
			final int errorCode = intent
					.getIntExtra(PushConstants.EXTRA_ERROR_CODE,
							PushConstants.ERROR_SUCCESS);
			//返回内容
			final String content = new String(
					intent.getByteArrayExtra(PushConstants.EXTRA_CONTENT));
			
			//把返回的json信息解析出来。
			JSONObject jsonObject = null ;
			try {
				 jsonObject = new JSONObject(content) ;
				 JSONObject json = jsonObject.optJSONObject("response_params") ;
				 String userid = json.optString("user_id") ;
					LogUtil.d("bdpush_userid", userid) ;
				 Editor editor = preferences.edit() ;
				 editor.putString("user_id", userid) ;
				 editor.commit() ;
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			LogUtil.d("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~", content) ;
			
			
			
			
			//用户在此自定义处理消息,以下代码为demo界面展示用	
			LogUtil.d(TAG, "onMessage: method : " + method);
			LogUtil.d(TAG, "onMessage: result : " + errorCode);
			LogUtil.d(TAG, "onMessage: content : " + content);
	
		
		LogUtil.d("----------------------------", "method : " + method + "\n result: " + errorCode
				+ "\n content = " + content) ;

			
		//可选。通知用户点击事件处理
		} else if (intent.getAction().equals(
				PushConstants.ACTION_RECEIVER_NOTIFICATION_CLICK)) {
			LogUtil.d(TAG, "intent=" + intent.toUri(0));
			
			Intent aIntent = new Intent();
			aIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			
			String str = intent.getStringExtra(PushConstants.EXTRA_EXTRA) ;
			
			/**
			 * str里面的数据   服务器返回的 
			
			 */
			//保存服务端推送下来的附加字段。这是个 JSON 字符串。
			//String extra = intent.getStringExtra(PushConstants.EXTRA_EXTRA) ;
			
			
			LogUtil.d("********************", str.toString()) ;
			/**
			 * 09-16 13:54:22.830: D/********************(10517): {"readUri":"xayoudao:market:loadPushInfo\/123456"}

			 */
			JSONObject jsonObject ;
			String resultArray []  ;
			String jumpwhere ; //跳转到哪里
			String loadPushInfo  ;
			try {
				jsonObject = new JSONObject(str) ;
				String resultJson = jsonObject.optString("readUri") ;
				if(resultJson != null){
					resultArray = resultJson.split(":") ;
					if(resultArray[0].equals("xayoudao")){
						jumpwhere = resultArray[1] ;
						loadPushInfo = resultArray[2] ;
						
						aIntent.setClass(context, MainActivity.class);
						aIntent.putExtra("currenttab", jumpwhere) ;
						aIntent.putExtra("appendurl", loadPushInfo) ;
						context.startActivity(aIntent);
					}else if(resultArray[0].equals("xayoudaonew")){
						aIntent.setClass(context, SplashActivity.class);
						context.startActivity(aIntent);
					}
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
//			aIntent.setClass(context, CustomActivity.class);
//			String title = intent 
//					.getStringExtra(PushConstants.EXTRA_NOTIFICATION_TITLE);
//			aIntent.putExtra(PushConstants.EXTRA_NOTIFICATION_TITLE, title);
//			String content = intent
//					.getStringExtra(PushConstants.EXTRA_NOTIFICATION_CONTENT);
			
			//保存服务端推送下来的附加字段。这是个 JSON 字符串。
		//	String extra = intent.getStringExtra(PushConstants.EXTRA_EXTRA) ;
			
		//	Log.d("********************", extra.toString()) ;
//			aIntent.putExtra(PushConstants.EXTRA_NOTIFICATION_CONTENT, content);
//
//			context.startActivity(aIntent);
		}
		
	}

}
