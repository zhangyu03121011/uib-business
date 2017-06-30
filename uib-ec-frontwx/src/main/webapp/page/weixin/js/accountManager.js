/**
 * 账户管理
 */
$(document).ready(function(){
	//获取当前用户信息
	AJAX.call(rootPath + "/wechat/member/user/getMemberByUserName", "post", "json", "", true, function(result){
		if(null!=result){
			$("#username").text(result.username);
			$("#phone").text(result.phone);
		}
	});
});
/*
 * 按钮
 */
function logoff(){
	var bool =window.confirm('确定退出登录？');
	if(bool){
		//退出登录
		AJAX.call(rootPath + "/wechat/user/loginOff", "post", "json", "", true, function(result){
			if(null!=result){
				if(true==result.status){
					//成功
					window.location.href = "home.html";
				}else{
					//退出失败
					Alert.disp_prompt("退出失败！");
				}
			}
		});
	}
}