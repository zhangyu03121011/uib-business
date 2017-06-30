/**
 * 个人中心---若为c2或者分销商 点设置
 */
$(document).ready(function(){
	
	//接收路径参
	if(window.localStorage){
 		p_name=localStorage.p_name;
 		p_avatar=localStorage.p_avatar;
 		p_rankName=localStorage.p_rankName;
 		p_phone = localStorage.p_phone;
 		//清空
// 		localStorage.p_name=null;
// 		localStorage.p_avatar=null;
// 		localStorage.p_rankName=null;
// 		localStorage.p_phone=null;
	}

	//写入 用户名、头像、会员等级、手机号码
	$("#image").attr("src",p_avatar);
	$("#name").text("用户名："+p_name);
	$("#rankName").text("会员等级："+p_rankName);
	$("#phone").text(formatPhone(p_phone));
    
	//点击更换手机号,跳转页面
	$("#change_phone").click(function(){
		window.location.href="./Authentication-two.html";
	});
});