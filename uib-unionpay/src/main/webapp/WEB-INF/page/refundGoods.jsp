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
			document.returnGoods_form.submit();
		}
	</script>
	<form id="returnGoods_form" name="returnGoods_form"
		action="${returnGoodsUrl }" method="post">
		<input type="hidden" name="acqCode" id="acqCode" value="${upRequestDto.acqCode }">
		<input type="hidden" name="backEndUrl" id="backEndUrl" value="${upRequestDto.backEndUrl }">
		<input type="hidden" name="charset" id="charset" value="${upRequestDto.charset }">
		<input type="hidden" name="commodityDiscount" id="commodityDiscount" value="${upRequestDto.commodityDiscount }">
		<input type="hidden" name="commodityName" id="commodityName" value="${upRequestDto.commodityName }">
		<input type="hidden" name="commodityQuantity" id="commodityQuantity" value="${upRequestDto.commodityQuantity }">
		<input type="hidden" name="commodityUnitPrice" id="commodityUnitPrice" value="${upRequestDto.commodityUnitPrice }">
		<input type="hidden" name="commodityUrl" id="commodityUrl" value="${upRequestDto.commodityUrl }">
		<input type="hidden" name="customerIp" id="customerIp" value="${upRequestDto.customerIp }">
		<input type="hidden" name="customerName" id="customerName" value="${upRequestDto.customerName }">
		<input type="hidden" name="defaultBankNumber" id="defaultBankNumber" value="${upRequestDto.defaultBankNumber }">
		<input type="hidden" name="defaultPayType" id="defaultPayType" value="${upRequestDto.defaultPayType }">
		<input type="hidden" name="frontEndUrl" id="frontEndUrl" value="${upRequestDto.frontEndUrl }">
		<input type="hidden" name="merAbbr" id="merAbbr" value="${upRequestDto.merAbbr }">
		<input type="hidden" name="merCode" id="merCode" value="${upRequestDto.merCode }">
		<input type="hidden" name="merId" id="merId" value="${upRequestDto.merId }">
		<input type="hidden" name="merReserved" id="merReserved" value="${upRequestDto.merReserved }">
		<input type="hidden" name="orderAmount" id="orderAmount" value="${upRequestDto.orderAmount }">
		<input type="hidden" name="orderCurrency" id="orderCurrency" value="${upRequestDto.orderCurrency }">
		<input type="hidden" name="orderNumber" id="orderNumber" value="${upRequestDto.orderNumber }">
		<input type="hidden" name="orderTime" id="orderTime" value="${upRequestDto.orderTime }">
		<input type="hidden" name="origQid" id="origQid" value="${upRequestDto.origQid }">
		<input type="hidden" name="transTimeout" id="transTimeout" value="${upRequestDto.transTimeout }">
		<input type="hidden" name="transType" id="transType" value="${upRequestDto.transType }">
		<input type="hidden" name="transferFee" id="transferFee" value="${upRequestDto.transferFee }">
		<input type="hidden" name="version" id="version" value="${upRequestDto.version }">
		<input type="hidden" name="signMethod" id="signMethod" value="${signMethod }">
		<input type="hidden" name="signature" id="signature" value="${signature }">
	</form>
</body>
</html>
