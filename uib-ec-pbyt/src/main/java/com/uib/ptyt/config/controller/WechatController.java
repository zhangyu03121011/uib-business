package com.uib.ptyt.config.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uib.common.web.Global;
import com.uib.ptyt.config.entity.WechatConfig;
import com.uib.ptyt.config.service.WechatService;

/**
 * @Todo 获取微信配置
 * @date 2016年6月14日
 * @author Ly
 */
@Controller
@RequestMapping("/wechat/config")
public class WechatController {

	private Logger logger = Logger.getLogger(WechatController.class);

	@Autowired
	private WechatService wechatService;

	/**
	 * 获取微信页签
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getWechatConfig")
	public WechatConfig getWechatConfig(String url) throws Exception {
		logger.info("获取微信页签开始……" + url);
		WechatConfig config = new WechatConfig();
		try {
			config = wechatService.getWechatConfig(url);
		} catch (Exception e) {
			logger.info("获取微信页签异常:" + e.fillInStackTrace());
		}
		return config;
	}

	/**
	 * 从微信下载图片
	 * 
	 * @param mediaId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/downloadImage")
	@ResponseBody
	public String downloadImage(String mediaId, HttpServletRequest request) {
		logger.info("从微信下载图片开始……" + mediaId);
		String rootPath = request.getSession().getServletContext()
				.getRealPath("");
		String imgPath = "";
		try {
			imgPath = wechatService.downloadImage(mediaId, rootPath);
		} catch (Exception e) {
			logger.info("从微信下载图片异常:" + e.fillInStackTrace());
		}
		return imgPath;
	}
	
	/**
	 * 从服务器上删除页面上要删的图片
	 * @param rootPath
	 * @return
	 */
	@RequestMapping("/deleteFile")
	@ResponseBody
	public Boolean deleteFile(String rootPath, HttpServletRequest request) {
		 String imageUrl=Global.getConfig("upload.image.path");
		 logger.info("从服务器上删除图片开始……imageUrl=" + imageUrl);
		 rootPath=imageUrl+rootPath;
		logger.info("从服务器上删除图片开始……rootPath=" + rootPath);
		 File file = new File(rootPath);
		 boolean flag=false;
		try {
			 if (file.exists()){
				 logger.info("------------------------图片存在，开始删----------");
				 file.delete();
			 }
			 logger.info("--------------------------图片不存在,但是不报错------------------");
			 flag=true;
		} catch (Exception e) {
			logger.info("从服务器上删除图片异常:" + e.fillInStackTrace());
		}
		return flag;
	}

}
