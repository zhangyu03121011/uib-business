/****************
	订单 - 待收货
****************/

var orderDelivery = {};

/**
 * 初始化表格
 */
orderDelivery.initTable = function(){
	$.get(rootPath + "/wechat/member/order/query",{orderStatus:"6"},function(result){
		if(result.status){
			var str = orderDelivery.orderListtemple(result.data);
	        $(".order-list").empty();
			$(".order-list").append(str);
		}
	});

};

orderDelivery.orderListtemple = function(data) {
	var str = "";
	for(var i = 0; i < data.length; i ++){
		var order = data[i];
		var orderStatus = "待收货";
		var $s = " <div class='payment-btn'><a href='javascript:void(0)' onclick='confirmReceive(\""+order.orderNo+"\")'>确认收货</a></div>";
		
		str += "<li>";
		
		str += "        <div class='status-title'>";
		str += $s;
		str += "    <a href='order-detail.html?orderNo=" + order.orderNo+"'>";
		str += "        	<p>状态：<span class='pink'>"+orderStatus+"</span></p>";
		str += "        	<p>总价：<span class='pink'>¥" + order.amount + "</span></p>";
		str += "    </a>";
		str += "        </div>";
		
		
		for(var j = 0; j < order.list_ordertable_item.length; j ++){
			var product = order.list_ordertable_item[j];
			str += "<a href='products.html?id=" + product.goodsNo + "'>";
			str += "   	<div class='wbox'>";
			str += "    	<div class='img'><img src='" + product.goodsNo + "'/></div>";
			str += "    	<div class='wbox-flex'>";
			str += "         	<p class='name'>" + product.fullName + "</p>";
			str += "         	<div class='price'><font class='fr'>X"+product.quantity+"</font><span class='fl'>" + formatPrice(product.price) + "</span>";
			str += "         	</div>";
			str += "    	</div>";
			str += " 	</div>";
			str += "</a>";
		}
		
		str += "</li>";
	}
	return str;
};

/**
 * 加载页面
 */
$(document).ready(function(){
	var callBack = URL_PARAM.getParam("orderPaymentCallback");
	if (callBack) {
		$("#orderDelivery").click();
	}
});


function toOrderDetail(orderNo){
	window.location.href = "order-detail.html?orderNo="+orderNo;
}


function confirmReceive(orderNo){
	if(confirm("是否确认收货？")){
		$.ajax({  
	        url : rootPath + "/wechat/member/order/update/status",  
	        data : {orderNo:orderNo, orderStatus:1},
	        async : false, 
	        type : "POST",  
	        success : function(result) {
	        	if(result.status){
	        		Alert.disp_prompt('订单确认收货成功');
//	        		setTimeout(function(){
//	        			location.href = "order.html";
//	        		},2000);
	        		orderDelivery.initTable();
	        	}else{
	        		Alert.disp_prompt(result.msg);
	        	}
	        }  
	    });
	}
}
