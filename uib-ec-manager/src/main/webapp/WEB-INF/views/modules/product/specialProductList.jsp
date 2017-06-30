<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>专题商品管理</title>
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
		function addProductTip(){
			$.jBox.tip('请先添加专题信息。');
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/product/special/">专题信息列表</a></li>
			<li class="active"><a href="${ctx}/product/special/form">专题信息添加</a></li>
	</ul><br/>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/product/special/formView?id=${product.specialId}">专题基本信息</a></li>
		<li class="active"><a href="${ctx}/product/special/selectProduct?specialId=${product.specialId}">专题商品查看</a></li>
		<c:choose>
			<c:when test="${product.specialId!=null && not empty product.specialId}">
			<li><a href="${ctx}/product/special/productList?specialId=${product.specialId}">专题商品添加</a></li>
			</c:when>
			<c:otherwise>
			<li><a href ="javascript:volid(0);" onclick="addProductTip()">专题商品添加</a></li>
			</c:otherwise>
		</c:choose>
	</ul>
	<form:form id="searchForm" modelAttribute="searchProductRef" action="${ctx}/product/special/selectProduct" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="specialId" name="specialId" type="hidden" value="${product.specialId}"/>
		<input id="updateFlag" name="updateFlag" type="hidden" value="${product.updateFlag}"/>
		<ul class="ul-form">
			<li><label>商品名称：</label>
				<input id="product.name" name="product.name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>商品编号：</label>
				<input id="product.productNo" name="product.productNo" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>搜索关键词：</label>
				<input id="product.keyword" name="product.keyword" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>商品分类：</label> <sys:treeselect id="productCategory" name="product.productCategoryId" value="${product.productCategoryId}" labelName="productCategory.name"
					labelValue="${product.productCategory.name}" title="商品分类" url="/product/productCategory/treeData" module="product" notAllowSelectRoot="false" cssClass="input-small" /></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商品名称</th>
				<th>商品编号</th>
				<th>分类</th>
				<th>市场价</th>
				<shiro:hasPermission name="product:special:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="specialRef">
			<tr>
				<td><a href="${ctx}/product/product/form?id=${specialRef.product.id}" target="view_frame">
					${specialRef.product.name}
				</a></td>
				<td>
					${specialRef.product.productNo}
				</td>
				<td>
					${specialRef.product.productCategory.name}
				</td>
				<td>
					${specialRef.product.marketPrice}
				</td>
				<shiro:hasPermission name="product:special:edit"><td>
					<a href="${ctx}/product/special/deleteSpecialProduct?id=${specialRef.id}&&specialId=${specialRef.specialId}" onclick="return confirmx('确认要删除该专题商品吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>