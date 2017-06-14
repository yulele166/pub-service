package com.eauto100.pub.service.sms.controller;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eauto100.pub.service.sms.service.plugin.ISmsSendEvent;
import com.eauto100.pub.service.sms.service.util.Utils;


@Controller
@RequestMapping("/sms")
public class SmsSenderController {

	@Autowired
	private ISmsSendEvent smsSender;
	@RequestMapping(value="/send",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String send(HttpServletRequest request){
		String json="";
		try {
//			String smsBean=AppConfigParse.getAppId("sms_bean");
//			Class<?> c=Class.forName(smsBean);
//			ISmsSendEvent<?> smsSender=(ISmsSendEvent<?>) c.newInstance();
			String phone=request.getParameter("phone");
			String content=request.getParameter("content");
			String source=request.getParameter("type");
			Map<String,String> map=Utils.requestCheck(phone, content);
			if(map.size()!=0){
				return JSONObject.toJSONString(map);
			}
			System.out.println(smsSender);
			json=smsSender.onSend(phone, content,source,request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
		
	}
	
	@RequestMapping("/mvc")
	public String helloMvc() {
		//视图渲染，/WEB-INF/jsps/home.jsp
		return "index";
	}
}
