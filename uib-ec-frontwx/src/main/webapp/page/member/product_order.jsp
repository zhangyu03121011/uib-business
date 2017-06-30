<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="${pageContext.request.contextPath  }/static/css/common.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath  }/static/css/user.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${pageContext.request.contextPath  }/static/js/jquery-1.8.2.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath  }/static/js/hover.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath  }/static/js/photo-info.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath  }/static/js/select.js"></script>
    <script type="text/javascript" >
		function FindOrder(){
			if($("#mixFind_value").val()=="商品名称/商品编号/订单编号"){
				$("#mixFind_value").val("");
			}
			$("#form").attr("action","${pageContext.request.contextPath  }/f/member/myProduct").submit();
		}
		
		function deleteOrder(orderNo){
			$.ajax({
				type:"POST",
				url:"${pageContext.request.contextPath  }/f/order/deleteByOrderNo",
				data:{orderNo:orderNo},
				dataType: "json",
				success:function(data){
					if(data.status==false){
						alert(data.msg);
						return false;
					}
					$("#tbody_of_order_list tr[data='"+orderNo+"']").remove();
					alert("订单删除成功");
				}
			});
		}
	</script>
</head>
<body>


<!-- 订单信息 -->
<div class="my_order">
<form action="#" id="form" method="get" >
<ul>
<li class="my_ordertitle font14">我的订单</li>
<%-- <li class="my_orderstate"><span></span>交易提醒：<a href="${pageContext.request.contextPath  }/f/member/myProduct?type=1" target="myFrame">待付款(${count_obligation})</a><a href="${pageContext.request.contextPath  }/f/member/myProduct?type=4" target="myFrame">待收货(${count_received})</a><a href="${pageContext.request.contextPath  }/f/member/myProduct?type=5" target="myFrame" >待评价(${count_evaluate})</a></li> --%>
</ul>
<dl class="order_screening">
<!-- <dt><select name="">
  <option>所有订单</option>
</select></dt> -->
<dt><select name="orderStatus">
  <option value='4' selected>待付款</option>
  <option value='6'>待收货</option>
  <option value='3'>已取消</option>
  <option value='2'>已完成</option>
</select></dt>
<dd>
<div class="order_search">
<input type="text" name="queryParam" id="mixFind_value" value="商品名称/商品编号/订单编号" onfocus="if(value=='商品名称/商品编号/订单编号') {value=''}" onblur="if (value=='') {value='商品名称/商品编号/订单编号'}" />
<script type="text/javascript" >
	var orderStatus = '${param.orderStatus}';
	$("select[name='orderStatus']").val(orderStatus);
 	var queryParam = '${param.queryParam}';
 	if(queryParam!=''){
 		$("input[name='queryParam']").val(queryParam);
 	}
 </script>
<a class="search_btn" id="mixFind"  href="javascript:void(0);" onclick="FindOrder();" target="myFrame">筛选</a>
</div>
</dd>
</dl>
</form>
<!-- 列表 -->
<div class="myorder_list">
<table width="100%" cellspacing="0" cellpadding="0" border="0" class="tb_void">
<colgroup>
<col width="320px">
<col width="110px">
<col width="110px">
<col width="110px">
<col>
</colgroup>
<thead>
<tr>
<td>订单商品</td>
<td>收货人</td>
<td>订单金额</td>
<td>订单状态</td>
<td>操作</td>
</tr>
</thead>

<tbody id="tbody_of_order_list">
<c:forEach items="${list}" var="myOrder">
<tr class="order_info" data="${myOrder.orderNo }">
<td colspan="5"><span>下单时间：<fmt:formatDate value="${myOrder.createDate}" pattern="yyyy-MM-dd  HH:mm:ss" /></span><span>订单编号：${myOrder.orderNo }</span><span>商家：初云汇商城</span></td>
</tr>
<tr data="${myOrder.orderNo }">
<td>
<div class="img-list"> 
<c:forEach items="${myOrder.list_ordertable_item }" var="item"> <%--获取订单List的关联订单项 --%>
		<a href="${pageContext.request.contextPath}/f/product/details?productId=${item.goodsNo}" target="_blank"><img src="${baseFilePath }${item.thumbnail}" title="${item.fullName}"></a>
</c:forEach>
</div>
</td>
<td>${myOrder.consignee }</td>
<td><font class="red font16">¥${myOrder.amount}</font><br><c:choose><c:when test="${myOrder.paymentMethod =='0'}">货到付款</c:when> <c:otherwise>在线支付</c:otherwise> </c:choose></td>
<td class="gray999">
<c:choose >
	<c:when test="${myOrder.orderStatus =='4'}">待付款<br>
	</td>
		<td>
			<a class="blue" target="_blank" href="${pageContext.request.contextPath}/f/order/query/orderDetail?orderNo=${myOrder.orderNo}&userName=${member.username}">查看</a><br>
			<a class="blue" target="_blank" href="${pageContext.request.contextPath}/f/order/toPayView?orderNo=${myOrder.orderNo}&amount=${myOrder.amount}">支付</a>|
			<a class="blue" target="_parent" href="${pageContext.request.contextPath}/f/order/cancelOrder?id=${myOrder.id}">取消</a>
		</td>
   </c:when>
	<c:when test="${myOrder.orderStatus =='6'}">待收货<br>
	</td>
		<td>
			<a class="blue" target="_blank" href="${pageContext.request.contextPath}/f/order/query/orderDetail?orderNo=${myOrder.orderNo}&userName=${member.username}">查看</a><br>
			<!-- <a class="blue" href="#">申请返修/退换货</a><br>
			<a class="again_buybtn" href="#">还要买</a> -->
		</td>
	</c:when>
	<c:when test="${myOrder.orderStatus =='2'}">已完成 <br>
	</td>
		<td>
			<a class="blue" target="_blank" href="${pageContext.request.contextPath}/f/order/query/orderDetail?orderNo=${myOrder.orderNo}&userName=${member.username}">查看</a><br>
			<a class="blue" href="javascript:void(0);" onclick="deleteOrder('${myOrder.orderNo}')">删除</a><br>
			<!-- <a class="again_buybtn" href="#">还要买</a> -->
		</td>
	</c:when>
	<c:when test="${myOrder.orderStatus =='3'}">已取消<br>
	</td>
		<td>
			<a class="blue" target="_blank" href="${pageContext.request.contextPath}/f/order/query/orderDetail?orderNo=${myOrder.orderNo}&userName=${member.username}">查看</a> <!--| <a class="blue" target="_blank" href="#">删除</a><br> -->
			<a class="blue" href="javascript:void(0);" onclick="deleteOrder('${myOrder.orderNo}')">删除</a>
		</td>
	</c:when>
</c:choose>        

</tr>
</c:forEach>
</tbody>
</table>

</div>
</div>
</div>


</body>	
</html>