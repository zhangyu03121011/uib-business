var curCount = 90;
var phoneFlag = false;
var InterValObj;

/**
 * 接收短信验证码
 */
function receiveCodeClick(obj){
	curCount = 90;
	var btn = $(obj);
	btn.removeAttr('onclick');
	var bid = btn.attr('id');
	if("firstReceiveCode" == bid){
		AJAX.call(rootPath+"/wechat/phone/sendCode4ResetPhone","post", "json", "", true,function(data){
			if(data.status){
				sendMessage(btn);
			}else{
				btn.attr('onclick','receiveCodeClick(this)');
			}
		});
	}else{
		checkPhone();
		if(phoneFlag){
			var phone = $("#newPhone").val();
			AJAX.call(rootPath+"/wechat/phone/sendCode","post", "json", "phone=" + phone, true,function(data){
				if(data.status){
					sendMessage(btn);
				}else{
					btn.attr('onclick','receiveCodeClick(this)');
				}
			});
		}else{
			btn.attr('onclick','receiveCodeClick(this)');
			$("#phoneNum").focus();
		}
		
	}
	
}

/**
 * 校验手机号码
 */
function checkPhone(){
	var phone = $("#newPhone").val();
	var valResult = VALIDATOR.isValidMobel(phone);
	if(valResult){
		AJAX.call(rootPath+"/reg/validatePhone?phone=" + phone, "post", "json", "", false, function(data){
			if (data == false){
				phoneFlag= true;
			} else {
				Alert.disp_prompt("该手机用户已存在");
				phoneFlag= false;
			}
		});
	}else{
		phoneFlag = false;
		Alert.disp_prompt("不是正确的手机号码");
		$("#newPhone").focus();
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
    InterValObj = window.setInterval(function(){SetRemainTime(btn)}, 1000); //启动计时器，1秒执行一次
}

$(document).ready(function(){
	
	$("#nextStep").on("click",function(event) {
		
		var messCode = $("#firstMessCode").val();
		if(isNull(messCode)){
			Alert.disp_prompt("请输入短信验证码");
			$("#firstMessCode").focus;
		}else{
			AJAX.call(rootPath + "/wechat/phone/checkMessCode", "post", "json", "messCode=" + messCode + "&type=mess_code_2", true, function(result){
				if(result.status){
					//终止第一步计时
					window.clearInterval(InterValObj)
					$("#firstStepDiv").hide();
					$("#secondStepDiv").show();
				}else{
					Alert.disp_prompt(result.msg);
				}
			});
		}
	});
	
	$("#submit").on("click",function(event) {
		var firstMessCode = $("#firstMessCode").val();
		var secondMessCode = $("#secondMessCode").val();
		if(isNull(secondMessCode)){
			Alert.disp_prompt("请输入短信验证码");
		}else{
			AJAX.call(rootPath + "/wechat/phone/checkMessCode", "post", "json", "messCode=" + secondMessCode + "&type=mess_code_1", true, function(result){
				if(result.status){
					var phone = $("#newPhone").val();
					AJAX.call(rootPath + "/wechat/user/resetPhone", "post", "json", "firstMessCode=" + firstMessCode + "&secondMessCode=" + secondMessCode + "&phone=" + phone, true, function(result){
						if(result.status){
							window.location.href='account_security.html';
						}else{
							window.location.reload();
							Alert.disp_prompt(result.msg);
						}
					});
				}else{
					Alert.disp_prompt(result.msg);
				}
			});
		}
	});
	
	$("#newPhone").focusout(function(){
		var phone = $("#newPhone").val();
		var valResult = VALIDATOR.isValidMobel(phone);
		if(valResult){
			phoneFlag = true;
		}else{
			phoneFlag = false;
			Alert.disp_prompt("不是正确的手机号码");
		}
	});
	
	//获取当前用户信息
	AJAX.call(rootPath + "/wechat/member/user/getMemberByUserName", "post", "json", "", true, function(result){
		$("#currentPhone").text("当前手机号："+formatPhone(result.phone));
	});
	
});


