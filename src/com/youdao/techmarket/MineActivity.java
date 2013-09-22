package com.youdao.techmarket;

import org.apache.cordova.CordovaWebView;

import android.os.Bundle;
import android.util.Log;

/*
 * 我的界面
 */
public class MineActivity extends BaseActivity{
	
	private  CordovaWebView cordovaWebView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_mine) ;
		
//		this.setOnLoadUrlListener(new OnLoadUrlListener() {
//			
//			@Override
//			public void onloadUrl(String url) {
//				Toast.makeText(MineActivity.this, "123:"+url, 0).show() ;
//				Log.d("++++++++++++++++++++++++++++++market", url) ;
//				
//				//initAndLoadUrl(cordovaWebView, "file:///android_asset/innovation/index.html") ;
//				//tabHost.setCurrentTabByTag("innovation") ;
//			//	xayoudao://infomars:loadpageinfo/123456
//				
//				String infos[] = url.split(":") ;
//				if("xayoudao".equals(infos[0])){
//					String jumptab = infos[1] ;
//					String info = infos[2] ;
//					Intent intent = new Intent(MineActivity.this,MainActivity.class) ;
//					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//					intent.putExtra("currenttab", jumptab) ;
//					intent.putExtra("appendurl", info) ;
//					startActivity(intent) ;
//				}
//
//			}
//		}) ;
		
		cordovaWebView = (CordovaWebView) findViewById(R.id.minecordovaWebView);
		
		String loadInfo = getIntent().getStringExtra("appendurl") ;
		if(loadInfo!=null && !"".equals(loadInfo)){
			super.initAndLoadUrl(cordovaWebView, "file:///android_asset/mine/index.html#"+loadInfo);
			
			Log.d("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%mine", "file:///android_asset/mine/index.html#"+loadInfo) ;
			
		}else{
			super.initAndLoadUrl(cordovaWebView, "file:///android_asset/mine/index.html");
		}
	    
	//	super.initAndLoadUrl(cordovaWebView, "file:///android_asset/mine/index.html") ;
	  
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
