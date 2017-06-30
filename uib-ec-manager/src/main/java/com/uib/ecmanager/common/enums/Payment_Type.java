package com.uib.ecmanager.common.enums;

public enum Payment_Type {
	

	/** 订单支付 */
	payment("订单支付","0"),

	/** 预存款充值 */
	recharge("预存款充值","1");

	
	// 成员变量
    private String description;
    private String index;
    
    private Payment_Type(String description,String index) {
		this.description = description;
		this.index = index;
	}
    
    public static String getDescription(String index){
    	for (Payment_Type t : Payment_Type.values()) {
            if (t.getIndex().equals(index) ) {
                return t.description;
            }
        }
        return null;
    }

	public  String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	
}

