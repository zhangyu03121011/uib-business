$(document).ready(function(){
	var activate = ('createTouch' in document) ? 'touchstart' : 'click'
	var curCount = 90;
	
	/**
	 * 注册提交
	 */
	$("#submitBtn").live(activate,function(){
		var flag = true;
		var username =	$("#username").val();
		var phone =	$("#phone").val();
		var password1 = $("#password1").val();
		var password2 = $("#password2").val();
		var validateCode = $("#validateCode").val();
		
		if(!validateUserName()) {
			$("#username").trigger('focus');
			return;
		}
		
		if(!validatePhone()) {
			$("#phone").trigger('focus');
			return;
		}
		
		if(!varifyCodeCheck()) {
			$("#captcha_seq").trigger('focus');
			return;
		}
		
		if(!validate()) {
			$("#validateCode").trigger('focus');
			return;
		}
		
		if(!validatePassWord1()) {
			$("#password1").trigger('focus');
			return;
		}
		
		if(!validatePassWord2()) {
			$("#password2").trigger('focus');
			return;
		}
		
		if($('#agree').is(':checked')==false) {
			$("#showmessage").text("请同意相关协议");
			return;
		}else{
			$("#showmessage").text("");	
		}
		var  enResult = strEnc(password1,"1","2","3");	
		$("#password").val(enResult);
		ajaxCall("/wechat/user/save",$("#registerForm").serialize(),"post",false,function(data){
			if (data.status == true) {
				window.history.go(-2);
			} 
		});
	});
	
	/**
	 * 跟换图文验证码
	 */
	$("#captchaImage").attr("src",rootPath + "/common/captcha?timestamp="+ (new Date()).valueOf());
	$("#captchaImage,#captcha_image_change").live(activate,function(event) {
		valideFlag = false;
		$("#captchaImage").attr("src",rootPath + "/common/captcha?timestamp="+ (new Date()).valueOf());
	});
	
	/**
	 * 发送短信验证码
	 */
	$("#btnSendCode").live(activate,function (event){
		curCount = 90;
		var phone = $("#phone").val();
		if(!validateUserName()) {
			$("#username").trigger('focus');
			return;
		}
		
		if(!validatePhone()) {
			$("#phone").trigger('focus');
			return;
		}
		
		if(!varifyCodeCheck()) {
			$("#captcha_seq").trigger('focus');
			return;
		}
		
		$.post(rootPath + "/wechat/user/validateCode",{phone:phone,isRegist:"1",sendType:"1"},function(data){});
		sendMessage();
	});
	
	//倒计时
    function sendMessage() {
		//设置button效果，开始计时
    	$("#validateCode").trigger('focus');
    	 $("#btnSendCode").css('pointer-events',"none");
	     $("#btnSendCode").text(curCount+"秒");
	     InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
	}
	
	//timer处理函数
	function SetRemainTime() {
        if (curCount == 0) {   
            window.clearInterval(InterValObj);//停止计时器
            $("#btnSendCode").css('pointer-events',"auto");
            $("#btnSendCode").text("重新发送");
        }
        else {
            curCount--;
            $("#btnSendCode").text(curCount+"秒");
        }
    }
	
    /**
     * 校验验证码
     */
    function varifyCodeCheck(){
    	var phone = $("#phone").val();
        var varifyCode = $("#captcha_seq").val();
        var flag = false;
        if (null == varifyCode || varifyCode == ""){
			$("#showmessage").text("图文验证码不能为空!");
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
				$("#showmessage").text("验证码错误,请重新输入!");
			}else if(data.status==true){
				$("#showmessage").text("");
				flag = true;
			}
		});
		return flag;
    }
    
    $("#captcha_seq").focusout(function(){
    	if(!varifyCodeCheck()) {
			$("#captcha_seq").trigger('focus');
			return;
		}
    });
    
    $("#username").focusout(function(){
    	if(!validateUserName()) {
			$("#username").trigger('focus');
		}
    });
    
    $("#phone").focusout(function(){
		if (!validatePhone()) {
			$("#phone").trigger('focus');
		}
	 });
    
    $("#validateCode").focusout(function(){
    	if(!validate()) {
			$("#validateCode").trigger('focus');
		} else {
			
		}
	 });
    
    $("#password1").focusout(function(){
    	if(!validatePassWord1()) {
			$("#password1").trigger('focus');
		}
	 });
    
    $("#password2").focusout(function(){
    	if(!validatePassWord2()) {
			$("#password2").trigger('focus');
		}
	 });
	
    //验证用户名是否存在
    function validateUserName() {
    	var flag = false;
    	var userName = $("#username").val();
    	if(''!= userName && null != userName){
    		if(userName.length <4 || userName.length > 20){ 
				$("#showmessage").text("请输入4-20个字符!");
				return flag;
			}else{
				$("#showmessage").html("");
			}
    		ajaxCall("/reg/validateUserName",{ username:userName },"post",false,function(data){
				if (data == "true"){
					$("#showmessage").html("用户名已存在!"); 
				} else {
					$("#showmessage").html("");
					flag = true;
					return flag;
				}
			});
		} else {
			$("#showmessage").text("用户名不能为空!");
			return false;
		}
    	return flag;
    }
    
    //验证手机号码是否存在
    function validatePhone() {
    	var flag = false;
    	var phoneval=$("#phone").val();
		if(null != phoneval && ''!=phoneval){
			var isMobile=/^(?:13\d|14\d|15\d|18\d)\d{5}(\d{3}|\*{3})$/; //手机号码验证规则
			if(!isMobile.test(phoneval)){ 
				$("#showmessage").text("手机号码格式不正确!");
				return flag;
			}else{
				$("#showmessage").html("");
			}
			ajaxCall("/reg/validatePhone",{ phone:phoneval},"post",false,function(data){
				if (data == "true" || data == true){
					$("#showmessage").html("手机号码已存在!"); 
				} else {
					$("#showmessage").html("");
					flag = true;
					return flag;
				}
			});
		} else {
			$("#showmessage").text("手机号码不能为空!");
			return false;
		}
		return flag;
    }
    
    //短信验证码校验
    function validate(){
    	var flag = false;
    	var phone = $("#phone").val();
    	var formCode = $("#validateCode").val();
    	if (null != formCode && "" != formCode) {
    		if(validatePhone()) {
    			ajaxCall("/wechat/user/validateCode/check",{phone:phone,formCode:formCode},"post",false,function(data){
        			if (data.errorFlag == true) {
        				$("#showmessage").text("短信验证码错误!");
        				$("#validateCode").trigger('focus');
        			} else {
        				flag = true;
        			}
        		});
        	}
    	} else {
    		$("#showmessage").text("短信验证码不能为空!");
    		$("#phone").trigger('focus');
    		return true;
    	}
    	return flag;
    }
    
    function validatePassWord1(){
    	var flag = false;
    	var password = $("#password1").val();
    	if (null == password || "" == password){
			$("#showmessage").text("密码不能为空!");
			return flag;
		} else if(password.length <4 || password.length >16) {
			$("#showmessage").text("请输入4-16个字符!");
			return flag;
		} else {
			flag = true;
		}
    	return flag;
    }
    
    function validatePassWord2(){
    	var flag = false;
    	var password1 = $("#password1").val();
    	var password2 = $("#password2").val();
    	if (password1 != password2){
			$("#showmessage").text("密码不一致,请重新输入!");
			return;
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
    		})
    }
});
