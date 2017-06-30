package com.uib.ecmanager.common.enums;

public enum OrderSource {
	/** PC*/
	PC("PC","0"),

	/** App */
	App("App","1"),

	/** 微信 */
	weixin("微信","2");
	
	// 成员变量
    private String description;
    private String index;
    
    private OrderSource(String description,String index) {
		this.description = description;
		this.index = index;
	}
    
    public static String getDescription(String index){
    	for (OrderSource t : OrderSource.values()) {
            if (t.getIndex().equals(index)) {
                return t.description;
            }
        }
        return null;
    }
	public String getDescription() {
		return description;
	}
	
	public static OrderSource getOrderSource(String index){
		for (OrderSource t : OrderSource.values()) {
            if (t.getIndex().equals(index)) {
                return t;
            }
        }
        return null;
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
