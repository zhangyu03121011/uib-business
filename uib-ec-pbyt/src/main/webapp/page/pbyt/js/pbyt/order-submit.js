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
				//商品总价格
				var total_price = 0;
				//商品总数量
				var p_count = 0;
				var products_arr = [];
				var order_products = result.data.list_ordertable_item;
				if(order_products && isNotEmpty(order_products)) {
					$.each(order_products,function(i,item) {
						var $flag = true;
						//拼接商品规格信息
						var specificationHtml = "";
						if(isNotEmpty(item.productSpecificationList)) {
							$.each(item.productSpecificationList,function(i,item) {
								var groupName = item.specificationGroup.name;
								var specificationName = item.name;
								specificationHtml += groupName + " : " + specificationName+"    ";
							});
						}
						//商品已下架
						if(item.wxIsMarketable=="0"){
							 $flag = false;
						  }else{
							  //商品无库存
							  if(item.stock==0){
								 $flag = false;
							  }
						  }
						$("#total_price").empty();
						 if($flag){
							 var curtotalAmt = mult(item.price,item.quantity);
							 total_price = (Number(curtotalAmt) + Number(total_price)).toFixed(2);
							 p_count = Number(item.quantity) + Number(p_count);
							 products_arr.push('<a class="product-detail flex-row-top">');
								 products_arr.push('<img src="'+item.thumbnail+'" class="product">');
								 products_arr.push('<div class="flex-item detail">');
								 		products_arr.push('<p class="title">'+item.fullName+'</p>');
					                   	products_arr.push('<p class="specs">'+specificationHtml+'</p>');
					                   	products_arr.push('<div class="flex-row price-nub">');
						                   	products_arr.push('<span class="flex-item price">'+formatPrice(item.price)+'</span>');
						                 	products_arr.push('<span class="flex-item number">X'+item.quantity+'</span>');
					                    products_arr.push('</div>');
			                    products_arr.push('</div>');
		                    products_arr.push('</a>');	
						 }
					});
					$("#product_list").empty();
					$("#product_list").append(products_arr.join(""));
					//合计
					console.info(formatPrice(total_price));
					$("#total_price").html(formatPrice(total_price));
					$("#total_amount").html(formatPrice(total_price));
					$("#p_count").html(p_count);
				} else {
					alertDialog("查询商品信息异常");
				}
			}else{
				alertDialog(result.msg);
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

/**
 * 加载页面
 */
$(document).ready(function(){
	cartId = URL_PARAM.getParam('cartId');
	pid = URL_PARAM.getParam('pid');
	_receiverId = URL_PARAM.getParam('receiverId');
	_facePrice = URL_PARAM.getParam('facePrice');
	_couponCode = URL_PARAM.getParam('couponCode');
	_balanceFlag = URL_PARAM.getParam('balanceFlag');
	if(_balanceFlag == 1){
		$('#balancePay').lcs_on();
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
	
	/**
	 * 提交订单
	 */
	$("#sub_order").click(function (){
		_receiverId = $("#receiver_id").val();
		var areaIds = $("#receiver_area_id").val();
		if(isNull(_receiverId)){
			alertDialog("请选择收货地址");
			return ;
		}
		//保存订单
		$.ajax({
	        url : rootPath +"/wechat/member/order/add?cartId="+cartId+"&isInvoice=0&paymentMethod=1&shippingMethod=1&pid="+
	        		pid+"&receiverId="+_receiverId+"&couponCode="+_couponCode+"&balanceFlag="+_balanceFlag+"&balancePwd="+balancePwd,
	        data : {
	        	"areaIds":areaIds
	        },
	        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
	        async : false, 
	        type : "POST",  
	        success : function(result) {  
	        	if(!result.status){
	        		alertDialog(result.msg);
	        		$("#back_cart_div").css("display", "block");
	    		}else{
	    			//调用微信支付
//	    			if(result.data.wxPayFlag == 0){
//		    			$.get(rootPath + "/wechat/member/user/getWechatPayConfig","orderNo="+result.data.orderNo,function(data){
//		    				if(data != null){
//		    				    wx.chooseWXPay({
//		    				    	 timestamp: data.timestamp,
//		    				    	 nonceStr: data.nonceStr,
//		    				    	 package: data.pkg,
//		    				    	 signType: 'MD5',
//		    				    	 paySign: data.paySign,
//		    				    	 success: function (res) {
//		    				    		// 支付成功后的回调函数
//		    				    		 location.href="pay_success.html?orderNo="+result.data.orderNo;
//		    				    		// paymentCallback(result.data.orderNo);
//		    				    	 },
//							    	 fail: function (){
//							    		 alert("支付失败");
//							    	 },
//							    	 cancel: function (){
//							    		 window.location.href = "order-submit.html";
//							    	 }
//		    				    });
//		    				}else{
//		    					alert("支付异常");
//		    				}
//		    			});
//	    			}
	    			//清空地址缓存信息
	    			sessionStorage.cartId = "";
	    			window.location.href = "order_me.html";
	    		}
	        }  
	    });
	});
	
	/**
	 * 重新选择地址
	 */
	touch.on('.address-wrap', 'tap', function() {
		window.location.href = "addressList.html?cartId="+cartId+"&pid="+pid;
	});
	
	/**
	 * 选择地址回调信息
	 */
	touch.on('#finish', 'tap', function() {
	  $("#select-address").hide();
	  $("#address").show();
	});
	
	getDefaultAddressInfo();

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

/**
 * 获取默认地址信息
 */
function getDefaultAddressInfo() {
	var obj = null;
	//本地存储获取地址信息
	if(window.sessionStorage){
		var sessionAddresss = sessionStorage.cartId;
		if (isNotEmpty(sessionAddresss)) {
			obj = JSON.parse(sessionAddresss);
		} else {
			AJAX.call(rootPath +"/wechat/member/address/listDefaultAddress", "post", "json", "", false, function(result){
				if(result && result.data){
					obj = result.data;
				} 
			});
		}
	}
	$("#user_name").html(obj.consignee);
	$("#phone").html(obj.phone);
	$("#address_area").html(obj.areaName+obj.address);
	$("#receiver_id").val(obj.id);
	$("#receiver_area_id").val(obj.area);
	$("#select-address").hide();
	$("#address").show();
	$('.select-address').toggleClass('open');
}

/**
 * 跳转到地址选择页面
 */
function selectAddress(){
	window.location.href = "addressList.html?cartId="+cartId+"&pid="+pid;
}

function alertDialog(msg) {
	var dia=$.dialog({
		content: msg,
		button: ["取消", '确定']
	});
}

/**
 * 总价
 * @param num1
 * @param num2
 * @returns
 */
function mult(num1,num2){
	return (Number(num1) * Number(num2)).toFixed(2);
}