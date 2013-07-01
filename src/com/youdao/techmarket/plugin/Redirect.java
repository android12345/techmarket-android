package com.youdao.techmarket.plugin;

import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Intent;
import android.net.Uri;
/**
 * 调用拨号界面的插件
 * @author fengxue
 *
 */
public class Redirect extends CordovaPlugin {
	private final  String CALL = "callNumber" ;
	@Override
	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {
		if(CALL.equals(action)){
			final String number = args.getString(0) ;
			callPhone(number,callbackContext) ;
			return true ;
		}
		return false ;
	}
	/**
	 * 拨打电话的方法
	 * @param number
	 * @param callbackContext
	 */
	private void callPhone(String number, CallbackContext callbackContext) {
		
		if("".equals(number) || null == number){
			callbackContext.error("电话号码不能为空!") ;
		}else{
			callMethod(number);
		}
		
	}
	/**
	 * 调用电话界面的方法
	 * @param number
	 */
	private void callMethod(String number) {
		Intent intent = new Intent() ;
		//直接打出电话界面
		//intent.setAction("android.intent.action.CALL") ;
		//调用拨号界面 
		intent.setAction("android.intent.action.DIAL") ;
		intent.setData(Uri.parse("tel:"+number)) ;
		this.cordova.getActivity().startActivity(intent) ;
	}
}
