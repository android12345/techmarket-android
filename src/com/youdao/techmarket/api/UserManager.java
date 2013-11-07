package com.youdao.techmarket.api;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.youdao.techmarket.utils.HttpClientUtils;
import com.youdao.techmarket.utils.ServicesHolder;

/**
 * 用户管理的相关api
 * @author fengxue
 *
 */
public class UserManager {
	//采用单例模式（懒汉式） 减少对象的创建  三步可创建一个单例

	//1.静态对象
	private static UserManager instance;
	//2.构造方法私有化，防止外部实例化
	private UserManager(){} ;
	/**
	 * 3.静态工厂方法返回对像的实例
	 * @return
	 */
	public static UserManager getInstance() {
		if (instance == null)
			instance = new UserManager();
		return instance;
	}

	
	/**
	 * 用户登录的api
	 * @param context
	 * @param userCode 用户名
	 * @param userPwds 用户密码
	 * @param handler
	 */
	public void login(Context context,String  userCode,String userPwd,String udid,AsyncHttpResponseHandler handler){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userCode", userCode) ;
		params.put("userPwd", userPwd) ;
		params.put("udid", udid) ;
		
		HttpClientUtils.post(ServicesHolder.api(ServicesHolder.LOGIN), params, context, handler) ;
		
	}
	/**
	 * 用户注册的api
	 * @param context
	 * @param userCode 用户名
	 * @param userPwd  用户密码
	 * @param email    邮箱
	 * @param mobile   手机号
	 * @param handler
	 */
	public void register(Context context,String userCode,String userPwd,String email,String mobile,AsyncHttpResponseHandler handler){
		
		Map<String, Object> params = new HashMap<String, Object>() ;
		params.put("userCode",userCode ) ;
		params.put("userPwd",userPwd ) ;
		params.put("email",email ) ;
		params.put("mobile",mobile ) ;
		
		HttpClientUtils.post(ServicesHolder.api(ServicesHolder.REGISTER), params, context, handler) ;
		
	}
	/**
	 * 找回密码--填写信息(getBackPassword)
	 * @param context   上下文 
	 * @param userCode  登录用户名
	 * @param contact 手机号
	 * @param handler
	 */
	public void findpasswordinfo(Context context,String userCode,String contact,AsyncHttpResponseHandler handler){
		Map<String, Object> params = new HashMap<String, Object>() ;
		params.put("userCode",userCode ) ;
		params.put("contact",contact ) ;
		
		HttpClientUtils.post(ServicesHolder.api(ServicesHolder.FIND_PASS_INFO), params, context, handler) ;
	}
	
	/**
	 * 找回密码--验证短信验证码(active)
	 * @param context   上下文 
	 * @param userCode  登录用户名
	 * @param authCode   手机验证码
	 * @param handler
	 */
	public void smsverify(Context context,String userCode ,String authCode,AsyncHttpResponseHandler handler){
		
		Map<String, Object> params = new HashMap<String, Object>() ;
		params.put("userCode",userCode ) ;
		params.put("authCode",authCode ) ;
		
		HttpClientUtils.post(ServicesHolder.api(ServicesHolder.SMS_VERIFY), params, context, handler) ;
		
	}
	
	/**
	 * 找回密码--验证通过修改密码(findPassword)
	 * @param context  上下文 
	 * @param userCode  用户名
	 * @param newUserPwd 新密码
	 * @param handler
	 */
	public void verifysuccessModifyPass(Context context,String userCode ,String newUserPwd,AsyncHttpResponseHandler handler){
		Map<String, Object> params = new HashMap<String, Object>() ;
		params.put("userCode",userCode ) ;
		params.put("newUserPwd",newUserPwd ) ;
		
		HttpClientUtils.post(ServicesHolder.api(ServicesHolder.VERIFY_SUCCESS_FINDPASS), params, context, handler) ;
	}
	
}
