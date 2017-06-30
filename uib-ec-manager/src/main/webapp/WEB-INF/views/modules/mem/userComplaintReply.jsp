<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>投诉查看</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					var reply = $('#reply').val().trim();
					var flag = true;
					if(reply == null || reply == ""){
						flag = false;
						$('#error_reply').html("请输入答复内容");
					}
					if(flag){
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/mem/userComplaint/">投诉列表</a></li>
		<li class="active"><a href="${ctx}/mem/userComplaint/varifyView?id=${userComplaint.id}">投诉<shiro:hasPermission name="mem:userComplaint:edit">${not empty userComplaint.id?'查看':'添加'}</shiro:hasPermission><shiro:lacksPermission name="mem:userComplaint:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="userComplaint" action="${ctx}/mem/userComplaint/saveReplyInfo" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">用户名：</label>
			<div class="controls">
				${userComplaint.username}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">投诉类型：</label>
			<div class="controls">
				<c:if test="${userComplaint.feedbackType == '1'}">商品</c:if>
				<c:if test="${userComplaint.feedbackType == '2'}">服务</c:if>
				<c:if test="${userComplaint.feedbackType == '3'}">其它</c:if>	
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">标题：</label>
			<div class="controls">
				${userComplaint.title}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">投诉内容：</label>
			<div class="controls">
				${userComplaint.describeinfo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">投诉时间：</label>
			<div class="controls">
				<fmt:formatDate value="${userComplaint.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">投诉图片：</label>
			<div class="controls">
 			<c:forEach items="${userComplaint.userComplaintAttachmentList}" var="list"> 				
				<img alt="投诉图片" src="${frontWebImageBaseUrl }/${list.filePath}">
			</c:forEach>
			</div>
		</div>	
		<div class="control-group">
			<label class="control-label">解决状态：</label>
			<div class="controls">
				<c:if test="${userComplaint.solutionState != '1'}"><font color="red">未答复</font></c:if>
				<c:if test="${userComplaint.solutionState == '1'}"><font color="green">已答复</font></c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">答复内容：</label>
			<div class="controls">
				<c:choose>
					<c:when test="${userComplaint.solutionState == '1'}">
						${userComplaint.reply}
					</c:when>
					<c:otherwise>
						<form:textarea id="reply" path="reply" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge " name="reply"/>
					</c:otherwise>				
				</c:choose>
				<span id="error_reply" style="color:red"></span>
			</div>
		</div>
		<div class="form-actions">
			<c:choose>
					<c:when test="${userComplaint.solutionState == '1'}">
						<!-- <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"  disabled="true"/>&nbsp; -->
						<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>	
					</c:when>
					<c:otherwise>
						<shiro:hasPermission name="mem:userComplaint:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
						<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>	
					</c:otherwise>
				</c:choose>
		</div>
	</form:form>
</body>
</html>