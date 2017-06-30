package com.uib.common.web;

import java.io.InputStream;
import java.util.Properties;


public class Constants {
	
	// For localhost
	public  String HMD5_PASSWORD ="";
	public  String SIGN_TYPE="";
	public  String HMD5_PASSWORD_B2B ="";
	public  String SIGN_TYPE_B2B="";
	public  String KEY_PATH="D:\\work\\SMWorkSpace\\shopDemo\\WebContent\\cert\\shanghu1.pfx";//证书路径
	public  String KEY_PASSWORD="1";//证书保护密码
	public static  String PUBLIC_CERT_PATH="D:\\work\\SMWorkSpace\\shopDemo\\WebContent\\cert\\ttf.cer";//证书路径
	
	public  String PAY_URL="";//支付请求URL

	public  String ORDER_QUERY_URL="";//订单查询URL
	
	public  String MER_RETURN_URL="";//支付返回URL
	public  String MER_NOTIFY_URL="";//支付通知URL
	
	


	static Constants constants;
	
	public Constants(){
		Properties props = new Properties();
      try {
       InputStream in = getClass().getResourceAsStream("/system.properties");
      		 //new BufferedInputStream (new FileInputStream("load.properties"));
       props.load(in);
       
       //B2C
       HMD5_PASSWORD = props.getProperty ("hmd5_password");
      } catch (Exception e) {
      	System.out.println("=====================加载参数失败===============================");
      	System.out.println("失败原因"+e.getMessage());
      }
	}
	
	public static Constants getInstent(){
		if(constants!=null){
			return constants;
		} else {
			constants = new Constants();
			return constants;
		}
	}
}
