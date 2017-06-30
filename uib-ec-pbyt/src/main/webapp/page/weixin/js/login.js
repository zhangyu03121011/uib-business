$(document).ready(function(){
	var locationParm = URL_PARAM.getParam("locationParm");
	var productId = URL_PARAM.getParam("productId");
	var rmemberId = URL_PARAM.getParam("rmemberId");
	
	$("#seeBtn").on("click",function(){
		window.location.href = "home.html";
	});
	
	$("#submitBtn").on("click",function(){
		var password = $("#password").val();
		if(!validateUserName()) {
			$("#userName")[0].focus();
			return;
		}
		
		if(!validatePassWord()) {
			$("#password")[0].focus();
			return;
		}
		
		if(!varifyCodeCheck()) {
			$("#captcha_seq")[0].focus();
			return;
		}
		
		var  enResult = strEnc(password,"1","2","3");		
		
//		ajaxCall("/wechat/user/login",$("#loginForm").serialize(),"post",true,function(data){
		ajaxCall("/wechat/user/login","userName=" + $("#userName").val() + "&password=" + enResult, "post",false,function(data){
			if (data.status == true) {
				if(isNotEmpty(locationParm)){
//					window.history.go(-1);
//					window.history.back();
					window.location.href=document.referrer;
				}else{
					window.location.href = "home.html";
				}
				
				return;
			} else if(data.code == 1004) {
				Alert.disp_prompt(data.msg);
				$("#userName").val("");
				$("#password").val("");
				return;
			} else if(data.code == 1005) {
				Alert.disp_prompt(data.msg);
				$("#password").val("");
				$("#captcha_seq").val("");
				$("#password")[0].focus();
				$("#captchaImage").attr("src",rootPath + "/common/captcha?timestamp="+ (new Date()).valueOf());
				return;
			}
		});
	});
	
	/**
	 * 跟换图文验证码
	 */
	$("#captchaImage").attr("src",rootPath + "/common/captcha?timestamp="+ (new Date()).valueOf());
	$("#captchaImage,#captcha_image_change").on("click",function(event) {
		valideFlag = false;
		$("#captchaImage").attr("src",rootPath + "/common/captcha?timestamp="+ (new Date()).valueOf());
	});
	
	 /**
     * 校验验证码
     */
    function varifyCodeCheck(){
    	var phone = $("#phone").val();
        var varifyCode = $("#captcha_seq").val();
        var flag = false;
        if (null == varifyCode || varifyCode == ""){
        	Alert.disp_prompt("图文验证码不能为空!");
			return flag;
		}
    	
		if(!checkCaptcha4Ajax(varifyCode)){
			$("#captcha_seq").trigger('focus');
			return flag;
		} else {
			flag = true;
		}
		return flag;
	}
    
    function checkCaptcha4Ajax(varifyCode){
    	var flag = false;
		ajaxCall("/reg/captcha/check",{ captchaId:null,captcha:varifyCode },"post",false,function(data){
			if (data.status == false){
				$("#captchaImage").attr("src",rootPath + "/common/captcha?timestamp="+ (new Date()).valueOf());
				Alert.disp_prompt("验证码错误,请重新输入!");
			}else if(data.status==true){
				flag = true;
			}
		});
		return flag;
    }
    
    //验证用户名
    function validateUserName() {
    	var flag = false;
    	var userName = $("#userName").val();
    	if(''!= userName && null != userName){
    		if(userName.length <4 || userName.length > 20){ 
				Alert.disp_prompt("请输入4-20个字符!");
				return flag;
			}else{
				flag = true;
			}
		} else {
			Alert.disp_prompt("用户名不能为空!");
			return false;
		}
    	return flag;
    }
    
    /**
     * 验证密码
     */
    function validatePassWord(){
    	var flag = false;
    	var password = $("#password").val();
    	if (null == password || "" == password){
			Alert.disp_prompt("密码不能为空!");
			return flag;
		} else {
			flag = true;
		}
    	return flag;
    }
    
    function ajaxCall(url,data,type,async,success) {
    	$.ajax({
    		  type: type,
    		  url: rootPath + url,
    		  data: data,
    		  async : async,
    		  dataType: 'json',
    		  timeout: 300,
    		  context: $('body'),
    		  success: success,
    		  error: function(xhr, type){
    		  }
    		});
    }
});
