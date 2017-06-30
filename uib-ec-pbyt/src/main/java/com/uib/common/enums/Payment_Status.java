package com.uib.common.enums;

public enum Payment_Status {

	/** 等待支付 */
	wait("等待支付",0),

	/** 支付成功 */
	success("支付成功",1),

	/** 支付失败 */
	failure("支付失败",2);

	
	// 成员变量
    private String description;
    private int index;
    
    private Payment_Status(String description,int index) {
		this.description = description;
		this.index = index;
	}
    
    public static String getDescription(int index){
    	for (Payment_Status t : Payment_Status.values()) {
            if (t.getIndex() == index) {
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

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
    
}

