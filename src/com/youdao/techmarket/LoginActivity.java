package com.youdao.techmarket;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.youdao.techmarket.api.UserManager;
import com.youdao.techmarket.domain.MessageResult;
import com.youdao.techmarket.domain.User;
import com.youdao.techmarket.utils.CommUtils;
import com.youdao.techmarket.widgets.InputMethodRelativeLayout;
import com.youdao.techmarket.widgets.InputMethodRelativeLayout.OnSizeChangedListenner;

/**
 * 登录界面
 * @author fengxue
 *
 */
@SuppressWarnings("unused")
public class LoginActivity extends BaseActivity implements OnSizeChangedListenner{
	
	private LinearLayout showPass = null ;
	private LinearLayout findpass_ll ;
	
	private TextView show_pass_tv ; 
	
	private EditText username = null ;
	private EditText userpass = null ;
	private String pointpass = null ;
	
	
	private ImageView register = null ;
	private ImageView login = null ;

	private boolean mbDisplayFlg = false; // 定义显示或隐藏密码的标志位
	
	private InputMethodRelativeLayout layout; 
	private LinearLayout llLogo ;
	
	private YouDaoApplication application ;
	
	private SharedPreferences preferences = null ;
	
	private Context context ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_login) ;
		getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		preferences = this.getSharedPreferences("push_user", MODE_PRIVATE) ;
		initView() ;
		setOnclickListener() ;
		
	}
	
	public LoginActivity(Context context) {
		
		 this.context = context ;
	}
	
	public LoginActivity() {
		// TODO Auto-generated constructor stub
	}
	private void initView(){
		
		application = (YouDaoApplication) this.getApplication() ;
		
		showPass = (LinearLayout) this.findViewById(R.id.showpass_ll) ;
		findpass_ll = (LinearLayout) this.findViewById(R.id.findpass_ll) ;
		
		show_pass_tv = (TextView) this.findViewById(R.id.show_pass_tv) ;
		layout = (InputMethodRelativeLayout) this.findViewById(R.id.loginpage) ;
		llLogo = (LinearLayout) this.findViewById(R.id.linearLayout) ;
		username = (EditText) this.findViewById(R.id.et_login_username) ;
		userpass = (EditText) this.findViewById(R.id.et_login_userpass) ;
		register = (ImageView) this.findViewById(R.id.iv_login_register) ;
		login = (ImageView) this.findViewById(R.id.iv_login_login) ;
		
	}
	
	private void setOnclickListener(){
		showPass.setOnClickListener(showPassClickListener) ;
		layout.setOnSizeChangedListenner(this) ;
		register.setOnClickListener(registerOnclickListener) ;
		login.setOnClickListener(loginOnClickListener) ;
		
		//findpass_ll.setOnClickListener(findpassOnClickListener) ;
		
	}
	private  View.OnClickListener showPassClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			pointpass = userpass.getText().toString().trim(); //取得密码
			if(!TextUtils.isEmpty(pointpass)){ //如果密码不为空才能显示和隐藏密码
				showOrHideFlg(); // 调用显示、隐藏密码的方法
			}
		}
	};
	//找回密码监听事件
	
	private View.OnClickListener findpassOnClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(LoginActivity.this,FindPasswordActivity.class) ;
			startActivity(intent) ;
			
		}
	};
	//显示密码监听事件
	private  View.OnClickListener registerOnclickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(LoginActivity.this,RegisterActivity.class) ;
			startActivity(intent) ;
			CommUtils.hiddenSoft(LoginActivity.this) ;
		}
	};
	/**
	 * {"root":{"userExpandInfo":{},"data":{"token":"VEtWUEFWXkxFSkNaWhYUFRcJFB0JFRU=","userType":"0"},"code":0,"userInfo":{},"success":true}}
	 * 09-11 11:20:32.838: D/7777777777777777777777(6983): {"userExpandInfo":{},"data":{"token":"TEVMRVpaFhQVFwkUHQkVFQ==","userType":"0"},"code":0,"userInfo":{},"success":true}

	 */
	private View.OnClickListener loginOnClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			String username_content = username.getText().toString().trim() ;
			String userpass_content = userpass.getText().toString().trim() ;
			
			if(TextUtils.isEmpty(userpass_content) || TextUtils.isEmpty(username_content)){
				CommUtils.showMessage("用户名或密码不能为空!", LoginActivity.this) ;
				return ;
			}
			
			String userid = preferences.getString("user_id", null) ;
			
			//Toast.makeText(LoginActivity.this, userid, 0).show() ;
			
			UserManager.getInstance().login(LoginActivity.this, username_content,userpass_content,userid, new JsonHttpResponseHandler(){
				
				@Override
				public void onStart() {
					// TODO Auto-generated method stub
					super.onStart();
					CommUtils.startProgressDialog(LoginActivity.this, "正在登录，请稍候!") ;
				}
				@Override
				public void onSuccess(JSONObject jsonObject) {
					super.onSuccess(jsonObject) ;
					
					MessageResult result = MessageResult.prase(jsonObject) ;
//					Log.d("7777777777777777777777", result.success()+"") ;
				//	Log.d("7777777777777777777777", jsonObject.optJSONObject("root").optInt("code")+"") ;
					
					Log.d("7777777777777777777777", result.success()+"") ;
					if(result.success()){
						Log.d("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!", jsonObject.toString()) ;
						User user = User.parser(jsonObject) ;
						if(user!=null){
							application.setUser(user) ;
//							Intent intent = new Intent(LoginActivity.this,MainActivity.class) ;
//							startActivity(intent) ;
							Log.d("666666666666666666666666666666666", loginSuccess+"") ;
							if(loginSuccess!=null){
								loginSuccess.loginsuccess() ;
								
							}
//							if(cancelListner!=null){
//								cancelListner.cancelLogin(true) ;
//							}
							finish();
						}
					}else{
						CommUtils.showMessage(result.getMessage(), LoginActivity.this) ;
					}
					CommUtils.stopProgressDialog();
				}
				
				@Override
				public void onFinish() {
					// TODO Auto-generated method stub
					super.onFinish();
					CommUtils.stopProgressDialog();
				}
				@Override
				public void onFailure(Throwable arg0, String arg1) {
					// TODO Auto-generated method stub
					super.onFailure(arg0, arg1);
					CommUtils.stopProgressDialog() ;
				}
				
			}) ;
			
		}
	};

	/**
	 * 显示密码隐藏密码的方法
	 */
	private void showOrHideFlg() {
		if (!mbDisplayFlg) {
			// 显示密码，如："123456"
			userpass.setTransformationMethod(HideReturnsTransformationMethod
					.getInstance());
			show_pass_tv.setText("隐藏密码") ;
			userpass.setSelection(pointpass.length()) ; //光标定位到密码长度处
		} else {
			// 隐藏密码，如: "."
			userpass.setTransformationMethod(PasswordTransformationMethod
					.getInstance());
			show_pass_tv.setText("显示密码") ;
			userpass.setSelection(pointpass.length()) ;//光标定位到密码长度处
		}
		mbDisplayFlg = !mbDisplayFlg;
		userpass.postInvalidate();

	}

	@Override
	public void onSizeChange(boolean paramBoolean, int w, int h) {
		if(paramBoolean){  
            layout.setPadding(0, -80, 0, 0);  //键盘弹出时向上挤
            llLogo.setVisibility(View.INVISIBLE) ;
        }else{  
            layout.setPadding(0, 0, 0, 0); //键盘消失时还原位置
            llLogo.setVisibility(View.VISIBLE) ;
        }
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(loginSuccess!=null){
			loginSuccess = null ;
		}
//		if(cancelListner!=null){
//			cancelListner = null ;
//		}
		
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	
	private static LoginSuccess loginSuccess ;

	public static void setLoginSuccessListener(LoginSuccess loginSuccess1) {
		loginSuccess = loginSuccess1;
	}



	public interface LoginSuccess{
		public void loginsuccess() ;
		
	}
	
//	static CancelListner cancelListner ;
//	public static void setCancelListener(CancelListner cancelListner1){
//		cancelListner = cancelListner1 ;
//	}
//	
//	public interface CancelListner{
//		public void cancelLogin(boolean flag) ;
//	}

	
	
//	@Override
//	public void onBackPressed() {
//		// TODO Auto-generated method stub
//		super.onBackPressed();
//		cancelListner.cancelLogin(false) ;
//	}
}
