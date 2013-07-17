package com.youdao.techmarket;

import android.app.Activity;
import android.os.Bundle;

import com.youdao.techmarket.utils.AppManager;


/**
 * 应用程序activity的管理类，用于管理activity应用程序退出
 * @author fengxue
 *
 */
public class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//添加Activity到堆栈
		AppManager.getAppManager().addActivity(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		//结束Activity&从堆栈中移除
		AppManager.getAppManager().finishActivity(this);
	}

}
