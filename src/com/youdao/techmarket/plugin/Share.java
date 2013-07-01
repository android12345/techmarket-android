package com.youdao.techmarket.plugin;

import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import com.youdao.techmarket.domain.ShareInfo;
import com.youdao.techmarket.utils.ShareUtil;

/**
 * @author junjun 分享功能的插件
 */
public class Share extends CordovaPlugin {

	public final String SHARE = "share";

	// private static String uMengID ;
	private ShareUtil shareUtil = null;

	@Override
	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {

		final ShareInfo shareInfo = ShareInfo.parse(args);

		if (SHARE.equals(action)) {
			try {

				Runnable runnable = new Runnable() {

					@Override
					public void run() {
						if (shareUtil == null)
							shareUtil = new ShareUtil();
						
						shareUtil.share(cordova.getActivity(), shareInfo);
						// 清理自定义平台的数据
						shareUtil.clearCustomPlatforms() ;
						
					}
				};
				cordova.getActivity().runOnUiThread(runnable); // 在UI线程运行

			} catch (Exception e) {
				e.printStackTrace();
			}

			// Log.d(TAG, "projectName is :"+projectName) ;
			callbackContext.success("准备开始分享!");
			return true;
		}
		return false;
	}
}
