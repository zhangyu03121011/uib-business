package com.uib.ecmanager.common.enums;


public enum Log_Type {
		/** 订单创建 */
		create("订单创建",0),

		/** 订单修改 */
		modify("订单修改",1),

		/** 订单确认 */
		confirm("订单确认",2),

		/** 订单支付 */
		payment("订单支付",3),

		/** 订单退款 */
		refunds("订单退款",4),

		/** 订单发货 */
		shipping("订单发货",5),

		/** 订单退货 */
		returns("订单退货 ",6),

		/** 订单完成 */
		complete("订单完成",7),

		/** 订单取消 */
		cancel("订单取消",8),

		/** 其它 */
		other("其它",9);
		
		// 成员变量
        private String description;
        private int index;
        
        private Log_Type(String description,int index) {
			this.description = description;
			this.index = index;
		}
        
        public static  String getDescription(int index){
        	for (Log_Type t : Log_Type.values()) {
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
