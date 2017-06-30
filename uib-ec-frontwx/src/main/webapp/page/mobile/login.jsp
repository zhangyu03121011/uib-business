<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<title>注册</title>
<link href="${pageContext.request.contextPath}/static/css/public.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/page/mobile/js/alert.js"></script>
<!-- <script language="javascript" for="window" event="onload">   -->
<script language="javascript">  
   /*  if(document.readyState=="complete"){   */
        var error = "${requestScope.error}";   
    	
    	if(error != "" & error != null){ 
    		alert(error); 
    		/* Alert.disp_prompt(error); */
    	}
   /*  }  */
    function doSubmit() {
		var username = document.getElementById("userName").value;
		var password = document.getElementById("password").value;
		
		if (null == username || '' == username) {
			alert('用户名不能为空!');
			return false;
		}
		if (null == password || '' == password) {
			alert('密码不能为空!');
			return false;
		}
		
		$("#LoginForm").submit();
	}
</script>  
</head>

<body>
<!-- 提示区域 -->
<div class="dow-pop">
    <div class="close-box"><img src="${pageContext.request.contextPath}/static/images/close.png"/></div>
    <div class="title-box">华夏通移动商城注册送优惠券</div>
    <div class="dow-app"><a href="#">下载APP</a></div>
</div>
<!-- 头部 -->
<div class="sn-nav">
    <div class="sn-nav-back"><a class="sn-iconbtn" href="javascript:history.back(1)">返回</a></div>
    <div class="sn-nav-title">验证已注册用户</div>
</div>
<!-- 注册框区域 -->
<div class="main">
	<form action="${pageContext.request.contextPath}/f/mobile/user/login" method="post" name="LoginForm" id="LoginForm">
    <div class="common-wrapper">    	
        <!-- 用户名 -->
        <div class="reg-name mb1"><input name="userName" id="userName" type="text" placeholder="请输入您的用户名/手机号" value="${param.username}" ></div>
        <!-- 密码 -->
        <div class="reg-name mb1"><input name="password" id="password" type="password" placeholder="6-16位字母和数字密码" value="${param.password}" ></div>
        <!-- 按钮 -->
        <div class="reg-btn mb1 w100"><input type="submit" value="确认" onclick="return doSubmit();"></div>
        <input type="hidden" name="rMemberId" value="${param.rMemberId}"/>
		<input type="hidden" name="productId" value="${param.productId }"/>
    </div>
    </form>
</div>
</body>
</html>
