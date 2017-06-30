package com.uib.ecmanager.common.enums;


/**
 * 方式
 */
public enum Refunds_Method {
	
	/** 在线支付 */
	online("在线支付",0),

	/** 线下支付 */
	offline("线下支付",1),

	/** 预存款支付 */
	deposit("预存款支付",2);

	
	// 成员变量
    public String description;
    private int index;
    
    private Refunds_Method(String description,int index) {
		this.description = description;
		this.index = index;
	}
    
    public static String getDescription(int index){
    	for (Refunds_Method t : Refunds_Method.values()) {
            if (t.getIndex() == index) {
                return t.description;
            }
        }
        return null;
    }
	public   String getDescription() {
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
