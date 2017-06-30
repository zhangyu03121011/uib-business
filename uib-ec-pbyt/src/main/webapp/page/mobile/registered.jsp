<%@page language="java" contentType="text/html;charset=UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<title>注册</title>
<link href="${pageContext.request.contextPath}/static/css/public.css"
	rel="stylesheet" type="text/css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/page/mobile/js/common.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/page/mobile/js/alert.js"></script>
<script type="text/javascript">
	var valideFlag = false; //验证码是否通过flag
	var nameExistFlag = 0; //用户名是否存在flag
	var InterValObj; //timer变量，控制时间
	var count = 120; //间隔函数，1秒执行
	var curCount;//当前剩余秒数
	var phoneFlag = false; //手机号码是可用
	$()
			.ready(
					function() {
						// 更换验证码
						$("#captchaImage,#captcha_image_change")
								.click(
										function() {
											valideFlag = false;
											$("#captchaImage")
													.attr(
															"src",
															"${pageContext.request.contextPath}/f/common/captcha?captchaId=${captchaId}&timestamp="
																	+ (new Date())
																			.valueOf());
										});
						$("#captcha_seq").change(function(){
					    	varifyCodeCheck();
					    });	
						console.info("<%=request.getSession().getId()%>");
						$("#username").focusout(function() {
							var username = $("#username").val();
							$.post("${pageContext.request.contextPath  }/f/reg/validateUserName",{ username:username },function(data){
								nameExistFlag = 1;
								if (data == "true"){
									flag = false ;
									Alert.disp_prompt("该帐号已存在!");
									nameExistFlag = 2;
								}
							});
						});
						$("#username").keyup(function() {
							if(''!=$("#username").val()){
								var username =	$("#username").val();
								$.post("${pageContext.request.contextPath  }/f/reg/validateUserName",{ username:username },function(data){
									if (data == "true"){
										Alert.disp_prompt("该帐号已存在!");
									}else{
										$("#showmessage").html("<span style='color:green'>该帐号可用</span>"); 
									} 
								});
							}else{
								$("#showmessage").html(""); 
							}
						});
						var errorMsg="${requestScope.error}";
						if(errorMsg!=null && errorMsg!=''){
							nameExistFlag=0;
							Alert.disp_prompt(errorMsg);
						}
						
				});
	
	function usenameCheck(){
		var username =	$("#username").val();
		if(username==null || username==''){
			Alert.disp_prompt("用户名为空,请输入!");
			return false;
		}
		if(nameExistFlag==2){
			Alert.disp_prompt("账号已存在,请重新输入");
			return false;
		}
		return true;
	}
	
	function phoneBlankCheck(){
		var phone=$("#phone").val();
		if(phone==null || phone==''){
			Alert.disp_prompt("手机号码为空，请输入!");
			return false;
		}
		if('' != phone)	{ 
			var isMobile=/^(?:13\d|15\d|18\d)\d{5}(\d{3}|\*{3})$/; //手机号码验证规则
			if(!isMobile.test(phone)){ 
				Alert.disp_prompt("手机号码不正确");
				return false;
			}
		}
		
		phoneFlag = false;
		AJAX.call("/f/reg/validatePhone?phone=" + phone, "post", "json", "", false, function(data){
			if (data == false){
				phoneFlag = true ;
			} else {
				Alert.disp_prompt("该手机用户已存在");
				phoneFlag = false;
			}
		});
		return phoneFlag;
	}
	
	function sendCode(){
		var validateCode = document.getElementById("captcha_seq").value;
		if (null == validateCode || '' == validateCode){
			Alert.disp_prompt('图片验证码不能为空!');
			return false;
		}
		if(!valideFlag||!varifyCodeCheck()){
			return;
		}
		if(!usenameCheck()){
			return false;
		}
		if(!phoneBlankCheck()){
			return false;
		}
		var phone = $("#phone").val();
		$.post("${pageContext.request.contextPath  }/f/mobile/user/wap/validateCode",{ email:'',phone:phone,sendType:'1',captcha:validateCode},function(data){
			if (data == "true"){
				Alert.disp_prompt("手机验证码已发送");
			}   
		});
		sendMessage();
	}
	
	function doSubmit() {
		var username = document.getElementById("username").value;
		var password = document.getElementById("password").value;
		var password1 = document.getElementById("password1").value;
		var phone = document.getElementById("phone").value;
		var validateCode = document.getElementById("validateCode").value;
		//var imageCode = document.getElementById("imageCode");

		if (null == username || '' == username) {
			Alert.disp_prompt.disp_prompt("用户名不能为空!");
			return false;
		}
		if (null == password || '' == password || password.length<6) {
			Alert.disp_prompt("密码不能为空或小于六个字符!");
			return false;
		}
		if (null == password1 || '' == password1 || password1.length<6) {
			Alert.disp_prompt("密码不能为空或小于六个字符!");
			return false;
		}
		
		if(password1!=password){
			Alert.disp_prompt("密码不一致,请重新输入!");
			return false;
		}

		if (!phoneBlankCheck()){
			return false;
		}

		if (null == validateCode || '' == validateCode) {
			Alert.disp_prompt("手机验证码不能为空!");
			return false;
		}

		if (!varifyCodeCheck()){
			return false;
		}

		$("#registerForm").submit();
	}
	
	function varifyCodeCheck(){
    	var captchaId = "<%=request.getSession().getId()%>";
    	<%-- console.info(<%=request.getSession.getAttribute("sessionid")%>); --%>
        var varifyCode = $("#captcha_seq").val();
    	var flag = false;
		if(varifyCode=='' || varifyCode==null || varifyCode.length<4 || !/^[A-Za-z]{4}$/.test(varifyCode)){
			//$("#red_tip_captcha").show();
			return flag;
		}
		if(checkCaptcha4Ajax(captchaId, varifyCode)){
			//$("#red_tip_captcha").hide();
			flag = true;
		}else{
			//$("#red_tip_captcha").show();
		}
		return flag;
	}
    
    function checkCaptcha4Ajax(captchaId,varifyCode){
    	var flag = false;
    	$.ajaxSetup({
            async : false  
        }); 
		$.post("${pageContext.request.contextPath  }/f/reg/captcha/check",{ captchaId:captchaId,captcha:varifyCode },function(data){
			if (data.status == false){
				$("#captchaImage").attr(
						"src",
						"${pageContext.request.contextPath}/f/common/captcha?captchaId=${captchaId}&timestamp="
								+ (new Date()).valueOf());
				valideFlag = false;
				Alert.disp_prompt("验证码错误,请重新输入!");
			}else if(data.status==true){
				flag = true;
				valideFlag = true;
			}
		});
		return flag;
    }
    
    function addCookie(name,value,expiresHours){ 
    	var cookieString=name+"="+escape(value); 
    	//判断是否设置过期时间 
    	if(expiresHours>0){ 
    	var date=new Date(); 
    	date.setTime(date.getTime+expiresHours*3600*1000); 
    	cookieString=cookieString+"; expires="+date.toGMTString(); 
    	} 
    	document.cookie=cookieString; 
    }
    function getCookie(name){ 
    	var strCookie=document.cookie; 
    	var arrCookie=strCookie.split("; "); 
    	for(var i=0;i<arrCookie.length;i++){ 
    		var arr=arrCookie[i].split("="); 
    		if(arr[0]==name)return arr[1]; 
    	} 
    	return ""; 
    }
    
  //timer处理函数
	function SetRemainTime() {
	            if (curCount == 0) {                
	                window.clearInterval(InterValObj);//停止计时器
	                $("#btnSendCode").attr("onclick","sendCode();");//启用点击事件
	                $("#btnSendCode").text("重新发送验证码");
	            }
	            else {
	                curCount--;
	                $("#btnSendCode").text(curCount + "秒");
	            }
	 }
  
	function sendMessage() {
		  curCount = count;
		//设置button效果，开始计时
		     $("#btnSendCode").removeAttr("onclick");
		     $("#btnSendCode").val(curCount + "秒");
		     InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
	}
    
