var cartId = URL_PARAM.getParam('cartId');
var pid = URL_PARAM.getParam('pid');
var addressFromValue="";
$(function(){
		//根据ID查询地址信息
		var id = URL_PARAM.getParam("id");
		
		$("#consignee").focus();  
		
		$(".address-edit-page").on('touchmove',function(e){
			e.preventDefault();
		})
		
		
		if(isNotEmpty(id)){
			var selectArea = new MobileSelectArea();
			$.post(rootPath+"/wechat/member/address/queryOne?id="+id, function(result){
				if(result.status){
					var data = result.data;
					var areaNameVal = data.areaName;
					if(isNotEmpty(areaNameVal)){
						var areaNameArr = areaNameVal.split("-");
						//console.info(areaNameArr);
						var areaCodeVal = data.area;
						var areaNameVal =areaNameArr[0]+" "+areaNameArr[1] +" "+areaNameArr[2];
						$(".ui-form-item.select-item").attr("data-value",areaCodeVal);
						$(".un-change.data-input").html(" "+areaNameArr[0]+" "+areaNameArr[1] +" "+areaNameArr[2]);
						selectArea.init({trigger:'.select-item',value:$('.ui-form-item.select-item').attr('data-value'),text:areaNameVal.split(" "),data:rootPath+'/ptyt/addr/findAddressList',eventName:'click',callback:chooseBoxCallBack });
					}else{
						selectArea.init({trigger:'.select-item',value:$('.defaultValue').data('value'),data:rootPath+'/ptyt/addr/findAddressList',eventName:'click',callback:chooseBoxCallBack });
					}
					
					//填充表单的值
					for(var key in data) {
						$("input[name="+key+"]").val(data[key]);
						if(key=="isDefault"){
							$("#set-default").attr("checked",data[key]);
						}
					}
					
				}
				
				
			});
		}else{
			var selectArea = new MobileSelectArea();
			selectArea.init({trigger:'.select-item',value:$('.defaultValue').data('value'),data:rootPath+'/ptyt/addr/findAddressList',eventName:'click',callback:chooseBoxCallBack });
		}
		
		
		//添加收货地址或修改收货地址
		touch.on('#new-address-btn', 'touchstart', function(event) {
			var flag=true;
			var areaName="";
			var areaValue="";
			var area="";
			var areaCode="";
			var selprovince="";
			var selprovinceCode="";
			var selcity="";
			var selcityCode="";
			var selarea="";
			var selareaCode="";
			var addressFrom = $("#addressFrom input[type=text],span");
			$.each(addressFrom,function(i,each) {
				var value="";
				if($(each).context.localName=="span"){
					value =$(each).text();
					if(value != "" || value != undefined || value != null){
						area = value.split(" ");
						selprovince = area[1];
						selcity=area[2];
						selarea =area[3];
					}
					if(selcity==null || selcity==""){
						var message="请选择城市！";
						Alert.disp_prompt(message);
						$(each).focus();
						flag=false;
						return flag;
					}
				}else{
					value= $(each).val();
					if($(each).context.placeholder=="请输入收货人的手机号"){
						if(!VALIDATOR.isValidMobel(value)){			
							var message="手机号码格式不正确！";
							Alert.disp_prompt(message);
							$(each).focus();
							flag=false;
							return flag;
						}
					}
				}
				if(value == "" || value == undefined || value == null) {
					Alert.disp_prompt($(each).attr("errorMsg"));
					$(each).focus();
					flag=false;
					return flag;
				}
			});
			if(flag){
				addressFromValue = $("#addressFrom").serialize();
				addressFromValue = decodeURIComponent(addressFromValue,true);
				//获取省市区的code
				var code = $(".ui-form-item.select-item").attr("data-value");
				areaCode = code.split(",");
				selprovinceCode = areaCode[0];
				selcityCode = areaCode[1];
				selareaCode = areaCode[2];
				//默认地址
				var isDefault=0;
				if($("input[type='checkbox']").is(':checked')){
					isDefault =1;
				}
				//拼接参数
				areaName =selprovince+"-"+selcity+"-"+selarea;
				areaValue=selprovinceCode+","+selcityCode+","+selareaCode;
				if(isNotEmpty(areaName)&&isNotEmpty(areaValue)){
					addressFromValue = addressFromValue+"&areaName="+areaName+"&area="+areaValue+"&isDefault="+isDefault;
				 }
				if(isNotEmpty(id)){
					submit(rootPath + "/wechat/member/address/update",id);
				}else{
					submit(rootPath + "/wechat/member/address/save","");
				}
				
			}
		});
		//删除收货地址
		touch.on('#delete-address-btn', 'touchstart', function(event) {
			delAddress(id);
		});
		
})

function chooseBoxCallBack(scroller,text,value){
		var result ="" ;
		for(var i=0 ;i<text.length ; i++){
			result += (" "+text[i]);
		} ;
		$('.data-input').html(result) ;
	}
//删除收货地址
function delAddress(id) {
	var bool =window.confirm('是否删除收货地址？');	
	if(isNotEmpty(id)&&bool) {
		AJAX.call(rootPath+"/wechat/member/address/delete", "post", "json", "id="+id, true, function(result){
			if(result.status){
				Alert.disp_prompt("删除成功！");
				location.href = "/page/pbyt/addressList.html?cartId="+cartId+"&pid="+pid;
			} else {
				Alert.disp_prompt("删除失败！");
			}
		}, function(){});
	}
}
//添加收货地址或修改收货地址
function submit(url,id){
	AJAX.call(url, "post", "json", addressFromValue+"&id="+id, true, function(result){
		if(result.status){
			Alert.disp_prompt("保存成功！");
			location.href = "/page/pbyt/addressList.html?cartId="+cartId+"&pid="+pid;
		} else {
			Alert.disp_prompt("保存失败！");
		}
	}, function(){});
}