package com.youdao.techmarket;

import org.apache.cordova.CordovaWebView;

import android.os.Bundle;

import com.youdao.techmarket.utils.LogUtil;

/*
 * 我的界面
 */
public class MineActivity extends BaseActivity{
	
	private  CordovaWebView cordovaWebView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_mine) ;

		cordovaWebView = (CordovaWebView) findViewById(R.id.minecordovaWebView);
		
		String loadInfo = getIntent().getStringExtra("appendurl") ;
		if(loadInfo!=null && !"".equals(loadInfo)){
			super.initAndLoadUrl(cordovaWebView, "file:///android_asset/mine/index.html#"+loadInfo);
			
			LogUtil.d("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%mine", "file:///android_asset/mine/index.html#"+loadInfo) ;
			
		}else{
			super.initAndLoadUrl(cordovaWebView, "file:///android_asset/mine/index.html");
		}
	  
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	
	}

}
