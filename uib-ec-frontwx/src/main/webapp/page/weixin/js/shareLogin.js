/**
 * 已验证用户登录
 */
$(document).ready(function(){
	var locationParm = URL_PARAM.getParam("locationParm");
	var productId = URL_PARAM.getParam("productId");
	var rmemberId = URL_PARAM.getParam("rmemberId");

	
	$("#submitBtn").on("click",function(){
		var password = $("#password").val();
		if(!validateUserName()) {
			$("#userName")[0].focus();
			return;
		}
		
		if(!validatePassWord()) {
			$("#password")[0].focus();
			return;
		}

		
		var  enResult = strEnc(password,"1","2","3");		
		
//		ajaxCall("/wechat/user/login",$("#loginForm").serialize(),"post",true,function(data){
		ajaxCall("/wechat/user/login","userName=" + $("#userName").val() + "&password=" + enResult + "&productId="+ productId + "&rmemberId=" + rmemberId , "post",false,function(data){
			if (data.status == true) {
				window.location.href = "shareLogin-success.html";
				return;
			} else if(data.code == 1004) {
				Alert.disp_prompt(data.msg);
				$("#userName").val("");
				$("#password").val("");
				return;
			} else if(data.code == 1005) {
				Alert.disp_prompt(data.msg);
				$("#password").val("");
				$("#password")[0].focus();
				return;
			}
		});
	});



    
    //验证用户名
    function validateUserName() {
    	var flag = false;
    	var userName = $("#userName").val();
    	if(''!= userName && null != userName){
    		if(userName.length <4 || userName.length > 20){ 
				Alert.disp_prompt("请输入4-20个字符!");
				return flag;
			}else{
				flag = true;
			}
		} else {
			Alert.disp_prompt("用户名不能为空!");
			return false;
		}
    	return flag;
    }
    
    /**
     * 验证密码
     */
    function validatePassWord(){
    	var flag = false;
    	var password = $("#password").val();
    	if (null == password || "" == password){
			Alert.disp_prompt("密码不能为空!");
			return flag;
		} else {
			flag = true;
		}
    	return flag;
    }
    
    function ajaxCall(url,data,type,async,success) {
    	$.ajax({
    		  type: type,
    		  url: rootPath + url,
    		  data: data,
    		  async : async,
    		  dataType: 'json',
    		  timeout: 300,
    		  context: $('body'),
    		  success: success,
    		  error: function(xhr, type){
    		  }
    		});
    }
});
