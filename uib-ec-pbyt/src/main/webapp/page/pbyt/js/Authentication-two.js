/**
 * 个人中心---身份认证
 */
var userType=URL_PARAM.getParam("userType");
$(document).ready(function(){
	//清除发送验证码时所造成的缓存
	AJAX.call(rootPath+"/phone/cleanSession?type=3","post", "json", "", false,function(data){
	    if(data.status){
		}else{
			 Alert.disp_prompt(result.msg);
		}
	});
	
	//绑定手机号 和更改手机号时提示语不一样
	if(null==userType || ""==userType || undefined==userType){
		$("#show_message").text("请输入您的手机号码，进行更改");
	}
});
//点击下一步按钮
function submit(){
	if(!checkPhone()){
		return;
	}
	window.location.href='./Authentication-tre.html?phone='+$("#phone").val()+"&userType="+userType;
}

//校验手机号码
function checkPhone(){
	var phone=$("#phone").val();
	if(""!=phone && null!=phone){
		 var valResult=VALIDATOR.isValidMobel(phone);
		 if(valResult){
		     return true;
		  }else{
			  Alert.disp_prompt("请输入合法的手机号");
			  return false;
		  }
	 }else{
         Alert.disp_prompt("手机号不能为空");
		 return false;
	 }
}