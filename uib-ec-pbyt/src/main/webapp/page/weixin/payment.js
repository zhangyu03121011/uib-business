$(document).ready(function(){
	var orderNo = getParam("orderNo");
	
	//显示支付价格
	$.post(rootPath +"/wechat/member/order/queryOrder?orderNo="+orderNo,function(result){
		if(result.status){
			$("#amount").text(formatPrice(result.data.amount));
		}							
	});
	
	function getParam(key) {
		var svalue = window.location.search.match(new RegExp("[\?\&]" + key
				+ "=([^\&]*)(\&?)", "i"));
		return svalue ? svalue[1] : svalue;
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
	        	$("#wxpay").attr("id","");
	            setTimeout(arguments.callee, 1000);
	        } else {
	        	$(".sk-circle").hide();
	        	$("#wxpay").attr("id","wxpay");
	        	location.href="order.html?orderPaymentCallback=true";
	        }
	    }, 1000);
	}
	
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
		document.querySelector('#wxpay').onclick = function () {
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
				    	 }
				    });
				}else{
					alert("支付异常");
				}
			});
		};
	});
//	wx.error(function (res) {
//		alert(res.errMsg);
//	});
});