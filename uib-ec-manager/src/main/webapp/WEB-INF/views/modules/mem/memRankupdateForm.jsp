<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员等级管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/mem/memRank/">会员等级列表</a></li>
		<li class="active"><a href="${ctx}/mem/memRank/form?id=${memRank.id}">会员等级<shiro:hasPermission name="mem:memRank:edit">${not empty memRank.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="mem:memRank:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="memRank" action="${ctx}/mem/memRank/update" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">贡献值（元）：</label>
			<div class="controls">
   				<c:choose>
   					<c:when test="${memRank.count > 0}">
   						<input type="text" htmlEscape="false" class="input-xlarge " value="${memRank.amount}" disabled="disabled"/>
   					</c:when>
   					<c:otherwise>
   						<form:input path="amount" htmlEscape="false" class="input-xlarge number required"/>
   						<span class="help-inline"><font color="red">*</font> </span>
   					</c:otherwise>
   				</c:choose>			
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">是否默认：</label>
			<div class="controls">
				<form:radiobuttons path="isDefult" items="${fns:getDictList('default_flag')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div> --%>
		<%-- <div class="control-group">
			<label class="control-label">是否特殊：</label>
			<div class="controls">
				<form:radiobuttons path="isSpecial" items="${fns:getDictList('default_flag')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div> --%>
		<%-- <div class="control-group">
			<label class="control-label">优惠比例：</label>
			<div class="controls">
				<form:input path="scale" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="mem:memRank:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>