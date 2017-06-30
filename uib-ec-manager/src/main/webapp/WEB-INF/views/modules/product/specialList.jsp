<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>专题信息管理</title>
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
		<li class="active"><a href="${ctx}/product/special/">专题信息列表</a></li>
		<shiro:hasPermission name="product:special:save"><li><a href="${ctx}/product/special/form">专题信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="special" action="${ctx}/product/special/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>专题名称：</label>
				<form:input path="specialTitle" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li>&nbsp;&nbsp;&nbsp;开始日期范围：&nbsp;<input id="after1BeginDate" name="after1BeginDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
				 onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				--&nbsp;<input id="before1EndDate" name="before1EndDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>&nbsp;&nbsp;
			</li>
			<li>&nbsp;&nbsp;&nbsp;结束日期范围：&nbsp;<input id="after2BeginDate" name="after2BeginDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				--&nbsp;<input id="before2EndDate" name="before2EndDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>&nbsp;&nbsp;
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>专题名称</th>
				<th>推荐商品数</th>
				<th>排序</th>
				<th>开始日期</th>
				<th>结束日期</th>
				<shiro:hasPermission name="product:special:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="special">
			<tr>
				<td><a href="${ctx}/product/special/specialView?id=${special.id}">
					${special.specialTitle}
				</a></td>
				<td>
					${special.productCount}
				</td>
				<td>
					${special.sort}
				</td>
				<td>
					<fmt:formatDate value="${special.beginDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${special.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="product:special:edit"><td>
					
    				<a href="${ctx}/product/special/updateFormView?id=${special.id}">修改</a>
					<a href="${ctx}/product/special/delete?id=${special.id}" onclick="return confirmx('确认要删除该专题信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>