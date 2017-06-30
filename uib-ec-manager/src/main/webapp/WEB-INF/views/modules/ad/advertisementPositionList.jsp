<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>广告位管理</title>
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
		<li class="active"><a href="${ctx}/ad/advertisementPosition/">广告位列表</a></li>
		<shiro:hasPermission name="ad:advertisementPosition:save"><li><a href="${ctx}/ad/advertisementPosition/form">广告位添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="advertisementPosition" action="${ctx}/ad/advertisementPosition/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<th>编码</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="ad:advertisementPosition:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="advertisementPosition">
			<tr>
				<td><a href="${ctx}/ad/advertisementPosition/form?id=${advertisementPosition.id}">
					${advertisementPosition.name}
				</a></td>
				<td>
					${advertisementPosition.code}
				</td>
				<td>
					<fmt:formatDate value="${advertisementPosition.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${advertisementPosition.remarks}
				</td>
				<shiro:hasPermission name="ad:advertisementPosition:edit"><td>
    				<a href="${ctx}/ad/advertisementPosition/updateFormView?id=${advertisementPosition.id}">修改</a>
					<a href="${ctx}/ad/advertisementPosition/delete?id=${advertisementPosition.id}" onclick="return confirmx('确认要删除该广告位吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>