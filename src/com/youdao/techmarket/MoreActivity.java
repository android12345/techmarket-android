package com.youdao.techmarket;

import org.apache.cordova.CordovaWebView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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
		
//		this.setOnLoadUrlListener(new OnLoadUrlListener() {
//			
//			@Override
//			public void onloadUrl(String url) {
//				Toast.makeText(MoreActivity.this, "123:"+url, 0).show() ;
//				Log.d("++++++++++++++++++++++++++++++more", url) ;
//				
//				//initAndLoadUrl(cordovaWebView, "file:///android_asset/innovation/index.html") ;
//				//tabHost.setCurrentTabByTag("innovation") ;
//			//	xayoudao://infomars:loadpageinfo/123456
//				
//				String infos[] = url.split(":") ;
//				if("xayoudao".equals(infos[0])){
//					String jumptab = infos[1] ;
//					String info = infos[2] ;
//					Intent intent = new Intent(MoreActivity.this,MainActivity.class) ;
//					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//					intent.putExtra("currenttab", jumptab) ;
//					intent.putExtra("appendurl", info) ;
//					startActivity(intent) ;
//				}
//
//			}
//		}) ;
		
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
