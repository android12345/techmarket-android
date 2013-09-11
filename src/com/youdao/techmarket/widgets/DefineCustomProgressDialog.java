package com.youdao.techmarket.widgets;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.youdao.techmarket.R;
/**
 * 自定义dialog
 * @author fengxue
 *
 */
public class DefineCustomProgressDialog extends Dialog {
	
	private Context context = null;
	private static DefineCustomProgressDialog customProgressDialog = null;

	public DefineCustomProgressDialog(Context context) {
		super(context);
		this.context = context;
	}
	public DefineCustomProgressDialog(Context context, int theme) {
        super(context, theme);
    }
	
	public static DefineCustomProgressDialog createDialog(Activity context){
		//实例化dialog并设将其样式设置为全屏的
		//customProgressDialog = new DefineCustomProgressDialog(context, android.R.style.Theme_Translucent_NoTitleBar);
		customProgressDialog = new DefineCustomProgressDialog(context,R.style.CustomProgressDialog);
		customProgressDialog.setContentView(R.layout.defineprogressdialog);

		return customProgressDialog ;
	}
 
    public void onWindowFocusChanged(boolean hasFocus){
    	
    	if (customProgressDialog == null){
    		return;
    	}
 
    }
    /**
     * 设置标题
     * @param strTitle
     * @return
     */
    public DefineCustomProgressDialog setTitile(String strTitle){
    	return customProgressDialog;
    }
    /**
     * 设置信息
     * @param strMessage
     * @return
     */
    public DefineCustomProgressDialog setMessage(String strMessage){
    	TextView tvMsg = (TextView)customProgressDialog.findViewById(R.id.id_tv_loadingmsg);
    	
    	if (tvMsg != null){
    		tvMsg.setText(strMessage);
    	}
    	
    	return customProgressDialog;
    }
}
