<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品属性管理</title>
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/product/propertyGroup/">属性列表</a></li>
		<li class="active"><a href="${ctx}/product/propertyGroup/form?id=${propertyGroup.id}"><shiro:hasPermission name="product:propertyGroup:edit">${not empty propertyGroup.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="product:propertyGroup:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="propertyGroup" action="${ctx}/product/propertyGroup/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="32" class="required"/>
			</div>
		</div>	
		<%-- <div class="control-group">
			<label class="control-label">是否是筛选条件：</label>
			<div class="controls">
				<form:radiobutton path="isFilterCondition" value="1" />是
				<form:radiobutton path="isFilterCondition" value="0" />否
			</div>
		</div>	 --%>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="orders" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品分类：</label>
			<div class="controls">
			<sys:treeselect id="productCategoryId" name="productCategoryId" value="${productCategory.id}" labelName="productCategory.name" labelValue="${productCategory.name}"
					title="商品分类" url="/product/productCategory/treeData" module="product" notAllowSelectRoot="false" cssClass="" allowClear="true"/>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">商户号：</label>
			<div class="controls">
				<form:input path="merchantNo" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">是否作为筛选条件：</label>
			<div class="controls">
			<input type="radio" name="isFiltre" checked="checked" value="0">否
			<input type="radio" name="isFiltre"  value="1"<c:if test="${propertyGroup.isFiltre==1}">checked="checked"</c:if>>是
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
			<div class="control-group">
				<label class="control-label">商品属性表：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>属性名称</th>
								<th>排序</th>
								<th>备注信息</th>
								<shiro:hasPermission name="product:propertyGroup:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="productPropertyList">
						</tbody>
						<shiro:hasPermission name="product:propertyGroup:save"><tfoot>
							<tr><td colspan="8"><a href="javascript:" onclick="addRow('#productPropertyList', productPropertyRowIdx, productPropertyTpl);productPropertyRowIdx = productPropertyRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="productPropertyTpl">//<!--
						<tr id="productPropertyList{{idx}}">
							<td class="hide">
								<input id="productPropertyList{{idx}}_id" name="productPropertyList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="productPropertyList{{idx}}_delFlag" name="productPropertyList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="productPropertyList{{idx}}_name" name="productPropertyList[{{idx}}].name" type="text" value="{{row.name}}" maxlength="32" class="required"/>
							</td>
							<td>
								<input id="productPropertyList{{idx}}_orders" name="productPropertyList[{{idx}}].orders" type="text" value="{{row.orders}}" maxlength="11" class="input-small  digits"/>
							</td>
							<td>
								<textarea id="productPropertyList{{idx}}_remarks" name="productPropertyList[{{idx}}].remarks" rows="4" maxlength="255" class="input-small ">{{row.remarks}}</textarea>
							</td>
							<shiro:hasPermission name="product:propertyGroup:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#productPropertyList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var productPropertyRowIdx = 0, productPropertyTpl = $("#productPropertyTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(propertyGroup.productPropertyList)};
							for (var i=0; i<data.length; i++){
								addRow('#productPropertyList', productPropertyRowIdx, productPropertyTpl, data[i]);
								productPropertyRowIdx = productPropertyRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
		<div class="form-actions">
			<c:if test="${propertyGroup.id==null}">
			<shiro:hasPermission name="product:propertyGroup:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>