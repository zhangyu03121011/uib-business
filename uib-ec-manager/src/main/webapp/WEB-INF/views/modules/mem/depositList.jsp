<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>预存款管理</title>
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
		<li class="active"><a href="${ctx}/mem/deposit/">预存款列表</a></li>
		<shiro:hasPermission name="mem:deposit:save"><li><a href="${ctx}/mem/deposit/form">预存款添加</a></li></shiro:hasPermission>
	</ul><%-- 
	<form:form id="searchForm" modelAttribute="deposit" action="${ctx}/mem/deposit/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>操作员：</label>
				<form:input path="operator" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form> --%>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>类型</th>
				<th>收入金额</th>
				<th>支出金额</th>
				<th>当前余额</th>
				<th>操作员</th>
				<th>会员</th>
				<th>订单</th>
				<th>收款单</th>
				<th>更新时间</th>
				<shiro:hasPermission name="mem:deposit:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="deposit">
			<tr>
				<td>
					${deposit.typeName}
				</td>
				<td>
					${deposit.credit}
				</td>
				<td>
					${deposit.debit}
				</td>
				<td>
					${deposit.balance}
				</td>
				<td>
					${deposit.operator}
				</td>
				<td>
				<c:choose>
						<c:when test="${!memMember.id}">
							<a href="../mem/memMember/form?id=${memMember.id}">${memMember.username}</a>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
					</c:choose>
				
					
				</td>
				<td>
					<c:choose>
						<c:when test="${!orderTable.id}">
							<a href="../order/orderTable/form?id=${orderTable.id}">${orderTable.orderNo}</a>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
					</c:choose>
				</td>
				
				<td>
					<c:choose>
						<c:when test="${!orderTablePayment.id}">
							<a href="../order/orderTablePayment/form?id=${orderTablePayment.id}">${orderTablePayment.paymentNo}</a>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
					</c:choose>
				</td>
				<td><a href="${ctx}/mem/deposit/form?id=${deposit.id}">
					<fmt:formatDate value="${deposit.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<shiro:hasPermission name="mem:deposit:edit"><td>
    				<a href="${ctx}/mem/deposit/updateFormView?id=${deposit.id}">修改</a>
					<a href="${ctx}/mem/deposit/delete?id=${deposit.id}" onclick="return confirmx('确认要删除该预存款吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>