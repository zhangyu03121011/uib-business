/**
 * 个人中心---身份认证2
 */
//获取路径参
var phone=URL_PARAM.getParam("phone");
var userType=URL_PARAM.getParam("userType");
$(document).ready(function(){
	//1.写入手机号  请输入136****8903收到的短信验证码
	$("#show_phone").text("请输入"+formatPhone(phone)+"收到的短信验证码");
	
	//2.发送手机验证码
	AJAX.call(rootPath+"/phone/sendCode?phone="+phone+"&type=3","post", "json", "", false,function(data){
	    if(data.status){
		}else{
			 Alert.disp_prompt(result.msg);
		}
	});
	
	//点击确认按钮
	$("#submit").click(function(){
		//1.校验验证码  
		if(!validateCode()){
			return;
		}
		//2.往后台更新数据
		if(!updatePhone()){
			return;
		}
		// 3.跳转页面
		touch.on('.confirm-btn', 'tap', function(event) {
		    var dia=$.dialog({
		        content: '恭喜您认证成功，尽享会员优惠特权。',
		        button: ["确认"]
		    });
		     dia.on("dialog:action",function(e){
		         window.location.href='./personal.html';
		     });
		});
	});
	
	//点击返回按钮
	$("#return").click(function(){
		window.location.href="javascript:history.back(1)";
	});
});
//更新手机号
function updatePhone(){
	 var flag=false;
	 if(null==userType || ""==userType || "undefined"==userType || "null"==userType){
		 AJAX.call(rootPath + "/memMember/updatePhone?phone="+phone, "post", "json", "", false, function(result){
			 if(result.status){
				 flag=true;
			 }else{
				 Alert.disp_prompt(result.msg);
			 }
		 });
	 }else{
		 AJAX.call(rootPath + "/memMember/updatePhone?phone="+phone+"&userType="+userType, "post", "json", "", false, function(result){
			 if(result.status){
				 flag=true;
			 }else{
				 Alert.disp_prompt(result.msg);
			 }
		 });
	 }
	 
	 return flag;
}

/**
 * 发送短信验证码
 */
function receiveCodeClick(obj){
	curCount = 120;
	var btn = $(obj);
	btn.removeAttr('onclick');
	AJAX.call(rootPath+"/phone/sendCode?phone="+phone+"&type=3","post", "json", "", false,function(data){
	    if(data.status){
		    sendMessage(btn);
		}else{
			Alert.disp_prompt(data.msg);
		    btn.attr('onclick','receiveCodeClick(this)');
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
	AJAX.call(rootPath+"/phone/checkMessCode", "post", "json", "messCode=" + code + "&type=3", false, function(result){
		if (result.status) {
            flag=true;
        } else {
    	    Alert.disp_prompt(result.msg);
    	}
     });
	return flag;
}
