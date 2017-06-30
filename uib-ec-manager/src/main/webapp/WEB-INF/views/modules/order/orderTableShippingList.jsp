<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>发货单管理</title>
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
		<li class="active"><a href="${ctx}/order/orderTableShipping/">发货单列表</a></li>
		<shiro:hasPermission name="order:orderTableShipping:save"><li><a href="${ctx}/order/orderTableShipping/form">发货单添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="orderTableShipping" action="${ctx}/order/orderTableShipping/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>发货单编号：</label>
				<form:input path="shippingNo" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>订单编号：</label>
				<form:input path="orderNo" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>创建时间：</label>
				<input name="createDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${orderTableShipping.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>发货单编号</th>
				<th>收货人</th>
				<th>物流公司</th>
				<th>配送方式</th>
				<th>运单号</th>
				<th>订单编号</th>
				<th>是否备注</th>
				<th>发货时间</th>
				<th>创建时间</th>
				<shiro:hasPermission name="order:orderTableShipping:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="orderTableShipping">
			<tr>
				<td><a href="${ctx}/order/orderTableShipping/form?id=${orderTableShipping.id}">
					${orderTableShipping.shippingNo}
				</a></td>
				<td>
					${orderTableShipping.consignee}
				</td>
				<td>
					${orderTableShipping.deliveryCorp}
				</td>
				<td>
					${orderTableShipping.shippingMethod}
				</td>
				<td>
					${orderTableShipping.trackingNo}
				</td>
				<td><a href="${ctx}/order/orderTable/form?id=${orderTableShipping.orderId}">
					${orderTableShipping.orderNo}
				</a></td>
				<td>
					<c:choose>
						<c:when test="${orderTableShipping.isRemarks!=0 && orderTableShipping.isRemarks!=null}">是</c:when>
						<c:otherwise>否</c:otherwise>
					</c:choose>
				</td>
				<td>
					<fmt:formatDate value="${orderTableShipping.shippingDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${orderTableShipping.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="order:orderTableShipping:edit"><td>
					<a href="${ctx}/order/orderTableShipping/delete?id=${orderTableShipping.id}" onclick="return confirmx('确认要删除该发货单吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>