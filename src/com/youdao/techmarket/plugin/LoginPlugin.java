package com.youdao.techmarket.plugin;

import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Intent;

import com.youdao.techmarket.LoginActivity;

/**
 * 登录的插件
 * @author fengxue
 *
 */
public class LoginPlugin extends CordovaPlugin {
	
	public static final String LOGIN = "login" ;
	@Override
	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {
		
		if(LOGIN.equals(action)){
			
			Intent intent = new Intent() ;
			intent.setClass(cordova.getActivity(),LoginActivity.class) ;
			cordova.getActivity().startActivity(intent) ;
			return true ;
			
		}
		return false ;
	}
}
