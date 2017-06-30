/****************
	订单 - 详情	
****************/


var obj = {};
var orderNo = "";
var orderStatus = 0;
var $expressNo = '';
var $shippingMethod = '';

/**
 * 初始化表格
 */
obj.initTable = function(orderNo){
	if(!isNull(orderNo)){
		
		$.ajax({  
	        url : rootPath + "/wechat/member/order/queryOrder?orderNo=" + orderNo,  
	        async : false, 
	        type : "POST",  
	        success : function(result) {
	        	if(result.status){
					var order = result.data;
					orderStatus = order.orderStatus;
					
					$("#orderStatus").text(formatOrderStatus(order.orderStatus));
					$("#orderNo").text(order.orderNo);
					$("#createDate").text(order.createDate);
					$("#address").text(order.areaName.replace(new RegExp(/(-)/g),'') + order.address);
					$("#consignee").text(order.consignee + '  ' + order.phone);
					//$("#shippingMethod").text(order.shippingMethodName);
					$shippingMethod = order.deliveryCorp;
					$expressNo = order.trackingNo;
					
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
						str += "            	<p class='name'>" + product.fullName + "</p>";
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
	        }  
	    });
	}
};


/**
 * 初始化页面
 */
obj.initHtml = function(orderNo){
	obj.initTable(orderNo);
};

/**
 * 加载页面
 */
$(document).ready(function(){
	var url = location.href;
	if(url.indexOf("=") != -1){
		orderNo = url.substring(url.indexOf("=") + 1, url.length);
	}
	obj.initHtml(orderNo);
	if(orderStatus == 2 || orderStatus == 3){
		//已完成、已取消订单
		$("#go_cancell_div").css("display", "block");
		if(orderStatus == 2){
			$("#shippingMethod").html($shippingMethod + '(快递单号：'+$expressNo+')');
			$("#shippingMethod_div").css("display", "block");
		}
	}else if(orderStatus == 6){
		//已发货，待收货
		$("#go_receive_div").css("display", "block");
		$("#shippingMethod").html($shippingMethod + '(快递单号：'+$expressNo+')');
		$("#shippingMethod_div").css("display", "block");
	}else if(orderStatus == 4){
		//待付款
		$("#go_pay_div").css("display", "block");
		$('.pop-up-box .close-tip').click(function(){
			$('.theme-popover-mask').fadeOut(100);
			$('.pop-up-box').slideUp(200);
		});
		
		$(".payment-go").click(function(){
			showPayPage();
		});
		
		var url = location.href.split('#')[0];
		$.get(rootPath + "/wechat/member/user/getWechatConfig?url=" + url,"",function(data){
			wx.config({
				debug: false,
				appId: data.appId,
				nonceStr: data.nonceStr,
				signature: data.signature,
				timestamp: parseInt(data.timestamp),
				jsApiList: [
		            'checkJsApi',
		            'onMenuShareTimeline',
		            'onMenuShareAppMessage',
		            'onMenuShareQQ',
		            'onMenuShareWeibo',
		            'hideMenuItems',
		            'showMenuItems',
		            'hideAllNonBaseMenuItem',
		            'showAllNonBaseMenuItem',
		            'translateVoice',
		            'startRecord',
		            'stopRecord',
		            'onRecordEnd',
		            'playVoice',
		            'pauseVoice',
		            'stopVoice',
		            'uploadVoice',
		            'downloadVoice',
		            'chooseImage',
		            'previewImage',
		            'uploadImage',
		            'downloadImage',
		            'getNetworkType',
		            'openLocation',
		            'getLocation',
		            'hideOptionMenu',
		            'showOptionMenu',
		            'closeWindow',
		            'scanQRCode',
		            'chooseWXPay',
		            'openProductSpecificView',
		            'addCard',
		            'chooseCard',
		            'openCard'
		        ]
			});
		});
		
		wx.ready(function () {
			document.querySelector('#wx_pay_btn').onclick = function () {
				$.get(rootPath + "/wechat/member/user/getWechatPayConfig","orderNo="+orderNo,function(data){
					if(data != null){
					    wx.chooseWXPay({
					    	 timestamp: data.timestamp,
					    	 nonceStr: data.nonceStr,
					    	 package: data.pkg,
					    	 signType: 'MD5',
					    	 paySign: data.paySign,
					    	 success: function (res) {
					    		// 支付成功后的回调函数
					    		 paymentCallback();
					    	 },
					    	 fail: function (){
					    		 alert("支付失败");
					    	 }
					    });
					}else{
						alert("支付异常");
					}
				});
			};
		});
	}
});


function showPayPage(){
	$('.theme-popover-mask').fadeIn(100);
	$('.pop-up-box').slideDown(200);
}

function paymentCallback() {
	var orderStatus = 0;
	setTimeout(function () {
        $.post(rootPath +"/wechat/member/order/queryOrder?orderNo="+orderNo,function(result){
    		if(result.status && result.data){
    			orderStatus = result.data.orderStatus;
    		}							
    	});
        if (orderStatus != 5) {
        	$(".sk-circle").show();
            setTimeout(arguments.callee, 1000);
        } else {
        	$(".sk-circle").hide();
        	location.href="pay_success.html?orderNo="+orderNo;
        }
    }, 1000);
}

function cancelledOrder(){
	if(confirm("是否取消订单？")){
		$.ajax({  
	        url : rootPath + "/wechat/member/order/update/status",  
	        data : {orderNo:orderNo, orderStatus:3},
	        async : false, 
	        type : "POST",  
	        success : function(result) {
	        	if(result.status){
	        		Alert.disp_prompt('订单取消成功');
	        		setTimeout(function(){
	        			 location.href = "order-detail.html?orderNo="+orderNo;
	        		},2000);
	        	}else{
	        		Alert.disp_prompt(result.msg);
	        	}
	        }  
	    });
	}
}


function deleteOrder(){
	if(confirm("是否删除订单？")){
		$.ajax({  
	        url : rootPath + "/wechat/member/order/delete",  
	        data : {orderNo:orderNo},
	        async : false, 
	        type : "POST",  
	        success : function(result) {
	        	if(result.status){
	        		Alert.disp_prompt('订单删除成功');
	        		setTimeout(function(){
	        			 location.href = "order.html";
	        		},2000);
	        	}else{
	        		Alert.disp_prompt(result.msg);
	        	}
	        }  
	    });
	}
}

function confirmReceive(){
	if(confirm("是否确认收货？")){
		$.ajax({  
	        url : rootPath + "/wechat/member/order/update/status",  
	        data : {orderNo:orderNo, orderStatus:1},
	        async : false, 
	        type : "POST",  
	        success : function(result) {
	        	if(result.status){
	        		Alert.disp_prompt('订单确认收货成功');
	        		setTimeout(function(){
	        			location.href = "order.html";
	        		},2000);
	        	}else{
	        		Alert.disp_prompt(result.msg);
	        	}
	        }  
	    });
	}
}