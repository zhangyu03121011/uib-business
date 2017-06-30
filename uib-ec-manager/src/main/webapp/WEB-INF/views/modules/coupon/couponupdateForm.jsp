<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>优惠券管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/kindeditor.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/themes/default/default.css" />
	<script src="${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/lang/zh_CN.js"></script>
	
	<script type="text/javascript">
	var tables = ["base_info_tab","coupon_introduction_tab"];
	var $isExchange = $("#isExchange");
	KindEditor.ready(function(K) {
		K.create('textarea[name="introduction"]', {
			uploadJson : '${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/jsp/upload_json.jsp',
		                fileManagerJson : '../../../resources/kindedito${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/jsp/file_manager_json.jsp',
		                allowFileManager : true,
		                allowImageUpload : true, 
		    width : "100%",
			autoHeightMode : true,
			afterCreate : function() {this.loadPlugin('autoheight');},
			afterBlur : function(){ this.sync(); }  //Kindeditor下获取文本框信息
		});
		var editor = K.editor({
			allowFileManager : true
		});
	});
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
		//切换菜单
		$(function(){
			$("#inputForm ul > li").click(function(){
		        $(this).addClass("active").siblings().removeClass("active");
				var table_id = $(this).attr("id").replace("li","tab");
				$("#"+table_id).attr("style","display:table;width:100%");
				for(var i = 0 ; i < tables.length ; i++){
					if(tables[i] == table_id){
						continue;
					}
					$("#"+tables[i]).attr("style","display:none");
				}
			});
		});
		
		// 是否允许积分兑换
		$isExchange.live("click",function() {
			if ($(this).prop("checked")) {
				$("#point").prop("disabled", false);
			} else {
				$("#point").val("").prop("disabled", true);
			}
		});
		//验证积分
		function isPoint(obj){
			   reg=/^(([0-9]*[1-9][0-9]*)|0)$/;
			   if(!reg.test(obj)){
				   $("#pointNotnull").html("只允许输入零或正整数");
			   }else{
				   $("#pointNotnull").html("");
			   }
			}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/coupon/coupon/">优惠券列表</a></li>
		<li class="active"><a href="${ctx}/coupon/coupon/updateFormView?id=${coupon.id}">优惠券<shiro:hasPermission name="coupon:coupon:edit">${not empty coupon.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="coupon:coupon:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="coupon" action="${ctx}/coupon/coupon/update" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<ul class="nav nav-tabs" id="tab">
			<li class="active" id="base_info_li"><a>基本信息</a></li>
			<li id="coupon_introduction_li"><a>介绍</a></li>
		</ul>
		
		<div id="base_info_tab">	
		<div class="control-group">
			<label class="control-label"><font color="red">*</font>名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="32" class="input"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><font color="red">*</font>前缀：</label>
			<div class="controls">
				<form:input path="prefix" htmlEscape="false" maxlength="255" class="input"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">使用起始日期：</label>
			<div class="controls">
				<input name="beginDate" type="text" readonly="readonly" maxlength="20" class="text Wdate "
					value="<fmt:formatDate value="${coupon.beginDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">使用结束日期：</label>
			<div class="controls">
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="text Wdate "
					value="<fmt:formatDate value="${coupon.endDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
<!-- 		<div class="control-group">
			<label class="control-label">最大商品价格：</label>
			<div class="controls">
				<input type="text" name="maximumPrice" class="text" maxlength="200" style="margin-bottom: 0px"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最大商品数量：</label>
			<div class="controls">
				<input type="text" name="maximumQuantity" class="text" maxlength="200" style="margin-bottom: 0px"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最小商品价格：</label>
			<div class="controls">
				<input type="text" name="minimumPrice" class="text" maxlength="200" style="margin-bottom: 0px"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最小商品数量：</label>
			<div class="controls">
				<input type="text" name="minimumQuantity" class="text" maxlength="200" style="margin-bottom: 0px"/>
			</div>
		</div> -->
				<div class="control-group">
			<label class="control-label">所需消费金额：</label>
			<div class="controls">
				<form:input path="needConsumeBalance" htmlEscape="false" maxlength="11" class="text"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">面额：</label>
			<div class="controls">
				<form:input path="facePrice" htmlEscape="false" maxlength="11" class="text"/>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">优惠券来源：</label>
			<div class="controls">
				<form:input path="couponSource" htmlEscape="false" maxlength="32" class="text"/>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">数量：</label>
			<div class="controls">
				<form:input path="sum" htmlEscape="false" maxlength="11" class="text"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">赠送数量：</label>
			<div class="controls">
				<form:input path="presentSum" htmlEscape="false" maxlength="11" class="text"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">赠送类型：</label>
			<div class="controls">
				<select id="presentType" name="presentType">
				<c:forEach items="${listPresentType}" var="presentType">
					<c:choose>
						<c:when test="${presentType.key==coupon.presentType}">
							<option value="${presentType.key}" selected="selected">${presentType.value}</option>
						</c:when>
						<c:otherwise>
						<option value="${presentType.key}">${presentType.value}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				</select>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">价格运算表达式：</label>
			<div class="controls">
				<form:input path="priceExpression" htmlEscape="false" maxlength="255" class="input"/>
			</div>
		</div> --%>
		<%-- <div class="control-group">
			<label class="control-label">设置：</label>
			<div class="controls">
				<c:choose>
					<c:when test="${coupon.isExchange}">
					<input type="checkbox" id="isExchange" name="isExchange" value="1" checked="checked"/>是否允许积分兑换
					</c:when>
					<c:otherwise>
					<input type="checkbox" id="isExchange" name="isExchange" value="0"/>是否允许积分兑换
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><font color="red">*</font>积分兑换数：</label>
			<div class="controls">
				<c:choose>
					<c:when test="${coupon.isExchange}">
						<input type="text" id="point" name="point" class="text" value="${coupon.point}" maxlength="9"  onblur="isPoint(this.value)"/>
						<font color="#ffb042"><span id="pointNotnull"></span></font>
					</c:when>
					<c:otherwise>
						<input type="text" id="point" name="point" class="text" value="${coupon.point}" maxlength="9" disabled="disabled" onblur="isPoint(this.value)"/>
						<font color="#ffb042"><span id="pointNotnull"></span></font>
					</c:otherwise>
				</c:choose>
			</div>
		</div> --%>
		</div>
			<!-- 介绍 -->
		<table id="coupon_introduction_tab" style="display:none;width:100%;">
		<tr class="input">
				<td>
					<textarea id="editor" name="introduction" class="editor"  style="width: 100%;">${coupon.introduction}</textarea>
				</td>
			</tr>
		</table>
			
		<div class="form-actions">
			<shiro:hasPermission name="coupon:coupon:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>