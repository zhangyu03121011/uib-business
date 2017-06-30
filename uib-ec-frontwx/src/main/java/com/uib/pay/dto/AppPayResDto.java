package com.uib.pay.dto;

/**
 * 手机控件支付返回实体类
 * @author kevin
 *
 */
public class AppPayResDto {

	/** 0: 成功, 1: 失败,2:订单已存在,3:系统错误 */
	private String code;

	/** 消息备注 */
	private String msg;

	/** 返回 返回手机控件 tn */
	private String tn;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getTn() {
		return tn;
	}

	public void setTn(String tn) {
		this.tn = tn;
	}
	
	
	

}
