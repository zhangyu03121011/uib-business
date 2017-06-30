<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员等级管理</title>
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
		<li class="active"><a href="${ctx}/mem/memRank/">会员等级列表</a></li>
		<shiro:hasPermission name="mem:memRank:save"><li><a href="${ctx}/mem/memRank/form">会员等级添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="memRank" action="${ctx}/mem/memRank/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="32" class="input-medium"/>
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
				<th>贡献值（元）</th>
				<!-- <th>是否默认</th> -->
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="mem:memRank:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="memRank">
			<tr>
				<shiro:hasPermission name="mem:memRank:edit">
				<td><a href="${ctx}/mem/memRank/updateFormView?id=${memRank.id}">
					${memRank.name}
				</a></td></shiro:hasPermission>
				<td>
					${memRank.amount}
				</td>
				<%-- <td>
					${fns:getDictLabel(memRank.isDefult, 'default_flag', '')}
				</td> --%>
				<td>
					<fmt:formatDate value="${memRank.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${memRank.remarks}
				</td>
				<shiro:hasPermission name="mem:memRank:edit"><td>
    				<a href="${ctx}/mem/memRank/updateFormView?id=${memRank.id}">修改</a>
    				<c:if test="${memRank.count < 0}">
    					<a href="${ctx}/mem/memRank/delete?id=${memRank.id}" onclick="return confirmx('确认要删除该会员等级吗？', this.href)">删除</a>
    				</c:if>
    				<c:choose>
    					<c:when test="${memRank.count > 0}">
    						<font color="gray">删除</font>
    					</c:when>
    					<c:otherwise>
    						<a href="${ctx}/mem/memRank/delete?id=${memRank.id}" onclick="return confirmx('确认要删除该会员等级吗？', this.href)">删除</a>
    					</c:otherwise>
    				</c:choose>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>