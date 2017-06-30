var curCount = 90;
var url=window.location.href;

/**
 * 接收短信验证码
 */
function receiveCodeClick(obj){
	curCount = 90;
	var btn = $(obj);
	btn.removeAttr('onclick');
	if(checkPhone()){
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
		$("#phone").focus();
	}
}

/**
 * 校验手机号码
 */
function checkPhone(){
	var phoneFlag = false;
	var phone = $("#phone").val();
	var valResult = VALIDATOR.isValidMobel(phone);
	if(valResult){
		AJAX.call(rootPath+"/reg/validatePhone?phone=" + phone, "post", "json", "", false, function(data){
			if (data){
				Alert.disp_prompt("该手机已注册");
				phoneFlag= false;
			} else {
				phoneFlag= true;
			}
		});
	}else{
		phoneFlag = false;
		Alert.disp_prompt("手机号码格式错误");
		$("#phone").focus();
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

/**
 * 图形验证码校验
 */
function checkImgCode(){
    var varifyCode = $("#captcha_seq").val();
    var flag = false;
    if (null == varifyCode || varifyCode == ""){
		Alert.disp_prompt("图文验证码不能为空!");
		return flag;
	}
	
	if(!checkCaptcha4Ajax(varifyCode)){
		$("#captcha_seq").focus();
		return flag;
	} else {
		flag = true;
	}
	return flag;
}

function checkCaptcha4Ajax(varifyCode){
	var flag = false;
	AJAX.call(rootPath + "/reg/captcha/check","post","json",{ captchaId:null,captcha:varifyCode },false,function(data){
		if (data.status == false){
			$("#captchaImage").attr("src",rootPath + "/common/captcha?timestamp="+ (new Date()).valueOf());
			Alert.disp_prompt("验证码错误,请重新输入!");
		}else if(data.status==true){
			flag = true;
		}
	});
	return flag;
}

//验证用户名是否存在
function checkUserName() {
	var flag = false;
	var userName = $("#userName").val();
	if(isNotEmpty(userName)){
		if(userName.length <4 || userName.length > 20){ 
			Alert.disp_prompt("请输入4-20个字符!");
			return flag;
		}
//		var validUserName = VALIDATOR.isValidUsername(userName);
		if(!VALIDATOR.isValidUsername(userName)){
			Alert.disp_prompt("用户名格式不正确");
			return flag;
		}
		AJAX.call(rootPath + "/reg/validateUserName","post","json",{username:userName},false,function(data){
			if (data){
				Alert.disp_prompt("用户名已存在!");
				flag = false;
			} else {
				flag = true;
			}
		});
	} else {
		Alert.disp_prompt("用户名不能为空!");
		flag = false;
	}
	return flag;
}

//短信验证码校验
function checkMessCode(){
	var flag = false;
	var formCode = $("#validateCode").val();
	if (isNotEmpty(formCode)) {
		AJAX.call(rootPath + "/wechat/phone/checkMessCode", "post", "json", "messCode=" + formCode + "&type=mess_code_1", false, function(result){
			if (result.status) {
				flag = true;
			} else {
				Alert.disp_prompt(result.msg);
				flag = false;
			}
		});
	} else {
		Alert.disp_prompt("短信验证码不能为空!");
		flag = false;
	}
	return flag;
}

function validatePassWord1(){
	var flag = false;
	var password = $("#password1").val();
	if (null == password || "" == password){
		Alert.disp_prompt("密码不能为空!");
		return flag;
	} else if(password.length <6 || password.length >16) {
		Alert.disp_prompt("请输入6-16个字符!");
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
		Alert.disp_prompt("密码不一致,请重新输入!");
		return flag;
	} else {
		flag = true;
	}
	return flag;
}

$(document).ready(function(){
	$("#submitBtn").on("submit",function(e) {
		e.preventDefault();
	});
	
	/**
	 * 注册提交
	 */
	touch.on('#submitBtn',"tap",function(e){
		//取消表单默认提交事件
		e.preventDefault();
		if(!checkUserName()) {
			$("#userName").focus();
			return false;
		}
		
		if(!checkPhone()) {
			$("#phone").focus();
			return false;
		}
		
		if(!checkImgCode()) {
			$("#captcha_seq").focus();
			return false;
		}
		
		if(!checkMessCode()) {
			$("#validateCode").focus();
			return false;
		}
		
		if(!validatePassWord1()) {
			$("#password1").focus();
			return false;
		}
		
		if(!validatePassWord2()) {
			$("#password2").focus();
			return false;
		}
		
		if($('#agree').is(':checked')==false) {
			Alert.disp_prompt("请同意相关协议");
			return false;
		}
		var password1 = $("#password1").val();
		var  enResult = strEnc(password1,"1","2","3");	
		$("#password").val(enResult);
		AJAX.call(rootPath + "/wechat/user/save","post","json",$("#registerForm").serialize(),false,function(data){
			if (data.status == true) {
				window.location.href = "home.html";
			} else{
				Alert.disp_prompt(data.msg);
			}
		});		
	})
	
	
//	$("#captcha_seq").blur(function(){
//		checkImgCode();
//	});
//	
//	$("#userName").blur(function(){
//		checkUserName();
//	});
//	
//	$("#validateCode").blur(function(){
//		checkMessCode();
//	});
	
	/**
	 * 跟换图文验证码
	 */
	$("#captchaImage").attr("src",rootPath + "/common/captcha?timestamp="+ (new Date()).valueOf());
	$("#captchaImage,#captcha_image_change").on("click",function() {
		$("#captchaImage").attr("src",rootPath + "/common/captcha?timestamp="+ (new Date()).valueOf());
		$("#captcha_seq").val("");
		$("#captcha_seq").focus();
	});
    
});
