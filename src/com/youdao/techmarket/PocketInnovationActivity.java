package com.youdao.techmarket;

import org.apache.cordova.CordovaWebView;

import android.os.Bundle;


/**
 * 掌上创新界面
 * @author fengxue
 *
 */
public class PocketInnovationActivity extends BaseActivity {
	
	private CordovaWebView cordovaWebView = null ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_pocketinoo) ;
		
		cordovaWebView = (CordovaWebView) findViewById(R.id.inoocordovaWebView);
	
		super.initAndLoadUrl(cordovaWebView, "file:///android_asset/www/index.html") ;
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
