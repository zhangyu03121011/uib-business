package com.uib.ecmanager.common.enums;



public enum OrderStatus {
	/** 未确认 */
	unconfirmed("未确认","0"),

	/** 已确认 */
	confirmed("已确认","1"),

	/** 已完成 */
	completed("已完成","2"),
	
	/** 已取消 */
	cancelled("已取消","3"),
	
	/** 等待付款 */
	wait_pay("等待付款","4"),
	
	/**已付款  等待发货 */
	paid_shipped("已付款等待发货","5"),
	/** 等待收货 */
	shipped("等待收货","6"),
	
	/** 已签收 */
	signfor("已签收","7"),
	
	/** 退货中 */
	returnloding("退货中","8"),
	
	/** 已退货 */
	returned("已退货","9");

	
	// 成员变量
    private String description;
    private String index;
    
    private OrderStatus(String description,String index) {
		this.description = description;
		this.index = index;
	}
    
    public static String getDescription(String index){
    	for (OrderStatus t : OrderStatus.values()) {
            if (t.getIndex().equals(index)) {
                return t.description;
            }
        }
        return null;
    }
    
    public static boolean CheckOrderStatusCode(String index){
    	for (OrderStatus t : OrderStatus.values()) {
            if (t.getIndex().equals(index) ) {
                return true;
            }
        }
    	return false;
    }
    
    public static OrderStatus getStatusByIndex(String index){
    	for (OrderStatus t : OrderStatus.values()) {
            if (t.getIndex().equals(index) ) {
                return t;
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


	
	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}
    
}
