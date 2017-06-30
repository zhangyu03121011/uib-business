<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="${pageContext.request.contextPath  }/static/css/common.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath  }/static/css/user.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${pageContext.request.contextPath  }/static/js/jquery-1.8.2.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath  }/static/js/hover.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath  }/static/js/photo-info.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath  }/static/js/select.js"></script>
</head>
<body>



<ul>
<li class="my_ordertitle font14">我的保单</li>
<li class="my_orderstate"><span></span>交易提醒：<a href="#">待付款(0)</a><a href="#">待收货(0)</a><a href="#">待评价(0)</a></li>
</ul>
<dl class="order_screening">
<dt><select name="">
  <option>所有订单</option>
</select></dt>
<dt><select name="">
  <option>所有状态</option>
</select></dt>
<dd>
<div class="order_search">
<input type="text" value="商品名称/商品编号/订单编号" />
<a class="search_btn" href="#">筛选</a>
</div>
</dd>
</dl>
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
<td>投保人</td>
<td>被保人姓名</td>
<td>状态</td>

<td>操作</td>
</tr>
</thead>
		<c:if test="${not empty(insuranceList)}">
				<c:forEach var="item" items="${ insuranceList}">
						<tbody>
							<tr class="order_info">
								<td colspan="4"><span>投保开始时间：2014-09-22</span><span>投保结束时间：2014-05-22</span><span>保单号:${item.policyCode }</span></td>
							</tr>
							<tr>
								<td>
									${item.policyholderName }
								</td>
								
								<td><font class="red font16">${item.insuredName}</td>
								<td>
									<c:choose>
											<c:when test="${item.status=='1'}">
												已投保
											</c:when>
											<c:when test="${item.status=='2'}">
												已确认
											</c:when>
											<c:when test="${item.status=='3'}">
												未支付
											</c:when>
											<c:when test="${item.status=='4'}">
												已支付
											</c:when>
											<c:otherwise>
												已生成保单
											</c:otherwise>
									</c:choose>
								</td>
								<td><a class="blue" target="_blank" href="order_detail.html">查看</a> | <a class="blue" target="_blank" href="#">删除</a><br>
									<a class="blue" href="#">申请返修/退换货</a><br>
									<a class="blue" href="#">评价</a><br>
									<a class="again_buybtn" href="#">还要买</a>
								</td>
							</tr>
						</tbody>
				</c:forEach>
		</c:if>
		
</table>
</div>
</div>

</body>	