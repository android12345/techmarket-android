package com.youdao.techmarket;

import org.apache.cordova.CordovaWebView;

import android.os.Bundle;

import com.youdao.techmarket.utils.LogUtil;

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
		cordovaWebView = (CordovaWebView) findViewById(R.id.hometutorialView);
	}

	@Override
	protected void onResume() {
		super.onResume();
		String loadInfo = getIntent().getStringExtra("appendurl") ;
		if(loadInfo!=null && !"".equals(loadInfo)){
			super.initAndLoadUrl(cordovaWebView, "file:///android_asset/home/index.html#"+loadInfo);
			
			LogUtil.d("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%home", "file:///android_asset/home/index.html#"+loadInfo) ;
			
		}else{
			
			super.initAndLoadUrl(cordovaWebView, "file:///android_asset/home/index.html");
		}

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
