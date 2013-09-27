package com.youdao.techmarket.plugin;

import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import com.youdao.techmarket.YouDaoApplication;
import com.youdao.techmarket.domain.User;

/**
 * 取得用户登录成功信息的插件 比如 token等
 * 
 * @author fengxue
 * 
 */
public class GetUserInfoPlugin extends CordovaPlugin {

	public static final String GETUSERINFO = "getuserinfo";
	public static final String GETUSERPARARM = "getuserinfoWithParam";
	public static final String LOGOUT = "deleteUserInfo";
	private YouDaoApplication application = null;

	@Override
	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {

		application = (YouDaoApplication) this.cordova.getActivity()
				.getApplication();
		User user = application.getUser();

		if (GETUSERINFO.equals(action)) {

			getUserInfo(user, callbackContext);

			return true;
		}
		if (GETUSERPARARM.equals(action)) {

			getUserParams(args, callbackContext, user);

			return true;
		}
		if (LOGOUT.equals(action)) {
			clearUser(user, callbackContext);
		}

		return false;
	}

	private void clearUser(User user, CallbackContext callbackContext) {
		if (user != null) {
			application.clearUser();
			callbackContext.success("退出登录成功!");
		}

	}

	/**
	 * 根据参数取得相应的值
	 * 
	 * @param args
	 * @param callbackContext
	 * @param user
	 * @throws JSONException
	 */
	private void getUserParams(JSONArray args, CallbackContext callbackContext,
			User user) throws JSONException {
		final String arg = args.getString(0);

		if (user != null) {
			if ("token".equals(arg)) {
				callbackContext.success(user.getToken());
			}
			if ("userType".equals(arg)) {
				callbackContext.success(user.getUserType());
			}
		}else{
			callbackContext.success("");
		}
	}
	/**
	 * 以jsonarray的形式　返回所有用户信息
	 * @param user
	 * @param callbackContext
	 */
	private void getUserInfo(User user, CallbackContext callbackContext) {

		if (user != null) {

			JSONArray array = new JSONArray();
			array.put(user.getToken());
			array.put(user.getUserType());
			callbackContext.success(array);
		}

	}
}
