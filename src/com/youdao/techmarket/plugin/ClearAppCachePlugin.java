package com.youdao.techmarket.plugin;

import java.io.File;

import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import android.os.Handler;

import com.youdao.techmarket.utils.CommUtils;
/**
 * 清理手机缓存插件
 * @author fengxue
 *
 */
public class ClearAppCachePlugin extends CordovaPlugin {
	
	public static final String CLEARCACHE = "clearcache" ;

	@Override
	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {
		
		
		final File cache = cordova.getActivity().getCacheDir();
		
		if(CLEARCACHE.equals(action)){
			
			CommUtils.startProgressDialog(cordova.getActivity(), "正在清理请稍候!") ;
			
			
			
			new Handler().postDelayed(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					clearCacheFolder(new File( cache.getParent() + "/app_database"));
				}
			}, 3000) ;
			
			
			return true ;
		}
		
		return false ;
	}

	/**
	 * 删除手机应用的缓存文件
	 * @param dir
	 */
	private void clearCacheFolder(File file) {

        
		    if (file!= null && file.isDirectory()) {             
		        try {                
		            for (File child:file.listFiles()) {    
		                if (child.isDirectory()) {              
		                	clearCacheFolder(child);      
		                }    
		                 child.delete() ;   
		            }   
		            CommUtils.stopProgressDialog() ;
		        } catch(Exception e) {       
		            e.printStackTrace();    
		            CommUtils.stopProgressDialog() ;
		        }     
		    }   
		
	}


}
