<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>供应商管理</title>
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
		<li class="active"><a href="${ctx}/supplier/supplier/">供应商列表</a></li>
		<shiro:hasPermission name="supplier:supplier:save"><li><a href="${ctx}/supplier/supplier/form">供应商添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="supplier" action="${ctx}/supplier/supplier/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>公司名称：</label>
				<form:input path="companyName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>编号</th>
				<th>名称</th>
				<th>联系人</th>
				<th>添加时间</th>
				<shiro:hasPermission name="supplier:supplier:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="supplier">
			<tr>
				<td><a href="${ctx}/supplier/supplier/updateFormView?id=${supplier.id}">
					${supplier.supplierNo}
				</a></td>
				<td>
					${supplier.companyName}
				</td>
				<td>
					${supplier.principalName}
				</td>
				<td>
					<fmt:formatDate value="${supplier.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="supplier:supplier:edit"><td>
    				<a href="${ctx}/supplier/supplier/updateFormView?id=${supplier.id}">修改</a>
					<a href="${ctx}/supplier/supplier/delete?id=${supplier.id}" onclick="return confirmx('确认要删除该供应商管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>