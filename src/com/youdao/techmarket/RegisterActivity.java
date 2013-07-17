package com.youdao.techmarket;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.youdao.techmarket.utils.CommUtils;

/**
 * 用户注册界面
 * @author fengxue
 *
 */
public class RegisterActivity extends BaseActivity {
	
	private ImageView cancel = null ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_register) ;
		
		
		initView() ;
		setOnclickListener() ;
	}
	/**
	 * 初始化组件
	 */
	private void initView(){
		cancel = (ImageView) this.findViewById(R.id.bt_register_cancel) ;
	}
	/**
	 * 添加添听事件 
	 */
	private void setOnclickListener(){
		cancel.setOnClickListener(cancelRegisterOnclickListener) ;
	}
	
	private View.OnClickListener cancelRegisterOnclickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			//取消軟鍵盘
			CommUtils.hiddenSoft(RegisterActivity.this) ;
			RegisterActivity.this.finish() ;
			 
		}
	};
}
