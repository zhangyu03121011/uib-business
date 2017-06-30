$(document).ready(function(){
	
	$("#resetPwd").on("click",function(){
		window.location.href = "resetPwd.html";
	});
	
	$("#resetPhoneNum").on("click",function(){
		window.location.href = "resetPhoneNum.html";
	});
	
	$.ajax({  
        url : rootPath + "/wechat/member/user/getMemberInfo",  
        async : false, 
        type : "POST",  
        success : function(result) {
        	if(result && result.status){
        		var memberInfo = result.data;
        		if(isNotEmpty(memberInfo.payPassword)) {
        			$("#resetPayPwd").text("修改支付密码");
        			$("#resetPayPwd").attr("href","resetPayPwd.html");
        		}else{
        			$("#resetPayPwd").text("设置支付密码");
        			$("#resetPayPwd").attr("href","payPwd.html");
        		}
        	}
        }  
    });
});


