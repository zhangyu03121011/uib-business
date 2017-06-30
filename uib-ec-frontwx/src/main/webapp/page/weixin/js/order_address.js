/**
 * 地址管理
 */
var cartId = '';
var pid = '';
var _couponCode = '';
var _facePrice = 0;
var _memo = '';
var removeIdArray = [];
var _balanceFlag = '';
var balancePwd = '';

$(document).ready(function(){
	cartId = URL_PARAM.getParam('cartId');
	pid = URL_PARAM.getParam('pid');
	_couponCode = URL_PARAM.getParam('couponCode');
	_memo = URL_PARAM.getParam('memo');
	_facePrice = URL_PARAM.getParam('facePrice');
	_balanceFlag = URL_PARAM.getParam('balanceFlag');
	balancePwd = URL_PARAM.getParam('balancePwd');
	
	//加载收货地址列表
	AJAX.call(rootPath+"/wechat/member/address/listAddress", "post", "json", "", true, function(result){
		var dataArr = [];
		var data = result.data;
		if(data) {
			$.each(data,function(i,item) {
				var id = item.id;
				var consignee = item.consignee;
				var phone = item.phone;
				var areaName = item.areaName;
				var address = item.address;				
				var flag=item.isDefault;
				var str = "";
				if(flag){
					str = "checked=true  ";
				}
				dataArr.push("<div class='address-box' id='"+id+"'>");
				dataArr.push("<div onclick=selectAddress(\'"+id+"\')><p>"+consignee+"<font>"+phone+"</font></p>");
				dataArr.push("<p class='gray'>"+areaName+address+"</p></div>");
				dataArr.push("<div class='operating'>");
				//dataArr.push("<span class='fr'><a href='/page/weixin/order-add-address.html?id="+id+ "&cartId="+cartId+"&pid="+pid +"'>编辑</a><a class='pink' href='javascript:void(0)' onclick=delAddress('"+id+"')>删除</a></span>");
				dataArr.push("<span class='fr'><a href='/page/weixin/order-add-address.html?id="+id+ "&cartId="+cartId+"&pid="+pid + "&facePrice=" + _facePrice + "&couponCode=" +_couponCode + "&memo=" + _memo + "&balanceFlag=" + _balanceFlag + "&balancePwd=" +balancePwd +"'>编辑</a><a class='pink' href='javascript:void(0)' onclick=delAddress('"+id+"')>删除</a></span>");
				dataArr.push("<label><a href='javascript:void(0)' onclick=setDefalutAddress('"+id+"') ><input id='cb_"+id+"' type='checkbox' value='"+id+"' "+str+"'/></a>设为默认</label>");
				dataArr.push("</div>");
				dataArr.push("</div>");
			});
		}
		if(dataArr) {
			dataArr = dataArr.join(" ");
		}
		$("#addressListForm").html(dataArr);
	}, function(){});
});

function selectAddress(id){
	var isOk = true;
	$.each(removeIdArray, function (index, tid){
		if(tid == id){
			isOk = false;
		}
	});
	if(isOk){
		window.location.href = "order-confirm.html?cartId="+cartId+"&isInvoice=0&paymentMethod=1&pid="+pid+"&receiverId="+id+"&couponCode="+_couponCode+"&facePrice="+_facePrice+"&memo="+_memo+"&balanceFlag="+_balanceFlag+"&balancePwd="+balancePwd;
	}
}

/**
 * 删除
 */
function delAddress(id) {
	var bool =window.confirm('是否删除收货地址？');	
	if(isNotEmpty(id)&&bool) {
		removeIdArray.push(id);
		AJAX.call(rootPath+"/wechat/member/address/delete", "get", "json", "id="+id, true, function(result){
			if (result && result.code == null) {
				$("#"+id).remove();
			}
		}, function(){});
	}
}

/**
 * 设置默认的收货地址
 */
function setDefalutAddress(id) {
	if(isNotEmpty(id)) {
		 $("input").each(function(){ 
			 if($(this).attr("type") == "checkbox" && $(this).val() != id ){
				 $(this).removeAttr("checked");
			 }
          });
			AJAX.call(rootPath+"/wechat/member/address/updateDefaultAddress", "get", "json", "id="+id, true, function(result){
				if (result && result.code == null) {			
					
				}
			}, function(){});
		
	}	
}
$("#add_address").click(function(){
	$("#add_address").attr("href","/page/weixin/order-add-address.html?cartId="+cartId+"&pid="+pid+ "&facePrice=" + _facePrice + "&couponCode=" +_couponCode + "&memo=" + _memo+"&balanceFlag="+_balanceFlag+"&balancePwd="+balancePwd);
});