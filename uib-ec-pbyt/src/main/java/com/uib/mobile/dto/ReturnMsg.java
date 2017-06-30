package com.uib.mobile.dto;

/**
 * 手机返回JSON信息
 * 
 * @author kevin
 *
 */
public class ReturnMsg<T> {

	
	private String code; //错误编码

	private String msg;  //错误消息

	private boolean status = true; //false 时才有code   msg

	private T data;

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

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
