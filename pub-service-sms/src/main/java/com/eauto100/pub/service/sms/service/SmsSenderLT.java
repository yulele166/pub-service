package com.eauto100.pub.service.sms.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import com.eauto100.pub.service.framework.database.BaseSupport;
import com.eauto100.pub.service.framework.util.StringUtil;
import com.eauto100.pub.service.sms.model.SmsPlatform;
import com.eauto100.pub.service.sms.service.parseConfig.AppConfigParse;
import com.eauto100.pub.service.sms.service.plugin.ISmsSendEvent;

import net.sf.json.JSONObject;
//@Service
public class SmsSenderLT extends BaseSupport implements ISmsSendEvent{
	
	@SuppressWarnings("finally")
	public String onSend(String phone, String content) {
		String messagecode = "";
		String sql ="select * from pub_sms_platform where id=3";
		SmsPlatform platform =  (SmsPlatform) this.daoSupport.queryForObject(sql, SmsPlatform.class);
		String config=platform.getConfig();
		JSONObject jsonObject = JSONObject.fromObject(config);  
		Map configMap = (Map)jsonObject.toBean(jsonObject, Map.class);
		String url=configMap.get("url").toString();
		String uid=configMap.get("uid").toString();
		String pwd=configMap.get("pwd").toString();
//			   pwd=StringUtil.md5(uid+pwd);//加密
//			   pwd="4b3d06d1fce032d72294c93e59ef8733";
		// 创建StringBuffer对象用来操作字符串
		StringBuffer sb = new StringBuffer(url);
		// 向StringBuffer追加用户名
		sb.append("?action=sendOnce&uid=").append(uid);
		// 向StringBuffer追加密码（密码采用MD5 32位 小写）
		sb.append("&pwd=").append(pwd);
		// 向StringBuffer追加手机号码
		sb.append("&mobile=").append(phone);
		// 向StringBuffer追加消息内容转URL标准码
		sb.append("&content=").append(URLEncoder.encode(content));
		sb.append("&encode=utf8");
		System.out.println(sb.toString());
		try {
			// 创建url对象
			URL url2 = new URL(sb.toString());
			
			// 打开url连接
			HttpURLConnection connection = (HttpURLConnection) url2.openConnection();
			
			// 设置url请求方式 ‘get’ 或者 ‘post’
			connection.setRequestMethod("POST");

			// 发送
			BufferedReader in = new BufferedReader(new InputStreamReader(url2.openStream()));

			// 返回发送结果
			String result = in.readLine().toString();
			System.out.println("网关信息:"+decodeUnicode(result));
			 // 将String转换为XML文档 
			Document doc=DocumentHelper.parseText(result);
			messagecode=parseDOM4J(doc);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			String message=getResponse(messagecode);
//			Map<String,String> smsMap = new HashMap<String,String>();
//			JSONObject jsonObj = JSONObject.fromObject(message);  
//			smsMap = (Map)jsonObject.toBean(jsonObj, Map.class);
//			smsMap.put("messagecode",result);
//			smsMap.put("message", message);
			AppConfigParse appconfig=new AppConfigParse();
			System.out.println(appconfig.getAppId("similar_sms"));
			if("yes".equals(appconfig.getAppId("similar_sms"))){
				this.mockSend(message);
			}
			
			return str2json(messagecode);
		}
	}
	 // 解析XML文档    
    private static String parseDOM4J(Document doc) {    
        Element root = doc.getRootElement();
        Attribute resultAttr=root.attribute("result");
        String result=resultAttr.getValue();
        return result;
    }
	/**
	 * 模拟发送短信
	 * @param message
	 */
	public void mockSend(String message){
		File file=new File("sms.txt");
		System.out.println(file);
		try {
			FileOutputStream fos=new FileOutputStream(file);
			byte[] b=message.getBytes(); 
			fos.write(b);
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 将返回状态编码转化为描述结果
	 * 
	 * @param result 状态编码
	 * @return 描述结果
	 */
	public String getResponse(String result) {
		 switch (Integer.parseInt(result))
		    {
			    case 1: 
			      return "成功";
			    case 0: 
			      return "帐户格式不正确";
			    case -1: 
			      return "服务器拒绝";
			    case -2: 
			      return "密钥不正确";
			    case -3: 
			      return "密钥已锁定";
			    case -4: 
			      return "参数不正确";
			    case -5: 
			      return "无此帐户";
			    case -6: 
			      return "帐户已锁定或已过期";
			    case -7: 
			      return "帐户未开启接口发送";
			    case -8: 
			      return "不可使用该通道组";
			    case -9: 
			      return "帐户余额不足";
			    case -10: 
			      return "内部错误";
			    case -11: 
			      return "扣费失败";
		    }
		    return "未知错误";
	}
	private String str2json(String messagecode) {
		switch (Integer.parseInt(messagecode))
	    {
		    case 1: 
		      return "{\"messagecode\":"+messagecode+",\"message\":\"成功\"}";
		   default:
			  return "{\"messagecode\":"+messagecode+",\"message\":\"失败\"}";
		    
	    }
	}
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
	           case '9':    
	            value = (value << 4) + aChar - '0';    
	            break;    
	           case 'a':    
	           case 'b':    
	           case 'c':    
	           case 'd':    
	           case 'e':    
	           case 'f':    
	            value = (value << 4) + 10 + aChar - 'a';    
	           break;    
	           case 'A':    
	           case 'B':    
	           case 'C':    
	           case 'D':    
	           case 'E':    
	           case 'F':    
	            value = (value << 4) + 10 + aChar - 'A';    
	            break;    
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
	/* (non-Javadoc)
	 * @see com.eauto100.pub.service.sms.service.plugin.ISmsSendEvent#onSend(java.lang.String, java.lang.String, java.lang.String)
	 */
	public String onSend(String phone, String content, String source) {
		// TODO Auto-generated method stub
		return null;
	}
	/* (non-Javadoc)
	 * @see com.eauto100.pub.service.sms.service.plugin.ISmsSendEvent#onSend(java.lang.String, java.lang.String, java.lang.String, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String onSend(String phone, String content, String source, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
	  

}
