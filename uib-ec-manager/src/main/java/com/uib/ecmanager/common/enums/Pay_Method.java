package com.uib.ecmanager.common.enums;

import java.util.ArrayList;
import java.util.List;

public enum Pay_Method {
	/** 在线支付 */
	online("在线支付",0),

	/** 线下支付 */
	offline("线下支付",1);
	
	// 成员变量
    private String description;
    private int index;
    
    private Pay_Method(String description,int index) {
		this.description = description;
		this.index = index;
	}
    
    public static String getDescription(int index){
    	for (Pay_Method t : Pay_Method.values()) {
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

	public  int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
