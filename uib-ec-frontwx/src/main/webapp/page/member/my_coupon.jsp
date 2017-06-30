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
			$("#form").attr("action","${pageContext.request.contextPath  }/f/member/myProduct").submit();
		}
	</script>
</head>
<body>
<div class="my_order">
<div class="my_coupons">
	<ul>
		<li id="one1" onclick="setTab('one',1,3)" class="hover">未用优惠券</li>
		<li id="one2" onclick="setTab('one',2,3)">已用优惠券</li>
		<li id="one3" onclick="setTab('one',3,3)">已过期优惠券</li>
	</ul>
</div>
<div class="my_couponscn top_line">
	<!-- 未用 -->
	<div id="con_one_1">
			<table width="100%" cellspacing="0" cellpadding="0" border="0" class="tb_void">
				<colgroup>
					<col width="120px">
					<col width="120px">
					<col width="120px">
					<col width="120px">
					<col width="150px">
					<col>
				</colgroup>
				<thead>
					<tr class="my_couponscn_inof">
						<th>编号</th>
						<th>面值</th>
						<th>所需消费余额</th>
						<th>有效期</th>
						<th>优惠券来源</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${listNotUsedCoupon}" var="myCoupon">
							<tr>
								<td>${myCoupon.id}</td>
								<td>
								<c:choose>
									<c:when test="${myCoupon.facePrice!=null}">
										¥${myCoupon.facePrice}
									</c:when>
									<c:otherwise>
										-
									</c:otherwise>
								</c:choose>
								</td>
								<td>
								<c:choose>
									<c:when test="${myCoupon.needConsumeBalance!=null}">
										¥${myCoupon.needConsumeBalance}
									</c:when>
									<c:otherwise>
										-
									</c:otherwise>
								</c:choose>
								</td>
								<td><fmt:formatDate value="${myCoupon.beginDate}" pattern="yyyy-MM-dd" />至<br><fmt:formatDate value="${myCoupon.endDate}" pattern="yyyy-MM-dd" /></td>
								<td>${myCoupon.couponSource}</td>
							</tr>
					</c:forEach>
				</tbody>
			</table>
	</div>
	<!-- 已用 -->
	<div id="con_one_2" style="display:none">
		<table width="100%" cellspacing="0" cellpadding="0" border="0" class="tb_void">
			<colgroup>
				<col width="120px">
				<col width="120px">
				<col width="120px">
				<col width="120px">
				<col width="150px">
			<col>
			</colgroup>
			<thead>
				<tr class="my_couponscn_inof">
					<th>编号</th>
					<th>面值</th>
					<th>所需消费余额</th>
					<th>有效期</th>
					<th>优惠券来源</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${listUsedCoupon}" var="myCoupon">
							<tr>
								<td>${myCoupon.id}</td>
								<td>
								<c:choose>
									<c:when test="${myCoupon.facePrice != null }">
										¥${myCoupon.facePrice}
									</c:when>
									<c:otherwise>
										-
									</c:otherwise>
								</c:choose>
								</td>
								<td>
								<c:choose>
									<c:when test="${myCoupon.needConsumeBalance!=null}">
										¥${myCoupon.needConsumeBalance}
									</c:when>
									<c:otherwise>
										-
									</c:otherwise>
								</c:choose>
								</td>
								<td><fmt:formatDate value="${myCoupon.beginDate}" pattern="yyyy-MM-dd" />至<br><fmt:formatDate value="${myCoupon.endDate}" pattern="yyyy-MM-dd" /></td>
								<td>${myCoupon.couponSource}</td>
							</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<!-- 已过期 -->
	<div id="con_one_3" style="display:none">
		<table width="100%" cellspacing="0" cellpadding="0" border="0" class="tb_void">
			<colgroup>
				<col width="120px">
				<col width="120px">
				<col width="120px">
				<col width="120px">
				<col width="150px">
				<col>
			</colgroup>
			<thead>
				<tr class="my_couponscn_inof">
					<th>编号</th>
					<th>面值</th>
					<th>所需消费余额</th>
					<th>有效期</th>
					<th>优惠券来源</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${listEndDateCoupon}" var="myCoupon">
							<tr>
								<td>${myCoupon.id}</td>
								<td>
								<c:choose>
									<c:when test="${myCoupon.facePrice!=null}">
										¥${myCoupon.facePrice}
									</c:when>
									<c:otherwise>
										-
									</c:otherwise>
								</c:choose>
								</td>
								<td>
								<c:choose>
									<c:when test="${myCoupon.needConsumeBalance!=null}">
										¥${myCoupon.needConsumeBalance}
									</c:when>
									<c:otherwise>
										-
									</c:otherwise>
								</c:choose>
								</td>
								<td><fmt:formatDate value="${myCoupon.beginDate}" pattern="yyyy-MM-dd" />至<br><fmt:formatDate value="${myCoupon.endDate}" pattern="yyyy-MM-dd" /></td>
								<td>${myCoupon.couponSource}</td>
							</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
</div>
</body>
</html>