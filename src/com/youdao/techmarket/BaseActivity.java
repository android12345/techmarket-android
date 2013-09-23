package com.youdao.techmarket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.cordova.Config;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewClient;
import org.apache.cordova.IceCreamCordovaWebViewClient;
import org.apache.cordova.api.CordovaInterface;
import org.apache.cordova.api.CordovaPlugin;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;


import com.umeng.analytics.MobclickAgent;
import com.youdao.techmarket.utils.AppManager;
import com.youdao.techmarket.widgets.DefineCustomProgressDialog;

/**
 * 应用程序activity的管理类，用于管理activity应用程序退出 webview的加载以及监听 url变化
 * @author fengxue
 *
 */
@SuppressLint("NewApi")
public class BaseActivity extends Activity implements CordovaInterface{
	
	private final ExecutorService threadPool =Executors.newCachedThreadPool();
	
	private DefineCustomProgressDialog progressDialog = null;
	
	private CordovaWebViewClient cordovaWebViewClient ;

	@TargetApi(Build.VERSION_CODES.FROYO)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Config.init(this);

		//添加Activity到堆栈
		AppManager.getAppManager().addActivity(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		//结束Activity&从堆栈中移除
		AppManager.getAppManager().finishActivity(this);
	}

	@Override
	public Activity getActivity() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public ExecutorService getThreadPool() {
		// TODO Auto-generated method stub
		return threadPool;
	}

	@Override
	public Object onMessage(String arg0, Object arg1) {
		// TODO Auto-generated method stub
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
	
	/**
	 * 初始化并加载网页
	 * @param cordovaWebView phonegap包装过的webview
	 * @param url  要加载的地址
	 */
	public  void initAndLoadUrl(final CordovaWebView cordovaWebView,String url){
	    if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) { //3.0以前
	    	   cordovaWebViewClient = new CordovaWebViewClient(this, cordovaWebView) {  
	    		   //监听url的变化
	    		   @Override
	    		   public boolean shouldOverrideUrlLoading(WebView webView,String url) {
	    			   
	    			 //  onLoadUrlListener.onloadUrl(url) ;
	   
	    			//xayoudao://infomars:loadpageinfo/123456
	    			   //             xayoudao://market:showPolicy

	    			
					String infos[] = url.split(":");
					if ("xayoudao".equals(infos[0])) {
						String jumptab = infos[1];
						String info = infos[2];
						jumptab = jumptab.replace("//", "") ;
						Log.d("***************************************************************", jumptab) ;
						Intent intent = new Intent(BaseActivity.this,MainActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						intent.putExtra("currenttab", jumptab);
						intent.putExtra("appendurl", info);
						startActivity(intent);
					}

	    			 
	    			   return true;
	    		   }
		           @Override  
		           public void onPageStarted(WebView view, String url, Bitmap favicon) {  
		              super.onPageStarted(view, url, favicon);  
		              startProgressDialog() ;
		               //ommUtils.visibleProgressBar(getActivity(),"正在加载，请稍后...") ;
		           }  
		  
		           @Override  
		           public void onPageFinished(WebView view, String url) {  
		               super.onPageFinished(view, url);  
			              stopProgressDialog() ;
		               //CommUtils.removeProgressBar(getActivity()) ;
		           }  
		  
		           @Override  
		           public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {  
		              // LogUtil.debug("onReceivedError " + url);  
		               super.onReceivedError(view, errorCode, description, failingUrl);  
		              // progressBar.setVisibility(View.GONE);  
		              // errorView.setVisibility(View.VISIBLE);  
		               stopProgressDialog() ;
		               //cordovaWebView.loadUrl("file:///android_asset/www/index.html");  
		              // CommUtils.removeProgressBar(getActivity()) ;
		           }  
		           @Override
		        public void onLoadResource(WebView view, String url) {
		        	// TODO Auto-generated method stub
		        	super.onLoadResource(view, url);
		        }
		       }; 
			} else {
				cordovaWebViewClient = new IceCreamCordovaWebViewClient(this, cordovaWebView) {
					//监听url的变化
					@Override
					public boolean shouldOverrideUrlLoading(WebView webView,String url) {
		    			   
						 //  onLoadUrlListener.onloadUrl(url) ;
						//Log.d("***************************************************************", url) ;
					String infos[] = url.split(":");
					if ("xayoudao".equals(infos[0])) {
						String jumptab = infos[1];
						String info = infos[2];
						jumptab = jumptab.replace("//", "") ;
						//jumptab = jumptab.substring(1, jumptab.length()-1) ;
						Log.d("***************************************************************", jumptab) ;
						Intent intent = new Intent(BaseActivity.this,MainActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						intent.putExtra("currenttab", jumptab);
						intent.putExtra("appendurl", info);
						startActivity(intent);
					}
		    			   
		    			return true ;
		    		}
					
					@Override
					public void onPageStarted(WebView view, String url,Bitmap favicon) {
						super.onPageStarted(view, url, favicon);
						startProgressDialog() ;
						 //CommUtils.visibleProgressBar(getActivity(),"正在加载，请稍后...") ;
					}
					@Override
					public void onPageFinished(WebView view, String url) {
						super.onPageFinished(view, url);
						  stopProgressDialog() ;
						//CommUtils.removeProgressBar(getActivity()) ;
					}
					@Override
					public void onReceivedError(WebView view, int errorCode,
							String description, String failingUrl) {
						super.onReceivedError(view, errorCode, description, failingUrl);
						// cordovaWebView.loadUrl("file:///android_asset/www/index.html");  
						stopProgressDialog() ;
						//CommUtils.removeProgressBar(getActivity()) ;
					}
				};
			}
//	    cordovaWebView.getSettings().setJavaScriptEnabled(true); 
//	    cordovaWebView.getSettings().setLoadWithOverviewMode(true);
	    cordovaWebView.setWebViewClient(cordovaWebViewClient);  
	    
	    //没有网时调用 错误页面
	   	ConnectivityManager connectMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo mobNetInfo = connectMgr
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo wifiNetInfo = connectMgr
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {//没有网络加载错误页面，否则加载远程地址
			// unconnect network
			// mNetWorkState = NetworkUtils.NETWORN_NONE;
			cordovaWebView.loadUrl("file:///android_asset/www/error.html"); 
			
		}else{
			cordovaWebView.loadUrl(url);   
		}
	       
	       
	}
	/**
	 * 显示loading
	 */
	private void startProgressDialog(){
		if (progressDialog == null || !progressDialog.isShowing()){
			progressDialog = DefineCustomProgressDialog.createDialog(this);
	    	progressDialog.setMessage("正在加载，请稍后...");
		}
    	progressDialog.show();
	}
	
	/**
	 * 关闭loading
	 */
	private void stopProgressDialog(){
		if (progressDialog != null){
			progressDialog.dismiss();
			progressDialog = null;
		}
	}
	
	
	/**
	 * 添加统计
	 */
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	/**
	 * 添加统计
	 */
	@Override
	protected void onPause() {
		super.onPause();
		 MobclickAgent.onPause(this);
	}
	
//	/**
//	 * 监听url变化接口
//	 * @author fengxue
//	 *
//	 */
//	public interface OnLoadUrlListener{
//		public void onloadUrl(String url) ;
//	}
//
//	private OnLoadUrlListener onLoadUrlListener ;
//	
//	public void setOnLoadUrlListener(OnLoadUrlListener onLoadUrlListener){
//		this.onLoadUrlListener = onLoadUrlListener ;
//	}
	
	
	
}
