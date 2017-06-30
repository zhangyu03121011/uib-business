/***
 * 支付成功页面
 */
var $order_no = '';

$(document).ready(function(){
	var url = location.href;
	if(url.indexOf("?") != -1){
		var temp = url.substring(url.indexOf("?") + 1, url.length);
		var params = temp.split("&");
		for(var i =0;i<params.length;i++){
			var pname = params[i].split("=")[0];
			var pvalue = params[i].split("=")[1];
			if(pname == 'orderNo'){
				$order_no = pvalue;
			}
		}
	}
	
	$("#view_order_btn").click(function(){
		window.location.href = "order-detail.html?orderNo="+$order_no;
	});
	
	if(!isNull($order_no)){
		$.get(rootPath + "/wechat/member/order/queryOrder?orderNo=" + $order_no,"",function(result){
			if(result.status){
				var order = result.data;
				$("#order_no_span").html($order_no);
				$("#order_price_span").html(formatPrice(order.amount));
			}
		});
	}
	
});