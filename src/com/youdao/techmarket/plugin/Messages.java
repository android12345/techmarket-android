package com.youdao.techmarket.plugin;

import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import com.youdao.techmarket.utils.CommUtils;


/**
 * 显示消息的Toast插件
 * @author fengxue
 *
 */
public class Messages extends CordovaPlugin {
	
	private final static String SHOWTOAST = "showMsg" ;
	
	@Override
	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {
		//取得js传过来的消息内容
		final String toastContent = args.getString(0) ;
		if(SHOWTOAST.equals(action)){
			showToast(callbackContext,toastContent) ;
			return true ;
		}
		return false; 
	}
	/**
	 * 显示提醒内容的方法
	 * @param callbackContext
	 */
	private void showToast(CallbackContext callbackContext,String toastContent) {
		if(!("".equals(toastContent))){
			CommUtils.showMessage(toastContent, this.cordova.getActivity()) ;
		}else{
			callbackContext.error("清输入要显示的信息内容!") ;
		}
	}
}
