<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>商品管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
	function checkAll(obj) {//全选
		if (obj.checked == true) {
			$("input[name=" + obj.name + "]").attr("checked", true);
		} else {
			$("input[name=" + obj.name + "]").attr("checked", false);
		}
	}

	function putaway() {
		if ($("tbody  input:checked").length == 0) {
			$.jBox.tip('请至少选择一个。');
			return;
		}
		if (!confirm("是否进行上架操作")) {
			return;
		}
		if ($("tbody  input[name='pcIds']:checked").length == 0&&$("tbody  input[name='appIds']:checked").length == 0&&$("tbody  input[name='wxIds']:checked").length == 0) {
			$.jBox.tip('请至少选择一个。');
			return;
		} 
		$("#inputForm").attr("action", "${ctx}/product/product/putaway").submit();
	}

	function soldOut() {
		if ($("tbody  input:checked").length == 0) {
			$.jBox.tip('请至少选择一个。');
			return;
		}
		if (!confirm("是否进行下架操作")) {
			return;
		}

		if ($("tbody  input[name='pcIds']:checked").length == 0&&$("tbody  input[name='appIds']:checked").length == 0&&$("tbody  input[name='wxIds']:checked").length == 0) {
			$.jBox.tip('请至少选择一个。');
			return;
		} 
		$("#inputForm").attr("action", "${ctx}/product/product/soldOut").submit();
	}
	
	
	function del() {
		if ($("tbody  input[name='ids']:checked").length == 0) {
			$.jBox.tip('请至少选择一个。');
			return;
		}
		if (!confirm("是否进行删除操作")) {
			return;
		}
		$("#inputForm").submit();
	}
	
	function resetBtn(){
		$("#name").attr("value","");
		$("#productCategoryId").attr("value","");
		$("#wxIsMarketable_option option[value='']").attr("selected", true);
		$(".select2-chosen").html("全部");
		
	}
	/* 	function lookTwoDimensionCode(id) {
	 $.jBox("iframe:${ctx}/product/product/twoDimensionCode?id="+id, {
	 title: "二维码",
	 width: 700,
	 height: 600,
	 persistent:true,
	 showScrolling:false,
	 iframeScrolling:'no',
	 buttons: { '关闭': true }
	 }); 
	 }; */
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/product/product/">商品列表</a></li>
		<shiro:hasPermission name="product:product:save">
			<li><a href="${ctx}/product/product/form">商品添加</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="product" action="${ctx}/product/product/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
		<ul class="ul-form">
			<li><label>名称：</label> <form:input path="name" id="name" htmlEscape="false" maxlength="32" class="input-medium" /></li>
<%-- 			<li><label>搜索关键词：</label> <form:input path="keyword" htmlEscape="false" maxlength="32" class="input-medium" /></li> --%>
			<li><label>商品分类：</label> <sys:treeselect id="productCategory" name="productCategoryId" value="${product.productCategoryId}" labelName="productCategory.name"
					labelValue="${product.productCategory.name}" title="商品分类" url="/product/productCategory/treeData" module="product" allowClear="true"   cssClass="input-small" /></li>
			<%-- <li><label>PC：</label> 
			<form:select path="pcIsMarketable" class="input-medium">
				<form:option value="" label="请选择"/>
				<form:options items="${fns:getDictList('is_marketable')}" itemLabel="label" itemValue="value" htmlEscape="false" />
			</form:select> </li>
			<li><label>APP：</label> 
			<form:select path="appIsMarketable" class="input-medium">
				<form:option value="" label="请选择"/>
				<form:options items="${fns:getDictList('is_marketable')}" itemLabel="label" itemValue="value" htmlEscape="false" />
			</form:select> </li> --%>
			<li>
			<label>微信：</label> 
<%-- 			<form:select path="wxIsMarketable" class="input-medium"> --%>
<%-- 				<form:option value="" label="请选择"/> --%>
<%-- 				<form:options items="${fns:getDictList('is_marketable')}" itemLabel="label" itemValue="value" htmlEscape="false" /> --%>
<%-- 			</form:select> --%>
				<select name="wxIsMarketable" style="width:100px;" id="wxIsMarketable_option">
					<option value="" <c:if test="${product.wxIsMarketable =='' }">selected="selected"</c:if>>全部</option>
					<option value="0" <c:if test="${product.wxIsMarketable =='0' }">selected="selected"</c:if>>下架</option>
					<option value="1" <c:if test="${product.wxIsMarketable =='1' }">selected="selected"</c:if>>上架</option>
				</select>
			  </li>
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" /></li>
				<input  class="btn btn-primary" type="button" value="重置" onclick="resetBtn()"/>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<shiro:hasPermission name="product:product:edit">
				<th><input type="button" class="btn btn-primary" onclick="putaway()" value="上架"> <input type="button" class="btn btn-primary" onclick="soldOut()" value="下架"> <input
					type="button" class="btn btn-primary" onclick="del()" value="删除"></th>
			</shiro:hasPermission>
			<tr>
				<th width="2"><input name="ids" type="checkbox" onclick="checkAll(this) ">
				</td>全选(删除)
				<!-- <th><input id="pcIds" name="pcIds" type="checkbox" onclick="checkAll(this) ">
				</td>全选
				</th>
				<th><input id="appIds" name="appIds" type="checkbox" onclick="checkAll(this) ">
				</td>全选
				</th> -->
				<th><input id="wxIds" name="wxIds" type="checkbox" onclick="checkAll(this) ">
				</td>全选
				</th>
				<th>名称</th>
				<th>分类</th>
				<!-- <th>售价</th> -->
				<th>成本价</th>
				<th>库存</th>
				<!-- <th>PC</th>
				 <th>APP</th> -->
				<th>微信</th>
				<th>创建时间</th>
				<th>评论</th>
				<th>二维码</th>
				<shiro:hasPermission name="product:product:edit">
					<th>操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
			<form:form id="inputForm" action="${ctx}/product/product/del" method="post">
				<c:forEach items="${page.list}" var="product">
					<tr>
						<td><input name="ids" type="checkbox" value="${product.id}"></td>
						<%-- <td><input name="pcIds" value="${product.id}" type="checkbox">PC</td>
						<td><input name="appIds" value="${product.id}" type="checkbox">APP</td> --%>
						<td><input name="wxIds" value="${product.id}" type="checkbox">微信</td>
						<td><a href="${ctx}/product/product/updateFormView?id=${product.id}"> ${product.name} </a></td>
						<td>${product.productCategory.name}</td>
						<%-- <td>${product.price}</td> --%>
						<td>${product.cost}</td>
						<td>${product.stock-product.allocatedStock}</td>
						<%-- <td>${product.pcIsMarketable==1?'上架':'下架'}</td>
						<td>${product.appIsMarketable==1?'上架':'下架'}</td> --%>
						<td>${product.wxIsMarketable==1?'上架':'下架'}</td>
						<td><fmt:formatDate value="${product.createDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td><a href="${ctx }/product/comment/query?paramMap.productId=${product.id}">查看</a></td>
						<td><a href="${pageContext.request.contextPath}/a/product/product/twoDimensionCode?id=${product.id}" target="_bank">查看</a></td>
						<shiro:hasPermission name="product:product:edit">
							<td><a href="${ctx}/product/product/updateFormView?id=${product.id}">修改</a> <a href="${ctx}/product/product/delete?id=${product.id}" onclick="return confirmx('确认要删除该商品吗？', this.href)">删除</a>
							</td>
						</shiro:hasPermission>
					</tr>
				</c:forEach>
			</form:form>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>