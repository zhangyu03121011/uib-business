/****************
	订单 - 支付
****************/
var orderConfirm = {};


var cartId = '';
var pid = '';
var _receiverId = '';
var _facePrice = 0;
var _couponCode = '';
var balancePayStatus = false;
var balancePwd = '';
var _balanceFlag = "0";

/**
 * 初始化表格
 */
orderConfirm.initTable = function(cartId, pid, receiverId){
	if(!isNull(cartId) && !isNull(pid)){
		$.post(rootPath +"/wechat/member/order/toSettle?cartId="+cartId + "&pid="+pid+"&isInvoice=0&paymentMethod=1&shippingMethod=1&receiverId="+receiverId,function(result){
			if(result.status){
				if(result.data.defaultAddressFlag == '1'){
					$("#default_font").html("[默认]");
				}
				if(!isNull(receiverId) || !isNull(result.data.areaName)){
					_receiverId = result.data.receiverId;
					$("#address").text(result.data.areaName.replace(new RegExp(/(-)/g),'') + result.data.address);
					$("#phone").text(result.data.consignee+formatPhone(result.data.phone));
				}
				$("#consignee").val(result.data.consignee);
				$("#areaName").val(result.data.areaName);
				$("#_address").val(result.data.address);
				$("#_phone").val(result.data.phone);
				$("#area").val(result.data.area);
				
				if(result.data.couponCount > 0 && _facePrice == 0){
					$("#coupon_b").html('有可用优惠券');
				}
				var order = result.data;
				var str = "";
				var price =0;
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
					price = price+product.price*product.quantity;
					$("#price").text(formatPrice(price));
					
					$("#sum").text(formatPrice(price + order.freight - _facePrice));
				}
				if(!isNull(formatPrice(order.freight))){
					$("#freight").text('+'+formatPrice(order.freight));
				}
				$("#product-list").empty();
				$("#product-list").append(str);
			}else{
//				window.location.href = "cart.html"; 
			}						
		});
	}
};


/**
 * 初始化页面
 */
orderConfirm.initHtml = function(cartId, pid, receiverId){
	orderConfirm.initTable(cartId, pid, receiverId);
};


orderConfirm.queryBalance = function(){
	//获取当前用户信息
	AJAX.call(rootPath + "/wechat/member/user/getMemberByUserName", "post", "json", "", true, function(result){
		if(isNotEmpty(result)){
			if(parseFloat(result.balance) > 0){
				$("#balanceShow").text("可用余额：￥" + result.balance);
				$("#balance").val(result.balance);
			}
		}
		
	});
};


orderConfirm.alert = function(){
	art.dialog({
			title : '余额支付',
			lock  : true,
			width : 300,
			height: 50,
			resize: false,
			okVal : '确定',
			cancelValue:'取消',
			drag : true,
			content : '<input type="password" placeholder="请输入支付密码" id="balancePwd"/>',
			esc : false,
			background : "#555",
			ok : function(){
				balancePwd = $('#balancePwd').val();
				if(balancePwd != ''){
					balancePwd = strEnc($('#balancePwd').val(),"1","2","3");
					$.ajax({
				        url : rootPath +"/wechat/member/user/checkPayPassword",
				        data : {payPassword:balancePwd},
				        async : false, 
				        type : "POST",  
				        success : function(result) { 
				        	if(result.status){
				        		//alert("密码输入正确");
				        		_balanceFlag = 1;
				        	}else{
				        		_balanceFlag = 0;
				        		Alert.disp_prompt(result.msg);
				        		orderConfirm.alert();
				    			return ;
				        	}
				        }  
				    });
				}else{
					_balanceFlag = 0;
					Alert.disp_prompt("请输入支付密码");
					orderConfirm.alert();
					return ;
				}
			}, 
			cancel: function () {
				_balanceFlag = 0;
				$('#balancePay').lcs_off();
			}
	});
};

/**
 * 加载页面
 */
