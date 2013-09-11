package com.youdao.techmarket;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;
import android.util.Log;

import com.youdao.techmarket.domain.User;
/**
 * For developer startup JPush SDK  和取得　经纬度初始化
 * 
 * 一般建议在自定义 Application 类里初始化。也可以在主 Activity 里。
 */
public class YouDaoApplication extends Application {
	   private static final String TAG = "YouDaoApplication";
	   
	   private SharedPreferences mSharedPreferences; 
	   private User user = null;

	    @Override
	    public void onCreate() {
	    	 Log.d(TAG, "onCreate");
	         super.onCreate();
	 		
	         
	         mSharedPreferences = getSharedPreferences("com.youdao.techmarket",
	 				MODE_PRIVATE);
	         
	    }

	    /**
		 * 清除登录的用户信息
		 */
		public void clearUser() {
			Editor editor = mSharedPreferences.edit();
			editor.remove("login_user");
			editor.commit();
			user = null;
			//  ("clearUser");
		}

		/**
		 * 获取登录的用户信息
		 */
		public User getUser() {
			if (user == null) {
				user = (User) readObjectToShared(mSharedPreferences.getString(
						"login_user", null));
				//  ("getUser"
				// + mSharedPreferences.getString("login_user", null));
			}
			return user;
		}

		/**
		 * 记住登录的用户信息
		 */
		public void setUser(User user) {
			this.user = user;
			if (user != null) {
				Editor editor = mSharedPreferences.edit();
				editor.putString("login_user", saveObjectToShared(user));
				editor.commit();
				// System.out.println("getUser" + saveObjectToShared(user));
			}
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
