/**
 * 
 */
package com.eauto100.pub.service.member.service.plugin;

import org.springframework.stereotype.Service;

/**
 * @author yulele
 * @date 2015年12月2日  下午12:38:42
 */
@Service
public interface IRegValid {
	/**
	 * 手机验证
	 * @param phone
	 */
	public boolean phoneValid(String phone);
	/**
	 * 邮箱验证
	 * @param email
	 */
	public boolean emailValid(String email);
	/**
	 * 用户名验证
	 * @param uname
	 */
	public boolean unameValid(String uname);

}
