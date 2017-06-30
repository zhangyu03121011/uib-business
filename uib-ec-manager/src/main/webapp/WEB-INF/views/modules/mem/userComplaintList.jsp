<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员投诉管理</title>
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
		<li class="active"><a href="${ctx}/mem/userComplaint/">会员投诉列表</a></li>
	<%-- <shiro:hasPermission name="mem:userComplaint:save"><li><a href="${ctx}/mem/userComplaint/form">会员投诉添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="userComplaint" action="${ctx}/mem/userComplaint/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>投诉类型：</label>
				<form:select path="feedbackType" class="input-medium">
					<form:option value="" label=""/>
					<form:option value="" label="全部"/>
					<c:forEach items="${listComplaint_Type}" var="Compaint_Type">
					<form:option value="${Compaint_Type.key}" label="${Compaint_Type.value}"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label>标题：</label>
				<form:input path="title" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>用户名</th>
				<th>投诉类型</th>
				<th>标题</th>
				<th>投诉内容</th>
				<th>投诉时间</th>
				<th>回复状态</th>
				<th>回复时间</th>
				<shiro:hasPermission name="mem:userComplaint:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userComplaint">
			<tr>
				<td>
					<a href="${ctx}/mem/userComplaint/replyView?id=${userComplaint.id}">${userComplaint.username}</a>
				</td>
				<td>
					<c:if test="${userComplaint.feedbackType == '1'}">商品</c:if>
					<c:if test="${userComplaint.feedbackType == '2'}">服务</c:if>
					<c:if test="${userComplaint.feedbackType == '3'}">其它</c:if>		
				</td>
				<td>
					${userComplaint.title}
				</td>
				<td>
					${userComplaint.describeinfo}
				</td>
				<td>
					<fmt:formatDate value="${userComplaint.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<c:if test="${userComplaint.solutionState != '1'}"><font color="red">未答复</font></c:if>
					<c:if test="${userComplaint.solutionState == '1'}"><font color="green">已答复</font></c:if>
				</td>
				<td>
					<fmt:formatDate value="${userComplaint.solutionTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="mem:userComplaint:edit"><td>
					<%-- <a href="${ctx}/mem/userComplaint/updateFormView?id=${userComplaint.id}">修改</a> --%>
					<a href="${ctx}/mem/userComplaint/replyView?id=${userComplaint.id}">查看</a>
					<a href="${ctx}/mem/userComplaint/delete?id=${userComplaint.id}" onclick="return confirmx('确认要删除该会员投诉吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>