/****************
	订单	
****************/

/**
 * 加载页面
 */
$(document).ready(function(){
	//获取订单号
	order_no = URL_PARAM.getParam("orderNo");
//	var url = location.href.split('#')[0];
//	$.get(rootPath + "/wechat/member/user/getWechatConfig?url=" + url,"",function(data){
//		wx.config({
//			debug: false,
//			appId: data.appId,
//			nonceStr: data.nonceStr,
//			signature: data.signature,
//			timestamp: parseInt(data.timestamp),
//			jsApiList: [
//	            'checkJsApi',
//	            'onMenuShareTimeline',
//	            'onMenuShareAppMessage',
//	            'onMenuShareQQ',
//	            'onMenuShareWeibo',
//	            'hideMenuItems',
//	            'showMenuItems',
//	            'hideAllNonBaseMenuItem',
//	            'showAllNonBaseMenuItem',
//	            'translateVoice',
//	            'startRecord',
//	            'stopRecord',
//	            'onRecordEnd',
//	            'playVoice',
//	            'pauseVoice',
//	            'stopVoice',
//	            'uploadVoice',
//	            'downloadVoice',
//	            'chooseImage',
//	            'previewImage',
//	            'uploadImage',
//	            'downloadImage',
//	            'getNetworkType',
//	            'openLocation',
//	            'getLocation',
//	            'hideOptionMenu',
//	            'showOptionMenu',
//	            'closeWindow',
//	            'scanQRCode',
//	            'chooseWXPay',
//	            'openProductSpecificView',
//	            'addCard',
//	            'chooseCard',
//	            'openCard'
//	        ]
//		});
//	});
//	
//	wx.ready(function () {
//		document.querySelector('#wx_pay_btn').onclick = function () {
//			$.get(rootPath + "/wechat/member/user/getWechatPayConfig","orderNo="+orderNo,function(data){
//				if(data != null){
//				    wx.chooseWXPay({
//				    	 timestamp: data.timestamp,
//				    	 nonceStr: data.nonceStr,
//				    	 package: data.pkg,
//				    	 signType: 'MD5',
//				    	 paySign: data.paySign,
//				    	 success: function (res) {
//				    		// 支付成功后的回调函数
//				    		 paymentCallback();
//				    	 },
//				    	 fail: function (){
//				    		 alert("支付失败");
//				    	 }
//				    });
//				}else{
//					alert("支付异常");
//				}
//			});
//		};
//	});
});

/**
 * 微信支付回调
 */
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

/**
 * 取消订单
 */
function cancelledOrder(orderNo){
	orderNo = orderNo || order_no;
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
	        			 location.reload();
	        		},2000);
	        	}else{
	        		Alert.disp_prompt(result.msg);
	        	}
	        }  
	    });
	}
}
//去付款
function payOrder(orderNo){
	orderNo = orderNo || order_no;
		$.ajax({  
	        url : rootPath + "/wechat/member/order/update/status",  
	        data : {orderNo:orderNo, orderStatus:5},
	        async : false, 
	        type : "POST",  
	        success : function(result) {
	        	if(result.status){
	        		Alert.disp_prompt('订单支付成功');
	        		setTimeout(function(){
	        			window.location.href = "order_me.html";
	        		},2000);
	        	}else{
	        		Alert.disp_prompt(result.msg);
	        	}
	        }  
	    });
}

/**
 * 删除订单
 */
function deleteOrder(orderNo){
	orderNo = orderNo || order_no;
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
	        			 location.href = "order_me.html";
	        		},2000);
	        	}else{
	        		Alert.disp_prompt(result.msg);
	        	}
	        }  
	    });
	}
}

/**
 * 确认收货
 */
function confirmReceive(orderNo){
	orderNo = orderNo || order_no;
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
	        			location.reload();
	        		},2000);
	        	}else{
	        		Alert.disp_prompt(result.msg);
	        	}
	        }  
	    });
	}
}

/**
 * 跳转到商品详情页面
 */
function toProductDetail(productId){
	window.location.href = "product-detail.html?id="+productId;
}