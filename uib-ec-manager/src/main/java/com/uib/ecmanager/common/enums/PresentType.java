package com.uib.ecmanager.common.enums;

public enum PresentType {
	/** 注册赠送 */
	register_present("注册赠送",1),

	/** 购买赠送 */
	buy_present("购买赠送",2),
	
	/** 活动赠送 */
	activities_present("活动赠送",3);

	
	// 成员变量
    private String description;
    private int index;
    
    private PresentType(String description,int index) {
		this.description = description;
		this.index = index;
	}
    
    public static String getDescription(int index){
    	for (PresentType t : PresentType.values()) {
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
