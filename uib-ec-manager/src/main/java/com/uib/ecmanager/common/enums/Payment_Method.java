package com.uib.ecmanager.common.enums;

public enum Payment_Method {
	/** 在线支付 */
	online("在线支付","0"),

	/** 线下支付 */
	offline("线下支付","1"),

	/** 预存款支付 */
	deposit("预存款支付","2"),
	
	/** 微信jsapi支付 */
	JSAPI("微信支付","4");

	
	// 成员变量
    private String description;
    private String index;
    
    private Payment_Method(String description,String index) {
		this.description = description;
		this.index = index;
	}
    
    public static String getDescription(String index){
    	for (Payment_Method t : Payment_Method.values()) {
            if (index.equals(t.getIndex())) {
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

