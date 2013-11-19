package com.youdao.techmarket;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.youdao.techmarket.api.UserManager;
import com.youdao.techmarket.utils.CommUtils;
import com.youdao.techmarket.utils.LogUtil;

/**
 * 用户注册界面
 * @author fengxue
 *
 */
public class RegisterActivity extends BaseActivity {
	
	private ImageView cancel = null ;
	private ImageView register_iv = null ;
	private EditText et_register_username ; //用户名
	private EditText et_register_phonenum ; //手机号
	private EditText et_register_email ;    //邮箱
	private EditText et_register_userpass ; //密码
	//private EditText et_register_confiruserpass ;//确认密码
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_register) ;
		getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		
		initView() ;
		setOnclickListener() ;
	}
	/**
	 * 初始化组件
	 */
	private void initView(){
		et_register_username = (EditText) this.findViewById(R.id.et_register_username) ;
		et_register_phonenum = (EditText) this.findViewById(R.id.et_register_phonenum) ;
		et_register_email = (EditText) this.findViewById(R.id.et_register_email) ;
		et_register_userpass = (EditText) this.findViewById(R.id.et_register_userpass) ;
		//et_register_confiruserpass = (EditText) this.findViewById(R.id.et_register_confiruserpass) ;
		
		register_iv = (ImageView) this.findViewById(R.id.bt_register_register) ;
		cancel = (ImageView) this.findViewById(R.id.bt_register_cancel) ;
	}
	/**
	 * 添加添听事件 
	 */
	private void setOnclickListener(){
		register_iv.setOnClickListener(registerOnclickListener) ;
		cancel.setOnClickListener(cancelRegisterOnclickListener) ;
		
	}
	//注册的单击事件实现类
	private View.OnClickListener registerOnclickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			String username_content = et_register_username.getText().toString().trim() ;
			String phonenum_content = et_register_phonenum.getText().toString().trim() ;
			String email_content = et_register_email.getText().toString().trim() ;
			String userpass_content = et_register_userpass.getText().toString().trim() ;
			//String confirm_userpass = et_register_confiruserpass.getText().toString().trim() ;
			
			if(TextUtils.isEmpty(username_content) || TextUtils.isEmpty(phonenum_content) || TextUtils.isEmpty(userpass_content) || TextUtils.isEmpty(email_content)){
				CommUtils.showMessage("注册信息不能为空!", RegisterActivity.this) ;
				return ;
			}
//			if(!userpass_content.equals(confirm_userpass)){
//				CommUtils.showMessage("两次密码输入不一致!", RegisterActivity.this) ;
//				return ;
//			}
			UserManager.getInstance().register(RegisterActivity.this, username_content, userpass_content, email_content, phonenum_content, new JsonHttpResponseHandler(){

				@Override
				public void onStart() {
					super.onStart();
					CommUtils.startProgressDialog(RegisterActivity.this, "正在注册，请稍候!") ;
				}
				
				@Override
				public void onSuccess(JSONObject jsonObject) {
					super.onSuccess(jsonObject);
					LogUtil.d("***************************", jsonObject.toString()) ;
					if(jsonObject.optInt("code") == 0){
						Intent intent = new Intent(RegisterActivity.this,LoginActivity.class) ;
						startActivity(intent) ;
						finish() ;
					}else{
						CommUtils.showMessage(jsonObject.optString("message"), RegisterActivity.this) ;
					}
					CommUtils.stopProgressDialog() ;
					
				}

				@Override
				public void onFinish() {
					super.onFinish();
					
					CommUtils.stopProgressDialog() ;
				}

				@Override
				public void onFailure(Throwable arg0, String arg1) {
					super.onFailure(arg0, arg1);
					
					CommUtils.stopProgressDialog() ;
				}
				
			}) ;
			
		}
	};
	
	private View.OnClickListener cancelRegisterOnclickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
		
			RegisterActivity.this.finish() ;
			 
		}
	};
}
