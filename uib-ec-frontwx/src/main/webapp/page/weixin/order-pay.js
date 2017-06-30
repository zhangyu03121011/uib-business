/****************
	订单 - 待付款
****************/

var orderPay = {};

/**
 * 初始化表格
 */
orderPay.initTable = function(){
	$.get(rootPath + "/wechat/member/order/query",{orderStatus:"4"},function(result){
		var str = "";
		$(".order-list").empty();
		if(result.status){
			for(var i = 0; i < result.data.length; i ++){
				var order = result.data[i];
				
				str += "<li>";	
				str += "    <div  >";
				str += "        <div class='status-title' onclick='orderPay.detail(\""+order.orderNo+"\")'>";
				str += "        	<p>状态：<span class='pink'>待付款</span></p>";
				str += "        	<p>总价：<span class='pink'>¥" + order.amount + "</span></p>";
				str += "        </div>";
				str += "	<div class='payment-btn'>";
				str += "		<a class='ml1' href='javascript:void(0)' onclick='showPayPage(\""+order.orderNo+"\")'>去支付</a>";
				str += "		<a href='javascript:void(0)' onclick='cancelledOrder(\""+order.orderNo+"\")'>取消订单</a>";
				str += "	</div>";
				for(var j = 0; j < order.list_ordertable_item.length; j ++){
					var product = order.list_ordertable_item[j];
					str += "<a href='products.html?id=" + product.goodsNo + "'>";
					str += "   	<div class='wbox'>";
					str += "    	<div class='img'><img src='" + product.thumbnail + "'/></div>";
					str += "    	<div class='wbox-flex'>";
					str += "         	<p class='name'>" + product.fullName + "</p>";
					str += "         	<div class='price'><font class='fr'>X"+product.quantity+"</font><span class='fl'>" + formatPrice(product.price) + "</span>";
					str += "         	</div>";
					str += "    	</div>";
					str += " 	</div>"; 
					str += "</a>";
				}
				str += "    </div>";
				str += "</li>";
			}
		}
		$(".order-list").append(str);	
	});
};

orderPay.detail = function(orderNo){
	location.href = "order-detail.html?orderNo=" + orderNo;
};

/**
 * 初始化页面
 */
orderPay.initHtml = function(){
	orderPay.initTable();
};

/**
 * 加载页面
 */
$(document).ready(function(){
	orderPay.initHtml();
	
	$('.pop-up-box .close-tip').click(function(){
		$('.theme-popover-mask').fadeOut(100);
		$('.pop-up-box').slideUp(200);
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
			var orderNo = $("#order_no").val();
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
				    		 paymentCallback(orderNo);
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
});

/***
 * 微信支付
 */
function wxPay(){
	var $orderNo = $("#order_no").val();
	$.get(rootPath + "/wechat/member/user/getWechatPayConfig","orderNo="+$orderNo,function(data){
		if(data != null){
		    wx.chooseWXPay({
		    	 timestamp: data.timestamp,
		    	 nonceStr: data.nonceStr,
		    	 package: data.pkg,
		    	 signType: 'MD5',
		    	 paySign: data.paySign,
		    	 success: function (res) {
		    		// 支付成功后的回调函数
		    		 paymentCallback($orderNo);
		    	 },
		    	 fail:function (){
		    		 alert("支付失败");
		    	 },
		    	 cancel:function (){
		    		 alert("取消支付");
		    	 }
		    });
		}else{
			alert("支付异常");
		}
	});
}

function paymentCallback(orderNo) {
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


function showPayPage(orderNo){
	$("#order_no").val(orderNo);
	$('.theme-popover-mask').fadeIn(100);
	$('.pop-up-box').slideDown(200);
}

function cancelledOrder(order_no){
	if(confirm("是否取消订单？")){
		$.ajax({  
	        url : rootPath + "/wechat/member/order/update/status",  
	        data : {orderNo:order_no, orderStatus:3},
	        async : false, 
	        type : "POST",  
	        success : function(result) {
	        	if(result.status){
	        		Alert.disp_prompt('订单取消成功');
//	        		setTimeout(function(){
//	        			location.href = "order.html";
//	        		},2000);
	        		orderPay.initTable();
	        	}else{
	        		Alert.disp_prompt(result.msg);
	        	}
	        }  
	    });
	}
}
