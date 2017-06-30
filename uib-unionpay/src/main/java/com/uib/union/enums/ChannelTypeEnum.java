package com.uib.union.enums;

/**
 * 渠道类型枚举
 * @author kevin
 *
 */
public enum ChannelTypeEnum {
	
	/**
	 * 语音
	 */
	VOICE("05"),
	
	/**
	 * 互联网
	 */
	INTERNET("07"),
	
	/**
	 * 移动
	 */
	MOBILE("08");
	
	private String desc;
	
	
	
	
	public String getDesc() {
		return desc;
	}




	public void setDesc(String desc) {
		this.desc = desc;
	}




	private ChannelTypeEnum(String desc){
		this.desc = desc;
	}

}
