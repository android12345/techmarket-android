package com.youdao.techmarket;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.youdao.techmarket.api.UserManager;
import com.youdao.techmarket.utils.CommUtils;
import com.youdao.techmarket.utils.LogUtil;
/**
 * 找回密码--填写信息(getBackPassword)  activity
 * @author fengxue
 *
 */
public class GetBackPasswordActivity extends BaseActivity {
	
	private EditText username = null ;  //用户名
	
	private EditText phonenum = null ; // 手机号

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_getbackpassword) ;
		
		initview() ;
		setOnclickListener() ;
	}
	
	private void setOnclickListener() {
		this.findViewById(R.id.submit).setOnClickListener(submitOnClickListener) ;
		
	}
	//初始化组件
	private void initview() {
		this.username = (EditText) this.findViewById(R.id.username) ;
		this.phonenum = (EditText) this.findViewById(R.id.phonenmu) ;

	}
	//按钮的监听事件 
	private View.OnClickListener submitOnClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			final String uname = username.getText().toString().trim() ;
			String pnum = phonenum.getText().toString().trim() ;
			
			if(TextUtils.isEmpty(uname) || TextUtils.isEmpty(pnum)){
				
				CommUtils.showMessage("用户名或手机号不能为空!", GetBackPasswordActivity.this) ;
				return ;
			}
			UserManager.getInstance().findpasswordinfo(GetBackPasswordActivity.this, uname, pnum, new JsonHttpResponseHandler(){

				@Override
				public void onStart() {
					// TODO Auto-generated method stub
					super.onStart();
					CommUtils.startProgressDialog(GetBackPasswordActivity.this, "正在验证信息，请稍候!") ;
				}
				@Override
				public void onSuccess(JSONObject jsonObject) {
					super.onSuccess(jsonObject) ;

					int code = jsonObject.optInt("code") ;
					String message = jsonObject.optString("message") ;
					LogUtil.d("------------------------------------", jsonObject.toString()) ;
					if(0==code){
						CommUtils.showMessage(message, GetBackPasswordActivity.this) ;
						Intent intent = new Intent(GetBackPasswordActivity.this,SmsVerifyActivity.class) ;
						intent.putExtra("username", uname) ;
						startActivity(intent) ;
						finish() ;
					}
					else{
						
						CommUtils.showMessage(message, GetBackPasswordActivity.this) ;
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
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
}
