package com.youdao.techmarket.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXImageObject;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXTextObject;
import com.tencent.mm.sdk.openapi.WXWebpageObject;
import com.tencent.mm.sdk.platformtools.Util;
import com.umeng.socialize.bean.CustomPlatform;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeConfig;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.OnCustomPlatformClickListener;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMediaObject;
import com.youdao.techmarket.R;
import com.youdao.techmarket.domain.ShareInfo;

/**
 * @author junjun 分享的工具类，集成友盟的分享功能和自定义的平台（如：微信，朋友圈）
 */
public class ShareUtil {
	private IWXAPI api;
	private final int THUMB_SIZE = 150;
	private static final String TAG = "ShareUtil";
	private Context context;
	private UMSocialService controller;

	private int customPlatformCount = 0;

	private static final int TIMELINE_SUPPORTED_VERSION = 0x21020001;

	// private static String uMengID ;

	// public static void createUmeng(Activity context,String uMengID){
	// SocializeConstants.APPKEY = uMengID;
	// //ShareUtil.uMengID = uMengID ;
	// }
	/**
	 * 解决严格模式 采用异步
	 */
	public Handler UIHandler = new Handler(Looper.getMainLooper()) {
		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case 1:
				Bitmap bitmap = (Bitmap) msg.obj;
				controller.setShareImage(new UMImage(context, bitmap));
				break;

			default:
				break;
			}
		};
	};

	public ShareUtil() {

		controller = UMServiceFactory.getUMSocialService("Android",
				RequestType.SOCIAL);

		controller.registerListener(new SnsPostListener() {
			@Override
			public void onStart() {
				CommUtils.showMessage("开始分享", context);
			}

			@Override
			public void onComplete(SHARE_MEDIA arg0, int arg1,
					SocializeEntity arg2) {

				if (arg1 == 200) {
					CommUtils.showMessage("分享成功", context);
				} else {
					String eMsg = "";
					if (arg1 == -101)
						eMsg = "没有授权";
					CommUtils
							.showMessage("分享失败[" + arg1 + "] " + eMsg, context);
				}

			}
		});
	}

	/**
	 * 友盟的分享功能，支持微信
	 * 
	 * @param context
	 */
	public void share(final Context context, final ShareInfo shareInfo) {

		this.context = context;
		// 清理自定义平台的数据
//		clearCustomPlatforms();

		String wxAppID = context.getResources().getString(R.string.weixin_key);
		// 检查微信key以及是否安装了微信
		if (wxAppID != null && !wxAppID.equals("") && !wxAppID.equals("wxkey")) {

			api = WXAPIFactory.createWXAPI(context, wxAppID);
			Log.d(TAG, "share Weixin " + wxAppID);
			// 检查是否安装了微信
			if (checkInstallwx(context)) {
				// Log.d(TAG, "当前线程弹出："+Thread.currentThread() );
				SocializeConfig config = controller.getConfig(); // new
																	// SocializeConfig();

				CustomPlatform mWXPlatform = new CustomPlatform(context
						.getResources().getString(R.string.weixin),
						R.drawable.weixin_icon);

				addWxClickListener(context, mWXPlatform, shareInfo, false);
				config.addCustomPlatform(mWXPlatform); // 添加微信功能到友盟
				customPlatformCount++;

				// 检查是否可以分享的朋友圈
				if (checkSupportTimeline()) {
					CustomPlatform mWXCircle = new CustomPlatform(context
							.getResources().getString(R.string.friend),
							R.drawable.wxcircel);

					addWxClickListener(context, mWXCircle, shareInfo, true);
					config.addCustomPlatform(mWXCircle); // 添加朋友圈功能到友盟
					customPlatformCount++;
				}

				// 更新config
				controller.setConfig(config);
			}
		}

		// 预设置分享内容
		if (shareInfo.getShareText() != null) {
			controller.setShareContent(shareInfo.getShareText());
		}
 
		// 设置图片
		if (shareInfo.getShareImageUrl() != null) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					Bitmap bitmap = AsyncImageLoader.getBitmap(shareInfo
							.getShareImageUrl());
					UIHandler.sendMessage(Message.obtain(UIHandler, 1, bitmap));
				}
			}).start();

		}

		// if(ShareUtil.uMengID !=null){
		controller.openShare(context, false);
		// }
	}

	/**
	 * 清理自定义平台的数据方法
	 */
	public void clearCustomPlatforms() {
		if (controller != null) {
			SocializeConfig config = controller.getConfig();
			int total = config.getCustomPlatforms().size();
			for (int x = 1; x <= customPlatformCount; x++) {
				config.getCustomPlatforms().remove(total - x);
			}
			customPlatformCount = 0;
		}
	}

	/**
	 * 添加微信或朋友圈点击事件
	 * 
	 * @param context
	 * @param customPlatform
	 * @param shareInfo
	 * @param timeline
	 *            是微信还是朋友圈
	 */
	private void addWxClickListener(final Context context,
			CustomPlatform customPlatform, final ShareInfo shareInfo,
			final boolean timeline) {
		customPlatform.clickListener = new OnCustomPlatformClickListener() { // 微信分享按钮监听事件
			@Override
			public void onClick(CustomPlatform customPlatform,
					String shareContent, UMediaObject shareImage) {

				Log.d(TAG,
						shareInfo.getShareImageUrl() + "|"
								+ shareInfo.getShareText());

				wxShareTextAndImage(shareInfo, context.getResources()
						.getString(R.string.app_name), timeline);
				/*
				 * if (imageUrl != null) { // 如果图片不为空就分享图片　
				 * wxShareImageThread(imageUrl); } else if (text != null) {
				 * wxShareText(text); }
				 */
			}

		};
	}

	/**
	 * 微信分享图片的线程 解决严格模式 不能在主线程中运行的错误
	 * 
	 * @param imageUrl
	 */
	private void wxShareImageThread(final String imageUrl) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				wxShareImage(imageUrl);
			}
		}).start();
	}

	/**
	 * 分享内容和图片方法 包括微信和朋友圈
	 * 
	 * @param shareInfo
	 * @param appName
	 * @param timeline
	 *            是微信还是朋友圈
	 */
	private void wxShareTextAndImage(final ShareInfo shareInfo,
			final String appName, final boolean timeline) {
		Log.d(TAG, shareInfo.toString());
		Log.d(TAG, "timeline = " + timeline);
		WXWebpageObject webpage = new WXWebpageObject();
		if (shareInfo.getShareUrl() != null) {
			webpage.webpageUrl = shareInfo.getShareUrl();
		} else {
			webpage.webpageUrl = "http://www.xayuodao.com";
		}

		final WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = "来自于[" + appName + "]应用";
		msg.description = shareInfo.getShareText() == null ? "" : shareInfo
				.getShareText();

		if (shareInfo.getShareImageUrl() != null) {

			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Bitmap bmp;
						bmp = BitmapFactory.decodeStream(new URL(shareInfo
								.getShareImageUrl()).openStream());

						Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp,
								THUMB_SIZE, THUMB_SIZE, true);
						bmp.recycle();
						msg.thumbData = Util.bmpToByteArray(thumbBmp, true);
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					SendMessageToWX.Req req = new SendMessageToWX.Req();
					req.transaction = buildTransaction("webpage");
					req.message = msg;
					req.scene = timeline ? SendMessageToWX.Req.WXSceneTimeline
							: SendMessageToWX.Req.WXSceneSession;
					api.sendReq(req);
				}
			}).start();
		}

	}

	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis())
				: type + System.currentTimeMillis();
	}

	/**
	 * 检查是否安装了微信应用
	 * 
	 * @param context
	 */
	private boolean checkInstallwx(final Context context) {
		if (!api.isWXAppInstalled()) {
			CommUtils.showMessage("你还没有安装微信", context);
			return false;
		}
		return true;
	}

	/**
	 * 检查是否支持朋友圈
	 * 
	 * @return
	 */
	private boolean checkSupportTimeline() {
		int wxSdkVersion = api.getWXAppSupportAPI();
		return wxSdkVersion >= TIMELINE_SUPPORTED_VERSION;

	}

	/**
	 * 微信和朋友圈分享内容
	 * 
	 * @param text
	 */
	private void wxShareText(String text) {
		if (text == null || text.length() == 0) {
			return;
		}
		// 初始化一个WXTextObject对象
		WXTextObject textObj = new WXTextObject();
		textObj.text = text;

		// 用WXTextObject对象初始化一个WXMediaMessage对象
		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = textObj;
		// 发送文本类型的消息时，title字段不起作用
		// msg.title = "Will be ignored";
		msg.description = text;

		// 构造一个Req
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("text"); // transaction字段用于唯一标识一个请求
		req.message = msg;

		// 调用api接口发送数据到微信
		api.sendReq(req);
	}

	/**
	 * 微信和朋友圈分享图片
	 * 
	 * @param imageUrl
	 */
	private void wxShareImage(String imageUrl) {
		WXImageObject imgObj = new WXImageObject();
		imgObj.imageUrl = imageUrl;

		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = imgObj;

		Bitmap bmp;
		try {
			bmp = BitmapFactory.decodeStream(new URL(imageUrl).openStream());
			Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE,
					THUMB_SIZE, true);
			bmp.recycle();
			msg.thumbData = Util.bmpToByteArray(thumbBmp, true);

			SendMessageToWX.Req req = new SendMessageToWX.Req();
			req.transaction = buildTransaction("img");
			req.message = msg;
			api.sendReq(req);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
