package com.youdao.techmarket.widgets;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Display;
import android.widget.RelativeLayout;

/**
 * 自定义RelativeLayout 解决键盘住输入框的问题
 * @author fengxue
 *
 */
public class InputMethodRelativeLayout extends RelativeLayout {
	
	
	private int width;
	protected OnSizeChangedListenner onSizeChangedListenner;
	private boolean sizeChanged  = false;
	private int height;
	private int screenWidth;
	private int screenHeight;

	public InputMethodRelativeLayout(Context paramContext,
			AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		Display localDisplay = ((Activity) paramContext).getWindowManager().getDefaultDisplay();
		this.screenWidth = localDisplay.getWidth();
		this.screenHeight = localDisplay.getHeight();
	}

	public InputMethodRelativeLayout(Context paramContext,
			AttributeSet paramAttributeSet, int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		this.width = widthMeasureSpec;
		this.height = heightMeasureSpec;
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	@Override
	public void onSizeChanged(int w, int h, int oldw,
			int oldh) {
		if ((this.onSizeChangedListenner!= null) && (w == oldw) && (oldw != 0)
				&& (oldh != 0)) {
			if ((h >= oldh)
					|| (Math.abs(h - oldh) <= 1 * this.screenHeight / 4)) {
				if ((h <= oldh)
						|| (Math.abs(h - oldh) <= 1 * this.screenHeight / 4))
					return;
				this.sizeChanged  = false;
			} else {
				this.sizeChanged  = true;
			}
			this.onSizeChangedListenner.onSizeChange(this.sizeChanged ,oldh, h);
			measure(this.width - w + getWidth(), this.height
					- h + getHeight());
		}
	}
	/**
	 * 设置监听事件
	 * @param paramonSizeChangedListenner
	 */
	public void setOnSizeChangedListenner(
			InputMethodRelativeLayout.OnSizeChangedListenner paramonSizeChangedListenner) {
		this.onSizeChangedListenner = paramonSizeChangedListenner;
	}

	/**
	 * 内部接口
	 * @author fengxue
	 *
	 */
	public abstract interface OnSizeChangedListenner {
		public abstract void onSizeChange(boolean paramBoolean, int w,int h);
	}
}
