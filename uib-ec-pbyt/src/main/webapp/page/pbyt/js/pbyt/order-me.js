/****************
	我的订单
****************/
var pageIndex = 0;
var pageSize = 4;
var isLoad = true;//是否需要加载(到最后一页时,禁止滚动加载)
var isRequest = false;//是否正在请求(正在请求时,禁止滚动加载)
var order = {};
//订单状态
var order_status = "";
/**
 * 初始化表格
 */
order.initTable = function(orderStatus){
	$.get(rootPath + "/wechat/member/order/query/{0}/{1}".format(pageIndex,pageSize),{"orderStatus":orderStatus},function(result){
		if(result.status){
			var str = order.orderListtemple(result.data);
			var orderList = $("#order-list").html();
			 if(!isNotEmpty(orderList)) {
				$("#no-data").show();
			 }
		}
	});
};

order.orderListtemple = function(data) {
	var str = "";
	productListArr = [];
	if(isNotEmpty(data)) {
		for(var i = 0; i < data.length; i ++){
			var order = data[i];
			productListArr.push('<div id="order-list">');
			productListArr.push('<div class="me-wrap" id="item1">');
			productListArr.push('<div class="flex-row me-order">');
			productListArr.push('<div class="flex-item">');
			productListArr.push('<span class="number">订单 '+order.orderNo+'</span>');
			productListArr.push('</div>');
			productListArr.push('<div class="align-right">');
			if(order.orderStatus == "4") {
				productListArr.push('<span class="tip">等待买家付款</span>');
	       	} else if(order.orderStatus == "5") {
	       		productListArr.push('<span class="tip">等待卖家发货</span>');
	       	} else if(order.orderStatus == "6") {
	       		productListArr.push('<span class="tip">等待买家收货</span>');
	       	} else if(order.orderStatus == "2") {
	       		productListArr.push('<span class="tip">交易成功</span>');
	       	} else if(order.orderStatus == "3") { //已取消订单
	       		productListArr.push('<span class="tip">交易关闭</span>');
	       	}
			
			productListArr.push('</div>');
			productListArr.push('</div>');
	        var orderNo = order.orderNo+"";            
	        var productList = data[i].list_ordertable_item;
	        if(isNotEmpty(productList)) {
	        	 $.each(productList,function(i,product) {
	        		//拼接商品规格信息
	 				var specificationHtml = "";
	 				if(isNotEmpty(product.productSpecificationList)) {
	 					$.each(product.productSpecificationList,function(i,item) {
	 						var groupName = item.specificationGroup.name;
	 						var specificationName = item.name;
	 						specificationHtml += groupName + " : " + specificationName+"   ";
	 					});
	 				} 
	        		//商品列表
	    			productListArr.push('<div id=product_list>');
	    			productListArr.push(	'<a class="product-detail flex-row-top" href="javascript:void(0)" onclick=toOrderDetail("'+orderNo+'")>');
	    			productListArr.push(		'<img src="'+product.thumbnail+'" class="product">');
					productListArr.push(		'<div class="flex-item detail">');
					productListArr.push(			'<p class="title">'+product.fullName+'</p>');
	               	productListArr.push(			'<p class="specs">');
	               	productListArr.push(				'<span>'+specificationHtml+'</span>');
	               	productListArr.push(			'</p>');
	               	productListArr.push(			'<div class="flex-row price-nub">');
	               	productListArr.push(				'<span class="flex-item price">'+formatPrice(product.price)+'</span>');
	               	productListArr.push(				'<span class="flex-item number">'+product.quantity+'</span>');
	                productListArr.push(			'</div>');
		            productListArr.push(		'</div>');
	    	        productListArr.push(	'</a>');
	    	        productListArr.push('</div>');
	             });
	        } 
	       
	        productListArr.push('<div class="flex-row me-pay">');
	        	productListArr.push('<div class="flex-item sum">实付款：<span>'+formatPrice(order.amount)+'</span>');
	       	productListArr.push('</div>');
	       	productListArr.push('<div class="flex-item align-right">');
	       	if(order.orderStatus == "4") {
	       		productListArr.push('<a href="javascript:void(0)" class="me-btn" onclick=cancelledOrder("'+orderNo+'")>取消订单</a>&nbsp&nbsp<a class="me-btn" href="#" onclick=payOrder("'+orderNo+'")>去付款</a>');
	       	} else if(order.orderStatus == "2" || order.orderStatus == "3") {
	       		productListArr.push('<p onclick=deleteOrder("'+orderNo+'") class="me-btn">删除订单</p>');
	       	} else if(order.orderStatus == "6") {
	       		productListArr.push('<a href="#" class="me-btn" onclick=confirmReceive("'+orderNo+'")>确认收货</a>');
	       	}
	       	productListArr.push('</div>');
	       	productListArr.push('</div>');
	       	productListArr.push('</div>');
	       	productListArr.push('</div>');
		} 
		
		if(data.length < pageSize || !isLoad){
			isRequest = true;
			isLoad = false;
			$(".scrimg .load_tip").text("没有更多数据");
		}
	}else {
		isLoad = false;
	}
	if(pageIndex == 0){
		$("#order-list").empty();
	}
	$("#order-list").append(productListArr.join(""));
	
	return;
};

/**
 * 加载页面
 */
$(document).ready(function(){
	order.initTable();
	//切换订单状态
    touch.on(".me-status .item", "touchstart", function(event) {
        event.preventDefault();
        $(".item").removeClass('active');
        $(this).addClass('active');
        $(".me-wrap").hide();
        order_status = Number($(this).attr("orderStatus"));
        //隐藏加载tip
        $(".scrimg").hide();
        //分页重新开始计算
        pageIndex = 0;
        isLoad = true;
        isRequest = false;
        $(".scrimg .load_tip").text("正在努力加载数据...");
        switch (order_status) {
		case 2: //已完成订单
			order.initTable(order_status);
			break;
		case 4: //待付款订单
			order.initTable(order_status);
			break;
		case 5: //待发货订单
			order.initTable(order_status);
			break;
		case 6: //待收货订单
			order.initTable(order_status);
			break;
		default://全部订单
			order_status = "";
			order.initTable(order_status);
			break;
		}
    });
    
	//滚动分页
	 $(window).scroll(function(){
	 	var scrollTop = $(this).scrollTop();
	 	var scrollHeight = $(document).height();
	 	var windowHeight = $(this).height();
	 	if(scrollTop/(scrollHeight-windowHeight) >= 0.95 && isRequest == false && isLoad == true){
	 		$(".scrimg").show();
	 		pageIndex += pageSize;
	 		order.initTable(order_status);
	 	}
	 });
	 
	 
});

/**
 * 删除已完成订单
 * @param orderNo
 */
function orderDel(orderNo) {
	//删除订单
	if(isNull(orderNo)){
		Alert.disp_prompt("请选择订单");
		return ;
	}
	$.ajax({
        url : rootPath +"/wechat/member/order/delete?orderNo="+orderNo,
        data : {
        },
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        async : false, 
        type : "POST",  
        success : function(result) {  
        	if(result.status){
        		Alert.disp_prompt("删除订单成功");
        		order.initTable(2);
    		}
        }  
    });
};

/**
 * 跳转订单详情
 * @param orderNo
 */
function toOrderDetail(orderNo){
	window.location.href = "me-detail.html?orderNo="+orderNo;
}

