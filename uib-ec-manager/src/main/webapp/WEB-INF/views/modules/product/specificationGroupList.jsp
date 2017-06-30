<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品规格管理</title>
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
		<li class="active"><a href="${ctx}/product/specificationGroup/">规格列表</a></li>
		<shiro:hasPermission name="product:specificationGroup:save"><li><a href="${ctx}/product/specificationGroup/form">添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="specificationGroup" action="${ctx}/product/specificationGroup/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>分类：</label>
			<sys:treeselect id="productCategoryId" name="productCategoryId" value="${productCategory.id}" labelName="productCategory.name" labelValue="${productCategory.name}"
			  title="商品分类" url="/product/productCategory/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>
			<li><label>类型：</label>
				<form:select path="contentType" class="input-xlarge " id="specification_content_type">
					<form:option value="" label="所有" htmlEscape="false"/>
					<form:options items="${contentTypes}" itemLabel="description" itemValue="index" htmlEscape="false"/>
				</form:select>
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
				<th>分类</th>
				<th>类型</th>
				<th>排序</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="product:specificationGroup:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="specificationGroup">
			<tr>
				<td>
					${specificationGroup.name}
				 </td>
				<td>
					${specificationGroup.productCategory.name }
				</td>
				<td>
					${specificationGroup.contentTypeName}
				<td>
					${specificationGroup.orders}
				</td>
				<td>
					<fmt:formatDate value="${specificationGroup.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${specificationGroup.remarks}
				</td>
				<shiro:hasPermission name="product:specificationGroup:edit"><td>
    				<a href="${ctx}/product/specificationGroup/updateFormView?id=${specificationGroup.id}">修改</a>
					<a href="${ctx}/product/specificationGroup/delete?id=${specificationGroup.id}" onclick="return confirmx('确认要删除该商品规格吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>