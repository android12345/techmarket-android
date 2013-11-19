package com.youdao.techmarket;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.youdao.techmarket.api.UserManager;
import com.youdao.techmarket.utils.CommUtils;
import com.youdao.techmarket.utils.LogUtil;
/**
 * 找回密码--验证通过修改密码(findPassword) activity
 * @author fengxue
 *
 */
public class ModifyPassActivity extends BaseActivity {
	
	private EditText newPassWord = null ;
	
	private String uname = null ;

	private TextView sms_modify_tv ; //这里短信验证和修改新密码用的是同一个布局 只是字不一样和提交按钮不一样而已。
	
	private ImageView submit_button = null ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_smsverify) ;
		
		uname = getIntent().getStringExtra("username") ;
		
		sms_modify_tv = (TextView)this.findViewById(R.id.sms_modify_tv) ;
		
		sms_modify_tv.setText("设置新密码") ;
		
		newPassWord = (EditText) this.findViewById(R.id.verfiycode) ;
		
		newPassWord.setHint("请输入新密码") ;
		
		
		submit_button = (ImageView) this.findViewById(R.id.submit) ;
		
		submit_button.setBackgroundResource(R.drawable.modifypass_submitbutton_selector) ; //设置按钮的样式为提交
		
		submit_button.setOnClickListener(submitOnclickListener) ;
		
		
	}
	
	
	private View.OnClickListener submitOnclickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			String newpass_content = newPassWord.getText().toString().trim() ;
			if(TextUtils.isEmpty(newpass_content) || TextUtils.isEmpty(uname)){
				CommUtils.showMessage("新密码不能为空!", ModifyPassActivity.this) ;
				return  ;
			}
			
			UserManager.getInstance().verifysuccessModifyPass(ModifyPassActivity.this, uname, newpass_content, new JsonHttpResponseHandler(){
				@Override
				public void onStart() {
					// TODO Auto-generated method stub
					super.onStart();
					CommUtils.startProgressDialog(ModifyPassActivity.this, "正在修改密码，请稍候!") ;
				}
				@Override
				public void onSuccess(JSONObject jsonObject) {
					super.onSuccess(jsonObject) ;

					LogUtil.d("***********************************************", jsonObject.toString()) ;
					
					int code = jsonObject.optInt("code") ;
					String message = jsonObject.optString("message") ;
					if(0==code){
						Intent intent = new Intent(ModifyPassActivity.this,LoginActivity.class) ;
						startActivity(intent) ;
						finish() ;
					}
					else{
						
						CommUtils.showMessage(message, ModifyPassActivity.this) ;
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
