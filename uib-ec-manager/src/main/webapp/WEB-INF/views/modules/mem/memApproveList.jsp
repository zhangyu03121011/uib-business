<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员审核</title>
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
		<li class="active"><a href="${ctx}/mem/memMember/approveList">审核列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="memMember" action="${ctx}/mem/memMember/approveList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<%-- <li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li> --%>
			<li><label>用户名：</label>
				<form:input path="username" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>			
			<%-- <li><label>用户类型：</label>
				<form:select path="userType" class="input-medium">
					<form:option value="" label=""/>
					<c:forEach items="${listUserType}" var="userType">
					<form:option value="${userType.key}" label="${userType.value}"/>
					</c:forEach>
				</form:select>
			</li> --%>
			<li><label>手机号：</label>
				<form:input path="phone" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>会员等级：</label>
				<form:select path="memRank.name" class="input-medium">
					<form:option value="" label=""/>
					<form:option value="" label="全部"/>
					<c:forEach items="${memRankList}" var="memRank">
					<form:option value="${memRank.name}" label="${memRank.name}"/>
					</c:forEach>
				</form:select>
			</li>
			<%-- <li><label>是否锁定：</label>
				<form:select path="isLocked" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('lock_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li> --%>
			<li><label>审核状态：</label>
				<form:select path="approveFlag" class="input-medium">
					<form:option value="" label=""/>
					<form:option value="" label="全部"/>
					<c:forEach items="${listApproveFlag}" var="approveFlag">
					<form:option value="${approveFlag.key}" label="${approveFlag.value}"/>
					</c:forEach>
				</form:select>
			</li>
			<%-- <li><label>身份证号：</label>
				<form:input path="idCard" htmlEscape="false" maxlength="18" class="input-medium"/>
			</li>
			<li><label>是否启用：</label>
				<form:select path="isEnabled" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('is_enabled')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			
			<li>
				<label>认证状态：</label>
				<form:select path="approveFlag" class="input-medium">
					<form:option value="" label=""/>
					<form:option value="3" label="所有"/>
					<form:option value="" label="未认证"/>
					<form:option value="0" label="待审核"/>
					<form:option value="1" label="审核成功"/>
					<form:option value="2" label="审核失败"/>
				</form:select>
			</li> --%>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>用户名</th>
				<th>用户类型</th>
				<th>会员贡献值</th>
				<th>会员等级</th>
				<th>手机号</th>
				<th>身份证号</th>
				<!-- <th>是否锁定</th> -->
				<th>审核状态</th>
				<!-- <th>备注</th> -->
				<shiro:hasPermission name="mem:memMember:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="memMember">	
			<tr>
				<td><a href="${ctx}/mem/memMember/approveForm?id=${memMember.id}">
					${memMember.username}
				</a></td>
				<td>				
					<c:if test="${memMember.userType == '1'}">c1消费者</c:if>
					<c:if test="${memMember.userType == '2'}">分销商</c:if>
					<c:if test="${memMember.userType == '3'}">c2消费者</c:if>
				</td>
				<td>
					${memMember.sumamount}
				</td>
				<td>
					${memMember.memRank.name}
				</td>
				<td>
					${memMember.phone}
				</td>
				<td>
					${memMember.idCard}
				</td>
				<%-- <td>
					<c:if test="${memMember.isLocked == '0'}">否</c:if>
					<c:if test="${memMember.isLocked == '1'}">是</c:if>
				</td> --%>				
				<td>
					<c:if test="${memMember.approveFlag == '0'}">待审核</c:if>
					<c:if test="${memMember.approveFlag == '1'}"><font color="green">审核成功</font></c:if>
					<c:if test="${memMember.approveFlag == '2'}"><font color="red">审核失败</font></c:if>									
				</td>				
				<shiro:hasPermission name="mem:memMember:edit"><td>
    				<%-- <a href="${ctx}/mem/memMember/updateFormView?id=${memMember.id}">修改</a> --%>					
					<c:choose>
						<c:when test="${memMember.approveFlag == '0'}">
							<a href="${ctx}/mem/memMember/varifyView?id=${memMember.id}">审核</a>
						</c:when>
						<c:otherwise>
							<a href="${ctx}/mem/memMember/approveForm?id=${memMember.id}">查看</a>
						</c:otherwise>
					</c:choose>
					<a href="${ctx}/mem/memMember/approveDelete?id=${memMember.id}" onclick="return confirmx('确认要删除该会员审核吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>