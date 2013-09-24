package com.youdao.techmarket;

import org.apache.cordova.CordovaWebView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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
		
//		this.setOnLoadUrlListener(new OnLoadUrlListener() {
//			
//			@Override
//			public void onloadUrl(String url) {
//				Toast.makeText(MarketActivity.this, "123:"+url, 0).show() ;
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
//					Intent intent = new Intent(MarketActivity.this,MainActivity.class) ;
//					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//					intent.putExtra("currenttab", jumptab) ;
//					intent.putExtra("appendurl", info) ;
//					startActivity(intent) ;
//				}
//
//			}
//		}) ;
		

		cordovaWebView = (CordovaWebView) findViewById(R.id.marketcordovaWebView);
		String loadInfo = getIntent().getStringExtra("appendurl") ;
		
	//	Toast.makeText(MarketActivity.this, "调用了:"+loadInfo, 0).show() ;
		Log.d("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%market", "file:///android_asset/home/index.html#"+loadInfo) ;
		if(loadInfo!=null && !"".equals(loadInfo)){
			super.initAndLoadUrl(cordovaWebView, "file:///android_asset/market/index.html#"+loadInfo);
			
		//	Log.d("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%market", "file:///android_asset/home/index.html#"+loadInfo) ;
			
		}else{
			super.initAndLoadUrl(cordovaWebView, "file:///android_asset/market/index.html");
		}
		
		
//		super.initAndLoadUrl(cordovaWebView,"file:///android_asset/market/index.html");
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
