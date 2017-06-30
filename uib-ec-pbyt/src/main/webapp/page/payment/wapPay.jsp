<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.easypay.common.utils.Base64Util" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>wap pay</title>
    <script type="text/javascript">
    <%-- <%
    	String tranData = request.getAttribute("tranData_").toString(); 
		String tranDataBase64 = Base64Util.getBase64(tranData);
	%> --%>
			function wapPay(){
				document.getElementById("payForm").submit();
			}
	</script>
</head>
<body onload="wapPay();">
	<form name="pay" action='${URL}' id="payForm" method="post">
		<input type='hidden' name='interfaceName' value='${interfaceName}'>
		<input type='hidden' name='bankId' value='${bankId}'>
		<input type='hidden' name='tranData' value='${tranData }'> 
		<input type='hidden' name='merSignMsg' value='${merSignMsg }'> 
		<input type='hidden' name='merchantId' value='${merchantId}'>
		<%--<input type='submit' value='提交' onclick=""/>
		 <br/>
		交易数据：<br/>
		<textarea type='text' name='t' cols="100" rows="5"><%=tranData%></textarea> --%>
	</form>
</body>
</html>
