<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</script>
</head>

<body>
	<script language="javascript">
		window.onload = function() {
			document.pay_form.submit();
		}
	</script>
	<form id="pay_form" name="pay_form"
		action="${gateWayPayUrl }" method="post">
		<input type="hidden" name="acqCode" id="acqCode" value="${unionPay.acqCode }">
		<input type="hidden" name="backEndUrl" id="backEndUrl" value="${unionPay.backEndUrl }">
		<input type="hidden" name="charset" id="charset" value="${unionPay.charset }">
		<input type="hidden" name="commodityDiscount" id="commodityDiscount" value="${unionPay.commodityDiscount }">
		<input type="hidden" name="commodityName" id="commodityName" value="${unionPay.commodityName }">
		<input type="hidden" name="commodityQuantity" id="commodityQuantity" value="${unionPay.commodityQuantity }">
		<input type="hidden" name="commodityUnitPrice" id="commodityUnitPrice" value="${unionPay.commodityUnitPrice }">
		<input type="hidden" name="commodityUrl" id="commodityUrl" value="${unionPay.commodityUrl }">
		<input type="hidden" name="customerIp" id="customerIp" value="${unionPay.customerIp }">
		<input type="hidden" name="customerName" id="customerName" value="${unionPay.customerName }">
		<input type="hidden" name="defaultBankNumber" id="defaultBankNumber" value="${unionPay.defaultBankNumber }">
		<input type="hidden" name="defaultPayType" id="defaultPayType" value="${unionPay.defaultPayType }">
		<input type="hidden" name="frontEndUrl" id="frontEndUrl" value="${unionPay.frontEndUrl }">
		<input type="hidden" name="merAbbr" id="merAbbr" value="${unionPay.merAbbr }">
		<input type="hidden" name="merCode" id="merCode" value="${unionPay.merCode }">
		<input type="hidden" name="merId" id="merId" value="${unionPay.merId }">
		<input type="hidden" name="merReserved" id="merReserved" value="${unionPay.merReserved }">
		<input type="hidden" name="orderAmount" id="orderAmount" value="${unionPay.orderAmount }">
		<input type="hidden" name="orderCurrency" id="orderCurrency" value="${unionPay.orderCurrency }">
		<input type="hidden" name="orderNumber" id="orderNumber" value="${unionPay.orderNumber }">
		<input type="hidden" name="orderTime" id="orderTime" value="${unionPay.orderTime }">
		<input type="hidden" name="origQid" id="origQid" value="${unionPay.origQid }">
		<input type="hidden" name="transTimeout" id="transTimeout" value="${unionPay.transTimeout }">
		<input type="hidden" name="transType" id="transType" value="${unionPay.transType }">
		<input type="hidden" name="transferFee" id="transferFee" value="${unionPay.transferFee }">
		<input type="hidden" name="version" id="version" value="${unionPay.version }">
		<input type="hidden" name="signMethod" id="signMethod" value="${signMethod }">
		<input type="hidden" name="signature" id="signature" value="${signature }">
	</form>
</body>
</html>
