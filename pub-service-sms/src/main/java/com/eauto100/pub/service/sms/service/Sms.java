package com.eauto100.pub.service.sms.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.eauto100.pub.service.framework.util.StringUtil;
import com.eauto100.pub.service.sms.service.parseConfig.AppConfigParse;
@Service
public class Sms {
	
	/** 号码总数 */
	public static final int SUM_NUM = 5;
	/** 短信内容最大长度 */
	public static final int MAX_LEN = 70;
	/** 用户名常量 */
	public static final String UID = "test";
	/** 用户密码常量 */
	public static final String PWD = "123456";
	
	/** 用户名 */
	private String uid;
	/** 用户密码 */
	private String pwd;
	/** 群发号码组 */
	private String mobile;
	/** 短信内容 */
	private String content;
	/** 返回数据类型 */
	private String type;
	

	public Sms() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 完全构造函数：进行密码的MD5加密，内容的URL编码
	 * 
	 * @param uid
	 *            用户名
	 * @param pwd
	 *            用户密码
	 * @param mobile
	 *            手机号码字符串（用","隔开）
	 * @param content
	 *            短信内容(未指定长度，默认长度参数为0)
	 */
	public Sms(String uid, String pwd, String mobile, String content,String type) {
		super();
		this.uid = UID;
		this.pwd = StringUtil.md5(PWD+UID);//加密成32位字符串
		//多个号码以“,”分隔，一次最多提交号码不超过5个
		if(mobile.split(",").length>this.SUM_NUM){
			try {
				throw new Exception("提交号码不能超过5个");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			this.mobile=mobile;
		}
		//短信内容不超过70字符，数字、字母、英文、全角、半角、标点符都算为一个字符
		if (content.length() > this.MAX_LEN) {
			content = content.substring(0, this.MAX_LEN - 1);
		}
		this.content = content;
		this.type=type;
	}
	
	/**
	 * 发送信息的接口方法
	 * 
	 * @param parentIds
	 * @param senderId
	 * 
	 * @return 发送信息返回状态结果
	 */
	public Map<String,String> send(String mobile, String content) {
		return sendMessage(mobile, content);
	}

	/**
	 * 使用http协议发送信息
	 * 
	 * @return 发送信息返回状态结果
	 */
	@SuppressWarnings("finally")
	private Map<String,String> sendMessage(String mobile, String content) {
		String result = "";
		// 创建StringBuffer对象用来操作字符串
		StringBuffer sb = new StringBuffer("http://gjdx.114ppt.cn/admin/master/SMSApi.php");
		// 向StringBuffer追加用户名
		sb.append("?uid=").append(this.getUid());
		// 向StringBuffer追加密码（密码采用MD5 32位 小写）
		sb.append("&pwd=").append(this.getPwd());
		// 向StringBuffer追加手机号码
		sb.append("&mobile=").append(this.getMobile());
		// 向StringBuffer追加消息内容转URL标准码
		sb.append("&content=").append(URLEncoder.encode(this.getContent()));
		sb.append("&encode=utf8");
		System.out.println(sb.toString());
		try {
			// 创建url对象
			URL url = new URL(sb.toString());
			
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
		} finally {
			Map<String,String> smsMap = new HashMap<String,String>();
			String message=getResponse(result);
			smsMap.put("messagecode",result);
			smsMap.put("message", message);
			AppConfigParse appconfig=new AppConfigParse();
			System.out.println(appconfig.getAppId("similar_sms"));
			if("yes".equals(appconfig.getAppId("similar_sms"))){
				this.mockSend(message);
			}
			return smsMap;
		}
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
	 * @param result
	 *            状态编码
	 * @return 描述结果
	 */
	private String getResponse(String result) {
		if (result.equals("200")) {
			return "成功";
		}
		if (result.equals("201")) {
			return "连不上短信网关";
		}
		if (result.equals("202")) {
			return "短信网关返回错误信息";
		}
		if (result.equals("203")) {
			return "连接不上邮件网关";
		}
		if (result.equals("204")) {
			return "邮件网关返回错误信息";
		}
		if (result.equals("205")) {
			return "手机号已被注册";
		}
		if (result.equals("206")) {
			return "注册验证成功";
		}
		if (result.equals("207")) {
			return "注册验证失败";
		}
		return "未知错误";
	}
	

	public String getUid() {
		return UID;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPwd() {
		return PWD;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
