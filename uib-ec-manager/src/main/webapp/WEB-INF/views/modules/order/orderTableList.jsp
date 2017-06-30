<%@ page contentType="text/html;charset=UTF-8" import="com.uib.ecmanager.common.enums.*" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				$("#searchForm").attr("action","${ctx}/order/orderTable/export").submit();
				$("#searchForm").attr("action","${ctx}/order/orderTable/");
			});
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
		<li class="active"><a href="${ctx}/order/orderTable/">订单列表</a></li>
		<shiro:hasPermission name="order:orderTable:save"><li><a href="${ctx}/order/orderTable/form">订单添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="orderTable" action="${ctx}/order/orderTable/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>订单编号：</label>
				<form:input path="orderNo" htmlEscape="false" maxlength="32" class="input-small"/>
			</li>
			<li><label>来源：</label>
				<form:select path="orderSource" class="input-small">
					<form:option value="" label=""/>
					<form:option value="" label="全部"/>
					<form:option value="0" label="PC"/>
					<form:option value="1" label="App"/>
					<form:option value="2" label="微信"/>
				</form:select>
			</li>
			<li><label>状态：</label>
				<form:select path="orderStatus" class="input-small">
					<form:option value="" label=""/>
					<form:option value="" label="全部"/>
					<form:option value="4" label="待付款"/>
					<form:option value="5" label="已付款待发货"/>
					<form:option value="6" label="待收货"/>
					<form:option value="3" label="已取消"/>
					<form:option value="2" label="已完成"/>
				</form:select>
			</li>
			<li>
				<label>供应商名称：</label>
				<sys:treeselect id="supplier" name="supplierId" value="${supplier.id}" labelName="supplier.companyName" labelValue="${supplier.companyName}" title="供应商"
						url="/supplier/supplier/treeData" module="product" notAllowSelectRoot="false" cssClass="input-small required" /><span class="help-inline"><font color="red">*</font> </span>
<%-- 				<form:input path="supplierName" htmlEscape="false" maxlength="32" class="input-small"/> --%>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>订单编号</th>
				<th>订单金额</th>
				<th>收货人</th>
				<th>支付方式</th>
				<!-- <th>配送方式</th> -->
				<th>订单状态</th>
				<th>支付状态</th>
				<th>配送状态</th>
				<th>订单来源</th>
				<th>供应商名称</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<shiro:hasPermission name="order:orderTable:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="orderTable">
			<tr>
				<td><a href="${ctx}/order/orderTable/form?id=${orderTable.id}">
					${orderTable.orderNo}
				</a></td>
				<td>
					￥${orderTable.amount}
				</td>
				<td>
					${orderTable.consignee}
				</td>
				<td>
					${orderTable.paymentMethod.name}
				</td>
				<%-- <td>
					${orderTable.shippingMethod.name}
				</td> --%>
				<td>
					${orderTable.orderStatusName}
					<%-- <c:choose>
					<c:when test="${orderTable.expired}">
					<span >(已过期)</span>
					</c:when>
				</c:choose> --%>
				</td>
				<td>
					${orderTable.paymentStatusName}
				</td>
				<td>
					${orderTable.shippingStatusName}
				</td>
				<td>
					${orderTable.orderSourceName}
				</td>
				<td>
					${orderTable.supplierName}
				</td>
<!-- 				<td> -->
<%-- 					<c:choose> --%>
<%-- 						<c:when test="${orderTable.isRemarks!=0 && orderTable.isRemarks!=null}">是</c:when> --%>
<%-- 						<c:otherwise>否</c:otherwise> --%>
<%-- 					</c:choose> --%>
<!-- 				</td> -->
				<td>
					<fmt:formatDate value="${orderTable.createDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${orderTable.updateDate}" pattern="yyyy-MM-dd"/>
				</td>
				<shiro:hasPermission name="order:orderTable:edit"><td>
    				<a href="${ctx}/order/orderTable/updateFormView?id=${orderTable.id}">修改</a>
					<a href="${ctx}/order/orderTable/delete?id=${orderTable.id}" onclick="return confirmx('确认要删除该订单吗？', this.href)">删除</a>
					<c:set var="paidShipped" value="<%=OrderStatus.paid_shipped %>"/>
					<c:choose>
						<c:when test="${orderTable.orderStatus != paidShipped.index }">
							<a href="${ctx}/order/orderTable/formShipping?id=${orderTable.id}" ><font color="gray">发货</font></a>
						</c:when>
						<c:otherwise>
							<a href="${ctx}/order/orderTable/formShipping?id=${orderTable.id}" >发货</a>					
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