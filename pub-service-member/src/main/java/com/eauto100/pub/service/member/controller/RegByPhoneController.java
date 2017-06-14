/**
 * 手机注册会员
 */
package com.eauto100.pub.service.member.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eauto100.pub.service.framework.util.StringUtil;
import com.eauto100.pub.service.member.service.plugin.IRegByPhone;

/**
 * @author yulele
 * @date 2015年12月2日  下午4:07:31
 */
@Controller
@RequestMapping("/member")
public class RegByPhoneController {
	@Autowired
	private IRegByPhone regbyphone;
	@RequestMapping(value="/regbyphone")
	@ResponseBody
	public String regbyphome(HttpServletRequest request){
		String phone=request.getParameter("phone");
		String pwd=StringUtil.md5(request.getParameter("pwd")+phone);
		Map<String,Object> map=new HashMap<String,Object>();
		if(!regbyphone.phoneValid(phone)){
			map.put("messagecode", 205);
			map.put("message", "失败(手机号已被注册)");
			map.put("guid", "");
			return JSONObject.toJSONString(map);
		}else{
			String guid=regbyphone.newGuid();
			map.put("messagecode", 200);
			map.put("message", "成功");
			map.put("guid",guid);
			regbyphone.reg(phone, pwd, guid);
			return JSONObject.toJSONString(map);
		}
	}

}
