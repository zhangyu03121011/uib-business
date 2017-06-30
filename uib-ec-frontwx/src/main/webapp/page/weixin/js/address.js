/**
 * 地址管理
 */
$(document).ready(function(){
	//加载收货地址列表
	AJAX.call(rootPath+"/wechat/member/address/listAddress", "post", "json", "", true, function(result){
		var dataArr = [];
		var data = result.data;
//		var url = "/page/weixin/authenticate.html";
//		if (result.code == "500") {
//			$("#add_address").unbind("click");
//			var bool =window.confirm('请先实名认证!');		
//			if(bool){
//				window.location.href = url;
//			}
//			
//			//Alert.disp_prompt("请先实名认证");
//			//DIALOG.epConfirm("请先实名认证!",function() },function(){this.close;});
//			//location.href = "/page/weixin/authenticate.html?openId="+userName;
////			$("#add_address").attr("href","javascript:void(0)");	
//			return false;		
//		} else if((result.code == "2" || result.code == "0") && !isNull(result.code)){
//			//如果已经实名认证，并且认证失败
//			location.href = "/page/weixin/verify.html";
//		}
		
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
					str = "checked=true ";
				}
				dataArr.push("<div class='address-box' id='"+id+"'>");
				dataArr.push("<p>"+consignee+"<font>"+phone+"</font></p>");
				dataArr.push("<p class='gray'>"+areaName+address+"</p>");
				dataArr.push("<div class='operating'>");
				dataArr.push("<span class='fr'><a href='/page/weixin/add-address.html?id="+id+"'>编辑</a><a class='pink' href='javascript:void(0)' onclick=delAddress('"+id+"')>删除</a></span>");
				//dataArr.push("<label><a href='javascript:void(0)' onclick=setDefalutAddress('"+id+"')><input name='' onclick=setDefalutAddress('"+id+"') type='checkbox' value='"+id+"' "+str+"'></a>设为默认</label>");
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

/**
 * 删除
 */
function delAddress(id) {
	var bool =window.confirm('是否删除收货地址？');	
	if(isNotEmpty(id)&&bool) {
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
	AJAX.call(rootPath+"/wechat/member/address/listAddress", "post", "json", "", true, function(result){
		if(result.status){
			if(result.data.length==10){
				Alert.disp_prompt("收货地址只能新建10条！");
			}else{
				$("#add_address").attr("href","/page/weixin/add-address.html");				
			}
		}else{
			$("#add_address").attr("href","/page/weixin/add-address.html");				
		}
			
	});
});

	
