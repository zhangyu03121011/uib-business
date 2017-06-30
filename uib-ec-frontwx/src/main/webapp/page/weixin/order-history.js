/****************
	订单 - 历史
****************/

var orderHistory = {};


/**
 * 初始化表格
 */
orderHistory.initTable = function(){
	$.get(rootPath + "/wechat/member/order/query",{orderStatus:""},function(result){
		var str = "";
		if(result.status){
			for(var i = 0; i < result.data.length; i ++){
				var order = result.data[i];
				if(order.orderStatus=="2" || order.orderStatus=="3"){
					var status = "已完成";
					if(order.orderStatus==3){
						status = "已取消";
					}
					str += "<li>";
					str += "        <div class='status-title'  onclick='toOrderDetail(\""+order.orderNo+"\")' >";
					str += "			<div>";
					str += "        		<p>状态：<span class='pink'>" + status + "</span></p>";
					str += "        		<p>总价：<span class='pink'>¥" + order.amount + "</span></p>";
					str += "        	</div>";
					str += "        </div>";
					str += "		<div class='payment-btn'><a href='javascript:void(0)' onclick='deleteOrder(\""+order.orderNo+"\")'>删除订单</a></div>";
					
					for(var j = 0; j < order.list_ordertable_item.length; j ++){
						var product = order.list_ordertable_item[j];
						str += "        <div class='wbox'>";
						str += "        	<div class='img'><a href='products.html?id="+ product.goodsNo + "'><img src='" + product.thumbnail + "'/></a></div>";
						str += "        	<div class='wbox-flex'>";
						str += "				<a href='products.html?id="+ product.goodsNo + "'>";
						str += "            		<p class='name'>" + product.fullName + "</p>";
						str += "            		<div class='price'><span class='fl'>" + formatPrice(product.price) + "</span>";
						str += " 				</a>";
						str += "            </div>";
						str += "			<div class='payment-btn'><a href='javascript:void(0)' onclick='addCart(\""+product.goodsNo+"\")'>再次购买</a></div>";
						str += "				<input type='hidden' id='stock_"+product.goodsNo+"' value='"+product.stock+"'/>";
						str += "				<input type='hidden' id='xj_"+product.goodsNo+"' value='"+product.wxIsMarketable+"'/>";
						str += "        	</div>";
						str += "    	</div>";
					}
					str += "    </a>";
					str += "</li>";
				}
			}
		}
        $(".order-list").empty();
		$(".order-list").append(str);	
	});
};

function toOrderDetail(orderNo){
	window.location.href = "order-detail.html?orderNo="+orderNo;
}

/***
 * 再次购买、加入购物车
 * @param productId
 */
function addCart(productId){
	var $stock = $("#stock_"+productId).val();
	var $xj = $("#xj_"+productId).val();
	
	if($stock < 1){
		Alert.disp_prompt("商品库存不足");
	}else if ($xj != '1'){
		Alert.disp_prompt("商品已下架");
	}else{
		AJAX.call( rootPath + "/wechat/cart/addCart?productId=" + productId +"&quantity=1", 
				  "post", "json", "", true, 
				  function(result){
						if(result.status){
							Alert.disp_prompt("商品已添加到购物车");
						}else{
							Alert.disp_prompt("添加失败:"+result.msg);
						}
			      }, function(){});
	}
	
}

function deleteOrder(orderNo){
	if(confirm("是否删除订单？")){
		$.ajax({  
	        url : rootPath + "/wechat/member/order/delete",  
	        data : {orderNo:orderNo},
	        async : false, 
	        type : "POST",  
	        success : function(result) {
	        	if(result.status){
	        		Alert.disp_prompt('订单删除成功');
	        		orderHistory.initTable();
	        	}else{
	        		Alert.disp_prompt(result.msg);
	        	}
	        }  
	    });
	}
}

