<%@ page contentType="text/html;charset=UTF-8" import="com.uib.ecmanager.common.enums.*"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>

	<title>优惠券管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/coupon/coupon/">优惠券列表</a></li>
		<shiro:hasPermission name="coupon:coupon:save"><li><a href="${ctx}/coupon/coupon/form">优惠券添加</a></li></shiro:hasPermission>
	</ul>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>优惠券名称</th>
				<th>优惠码</th>
				<th>是否已使用</th>
				<th>用户名</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="couponCode">
			<tr>
				<td>
					${couponCode.coupon.name}
				</td>
				<td>
					${couponCode.code}
					
				</td>
				<td>
				<c:choose>
					<c:when test="${couponCode.isUsed != 0}">
						是
					</c:when>
					<c:otherwise>
						否
					</c:otherwise>
				</c:choose>
					
				</td>
				<td>
					${couponCode.memMember.username}
					
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
	<div class="pagination">${page}</div>
</body>
</html>