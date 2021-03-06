package com.youdao.techmarket.utils;


import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.youdao.techmarket.R;
import com.youdao.techmarket.widgets.DefineCustomProgressDialog;


public class CommUtils {
	/**
	 * 显示消息
	 * @param toastContent
	 * @param context
	 */
	public static void showMessage(String toastContent,Context context){
		LayoutInflater inflater =  LayoutInflater.from(context) ;
		// 根据指定的布局文件创建一个具有层级关系的View对象
		View layout = inflater.inflate(R.layout.toast, null);
		LinearLayout root = (LinearLayout) layout
				.findViewById(R.id.toast_layout_root);
		root.getBackground().setAlpha(150);// 0~255透明度值
		
		TextView text = (TextView) layout.findViewById(R.id.text);
		text.setText(toastContent);
	
		Toast toast = new Toast(context);
		// 设置Toast的位置
		// toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		// toast.setGravity(Gravity.BOTTOM, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		// 让Toast显示为我们自定义的样子
		toast.setView(layout);
		toast.show();
	}


	public static ProgressDialog progressDialog;

	public static void showProgressDialog(Context context,String title,String message) {
		progressDialog = new ProgressDialog(context);
		progressDialog.setTitle(title);  //设置标题
		progressDialog.setMessage(message); //设置内容
		progressDialog.show();
	}

	public static void dissDialog() {
		if(progressDialog !=null && progressDialog.isShowing()){
			progressDialog.dismiss();
		}
	}
	/**
	 * 获取手机运营商
	 * @param context
	 * @return
	 */
	public static String getProvidersName(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		if (telephonyManager.getSimState() == TelephonyManager.SIM_STATE_READY) {
			String ProvidersName = null;

			// 返回唯一的用户ID;就是这张卡的编号神马的
			String IMSI = telephonyManager.getSubscriberId();
			// IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
			if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
				ProvidersName = "中国移动";

			} else if (IMSI.startsWith("46001")) {
				ProvidersName = "中国联通";

			} else if (IMSI.startsWith("46003")) {
				ProvidersName = "中国电信";
			}

			return ProvidersName;

		} else {

			CommUtils.showMessage("请确认sim卡是否插入或者sim卡暂时不可用！", context);
		}
		return null;

	}
	/**
	 * 获取手机品牌  
	 * @return
	 * Build.MODEL 手机型号
	 * Build.BRAND 手机品牌
	 */
	public static String getPhoneBrand(){
		   Build bd = new Build();
		//return Build.BRAND + Build.MODEL  +Build.DEVICE;
		return Build.BRAND ;
	}	
	/**
	 * 获取手机当前系统的版本号
	 * @return
	 */
	public static String getAndroidSDKVersion() {
        String version = null;
        try {
            version = Build.VERSION.RELEASE;
        } catch (NumberFormatException e) {
           
        }
        return version;
    }
	
	/**
	 * 获取应用的版本号
	 * @param context
	 * @return
	 */

	public static String getVersionCode(Context context) {
		// 获取packagemanager的实例
		String version = null;
		try {
			PackageManager packageManager = context.getPackageManager();
			// getPackageName()是你当前类的包名，0代表是获取版本信息
			PackageInfo packInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			version = packInfo.versionName;

		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return version;
	}

	/**
	 * 隐藏软键盘的方法
	 * 
	 * @param context
	 *            要传入的上下文
	 */
	public static void hiddenSoft(Context context) {
		// 取得输入方法的服务类
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		View view = ((Activity) context).getCurrentFocus();
		if (view != null) {
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);// 隐藏软键盘

		}
	}
	/**
	 * 显示软件盘的方法，这里要采用延时操作，不然会显示不了软键盘。主要是登录，注册时一进去就自动弹出键盘。
	 */
	public static void showSoft(final EditText editText) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				InputMethodManager inputManager = (InputMethodManager) editText
						.getContext().getSystemService(
								Context.INPUT_METHOD_SERVICE);
				inputManager.showSoftInput(editText, 0);
			}

		}, 50);

	}
	
	// 获取百度推送的AppKey
    public static String getMetaValue(Context context, String metaKey) {
        Bundle metaData = null;
        String apiKey = null;
        if (context == null || metaKey == null) {
        	return null;
        }
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (null != ai) {
                metaData = ai.metaData;
            }
            if (null != metaData) {
            	apiKey = metaData.getString(metaKey);
            }
        } catch (NameNotFoundException e) {

        }
        return apiKey;
    }
	
	
	private static DefineCustomProgressDialog myprogressDialog = null;
	
	/**
	 * 显示loading
	 */
	public static void startProgressDialog(Activity context ,String message){
		
			myprogressDialog = DefineCustomProgressDialog.createDialog(context);
			myprogressDialog.setMessage(message);
		
		
		myprogressDialog.show();
	}
	/**
	 * 关闭loading
	 */
	public static void stopProgressDialog(){
		if (myprogressDialog != null&&myprogressDialog.isShowing()){
			myprogressDialog.dismiss();
			myprogressDialog = null;
		}
	}
}
