package com.youdao.techmarket;

import org.apache.cordova.CordovaWebView;

import android.os.Bundle;

/**
 * 更多界面
 * @author junjun
 *
 */
public class MoreActivity extends BaseActivity  {
	
	private CordovaWebView cordovaWebView = null ;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_more) ;
		
		cordovaWebView = (CordovaWebView) findViewById(R.id.morecordovaWebView);
		super.initAndLoadUrl(cordovaWebView, "file:///android_asset/more/index.html") ;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
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
}
