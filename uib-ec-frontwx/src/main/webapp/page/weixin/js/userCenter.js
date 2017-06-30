/**
 * 个人中心
 */
/*
 * 账户管理功能
 */
function accountManager(){
	window.location.href="accountManager.html";
}

//根据给定的订单状态统计订单数量
function queryOrderCountByStatus(status){
	AJAX.call(rootPath + "/wechat/member/order/queryOrderCountByStatus?", "post", "json", "status="+status, true, function(result){
		if(result.status){
			if(status=="4"){
				$("#wait_pay").text(result.data);
			}else{
				$("#paid_shipped").text(result.data);
			}
		}else{
			Alert.disp_prompt(result.msg);	
		}
	});
}
$(document).ready(function(){
	/*
	 * 统计待付款的订单数量
	 */
	queryOrderCountByStatus("4");
	/*
	 * 统计带发货的订单数量
	 */
	 queryOrderCountByStatus("5");
	/*
	 * 统计待收货的订单数量
	 */
	AJAX.call(rootPath + "/wechat/member/order/queryOrderDeliveryCount/status", "post", "json", "", true, function(result){
		var orderNo = 0;
		if(isNotEmpty(result)){
			orderNo = result;
		}
		$("#shipped").text(orderNo);
	});
	
	//获取当前用户信息
	AJAX.call(rootPath + "/wechat/member/user/getMemberByUserName", "post", "json", "", true, function(result){
		$("#username").text(result.username+"  >");
	});
	
	 //统计我的钱包
	AJAX.call(rootPath +"/wechat/Commission/queryCommission?settleFlag=1", "post", "json", "", true, function(result){
		var data = result.data;
		if(isNotEmpty(data)){
			if(null==data.accountAmt){
				data.accountAmt=0.00;
			}
			$("#myWallet").text(data.accountAmt);
		}
	}, function(){});
	
	//查询实名认证的当前状态
	$.get(rootPath + "/wechat/member/user/isAuthentication",null,function(result){
		if(!result.status){
			if (result.code == "500") {
				$("#authenticateSpan").text("未认证");					
			}
			if((result.code == "2" )){
				$("#authenticateSpan").text("认证失败");
			}
			if((result.code == "0") && !isNull(result.code)){
				$("#authenticateSpan").text("认证中");
			}

		}else{
			$("#authenticateSpan").text("认证通过");
		}
	});
	
	//统计我的收藏
	$.post(rootPath +"/wechat/member/favorite/myFavoriteCount",
			function(result){
		if(result.status){
			$("#fav_count_p").text(result.data);
		}
		
	});
	
//	
//	$("#authenticate").click(function(){
//		$.get(rootPath + "/wechat/member/user/isAuthentication",null,function(result){
//			if(!result.status){
//				if (result.code == "500") {
//					location.href = "/page/weixin/authenticate.html";
//					return false;
//				}
//				if((result.code == "2" || result.code == "0") && !isNull(result.code)){
//					//如果已经实名认证，并且认证失败
//					location.href = "/page/weixin/verify.html";
//					return false;
//				}
//			}
//		});
//	});

	
//	//查询实名认证的当前状态
//	$.get(rootPath + "/wechat/member/user/isAuthentication",null,function(result){
//		if(!result.status){
//			if (result.code == "500") {
//				$("#authenticateSpan").text("未认证");					
//			}
//			if((result.code == "2" )){
//				$("#authenticateSpan").text("认证失败");
//			}
//			if((result.code == "0") && !isNull(result.code)){
//				$("#authenticateSpan").text("认证中");
//			}
//
//		}else{
//			$("#authenticateSpan").text("认证通过");
//		}
//	});
//	
	
	$("#authenticate").click(function(){
		$.get(rootPath + "/wechat/member/user/isAuthentication",null,function(result){
			if(!result.status){
				if (result.code == "500") {
					location.href = "/page/weixin/authenticate.html";
					return false;
				}
				if((result.code == "2" || result.code == "0") && !isNull(result.code)){
					//如果已经实名认证，并且认证失败
					location.href = "/page/weixin/verify.html";
					return false;
				}
			}else{
				if(result.code == "1"){
					location.href = "/page/weixin/verify.html";
					return false;
				}
			}
		});
	});
	//退出登录
	$("#loginoff").click(function(){
		$.get(rootPath + "/wechat/user/loginOff",null,function(result){
			if(result.status){
				location.href = "/page/weixin/login.html";
				return false;
			}
		});
	});
});


