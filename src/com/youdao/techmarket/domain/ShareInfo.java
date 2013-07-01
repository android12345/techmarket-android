package com.youdao.techmarket.domain;

import org.json.JSONArray;

/**
 * @author junjun 分享的数据bean
 */
public class ShareInfo {

	private String shareText; // 分享的文字
	private String ShareImageUrl; // 图片的URl地址
	private String ShareUrl;

	public String getShareText() {
		return shareText;
	}

	public void setShareText(String shareText) {
		this.shareText = shareText;
	}

	public String getShareImageUrl() {
		return ShareImageUrl;
	}

	public void setShareImageUrl(String shareImageUrl) {
		ShareImageUrl = shareImageUrl;
	}

	public String getShareUrl() {
		return ShareUrl;
	}

	public void setShareUrl(String shareUrl) {
		ShareUrl = shareUrl;
	}

	/**
	 * 把js端传递进来的数据解析到 ShareInfo中
	 * 
	 * @param array
	 * @return
	 */
	public static ShareInfo parse(JSONArray array) {
		ShareInfo shareInfo = null;
		try {
			shareInfo = new ShareInfo();
			shareInfo.shareText = array.optString(0);
			shareInfo.ShareImageUrl = array.optString(1);
			shareInfo.ShareUrl = array.optString(2);
			return shareInfo;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String toString() {
		return shareText + "|" + ShareImageUrl + "|" + ShareUrl;
	}
}
