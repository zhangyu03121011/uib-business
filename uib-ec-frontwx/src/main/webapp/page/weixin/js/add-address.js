/**
 * 新增收货地址
 */
$(document).ready(function(){	
	var id = URL_PARAM.getParam("id");
	
	if(isNotEmpty(id)){
		$.post(rootPath+"/wechat/member/address/queryOne?id="+id, function(result){
			var data = result.data;
			var areaNameVal = data.areaName;
			var areaNameArr = areaNameVal.split("-");
			var areaCodeVal = data.area;
			var areaCodeArr = areaCodeVal.split(",");			
			$.post(rootPath+"/wechat/member/address/listByParentId?parentId="+1, function(result){
				var option="";
				$("#selprovince").empty();
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
//		$.post(rootPath+"/wechat/member/user/getMemberByUserName", function(result){
//			$("#consignee").val(result.realName);
//		});
		queryProvince();
	}	
	
});
function getCityAdd(id){
	if(isNotEmpty(id)){
		$.post(rootPath+"/wechat/member/address/listByParentId?parentId="+id, function(result){
			var option="";
			$("#selcity").empty();
			$("#selarea").empty();
			var selcity= "<option>-请选择城市-</option>";
		    var selarea = " <option>-请选择地区-</option> ";
		    $("#selcity").append(selcity);
		    $("#selarea").append(selarea);
			$.each(result.data,function(item,i){
				option=" <option value="+i.id+">" + i.name + "</option> ";
				$("#selcity").append(option);			
			});
			
//			$.post(rootPath+"/wechat/member/address/listByParentId?parentId="+result.data[0].id, function(result){
//				var option="";
//				$("#selarea").empty();
//				$.each(result.data,function(item,i){
//					option=" <option value="+i.id+">" + i.name + "</option> ";
//					$("#selarea").append(option);
//				});
//			});
		});
	}
	
}
function getAreaAdd(id){
	if(isNotEmpty(id)){
	$.post(rootPath+"/wechat/member/address/listByParentId?parentId="+id, function(result){
		var option="";
		$("#selarea").empty();
		 var selarea = " <option>-请选择地区-</option> ";
		 $("#selarea").append(selarea);
		$.each(result.data,function(item,i){
			option=" <option value="+i.id+">" + i.name + "</option> ";
			$("#selarea").append(option);
		});
	});
	}
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
	var flag=true;
	var addrFrom = $("#addressFrom").serialize();
	addrFrom = decodeURIComponent(addrFrom,true);
	var phone=$("#phone").val();
	var consignee =$("#consignee").val();
	if(consignee==""||consignee==null||consignee=='undefind'){
		flag=false;
		var message="姓名不能为空！";
		Alert.disp_prompt(message);
		return flag;
		
	}
	if(!VALIDATOR.isValidMobel(phone)){
		flag=false;
		var message="手机号码格式不正确！";
		Alert.disp_prompt(message);
		return flag;
	}
	var areaName="";
	var area="";
	var selprovince= $("#selprovince").find("option:selected").text();
	var selprovinceCode = $("#selprovince").find("option:selected").val();
	if(selprovince=="-请选择省份-"){
		flag=false;
		var message="请填写省份！";
		Alert.disp_prompt(message);
		return flag;
	}
	var selcity =$("#selcity").find("option:selected").text();
	var selcityCode =$("#selcity").find("option:selected").val();
	if(selcity=="-请选择城市-"){
		flag=false;
		var message="请选择城市！";
		Alert.disp_prompt(message);
		return flag;
	}
	var selarea =$("#selarea").find("option:selected").text();
	var selareaCode=$("#selarea").find("option:selected").val();
	if(selarea=="-请选择地区-"){
		selarea="";
	}
	areaName =selprovince+"-"+selcity+"-"+selarea;
	area=selprovinceCode+","+selcityCode+","+selareaCode;
	if(isNotEmpty(areaName)&&isNotEmpty(area)){
		addrFrom = addrFrom+"&areaName="+areaName+"&area="+area+"&id="+id;
	 }
	var addressdesc =$("#address").val();
	if(addressdesc==""||addressdesc==null||addressdesc=='undefind'){
		flag=false;
		var message="详细地址不能为空！";
		Alert.disp_prompt(message);
		return flag;
	}
	if(flag){
		AJAX.call(url, "post", "json", addrFrom, true, function(result){
			if(result.status){
				Alert.disp_prompt("保存成功！");
				location.href = "/page/weixin/address.html";
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