</script>

</head>

<body>
	<!-- 提示区域 -->
	<div class="dow-pop">
		<div class="close-box">
			<img src="${pageContext.request.contextPath}/static/images/close.png" />
		</div>
		<div class="title-box">华夏通移动商城注册送优惠券</div>
		<div class="dow-app">
			<a href="#">下载APP</a>
		</div>
	</div>
	<!-- 头部 -->
	<div class="sn-nav">
		<div class="sn-nav-back">
			<a class="sn-iconbtn" href="javascript:history.back(1)">返回</a>
		</div>
		<div class="sn-nav-title">注册</div>
	</div>
	<!-- 注册框区域 -->
	<form
		action="${pageContext.request.contextPath}/f/mobile/user/register"
		method="post" name="registerForm" id="registerForm">
		<div class="main">
			<div class="common-wrapper">
				<!-- 用户名 -->
				<div class="reg-name mb1">
					<input name="userName" id="username" type="text"
						placeholder="请输入用户名" value="${param.username }">
				</div>
				
				<!-- 图形验证码 -->
				<div class="reg-name mb1">
					<div class="codetwo-left">
						<input name="captcha" id="captcha_seq" type="text" placeholder="图形验证码">
					</div>
					<div class="codetwo-right">
						<img id="captchaImage" title="换一张"
							src="${pageContext.request.contextPath}/f/common/captcha?captchaId=${captchaId}" /><a
							href="javascript:void(0)" id="captcha_image_change">换一张</a>
					</div>
				</div>
				
				
				<!-- 手机号 -->
				<div class="reg-name mb1">
					<input name="phone" id="phone" type="text" placeholder="请输入11位手机号" value="${param.phone }">
				</div>
				<!-- 手机验证码 -->
				<div class="reg-name mb1">
					<div class="code-left">
						<input name="validateCode" id="validateCode" type="text"
							placeholder="请输入短信验证码">
					</div>
					<div class="code-right">
						<a href="javascript:void(0);" onclick="sendCode();" id="btnSendCode" style="font-size: x-large;">获取验证码</a>
					</div>
				</div>
				<!-- 密码 -->
				<div class="reg-name mb1">
					<input name="password" id="password" type="password" placeholder="6-16位字母和数字密码">
				</div>
				<!-- 密码 -->
				<div class="reg-name mb1">
					<input name="password1" id="password1" type="password"
						placeholder="确认密码">
				</div>

				<!-- 按钮 -->
				<div class="reg-btn mb1 w100">
					<input type="submit" value="立即注册" onclick="return doSubmit();">
				</div>
				<!-- 提示语 -->
				<div class="reg-name reg-tip">无需注册，下载客户端使用QQ或微信直接登录</div>
				<input type="hidden" name="rMemberId" value="${param.rMemberId}"/>
				<input type="hidden" name="productId" value="${param.productId }"/>
			</div>
		</div>
	</form>
</body>
</html>

