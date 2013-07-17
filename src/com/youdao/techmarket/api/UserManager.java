package com.youdao.techmarket.api;


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
	
	
	
	
	public void login(){
		
	}
	
	public void register(){
		
	}
	public void findpassword(){
		
	}
	public void loginout(){
		
	}
}
