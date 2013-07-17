package com.youdao.techmarket.widgets;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.youdao.techmarket.R;
/**
 * 自定义dialog
 * @author fengxue
 *
 */
public class CustomProgressDialog extends Dialog {
	
	private Context context = null;
	private static CustomProgressDialog customProgressDialog = null;

	public CustomProgressDialog(Context context) {
		super(context);
		this.context = context;
	}
	public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
    }
	
	public static CustomProgressDialog createDialog(Activity context){
		//实例化dialog并设将其样式设置为全屏的
		customProgressDialog = new CustomProgressDialog(context, android.R.style.Theme_Translucent_NoTitleBar);
		//customProgressDialog = new CustomProgressDialog(context,R.style.CustomProgressDialog);
		customProgressDialog.setContentView(R.layout.customprogressdialog);

		return customProgressDialog ;
	}
 
    public void onWindowFocusChanged(boolean hasFocus){
    	
    	if (customProgressDialog == null){
    		return;
    	}
    	
        ImageView imageView = (ImageView) customProgressDialog.findViewById(R.id.loadingImageView);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();
    }
    /**
     * 设置标题
     * @param strTitle
     * @return
     */
    public CustomProgressDialog setTitile(String strTitle){
    	return customProgressDialog;
    }
    /**
     * 设置信息
     * @param strMessage
     * @return
     */
    public CustomProgressDialog setMessage(String strMessage){
    	TextView tvMsg = (TextView)customProgressDialog.findViewById(R.id.id_tv_loadingmsg);
    	
    	if (tvMsg != null){
    		tvMsg.setText(strMessage);
    	}
    	
    	return customProgressDialog;
    }
}
