<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>退款单管理</title>
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
		<li class="active"><a href="${ctx}/order/orderTableRefunds/">退款单列表</a></li>
		<shiro:hasPermission name="order:orderTableRefunds:save"><li><a href="${ctx}/order/orderTableRefunds/form">退款单添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="orderTableRefunds" action="${ctx}/order/orderTableRefunds/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>退款单编号：</label>
				<form:input path="refundNo" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>创建时间：</label>
				<input name="createDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${orderTableRefunds.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				<th>退款单编号</th>
				<th>退款金额</th>
				<th>方式</th>
				<th>收款人</th>
				<th>支付方式</th>
				<th>创建时间</th>
				<shiro:hasPermission name="order:orderTableRefunds:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="orderTableRefunds">
			<tr>
				<td><a href="${ctx}/order/orderTableRefunds/form?id=${orderTableRefunds.id}">
					${orderTableRefunds.refundNo}
				</a></td>
				<td>
				<c:choose>
					<c:when test="${orderTableRefunds.amount!=null ||orderTableRefunds.amount!=0}">
					￥${orderTableRefunds.amount}
					</c:when>
				<c:otherwise>
					0
				</c:otherwise>
				</c:choose>
				</td>
				<td>
					${orderTableRefunds.methodName}
				</td>
				<td>
					${orderTableRefunds.payee}
				</td>
				<td>
					${fns:getDictLabel(orderTableRefunds.paymentMethod, 'order_Payment', '')}
				</td>
				<td>
					<fmt:formatDate value="${orderTableRefunds.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="order:orderTableRefunds:edit"><td>
    				<%-- <a href="${ctx}/order/orderTableRefunds/updateFormView?id=${orderTableRefunds.id}">修改</a> --%>
					<a href="${ctx}/order/orderTableRefunds/delete?id=${orderTableRefunds.id}" onclick="return confirmx('确认要删除该退款单吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>