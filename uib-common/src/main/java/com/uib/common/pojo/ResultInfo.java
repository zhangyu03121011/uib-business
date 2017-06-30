package com.uib.common.pojo;

import java.util.Map;

/**
 * 
 * @author gaven
 *
 */
public class ResultInfo {
	private String code; //错误编码

	private String msg;  //错误消息

	private boolean status = true; //false 时才有code   msg

	private Map<String,Object> data = null;

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

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
}
