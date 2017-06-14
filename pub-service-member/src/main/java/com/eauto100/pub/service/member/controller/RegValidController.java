/**
 * 注册验证
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

import com.eauto100.pub.service.member.service.plugin.IRegValid;

/**
 * @author yulele
 * @time 上午11:19:08
 */
@Controller
@RequestMapping("/member")
public class RegValidController {
	@Autowired
	private IRegValid regvalid;
	@RequestMapping(value="/regvalid", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String regvalid(HttpServletRequest request){
		String value=request.getParameter("value");
		String type=request.getParameter("type");
		Map<String,Object> map=new HashMap<String,Object>();
		if("1".equals(type)){//"1"代表手机
			boolean flag=regvalid.phoneValid(value);
			if(flag==false){
				map.put("messagecode", 207);
				map.put("message", "注册验证失败(手机号已被注册)");
				return JSONObject.toJSONString(map);
			}
		}else if("2".equals(type)){//邮箱
			boolean flag=regvalid.emailValid(value);
			if(flag==false){
				map.put("messagecode", 207);
				map.put("message", "注册验证失败(邮箱已被注册)");
				return JSONObject.toJSONString(map);
			}
		}else if("3".equals(type)){//用户名
			boolean flag=regvalid.emailValid(value);
			if(flag==false){
				map.put("messagecode", 207);
				map.put("message", "注册验证失败(用户名已存在)");
				return JSONObject.toJSONString(map);
			}
		}
		map.put("messagecode", 206);
		map.put("message", "注册验证成功");
		return JSONObject.toJSONString(map);
	}
	
}
