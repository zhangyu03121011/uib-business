<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>优惠券管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					$("#totalCount").text(parseInt($("#totalCount").text())+parseInt($("#count").val()))
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
		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
		}
		function delRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			}
		}
		
	 	$("#btnSubmit").live("click",function() {
	 		$("#btnSubmit").submit();
	 		$("#sum1").text(0);
			$("#btnSubmit").prop("disabled",true);
		});
				
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/coupon/coupon/">优惠券列表</a></li>
		<li class="active"><a href="${ctx}/coupon/coupon/couponBuild?id=${coupon.id}"><shiro:hasPermission name="coupon:coupon:edit">${not empty coupon.id?'生成优惠码':'添加'}</shiro:hasPermission><shiro:lacksPermission name="coupon:coupon:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="coupon" action="${ctx}/coupon/coupon/download" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="sum"/>
		<sys:message content="${message}"/>	
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<%-- <form:input path="name" htmlEscape="false" maxlength="32" class="input-xlarge "/> --%>
				${coupon.name}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">使用起始日期：</label>
			<div class="controls">
				<%-- <input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${coupon.beginDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> --%>
				<fmt:formatDate value="${coupon.beginDate}" pattern="yyyy-MM-dd"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">使用结束日期：</label>
			<div class="controls">
				<%-- <input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${coupon.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> --%>
				<fmt:formatDate value="${coupon.endDate}" pattern="yyyy-MM-dd"/>
			
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">已生成数量：</label>
			<div class="controls">
				<span id="totalCount">${totalCount}</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">已使用数量：</label>
			<div class="controls">
					${usedCount}
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label"><font color="red">*</font>生成数量：</label>
			<div class="controls">
				<span id="sum1">${coupon.sum}</span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="coupon:coupon:edit"><c:choose><c:when test="${coupon.sum>0}"><input id="btnSubmit" class="btn btn-primary" type="submit" value="生成"/></c:when></c:choose></shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>