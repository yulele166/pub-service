package com.eauto100.pub.service.sms.service.plugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.eauto100.pub.service.sms.service.parseConfig.AppConfigParse;

@Service
public interface ISmsSendEvent<T> {
	
	/**
	 * 
	 * @param phone
	 * @param content
	 * @return  json结构的状态码和状态信息
	 * 修饰符默认public abstract
	 */
	public String onSend(String phone, String content,String source,HttpServletRequest request);

	/**
	 * 将返回状态编码转化为描述结果
	 * 
	 * @param result 状态编码
	 * @return 描述结果
	 */
	public String getResponse(String result);
	 
	/**
	 * 
	 * 模拟发送短信
	 *  	扩展方法，子类直接调用
	 * @param message 短信内容
	 * @param path 生成路径
	 * @param flag  发送标记
	 */
	public default void mockSend(String message,String path,String flag) {
		// 配置文件为yes时写入文件
		if ("yes".equals(flag)) {
			try {
				File file = new File(path,"sms.txt");
				if (!file.exists()) {
					try {
						file.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				write(path + "sms.txt", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+message);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	/**
	 * 把内容写入文件
	 * 
	 * @param filePath
	 * @param fileContent
	 */
	public static void write(String filePath, String fileContent) {

		try {
			FileOutputStream fo = new FileOutputStream(filePath);
			OutputStreamWriter out = new OutputStreamWriter(fo, "UTF-8");
			out.write(fileContent);
			out.close();
		} catch (IOException ex) {
			System.err.println("Create File Error!");
			ex.printStackTrace();
		}
	}
	
}
