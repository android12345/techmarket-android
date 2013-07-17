package com.youdao.techmarket.utils;

import java.io.IOException;

import org.apache.cordova.api.LOG;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.res.XmlResourceParser;



public class MyConfig {
	
	private String indexUrl = null ;
	private String messageUrl = null ;
	private String friendUrl = null ;
	private String moreUrl = null ;
	
	
	

	public String getIndexUrl() {
		return indexUrl;
	}

	public void setIndexUrl(String indexUrl) {
		this.indexUrl = indexUrl;
	}

	public String getMessageUrl() {
		return messageUrl;
	}

	public void setMessageUrl(String messageUrl) {
		this.messageUrl = messageUrl;
	}

	public String getFriendUrl() {
		return friendUrl;
	}

	public void setFriendUrl(String friendUrl) {
		this.friendUrl = friendUrl;
	}

	public String getMoreUrl() {
		return moreUrl;
	}

	public void setMoreUrl(String moreUrl) {
		this.moreUrl = moreUrl;
	}

	private static MyConfig myConfig = null;
	public static MyConfig getInstance(Activity activity){
		if(myConfig == null){
			return new MyConfig(activity) ;
		}
		return myConfig ;
	}
	
	private MyConfig(Activity activity) {
		
		 if (activity == null) {
	            LOG.i("CordovaLog", "There is no activity. Is this on the lock screen?");
	            return;
	        }

	        int id = activity.getResources().getIdentifier("config", "xml", activity.getPackageName());
	        if (id == 0) {
	            id = activity.getResources().getIdentifier("cordova", "xml", activity.getPackageName());
	            LOG.i("CordovaLog", "config.xml missing, reverting to cordova.xml");
	        }
	        if (id == 0) {
	            LOG.i("CordovaLog", "cordova.xml missing. Ignoring...");
	            return;
	        }
	        
	        
	        XmlResourceParser xml = activity.getResources().getXml(id);
	        int eventType = -1;
	        while (eventType != XmlResourceParser.END_DOCUMENT) {
	            if (eventType == XmlResourceParser.START_TAG) {
	                String strNode = xml.getName();

	                if (strNode.equals("index")) {
	                    String origin = xml.getAttributeValue(null, "messageurl");
	                    String subdomains = xml.getAttributeValue(null, "subdomains");
	                    if (origin != null) {
	                       myConfig.setIndexUrl(origin) ;
	                    }
	                }
	           /*     else if (strNode.equals("log")) {
	                    String level = xml.getAttributeValue(null, "level");
	                    LOG.i("CordovaLog", "Found log level %s", level);
	                    if (level != null) {
	                        LOG.setLogLevel(level);
	                    }
	                }
	                else if (strNode.equals("preference")) {
	                    String name = xml.getAttributeValue(null, "name");
	                     Java 1.6 does not support switch-based strings
	                       Java 7 does, but we're using Dalvik, which is apparently not Java.
	                       Since we're reading XML, this has to be an ugly if/else.
	                       
	                       Also, due to cast issues, each of them has to call their separate putExtra!  
	                       Wheee!!! Isn't Java FUN!?!?!?
	                       
	                       Note: We should probably pass in the classname for the variable splash on splashscreen!
	                       
	                    if(name.equals("splashscreen")) {
	                        String value = xml.getAttributeValue(null, "value");
	                        int resource = 0;
	                        if (value != null)
	                        {
	                            value = "splash";
	                        }
	                        resource = action.getResources().getIdentifier(value, "drawable", action.getPackageName());
	                        
	                        action.getIntent().putExtra(name, resource);
	                        LOG.i("CordovaLog", "Found preference for %s=%s", name, value);
	                        Log.d("CordovaLog", "Found preference for " + name + "=" + value);
	                    }
	                    else if(name.equals("backgroundColor")) {
	                        int value = xml.getAttributeIntValue(null, "value", Color.BLACK);
	                        action.getIntent().putExtra(name, value);
	                        LOG.i("CordovaLog", "Found preference for %s=%d", name, value);
	                        Log.d("CordovaLog", "Found preference for " + name + "=" + Integer.toString(value));
	                    }
	                    else if(name.equals("loadUrlTimeoutValue")) {
	                        int value = xml.getAttributeIntValue(null, "value", 20000);
	                        action.getIntent().putExtra(name, value);
	                        LOG.i("CordovaLog", "Found preference for %s=%d", name, value);
	                        Log.d("CordovaLog", "Found preference for " + name + "=" + Integer.toString(value));
	                    }
	                    else if(name.equals("keepRunning"))
	                    {
	                        boolean value = xml.getAttributeValue(null, "value").equals("true");
	                        action.getIntent().putExtra(name, value);
	                    }
	                    else
	                    {
	                        String value = xml.getAttributeValue(null, "value");
	                        action.getIntent().putExtra(name, value);
	                        LOG.i("CordovaLog", "Found preference for %s=%s", name, value);
	                        Log.d("CordovaLog", "Found preference for " + name + "=" + value);
	                    }
	                    
	                    LOG.i("CordovaLog", "Found preference for %s=%s", name, value);
	                    Log.d("CordovaLog", "Found preference for " + name + "=" + value);
	                     
	                }
	                else if (strNode.equals("content")) {
	                    String src = xml.getAttributeValue(null, "src");

	                    LOG.i("CordovaLog", "Found start page location: %s", src);

	                    if (src != null) {
	                        Pattern schemeRegex = Pattern.compile("^[a-z]+://");
	                        Matcher matcher = schemeRegex.matcher(src);
	                        if (matcher.find()) {
	                            startUrl = src;
	                        } else {
	                            if (src.charAt(0) == '/') {
	                                src = src.substring(1);
	                            }
	                            startUrl = "file:///android_asset/www/" + src;
	                        }
	                    }
	                }*/

	            }

	            try {
	                eventType = xml.next();
	            } catch (XmlPullParserException e) {
	                e.printStackTrace();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }

	} 
}
