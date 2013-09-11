package com.youdao.techmarket;

import org.apache.cordova.CordovaWebView;

import android.os.Bundle;

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
	    
		super.initAndLoadUrl(cordovaWebView, "file:///android_asset/mine/index.html") ;
	  
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
