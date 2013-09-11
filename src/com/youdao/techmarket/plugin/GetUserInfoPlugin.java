package com.youdao.techmarket.plugin;

import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import com.youdao.techmarket.YouDaoApplication;
import com.youdao.techmarket.domain.User;

/**
 * 取得用户登录成功信息的插件  比如 token等 
 * @author fengxue
 *
 */
public class GetUserInfoPlugin extends CordovaPlugin {

	public static final String GETUSERINFO = "getuserinfo" ;
	private YouDaoApplication application = null ;
	@Override
	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {
		
		if(action.equals(GETUSERINFO)){
			
			getUserInfo(callbackContext) ;
			
			return true ;
		}
		
		
		return false ;
	}
	private void getUserInfo(CallbackContext callbackContext) {
		
		application = (YouDaoApplication) this.cordova.getActivity().getApplication() ;
		User user = application.getUser() ;
		if(user!=null){
			callbackContext.success(user.getToken()) ;
		}
		
	}
}
