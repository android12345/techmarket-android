package com.youdao.techmarket;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.youdao.techmarket.utils.CommUtils;


/**
 * 登录界面
 * @author fengxue
 *
 */
public class LoginActivity extends BaseActivity {
	
	private ImageView showPass = null ;
	
	private EditText username = null ;
	private EditText userpass = null ;
	private String pointpass = null ;
	
	private ImageView register = null ;
	private ImageView login = null ;

	private boolean mbDisplayFlg = false; // 定义显示或隐藏密码的标志位
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_login) ;
		
		initView() ;
		setOnclickListener() ;
	}
	
	private void initView(){
	//	showPass = (ImageView) this.findViewById(R.id.lv_showpassword) ;
		username = (EditText) this.findViewById(R.id.et_login_username) ;
		userpass = (EditText) this.findViewById(R.id.et_login_userpass) ;
		register = (ImageView) this.findViewById(R.id.iv_login_register) ;
		login = (ImageView) this.findViewById(R.id.iv_login_login) ;
		
	}
	
	private void setOnclickListener(){
		//showPass.setOnClickListener(showPassClickListener) ;
		register.setOnClickListener(registerOnclickListener) ;
		login.setOnClickListener(loginOnClickListener) ;
		
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
	
	private  View.OnClickListener registerOnclickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(LoginActivity.this,RegisterActivity.class) ;
			startActivity(intent) ;
			CommUtils.hiddenSoft(LoginActivity.this) ;
		}
	};
	private View.OnClickListener loginOnClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			
			
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
			//ImageView.setText("隐藏密码") ;
			userpass.setSelection(pointpass.length()) ; //光标定位到密码长度处
		} else {
			// 隐藏密码，如: "."
			userpass.setTransformationMethod(PasswordTransformationMethod
					.getInstance());
			//ImageView.setText("显示密码") ;
			userpass.setSelection(pointpass.length()) ;//光标定位到密码长度处
		}
		mbDisplayFlg = !mbDisplayFlg;
		userpass.postInvalidate();

	}
}
