/****************
	销售、隐私、退货政策
****************/



/**
 * 初始化表格
 *//*
obj.initTable = function(orderNo){
	if(!isNull(orderNo)){
		$.get(rootPath + "/wechat/member/order/queryOrder?orderNo=" + orderNo,"",function(result){
			if(result.status){
				var order = result.data;
				$("#orderStatus").text(formatOrderStatus(order.orderStatus));
				$("#orderNo").text(order.orderNo);
				$("#createDate").text(order.createDate);
				$("#address").text(order.areaName.replace(new RegExp(/(-)/g),'') + order.address);
				$("#consignee").text(order.consignee + '  ' + order.phone);
				$("#shippingMethod").text(order.shippingMethodName);
				if(order.couponDiscount > 0){
					$("#coupon_p_span").text('-' + formatPrice(order.couponDiscount));
				}
				var str = "";
				for(var i = 0; i < order.list_ordertable_item.length; i++){
					var product = order.list_ordertable_item[i];
					str += "    <a href='products.html?id="+product.goodNo+"'>";
					str += "        <div class='wbox'>";
					str += "        	<div class='img'><img src='" + product.thumbnail + "'/></div>";
					str += "        	<div class='wbox-flex'>";
					str += "            	<p class='name'>" + product.name + "</p>";
					str += "            	<div class='price'><font class='fr'>X"+product.quantity+"</font><span class='fl'>" + formatPrice(product.price) + "</span>";
					str += "            	</div>";
					str += "        	</div>";
					str += "    	</div>";
					str += "    </a>";
				}
				$("#product-list").empty();
				$("#product-list").append(str);
				$("#productAmount").text(formatPrice(order.productAmount));
				if(!isNull(formatPrice(order.freight))){
					$("#freight").text(formatPrice(order.freight));
				}
				$("#amount").text(formatPrice(order.amount));
			}
		});
	}
};*/

/**
 * 加载页面
 */
$(document).ready(function(){
	var cmsCategoryNo = URL_PARAM.getParam("cmsCategoryNo");
	$.get(rootPath + "/wechat/CmsCategory/listCmsRegister?cmsCategoryNo=" + cmsCategoryNo,"",function(result){
		if(null!=result.data){
			$('title').text(result.data.title);
			$("#p").text(result.data.content);
		}
		
	});
});
