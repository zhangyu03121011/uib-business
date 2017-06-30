package com.uib.ecmanager.common.enums;

/**
 * 会员审核状态枚举类
 * @author：George Rom
 * @date：2016年7月15日 上午10:05:05
 */
public enum ApproveFlag {
	/** 待审核 */
	toApprove("待审核",0),
	
	/** 审核成功 */
	success("审核成功",1),
	
	/** 审核失败 */
	failure("审核失败",2);
	
	private String description;
	private int index;
	
	private ApproveFlag(String description,int index){
		this.description = description;
		this.index = index;
	}
	
    public static String getDescription(int index){
    	for (ApproveFlag t : ApproveFlag.values()) {
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
