package com.youdao.techmarket;


import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TabHost;

/**
 * 程序的主入口
 * @author fengxue
 *
 */

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity implements OnCheckedChangeListener{

	private TabHost tabHost = null ;
	private RadioButton home = null ;
	private RadioButton message = null ;
	private RadioButton friend = null ;
	private RadioButton more = null ;
	private RadioButton prokeyinoo = null ;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		tabHost = (TabHost) findViewById(R.id.tabhost) ;
//		tabHost.setup() ;
		tabHost = this.getTabHost() ;
		buildTabSpec();  
		initRadios() ;
	}

	// 设置选项卡标签和要跳转的Activity
	private void buildTabSpec() {

		Intent home_intent = new Intent(this, HomeActivity.class);
		Intent market_intent = new Intent(this, MarketActivity.class);
		Intent mine_intent = new Intent(this, MineActivity.class);
		Intent more_intent = new Intent(this, MoreActivity.class);
		Intent innovation_intent = new Intent(this, PocketInnovationActivity.class);

		tabHost.addTab(tabHost.newTabSpec("home").setIndicator("首页")
				.setContent(home_intent));
		tabHost.addTab(tabHost.newTabSpec("market").setIndicator("大市场")
				.setContent(market_intent));
		tabHost.addTab(tabHost.newTabSpec("innovation").setIndicator("掌上创新")
				.setContent(innovation_intent));
		tabHost.addTab(tabHost.newTabSpec("mine").setIndicator("我的")
				.setContent(mine_intent));
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
				this.tabHost.setCurrentTabByTag("mine");
				message.setChecked(false);
				home.setChecked(false);
				more.setChecked(false);
				prokeyinoo.setChecked(false);
				break;
			case R.id.radio_button_more: // 更多
				this.tabHost.setCurrentTabByTag("more");
				message.setChecked(false);
				friend.setChecked(false);
				home.setChecked(false);
				prokeyinoo.setChecked(false);
				break;
			}
		}
	}

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
