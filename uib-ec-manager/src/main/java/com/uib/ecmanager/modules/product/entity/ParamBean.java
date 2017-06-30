package com.uib.ecmanager.modules.product.entity;

public class ParamBean<T> {
	private String paramKey;	//参数键
	private T paramValue;	//参数值
	
	public ParamBean() {
		// TODO Auto-generated constructor stub
	}

	public ParamBean(String paramKey, T paramValue) {
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

	public void setParamValue(T paramValue) {
		this.paramValue = paramValue;
	}
	
}
