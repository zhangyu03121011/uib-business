var curCount = 120;
/**
 * 忘记支付密码
 */
$(document).ready(function(){
	//显示支付手机号码
	AJAX.call(rootPath + "/wechat/member/user/getMemberByUserName", "post", "json", "", true, function(result){
		$("#phone").html("手机号："+formatPhone(result.phone));
	}, function(){});
	
	//图片验证码
	$("#captchaImage").attr("src",rootPath+"/common/captcha?timestamp="+ (new Date()).valueOf());
	$("#captchaImage,#captcha_image_change").click(function(){
		$("#captchaImage").attr("src",rootPath+"/common/captcha?timestamp="+ (new Date()).valueOf());
	});
	$("#captcha_seq").focusout(function(){
		if(!varifyCodeCheck()){
			falg=false;
    		return;
    	}
	});
	
	//短信验证码
	$("#validateCode").focusout(function(){
		if(!validate()){
			flag = false;
			return;
		}
	});
	$("#password").focusout(function(){
    	if(!validatePassWord()) {
    		flag = false;
			return;
		}
	 });
    
    $("#checkpassword").focusout(function(){
    	if(!validatePassWord1()) {
    		flag = false;
			return;
		}
	 });
	
	$("#submitBtn").click(function(){
		var flag = true;
		var forgetPayPwdForm = $("#forgetPayPwdForm").serialize();
	 if(!varifyCodeCheck()){
		falg=false;
	    return;
	   }
		if(!validate()){
			flag = false;
			return;
		}
	
	   if(!validatePassWord()) {
	       flag = false;
			return;
		}

	   if(!validatePassWord1()) {
	    	flag = false;
			return;
			}
		
		if(flag){
			AJAX.call(rootPath+"/wechat/user/forgetPayPassword", "post", "json", forgetPayPwdForm, true, function(result){
				if(result.status){
					Alert.disp_prompt("保存成功！");
					setTimeout(function(){
						window.location.href = "account_security.html";
					},2000);
				} else {
					Alert.disp_prompt(result.msg);
				}
			}, function(){});
		}
	});
	
});

/**
 * 密码
 */
function validatePassWord(){
	var flag = false;
	var password = $("#password").val();
	if (null == password || "" == password){
		Alert.disp_prompt("密码不能为空");
		return flag;
	} else if(password.length <6 || password.length >16) {
		Alert.disp_prompt("密码请输入6-16个字符");
		return flag;
	} else {
		flag = true;
	}
	return flag;
}

function validatePassWord1(){
	var flag = false;
	var password = $("#password").val();
	var password1 = $("#checkpassword").val();
	if (password != password1){
		Alert.disp_prompt("密码不一致,请重新输入");
		return;
	} else {
		flag = true;
	}
	return flag;
}

//短信验证码校验
function validate(){
	var flag = false;
	var formCode = $("#validateCode").val();
	if (null != formCode && "" != formCode) {
			AJAX.call(rootPath + "/wechat/phone/checkMessCode", "post", "json", "messCode=" + formCode.trim()+ "&type=mess_code_2", false, function(result){
    			if (result.status) {
    				flag = true;
    			} else {
    				Alert.disp_prompt(result.msg);
    				flag=false;
    			}
    		});
    	
	} else {
		Alert.disp_prompt("手机验证码不能为空");
		flag=false;
	}
	return flag;
}

/**
 * 接收短信验证码
 */
function receiveCodeClick(obj){
	curCount = 120;
	var btn = $(obj);
	btn.removeAttr('onclick');
	if(varifyCodeCheck()){
		AJAX.call(rootPath+"/wechat/phone/sendCode4ResetPhone","post", "json", "", true,function(data){
			if(data.status){
				sendMessage(btn);
			}else{
				btn.attr('onclick','receiveCodeClick(this)');
			}
		});
	}else{
		btn.attr('onclick','receiveCodeClick(this)');
	}
}

//timer处理函数
function SetRemainTime(btn) {
    if (curCount == 0) {   
        window.clearInterval(InterValObj);//停止计时器
        btn.attr('onclick','receiveCodeClick(this)');
        btn.text("重新发送");
    }
    else {
        curCount--;
        btn.text(curCount+"秒");
    }
}

//倒计时
function sendMessage(btn) {
	//设置button效果，开始计时	
	btn.text(curCount+"秒");
    InterValObj = window.setInterval(function(){SetRemainTime(btn);}, 1000); //启动计时器，1秒执行一次
}

/**
 * 校验验证码
 */
function varifyCodeCheck(){
    var varifyCode = $("#captcha_seq").val();
	var flag = false;
	if(null != varifyCode && "" != varifyCode){
		if(checkCaptcha4Ajax(varifyCode)){
			flag = true;
		}
	}else{
		Alert.disp_prompt("验证码不能为空");
		flag=false;
	}	
	return flag;
}
function checkCaptcha4Ajax(varifyCode){
	var flag = false;
	AJAX.call(rootPath+"/reg/captcha/check?captcha="+varifyCode, "post", "json", "", false, function(data){
		if (data.status == false){
			$("#captchaImage").attr("src",rootPath+"/common/captcha?timestamp="+ (new Date()).valueOf());
			valideFlag = false;
			Alert.disp_prompt("验证码错误,请重新输入");
		}else if(data.status==true){
			flag = true;
			valideFlag = true;
		}
	});
	return flag;
}
