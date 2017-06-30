package com.uib.ecmanager.modules.sys.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.uib.ecmanager.common.config.Global;

@Controller
@RequestMapping(value = "${adminPath}/")
public class ImageController {
	public static final String SYSTEM_FILE_PATH = Global
			.getConfig("upload.image.path");
	public Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * 下载规格值图片
	 * 
	 * @param dto
	 */
	@RequestMapping(value = "/load/image/{time}/{fileName}", method = RequestMethod.GET)
	@ResponseBody
	public void downloadFile(@PathVariable String time,
			@PathVariable String fileName, HttpServletResponse response) {
		try {

			if (StringUtils.isNotBlank(fileName)) {
				File file = new File(SYSTEM_FILE_PATH + "/image/" + time);
				String fileExt = fileName.split("_")[1];
				if (file.isDirectory()) {
					InputStream is = new FileInputStream(SYSTEM_FILE_PATH
							+ "/image/" + time + "/" + fileName + "." + fileExt);
					byte[] bts = IOUtils.toByteArray(is);
					IOUtils.write(bts, response.getOutputStream());
				}
				response.flushBuffer();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 上传规格值图片
	 * 
	 * @param dto
	 */
	@RequestMapping(value = "/upload/{time}/{fileName}", method = RequestMethod.POST)
	@ResponseBody
	public void uploadFile(@PathVariable String time,
			@PathVariable String fileName, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			try {
				String realPath = SYSTEM_FILE_PATH + "/image/time/";
				File dir = new File(realPath);
				if (!dir.isDirectory())
					dir.mkdir();
				DiskFileItemFactory dff = new DiskFileItemFactory();
				dff.setRepository(dir);
				dff.setSizeThreshold(1024000);
				ServletFileUpload sfu = new ServletFileUpload(dff);
				FileItemIterator fii = null;
				fii = sfu.getItemIterator(request);
				String title = ""; // 图片标题
				String url = ""; // 图片地址
				String _fileName = "";
				String state = "SUCCESS";
				String realFileName = "";
				InputStream is = request.getInputStream();
				// Object file = request.getParameter("file");
				// System.out.println(request.getParameter("name")+", "+request.getAttribute("name"));
				//
				byte[] bta = IOUtils.toByteArray(is);
				System.out.println(bta.length);
				while (fii.hasNext()) {
					FileItemStream fis = fii.next();

					try {
						if (!fis.isFormField() && fis.getName().length() > 0) {
							_fileName = fis.getName();
							Pattern reg = Pattern
									.compile("[.]jpg|png|jpeg|gif$");
							Matcher matcher = reg.matcher(_fileName);
							if (!matcher.find()) {
								state = "文件类型不允许！";
								break;
							}
							realFileName = new Date().getTime()
									+ _fileName.substring(
											_fileName.lastIndexOf("."),
											_fileName.length());
							url = realPath + "\\" + realFileName;

							BufferedInputStream in = new BufferedInputStream(
									fis.openStream());// 获得文件输入流
							FileOutputStream a = new FileOutputStream(new File(
									url));
							BufferedOutputStream output = new BufferedOutputStream(
									a);
							Streams.copy(in, output, true);// 开始把文件写到你指定的上传文件夹
						} else {
							String fname = fis.getFieldName();

							if (fname.indexOf("pictitle") != -1) {
								BufferedInputStream in = new BufferedInputStream(
										fis.openStream());
								byte c[] = new byte[10];
								int n = 0;
								while ((n = in.read(c)) != -1) {
									title = new String(c, 0, n);
									break;
								}
							}
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				response.setStatus(200);
				String retxt = "{src:'" + realPath + "/" + realFileName
						+ "',status:success}";
				response.getWriter().print(retxt);
			} catch (Exception ee) {
				ee.printStackTrace();
			}
			// if (StringUtils.isNotBlank(fileName)) {
			// File file = new File(SYSTEM_FILE_PATH + "/image/" + time);
			// String fileExt = fileName.split("_")[1];
			// if (file.isDirectory()) {
			// InputStream is = new FileInputStream(SYSTEM_FILE_PATH
			// + "/image/" + time + "/" + fileName + "." + fileExt);
			// byte[] bts = IOUtils.toByteArray(is);
			// IOUtils.write(bts, response.getOutputStream());
			// }
			// response.flushBuffer();
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping("/uploadFile")
	@ResponseBody
	public Map<String, Object> uploadFile4Ajax(
			@RequestParam("file") MultipartFile myfile,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(
					SYSTEM_FILE_PATH, myfile.getOriginalFilename()));
		} catch (Exception e) {
			log.error("上传图片报错!", e);
			result.put("status", false);
			result.put("code", "500");
		}
		return result;
	}
}
