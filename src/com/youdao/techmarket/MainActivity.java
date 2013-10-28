package com.youdao.techmarket;


import org.apache.cordova.CordovaWebView;

import android.app.Activity;
import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TabHost;

import com.umeng.analytics.MobclickAgent;
import com.youdao.techmarket.LoginActivity.LoginSuccess;
import com.youdao.techmarket.utils.AppManager;
import com.youdao.techmarket.utils.CommUtils;
import com.youdao.techmarket.utils.NetWorkUtils;

/**
 * 程序的主入口
 * @author fengxue
 * 
 */

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity implements OnCheckedChangeListener,LoginSuccess{

	public TabHost tabHost = null ;
	private RadioButton home = null ;
	private RadioButton message = null ;
	private RadioButton friend = null ;
	private RadioButton more = null ;
	private RadioButton prokeyinoo = null ;
	private boolean isExit; 
	
	private BroadcastReceiver connectionReceiver;
	
	private YouDaoApplication application ;
	
//	private LoginActivity activity = null ;

	public MainActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		tabHost = (TabHost) findViewById(R.id.tabhost) ;
//		tabHost.setup() ;
		application = (YouDaoApplication) this.getApplication() ;
		
		addNetWorkReceiver() ;

		tabHost = this.getTabHost() ;
		initRadios() ;
		buildTabSpec();  
	
	}
	private Intent market_intent ,mine_intent,more_intent,innovation_intent,home_intent;
	// 设置选项卡标签和要跳转的Activity
	private void buildTabSpec() {
		
		Intent intent = getIntent() ;
//		//接收推送过来要跳转到的tab
//		String whichtab = intent.getStringExtra("whitchtab") ;
		
		
		//接收应用内部点击按钮要跳罢的tab和地址  包括接收推送过来要跳转到的tab和要加载的页面地址
		String currenttab = intent.getStringExtra("currenttab") ;
		String appendUrl = intent.getStringExtra("appendurl") ;
		
		
	
			
		
			 home_intent = new Intent(this, HomeActivity.class);
		
		
//		if("home".equals(whichtab)){ //处理推送过跳转过来接收信息
//			String loadinfo = intent.getStringExtra("loadinfo") ;
//			home_intent.putExtra("loadinfo",loadinfo) ;
//		}

			 
			 
		market_intent = new Intent(this, MarketActivity.class);
		
		 mine_intent = new Intent(this, MineActivity.class);
		 more_intent = new Intent(this, MoreActivity.class);
		 innovation_intent = new Intent(this, PocketInnovationActivity.class);
		
		
		
		
		if("home".equals(currenttab)){
			tabHost.addTab(tabHost.newTabSpec("home").setIndicator("首页")
					.setContent(home_intent));
	//		this.tabHost.setCurrentTabByTag("home");
			
			home_intent.putExtra("appendurl",appendUrl) ;
			home.setChecked(true);
//			Toast.makeText(MainActivity.this, "haha", 0).show();
		}
		else{
			tabHost.addTab(tabHost.newTabSpec("home").setIndicator("首页")
					.setContent(home_intent));
		}
			
		
		
		
		if("market".equals(currenttab)){
			tabHost.addTab(tabHost.newTabSpec("market").setIndicator("大市场")
					.setContent(market_intent));
		//	this.tabHost.setCurrentTabByTag("market");
			
			market_intent.putExtra("appendurl",appendUrl) ;
			
	//	Toast.makeText(MainActivity.this, "get value:"+appendUrl, 0).show() ;
		//	this.tabHost.setCurrentTabByTag("market");
			message.setChecked(true);
		}else{
			
			tabHost.addTab(tabHost.newTabSpec("market").setIndicator("大市场")
					.setContent(market_intent));
		}
		
		if("innovation".equals(currenttab)){
			tabHost.addTab(tabHost.newTabSpec("innovation").setIndicator("掌上创新")
					.setContent(innovation_intent));
		//	this.tabHost.setCurrentTabByTag("innovation");
			
			innovation_intent.putExtra("appendurl",appendUrl) ;
			
			prokeyinoo.setChecked(true);
//			Toast.makeText(MainActivity.this, "haha", 0).show();
		}
		tabHost.addTab(tabHost.newTabSpec("innovation").setIndicator("掌上创新")
				.setContent(innovation_intent));
		
		
		
		if("mine".equals(currenttab)){
			tabHost.addTab(tabHost.newTabSpec("mine").setIndicator("我的")
					.setContent(mine_intent));
			//this.tabHost.setCurrentTabByTag("mine");
			
			mine_intent.putExtra("appendurl",appendUrl) ;
			
			friend.setChecked(true);
//			Toast.makeText(MainActivity.this, "haha", 0).show();
		}else{
			tabHost.addTab(tabHost.newTabSpec("mine").setIndicator("我的")
					.setContent(mine_intent));
		}
		
		
		tabHost.addTab(tabHost.newTabSpec("more").setIndicator("更多")
				.setContent(more_intent));
		
		
	}

	// 初始化RadioButton并添加监听事件 
	private void initRadios() {
		home = ((RadioButton) findViewById(R.id.radio_button_home));
		message = ((RadioButton) findViewById(R.id.radio_button_message));
		friend = ((RadioButton) findViewById(R.id.radio_button_friend));
		more = ((RadioButton) findViewById(R.id.radio_button_more));
		prokeyinoo = (RadioButton) findViewById(R.id.radio_button_proketinoo);

		home.setOnCheckedChangeListener(this);
		message.setOnCheckedChangeListener(this);
		friend.setOnCheckedChangeListener(this);
		more.setOnCheckedChangeListener(this);
		prokeyinoo.setOnCheckedChangeListener(this);
	}
	private boolean flag = false ;
	/**
	 * 当选项卡改变的时候调用的方法
	 */
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			
			switch (buttonView.getId()) { // RadtioButton代替了原始的选项卡，那么原始的选项功能也就失去了，这里让RadionButton具有选项功能。
			case R.id.radio_button_home: // 首页
				this.tabHost.setCurrentTabByTag("home");
				message.setChecked(false);
				friend.setChecked(false);
				more.setChecked(false);
				prokeyinoo.setChecked(false);
				break;
			case R.id.radio_button_message: // 大市场
				//market_intent.putExtra("appendurl","") ;
				this.tabHost.setCurrentTabByTag("market");
				home.setChecked(false);
				friend.setChecked(false);
				more.setChecked(false);
				prokeyinoo.setChecked(false);
				break;
			case R.id.radio_button_proketinoo:
				this.tabHost.setCurrentTabByTag("innovation");
				message.setChecked(false);
				friend.setChecked(false);
				home.setChecked(false);
				more.setChecked(false);

				break;
			case R.id.radio_button_friend: // 我的
				if(application.getUser()!=null){  //如果登录了，就显示内容
					this.tabHost.setCurrentTabByTag("mine");
					message.setChecked(false);
					home.setChecked(false);
					more.setChecked(false);
					prokeyinoo.setChecked(false);
				}else{//如果没有登录，就让去登录
					LoginActivity.setLoginSuccessListener(MainActivity.this) ;
					Intent intent = new Intent(MainActivity.this,LoginActivity.class) ;
					startActivity(intent) ;
					flag = true ;
					friend.setChecked(false);
					
//					message.setChecked(false);
//					friend.setChecked(false);
//					more.setChecked(false);
//					prokeyinoo.setChecked(false);
//					new Handler().postDelayed(new Runnable() {
//						
//						@Override
//						public void run() {
//							// TODO Auto-generated method stub
//							tabHost.setCurrentTabByTag("home");
//							home.setChecked(true);
//							
//						}
//					}, 500) ;
					
					
				}
				break;
			case R.id.radio_button_more: // 更多
				this.tabHost.setCurrentTabByTag("more");
				LoginActivity.setLoginSuccessListener(MainActivity.this) ;
				message.setChecked(false);
				friend.setChecked(false);
				home.setChecked(false);
				prokeyinoo.setChecked(false);
				break;
			}
		}
	}
	  /**
	    * 在tab当前页调用CordovaWebView的返回，如果没有返回则退出整个系綂
	    */
	  public void webViewGoBack(){

			if (tabHost.getCurrentTabTag().equals("home")) {
				currentWebViewGoBack("home",R.id.hometutorialView);
			} else if (tabHost.getCurrentTabTag().equals("market")) {
				currentWebViewGoBack("market",R.id.marketcordovaWebView);
			} else if (tabHost.getCurrentTabTag().equals("innovation")) {
				currentWebViewGoBack("innovation",R.id.inoocordovaWebView);
			} else if (tabHost.getCurrentTabTag().equals("mine")) {
				currentWebViewGoBack("mine",R.id.minecordovaWebView);
			}else if(tabHost.getCurrentTabTag().equals("more")){
				currentWebViewGoBack("more",R.id.morecordovaWebView);
			}
			else{//如果当前页是更多
				exit() ;
			}

		}
	/**
	 * 取得当前的Tab中所对应的CordovaWebView并设置返回
	 * @param str tabid
	 * @param id   CordovaWebView的资源ID
	 */
	private void currentWebViewGoBack(String str,int id) {
		Activity activity=getLocalActivityManager().getActivity(str);
		CordovaWebView currentCordovaWebView = (CordovaWebView) activity.findViewById(id) ;
		goBack(currentCordovaWebView);
	}
	  
	//返回功能
		public void goBack(CordovaWebView webView) {
			if (webView.canGoBack()) {
				webView.goBack();
			} else {
				 exit(); 
			}
		}
	private void exit() {
			 if (!isExit) {  
		            isExit = true;  
		            CommUtils.showMessage("再按一次返回键返回到桌面", getApplicationContext()) ;
		            mHandler.sendEmptyMessageDelayed(0, 2000);  
		        } else {  
//		            Intent intent = new Intent(Intent.ACTION_MAIN);  
//		            intent.addCategory(Intent.CATEGORY_HOME);  
//		            startActivity(intent);  
		            AppManager.getAppManager().AppExit(MainActivity.this);  
		        }  
			
		}
	  private Handler mHandler = new Handler() {  
	       
	       @Override  
	       public void handleMessage(Message msg) {  
	           
	           super.handleMessage(msg);  
	           isExit = false;  
	       }  
	 
	   };  
	

	/**
	 * 在tabactivity中重写onKeyDown方法，取得反回键不起作用，重写dispatchKeyEvent就可以了
	 */
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		
	/**
	  注意在if判断中要加一个event.getAction()
       KeyEvent.ACTION_DOWN判断，因为按键有两个事件ACTION_DOWN和ACTION_UP，
       也就是按下和松开，如果不加这个判断，代码会执行两遍，而在下面的代码中就是弹两次AlertDialog
	*/
		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			
				//webViewGoBack() ;
			exit() ;
	            return false; 
			}
		
		return super.dispatchKeyEvent(event);
	}
	
	/**
	 * 取得网络类型并注册广播
	 */
	private void addNetWorkReceiver() {
		// 注册一个广播监听器
		connectionReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				ConnectivityManager connectMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
				NetworkInfo mobNetInfo = connectMgr
						.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
				NetworkInfo wifiNetInfo = connectMgr
						.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

				if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
					//如果没有 网络 弹出设置网络对话框
					NetWorkUtils.setNetWorkDialog(MainActivity.this);
				}

				
			}

		};
		//注册广播
		setRegisterReceiver(connectionReceiver);
	}
	
	/**
	 * 注册网络检测广播
	 * 
	 * @param receiver
	 */
	private void setRegisterReceiver(BroadcastReceiver receiver) {
		this.connectionReceiver = receiver;
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(connectionReceiver, intentFilter);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(connectionReceiver);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	public void loginsuccess() {
		// TODO Auto-generated method stub
			
					friend.setChecked(true) ;
				
				
		
		
		/*else if(tabHost.getCurrentTabTag().equals("more")){
			Activity activity=getLocalActivityManager().getActivity("more");
			CordovaWebView currentCordovaWebView = (CordovaWebView) activity.findViewById(R.id.morecordovaWebView) ;
			currentCordovaWebView.loadUrl("file:///android_asset/more/index.html") ;
			Toast.makeText(MainActivity.this, "asdfa", 0).show() ;
		//	more.setChecked(true) ;
		}*/
		
	//	Toast.makeText(MainActivity.this, "made diao le mei ", 0).show() ;
	}

//	@Override
//	public void cancelLogin(boolean flag) {
//		// TODO Auto-generated method stub
//		if(flag){
//			Toast.makeText(MainActivity.this, "调用了", 0).show() ;
//			Activity activity=getLocalActivityManager().getActivity("more");
//			CordovaWebView currentCordovaWebView = (CordovaWebView) activity.findViewById(R.id.morecordovaWebView) ;
//			currentCordovaWebView.reload() ;
//		}
//	}

	
	
//		private boolean isExit; 
//	    @Override  
//	    public boolean onKeyDown(int keyCode, KeyEvent event) {  
//	        if (keyCode == KeyEvent.KEYCODE_BACK) {  
//	            exit();  
//	            return false;  
//	        } else {  
//	            return super.onKeyDown(keyCode, event);  
//	        }  
//	    }
//		private void exit() {
//			 if (!isExit) {  
//		            isExit = true;  
//		            CommUtils.showMessage("再按一次退出程序", getApplicationContext()) ;
//		            mHandler.sendEmptyMessageDelayed(0, 2000);  
//		        } else {  
//		            Intent intent = new Intent(Intent.ACTION_MAIN);  
//		            intent.addCategory(Intent.CATEGORY_HOME);  
//		            startActivity(intent);  
//		            System.exit(0);  
//		        }  
//			
//		}  
//	    Handler mHandler = new Handler() {  
//	        
//	        @Override  
//	        public void handleMessage(Message msg) {  
//	            // TODO Auto-generated method stub  
//	            super.handleMessage(msg);  
//	            isExit = false;  
//	        }  
//	  
//	    };  

}
