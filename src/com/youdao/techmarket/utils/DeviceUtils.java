package com.youdao.techmarket.utils;

import java.util.UUID;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * 设备唯一id
 * 
 */
public class DeviceUtils {
	public static String deviceUUID(Context context) {
		final TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		final String tmDevice, tmSerial, androidId;
		tmDevice = "" + tm.getDeviceId();
		tmSerial = "" + tm.getSimSerialNumber();
		androidId = ""
				+ android.provider.Settings.Secure.getString(
						context.getContentResolver(),
						android.provider.Settings.Secure.ANDROID_ID);
		UUID deviceUUID = new UUID(androidId.hashCode(),
				((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());

		return deviceUUID.toString();
	}
	/**
	 * 取得设备的UUID 去掉-连接符
	 * @param context
	 * @return   设备的UUID
	 */
	public static String getUUID(Context context) {
		
		StringBuilder builder = new StringBuilder() ;
		String uuids[] = deviceUUID(context).split("-") ;
		for(int i=0;i<uuids.length;i++){
			builder.append(uuids[i]) ;
		}
		return builder.toString();
	}
}
