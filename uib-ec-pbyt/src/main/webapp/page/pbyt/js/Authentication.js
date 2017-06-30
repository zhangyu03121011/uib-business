/**
 * 提现---身份认证页面
 */
var balance=0.00;
var cardNo="";
var bankName="";
$(document).ready(function(){
	//接受路径参
	if(window.localStorage){
		balance=localStorage.balance;
		cardNo=localStorage.cardNo;
		applyPhone=localStorage.applyerPhone;
		bankName=localStorage.bankName;
	}
	//1.展示手机号
	//请输入136****8903收到的短信验证码  
	$("#applyPhone").text("请输入"+formatPhone(applyPhone)+"收到的短信验证码");
	
	//2.发送手机验证码
	AJAX.call(rootPath+"/phone/sendCode?phone="+applyPhone+"&type=2","post", "json", "", false,function(data){
	    if(data.status){
		}else{
			 Alert.disp_prompt(result.msg);
		}
	});
});

//3.点下一步按钮 
function submit(){
	//验证验证码 跳转页面
	if(!validateCode()){
		return;
	}
	//页面传值
	if(window.localStorage){
		localStorage.balance = balance;
		localStorage.cardNo=cardNo;
		localStorage.bankName=bankName;
	}
	if(null==balance || ""==balance || undefined==balance || "null"==balance){
		window.location.href="./aftermarket.html";
	}else{
		window.location.href="./withdrawals-bank.html";	
	}
}

/**
 * 发送短信验证码
 */
function receiveCodeClick(obj){
	curCount = 120;
	var btn = $(obj);
	btn.removeAttr('onclick');
	AJAX.call(rootPath+"/phone/sendCode?phone="+applyPhone+"&type=2","post", "json", "", false,function(data){
	    if(data.status){
		    sendMessage(btn);
		}else{
		    btn.attr('onclick','receiveCodeClick(this)');
		    Alert.disp_prompt(data.msg);
		}
	});
}

//timer处理函数
function SetRemainTime(btn) {
    if (curCount == 0) {   
        window.clearInterval(InterValObj);//停止计时器
        btn.attr('onclick','receiveCodeClick(this)');
        btn.text("重新发送");
        btn.removeAttr('disabled');
    }
    else {
        curCount--;
        btn.text(curCount+"秒后重新获取");
    }
}

//倒计时
function sendMessage(btn) {
	//设置button效果，开始计时	
	btn.text(curCount+"秒后重新获取");
	btn.attr('disabled','true');
    InterValObj = window.setInterval(function(){SetRemainTime(btn);}, 1000); //启动计时器，1秒执行一次
}
/**
 * 检验验证码
 */
function validateCode(){
	var flag=false;
	var code=$("#code").val();
	if(""==code || null==code){
		Alert.disp_prompt("验证码不能为空");
		return flag;
	}
	AJAX.call(rootPath+"/phone/checkMessCode", "post", "json", "messCode=" + code + "&type=2", false, function(result){
		if (result.status) {
            flag=true;
        } else {
    	    Alert.disp_prompt(result.msg);
    	}
     });
	return flag;
}
