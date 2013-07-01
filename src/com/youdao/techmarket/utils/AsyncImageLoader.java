package com.youdao.techmarket.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 图片异步加载工具类
 * 
 * @version V1.0
 */
public class AsyncImageLoader {

	private static final String TAG = "AsyncImageLoader";
	private static HashMap<String, Bitmap> imageMap = new HashMap<String, Bitmap>(); // 存放图片列表：key-图片路径；value-Bitmap
	private static  byte[] getImage(String path) throws Exception{
		URL url = new URL(path) ;
		HttpURLConnection connection = (HttpURLConnection) url.openConnection() ;
		//设置连接超时
		connection.setConnectTimeout(5000) ;
		//设置请求方式为GET
		connection.setRequestMethod("GET") ;
		//Log.d(TAG, "connection.getResponseCode() is " + connection.getResponseCode())  ;
		if(connection.getResponseCode()==200){//如果返回的响应码为200，证明请求成功
			//到得客户端的输入流
			InputStream is = connection.getInputStream() ;
			//准备一个字节数组输出流
			ByteArrayOutputStream bos = new ByteArrayOutputStream() ;
			//定义一个缓冲字节数组
			byte buffer[] = new byte[is.available()] ;
			int len = 0 ;
			while((len = is.read(buffer))!=-1){//将读到的数据写到bos流中
				bos.write(buffer,0,len) ;
			}
			is.close() ;
			bos.close() ;
			// 将bos转化成字节数组并返回
			byte data[] = bos.toByteArray() ;
			return data ;
		}
		return null ;
	}
	/**
	 * 根据图片地址同步获取图片  
	 * @param path  图片的url地址
	 * @return   返回bitmap图片
	 */
	public static Bitmap getBitmap(String path){
		
		byte data[];
		try {
			
			Bitmap image = imageMap.get(path); // 从缓存中获取数据
	        if (image == null) {

	        	data = getImage(path);
	       // LOG.d(TAG, "@@@@@@@@@@@@@@@@::::"+data) ;
	       // 	image = BitmapFactory.decodeByteArray(data, 0, data.length) ;

				// 先算出图片的高宽
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inJustDecodeBounds = true;
				BitmapFactory.decodeByteArray(data, 0, data.length,
						options);
				// 缩放到最高1000像素
				int be = 1;
				if (options.outHeight > options.outWidth) {
					be = (int) (options.outHeight / (float) 1000);
				} else {
					be = (int) (options.outWidth / (float) 1000);
				}
				if (be <= 0)
					be = 1;
				options.inSampleSize = be;
				options.inJustDecodeBounds = false;
				image = BitmapFactory.decodeByteArray(data, 0,
						data.length, options);
				 imageMap.put(path, image);
				 data = null ;
	            
	        }
	        return image ;
		} catch (Exception e) {
			e.printStackTrace();
			return null ;
		}
	}
}
