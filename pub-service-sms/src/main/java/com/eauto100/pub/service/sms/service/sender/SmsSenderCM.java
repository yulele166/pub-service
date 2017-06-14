package com.eauto100.pub.service.sms.service.sender;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eauto100.pub.service.framework.database.BaseSupport;
import com.eauto100.pub.service.framework.database.IDaoSupport;
import com.eauto100.pub.service.sms.model.App;
import com.eauto100.pub.service.sms.service.plugin.ISmsSendEvent;
import com.eauto100.pub.service.sms.service.util.Utils;

//@Service("smsSender")
/**
 * 上海创明网络科技有限公司
 * 短信平台
 * @author yulele
 *
 */
public class SmsSenderCM  implements ISmsSendEvent{
	
	/*用户账号*/
	private static final String AC="1001@501238730001";
	/*认证密钥*/
	private static final String AUTHKEY="7000F9780513B72ECC37636D6B8F8E56";
	/*通道组编号*/
	private static final String CGID="52";
	/*短信接口url*/
	private static final String URL="http://smsapi.c123.cn/OpenPlatform/OpenApi";
	@Autowired
	private IDaoSupport daoSupport;
	@Autowired
	private App app;
	@Override
	public String onSend(String phone, String content,String source,HttpServletRequest request) {
		
		//短信发送前
		Map<String,String> smslog=new HashMap<String,String>();
		smslog.put("log_type", "1");
		smslog.put("send_source", source);
		smslog.put("phone_number", phone);
		smslog.put("content", content);
		smslog.put("attenchment", "");
		smslog.put("send_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		daoSupport.insert("pub_sms_log", smslog);
		
		
		// 创建StringBuffer对象用来操作字符串
		StringBuffer sb = new StringBuffer(URL);
		// 向StringBuffer追加用户名
		sb.append("?action=sendOnce&ac=").append(AC);
		// 向StringBuffer追加密码（密码采用MD5 32位 小写）
		sb.append("&authkey=").append(AUTHKEY+"test");
		sb.append("&cgid=").append(CGID);
		// 向StringBuffer追加手机号码
		sb.append("&m=").append(phone);
		// 向StringBuffer追加消息内容转URL标准码
		sb.append("&c=").append(URLEncoder.encode(content));
		sb.append("&encode=utf8");
		System.out.println(sb.toString());
		
		//向短信平台发请求
		String result=Utils.request(sb.toString());
		
		String messagecode="";
		try {
			//将String转换为XML文档 
			Document doc= DocumentHelper.parseText(result);
			messagecode=Utils.parseDOM4J(doc);
			String message=this.getResponse(messagecode);
			//网关回送
			Map<String,String> smsSendLog=new HashMap<String,String>();
			smsSendLog.put("phone_number", phone);
			smsSendLog.put("content", content);
			smsSendLog.put("send_msg", sb.toString());
			smsSendLog.put("is_success", (messagecode=="1"?"1":"0"));
			smsSendLog.put("callback_msg", result);
			smsSendLog.put("send_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			this.daoSupport.insert("pub_sms_send_log", smsSendLog);
			
			System.out.println("网关信息:"+Utils.decodeUnicode(result));
			System.out.println(message);
			//模拟发送信息
			String path= request.getSession().getServletContext().getRealPath("/")+"sms\\";
			String flag=app.getSimilar_sms();//发送标记
			this.mockSend(message,path,flag);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return str2json(messagecode);
	}

	@Override
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
	/**
	 * 
	 * @param messagecode
	 * @return
	 */
	private String str2json(String messagecode) {
		switch (Integer.parseInt(messagecode))
	    {
		    case 1: 
		      return "{\"messagecode\":"+messagecode+",\"message\":\"成功\"}";
		   default:
			  return "{\"messagecode\":"+messagecode+",\"message\":\"失败\"}";
		    
	    }
	}

}
