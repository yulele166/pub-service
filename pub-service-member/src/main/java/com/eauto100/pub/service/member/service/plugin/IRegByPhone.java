/**
 * 
 */
package com.eauto100.pub.service.member.service.plugin;

import org.springframework.stereotype.Service;

/**
 * @author yulele
 * @date 2015年12月2日  下午4:16:08
 */
@Service
public interface IRegByPhone {
	/**
	 * 手机验证
	 * @param phone
	 */
	public boolean phoneValid(String phone);
	/**
	 * 生成guid标识
	 * 当前时间的秒数+四位随机数
	 */
	public String newGuid();
	/**
	 * 手机会员注册
	 * @param phone
	 * @param pwd
	 * @param guid
	 */
	public void reg(String phone,String pwd,String guid);
}
