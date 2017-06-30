<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	$(document).ready(function() {
		if($("#merCode").val()!=0){
			$("#btnSubmit").prop("disabled",true);
		}
	});
		function isMerCode(){
			$("#isRelevance").text("");
			$("#btnSubmit").removeAttr("disabled");
		};
		$("#btnSubmit").live("click",function() {
			var merCode = $("#merCode").val();
			
			if(merCode==0){
				$("#btnSubmit").attr("disabled",true);
				$("#isRelevance").text("必须选择一个商户");
			}else{
				$.ajax({
					cache:false,
					type:"POST",
					url:"${ctx}/sys/user/saveRelevance?"+$('#inputForm').serialize(),
					async:false,
					error:function(data){
						result = data;
						window.parent.window.jBox.tip("关联失败");
						window.parent.window.jBox.close();
					},
					success:function(data){
						result = data;
						window.parent.window.jBox.tip("关联成功");
						window.parent.window.jBox.close();
					}
				});
			}
		});
	</script>	
</head>
<body>
<form:form id="inputForm" class="form-horizontal">
	<br>
	<div class="control-group">
		<label class="control-label">用户名称：</label>
		<div class="controls">
			<input id="userName" name="userName" htmlEscape="false" maxlength="32" value="${user.loginName}" class="input" readonly="readonly"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">商户名称：</label>
		<div class="controls">
		<select id="merCode" name="merCode"class="input-small "  onchange="isMerCode()">
			<option value="0">请选择</option>
			<c:forEach items="${memMerchants}" var="merchant">
				<c:choose>
					<c:when test="${userMerchantMap.merCode==merchant.merchantNo}">
						<option value="${merchant.merchantNo}" selected="selected">
						${merchant.merchantName}</option>
					</c:when>
					<c:otherwise>
						<option value="${merchant.merchantNo}">
						${merchant.merchantName}</option>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</select>
		<font color="#ffb042"><span id="isRelevance"></span></font>
		</div>
	</div>
	<br><br>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" />&nbsp;
		</div>
	</form:form>
</body>
</html>