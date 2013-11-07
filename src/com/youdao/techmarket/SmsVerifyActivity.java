package com.youdao.techmarket;

import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.youdao.techmarket.api.UserManager;
import com.youdao.techmarket.domain.MessageResult;
import com.youdao.techmarket.utils.CommUtils;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Program.TextureType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
/**
 *找回密码--验证短信验证码(active) activity
 * @author fengxue
 *
 */
public class SmsVerifyActivity extends BaseActivity {
	
	
	private EditText verfiycode = null ;
	
	private String username = null ;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_smsverify) ;
		
		verfiycode = (EditText) this.findViewById(R.id.verfiycode) ;
		
		this.findViewById(R.id.submit).setOnClickListener(onSubmitOnClickListener) ;
		
		username = getIntent().getStringExtra("username") ;
		
		
	}
	
	
	private View.OnClickListener onSubmitOnClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			String verfiycode_content = verfiycode.getText().toString().trim() ;
			if(TextUtils.isEmpty(verfiycode_content) || TextUtils.isEmpty(username)){
				CommUtils.startProgressDialog(SmsVerifyActivity.this, "验证码不能为空!") ;
				return  ;
			}
			UserManager.getInstance().smsverify(SmsVerifyActivity.this, username, verfiycode_content, new JsonHttpResponseHandler(){
				
				@Override
				public void onStart() {
					// TODO Auto-generated method stub
					super.onStart();
					CommUtils.startProgressDialog(SmsVerifyActivity.this, "正在验证信息，请稍候!") ;
				}
				@Override
				public void onSuccess(JSONObject jsonObject) {
					super.onSuccess(jsonObject) ;
					
//					Log.d("7777777777777777777777", result.success()+"") ;
				//	Log.d("7777777777777777777777", jsonObject.optJSONObject("root").optInt("code")+"") ;
					
					Log.d("+++++++++++++++++++++++++++++++++++++++++++++++", jsonObject.toString()) ;
					
					
					int code = jsonObject.optInt("code") ;
					String message = jsonObject.optString("message") ;
					if(0==code){
					
						Intent intent = new Intent(SmsVerifyActivity.this,ModifyPassActivity.class) ;
						intent.putExtra("username", username) ;
						startActivity(intent) ;
						finish() ;
					}
					else{
						
						CommUtils.showMessage(message, SmsVerifyActivity.this) ;
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
