/**
 * Copyright &copy; 2014-2016  All rights reserved.
 *
 * Licensed under the 深圳嘉宝易汇通科技发展有限公司 License, Version 1.0 (the "License");
 * 
 */
package com.uib.common.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.uib.common.web.Global;

/**
 * @ClassName: ImageUploadUtil
 * @author sl
 * @date 2015年9月14日 下午5:04:56
 */
public class FileUploadUtil {

	private static Logger logger = LoggerFactory
			.getLogger(FileUploadUtil.class);

	private final static String path = Global.getConfig("upload.path");

	public static String upload(MultipartFile file) {
		if (file.isEmpty()) {
			return null;
		}
		String dir = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/";
		// String fileName = file.getOriginalFilename();
		String suffix = file.getOriginalFilename().substring(
				file.getOriginalFilename().lastIndexOf("."));
		String fileName = UUID.randomUUID() + suffix;
		File targetFile = new File(path + dir, fileName);
		logger.info("文件保存目录为：" + targetFile.getPath());
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		// 保存
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "upload/" + dir + fileName;
	}
}
