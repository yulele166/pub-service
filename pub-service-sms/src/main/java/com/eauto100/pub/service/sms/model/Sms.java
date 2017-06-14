package com.eauto100.pub.service.sms.model;


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
	public Sms(String uid, String pwd, String mobile, String content, String type) {
		super();
		this.uid = UID;
//		this.pwd = StringUtil.md5(PWD+UID);//加密成32位字符串
		//多个号码以“,”分隔，一次最多提交号码不超过5个
		if(mobile.split(",").length>Sms.getSumNum()){
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
		if (content.length() > Sms.getMaxLen()) {
			content = content.substring(0, Sms.getMaxLen() - 1);
		}
		this.content = content;
		this.type=type;
	}
	
	
	public static int getSumNum() {
		return SUM_NUM;
	}


	public static int getMaxLen() {
		return MAX_LEN;
	}


	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getPwd() {
		return pwd;
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
