package com.youdao.techmarket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.cordova.Config;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewClient;
import org.apache.cordova.IceCreamCordovaWebViewClient;
import org.apache.cordova.api.CordovaInterface;
import org.apache.cordova.api.CordovaPlugin;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;

import com.youdao.techmarket.widgets.CustomProgressDialog;

/**
 * 首页
 * @author junjun
 *
 */
public class HomeActivity extends BaseActivity implements CordovaInterface {
	
	private final ExecutorService threadPool =Executors.newCachedThreadPool();
	private CordovaWebView cordovaWebView = null ;
	
	private CustomProgressDialog progressDialog = null;
	//private MainFrameTask mMainFrameTask = null;
	
	private CordovaWebViewClient cordovaWebViewClient ;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.home_activity) ;
//		mMainFrameTask  = new MainFrameTask(this);
//		mMainFrameTask.execute();
		cordovaWebView = (CordovaWebView) findViewById(R.id.tutorialView);
	       Config.init(this);
	       
	       if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
	    	   cordovaWebViewClient = new CordovaWebViewClient(this, cordovaWebView) {  
		           @Override  
		           public void onPageStarted(WebView view, String url, Bitmap favicon) {  
		  
		               super.onPageStarted(view, url, favicon);  
		              startProgressDialog() ;
		               
		           }  
		  
		           @Override  
		           public void onPageFinished(WebView view, String url) {  
		               super.onPageFinished(view, url);  
			               stopProgressDialog() ;
		           }  
		  
		           @Override  
		           public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {  
		              // LogUtil.debug("onReceivedError " + url);  
		               super.onReceivedError(view, errorCode, description, failingUrl);  
		              // progressBar.setVisibility(View.GONE);  
		              // errorView.setVisibility(View.VISIBLE);  
		               stopProgressDialog() ;
		           }  
		       }; 
			} else {
				cordovaWebViewClient = new IceCreamCordovaWebViewClient(this, cordovaWebView) {
					@Override
					public void onPageStarted(WebView view, String url,
							Bitmap favicon) {
						super.onPageStarted(view, url, favicon);
						startProgressDialog() ;
					}
					@Override
					public void onPageFinished(WebView arg0, String arg1) {
						super.onPageFinished(arg0, arg1);
						   stopProgressDialog() ;
					}
					@Override
					public void onReceivedError(WebView arg0, int arg1,
							String arg2, String arg3) {
						super.onReceivedError(arg0, arg1, arg2, arg3);
						stopProgressDialog() ;
					}
				};
			}
		
	       cordovaWebView.setWebViewClient(cordovaWebViewClient);  
	       cordovaWebView.loadUrl("file:///android_asset/qj/index.html");  
	       
	}

	@Override
	public Activity getActivity() {
		return this;
	}

	@Override
	public ExecutorService getThreadPool() {
		return threadPool;
	}

	@Override
	public Object onMessage(String arg0, Object arg1) {
		//Toast.makeText(this, arg1.toString(), 0).show() ;
		return null;
	}

	@Override
	public void setActivityResultCallback(CordovaPlugin arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startActivityForResult(CordovaPlugin arg0, Intent arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	
	private void startProgressDialog(){
		if (progressDialog == null || !progressDialog.isShowing()){
			progressDialog = CustomProgressDialog.createDialog(this);
	    	progressDialog.setMessage("正在加载中...");
		}
		
    	progressDialog.show();
	}
	
	private void stopProgressDialog(){
		if (progressDialog != null){
			progressDialog.dismiss();
			progressDialog = null;
		}
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
