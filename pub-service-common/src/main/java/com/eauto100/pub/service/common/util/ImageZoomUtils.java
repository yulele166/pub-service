package com.eauto100.pub.service.common.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;

public class ImageZoomUtils {
	      
	    // 图片处理   
	    public static String zoomPic(String outputDir,  String outputFileName, int width, int height, boolean proportion,InputStream in) {   
	        try {   
	                      
	            Image img = ImageIO.read(in);   //如果是本地图片处理的话，这个地方直接把file放到ImageIO.read(file)中即可，如果是执行上传图片的话，  Formfile formfile=获得表单提交的Formfile ,然后 ImageIO.read 方法里参数放 formfile.getInputStream()
	            // 判断图片格式是否正确   
	            if (img.getWidth(null) == -1) {  
	                 
	                return "no";
	            } else {   
	                int newWidth; int newHeight;   
	                // 判断是否是等比缩放   
	                if (proportion == true) {   
	                    // 为等比缩放计算输出的图片宽度及高度   
	                    double rate1 = ((double) img.getWidth(null)) / (double) width + 0.1;   
	                    double rate2 = ((double) img.getHeight(null)) / (double) height + 0.1;   
	                    // 根据缩放比率大的进行缩放控制   
	                    double rate = rate1 > rate2 ? rate1 : rate2;   
	                    newWidth = (int) (((double) img.getWidth(null)) / rate);   
	                    newHeight = (int) (((double) img.getHeight(null)) / rate);   
	                } else {   
	                    newWidth = width; // 输出的图片宽度   
	                    newHeight = height; // 输出的图片高度   
	                }   
	               BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);   
	                 
	                  
	               tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);  
	               FileOutputStream out = new FileOutputStream(outputDir + outputFileName);  
	               // JPEGImageEncoder可适用于其他图片类型的转换   
	               JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
	               encoder.encode(tag);   
	               out.close();   
	            }   
	        } catch (IOException ex) {   
	            ex.printStackTrace();   
	        }   
	        return "ok";   
	    }
}
