<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/kindeditor.js"></script>
	<script type="text/javascript">
	var tables = ["base_info_tab","product_introduce_tab","product_image_tab","product_parameter_tab","product_property_tab","product_pecification_tab"];
		KindEditor.ready(function(K) {
		K.create('textarea[name="introduction"]', {
			uploadJson : '${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/jsp/upload_json.jsp',
		                fileManagerJson : '${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/jsp/file_manager_json.jsp',
		                allowFileManager : true,
		                allowImageUpload : true, 
		    width : "100%",
			autoHeightMode : true,
			afterCreate : function() {this.loadPlugin('autoheight');},
			afterBlur : function(){ this.sync(); }  //Kindeditor下获取文本框信息
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
			})
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/product/product/">商品列表</a></li>
		<li class="active"><a href="${ctx}/product/product/form?id=${product.id}">商品<shiro:hasPermission name="product:product:edit">${not empty product.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="product:product:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="product" action="${ctx}/product/product/save" method="post" class="form-horizontal">
		<ul class="nav nav-tabs" id="tab">
			<li class="active" id="base_info_li"><a>基本信息</a></li>
			<li id="product_introduce_li"><a>商品介绍</a></li>
			<li id="product_image_li"><a>商品图片</a></li>
			<li id="product_parameter_li"><a>商品参数</a></li>
			<li id="product_property_li"><a>商品属性</a></li>
			<li id="product_pecification_li"><a>商品规格</a></li>
		</ul><br/>
		<div id="base_info_tab">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">商品编号：</label>
			<div class="controls">
				<form:input path="productNo" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">已分配库存：</label>
			<div class="controls">
				<form:input path="allocatedStock" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">成本价：</label>
			<div class="controls">
				<form:input path="cost" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">全称：</label>
			<div class="controls">
				<form:input path="fullName" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">点击数：</label>
			<div class="controls">
				<form:input path="hits" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">展示图片：</label>
			<div class="controls">
				<form:input path="image" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否为赠品：</label>
			<div class="controls">
				<form:select path="isGift" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('is_gift')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否列出：</label>
			<div class="controls">
				<form:select path="isList" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('is_list')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否上架：</label>
			<div class="controls">
				<form:select path="isMarketable" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('is_marketable')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否置顶：</label>
			<div class="controls">
				<form:select path="isTop" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('is_top')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否会员价：</label>
			<div class="controls">
				<form:input path="isMemberPrice" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">热销：</label>
			<div class="controls">
				<form:input path="hotSell" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最新：</label>
			<div class="controls">
				<form:input path="newest" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">活动促销：</label>
			<div class="controls">
				<form:input path="promotion" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">搜索关键词：</label>
			<div class="controls">
				<form:input path="keyword" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">市场价：</label>
			<div class="controls">
				<form:input path="marketPrice" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:input path="memo" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">月点击数：</label>
			<div class="controls">
				<form:input path="monthHits" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">月点击数更新日期：</label>
			<div class="controls">
				<input name="monthHitsDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${product.monthHitsDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">月销售量：</label>
			<div class="controls">
				<form:input path="monthSales" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">月销售量更新日期：</label>
			<div class="controls">
				<input name="monthSalesDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${product.monthSalesDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">赠送积分：</label>
			<div class="controls">
				<form:input path="point" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">销售价格：</label>
			<div class="controls">
				<form:input path="price" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">销量：</label>
			<div class="controls">
				<form:input path="sales" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">评分：</label>
			<div class="controls">
				<form:input path="score" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">评分数：</label>
			<div class="controls">
				<form:input path="scoreCount" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">页面描述：</label>
			<div class="controls">
				<form:input path="seoDescription" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">页面关键词：</label>
			<div class="controls">
				<form:input path="seoKeywords" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">页面标题：</label>
			<div class="controls">
				<form:input path="seoTitle" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">库存：</label>
			<div class="controls">
				<form:input path="stock" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">库存备注：</label>
			<div class="controls">
				<form:input path="stockMemo" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">总评分：</label>
			<div class="controls">
				<form:input path="totalScore" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位：</label>
			<div class="controls">
				<form:input path="unit" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">周点击数：</label>
			<div class="controls">
				<form:input path="weekHits" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">周点击数更新日期：</label>
			<div class="controls">
				<input name="weekHitsDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${product.weekHitsDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">周销售量：</label>
			<div class="controls">
				<form:input path="weekSales" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">周销售量更新日期：</label>
			<div class="controls">
				<input name="weekSalesDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${product.weekSalesDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">重量：</label>
			<div class="controls">
				<form:input path="weight" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">货品：</label>
			<div class="controls">
				<form:input path="goods" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否自营：</label>
			<div class="controls">
				<form:input path="proprietary" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">商户号：</label>
			<div class="controls">
				<form:input path="merchantNo" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div> --%>
		<div class="control-group">
			<!-- <label class="control-label">分类主键ID：</label>
			<div class="controls">
				<form:select path="productCategoryId" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
			 -->
			<label class="control-label">商品分类：</label>
			<div class="controls">
			<sys:treeselect id="productCategory" name="productCategoryId" value="${productCategory.id}" labelName="productCategory.name" labelValue="${productCategory.name}"
					title="商品分类" url="/product/productCategory/treeData" module="product" notAllowSelectRoot="false" cssClass="input-small"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">品牌：</label>
			<div class="controls">
				<form:select path="brandId" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${brandList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		</div>
		<!--
			<div class="control-group">
				<label class="control-label">商品评论：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>评论内容</th>
								<th>评论的ip地址</th>
								<th>是否显示 1:是 0:否</th>
								<th>评分</th>
								<th>会员帐号</th>
								<th>商品编号</th>
								<th>备注信息</th>
								<shiro:hasPermission name="product:product:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="productReviewList">
						</tbody>
						<shiro:hasPermission name="product:product:save"><tfoot>
							<tr><td colspan="9"><a href="javascript:" onclick="addRow('#productReviewList', productReviewRowIdx, productReviewTpl);productReviewRowIdx = productReviewRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="productReviewTpl">//
						<tr id="productReviewList{{idx}}">
							<td class="hide">
								<input id="productReviewList{{idx}}_id" name="productReviewList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="productReviewList{{idx}}_delFlag" name="productReviewList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<textarea id="productReviewList{{idx}}_content" name="productReviewList[{{idx}}].content" rows="4" maxlength="5000" class="input-small ">{{row.content}}</textarea>
							</td>
							<td>
								<input id="productReviewList{{idx}}_ip" name="productReviewList[{{idx}}].ip" type="text" value="{{row.ip}}" maxlength="32" class="input-small "/>
							</td>
							<td>
								<input id="productReviewList{{idx}}_isShow" name="productReviewList[{{idx}}].isShow" type="text" value="{{row.isShow}}" maxlength="1" class="input-small "/>
							</td>
							<td>
								<input id="productReviewList{{idx}}_score" name="productReviewList[{{idx}}].score" type="text" value="{{row.score}}" maxlength="11" class="input-small  digits"/>
							</td>
							<td>
								<input id="productReviewList{{idx}}_useName" name="productReviewList[{{idx}}].useName" type="text" value="{{row.useName}}" maxlength="32" class="input-small "/>
							</td>
							<td>
								<input id="productReviewList{{idx}}_productNo" name="productReviewList[{{idx}}].productNo" type="text" value="{{row.productNo}}" maxlength="32" class="input-small "/>
							</td>
							<td>
								<textarea id="productReviewList{{idx}}_remarks" name="productReviewList[{{idx}}].remarks" rows="4" maxlength="255" class="input-small ">{{row.remarks}}</textarea>
							</td>
							<shiro:hasPermission name="product:product:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#productReviewList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//
					</script>
					<script type="text/javascript">
						var productReviewRowIdx = 0, productReviewTpl = $("#productReviewTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(product.productReviewList)};
							for (var i=0; i<data.length; i++){
								addRow('#productReviewList', productReviewRowIdx, productReviewTpl, data[i]);
								productReviewRowIdx = productReviewRowIdx + 1;
							}
						});
					</script>
					
				</div>
			</div>
			-->
		<table id="product_introduce_tab" style="display:none;width:100%;">
			<tr>
				<td>
					<textarea id="editor" name="introduction" class="editor" style="width: 100%;"></textarea>
				</td>
			</tr>
		</table>
		
		<table id="product_image_tab" width="100%">
			<thead>
				<tr>
					 <th class="hide"></th>
					<!--<th class="hide">商品编号</th>
						<th class="hide">图片路径</th>  -->
						<th>标题</th>
						<th>图片</th>
						<!-- <th class="hide">大图片</th>
						<th class="hide">中图片</th>
						<th class="hide">缩略图</th> -->
						<th>排序</th>
						<shiro:hasPermission name="product:product:edit"><th width="20%">删除操作&nbsp;</th></shiro:hasPermission>
				</tr>
			</thead>
			<tbody id="productImageRefList">
			</tbody>
			<shiro:hasPermission name="product:product:save"><tfoot>
			<tr><td colspan="10"><a href="javascript:" onclick="addRow('#productImageRefList', productImageRefRowIdx, productImageRefTpl);productImageRefRowIdx = productImageRefRowIdx + 1;" class="btn">新增</a></td></tr>
			</tfoot></shiro:hasPermission>
		</table>
			<script type="text/template" id="productImageRefTpl">//<!--
						<tr id="productImageRefList{{idx}}">
							<td class="hide">
								<input id="productImageRefList{{idx}}_id" name="productImageRefList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="productImageRefList{{idx}}_delFlag" name="productImageRefList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td width="20%">
								<input id="productImageRefList{{idx}}_title" name="productImageRefList[{{idx}}].title" type="text" value="{{row.title}}" maxlength="32" class="input-small "/>
							</td>
							<td width="40%">
								<input id="productImageRefList{{idx}}_source" name="productImageRefList[{{idx}}].source" type="hidden" value="{{row.source}}" maxlength="32"/>
								<sys:ckfinder input="productImageRefList{{idx}}_source" type="files" uploadPath="/product/product" selectMultiple="true"/>
							</td>
							<td width="20%">
								<input id="productImageRefList{{idx}}_orders" name="productImageRefList[{{idx}}].orders" type="text" value="{{row.orders}}" maxlength="11" class="input-small "/>
							</td>
							<shiro:hasPermission name="product:product:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#productImageRefList{{idx}}')" title="删除" text-align="center">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//--
					</script>
					<script type="text/javascript">
						var productImageRefRowIdx = 0, productImageRefTpl = $("#productImageRefTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(product.productImageRefList)};
							for (var i=0; i<data.length; i++){
								addRow('#productImageRefList', productImageRefRowIdx, productImageRefTpl, data[i]);
								productImageRefRowIdx = productImageRefRowIdx + 1;
							}
						});
					</script>
		<!--
		<table id="product_parameter_tab" ></table>
		<table id="product_property_tab" ></table>
		<table class="product_pecification_tab"></table>
		 -->
		<div class="form-actions">
			<shiro:hasPermission name="product:product:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>