/**
 * app.properties文件对象
 */
package com.eauto100.pub.service.sms.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author yulele
 * @date 2015年12月1日
 */
@Component("app")
public class App {
	
	@Value("${similar_sms}")
	private String similar_sms;
	
	@Value("${sms_bean}")
	private String sms_bean;
	
	public String getSimilar_sms() {
		return similar_sms;
	}
	public String getSms_bean() {
		return sms_bean;
	}
	
}
