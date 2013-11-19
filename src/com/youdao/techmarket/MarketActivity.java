package com.youdao.techmarket;

import org.apache.cordova.CordovaWebView;

import android.os.Bundle;

import com.youdao.techmarket.utils.LogUtil;

/**
 * 大市场界面
 * 
 * @author junjun
 * 
 */
public class MarketActivity extends BaseActivity {

	private CordovaWebView cordovaWebView = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_marekt);

		cordovaWebView = (CordovaWebView) findViewById(R.id.marketcordovaWebView);
		String loadInfo = getIntent().getStringExtra("appendurl") ;
		
		
		if(loadInfo!=null && !"".equals(loadInfo)){
			super.initAndLoadUrl(cordovaWebView, "file:///android_asset/market/index.html#"+loadInfo);
			
			LogUtil.d("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%market", "file:///android_asset/home/index.html#"+loadInfo) ;
			
		}else{
			super.initAndLoadUrl(cordovaWebView, "file:///android_asset/market/index.html");
		}
		
		
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
