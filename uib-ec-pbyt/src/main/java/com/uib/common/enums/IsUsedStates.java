package com.uib.common.enums;

public enum IsUsedStates {
	/** 未使用 */
	Unused("未使用", 0),

	/** 已使用 */
	Used("已使用", 1),
	
	/** 已锁定 */
	Lock("已锁定", 2);


	// 成员变量
	private Integer index;
	private String value;

	private IsUsedStates(String description, Integer index) {
		this.value = description;
		this.index = index;
	}

	public static String getDescription(Integer index) {
		for (IsUsedStates t : IsUsedStates.values()) {
			if (t.getIndex().equals(index)) {
				return t.value;
			}
		}
		return null;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
