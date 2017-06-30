var curCount = 90;

/**
 * 忘记密码
 */
$(document).ready(function(){
	
	valideFlag = false;
	$("#captchaImage").attr("src",rootPath+"/common/captcha?timestamp="+ (new Date()).valueOf());
	//图片验证码
	$("#captchaImage,#captcha_image_change").click(function(){
		$("#captchaImage").attr("src",rootPath+"/common/captcha?timestamp="+ (new Date()).valueOf());
	});
	
	$("#phone").focusout(function(){
		if(!validatePhone()){
			falg=false;
			return;
		}
		});
	
	$("#captcha_seq").focusout(function(){
		if(!varifyCodeCheck()){
			falg=false;
    		return;
    	}
		});
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
		var forgetPwdForm = $("#forgetPwdForm").serialize();
//		forgetPwdForm = decodeURIComponent(forgetPwdForm,true);

	 if(!validatePhone()){
		falg=false;
		return;
	 }
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
			AJAX.call(rootPath+"/wechat/user/forgetPassword", "post", "json", forgetPwdForm, true, function(result){
				if(result.status){
					Alert.disp_prompt("保存成功！");
					window.location.href = "/page/weixin/login.html";
				} else {
					Alert.disp_prompt("保存失败！");
				}
			}, function(){});
		}
	});
	
});

/**
 * 接收短信验证码
 */
function receiveCodeClick(obj){
	curCount = 90;
	var btn = $(obj);
	btn.removeAttr('onclick');
	var phoneFlag = validatePhone();
	if(phoneFlag && varifyCodeCheck()){
		var phone = $("#phone").val();
		AJAX.call(rootPath+"/wechat/phone/sendCode","post", "json", "phone=" + phone, true,function(data){
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

/**
 * 校验手机号码
 */
function validatePhone(){
	var phoneFlag = false;
	var phone = $("#phone").val();
	var valResult = VALIDATOR.isValidMobel(phone);
	if(valResult){
		AJAX.call(rootPath+"/reg/validatePhone?phone=" + phone, "post", "json", "", false, function(data){
			if (data == false){
				Alert.disp_prompt("该手机用户不存在");
				phoneFlag= false;
			} else {
				phoneFlag= true;
			}
		});
	}else{
		Alert.disp_prompt("不是正确的手机号码");
		$("#phone").focus();
		phoneFlag= false;
	}
	return phoneFlag;
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

//短信验证码校验
function validate(){
	var flag = false;
	var formCode = $("#validateCode").val();
	if (null != formCode && "" != formCode) {
		var checkPhone = validatePhone();
		if(checkPhone) {
			AJAX.call(rootPath + "/wechat/phone/checkMessCode", "post", "json", "messCode=" + formCode + "&type=mess_code_1", false, function(result){
    			if (result.status) {
    				flag = true;
    			} else {
    				Alert.disp_prompt("手机验证码错误");
    				flag=false;
    			}
    		});
    	}
	} else {
		Alert.disp_prompt("手机验证码不能为空");
		flag=false;
	}
	return flag;
}

/**
 * 校验验证码
 */
function varifyCodeCheck(){
	var phone = $("#phone").val();
    var varifyCode = $("#captcha_seq").val();
	var flag = false;
	if(null != varifyCode && "" != varifyCode){
		if(checkCaptcha4Ajax(phone, varifyCode)){
			flag = true;
		}
	}else{
		Alert.disp_prompt("图文验证码不能为空");
		flag=false;
	}	
	return flag;
}


function checkCaptcha4Ajax(phone,varifyCode){
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