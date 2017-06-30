package com.uib.weixin.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.easypay.common.utils.RandomUtil;
import com.uib.common.mapper.JsonMapper;
import com.uib.common.web.Global;
import com.uib.ptyt.config.entity.AccessToken;
import com.uib.ptyt.config.entity.JsapiTicket;
import com.uib.ptyt.config.entity.WechatConfig;
import com.uib.weixin.dto.PayInfo;
import com.uib.weixin.dto.WechatPayConfig;

/**
 * 微信工具类
 * 
 * @author Stephen
 * 
 */
@Component
public class WeixinUtil {

	@Value("${upload.image.path}")
	private static String UPLOAD_IMAGE_PATH;

	private static final Logger logger = LoggerFactory.getLogger(WeixinUtil.class);

	private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	// 获取ticket
	private static final String JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

	// 从微信下载已上传的图片
	private static final String DOWNLOAD_IMG_URL = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";

	private static final String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";

	private static final String OAUTH2_CODE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SNSAPI_BASE&state=1#wechat_redirect";
    
	//刷新ACCESS_TOKEN
    private static final String RESH_ACCESS_TOKEN_URL="https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
	//private static final String OAUTH2_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx47d43edfc65d459c&secret=d4624c36b6795d1d99dcf0547af5443d&code=CODE&grant_type=authorization_code";
	//生成
	//private static final String OAUTH2_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx9b70c488b856ff9f&secret=1cda26139dfbf0d0b7f5044afbadd9b1&code=CODE&grant_type=authorization_code";
	private static final String OAUTH2_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=APPSECRET&code=CODE&grant_type=authorization_code";
	// 统一下单
	private static final String WEIXIN_UNIFIED_ORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	// 获得用户基本信息，头像
	private static final String GETUSERINFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

	// 测试环境配置
	// private static final String PREVIEW_IMAGE_URL = "http://test.e-life.me";
	// //预览图片地址
	// private static final String WEIXIN_MCH_ID = "10022311";
	// private static final String PAY_CALL_BACK =
	// "http://test.e-life.me/f/wechat/member/user/payCallBack";
	// private static final String REDIRECT_URI =
	// "http://test.e-life.me/f/wechat/callback";
	// private static final String WEIXIN_KEY =
	// "649fe99013d24d48a87fee94622c96c6";
	// private static final String APPID = "wx3d407f02f5d3dd63"; //生产环境
	// private static final String APPSECRET =
	// "457738ed27423612b9a4e2c5cbdfe35a"; //生产环境

	// private static final String PREVIEW_IMAGE_URL =
	// "http://wechat.stg.e-life.me";
	//
	// //生产环境(新公众号)
	// private static final String PAY_CALL_BACK =
	// "http://wechat.stg.e-life.me/f/wechat/member/user/payCallBack";
	// private static final String REDIRECT_URI =
	// "http://wechat.stg.e-life.me/f/wechat/callback";
	// //支付商户号
	// private static final String WEIXIN_MCH_ID = "1228364502";
	// private static final String APPID = "wxbf4948dd78b6236c";
	// private static final String APPSECRET =
	// "98c631b7c50c8aa78ec6fc64c5bd8a3e";
	// private static final String WEIXIN_KEY =
	// "025c0dca0ea2448e827c88728c1bc6c3";

	// new生产公众号
	private static final String PREVIEW_IMAGE_URL = "http://mall.m.pingbyt.cn/";

	// 生产环境(新公众号)
	private static final String PAY_CALL_BACK = "http://mall.m.pingbyt.cn/f/wechat/member/user/payCallBack";
	private static final String REDIRECT_URI = "http://mall.m.pingbyt.cn/f/wechat/callback";
	// 支付商户号
	private static final String WEIXIN_MCH_ID = "1311844701";
	// private static final String APPID = "wx47d43edfc65d459c";
	// private static final String APPSECRET =
	// "d4624c36b6795d1d99dcf0547af5443d";
	// private static final String WEIXIN_KEY =
	// "0026571875344ae39183e5fc4b22f590";
	private static final String APPID = Global.getConfig("WECHAT.APPID");
	private static final String APPSECRET = Global.getConfig("WECHAT.APPKEY");

