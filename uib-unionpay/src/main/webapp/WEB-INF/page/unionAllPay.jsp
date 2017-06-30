<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</script>
</head>

<body>
	
	<c:choose>
			<c:when test="${not empty(requestMap['error'])}">
				${requestMap['error']}
			</c:when>
			<c:otherwise>
					<script language="javascript">
						window.onload = function() {
							document.pay_form.submit();
						}
					</script>
					<form id="pay_form" name="pay_form"
						action="${requestFrontUrl }" method="post">
						<input type="hidden" name="txnType" id="txnType" value="${requestMap['txnType']}">
						<input type="hidden" name="frontUrl" id="frontUrl" value="${requestMap['frontUrl'] }">
						<input type="hidden" name="currencyCode" id="currencyCode" value="${requestMap['currencyCode'] }">
						<input type="hidden" name="channelType" id="channelType" value="${requestMap['channelType'] }">
						<input type="hidden" name="merId" id="merId" value="${requestMap['merId'] }">
						<input type="hidden" name="txnSubType" id="txnSubType" value="${requestMap['txnSubType'] }">
						<input type="hidden" name="txnAmt" id="txnAmt" value="${requestMap['txnAmt'] }">
						<input type="hidden" name="version" id="version" value="${requestMap['version'] }">
						<input type="hidden" name="signMethod" id="signMethod" value="${requestMap['signMethod'] }">
						<input type="hidden" name="backUrl" id="backUrl" value="${requestMap['backUrl'] }">
						<input type="hidden" name="certId" id="certId" value="${requestMap['certId'] }">
						<input type="hidden" name="encoding" id="encoding" value="${requestMap['encoding'] }">
						<input type="hidden" name="bizType" id="bizType" value="${requestMap['bizType'] }">
						<input type="hidden" name="signature" id="signature" value="${requestMap['signature'] }">
						<input type="hidden" name="orderId" id="orderId" value="${requestMap['orderId'] }">
						<input type="hidden" name="accessType" id="accessType" value="${requestMap['accessType'] }">
						<input type="hidden" name="txnTime" id="txnTime" value="${requestMap['txnTime'] }">
					</form>
			</c:otherwise>
	</c:choose>
	
</body>
</html>
