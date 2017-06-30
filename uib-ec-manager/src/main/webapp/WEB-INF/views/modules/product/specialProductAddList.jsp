<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>专题商品管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	$(document).ready(function() {
		//$("#name").focus();
		$("#inputForm").validate({
				submitHandler: function(form){
					if ($("input[name='productId']:checked").length == 0) {
						$.jBox.tip('请至少选择一个。');
						return;
					}else{
					loading('正在提交，请稍等...');
					form.submit();
					}
				},
			errorContainer: "#messageBox",
			errorPlacement: function(error, element) {
				$("#messageBox").text("输入有误，请先更正。");
				if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
					error.appendTo(element.parent().parent());
				} else {
					error.insertAfter(element);
				}
			}
		});
	});
	
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function checkAllup() {  //全选
			if ($("#productIds").attr("checked") == "checked" ) {
				$("input[name='productId']").attr("checked", true);
				$("#productIds").attr("checked", true);
			} else {
				$("input[name='productId']").attr("checked", false);
				$("#ids").attr("checked", false);
			}
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
		<li>
			<a href="${ctx}/product/special/formView?id=${product.specialId}">专题基本信息</a>
		</li>
		<li><a href="${ctx}/product/special/selectProduct?specialId=${product.specialId}">专题商品查看</a></li>
		<c:choose>
			<c:when test="${product.specialId!=null && not empty product.specialId}">
			<li class="active"><a href="${ctx}/product/special/productList?specialId=${product.specialId}">专题商品添加</a></li>
			</c:when>
			<c:otherwise>
			<li><a href ="javascript:volid(0);" onclick="addProductTip()">专题商品添加</a></li>
			</c:otherwise>
		</c:choose>
	</ul>
	<form:form id="searchForm" modelAttribute="product" action="${ctx}/product/special/productList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="specialId" name="specialId" type="hidden" value="${product.specialId}"/>
		<input id="updateFlag" name="updateFlag" type="hidden" value="${product.updateFlag}"/>
		<ul class="ul-form">
			<li><label>商品名称：</label>
				<input id="name" name="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>商品编号：</label>
				<input id="productNo" name="productNo" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>搜索关键词：</label>
				<input id="keyword" name="keyword" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>商品分类：</label> <sys:treeselect id="productCategory" name="product.productCategoryId" value="${product.productCategoryId}" labelName="productCategory.name"
					labelValue="${product.productCategory.name}" title="商品分类" url="/product/productCategory/treeData" module="product" notAllowSelectRoot="false" cssClass="input-small" /></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<form:form id="inputForm" action="${ctx}/product/special/addProduct" method="post" class="breadcrumb form-search">
	<input type="hidden" id = "specialId" name = "specialId" value="${product.specialId}"/>
	<input type="hidden" id = "updateFlag" name = "updateFlag" value="${product.updateFlag}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input id="productIds" name="productIds" type="checkbox"  onclick="checkAllup()"/>全选</th>
				<th>商品名称</th>
				<th>商品编号</th>
				<th>分类</th>
				<th>市场价</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="product">
			<tr>
				<td><input type="checkbox" id ="productId" name="productId" value="${product.id}"/></td>
				<td><a href="${ctx}/product/product/form?id=${product.id}" target="view_frame">
					${product.name}
				</a></td>
				<td>
					${product.productNo}
				</td>
				<td>
					${product.productCategory.name}
				</td>
				<td>
					${product.marketPrice}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<div class="form-actions">
			<shiro:hasPermission name="product:special:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" onclick="addProduct()"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>