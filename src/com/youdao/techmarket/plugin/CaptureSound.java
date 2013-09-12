package com.youdao.techmarket.plugin;

import java.io.File;

import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;

/**
 * 录音插件
 * 
 * @author junjun
 * 
 */
public class CaptureSound extends CordovaPlugin {

	private static final String TAG = "CaptureSound";

	private File file = null;
	private MediaPlayer mediaPlayer = null;
	private AudioRecorderManager audioRecorderManager = new AudioRecorderManager();
	private String time; // js传过来的录音时间参数
	private boolean isStop = false;// 标记是否点击了停止

	@Override
	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {
		time = args.getString(0); // 取得设置录音的时间
		if ("audioRecord".equals(action)) { // 开始录制
			startRecording(callbackContext);
		} else if ("audioStop".equals(action)) { // 停止录制
			isStop = true;
			stopRecording(callbackContext);
		} else if ("play".equals(action)) { // 播放 测试用的
			play();
		}
		return false;
	}

	/**
	 * @param callbackContext
	 */
	private void stopRecording(CallbackContext callbackContext) {
		audioRecorderManager.stopRecording(); // 调用录音管理中的开始录音的方
		file = audioRecorderManager.getFile(); // 取得录音文件的路径
		if (file != null) {
			callbackContext.success("录音文件的路径：" + file);
		} else {
			callbackContext.error("文件录制失败或没有录音");
		}
		Log.d(TAG, "音频文件是：" + file);

	}

	private void play() {
		try {
			if (file != null) {
				mediaPlayer = new MediaPlayer();
				mediaPlayer.setDataSource(file.getAbsolutePath());
				mediaPlayer.prepare();
				mediaPlayer.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 开始录音 包括录音时间 默认的是30秒，和传递进来的值
	private void startRecording(final CallbackContext callbackContext) {
		audioRecorderManager.startRecording();
		if ("".equals(time) || null == time) {
			if (isStop) {
			} else {
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						stopRecording(callbackContext); // 默认是30秒
					}
				}, 30000);
				isStop = false;
			}
		} else {
			if (isStop) {
			} else {// 如果没有点击停止，就让他按传递的时间再停止录音
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						stopRecording(callbackContext);
					}
				}, Long.valueOf(time) * 1000); // 设置录音的时间
				isStop = false;
			}
		}

	}

}
