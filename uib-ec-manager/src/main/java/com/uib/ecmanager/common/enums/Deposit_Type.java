package com.uib.ecmanager.common.enums;

/**
 * 所属类型
 */
public enum Deposit_Type {
	
	/** 会员充值 */
	memberRecharge("会员充值",0),

	/** 会员支付 */
	memberPayment("会员支付",1),

	/** 后台充值 */
	adminRecharge("后台充值",2),

	/** 后台扣费 */
	adminChargeback("后台扣费",3),

	/** 后台退款 */
	adminRefunds("后台退款",4);

	
	// 成员变量
    private String description;
    private int index;
    
    private Deposit_Type(String description,int index) {
		this.description = description;
		this.index = index;
	}
    
    public static String getDescription(int index){
    	for (Deposit_Type t : Deposit_Type.values()) {
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
