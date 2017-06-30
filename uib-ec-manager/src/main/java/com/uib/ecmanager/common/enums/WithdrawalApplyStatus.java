package com.uib.ecmanager.common.enums;

/**
 * 提现状态枚举类
 * @author：Godge
 * @date：2016年7月28日 下午13:47:05
 */
public enum WithdrawalApplyStatus {
	/** 未处理 */
	toDispose("未处理",0),
	
	/** 处理失败 */
	failure("处理失败",1),
	
	/** 处理成功 */
	success("处理成功",2);
	
	private String description;
	private int index;
	
	private WithdrawalApplyStatus(String description,int index){
		this.description = description;
		this.index = index;
	}
	
    public static String getDescription(int index){
    	for (WithdrawalApplyStatus t : WithdrawalApplyStatus.values()) {
            if (t.getIndex() == index) {
                return t.description;
            }
        }
        return null;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	
}