	private static final String WEIXIN_KEY = "0026571875344ae39183e5fc4b22f590";

	/**
	 * get请求
	 * 
	 * @param url
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static JSONObject doGetStr(String url) throws ParseException, IOException {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		JSONObject jsonObject = null;
		HttpResponse httpResponse = client.execute(httpGet);
		HttpEntity entity = httpResponse.getEntity();
		if (entity != null) {
			String result = EntityUtils.toString(entity, "UTF-8");
			EntityUtils.consume(entity);
			jsonObject = JSONObject.parseObject(result);
		}
		return jsonObject;
	}

	/**
	 * HTTP Get 获取内容
	 * 
	 * @param url
	 *            请求的url地址 ?之前的地址
	 * @param params
	 *            请求的参数
	 * @param charset
	 *            编码格式
	 * @return 页面内容
	 */
	public static String doGet(String url, Map<String, String> params, String charset) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		logger.info("HttpUtil doGet url=====" + url);
		if (params != null) {
			logger.info("HttpUtil doGet params=====" + JSONObject.toJSON(params));
		}
		if (StringUtils.isBlank(url)) {
			return null;
		}
		try {
			if (params != null && !params.isEmpty()) {
				List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
				for (Map.Entry<String, String> entry : params.entrySet()) {
					String value = entry.getValue();
					if (value != null) {
						pairs.add(new BasicNameValuePair(entry.getKey(), value));
					}
				}
				url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
			}
			HttpGet httpGet = new HttpGet(url);
			CloseableHttpResponse response = httpClient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpGet.abort();
				throw new RuntimeException("HttpClient,error status code :" + statusCode);
			}
			HttpEntity entity = response.getEntity();
			String result = null;
			if (entity != null) {
				result = EntityUtils.toString(entity, charset);
				logger.info("HttpUtil result====" + result);
			}
			EntityUtils.consume(entity);
			response.close();
			return result;
		} catch (Exception e) {
			logger.error("doGet error ", e);
		}
		return null;
	}

	/**
	 * POST请求
	 * 
	 * @param url
	 * @param outStr
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static JSONObject doPostStr(String url, String outStr) throws ParseException, IOException {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(url);
		JSONObject jsonObject = null;
		httpost.setEntity(new StringEntity(outStr, "UTF-8"));
		HttpResponse response = client.execute(httpost);
		String result = EntityUtils.toString(response.getEntity(), "UTF-8");
		jsonObject = JSONObject.parseObject(result);
		return jsonObject;
	}

	/**
	 * 文件上传
	 * 
	 * @param filePath
	 * @param accessToken
	 * @param type
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws KeyManagementException
	 */
	public static String upload(String filePath, String accessToken, String type) throws IOException, NoSuchAlgorithmException,
			NoSuchProviderException, KeyManagementException {
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			throw new IOException("文件不存在");
		}

		String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);

		URL urlObj = new URL(url);
		// 连接
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

		con.setRequestMethod("POST");
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false);

		// 设置请求头信息
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");

		// 设置边界
		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

		StringBuilder sb = new StringBuilder();
		sb.append("--");
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");

		byte[] head = sb.toString().getBytes("utf-8");

		// 获得输出流
		OutputStream out = new DataOutputStream(con.getOutputStream());
		// 输出表头
		out.write(head);

		// 文件正文部分
		// 把文件已流文件的方式 推入到url中
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();

		// 结尾部分
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线

		out.write(foot);

		out.flush();
		out.close();

		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		String result = null;
		try {
			// 定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}

		JSONObject jsonObj = JSONObject.parseObject(result);
		System.out.println(jsonObj);
		String typeName = "media_id";
		if (!"image".equals(type)) {
			typeName = type + "_media_id";
		}
		String mediaId = jsonObj.getString(typeName);
		return mediaId;
	}

	/**
	 * 获取accessToken
	 * 
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static AccessToken getAccessToken() throws ParseException, IOException {
		logger.info("获取AccessToken开始：");
		AccessToken token = new AccessToken();
		String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
		JSONObject jsonObject = doGetStr(url);
		if (jsonObject != null) {
			token.setToken(jsonObject.getString("access_token"));
			token.setExpiresIn(jsonObject.getIntValue("expires_in"));
		}
		logger.info(JsonMapper.toJsonString(token));
		return token;
	}

	/**
	 * 从微信服务器下载
	 * 
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public static String downloadFromWeChat(String mediaId) throws Exception {
		logger.info("从微信服务器下载开始……");
		String url = DOWNLOAD_IMG_URL.replace("ACCESS_TOKEN", getAccessToken().getToken()).replace("MEDIA_ID", mediaId);
		logger.info(url);
		DefaultHttpClient client = null;
		HttpGet httpGet = null;
		try {
			client = new DefaultHttpClient();
			httpGet = new HttpGet(url);
			HttpResponse httpResponse = client.execute(httpGet);
			logger.info("响应码：" + httpResponse.getStatusLine().getStatusCode());
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				return uploadToService(httpResponse);
			} else {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("从微信服务器下载失败：" + e.getMessage());
			return "";
		} finally {
			httpGet.releaseConnection();
		}
	}

	/**
	 * 上传图片到服务器
	 * 
	 * @param httpResponse
	 * @return
	 * @throws Exception
	 */
	public static String uploadToService(HttpResponse httpResponse) throws Exception {
		String fileName = httpResponse.getFirstHeader("Content-disposition").getValue();
		if (StringUtils.isNotEmpty(fileName) && fileName.indexOf("\"") != -1) {
			fileName = fileName.substring(fileName.indexOf("\"") + 1, fileName.lastIndexOf("\""));
			logger.info("从微信服务器下载成功：" + fileName);
		}
		String dateStr = "/upload/" + new SimpleDateFormat("yyyyMMdd").format(new Date());
		String path = "/usr/local/tomcat7/webapps" + dateStr;

		File file = new File(path);
		if (!file.exists())
			file.mkdirs();
		logger.info("保存的图片路径：" + path);

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
			fos = new FileOutputStream(path + "/" + fileName);
			fos.write(bos.toByteArray());
			logger.info("图片预览地址：" + PREVIEW_IMAGE_URL + dateStr + "/" + fileName);
			return PREVIEW_IMAGE_URL + dateStr + "/" + fileName;
		} catch (Exception e) {
			logger.info("保存图片到服务器失败：" + e.fillInStackTrace());
			e.printStackTrace();
			return "";
		} finally {
			is.close();
			fos.close();
		}
	}

	/**
	 * 获取wechatConfig
	 * 
	 * @return
	 * @throws Exception
	 */
	public static WechatConfig getWechatConfig(String url) throws Exception {
		System.out.println(UPLOAD_IMAGE_PATH);
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
	 * 获取WechatPayConfig
	 * 
	 * @return
	 * @throws Exception
	 */
	public static WechatPayConfig getWechatPayConfig(String orderNo, String openId, String amount, HttpServletRequest request) throws Exception {
		// amount = "0.01";
		WechatPayConfig config = new WechatPayConfig();
		config.setAppId(APPID);
		Long curTimeMillis = System.currentTimeMillis();

		String timestamp = Long.toString(curTimeMillis / 1000);
		config.setTimestamp(timestamp);
		String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
		config.setNonceStr(nonceStr);

		PayInfo payInfo = new PayInfo();
		payInfo.setAppid(APPID);
		payInfo.setMch_id(WEIXIN_MCH_ID);// 商户号
		payInfo.setDevice_info("WEB");// 设备号
		payInfo.setNonce_str(nonceStr);// 随机字符串
		payInfo.setBody("嘉宝易汇通商品");// 商品描述
		payInfo.setOut_trade_no(orderNo);// 商户订单号
		String val = "0";
		if (!StringUtils.isEmpty(amount)) {
			String amount2 = changeY2F(Double.parseDouble(amount));
			val = amount2.substring(0, amount2.indexOf("."));
		}
		payInfo.setTotal_fee(val);// 支付金额

		// payInfo.setSpbill_create_ip(InetAddress.getLocalHost().getHostAddress());//
		// 终端IP
		payInfo.setSpbill_create_ip(request.getLocalAddr());

		payInfo.setNotify_url(PAY_CALL_BACK);// 接收微信支付异步通知回调地址
		payInfo.setTrade_type("JSAPI");// 交易类型
		payInfo.setOpenid(openId);// 用户标识

		String start = DateFormatUtils.format(curTimeMillis, "yyyyMMddHHmmss");
		payInfo.setTime_start(start);// 交易开始时间

		String expire = DateFormatUtils.format(curTimeMillis + 30 * 60 * 1000, "yyyyMMddHHmmss");
		payInfo.setTime_expire(expire);// 交易结束时间

		payInfo.setSign(getSign(payInfo));// 签名

		logger.info(JsonMapper.toJsonString(payInfo));
		String paramesValue = MessageUtil.payInfoToXML(payInfo).replace("__", "_").replace("<![CDATA[", "").replace("]]>", "");

		logger.info("paramesValue:{}", paramesValue);

		Map<String, String> map = httpsRequestToXML(paramesValue);
		logger.info("调用统一下单接口返回参：" + map);
		if (!map.get("return_code").equals("SUCCESS")) {
			logger.info("调用微信统一下单接口失败：" + map.get("return_msg"));
			return null;
		}
		logger.info("调用微信统一下单接口成功：");
		logger.info(map.toString());
		String pkg = "prepay_id=" + map.get("prepay_id");
		config.setPkg(pkg);

		String str = "appId=" + APPID + "&nonceStr=" + nonceStr + "&package=" + config.getPkg() + "&signType=MD5" + "&timeStamp=" + timestamp
		// + "&time_start="+start+"&time_expire="+expire
				+ "&key=" + WEIXIN_KEY;
		logger.info("str:" + str);

		String paySign = MD5Util.MD5Encode(str, "UTF-8").toUpperCase();
		logger.info("paySign:" + paySign);
		config.setPaySign(paySign);

		return config;
	}

	/**
	 * 获取jsapiTicket
	 * 
	 * @return
	 * @throws Exception
	 */
	public static JsapiTicket getJsapiTicket() throws Exception {
		JsapiTicket jsapiTicket = new JsapiTicket();
		String url = JSAPI_TICKET_URL.replace("ACCESS_TOKEN", getAccessToken().getToken());
		logger.info("获取JsapiTicket开始：");
		JSONObject jsonObject = doGetStr(url);
		if (jsonObject != null) {
			jsapiTicket.setErrcode(jsonObject.getString("errcode"));
			jsapiTicket.setErrmsg(jsonObject.getString("errmsg"));
			jsapiTicket.setExpiresIn(jsonObject.getString("expires_in"));
			jsapiTicket.setTicket(jsonObject.getString("ticket"));
		}
		logger.info(JsonMapper.toJsonString(jsapiTicket));
		return jsapiTicket;
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

	/**
	 * 生产唯一用户名
	 * 
	 * @return
	 */
	public static String getUserName() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
		Date date = new Date();
		String newDate = sdf.format(date);
		String userName = newDate + RandomUtil.getRandom(5);
		return userName;
	}

	/**
	 * 通过code在腾讯服务器上换取access_token,以及openid
	 * 
	 * @param
	 * @return
	 */
	public static String queryWxuseridCallTX(String code) {
		String openid = null;
		try {
			if (StringUtils.isNotEmpty(code)) {
				if (logger.isDebugEnabled()) {
					logger.debug(" start to repace appid= {},appkey={}", APPID, APPSECRET);
				}

				String oatu2AccessTokenUrl = OAUTH2_ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET).replace("CODE", code);
				// 获取openid
				logger.info("获取OAuth授权openid开始=====" + oatu2AccessTokenUrl);
				JSONObject oatu2AccessTokenUrlObject = doGetStr(oatu2AccessTokenUrl);
				logger.info("获取openid接口返回值：" + oatu2AccessTokenUrlObject);
				logger.info("获取OAuth授权openid结束=====" + oatu2AccessTokenUrlObject.getString("openid"));
				openid = oatu2AccessTokenUrlObject.getString("openid");
			}
		} catch (Exception e) {
			logger.info("OAuth授权获取openid异常" + e);
		}
		return openid;
	}
    
	/**
	 * 通过code在腾讯服务器上换取access_token,以及openid
	 * 
	 * @param
	 * @return
	 */
	public static Map queryWxuseridCall(String code) {
		String openid = null;
	    String	accesstoken=null;
	    String refreshToken=null;
		Map<String,Object> map =new HashMap<String, Object>();
		try {
			if (StringUtils.isNotEmpty(code)) {
				if (logger.isDebugEnabled()) {
					logger.debug(" start to repace appid= {},appkey={}", APPID, APPSECRET);
				}

				String oatu2AccessTokenUrl = OAUTH2_ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET).replace("CODE", code);
				// 获取openid
				logger.info("获取OAuth授权openid开始=====" + oatu2AccessTokenUrl);
				JSONObject oatu2AccessTokenUrlObject = doGetStr(oatu2AccessTokenUrl);
				logger.info("获取openid接口返回值：" + oatu2AccessTokenUrlObject);
				logger.info("获取OAuth授权openid结束=====" + oatu2AccessTokenUrlObject.getString("openid"));
				logger.info("获取OAuth授权accesstoken结束=====" + oatu2AccessTokenUrlObject.getString("access_token"));
				logger.info("获取OAuth授权refreshToken结束=====" + oatu2AccessTokenUrlObject.getString("refresh_token"));
				openid = oatu2AccessTokenUrlObject.getString("openid");
				accesstoken=oatu2AccessTokenUrlObject.getString("access_token");
				refreshToken=oatu2AccessTokenUrlObject.getString("refresh_token");
				map.put("openid", openid);
				map.put("accesstoken", accesstoken);
				map.put("refreshToken", refreshToken);
			}
		} catch (Exception e) {
			logger.info("OAuth授权获取openid异常" + e);
		}
		return map;
	}

	/**
	 * 获取统一支付签名
	 * 
	 * @param payInfo
	 * @return
	 * @throws Exception
	 */
	public static String getSign(PayInfo payInfo) throws Exception {
		String signTemp = "appid=" + payInfo.getAppid() + "&body=" + payInfo.getBody() + "&device_info=" + payInfo.getDevice_info() + "&mch_id="
				+ payInfo.getMch_id() + "&nonce_str=" + payInfo.getNonce_str() + "&notify_url=" + payInfo.getNotify_url() + "&openid="
				+ payInfo.getOpenid() + "&out_trade_no=" + payInfo.getOut_trade_no() + "&spbill_create_ip=" + payInfo.getSpbill_create_ip()
				+ "&time_expire=" + payInfo.getTime_expire() + "&time_start=" + payInfo.getTime_start() + "&total_fee=" + payInfo.getTotal_fee()
				+ "&trade_type=" + payInfo.getTrade_type() + "&key=" + WEIXIN_KEY; // 商户号API安全的API密钥
		String sign = MD5Util.MD5Encode(signTemp, "UTF-8").toUpperCase();
		return sign;
	}

	/**
	 * 调用统一下单接口
	 * 
	 * @param requestUrl
	 * @param requestMethod
	 * @param outputStr
	 * @return
	 */
	public static Map<String, String> httpsRequestToXML(String outputStr) {
		Map<String, String> result = new HashMap<>();
		try {
			StringBuffer buffer = httpsRequest(WeixinUtil.WEIXIN_UNIFIED_ORDER, "POST", outputStr);
			result = MessageUtil.parseXml(buffer.toString());
		} catch (ConnectException ce) {
			logger.error("连接超时：" + ce.getMessage());
		} catch (Exception e) {
			logger.error("https请求异常：" + e.getMessage());
		}
		return result;
	}

	/**
	 * post请求调用统一下单接口
	 * 
	 * @param requestUrl
	 * @param requestMethod
	 * @param output
	 * @return
	 * @throws Exception
	 */
	public static StringBuffer httpsRequest(String requestUrl, String requestMethod, String output) throws Exception {
		StringBuffer buffer = new StringBuffer();
		try {
			URL url = new URL(requestUrl);
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setConnectTimeout(30000); // 设置连接主机超时（单位：毫秒)
			connection.setReadTimeout(30000); // 设置从主机读取数据超时（单位：毫秒)
			connection.setDoOutput(true); // post请求参数要放在http正文内，顾设置成true，默认是false
			connection.setDoInput(true); // 设置是否从httpUrlConnection读入，默认情况下是true
			connection.setUseCaches(false); // Post 请求不能使用缓存
			// 设定传送的内容类型是可序列化的java对象(如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestMethod(requestMethod);// 设定请求的方法为"POST"，默认是GET
			connection.setRequestProperty("Content-Length", requestUrl.length() + "");
			String encode = "utf-8";
			if (null != output) {
				OutputStream outputStream = connection.getOutputStream();
				outputStream.write(output.getBytes("UTF-8"));
				outputStream.flush();
				outputStream.close();
			}
			// 从输入流读取返回内容
			InputStream inputStream = connection.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			connection.disconnect();
		} catch (Exception e) {
			logger.info("post请求调用统一下单接口异常" + e);
		}
		return buffer;
	}

	/**
	 * 将元为单位的转换为分 （乘100）
	 * 
	 * @param amount
	 * @return
	 */
	public static String changeY2F(Double amount) {
		return BigDecimal.valueOf(amount).multiply(new BigDecimal(100)).toString();
	}

	/**
	 * 通过openID获取微信头像，昵称等基本信息 chengjian 2016-08-05
	 * 
	 * @param
	 * @return
	 */
	public static Map<String, Object> getWeChatInfo(String openId) {
		String nickname = "";
		String sex = "";
		String headimgurl = "";
		// String headimg = "";
		Map<String, Object> userInfo = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotEmpty(openId)) {
				// 获取微信签证
				AccessToken token = getAccessToken();
				String accessToken = token.getToken();
				String getUserInfoUrl = GETUSERINFO.replace("OPENID", openId).replace("ACCESS_TOKEN", accessToken);
				logger.info("获取微信头像，昵称等基本信息开始=====" + getUserInfoUrl);
				
				JSONObject getUserInfoUrlObject = doGetStr(getUserInfoUrl);
				logger.info("获取微信头像，昵称等基本信息返回值开始：" + getUserInfoUrlObject);
				logger.info("获取微信昵称=====" + getUserInfoUrlObject.getString("nickname"));
				logger.info("获取微信性别=====" + getUserInfoUrlObject.getString("sex"));
				logger.info("获取微信头像=====" + getUserInfoUrlObject.getString("headimgurl"));
				nickname = getUserInfoUrlObject.getString("nickname");
				sex = getUserInfoUrlObject.getString("sex");
				headimgurl = getUserInfoUrlObject.getString("headimgurl");
				logger.info("获取微信头像，昵称等基本信息END=====" + getUserInfoUrl);
				/*
				 * headimg = downloadFromWeChat(headimgurl); // 把微信头像上传打包服务器上面
				 * logger.info("获取微信头像保存本地数据库地址=====" +
				 * getUserInfoUrlObject.getString("headimgurl"));
				 */
			}
		} catch (Exception e) {
			logger.info("获取微信头像，昵称等基本信息异常" + e);
		}
		userInfo.put("nickname", nickname);
		userInfo.put("sex", sex);
		userInfo.put("headimgurl", headimgurl);
		userInfo.put("openId", openId);
		return userInfo;
	}
	
	
	/**
	 * 通过openID获取微信头像，昵称等基本信息 xiaojp 2016-08-05
	 * 
	 * @param
	 * @return
	 */
	public static Map<String, Object> getWeChatUserInfo(String openId,String accessToken,String reshToken) {
		String nickname = "";
		String sex = "";
		String headimgurl = "";
		// String headimg = "";
		String errcode="";
		String getUserInfoUrl="";
		JSONObject getUserInfoUrlObject;
		Map<String, Object> userInfo = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotEmpty(openId)) {
				AccessToken token = getAccessToken();
				accessToken = token.getToken();
				logger.info("accessToken========================"+accessToken);
				// 获取微信签证
				 getUserInfoUrl = GETUSERINFO.replace("OPENID", openId).replace("ACCESS_TOKEN", accessToken);
				logger.info("获取微信头像，昵称等基本信息开始=====" + getUserInfoUrl);
				 getUserInfoUrlObject = doGetStr(getUserInfoUrl);
				 logger.info("获取微信头像，昵称等基本信息编译开始=====" + getUserInfoUrlObject);
			if(getUserInfoUrlObject.getString("errcode")!=null){
				errcode=getUserInfoUrlObject.getString("errcode");
				if(errcode.equals("4001")){
					token=reshToken(reshToken);
					accessToken= token.getToken();
					 getUserInfoUrl = GETUSERINFO.replace("OPENID", openId).replace("ACCESS_TOKEN", accessToken);
					 logger.info("重新获取微信头像，昵称等基本信息开始=====" + getUserInfoUrl);
					 getUserInfoUrlObject = doGetStr(getUserInfoUrl);
					 logger.info("重新获取微信头像，昵称等基本信息返回值开始：" + getUserInfoUrlObject);
					 logger.info("重新获取微信昵称=====" + getUserInfoUrlObject.getString("nickname"));
					 logger.info("重新获取微信性别=====" + getUserInfoUrlObject.getString("sex"));
					 logger.info("重新获取微信头像=====" + getUserInfoUrlObject.getString("headimgurl"));
					 nickname = getUserInfoUrlObject.getString("nickname");
					 sex = getUserInfoUrlObject.getString("sex");
					 headimgurl = getUserInfoUrlObject.getString("headimgurl");
					 logger.info("重新获取微信头像，昵称等基本信息END=====" + getUserInfoUrl);
					 logger.info("重新获取微信头像，昵称等基本信息END=====" + getUserInfoUrlObject);
				}
			}else{
				logger.info("获取微信头像，昵称等基本信息返回值开始：" + getUserInfoUrlObject);
				logger.info("获取微信昵称=====" + getUserInfoUrlObject.getString("nickname"));
				logger.info("获取微信性别=====" + getUserInfoUrlObject.getString("sex"));
				logger.info("获取微信头像=====" + getUserInfoUrlObject.getString("headimgurl"));
				nickname = getUserInfoUrlObject.getString("nickname");
				sex = getUserInfoUrlObject.getString("sex");
				headimgurl = getUserInfoUrlObject.getString("headimgurl");
				logger.info("获取微信头像，昵称等基本信息END=====" + getUserInfoUrl);
				/*
				 * headimg = downloadFromWeChat(headimgurl); // 把微信头像上传打包服务器上面
				 * logger.info("获取微信头像保存本地数据库地址=====" +
				 * getUserInfoUrlObject.getString("headimgurl"));
				 */
					}
			  }
			
		} catch (Exception e) {
			logger.info("获取微信头像，昵称等基本信息异常" + e);
		}
		userInfo.put("nickname", nickname);
		userInfo.put("sex", sex);
		userInfo.put("headimgurl", headimgurl);
		userInfo.put("openId", openId);
		return userInfo;
	}
	
	
	public   static AccessToken reshToken(String REFRESHTOKEN)throws ParseException, IOException{
		logger.info("重新获取AccessToken开始：");
		AccessToken token = new AccessToken();
		String url = RESH_ACCESS_TOKEN_URL.replace("APPID", APPID).replace("REFRESH_TOKEN", REFRESHTOKEN);
		JSONObject jsonObject = doGetStr(url);
		if (jsonObject != null) {
			token.setToken(jsonObject.getString("access_token"));
			token.setExpiresIn(jsonObject.getIntValue("expires_in"));
		}
		logger.info(JsonMapper.toJsonString(token));
		return token;
	}
	
}
