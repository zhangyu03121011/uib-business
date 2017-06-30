package com.uib.common.bean;

public class ParamBean {
	private String paramKey;	//参数键
	private Object paramValue;	//参数值
	
	public ParamBean() {
		// TODO Auto-generated constructor stub
	}

	public ParamBean(String paramKey, Object paramValue) {
		super();
		this.paramKey = paramKey;
		this.paramValue = paramValue;
	}

	public String getParamKey() {
		return paramKey;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	public Object getParamValue() {
		return paramValue;
	}

	public void setParamValue(Object paramValue) {
		this.paramValue = paramValue;
	}
	
}
