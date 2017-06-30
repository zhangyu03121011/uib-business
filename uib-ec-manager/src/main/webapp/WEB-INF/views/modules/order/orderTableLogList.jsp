<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单日志管理</title>
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
		<li class="active"><a href="${ctx}/order/orderTableLog/">订单日志列表</a></li>
		<shiro:hasPermission name="order:orderTableLog:save"><li><a href="${ctx}/order/orderTableLog/form">订单日志添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="orderTableLog" action="${ctx}/order/orderTableLog/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>订单编号：</label>
				<form:input path="orderNo" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>订单编号</th>
				<th>类型</th>
				<th>操作员</th>
				<th>内容</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<shiro:hasPermission name="order:orderTableLog:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="orderTableLog">
			<tr>
				<td><a href="${ctx}/order/orderTableLog/form?id=${orderTableLog.id}">
					${orderTableLog.orderNo}
					
				</a></td>
				<td>
					${orderTableLog.typeName}
					
				</td>
				<td>
					${orderTableLog.operator}
				</td>
				<td>
					${orderTableLog.content}
				</td>
				<td>
					<fmt:formatDate value="${orderTableLog.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${orderTableLog.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="order:orderTableLog:edit"><td>
    				<%-- <a href="${ctx}/order/orderTableLog/updateFormView?id=${orderTableLog.id}">修改</a> --%>
					<a href="${ctx}/order/orderTableLog/delete?id=${orderTableLog.id}" onclick="return confirmx('确认要删除该订单日志吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>