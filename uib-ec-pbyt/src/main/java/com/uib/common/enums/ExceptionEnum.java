/**
 * Copyright &copy; 2014-2016  All rights reserved.
 *
 * Licensed under the 深圳嘉宝易汇通科技发展有限公司 License, Version 1.0 (the "License");
 * 
 */
package com.uib.common.enums;

/**
 * @ClassName: ExceptionEnum
 * @Description: 异常枚举类错误代码
 * @author sl
 * @date 2015年10月13日 下午4:55:09
 */
public enum ExceptionEnum {
	/** 系统内部异常 */
	system_error("系统类部异常","500"),
	
	/** 参数不能为空 */
	param_not_null("参数不能为空", "1001"), 
	/** 未登录 */
	not_login("未登录", "1002"), 
	/** 该会员已存在 */
	member_exist("该会员已存在", "1003"),
	/** 该会员不存在 */
	member_not_exist("该会员不存在","1004"),
	/** 密码错误 */
	pass_error("密码错误","1005"),
	/** 手机号为空 */
	phone_is_null("手机号为空","1006"),
	/** 该手机号已注册 */
	phone_is_register("该手机号已注册","1007"),
	/** 该手机没有被注册 */
	phone_no_register("该手机没有被注册","1008"),
	/** 两次输入密码不一致 */
	password_different("两次输入密码不一致","1009"),
	/** 验证码不正确 */
	validate_code_error("验证码不正确","1010"),
	/** 验证码超时 */
	validate_code_timeout("验证码超时请重新获取","1011"),
	/** 验证码未超过120秒不能重新发送 */
	validate_code_noReady_send("验证码未超过120秒不能重新发送","1012"),
	
	/** 原始密码输入错误 */
	old_password_error("原始密码输入错误","1013"),
	/**
	 * 非预授权用户
	 */
	member_not_pre_auth("非预授权用户", "1015"),
	
	/** 同一用户两台app同时登录 */
	member_repetition_login("当前用户已在另一终端登录", "1016"), 
	
	/** 更新手机号码错误 */
	member_updatePhone_error("更新手机号码错误", "1017"), 
	
	/** 参数非法 */
	param_illegal("参数非法","2001"),
	/** 商品已下架 */
	product_undercarriage("商品已下架","2002"),
	/** 商品不存在 */
	product_not_exist("商品不存在","2003"),
	/** 商品为赠品 */
	product_is_gift("商品为赠品","2004"),
	/** 商品项超过购物车允许值 */
	cartItems_size_exceed("商品项超过购物车允许值","2005"),
	/** 商品数量超过允许值 */
	cartItem_count_exceed("商品数量超过允许值","2006"),
	/** 商品库存不足 */
	product_Low_stock("商品库存不足","2007"),
	/** 业务逻辑异常 */
	business_logic_exception("业务逻辑异常","2008"),
	/** 购物车为空 */
	cartItems_is_blank("购物车商品不存在或重复下单，请重新加入购物车","2009"),
	/** 购物车不存在 */
	cart_is_null("购物车不存在","2010"),
	/**无货，此商品不支持配送至该地区！*/
	cart_area_product_null("无货,此商品不支持配送至该地区！","2012"),
	
	order_product_area_is_notExists("您的订单中，部分商品配送范围无法覆盖您选择的收货地址，请更换其它商品!","2013"),
	
	/** 订单不存在 */
	order_not_exist("订单不存在","2011"),
	/** id不存在 */
	id_not_exist("id不存在","3001"),
	/** 地址不存在 */
	address_not_exist("地址不存在","3002"),
	/** 区域不存在 */
	area_not_exist("区域不存在","3003"),
	/** 区域名称不存在 */
	areaName_not_exist("区域名称不存在","3004"),
	/** 手机号不存在 */
	phone_not_exist("手机号不存在","3005"),
	/** 联系人不存在 */
	consignee_not_exist("联系人不存在","3006"),
	/** 查询默认地址为空 */
	list_default_null("查询默认地址为空","3007"),
	/** 查询收货地址为空 */
	list_address_null("查询收货地址为空","3008"),
	/** 查询区域为空 */
	list_area_null("查询区域为空","3009"),
	/** 订单金额不存在 */
	orderAmount_not_exist("订单金额不存在","3010"),
	/** 订单金额与传入金额不同 */
	orderAmount_different("订单金额与传入金额不同","3011"),
	/** 商品价格已改动 */
	product_price_different("商品价格已改动","3012"),
	/** 栏目编号不存在 */
	cmsCategoryNo_not_exist("栏目编号不存在","3013"),
	/** 商品评价不存在 */
	comment_not_exist("商品不存在","3014"),
	/** 佣金不存在 */
	commission_not_exist("佣金不存在","3015"),
	
	list_complaint_null("投诉记录为空","3016"),
	
	mess_code_type_error("手机验证码类型错误","3017"),
	/** 未设置支付密码 */
	no_set_pay_password("未设置支付密码","3018"),
	/** 审核成功或者审核中 */
	approve_passed("审核成功或者审核中","3019"),
	
	/** 该银行卡号已存在 */
	cardNo_is_exist("该银行卡号已存在 ","3020"),
	/** 当前用户的身份是消费者 */
	member_consumer("当前用户的身份是消费者 ","3021"),
	/** 获取当前用户的openId为空 */
	openId_is_null("获取当前用户的openId为空 ","3022"),
	/** 该手机号已存在 */
	phone_is_exist("该手机号已存在 ","3023"),
	/** 该商户已存在 */
	merchat_is_exist("该商户已存在 ","3024")
	;

	private String value;
	private String index;

	private ExceptionEnum(String value, String index) {
		this.value = value;
		this.index = index;
	}

	public static String getvalue(String index) {
		for (ExceptionEnum t : ExceptionEnum.values()) {
			if (t.getIndex().equals(index)) {
				return t.value;
			}
		}
		return null;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

}
