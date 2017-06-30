/**
 * 新增收货地址
 */

var cartId = '';
var pid = '';
var id = '';
var _couponCode = '';
var _facePrice = 0;
var _memo = '';
var _balanceFlag = '';
var balancePwd = '';

$(document).ready(function(){	
	cartId = URL_PARAM.getParam('cartId');
	pid = URL_PARAM.getParam('pid');
	_couponCode = URL_PARAM.getParam('couponCode');
	_memo = URL_PARAM.getParam('memo');
	_facePrice = URL_PARAM.getParam('facePrice');
	_balanceFlag = URL_PARAM.getParam('balanceFlag');
	id = URL_PARAM.getParam('id');
	balancePwd = URL_PARAM.getParam('balancePwd');
	
	if(isNotEmpty(id)){
		$.post(rootPath+"/wechat/member/address/queryOne?id="+id, function(result){
			var data = result.data;
			var areaCodeVal = data.area;
			var areaCodeArr = areaCodeVal.split(",");			
			$.post(rootPath+"/wechat/member/address/listByParentId?parentId="+1, function(result){
				var option="";
				$.each(result.data,function(item,i){
					if(i.id == areaCodeArr[0]){
						option=" <option value="+i.id+" selected>" + i.name + "</option> ";
					}else{
						option=" <option value="+i.id+">" + i.name + "</option> ";
					}
					$("#selprovince").append(option);
				});
			});
						
			$.post(rootPath+"/wechat/member/address/listByParentId?parentId="+areaCodeArr[0], function(result){
				var option="";
				$("#selcity").empty();
				$.each(result.data,function(item,i){
					if(i.id==areaCodeArr[1]){
						option=" <option value="+i.id+" selected>" + i.name + "</option> ";
					}else{
						option=" <option value="+i.id+">" + i.name + "</option> ";
					}
					
					$("#selcity").append(option);
				});		
			});
			
			$.post(rootPath+"/wechat/member/address/listByParentId?parentId="+areaCodeArr[1], function(result){
				var option="";
				$("#selarea").empty();
				$.each(result.data,function(item,i){
					if(i.id==areaCodeArr[2]){
						option=" <option value="+i.id+" selected>" + i.name + "</option> ";
					}else{
						option=" <option value="+i.id+">" + i.name + "</option> ";
					}				
					$("#selarea").append(option);
				});		
			});
			if(result.status){
				for(var key in data) {
					$("input[name="+key+"]").val(data[key]);
				}
			}
		});
	}else{
		queryProvince();
	}
	
});
function getCityAdd(id){
	$.post(rootPath+"/wechat/member/address/listByParentId?parentId="+id, function(result){
		var option="";
		$("#selcity").empty();
		$("#selarea").empty();
	    var selarea = " <option>-请选择地区-</option> ";
	    $("#selarea").append(selarea);
		$.each(result.data,function(item,i){
			option=" <option value="+i.id+">" + i.name + "</option> ";
			$("#selcity").append(option);
		});		
	});
}
function getAreaAdd(id){
	$.post(rootPath+"/wechat/member/address/listByParentId?parentId="+id, function(result){
		var option="";
		$("#selarea").empty();
		$.each(result.data,function(item,i){
			option=" <option value="+i.id+">" + i.name + "</option> ";
			$("#selarea").append(option);
		});
	});
}

$("#save").click(function(){
	var id = URL_PARAM.getParam("id");	
	if(isNotEmpty(id)){
		//更新
		var url = rootPath+"/wechat/member/address/update";
		submit(url,id);
	} else {
		//创建
		var url = rootPath+"/wechat/member/address/save";
		submit(url,id);
	}
	
});

function submit(url,id) {
	var addrFrom = $("#addressFrom").serialize();
	addrFrom = decodeURIComponent(addrFrom,true);
	var phone=$("#phone").val();
	var flag=true;
	var message = '';
	if(!VALIDATOR.isValidMobel(phone)){
		flag=false;
		message="手机号码格式不正确！";
	}
	var areaName="";
	var area="";
	var selprovince= $("#selprovince").find("option:selected").text();
	var selprovinceCode = $("#selprovince").find("option:selected").val();
	var selcity =$("#selcity").find("option:selected").text();
	var selcityCode =$("#selcity").find("option:selected").val();
	var selarea =$("#selarea").find("option:selected").text();
	var selareaCode=$("#selarea").find("option:selected").val();
	areaName =selprovince+"-"+selcity+"-"+selarea;
	area=selprovinceCode+","+selcityCode+","+selareaCode;
	if(isNotEmpty(areaName)&&isNotEmpty(area)){
		addrFrom = addrFrom+"&areaName="+areaName+"&area="+area+"&id="+id;
	 }
	if(flag){
		AJAX.call(url, "post", "json", addrFrom, true, function(result){
			if(result.status){
				Alert.disp_prompt("保存成功！");
				location.href = "/page/weixin/order_address.html?cartId="+cartId+"&pid="+pid + "&facePrice=" + _facePrice + "&couponCode=" +_couponCode + "&memo=" + _memo+"&balanceFlag="+_balanceFlag+"&balancePwd="+balancePwd;
			} else {
				Alert.disp_prompt("保存失败！");
			}
		}, function(){});
	}else{
		Alert.disp_prompt(message);
	}
	
}

function queryProvince(){
	$.post(rootPath+"/wechat/member/address/listByParentId?parentId="+1, function(result){
		var option="";
		$.each(result.data,function(item,i){
			option=" <option value="+i.id+">" + i.name + "</option> ";
			$("#selprovince").append(option);
		});
	});
}






