package com.youdao.techmarket.domain;

import org.json.JSONException;
import org.json.JSONObject;

public class MessageResult {
	private int code = -1;
	private String message;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String messagestr) {
		this.message = messagestr;
	}


	@Override
	public String toString() {
		return "MessageResult [code=" + code + ", messagestr=" + message + "]";
	}

	/*
	 * 判断是否访问成功
	 */
	public boolean success() {
		return code == 0;

	}
	/**
	 * @param data
	 * @return
	 * /**
	 * {"root":{"userExpandInfo":{},"data":{"token":"VEtWUEFWXkxFSkNaWhYUFRcJFB0JFRU=","userType":"0"},"code":0,"userInfo":{},"success":true}}
	 * 
	 * jsonObject.optJSONObject("root").optInt("code")
	 */
	 
	public static MessageResult prase(JSONObject jsonObject) {
		MessageResult messageResult = null;

		try {
			messageResult = new MessageResult();
	
			if (jsonObject == null) {
				return messageResult;
			}

			JSONObject json = jsonObject.optJSONObject("root") ;
			
			if (!json.isNull("code")) {
				messageResult.setCode(json.optInt("code"));
			}

			if (!json.isNull("message")) {
				
				messageResult.setMessage(json.getString("message"));
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return messageResult;

	}
}
