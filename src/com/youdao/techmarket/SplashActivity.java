package com.youdao.techmarket;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;

/**
 * splash界面
 * @author fengxue
 *
 */
public class SplashActivity extends BaseActivity implements OnClickListener{
	
	//显示引导界面的dialog
	protected Dialog guideDialog;
	//定义preferences
	private static SharedPreferences preferences;
	
	private static Handler handler = new Handler() { // 主线程
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1: //保存应用的版本号
				Editor editor = preferences.edit();
				editor.putString("versionName", (String) msg.obj);
				editor.commit();
				break;

			default:
				break;
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_splash) ;

		preferences = getSharedPreferences("currentVersion", MODE_PRIVATE);

		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				String cacheVersionName = preferences.getString("versionName", "");
				String versionName = getVersion();
				if (!cacheVersionName.equals(versionName)) {  //如果版本不相同，首次进应用会显示引导(版本肯定不同)

					handler.sendMessage(Message.obtain(handler, 1, versionName));

					List<Integer> guideImageIds = getGuideImages();

					if (guideImageIds.size() > 0) { //如果有资源图片的话就显示引导界面

						creatGuideLayout(guideImageIds);

					}else{
						gotoActivity(SplashActivity.this, MainActivity.class) ;
					}
				}else{//版本相同直接跳转到主界面
					gotoActivity(SplashActivity.this, MainActivity.class) ;
				}
				
				
			}
		}, 3000) ;
		
	}
	
	/**
	 * 创建新手引导布局在root上
	 */
	private void creatGuideLayout(List<Integer> images) {

		guideDialog = new Dialog(this,
				android.R.style.Theme_Translucent_NoTitleBar);
		// check to see if the splash screen should be full screen
		if ((getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) == WindowManager.LayoutParams.FLAG_FULLSCREEN) {
			guideDialog.getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
		Guide guide = new Guide(this, images, this, guideDialog);
		View view = guide.getView();
		guideDialog.setContentView(view);
		guideDialog.setCancelable(false);
		guideDialog.show();
	}
	
	/**
	 * 取得当前应用的版本名
	 * 
	 * @return
	 */
	private String getVersion() {

		PackageManager manager = this.getPackageManager();
		try {
			PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
			return info.versionName; // 版本名，versionCode同理
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void onClick(View v) {
		// 新手引导消失
		if (guideDialog != null && guideDialog.isShowing()) {
			gotoActivity(this, MainActivity.class) ;
			guideDialog.dismiss();
			guideDialog = null;
			
		}
	}

	/**
	 * 
	 * @return 返回引导图片的资源ID集合
	 */
	private List<Integer> getGuideImages() {
		List<Integer> images = new ArrayList<Integer>();
		for (int i = 1; i < 10; i++) {
			int guideImageId = getResources().getIdentifier("guide_" + i,
					"drawable", this.getPackageName());
			if (guideImageId != 0) {
				images.add(guideImageId);
			}
		}
		return images;
	}
	
	/**
	 * 打开一个新的Activity
	 */
	public void gotoActivity(Context context, Class<?> cls) {
		Intent intent = new Intent(context, cls);
		startActivity(intent);
		finish();
	}
}
