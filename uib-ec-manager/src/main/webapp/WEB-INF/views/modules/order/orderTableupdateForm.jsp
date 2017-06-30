<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	var tables = ["base_info_tab","order_product_tab"];
	var $inputForm = $("#inputForm");
	var $input = $("#inputForm :input:not(#productNo)");
	var $amount = $("#amount");
	var $weight = $("#weight");
	var $quantity = $("#quantity");
	var $isInvoice = $("#isInvoice");
	var $invoiceTitle = $("#invoiceTitle");
	var $tax = $("#tax");
	var $areaId = $("#areaId");
	var $orderItemTable = $("#orderItemTable");
	var $deleteOrderItem = $("#orderItemTable a.deleteOrderItem");
	var $productNo = $("#productNo");
	var $addOrderItem = $("#addOrderItem");
	var isLocked = false;
	var timeouts = {};
	var hiddenAmount =  parseInt($("#hiddenAmount").val());
	var amount =  parseInt($("#amount").text());
	var tex = parseInt($("#tex").val());
	var reg=/^(([0-9]*[1-9][0-9]*)|0)$/;
	
	
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
			
			var codes = $("#area").val().split(',');
			getProvince(1);
			if(codes!=null){
				getCity(codes[0],true);
				getArea(codes[1],true);
			}
			$("#selarea").change(function(){
				var provinceCode = $("#selprovince").val();
				var cityCode = $("#selcity").val();
				var areaCode = $("#selarea").val();
				$("#area").val(provinceCode+","+cityCode+","+areaCode);
				var provinceText = $("#selprovince").find("option:selected").text(); 
				var cityText = $("#selcity").find("option:selected").text(); 
				var areaText = $("#selarea").find("option:selected").text();
				$("#areaName").val(provinceText+cityText+areaText);
			});
			
			/* //验证调整金额
			  $('#offsetAmount').keyup(function(e) {
				var key = e.keyCode;
				var hiddenAmount =  parseInt($("#hiddenAmount").val());
				var amount =  parseInt($("#amount").text());
				var offsetAmount = parseInt($("#offsetAmount").val());
				 if(!reg.test(offsetAmount)){
						$("#amountNotnull").html("只允许输入零或正整数");   
					}
				 if( (key <48 || key>57) && key != 8) {
			            return false;
			        }
				 if(key == 8) {
					  $("#amount").text(amount-(offsetAmount*10+offsetAmount));
					 if( $("#offsetAmount").val()==0){
						 $("#amount").text(hiddenAmount);
					 }
						return true;
			        }
				 $("#amount").text(amount+offsetAmount);
			});  */
			
			/* //验证运费
			  $('#freight').keyup(function(e) {
			    var key = e.keyCode;
				var hiddenAmount =  parseInt($("#hiddenAmount").val());
				var amount =  parseInt($("#amount").text());
				var freight = parseInt($("#freight").val());
				 if(!reg.test(freight)){
						$("#freightNotnull").html("只允许输入零或正整数");   
					}
				 if( (key <48 || key>57) && key != 8) {
			            return false;
			        }
				 if(key == 8) {
					 $("#amount").text(amount-(freight*10+freight));
					 if( $("#freight").val()==0){
						 $("#amount").text(hiddenAmount);
					 }
						return true;
			        }
				 $("#amount").text(amount+freight);
			});  */
			
			/* //验证税金
			  $('#tex').keyup(function(e) {
				 var key = e.keyCode;	
				 var hiddenAmount =  parseInt($("#hiddenAmount").val());
				 var amount =  parseInt($("#amount").text());
				 var tex = parseInt($("#tex").val());
				 if(!reg.test(tex)){
						$("#texNotnull").html("只允许输入零或正整数");   
					}
				 if( (key <48 || key>57) && key != 8) {
			            return false;
			        }
				 if(key == 8) {
					  $("#amount").text(amount-(tex*10+tex));
					 if(tex==0){
						 $("#amount").text(hiddenAmount);
					 }
						return true;
			        }
				 $("#amount").text(amount+tex);
			}); 
			*/
		}); 

		// 开据发票
		$isInvoice.live("click",function() {
				if ($(this).prop("checked")) {
					$("#invoiceTitle").prop("disabled",false); 
					$("#tax").prop("disabled",false);
				} else {
					$("#invoiceTitle").prop("disabled",true);
					$("#tax").prop("disabled",true);
				}
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
		
		function getProvince(Id) {
			$("option:gt(0)","#selcity").remove();
			$.ajax( {
				type :'post',
				url :'../../sys/area/listByParentId?parentId='+Id,
				dataType :'json',
				async: false,
				success : function(result) {
					var proviceCode = $("#area").val().split(',')[0];
					$.each(result, function(entryIndex, entry) {
						var selected = "";
						if(proviceCode==entry.id){
							selected = "selected=\"selected\"";
							var way2=$("#provicediv span").eq(0);
							way2.text(entry.name);
						}
						var html = "<option value='" + entry.id + "'"+selected+">"+ entry.name + "</option>";
						$("#selprovince").append(html);
					});
					
				}
			});
		}
		
		function getCity(Id,isFirst) {
			var way2=$("#citydiv span").eq(0);
			way2.text("-请选择城市-");
			$('#areadiv').css('display','none');
			$("option:gt(0)","#selcity").remove();
			$("option:gt(0)","#selarea").remove();
			
			$.ajax( {
				type :'post',
				url :'../../sys/area/listByParentId?parentId='+Id,
				dataType :'json',
				async: false,
				success : function(result) {
					$.each(result, function(entryIndex, entry) {
						var selected = "";
						var cityCode = $("#area").val().split(',')[1];
						if(cityCode==entry.id && isFirst!=null){
							selected = "selected=\"selected\""
							way2.text(entry.name);
						}
						var html = "<option value='" + entry.id+ "'"+selected+">"+ entry.name + "</option>";
						$("#selcity").append(html);
					});
					$('#citydiv').css('display','inline');
				}
			});
		}
	
		function getArea(Id,isFirst) {
			var way2=$(" #areadiv span").eq(0);
			way2.text("-请选择地区-");
			if ($('#areadiv').css('display') == "inline") {
				$('#citydiv').css('display','none');
			}
			if ($('#areadiv').css('display') == "none"
					&& $("#selcity").prev().val() != 0
					&& $("#selarea").prev().val() == 0)
					{
			}
			$("#selarea option[value!=0]").remove();
			$.ajax( {
				type :'post',
				url :'../../sys/area/listByParentId?parentId='+Id,
				dataType :'json',
				async: false,
				success : function(result) {
					if(result==""){
						$('#areadiv').css('display','none');
						var provinceCode = $("#selprovince").val();
						var cityCode = $("#selcity").val();
						var areaCode = $("#selarea").val();
						$("#area").val(provinceCode+","+cityCode);
						var provinceText = $("#selprovince").find("option:selected").text(); 
						var cityText = $("#selcity").find("option:selected").text(); 
						$("#areaName").val(provinceText+cityText);
						return;
					}else{
						$('#areadiv').css('display','inline');
						$.each(result, function(entryIndex, entry) {
							var selected = "";
							var areaCode = $("#area").val().split(',')[2];
							if(areaCode==entry.id && isFirst!=null){
								selected = "selected=\"selected\"";
								way2.text(entry.name);
							}
							var html = "<option value='" + entry.id + "'"+selected+">"+ entry.name + "</option>";
							$("#selarea").append(html);
						});
					}
					
				}
			});
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
		  
		
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/order/orderTable/">订单列表</a></li>
		<li class="active"><a href="${ctx}/order/orderTable/updateFormView?id=${orderTable.id}">订单<shiro:hasPermission name="order:orderTable:edit">${not empty orderTable.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="order:orderTable:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="orderTable" action="${ctx}/order/orderTable/update" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		
		<ul class="nav nav-tabs" id="tab">
			<li class="active" id="base_info_li"><a>订单信息</a></li>
			<li id="order_product_li"><a>商品信息</a></li>
		</ul>	
		<div id="base_info_tab">	
		<div class="control-group">
			<label class="control-label">订单编号：</label>
			<div class="controls">
				${orderTable.orderNo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建日期：</label>
			<div class="controls">
				<fmt:formatDate value="${orderTable.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单状态：</label>
			<div class="controls">
				${orderTable.orderStatusName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">B端分销商：</label>
			<div class="controls">
				${orderTable.distributorName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">B端分销商手机号：</label>
			<div class="controls">
				${orderTable.distributorPhone}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">配送状态：</label>
			<div class="controls">
				${orderTable.shippingStatusName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付状态：</label>
			<div class="controls">
				${orderTable.paymentStatusName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">已付金额：</label>
			<div class="controls">
				<c:choose>
				 	<c:when test="${orderTable.amountPaid!=null && orderTable.amountPaid!= 0}">
				 	￥${orderTable.amountPaid}
				 	</c:when>
				 	<c:otherwise>
				 		￥0.00
				 	</c:otherwise>
				 </c:choose>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单金额：</label>
			<div class="controls">
				<c:choose>
				 	<c:when test="${orderTable.amount != 0}">
				 	￥${orderTable.amount}
				 	</c:when>
				 	<c:otherwise>
				 		￥0.00
				 	</c:otherwise>
				 </c:choose>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单来源：</label>
			<div class="controls">
				${orderTable.orderSourceName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">会员：</label>
			<div class="controls">
				<c:choose>
				 	<c:when test="${member.name!=null}">
				 		${member.name}
				 	</c:when>
				 	<c:otherwise>
				 		<span>-</span>
				 	</c:otherwise>
				 </c:choose>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">促销：</label>
			<div class="controls">
				 <c:choose>
				 	<c:when test="${orderTable.promotion!=null && orderTable.promotion!= 0}">
				 	${orderTable.promotion}
				 	</c:when>
				 	<c:otherwise>
				 		-
				 	</c:otherwise>
				 </c:choose>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">促销折扣：</label>
			<div class="controls">
				<c:choose>
				 	<c:when test="${orderTable.promotionDiscount!=null && orderTable.promotionDiscount!= 0}">
				 	￥${orderTable.promotionDiscount}
				 	</c:when>
				 	<c:otherwise>
				 		￥0.00
				 	</c:otherwise>
				 </c:choose>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品重量：</label>
			<div class="controls">
				<span id="weight">${orderTable.weight}</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品数量：</label>
			<div class="controls">
				<span id="quantity">${orderTable.quantity}</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">优惠码：</label>
			<div class="controls">
				 <c:choose>
				 	<c:when test="${orderTable.couponCode!=null}">
				 		${orderTable.couponCode}
				 	</c:when>
				 	<c:otherwise>
				 		<span>-</span>
				 	</c:otherwise>
				 </c:choose>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用户名：</label>
			<div class="controls">
				<c:choose>
				 	<c:when test="${orderTable.userName!=null}">
				 	${orderTable.userName}
				 	</c:when>
				 	<c:otherwise>
				 		-
				 	</c:otherwise>
				 </c:choose>
			</div>
		</div>		
		<div class="control-group">
			<label class="control-label">优惠劵折扣：</label>
			<div class="controls">
				<c:choose>
				 	<c:when test="${orderTable.couponDiscount!=null && orderTable.couponDiscount!= 0}">
				 	￥${orderTable.couponDiscount}
				 	</c:when>
				 	<c:otherwise>
				 		￥0.00
				 	</c:otherwise>
				 </c:choose>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">到期时间：</label>
			<div class="controls">
				<fmt:formatDate value="${orderTable.expire}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">锁定到期时间：</label>
			<div class="controls">
				<fmt:formatDate value="${orderTable.lockExpire}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">地区名称：</label>
			<div class="controls">
				${orderTable.areaName}
				<input type="hidden" id="areaName" name="areaName" class="text"  maxlength="200" />
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">调整金额：</label>
			<div class="controls">
				<form:input path="offsetAmount" htmlEscape="false" class="input" />

				</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">赠送积分：</label>
			<div class="controls">
				<form:input path="point" htmlEscape="false" maxlength="32" class="input"/>
				</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付手续费：</label>
			<div class="controls">
				<c:choose>
				 	<c:when test="${orderTable.fee!=null && orderTable.fee!= 0}">
				 	￥${orderTable.fee}
				 	</c:when>
				 	<c:otherwise>
				 		￥0.00
				 	</c:otherwise>
				 </c:choose>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">运费：</label>
			<div class="controls">
				<form:input path="freight" htmlEscape="false" maxlength="32" class="input" />
				</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付方式：</label>
			<div class="controls">
				<select id="paymentMethod" name="paymentMethod" data-value="{{row.orderShipping}}" class="input-small ">
					<option value="">请选择</option>
					<c:forEach items="${paymentMethods}" var="pm">
					<c:choose>
						<c:when test="${paymentMethod.id==pm.id}">
							<option value="${paymentMethod.id}" selected="selected">
							${paymentMethod.name}</option>
						</c:when>
						<c:otherwise>
							<option value="${pm.id}">
							${pm.name}</option>
						</c:otherwise>
					</c:choose>
					</c:forEach>
				</select>
				</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">配送方式：</label>
			<div class="controls">
				<select id="shippingMethod" name="shippingMethod" data-value="{{row.orderShipping}}" class="input-small ">
					<option value="">请选择</option>
					<c:forEach items="${shippingMethods}" var="sm">
						<c:choose>
						<c:when test="${shippingMethod.id==sm.id}">
							<option value="${shippingMethod.id}" selected="selected">
							${shippingMethod.name}</option>
						</c:when>
						<c:otherwise>
							<option value="${sm.id}">
							${sm.name}</option>
						</c:otherwise>
					</c:choose>
					</c:forEach>
				</select>
				</div>
		</div> --%>
		<%-- <div class="control-group">
			<label class="control-label">发票抬头：</label>
			<div class="controls">
				<c:choose>
					<c:when test="${orderTable.isInvoice!=0}">
						<input type="text" id="invoiceTitle" name="invoiceTitle" class="text" value="${orderTable.invoiceTitle}" maxlength="200" disabled="disabled"/>
						<input type="checkbox" id="isInvoice" name="isInvoice" value="0" />是否开据发票
					</c:when>
					<c:otherwise>
						<input type="text" id="invoiceTitle" name="invoiceTitle" class="text" value="${orderTable.invoiceTitle}" maxlength="200" />
						<input type="checkbox" id="isInvoice" name="isInvoice" value="1" checked="checked"/>是否开据发票
					</c:otherwise>
				</c:choose>
				</div>
		</div>
		<div class="control-group">
			<label class="control-label">税金：</label>
			<div class="controls">
			<c:choose>
				<c:when test="${order.isInvoice!=0}">
				<input type="text" id="tax" name="tax" class="text" value="${orderTable.tax}" maxlength="16" disabled="disabled" />

				</c:when>
			</c:choose>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">邮编：</label>
			<div class="controls">
				<form:input path="zipCode" htmlEscape="false" maxlength="32" class="input"/>
				 </div>
		</div>
		<div class="control-group">
			<label class="control-label">地区：</label>
			<div class="controls" >
					<div style="display: inline;" id="provicediv">
					  <select id="selprovince" name="areat" style="width: 130px" onchange="getCity(this.value,null)">
						<option value="0">-请选择省份-</option>
					  </select>
					</div>
					<div id="citydiv" style="display: inline;">
					  <select id="selcity" style="width: 130px" onchange="getArea(this.value,null)">
						<option value="0">-请选择城市-</option>
					  </select>
					</div>
					<div id="areadiv" style="display: none;">
					  <select id="selarea" style="width: 130px">
						<option value="0">-请选择地区-</option>
					  </select>
					 </div>
				 <form:input path="area" type="hidden" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收货人：</label>
			<div class="controls">
				<form:input path="consignee" htmlEscape="false" maxlength="32" class="input"/> 
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地址：</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="32" class="input"/> 
				
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">电话：</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" maxlength="32" class="input" /> 
			
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">附言：</label>
			<div class="controls">
				<form:input path="memo" htmlEscape="false" maxlength="32" class="input" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">异常备注：</label>
			<div class="controls">
				<textarea name="exceptionRemarks" rows="5" cols="8">${orderTable.exceptionRemarks}</textarea>
			</div>
		</div>
	</div>
	<!-- 商品信息 -->
		<table id="order_product_tab" style="display:none;width:100%;">
		<tr height="40" class="title" style="background-color: #f1f8ff">
			<td>商品编号</td>
			<td>商品名称</td>
			<td>商品价格</td>
			<td>数量</td>
			<td>小计</td>
			<!-- <td>操作</td> -->
		</tr>
		<c:forEach items="${orderTableItems}" var="orderTableItem">
			<tr  height="40">
				<td>
					${orderTableItem.product.id}
				</td>
				<td>
					${orderTableItem.product.name}
				</td>
				<td>
				<%-- <input type="text" id="price" name="price" class="text" maxlength="100" style="width: 30px"  value="${orderTableItem.price}" /> --%>
					${orderTableItem.price}
				</td>
				<td>
					<%-- <input type="text" id="quantity" name="quantity" class="text" maxlength="100" style="width: 30px" value="${orderTableItem.quantity}" />
					<span id="quantityNotnull"></span> --%>
					${orderTableItem.quantity}
				</td>
				<td>
					<span id ="subtotal">${orderTableItem.price*orderTableItem.quantity}</span>
				</td>
				<%-- <td><a href="${ctx}/order/orderTabel/deleteorderTableItem?id=${orderTableItem.id}" onclick="return confirmx('确认要删除该商品信息吗？', this.href)">删除</a></td> --%>
			</tr>
			</c:forEach>
		</table>
		<div class="form-actions">
			<shiro:hasPermission name="order:orderTable:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>