<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>提现管理表</title>
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
		<li><a href="${ctx}/mem/withdrawalApplyFor/">提现列表</a></li>
		<li class="active"><a href="${ctx}/mem/withdrawalApplyFor/form?id=${withdrawalApplyFor.id}">提现<shiro:hasPermission name="mem:withdrawalApplyFor:view">${not empty withdrawalApplyFor.id?'详情':'添加'}</shiro:hasPermission><shiro:lacksPermission name="mem:withdrawalApplyFor:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="withdrawalApplyFor" action="${ctx}/mem/withdrawalApplyFor/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">用户名：</label>
			<div class="controls">
				${withdrawalApplyFor.applyUsername}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机号：</label>
			<div class="controls">
				${withdrawalApplyFor.applyPhone}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">提现银行：</label>
			<div class="controls">
				${withdrawalApplyFor.bankName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属省市：</label>
			<div class="controls">
				${withdrawalApplyFor.provinceCity}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属分行：</label>
			<div class="controls">
				${withdrawalApplyFor.branchBankName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">提现银行卡号：</label>
			<div class="controls">
				${withdrawalApplyFor.cardNo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">持卡人姓名：</label>
			<div class="controls">
				${withdrawalApplyFor.cardPersonName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">提现金额(元)：</label>
			<div class="controls">
				${withdrawalApplyFor.applyAmount}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">申请时间：</label>
			<div class="controls">
				<fmt:formatDate value="${withdrawalApplyFor.applyDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">提现状态：</label>
			<div class="controls">
				<c:if test="${withdrawalApplyFor.applyStatus == '0'}">未处理</c:if>
				<c:if test="${withdrawalApplyFor.applyStatus == '1'}"><font color="red">处理失败</font></c:if>
				<c:if test="${withdrawalApplyFor.applyStatus == '2'}"><font color="green">处理成功</font></c:if>				
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">银行交易流水：</label>
			<div class="controls">
				${withdrawalApplyFor.bankTradeNo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">处理时间：</label>
			<div class="controls">
				<fmt:formatDate value="${withdrawalApplyFor.disposeDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<textarea htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "  disabled="disabled">${withdrawalApplyFor.remark}</textarea>
			</div>
		</div>
		<div class="form-actions">
			<%-- <shiro:hasPermission name="mem:withdrawalApplyFor:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission> --%>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>