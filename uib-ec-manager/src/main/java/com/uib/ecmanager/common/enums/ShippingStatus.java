package com.uib.ecmanager.common.enums;


public enum ShippingStatus {
	/** 未发货  */
	unshipped("未发货 ",0),

	/** 部分发货*/
	partialShipment("部分发货 ",1),

	/** 已发货*/
	shipped("已发货 ",2),
	
	/** 部分退货 */
	partialReturns("部分退货",3),
	
	/** 已退货  */
	returned("已退货",4),

	/** 空值  */
	statusNull("",5);
	// 成员变量
    private String description;
    private int index;
    
    private ShippingStatus(String description,int index) {
		this.description = description;
		this.index = index;
	}
    
    public static String getDescription(int index){
    	for (ShippingStatus t : ShippingStatus.values()) {
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
