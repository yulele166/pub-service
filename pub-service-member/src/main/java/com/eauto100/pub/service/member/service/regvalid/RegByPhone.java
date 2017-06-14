/**
 * 
 */
package com.eauto100.pub.service.member.service.regvalid;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eauto100.pub.service.framework.database.IDaoSupport;
import com.eauto100.pub.service.framework.util.DateUtil;
import com.eauto100.pub.service.framework.util.StringUtil;
import com.eauto100.pub.service.member.service.plugin.IRegByPhone;

/**
 * @author yulele
 * @date 2015年12月2日  下午4:19:21
 */
@Service
public class RegByPhone implements IRegByPhone {
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
	public String newGuid() {
		
		String guid=DateUtil.getDateline()+StringUtil.getRandomFour();
		return guid;
	}
	@Override
	public void reg(String phone, String pwd, String guid) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("mobile", phone);
		map.put("password", pwd);
		map.put("guid", this.newGuid());
		daoSupport.insert("pub_member", map);
	}

}
