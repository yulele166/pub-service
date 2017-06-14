package com.eauto100.pub.service.sms.service.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;

import com.eauto100.pub.service.framework.database.BaseSupport;
/**
 * 短信平台公用方法
 * @author yulele
 *
 */
public class Utils extends BaseSupport{
	
	/**
	 * 
	 * @param phone 不超过5个
	 * @param content
	 * 			短信内容不超过70字符，数字、字母、英文、
	 * 			全角、半角、标点符都算为一个字符
	 */
	public static Map<String,String> requestCheck(String phone,String content){
		Map<String,String> checkMap=new HashMap<String,String>();
		if(phone.split(",").length>5){
			checkMap.put("phone", "手机号码不能超过5个！");
		}
		if (content.length() > 70) {
			checkMap.put("content", "短信内容不能超过70！");
		}
		return checkMap;
	}
	/**
	 * post请求,网关返回json
	 * @param platform
	 * @return
	 */
	public static String request(String platform){
		String result="";
		try {
			// 创建url对象
			URL url = new URL(platform.toString());
			
			// 打开url连接
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			
			// 设置url请求方式 ‘get’ 或者 ‘post’
			connection.setRequestMethod("POST");
			
			// 发送
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			
			// 返回发送结果
			result = in.readLine().toString();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return result;
	}
	 // 解析XML文档    
    public static String parseDOM4J(Document doc) {    
        Element root = doc.getRootElement();
        Attribute resultAttr=root.attribute("result");
        String result=resultAttr.getValue();
        return result;
    }
   
   
	/**
	 * unicode解码
	 * @param theString
	 * @return
	 */
	public static String decodeUnicode(String theString) {    
		  
	     char aChar;    
	  
	     int len = theString.length();    
	  
	     StringBuffer outBuffer = new StringBuffer(len);    
	  
	     for (int x = 0; x < len;) {    
	  
	      aChar = theString.charAt(x++);    
	  
	      if (aChar == '\\') {    
	  
	       aChar = theString.charAt(x++);    
	  
	       if (aChar == 'u') {    
	  
	        // Read the xxxx    
	  
	        int value = 0;    
	  
	        for (int i = 0; i < 4; i++) {    
	  
	         aChar = theString.charAt(x++);    
	  
	         switch (aChar) {    
	         	case '0':    
	         	case '1':    
	         	case '2':    
	         	case '3':    
	         	case '4':    
	         	case '5':    
	         	case '6':    
	         	case '7':    
	         	case '8':    
	         	case '9': value = (value << 4) + aChar - '0';break;    
	         	case 'a':    
	         	case 'b':    
	         	case 'c':    
	         	case 'd':    
	         	case 'e':    
	         	case 'f': value = (value << 4) + 10 + aChar - 'a';break;    
	         	case 'A':    
	         	case 'B':    
	         	case 'C':    
	         	case 'D':    
	         	case 'E':    
	         	case 'F': value = (value << 4) + 10 + aChar - 'A';break;    
	         	default:   
	         		
	            throw new IllegalArgumentException(    
	              "Malformed   \\uxxxx   encoding.");    
	           }    
	  
	         }    
	          	outBuffer.append((char) value);    
	         } else {    
	          if (aChar == 't') 
	        	  
	        	  aChar = '\t';
	          
	          else if (aChar == 'r')
	        	  
	        	  aChar = '\r';    
	  
	          else if (aChar == 'n')    
	  
	        	  aChar = '\n';    
	  
	          else if (aChar == 'f')    
	  
	        	  aChar = '\f';    
	  
	          outBuffer.append(aChar);    
	  
	         }    
	  
	        } else   
	  
	        outBuffer.append(aChar);    
	  
	       }    
	  
	       return outBuffer.toString();    
	  
	      }   

}
