/****************
	订单 - 详情	
****************/


var obj = {};
var orderNo = "";
var orderStatus = 0;

var $expressNo = '';
//配送方式
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
					//订单状态
					$("#orderStatus").text(formatOrderStatus(order.orderStatus));
					//订单号
					$("#orderNo").text(order.orderNo);
					//下单时间
					$("#createDate").text(order.createDate);
					$("#address_detail").text(order.areaName.replace(new RegExp(/(-)/g),'') + order.address);
					//收货人
					$("#consignee").text(order.consignee);
					//收货人电话
					$("#phone").text(order.phone);
					//送货方式
					$("#shippingMethod").text(order.shippingMethodName || "");
					$shippingMethod = order.deliveryCorp;
					$expressNo = order.trackingNo;
					
					/**
					 * 商品列表
					 */
					var str = "";
					for(var i = 0; i < order.list_ordertable_item.length; i++){
						var product = order.list_ordertable_item[i];
						//拼接商品规格信息
						var specificationHtml = "";
						if(isNotEmpty(product.productSpecificationList)) {
							$.each(product.productSpecificationList,function(i,item) {
								var groupName = item.specificationGroup.name;
								var specificationName = item.name;
								specificationHtml += groupName + " : " + specificationName+"   ";
							});
						}
						str += '<a class="product-detail flex-row-top" href=product-detail.html?id='+product.goodNo+'>';
						    str += '<img src="'+product.thumbnail+'" class="product">';
							str += '<div class="flex-item detail">';
								str += '<p class="title"></p>';
								str += '<p class="specs">';
								str += '<span>'+specificationHtml+'</span>';        
								str += '</p>';
								str += '<div class="flex-row price-nub">';    
								str += '<span class="flex-item price">'+formatPrice(product.price)+'</span>';        
								str += '<span class="flex-item number">'+product.quantity+'</span>';        
								str += '</div>';
							str += '</div>';
						str += '</a>';
						//待发货、待收货 的订单可以申请售后
						if("5" == orderStatus || "6" == orderStatus) {
							var productName = encodeURI(product.name); 
							str += '<div class="flex-row me-pay">';
							str += 	'<div class="flex-item align-right">';
							//在实体类中加了returnType 标志商品的状态  退货完成 退货中 申请退货  
							var returnStatus=product.returnStatus;  
							if("3"==returnStatus){
								//退货中
								str += 	"<a id='apply_change' class='me-btn'>退货中</a>";
							}else if(""==returnStatus || null==returnStatus || undefined==returnStatus){
								//申请退货
								str += 	"<a id='apply_change' class='me-btn' onclick=applyAfter('"+orderNo+"','"+product.goodNo+"','"+productName+"','"+product.quantity+"',"+product.price+",'"+product.supplierId+"')>申请退货</a>";
							}
							str += 	'</div>';
							str += '</div>';
						}
					}
					$("#product-list").empty();
					$("#product-list").append(str);
					//商品总价格
					$("#productAmount").text(formatPrice(order.productAmount));
					//订单实付款
					$("#amount").text(formatPrice(order.amount));
				}
	        }  
	    });
	}
};

//点击申请售后的时候
function applyAfter(orderNo,goodNo,name,quantity,price,supplierId){
    //将订单编号,商品编号传到申请退换货页面
	if(window.localStorage){
	    localStorage.orderNo = orderNo;
		localStorage.goodNo=goodNo;
		localStorage.name=name;
		localStorage.quantity=quantity;
		localStorage.price=price;
		localStorage.supplierId=supplierId;
	}
	window.location.href="./aftermarket.html";
}

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
	//获取订单号
	orderNo = URL_PARAM.getParam("orderNo");
	
	//初始化订单信息
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
