package com.uib.weixin.service;


public interface MyCenterService {
	
	/**
	 * 关注保存用户信息
	 * @param toUserName
	 */
	public void weixinRegister(String fromUserName);
	
	/**
	 * 取消关注保存用户信息
	 * @param toUserName
	 */
	public void unSubscribe(String fromUserName);
}
