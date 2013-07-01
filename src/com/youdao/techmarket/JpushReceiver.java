package com.youdao.techmarket;

import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;
/**
 * @author junjun

 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 * 自定义广播接收者来拦截推过来的信息或通知
 */
public class JpushReceiver extends BroadcastReceiver {

	private static final String TAG = "JpushReceiver";
	 static techmarket activity ;

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
        Log.d(TAG, "onReceive - " + intent.getAction());
         
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "接收Registration Id : " + regId);
            Log.d(TAG, "uuid:"+JPushInterface.getUdid(context)) ;
            //send the Registration Id to your server...
        }else if (JPushInterface.ACTION_UNREGISTER.equals(intent.getAction())){
        	String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "接收UnRegistration Id : " + regId);
          //send the UnRegistration Id to your server...
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
        	Log.d(TAG, "接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
        
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "接收到推送下来的通知的ID: " + notifactionId);
            Log.d(TAG, "uuid:"+JPushInterface.getUdid(context)) ;
        	
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "用户点击打开了通知");
            
            //附加字段的值
            String urivaules = null ;
            try {
            	  JSONObject jsonObject = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA)) ;
                  urivaules = jsonObject.optString("uri") ;
                  Log.d(TAG, "接收到的附加字段: " + urivaules);
              	
                 // 打开自定义的Activity
              	Intent i = new Intent(context, techmarket.class);
              	i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              	i.putExtra("uri", urivaules) ;
              	activity.finish() ;
              	context.startActivity(i);
              
			} catch (Exception e) {
				e.printStackTrace() ;
			}
        
//            Intent i = new Intent(Intent.ACTION_VIEW , Uri.parse("http://www.baidu.com"));
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(i);
        	
        } else {
        	Log.d(TAG, "Unhandled intent - " + intent.getAction());
        }
	}

}
