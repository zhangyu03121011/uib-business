//全局加载数据
$(document).ready(function(){
	AJAX.call(rootPath+"/wechat/member/coupon/queryGetCoupon" , "post", "json", "", false, function(result){
		if(result.status){
			if(isNotEmpty(result.data)){
				$("#details").empty();
				var list=result.data;
				$.each(list, function(n, value) {
					var appendHtml="<li>"+
			                         "<dl>"+
		                               "<dt>"+
		                                 "¥<font>"+value.minPrice+"</font><span>满"+value.maxPrice+"元可用</span>"+
		                                 "<p>"+value.name+"</p>"+
		                                 "<p>有效期："+value.beginDate+"－"+value.endDate+"</p>"+
		                               "</dt>"+
		                               getCouponHtml(value.flag,value.id)+
		                             "</dl>"+
		                           "</li>";
					$("#details").append(appendHtml);
				});
			}
		}
	});
});

//返回相应的样式(立即领取和已领取)
function getCouponHtml(flag,id){
	if(0==flag){
		//未领取
		var arr = [];
		arr.push("<dd class='ture' id='cou_"+id+"'>");
		arr.push("<a href='#' onclick=getCoupon('"+id+"') >立即领取</a>");
		arr.push("</dd>");
		arr.push("<dd class='flase' id='couAlready_"+id+"' style='display:none'>已领取</dd>");
		return arr.join("");
	}else{
		//已领取
		return "<dd class='flase'>已领取</dd>";
	}
}

//给立即领取添加一个单击事件
function getCoupon(id){
	//1.判断是否登录
    AJAX.call(rootPath+"/wechat/user/ticket/state/", 'get','json', '', false,
  		  function(data){
				if(data == '0'){
					window.location.href="login.html?locationParm=jump";
					return;
				} 
		  }, function(){});
	//2.插入数据
	AJAX.call(rootPath+"/wechat/member/couponCode/insert?couponId="+id , "post", "json", "", false, function(result){
		if(result.status){
			$("#cou_"+id).css("display", "none");
			$("#couAlready_"+id).css("display", "block");
		}else{
			Alert.disp_prompt(result.msg);
		}
	});
}