package com.eauto100.pub.service.sms.service.parseConfig;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.eauto100.pub.service.framework.util.FileUtil;
import com.eauto100.pub.service.framework.util.StringUtil;


public class AppConfigParse {
	private static Map<String, String> appMap=new HashMap<String, String>();
	static{ 
		 
		try{
			InputStream in  = FileUtil.getResourceAsStream("/config/app.properties");
			Properties props = new Properties();
			props.load(in);
				
			String similar_sms= props.getProperty("similar_sms");
			String sms_bean= props.getProperty("sms_bean");
	
			appMap.put("similar_sms", similar_sms); //模拟发送短信标记
			appMap.put("sms_bean", sms_bean);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static String getAppId(String type){
		return appMap.get(type);
	}
}
