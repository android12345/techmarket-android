package com.youdao.techmarket;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import com.youdao.techmarket.utils.DeviceUtils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;
/**
 * For developer startup JPush SDK  和取得　经纬度初始化
 * 
 * 一般建议在自定义 Application 类里初始化。也可以在主 Activity 里。
 */
public class YouDaoApplication extends Application {
	   private static final String TAG = "YouDaoApplication";
	   
	   private SharedPreferences mSharedPreferences; 
	   

	    @Override
	    public void onCreate() {
	    	 Log.d(TAG, "onCreate");
	         super.onCreate();
	 		
	         JPushInterface.setDebugMode(true); 	//设置开启日志,发布时请关闭日志
	         
	         JPushInterface.init(this);     		// 初始化 JPush
	         JPushInterface.setAliasAndTags(this, DeviceUtils.getUUID(this), null) ;//极光设置别名
	         
	         mSharedPreferences = getSharedPreferences("com.youdao.techmarket",
	 				MODE_PRIVATE);
	         
	    }
	    
	    
	    /**
		 * 序列化对象
		 * 
		 * @param obj
		 * @return
		 */
		@SuppressLint("NewApi")
		private String saveObjectToShared(Object obj) {

			ByteArrayOutputStream output = new ByteArrayOutputStream();
			try {
				ObjectOutputStream oos = new ObjectOutputStream(output);
				oos.writeObject(obj);
				oos.close();
			} catch (IOException e) {

			}
			return Base64.encodeToString(output.toByteArray(), Base64.DEFAULT);

		}

		/**
		 * 反序列化对象
		 * 
		 * @param str
		 * @return
		 */
		@SuppressWarnings("unused")
		@SuppressLint("NewApi")
		private Object readObjectToShared(String str) {
			if (str != null) {
				byte[] base64Bytes = Base64.decode(str.getBytes(), Base64.DEFAULT);
				ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
				ObjectInputStream ois;
				try {
					ois = new ObjectInputStream(bais);
					return ois.readObject();
				} catch (StreamCorruptedException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return null;
		}
		
}
