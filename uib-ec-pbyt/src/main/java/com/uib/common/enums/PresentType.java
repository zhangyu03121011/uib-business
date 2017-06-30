package com.uib.common.enums;

public enum PresentType {
	/** 注册赠送 */
	register_present("注册赠送", "1"),

	/** 购买赠送 */
	buy_present("购买赠送", "2");

	// 成员变量
	private String index;
	private String value;

	private PresentType(String description, String index) {
		this.value = description;
		this.index = index;
	}

	public static String getDescription(String index) {
		for (PresentType t : PresentType.values()) {
			if (t.getIndex().equals(index)) {
				return t.value;
			}
		}
		return null;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
