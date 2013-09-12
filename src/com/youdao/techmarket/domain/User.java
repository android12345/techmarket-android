package com.youdao.techmarket.domain;

import java.io.Serializable;

import org.json.JSONObject;

/**
 * 管理用户的业务bean
 * @author fengxue   实现Serializable接口，方便对象的传递
 *{"root":{"userExpandInfo":{},"data":{"token":"TEVMRVpaFhQVFwkUHQkVFQ==","userType":"0"},"code":0,"userInfo":{},"success":true}} //登录成功返回的数据。
 *
  {"root":{"message":"用户名或密码错误","success":true,"code":10}} //登录失败返回的娄据
 */  
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String token ;
	private String userType ;
	

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Override
	public String toString() {
		return "User [token=" + token + ", userType=" + userType + "]";
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
	public static User parser(JSONObject jsonObject) {
		
		User user = new User() ;
//		if(jsonObject.optJSONObject("root").isNull("data")){
//			
//		}
		JSONObject dataJsonObject = jsonObject.optJSONObject("root").optJSONObject("data") ;
		user.setToken(dataJsonObject.optString("token")) ;
		user.setUserType(dataJsonObject.optString("userType")) ;
 		return user ;
		
	}

}