$(document).ready(function(){
	//绑定余额支付开关事件
	$('#balancePay').lc_switch();
	
	//选择开关改变事件
	$('body').delegate('.lcs_check', 'lcs-statuschange', function() {
		if(_balanceFlag != 1){
			balancePayStatus = ($(this).is(':checked')) ? true : false;
			if(balancePayStatus){
				orderConfirm.alert();
			}else{
				_balanceFlag = 0;
				balancePwd = "";
			}
		}else{
			_balanceFlag = 0;
			balancePwd = "";
		}
	});

	//查询可用余额
	orderConfirm.queryBalance();
	
	cartId = URL_PARAM.getParam('cartId');
	pid = URL_PARAM.getParam('pid');
	_receiverId = URL_PARAM.getParam('receiverId');
	_facePrice = URL_PARAM.getParam('facePrice');
	_couponCode = URL_PARAM.getParam('couponCode');
	_balanceFlag = URL_PARAM.getParam('balanceFlag');
	if(_balanceFlag == 1){
		$('#balancePay').lcs_on();
	}
	$("#memo").val(chineseFromUtf8Url(URL_PARAM.getParam('memo')));
	balancePwd = URL_PARAM.getParam('balancePwd');
	
	if(_facePrice != 0 && _facePrice != '' && _facePrice != null && _facePrice != 'null'){
		//_facePrice = parsefloat(_facePrice);
		$("#coupon_b").html('');
		$("#product_coupon_div").css("display", "block");
		$("#coupon_span").html("已使用"+ formatPrice(_facePrice));
		$("#coupon_p_span").html("-"+formatPrice(_facePrice));
	}else{
		_facePrice = 0;
	}
	orderConfirm.initHtml(cartId, pid, _receiverId);
	
	var _url = location.href.split('#')[0];
	$.get(rootPath + "/wechat/member/user/getWechatConfig?url=" + _url,"",function(data){
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
	
	
	$("#subOrder").click(function (){
		if(isNull(_receiverId) && (isNull($("#consignee").val()) || isNull($("#areaName").val()) 
				|| isNull($("#_address").val()) || isNull($("#_phone").val()) || isNull($("#area").val()))){
			Alert.disp_prompt("请选择收货地址");
			return ;
		}
		$.ajax({
	        url : rootPath +"/wechat/member/order/add?cartId="+cartId+"&isInvoice=0&paymentMethod=1&shippingMethod=1&pid="+
	        		pid+"&receiverId="+_receiverId+"&couponCode="+_couponCode+"&balanceFlag="+_balanceFlag+"&balancePwd="+balancePwd,
	        data : {
	        	memo : $("#memo").val(), 
	        	areaName : $("#areaName").val(),
	        	address : $("#_address").val(),
	        	phone : $("#_phone").val(),
	        	area : $("#area").val(),
	        	consignee : $("#consignee").val()
	        },
	        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
	        async : false, 
	        type : "POST",  
	        success : function(result) {  
	        	if(!result.status){
	        		Alert.disp_prompt(result.msg);
	        		$("#back_cart_div").css("display", "block");
	    		}else{
	    			//余额不足，需要调用微信支付剩余款项
	    			if(result.data.wxPayFlag == 1){
		    			$.get(rootPath + "/wechat/member/user/getWechatPayConfig","orderNo="+result.data.orderNo,function(data){
		    				if(data != null){
		    				    wx.chooseWXPay({
		    				    	 timestamp: data.timestamp,
		    				    	 nonceStr: data.nonceStr,
		    				    	 package: data.pkg,
		    				    	 signType: 'MD5',
		    				    	 paySign: data.paySign,
		    				    	 success: function (res) {
		    				    		// 支付成功后的回调函数
		    				    		 location.href="pay_success.html?orderNo="+result.data.orderNo;
		    				    		// paymentCallback(result.data.orderNo);
		    				    	 },
							    	 fail: function (){
							    		 alert("支付失败");
							    	 },
							    	 cancel: function (){
							    		 window.location.href = "order.html";
							    	 }
		    				    });
		    				}else{
		    					alert("支付异常");
		    				}
		    			});
	    			}else if(result.data.wxPayFlag == 2){
	    				 location.href="pay_success.html?orderNo="+result.data.orderNo;
	    			}
	    		}
	        }  
	    });
	});
});

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

function selectAddress(){
	window.location.href = "order_address.html?cartId="+cartId+"&pid="+pid+"&receiverId="+_receiverId+"&couponCode="+_couponCode+"&facePrice="+_facePrice+"&memo="+$("#memo").val()+"&balanceFlag="+_balanceFlag+"&balancePwd="+balancePwd;
}

function selectCoupon(){
	window.location.href = "order-coupon.html?cartId="+cartId+"&pid="+pid+"&receiverId="+_receiverId+"&shippingMethod=4&memo="+$("#memo").val()+"&balanceFlag="+_balanceFlag+"&balancePwd="+balancePwd;
}