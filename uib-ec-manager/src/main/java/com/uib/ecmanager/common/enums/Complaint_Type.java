package com.uib.ecmanager.common.enums;

/**
 * 投诉类型枚举类
 * @author luogc
 *
 */
public enum Complaint_Type {
	/** 商品 */
	goods("商品",1),

	/** 服务 */
	service("服务",2),
	
	/** 其它 */
	others("其它",3);

	
	// 成员变量
    private String description;
    private int index;
    
    private Complaint_Type(String description,int index) {
		this.description = description;
		this.index = index;
	}
    
    public static String getDescription(int index){
    	for (Complaint_Type t : Complaint_Type.values()) {
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
