/**
 * 重置支付密码
 */
   $(document).ready(function(){
	   $("#oldPwd").blur(function(){
		   checkOldPayPwd(); 
	   });
	   $("#password").blur(function(){
		   validatePayPassWord1();
	   });
	   $("#checkPassword").blur(function(){
		   validatePayPassWord2();
	   });
	   	   
	   $("#submit").click(function(){
		   if(!checkOldPayPwd()){
			   $("#oldPwd").focus();
			   return;
		   }
		   if(!validatePayPassWord1()){
			   $("#password").focus();
			   return;
		   }
		   
		   if(!validatePayPassWord2()){
			   $("#checkPassword").focus();
			   return;
		   }
		  //原密码
		  var oldPayPwd = strEnc($("#oldPwd").val(),"1","2","3");
		  //新密码
		  var payPwd = strEnc($("#password").val(),"1","2","3");
		 //2次新密码
		  var payPwd2 = strEnc($("#checkPassword").val(),"1","2","3");
		  
		  AJAX.call(rootPath+"/wechat/member/user/resetPayPwd?oldPassword=" + oldPayPwd+"&password=" + payPwd+ "&password2=" + payPwd2, "post", "json", "", false, function(result){
			  if(result.status){
				  Alert.disp_prompt("重置密码成功");
				  setTimeout(function(){
					  window.location.href = "/page/weixin/account_security.html";
				  }, 2000);
			  }
		   }); 
		   
	   });
   });
   
   
   function checkOldPayPwd(){
	   var oldpaypassword = $("#oldPwd").val();
	   oldPayPwd = strEnc(oldpaypassword,"1","2","3");	
	   AJAX.call(rootPath+"/wechat/member/user/checkOldPayPwd?oldpaypassword=" + oldPayPwd, "post", "json", "", false, function(result){
		  if(!result.status){
			  Alert.disp_prompt("原密码输入错误");
			  return false;
		  }
	   });
	   return true;
   }
   
   function validatePayPassWord1(){
		var password = $("#password").val();
		if (null == password || "" == password){
			Alert.disp_prompt("密码不能为空!");
			return false;
		} else if(password.length <6 || password.length >16) {
			Alert.disp_prompt("密码需为6-16个字符");
			return false;
		} 
		return true;
	}

	function validatePayPassWord2(){
		var password1 = $("#password").val();
		var password2 = $("#checkPassword").val();
		if (password1 != password2){
			Alert.disp_prompt("密码不一致,请重新输入");
			return false;
		} 
		return true;
	}