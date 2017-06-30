<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="com.chinagpay.mer.bean.DigestUtil"%>
<%@page import="com.chinagpay.mer.bean.ProcessMessage"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<style type="text/css">
body,p {
	margin: 0;
	padding: 0
}

a {
	color: #003084;
	text-decoration: none
}

a:hover {
	color: #003084;
	text-decoration: underline
}

.success_top {
	width: 100%;
	height: 52px;
	padding: 10px 0;
	border-bottom: solid 5px #003189
}

.success_logo {
	width: 980px;
	margin: 0 auto
}

.success_content {
	width: 980px;
	margin: 0 auto;
	position: relative;
	padding-top: 50px
}

.orderover_content {
	width: 818px;
	border: solid 1px #ccc;
	border-top: solid 2px #1c547d;
	overflow: auto;
	padding: 40px 80px
}

.orderover_success {
	position: absolute;
	top: 95px;
	left: 40px
}

.orderover_tips {
	float: left;
	width: 100%
}

.orderover_tips p {
	line-height: 40px
}

.orderover_tips p a {
	padding-right: 15px
}

.font18 {
	font-size: 18px
}

.line {
	border-bottom: dotted 1px #ccc
}

.red {
	color: #F00
}
</style>
<script type="text/javascript">
	function returnMerchat() {
		document.getElementById("merchantForm").submit();
	}
</script>

</head>

<body>
	<form action="${pageURL }" method="post" name="merchantForm" id="merchantForm">
		<input type="hidden" value="${interfaceName }" name="interfaceName" />
			<input type="hidden" value="${signData }" name="signData" />
			<input type="hidden" value="${tranData }" name="tranData" />
		    <input type="hidden" value="${version }" name="version" />
		<div class="success_top">
			<div class="success_logo">
				<img
					src="${pageContext.request.contextPath}/images/upop_logo.gif" />
			</div>
			<div class="success_content">
				<div class="orderover_content">
					<div class="orderover_success">
						<img src="${pageContext.request.contextPath}/images/hook.png" />
					</div>
					<div class="orderover_tips">
						<p class="font18 line">
							付款成功，订单号：${orderNo } 实付金额:<span class="red">¥${orderAmt}元</span>
						</p>
						<P>
							<a href="javascript:;" onclick="returnMerchat();">返回商城</a><a href="javascript:window.close();">关闭本窗口</a>
						</P>
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>
