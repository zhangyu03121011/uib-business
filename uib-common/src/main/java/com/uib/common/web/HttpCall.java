package com.uib.common.web;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


/**
 * 封装HTTP请求
 * 
 * @author wanghuan 2014年08月29日
 */
public class HttpCall {
    private static Log logger = LogFactory.getLog(HttpCall.class);

    private final static String DEFAULT_CHARACTER_ENCODING = "UTF-8";

    /**
     * 发送POST请求
     * 
     * @param url
     * @param params
     * @return
     */
    public static HttpCallResult post(String url, Map<String, String> params) {
        return post(url, params, "");
    }
    
    /**
     * 发送GET请求
     * 
     * @param url
     * @param params
     * @return
     */
    public static HttpCallResult get(String url) {
        
    	//输出日志
        logger.info("接口(GET)：" + url);
    	
    	HttpCallResult result = new HttpCallResult();
        HttpClientBuilder builder = HttpClientBuilder.create();
        CloseableHttpClient client = builder.build();
        HttpResponse response = null;
        try {
            HttpGet get = new HttpGet(url);
            get.setConfig(buildDefaultRequestConfig());
            response = client.execute(get);
            if (response != null) {
                result.setContent(EntityUtils.toString(response.getEntity()));
                result.setStatusCode(response.getStatusLine().getStatusCode());
            }
            //输出结果
	        logger.info("结果：" + result.getStatusCode());
        } catch (Exception e) {
        	logger.error(e.getMessage());
            result.setStatusCode(999);
        } finally {
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                	logger.error(e.getMessage());
                }
            }
        }
        return result;
    }

    /**
     * 发送POST请求
     * 
     * @param url
     * @param params
     * @param encoding
     * @return
     */
    public static HttpCallResult post(String url, Map<String, String> params, String encoding) {
    	
    	//输出日志
        logger.info("接口(POST)：" + url);

        if (StringUtils.isEmpty(encoding)) {
            encoding = DEFAULT_CHARACTER_ENCODING;
        }
        HttpCallResult result = new HttpCallResult();
        HttpClientBuilder builder = HttpClientBuilder.create();
        CloseableHttpClient client = builder.build();
        
        HttpResponse response = null;
        try {
            HttpPost post = new HttpPost(url);
            post.setConfig(buildDefaultRequestConfig());
            List<NameValuePair> nvps = map2NameValuePair(params);
            post.setEntity(new UrlEncodedFormEntity(nvps, encoding));
            response = client.execute(post);
            if (response != null) {
                result.setContent(EntityUtils.toString(response.getEntity()));
                result.setStatusCode(response.getStatusLine().getStatusCode());
            }
            //输出结果
	        logger.info("结果：" + result.getStatusCode());
        } catch (Exception e) {
        	logger.error(e.getMessage());
            result.setStatusCode(999);
        } finally {
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                	logger.error(e.getMessage());
                }
            }
        }
        return result;
    }

    /**
     * 异步发送HTTP请求
     * 
     * @param url
     * @param params
     * @return
     */
    public static HttpCallResult postAsync(String url, Map<String, String> params) {
        return post(url, params, "");
    }

    /**
     * 异步发送POST请求
     * 
     * @param url
     * @param params
     * @param encoding
     * @return
     */
    public static HttpCallResult postAsync(String url, Map<String, String> params, String encoding) {
        if (StringUtils.isEmpty(encoding)) {
            encoding = DEFAULT_CHARACTER_ENCODING;
        }
        HttpCallResult result = new HttpCallResult();

        CloseableHttpAsyncClient client = HttpAsyncClients.custom().setDefaultRequestConfig(buildDefaultRequestConfig()).build();
      
        HttpResponse response = null;
        try {
            HttpPost post = new HttpPost(url);
            List<NameValuePair> nvps = map2NameValuePair(params);
            post.setEntity(new UrlEncodedFormEntity(nvps, encoding));
            client.start();
            Future<HttpResponse> future = client.execute(post, null);
            response = future.get();
            if (response != null) {
                result.setContent(EntityUtils.toString(response.getEntity()));
                result.setStatusCode(response.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
        	logger.error(e.getMessage());
            result.setStatusCode(999);
        } finally {
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                	logger.error(e.getMessage());
                }
            }
        } 

        return result;
    }

    public static String postJosn(String json, String url) {

        byte[] json2Bytes = json.getBytes();
        InputStream instr = null;
        java.io.ByteArrayOutputStream out = null;
        try {
            URL URL = new URL(url);
            URLConnection urlCon = URL.openConnection();
            urlCon.setDoOutput(true);
            urlCon.setDoInput(true);
            urlCon.setUseCaches(false);
            urlCon.setRequestProperty("Content-Type", "text/xml");
            urlCon.setRequestProperty("Content-length", String.valueOf(json2Bytes.length));
            DataOutputStream printout = new DataOutputStream(urlCon.getOutputStream());
            printout.write(json2Bytes);
            printout.flush();
            printout.close();
            instr = urlCon.getInputStream();
            byte[] bis = IOUtils.toByteArray(instr);
            String responseString = new String(bis, "UTF-8");
            if ((responseString == null) || ("".equals(responseString.trim()))) {
                
            }
            return responseString;

        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        } finally {
            try {
                out.close();
                instr.close();

            } catch (Exception ex) {
                return "0";
            }
        }

    }

    /**
     * 
     * @return
     */
    private static RequestConfig buildDefaultRequestConfig() {
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).setConnectionRequestTimeout(30000).build();
        return requestConfig;
    }

    /**
     * 组装参数
     * 
     * @param params
     * @return
     */
    private static List<NameValuePair> map2NameValuePair(Map<String, String> params) {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (MapUtils.isNotEmpty(params)) {
            Iterator<String> it = params.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                String value = params.get(key);
                if (StringUtils.isNotEmpty(value)) {
                    NameValuePair nvp = new BasicNameValuePair(key, value);
                    logger.debug(">>HttpCall params:->" + key + "    =   " + value);
                    nvps.add(nvp);
                }
            }
        }
        return nvps;
    }

}
