/**
 * 重置密码
 */
   $(document).ready(function(){
	  
	   $("#oldPwd").blur(function(){
		   checkOldPwd(); 
	   });
	   $("#password").blur(function(){
		   validatePassWord1();
	   });
	   $("#checkPassword").blur(function(){
		   validatePassWord2();
	   });
	   	   
	   $("#submit").click(function(){
		   var flag=true;
		   if(!checkOldPwd()){
			   $("#oldPwd").focus();
			   return;
		   }
		   if(!validatePassWord1()){
			   $("#password").focus();
			   return;
		   }
		   
		   if(!validatePassWord2()){
			   $("#checkPassword").focus();
			   return;
		   }
		   
		  if(flag){
			  var password = $("#password").val();
			  password = strEnc(password,"1","2","3");	
			  AJAX.call(rootPath+"/wechat/member/user/resetPwd?password=" + password, "post", "json", "", false, function(result){
				  if(result.status){
					  Alert.disp_prompt("重置密码成功！");
					  window.location.href = "/page/weixin/login.html";
				  }
			   }); 
		  }
		   
	   });
   });
   
   
   function checkOldPwd(){
	   var flag = false;
	   var oldPwd = $("#oldPwd").val();
	   oldPwd = strEnc(oldPwd,"1","2","3");	
	   AJAX.call(rootPath+"/wechat/member/user/checkOldPwd?oldPwd=" + oldPwd, "post", "json", "", false, function(result){
		  if(!result.status){
			  flag=false;
			  Alert.disp_prompt("原密码输入错误！");
			  return flag;
		  }
		  flag = true
	   });
	   return flag;
   }
   
   function validatePassWord1(){
		var flag = false;
		var password = $("#password").val();
		if (null == password || "" == password){
			Alert.disp_prompt("密码不能为空!");
			return flag;
		} else if(password.length <6 || password.length >16) {
			Alert.disp_prompt("密码需为6-16个字符!");
			return flag;
		} else {
			flag = true;
		}
		return flag;
	}

	function validatePassWord2(){
		var flag = false;
		var password1 = $("#password").val();
		var password2 = $("#checkPassword").val();
		if (password1 != password2){
			Alert.disp_prompt("密码不一致,请重新输入!");
			return flag;
		} else {
			flag = true;
		}
		return flag;
	}