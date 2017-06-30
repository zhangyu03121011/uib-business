/**
 * 地址列表
 */
var cartId = URL_PARAM.getParam('cartId');
var pid = URL_PARAM.getParam('pid');
$(document).ready(function(){
	//加载收货地址列表
	AJAX.call(rootPath+"/wechat/member/address/listAddress", "post", "json", "", true, function(result){
		var dataArr = [];
		var data = result.data;
		if(data){
			$.each(data,function(i,item) {
				var id = item.id;
				var consignee = item.consignee;
				var phone = item.phone;
				var areaName = item.areaName;
				var address = item.address;				
				var flag=item.isDefault;
				var str = "";
				if(flag){
					str = "checked=true ";
				}
				dataArr.push("<div class='address-item display-flex align-items-center'>");
				dataArr.push("	<div class='flex-1 text' onclick='selectAddress("+JSON.stringify(item)+")'>");
				dataArr.push("		<p class='info cl'>");
				dataArr.push("			<span class='name'>"+consignee+"</span>");
				if(flag){
					dataArr.push("			<span class='default'>默认</span>");
				}
				dataArr.push("			<span class='tel'>"+phone+"</span>");
				dataArr.push(" 		</p>");
				dataArr.push("  	<p class='address-detail'>"+areaName+address+"</p>");
				dataArr.push(" </div>");
				dataArr.push("	<div class='edit-icon'>");
				dataArr.push("	<a href='/page/pbyt/edit-address.html?id="+id+"'><i class='iconfont icon-bianji'></i></a>");
				dataArr.push("	</div>");
				dataArr.push("</div>");
				
			});
			if(dataArr) {
				dataArr = dataArr.join(" ");
			}
				$(".address-list").prepend(dataArr);
		}
	});
	touch.on('.new-address-btn', 'touchstart', function(event) {
		event.preventDefault();
		location.href = "/page/pbyt/add-address.html?cartId="+cartId+"&pid="+pid;
	});
});

/**
 * 选择地址回调
 */
function selectAddress(addressInfo){
	if(window.sessionStorage){
		sessionStorage.cartId = JSON.stringify(addressInfo);
		window.location.href = "cart-submit.html?cartId="+cartId+"&isInvoice=0&paymentMethod=1&pid="+pid;
	}
}