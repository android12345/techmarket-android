package com.youdao.techmarket;

import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.youdao.techmarket.api.UserManager;
import com.youdao.techmarket.utils.CommUtils;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
/**
 * 找回密码--验证通过修改密码(findPassword) activity
 * @author fengxue
 *
 */
public class ModifyPassActivity extends BaseActivity {
	
	private EditText newPassWord = null ;
	
	private String uname = null ;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_modifypass) ;
		
		uname = getIntent().getStringExtra("username") ;
		
		newPassWord = (EditText) this.findViewById(R.id.newpass) ;
		
		
		this.findViewById(R.id.submit).setOnClickListener(submitOnclickListener) ;
		
		
	}
	
	
	private View.OnClickListener submitOnclickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			String newpass_content = newPassWord.getText().toString().trim() ;
			if(TextUtils.isEmpty(newpass_content) || TextUtils.isEmpty(uname)){
				CommUtils.startProgressDialog(ModifyPassActivity.this, "新密码不能为空!") ;
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
					
//					Log.d("7777777777777777777777", result.success()+"") ;
				//	Log.d("7777777777777777777777", jsonObject.optJSONObject("root").optInt("code")+"") ;
					
					Log.d("***********************************************", jsonObject.toString()) ;
					
					
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
