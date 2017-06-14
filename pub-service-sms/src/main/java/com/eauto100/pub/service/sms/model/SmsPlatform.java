package com.eauto100.pub.service.sms.model;


import com.eauto100.pub.service.framework.database.PrimaryKeyField;

/**
 * 短信平台信息
 * @author yulele
 *
 */
public class SmsPlatform {
	private Integer id;
	private String platform_name;	//短信平台名称
	private Integer is_open;
	private String config;
	private String code;
	
	@PrimaryKeyField
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPlatform_name() {
		return platform_name;
	}
	public void setPlatform_name(String platform_name) {
		this.platform_name = platform_name;
	}
	public Integer getIs_open() {
		return is_open;
	}
	public void setIs_open(Integer is_open) {
		this.is_open = is_open;
	}
	
	public String getConfig() {
		return config;
	}
	public void setConfig(String config) {
		this.config = config;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
