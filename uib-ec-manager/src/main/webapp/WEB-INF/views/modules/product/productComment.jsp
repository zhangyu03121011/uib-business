<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>商品评论</title>
<meta name="decorator" content="default" />
<style type="text/css">
</style>
<script type="text/javascript">
	$(document).ready(function() {

	})
</script>
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/product/product/">商品列表</a></li>
		<li class="active"><a href="${ctx}/product/comment/query?productId=${productId}">商品评论</a></li>
	</ul>
	<br />
	<sys:message content="${message}" />
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>评论者</th>
				<th>评论内容</th>
				<th>评论等级</th>
				<th>购买信息</th>
				<th>购买时间</th>
				<shiro:hasPermission name="product:product:edit">
					<th>操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${productComments}" var="productComment">
				<tr>
					<td>${productComment.memberId}</td>
					<td>${productComment.content}</td>
					<td>${productComment.score}</td>
					<td>${productComment.orderTableItemId}</td>
					<td><fmt:formatDate value="${product.createDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<shiro:hasPermission name="product:product:edit">
					<td><a href="javascript:void(0);">回复</a>&nbsp;<a  href="javascript:void(0);">删除</a></td>
					</shiro:hasPermission>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>