<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="com.chinagpay.mer.bean.DigestUtil"%>
<%@page import="com.chinagpay.mer.bean.ProcessMessage"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>

<script type="text/javascript">
	function returnMerchat() {
		document.getElementById("merchantForm").submit();
	}
</script>

</head>

<body onload="returnMerchat();">
	<form action="${pageURL }" method="post" name="merchantForm" id="merchantForm">
			<input type="hidden" value="${interfaceName }" name="interfaceName" />
			<input type="hidden" value="${signData }" name="signData" />
			<input type="hidden" value="${tranData }" name="tranData" />
		    <input type="hidden" value="${version }" name="version" />
	</form>
</body>
</html>
