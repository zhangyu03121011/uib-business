<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>收款单管理</title>
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
		<li class="active"><a href="${ctx}/order/orderTablePayment/">收款单列表</a></li>
		<shiro:hasPermission name="order:orderTablePayment:save"><li><a href="${ctx}/order/orderTablePayment/form">收款单添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="orderTablePayment" action="${ctx}/order/orderTablePayment/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>订单编号：</label>
				<form:input path="orderNo" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>收款账号：</label>
				<form:input path="paymentNo" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>创建时间：</label>
				<input name="createDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${orderTablePayment.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				<th>订单编号</th>
				<th>支付方式</th>
				<th>付款金额</th>
				<th>付款日期</th>
				<th>付款方式</th>
				<th>收款账号</th>
				<th>状态</th>
				<th>类型</th>
				<th>会员</th>
				<th>是否备注</th>
				<th>创建时间</th>
				<shiro:hasPermission name="order:orderTablePayment:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="orderTablePayment">
			<tr>
				<td><a href="${ctx}/order/orderTablePayment/form?id=${orderTablePayment.id}">
					${orderTablePayment.orderNo}
				</a></td>
				<td>
					${orderTablePayment.methodName}
				</td>
				<td>
				<c:choose>
					<c:when test="${orderTablePayment.amount!=null ||orderTablePayment.amount!=0}">
					￥${orderTablePayment.amount}
					</c:when>
				<c:otherwise>
					0
				</c:otherwise>
				</c:choose>
				</td>
				<td>
					<fmt:formatDate value="${orderTablePayment.paymentDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(orderTablePayment.paymentMethod, 'order_Payment', '')}
				</td>
				<td>
					${orderTablePayment.paymentNo}
				</td>
				<td>
					${orderTablePayment.statusName}
				</td>
				<td>
					${orderTablePayment.typeName}
				</td>
				<td>
					${member.name}
				</td>
				<td>
					<c:choose>
						<c:when test="${orderTablePayment.isRemarks!=0 && orderTablePayment.isRemarks!=null}">是</c:when>
						<c:otherwise>否</c:otherwise>
					</c:choose>
				</td>
				<td>
					<fmt:formatDate value="${orderTablePayment.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="order:orderTablePayment:edit"><td>
    				<%-- <a href="${ctx}/order/orderTablePayment/updateFormView?id=${orderTablePayment.id}">修改</a> --%>
					<a href="${ctx}/order/orderTablePayment/delete?id=${orderTablePayment.id}" onclick="return confirmx('确认要删除该收款单吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>