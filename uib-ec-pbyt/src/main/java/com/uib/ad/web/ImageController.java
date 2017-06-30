package com.uib.ad.web;

import java.io.File;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
@RequestMapping("/showImage")
public class ImageController {
	private Logger log = LoggerFactory.getLogger(ImageController.class);
	@Value("${upload.image.path}")
	private String UPLOAD_IMAGE_PATH;

	@RequestMapping(method = RequestMethod.GET)
	public void showImage(String filePath, HttpServletRequest request, HttpServletResponse response) {
		/*response.setContentType("image/*");
		FileInputStream fis = null;
		OutputStream os = null;
		try {
			fis = new FileInputStream(UPLOAD_IMAGE_PATH + filePath);
			os = response.getOutputStream();
			int count = 0;
			byte[] buffer = new byte[1024 * 8];
			while ((count = fis.read(buffer)) != -1) {
				os.write(buffer, 0, count);
				os.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
	}

	@RequestMapping("/uploadFile")
	// @ResponseBody
	public void uploadFile4Ajax(@RequestParam("fileUpload") CommonsMultipartFile myfile, HttpServletRequest request, HttpServletResponse response) {
		try {
			String callbackparam = request.getParameter("callback");
			System.out.println(callbackparam);
			FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File("D://temp", myfile.getOriginalFilename()));
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			PrintWriter writer = response.getWriter();

			writer.print("{");
			writer.print("msg:\"文件大小:" + myfile.getSize() + ",文件名:" + myfile.getName() + "\"");
			writer.print("}");

			writer.close();
		} catch (Exception e) {
			log.error("上传图片报错!", e);
		}
	}

//	private FileItem getUploadFileItem(List<FileItem> list) {
//		for (FileItem fileItem : list) {
//			if (!fileItem.isFormField()) {
//				return fileItem;
//			}
//		}
//		return null;
//	}
//
//	private String getUploadFileName(FileItem item) {
//		if (Utils.isBlank(item)) {
//			return "";
//		}
//		// 获取路径名
//		String value = item.getName();
//		// 索引到最后一个反斜杠
//		int start = value.lastIndexOf("/");
//		// 截取 上传文件的 字符串名字，加1是 去掉反斜杠，
//		String filename = value.substring(start + 1);
//
//		return filename;
//	}

	@RequestMapping("/aaa")
	public void testAaa(HttpServletRequest request, HttpServletResponse response) {
		try {
			String callbackparam = request.getParameter("callback");
			PrintWriter writer = response.getWriter();
			writer.print(callbackparam + "({");
			writer.print("msg:\"文件大小:0,文件名:1223\"");
			writer.print("})");
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
