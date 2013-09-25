package com.youdao.techmarket;

import org.apache.cordova.CordovaWebView;

import android.os.Bundle;
import android.util.Log;

/**
 * 首页
 * @author junjun
 *
 */
public class HomeActivity extends BaseActivity  {
	
	private CordovaWebView cordovaWebView = null ;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.home_activity) ;
//		mMainFrameTask  = new MainFrameTask(this);
//		mMainFrameTask.execute();
		cordovaWebView = (CordovaWebView) findViewById(R.id.hometutorialView);
		
		
//		this.setOnLoadUrlListener(new OnLoadUrlListener() {
//			
//			@Override
//			public void onloadUrl(String url) {
//				Toast.makeText(HomeActivity.this, "123:"+url, 0).show() ;
//				Log.d("++++++++++++++++++++++++++++++home", url) ;
//				
//				//initAndLoadUrl(cordovaWebView, "file:///android_asset/innovation/index.html") ;
//				//tabHost.setCurrentTabByTag("innovation") ;
//			//	xayoudao://infomars:loadpageinfo/123456
//				
//				String infos[] = url.split(":") ;
//				if("xayoudao".equals(infos[0])){
//					String jumptab = infos[1] ;
//					String info = infos[2] ;
//					Intent intent = new Intent(HomeActivity.this,MainActivity.class) ;
//					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//					intent.putExtra("currenttab", jumptab) ;
//					intent.putExtra("appendurl", info) ;
//					startActivity(intent) ;
//				}
//
//			}
//		}) ;
		
		
		String loadInfo = getIntent().getStringExtra("appendurl") ;
		if(loadInfo!=null && !"".equals(loadInfo)){
			super.initAndLoadUrl(cordovaWebView, "file:///android_asset/home/index.html#"+loadInfo);
			
			Log.d("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%home", "file:///android_asset/home/index.html#"+loadInfo) ;
			
		}else{
			
		//	Toast.makeText(HomeActivity.this, "调用了",0).show() ;
			super.initAndLoadUrl(cordovaWebView, "file:///android_asset/home/index.html");
		}
		
		
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		//cordovaWebView.loadUrl("file:///android_asset/home/index.html") ;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		
	}
//	public class MainFrameTask extends AsyncTask<Integer, String, Integer>{
//		private FragmentHome mainFrame = null;
//		
//		public MainFrameTask(FragmentHome mainFrame){
//			this.mainFrame = mainFrame;
//		}
//		
//		@Override
//		protected void onCancelled() {
//			stopProgressDialog();
//			super.onCancelled();
//		}
//
//		@Override
//		protected Integer doInBackground(Integer... params) {
//			
//			try {
//				//Thread.sleep(10 * 1000);
//				cordovaWebView.loadUrl(Config.getStartUrl());  
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			return null;
//		}
//			
//		@Override
//		protected void onPreExecute() {
//			startProgressDialog();
//			
//		}
//
//		@Override
//		protected void onPostExecute(Integer result) {
//			stopProgressDialog();
//		}
//			
//		
//		
//	}
	
	
	
	
//	  到时候把这个抽象到父类中，解决部分手机不能浏览的问题＊＊＊＊＊
//	
//    /**
//     * Create and initialize web container with default web view objects.
//     */
//    public void init() {
//        CordovaWebView webView = new CordovaWebView(DroidGap.this);
//        CordovaWebViewClient webViewClient;
//        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB)
//        {
//            webViewClient = new CordovaWebViewClient(this, webView);
//        }
//        else
//        {
//            webViewClient = new IceCreamCordovaWebViewClient(this, webView);
//        }
//        this.init(webView, webViewClient, new CordovaChromeClient(this, webView));
//    }
}
