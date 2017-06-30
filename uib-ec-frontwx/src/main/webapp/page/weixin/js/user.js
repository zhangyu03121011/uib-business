

var userName = '';
$(document).ready(function(){
	$.ajax({  
        url : rootPath + "/wechat/member/user/getMember",  
        async : false, 
        type : "POST",  
        success : function(result) {
        	if(!isNull(result.data)){
        		userName = result.data;
        		//var temp = userName.length > 10 ? userName.substring(0, 10) : userName;
        		$("#memberName").html(userName);
        	}
        	
        	if(!result.status){
    			//不存在
        		$("#bind").css("display", "block");
        		if(!isNull(result.code)){
        			Alert.disp_prompt(result.msg);
        		}
    		}else{
    			//已存在
    			$("#oldMemberPhone").css("display", "block");
    			$("#memberPhone").html(formatPhone(result.msg));
    			$("#bind").css("display", "none");
    		}
        	
        }  
    });
});

function subForm(){
	if(isNull(userName)){
		Alert.disp_prompt("该会员不存在");
		return ;
	}
	var phone = $("#phone").val();
	var code = $("#code").val();
	if(phone == ''){
		//alert("请输入11位手机号");
		Alert.disp_prompt("请输入11位手机号");
		return ;
	}
	var telReg = !!phone.match(/^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/);
	if(!telReg){
		Alert.disp_prompt("手机号格式有误");
		return ;
	}
	
	var isExist = '';
	$.ajax({  
        url : rootPath + "/wechat/member/user/checkPhone?phone="+phone,  
        async : false, 
        type : "POST",  
        success : function(result) {
        	isExist = result;
        }  
    });
	if(isExist == 'true'){
		//alert('该手机号已被其他用户绑定');
		Alert.disp_prompt("该手机号已被其他用户绑定");
		return ;
	}
	
	if(code == ''){
		//alert("请输入验证码");
		Alert.disp_prompt("请输入验证码");
		return ;
	}
	$.ajax({
        url : rootPath + "/wechat/member/user/bindPhone?phone="+phone+"&code="+code+"&wxName="+userName,  
        async : false, 
        type : "POST",  
        success : function(result) {
        	if(result.status){
        		Alert.disp_prompt("绑定成功");
        		window.location.href = "user.html";
        	}else{
        		Alert.disp_prompt(result.msg);
        	}
        }  
    });
}
