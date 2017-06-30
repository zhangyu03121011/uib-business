package com.uib.ptyt.config.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.uib.common.mapper.JsonMapper;
import com.uib.ptyt.config.entity.AccessToken;
import com.uib.ptyt.config.entity.JsapiTicket;
import com.uib.ptyt.config.entity.WechatConfig;

/**
 * @Todo 获取微信配置
 * @date 2016年6月14日
 * @author Ly
 */
@Service
public class WechatService {

	private Logger logger = Logger.getLogger(WechatService.class);
	@Value("${WECHAT.APPID}")
	private String APPID;
	@Value("${WECHAT.APPKEY}")
	private String APPSECRET;

	// access_token_url
	private String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	// jsapi_ticket_url
	private final String JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	// download_img_url
	private final String DOWNLOAD_IMG_URL = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";

	/**
	 * @Todo 获取微信页签
	 * @param url
	 * @return
	 * @throws Exception
	 *             WechatConfig
	 */
	public WechatConfig getWechatConfig(String url) throws Exception {
		logger.info("获取微信页签开始……" + url);
		WechatConfig config = new WechatConfig();
		config.setAppId(APPID);
		String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
		config.setNonceStr(nonceStr);
		String timestamp = Long.toString(System.currentTimeMillis() / 1000);
		config.setTimestamp(timestamp);
		String str = "jsapi_ticket=" + getJsapiTicket().getTicket() + "&noncestr=" + nonceStr + "&timestamp=" + timestamp + "&url=" + url;

		MessageDigest crypt = MessageDigest.getInstance("SHA-1");
		crypt.reset();
		crypt.update(str.getBytes("UTF-8"));
		String signature = byteToHex(crypt.digest());
		config.setSignature(signature);
		logger.info("wechatConfig:" + JsonMapper.toJsonString(config));
		return config;
	}

	/**
	 * 获取jsapiTicket
	 * 
	 * @return
	 */
	public JsapiTicket getJsapiTicket() throws Exception {
		JsapiTicket jsapiTicket = new JsapiTicket();
		String url = JSAPI_TICKET_URL.replace("ACCESS_TOKEN", getAccessToken().getToken());
		logger.info("获取JsapiTicket开始：");
		JSONObject jsonObject = httpGet(url);
		if (jsonObject != null) {
			jsapiTicket.setErrcode(jsonObject.getString("errcode"));
			jsapiTicket.setErrmsg(jsonObject.getString("errmsg"));
			jsapiTicket.setExpiresIn(jsonObject.getString("expires_in"));
			jsapiTicket.setTicket(jsonObject.getString("ticket"));
		}
		logger.info(JsonMapper.toJsonString(jsapiTicket));
		return jsapiTicket;
	}

	/**
	 * 获取accessToken
	 * 
	 * @return
	 */
	public AccessToken getAccessToken() throws Exception {
		logger.info("获取AccessToken开始：");
		AccessToken token = new AccessToken();
		String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
		JSONObject jsonObject = httpGet(url);
		if (jsonObject != null) {
			token.setToken(jsonObject.getString("access_token"));
			token.setExpiresIn(jsonObject.getIntValue("expires_in"));
		}
		logger.info(JsonMapper.toJsonString(token));
		return token;
	}

	/**
	 * @Todo 从微信下载图片
	 * @param mediaId
	 * @return
	 * @throws Exception
	 */
	public String downloadImage(String mediaId, String rootPath) throws Exception {
		String url = DOWNLOAD_IMG_URL.replace("ACCESS_TOKEN", getAccessToken().getToken()).replace("MEDIA_ID", mediaId);
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		try {
			HttpResponse response = client.execute(get);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return uploadImage(response, rootPath);
			} else {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("从微信服务器下载失败：" + e.getMessage());
			return "";
		} finally {
			get.abort();
		}
	}

	/**
	 * @Todo 上传到服务器
	 * @param httpResponse
	 * @return
	 * @throws Exception
	 */
	public String uploadImage(HttpResponse httpResponse, String rootPath) throws Exception {
		String imgName = httpResponse.getFirstHeader("Content-disposition").getValue();
		if (StringUtils.isNotEmpty(imgName) && imgName.indexOf("\"") != -1) {
			imgName = imgName.substring(imgName.indexOf("\"") + 1, imgName.lastIndexOf("\""));
			logger.info("从微信服务器下载成功：" + imgName);
		}
		File file = new File(rootPath);
		StringBuilder sb = new StringBuilder("/upload/image");
		sb.append(new SimpleDateFormat("/yyyyMMdd/").format(new Date()));
		File newFile = new File(file.getParent() + sb.toString());
		if (!newFile.exists())
			newFile.mkdirs();
		HttpEntity entity = httpResponse.getEntity();
		InputStream is = null;
		FileOutputStream fos = null;
		ByteArrayOutputStream bos = null;
		try {
			is = entity.getContent();
			bos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = is.read(buffer)) != -1) {
				bos.write(buffer, 0, len);
			}
			fos = new FileOutputStream(newFile.getPath() + File.separator + imgName);
			fos.write(bos.toByteArray());
			return sb.toString() + imgName;
		} catch (Exception e) {
			logger.info("保存图片到服务器失败：" + e.fillInStackTrace());
			e.printStackTrace();
			return "";
		} finally {
			is.close();
			fos.close();
		}
	}

	public JSONObject httpGet(String url) {
		JSONObject jsonObject = null;
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		try {
			HttpResponse response = client.execute(get);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				jsonObject = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
			}
		} catch (Exception e) {
			logger.info("HttpGet请求异常：" + url);
			e.printStackTrace();
		} finally {
			get.abort();
		}
		return jsonObject;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	public String getAPPID() {
		return APPID;
	}

	public void setAPPID(String aPPID) {
		APPID = aPPID;
	}

	public String getAPPSECRET() {
		return APPSECRET;
	}

	public void setAPPSECRET(String aPPSECRET) {
		APPSECRET = aPPSECRET;
	}

}
