/**
 * 
 */
package com.eauto100.pub.service.member.service.regvalid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eauto100.pub.service.framework.database.IDaoSupport;
import com.eauto100.pub.service.member.service.plugin.IRegValid;

/**
 * @author yulele
 * @date 2015年12月2日  下午12:42:32
 */
@Service
public class RegValid implements IRegValid {
	@Autowired
	private IDaoSupport daoSupport;
	@Override
	public boolean phoneValid(String phone) {
		String sql="select count(*) from pub_member where mobile=?";
		int count=daoSupport.queryForInt(sql, phone);
		boolean flag=true;
		if(count!=0){
			flag=false;
			return flag;
		}
		return true;
	}

	@Override
	public boolean emailValid(String email) {
		String sql="select count(*) from pub_member where email=?";
		int count=daoSupport.queryForInt(sql, email);
		boolean flag=true;
		if(count!=0){
			flag=false;
			return flag;
		}
		return true;
		
	}

	@Override
	public boolean unameValid(String uname) {
		String sql="select count(*) from pub_member where uname=?";
		int count=daoSupport.queryForInt(sql, uname);
		boolean flag=true;//用户名不重复
		if(count!=0){
			flag=false;
			return flag;
		}
		return true;
		
	}

	

}
