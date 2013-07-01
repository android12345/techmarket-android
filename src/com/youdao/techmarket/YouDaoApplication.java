package com.youdao.techmarket;

import com.youdao.techmarket.utils.DeviceUtils;

import android.app.Application;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;
/**
 * For developer startup JPush SDK  和取得　经纬度初始化
 * 
 * 一般建议在自定义 Application 类里初始化。也可以在主 Activity 里。
 */
public class YouDaoApplication extends Application {
	   private static final String TAG = "YouDaoApplication";
	   

	    @Override
	    public void onCreate() {
	    	 Log.d(TAG, "onCreate");
	         super.onCreate();
	 		
	         JPushInterface.setDebugMode(true); 	//设置开启日志,发布时请关闭日志
	         
	         JPushInterface.init(this);     		// 初始化 JPush
	         JPushInterface.setAliasAndTags(this, DeviceUtils.getUUID(this), null) ;//极光设置别名
	         
	    }
		
}
