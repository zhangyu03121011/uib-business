package com.uib.ptyt.constants;
/**
 * 微信常量   
 * @author chengjian
 * @date   2016年08月03日
 * @time   下午1:34:22
 */
public interface WechatConstant {
	//用户名
	public String USER_NAME = "userName";
	
	//用户id
	public String USER_ID = "userId";
	
	//openid
	public String OPEN_ID = "openId";
	
	String USER = "user";

	String USER_MERCHANT = "merchant";
	
	String MERCHANT_ID = "merchantId";
	//用户等级
	String RANK_ID = "rankId";
	
	//用户手机号码
	public String wx_user_phone = "phone";
	
	//验证码1
	public String wx_mess_code_1 = "mess_code_1";
	
	//验证码2
	public String wx_mess_code_2 = "mess_code_2";
	
	//会员信息
	public String wx_member_info =  "wx_member_info";
	
	//C端用户商品
	String product_type_1 = "1";
	//B端用户分享的商品
	String product_type_2 = "2";
	
	/**
	 * 普通消费方
	 */
	String USER_TYPE_C = "1";
	/**
	 * 普通消费方
	 */
	String USER_TYPE_C2 = "3";
	/**
	 * 申请成为代理分销商
	 */
	String USER_TYPE_B = "2";
	
	/**
	 * 待审核分销商
	 */
	String APPROVE_FLAG_0 = "0";
	
	/**
	 * 审核通过分销商
	 */
	String APPROVE_FLAG_1 = "1";
	
	/**
	 * 审核失败分销商
	 */
	String APPROVE_FLAG_2 = "2";
	
	/**
	 * 订单来源于平台购买
	 */
	String order_type_0 = "0";
	
	/**
	 * 订单来源于推荐人分享购买
	 */
	String order_type_1 = "1";
}


