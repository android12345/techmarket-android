package com.youdao.techmarket;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
/**
 * 
 * @author junjun
 *	引导界面  用代码实现，代替xml布局      给那个布局上面布局，就用那个布局作为布局pararm
 */
public class Guide implements OnTouchListener  {

//	private static final int TO_THE_END = 0;// 到达最后一张
//	private static final int LEAVE_FROM_END = 1;// 离开最后一张
	
			
	private List<View> guides = new ArrayList<View>();  //存放引导图片的集合
	private ViewPager pager;
	
	private int offset;// 位移量
	private int curPos = 0;// 记录当前的位置
	private RelativeLayout view = null ;  //根布局
	
	private FrameLayout frameLayout = null ;

	private ImageView dot7 = null ;
	private ImageView imgopen = null ;
	
	private Dialog guideDialog  ;
	
	
	private int lastX = 0;
	
	public Guide( Context context,final List<Integer> numguide,techmarket devActivity,final Dialog guideDialog) {
		this.guideDialog = guideDialog ;
		initUI(context, numguide);

		for (int i = 0; i < numguide.size(); i++) {
			ImageView iv = new ImageView(context);
			iv.setImageResource(numguide.get(i)) ;
			ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT);
			iv.setLayoutParams(params);
			iv.setScaleType(ScaleType.FIT_XY);
			guides.add(iv);
		}

		imgopen.setOnClickListener(devActivity) ;
		
		dot7.getViewTreeObserver().addOnPreDrawListener(
				new OnPreDrawListener() {
					public boolean onPreDraw() {
						offset = dot7.getWidth();
						return true;
					}
				});	
		
		GuidePagerAdapter adapter = new GuidePagerAdapter(guides);
		pager.setAdapter(adapter);
		pager.setOnTouchListener(this);
		pager.setOnPageChangeListener(new OnPageChangeListener() {
			public void onPageSelected(int arg0) {
				moveCursorTo(arg0);
//				if (arg0 == numguide.size()-1) {// 到最后一张了
//					handler.sendEmptyMessageDelayed(TO_THE_END, 500);
//				} else if (curPos == numguide.size()-1) {
//					handler.sendEmptyMessageDelayed(LEAVE_FROM_END, 100);
//				}
				if(arg0 == numguide.size()-1){
//					new Handler().postDelayed(new Runnable() {
//						
//						@Override
//						public void run() {
//							dismissDialog() ;
//						}
//					}, 2000) ;
				//	setLoginMainAnmi() ;
				}
				curPos = arg0;
			}

			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			public void onPageScrollStateChanged(int arg0) {
			}
			
		});
	}
	private void dismissDialog(){
		if (guideDialog != null && guideDialog.isShowing()) {
			guideDialog.dismiss();
			guideDialog = null;
		}
	}
	/**
	 * 用代码生成布局
	 * @param context
	 * @param guide_icon
	 */
	private void initUI(Context context, final List<Integer> guideImageResID) {
		//添加pager到根布局
		RelativeLayout.LayoutParams rel = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		view = new RelativeLayout(context)  ;
		view.setLayoutParams(rel) ;
		pager = new ViewPager(context) ;
		pager.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT)) ;
		view.addView(pager) ;
		
		//添加framelayout到根布局
		RelativeLayout.LayoutParams rel1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		frameLayout = new FrameLayout(context) ;
		rel1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE) ;
		rel1.addRule(RelativeLayout.CENTER_HORIZONTAL) ;
		view.addView(frameLayout,rel1) ;
		
	
		//linearlayout在framelayout上面布局，所以这里布局参数就要用FrameLayout的
		FrameLayout.LayoutParams lp4 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
	    LinearLayout linearLayout = new LinearLayout(context); 
	    linearLayout.setOrientation(LinearLayout.HORIZONTAL); 
	    lp4.bottomMargin = px2dip(context,22);
	    frameLayout.addView(linearLayout,lp4)  ;
		
		//添加图片中的圆点到Linearlayout上
		LinearLayout.LayoutParams ll1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		ll1.weight = 1 ;
		if(guideImageResID.size()>0){
		for(int i=0;i<guideImageResID.size();i++){
				ImageView dot1 = new ImageView(context) ;
				dot1.setLayoutParams(ll1) ;
				dot1.setImageResource(R.drawable.dot1_w) ;
				linearLayout.addView(dot1) ;
			}
		}
		
		FrameLayout.LayoutParams dot7pararm = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		dot7 = new ImageView(context) ;
		dot7.setImageResource(R.drawable.dot2_w) ;
		frameLayout.addView(dot7,dot7pararm) ;
		
		
		RelativeLayout.LayoutParams open = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		open.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE) ;
		open.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE) ;
		imgopen = new ImageView(context) ;
		imgopen.setLayoutParams(open) ;
		imgopen.setImageResource(R.drawable.ic_open) ;
	//	imgopen.setVisibility(View.GONE) ;
		view.addView(imgopen) ;
	}

	/**
	 * 移动指针到相邻的位置
	 * 
	 * @param position
	 * 		  		指针的索引值
	 * */
	private void moveCursorTo(int position) {
		TranslateAnimation anim = new TranslateAnimation(offset*curPos, offset*position, 0, 0);
		anim.setDuration(300);
		anim.setFillAfter(true);
		dot7.startAnimation(anim);
	}

//	Handler handler = new Handler() {
//		@Override
//		public void handleMessage(Message msg) {
//			if (msg.what == TO_THE_END)
//				imgopen.setVisibility(View.VISIBLE);
//			else if (msg.what == LEAVE_FROM_END)
//				imgopen.setVisibility(View.GONE);
//		}
//	};
	
//	private View.OnClickListener myConClickListener = new View.OnClickListener() {
//		
//		public void onClick(View v) {
//			view.setVisibility(View.GONE) ;
//			view.destroyDrawingCache(); 
//			view= null ;
//		}
//	};
	/**
	 * 取得引导布局
	 * @return
	 */
	public View getView(){
		return view ;
	}
	/**
	 *  根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 * @param context
	 * @param dpValue
	 * @return
	 */
	private int dip2px(Context context, float dpValue) { 
	    final float scale = context.getResources().getDisplayMetrics().density; 
	    return (int) (dpValue * scale + 0.5f); 
	}
	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 * @param context
	 * @param pxValue
	 * @return
	 */
    private int px2dip(Context context, float pxValue) { 
        final float scale = context.getResources().getDisplayMetrics().density; 
        return (int) (pxValue / scale + 0.5f); 
    } 
//    //滑动到最后一张加动画后进入主界面
//    private void setLoginMainAnmi(){
//    	AlphaAnimation alphaAnimation = new AlphaAnimation( 1, (float)0.1);   
//        alphaAnimation.setDuration(3000);//设定动画时间   
//        alphaAnimation.setAnimationListener(new AnimationListener() {   
//            @Override  
//            public void onAnimationStart(Animation animation) {   
//            }   
//  
//            @Override  
//            public void onAnimationRepeat(Animation animation) {   
//            }   
//  
//            @Override  
//            public void onAnimationEnd(Animation animation) {   
//            	dismissDialog() ;  
//            }   
//        });  
//      guides.get(guides.size()-1).setAnimation(alphaAnimation);   
//    }
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lastX = (int)event.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			if((lastX - event.getX()) >100 && (curPos == guides.size() -1)){
				dismissDialog() ; 
			}
			break;
		default:
			break;
		}
		return false;
	}
}
