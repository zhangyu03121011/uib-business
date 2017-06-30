<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<title>商品管理</title>
<meta name="decorator" content="default" />
<%@include file="/WEB-INF/views/include/treetable.jsp"%>
<style type="text/css">
.specificationGroupSelect {
	height: 100px;
	padding: 5px;
	overflow-y: scroll;
	border: 1px solid #cccccc;
}

.specificationGroupSelect ul li {
	float: left;
	min-width: 150px;
	_width: 200px;
	list-style-type: none;
}

.gray {
	color: gray;
}
</style>

<script type="text/javascript"
	src="${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/kindeditor.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/themes/default/default.css" />
<script
	src="${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/lang/zh_CN.js"></script>
<script type="text/javascript">
	var productCategoryId='';
	var firstFlag = true;
	var tables = [ "base_info_tab", "product_introduce_tab",
			"product_image_tab", "product_parameter_tab",
			"product_property_tab", "product_pecification_tab", "product_price_tab","sales_area_tab" ];
	var tables_area = ["update_sales_area_tab","select_sales_area_tab" ];
	KindEditor
			.ready(function(K) {
				K
						.create(
								'textarea[name="introduction"]',
								{
									uploadJson : '${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/jsp/upload_json.jsp',
									fileManagerJson : '${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/jsp/file_manager_json.jsp',
									allowFileManager : true,
									
									allowImageUpload : true,
									width : "100%",
									height:'250px',
									autoHeightMode : true,
									afterCreate : function() {
										//this.loadPlugin('autoheight');
									},
									afterBlur : function() {
										this.sync();
									} //Kindeditor下获取文本框信息
								});
				var editor = K.editor({
					allowFileManager : true
				});
				$('#product_image_tab').delegate(
						"tr td input[type=\"button\"]",
						"click",
						function() {
							console.info($(this).val() + ","
									+ $(this).prev().attr("id"));
							var url_id = $(this).prev().attr("id");
							editor.loadPlugin('image', function() {
								editor.plugin.imageDialog({
									imageUrl : K('#' + url_id).val(),
									clickFn : function(url, title, width,
											height, border, align) {
										//alert("url===" + url);
										K('#' + url_id).val(url);
										editor.hideDialog();
									}
								});
							});
						});
				
				var image_select_button = K.uploadbutton({
					button : K('#image_select_button')[0],
					fieldName : 'imgFile',
					url : '${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/jsp/upload_file.jsp?dir=image',
					afterUpload : function(data) {
						if (data.error === 0) {
							var url = K.formatUrl(data.url, 'absolute');
							K('#url').val(url);
						} else {
							alert(data.message);
						}
					},
					afterError : function(str) {
						alert('自定义错误信息: ' + str);
					}
				});
				image_select_button.fileBox.change(function(e) {
					image_select_button.submit();
				});
				//K('#image_select_button').click(
				//		function() {
				//			editor.loadPlugin('image', function() {
				//				editor.plugin.imageDialog({
				//					imageUrl : K('#image').val(),
				//					clickFn : function(url, title, width,
				//							height, border, align) {
				//						K('#image').val(url);
				//						editor.hideDialog();
				//					}
				//				});
				//			});
				//		});
			});
			$(document).ready(
				function() {
				var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				var data = ${fns:toJson(list)}, rootId = "0";
				addRow("#treeTableList", tpl, data, rootId, true);
				$("#treeTable").treeTable({expandLevel : 1});
				//$("#name").focus();
				$("#inputForm").validate({
				submitHandler: function(form){
						loading('正在提交，请稍等...');
						form.submit();
				},
				errorContainer : "#messageBox",
				errorPlacement : function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")
							|| element.is(":radio")
							|| element.parent().is(
									".input-append")) {
						error.appendTo(element.parent()
								.parent());
					} else {
						error.insertAfter(element);
					}
				}
				});
			});
			function addRow(list, tpl, data, pid, root){
				for (var i=0; i<data.length; i++){
					var row = data[i];
					if(row.areaId.length>0){
						row.checked = "checked";
					}
					if ((${fns:jsGetVal('row.parentId')}) == pid){
						$(list).append(Mustache.render(tpl, {
							dict: {
								type: getDictLabel(${fns:toJson(fns:getDictList('sys_area_type'))}, row.type)
							}, pid: (root?0:pid), row: row 
						}));
						addRow(list, tpl, data, row.code);
					}
				}
				
			}
			function checkAll(parents,code,obj,type) {
				var areaCode = code+"";
				var areaCodeInit =  $("input[id^='areaId_"+areaCode+"']");
				var provinceAreaCode = areaCode.substring(0,areaCode.length-4);
				areaCode = areaCode.substring(0,areaCode.length-2);
				var provinceArea = parents.substring(parents.indexOf(",")+1).substring(0,parents.indexOf(","));
				var cityArea = parents.substring(parents.indexOf(",")+1).substring(parents.indexOf(",")+1).substring(0,6);
				var provinceInput =  $("input[id^='areaId_"+provinceArea+"']");
				var cityInput =  $("input[id^='areaId_"+cityArea+"']");
				var areaInput =  $("input[id^='areaId_"+areaCode+"']");
				var provinceAreaInput = $("input[id^='areaId_"+provinceAreaCode+"']");
				if(type == 4 ){
					if(obj.checked == true){
							areaCodeInit.attr("checked", true);
							provinceInput.attr("checked", true);
							cityInput.attr("checked", true);
						} else {
							areaCodeInit.attr("checked", false);
						}
				}else if(obj.checked == true){
					if(type == 2){
						provinceAreaInput.attr("checked", true);
					}else if(type == 3){
					provinceInput.attr("checked", true);
					areaInput.attr("checked", true);
					}else if(type == 1){
						provinceInput.attr("checked", true);
						cityInput.attr("checked", true);
						areaInput.attr("checked", true);
					}
				} else {
					if(type == 2){
						provinceAreaInput.attr("checked", false);
					}else if(type==3){
					provinceInput.attr("checked", false);
					areaInput.attr("checked", false);
					}else if(type == 1){
						provinceInput.attr("checked", false);
						cityInput.attr("checked", false);
						areaInput.attr("checked", false);
					}
				}
			}
			  /*function addRow(list, idx, tpl, row) {
					$(list).append(Mustache.render(tpl, {
						idx : idx,
						delBtn : true,
						row : row
					}));
					$(list + idx).find("select").each(function() {
						$(this).val($(this).attr("data-value"));
					});
					$(list + idx).find("input[type='checkbox'], input[type='radio']").each(
							function() {
								var ss = $(this).attr("data-value").split(',');
								for (var i = 0; i < ss.length; i++) {
									if ($(this).val() == ss[i]) {
										$(this).attr("checked", "checked");
									}
								}
							});
						}
				function delRow(obj, prefix) {
					var id = $(prefix + "_id");
					var delFlag = $(prefix + "_delFlag");
					if (id.val() == "") {
						$(obj).parent().parent().remove();
					} else if (delFlag.val() == "0") {
						delFlag.val("1");
						$(obj).html("&divide;").attr("title", "撤销删除");
						$(obj).parent().parent().addClass("error");
					} else if (delFlag.val() == "1") {
						delFlag.val("0");
						$(obj).html("&times;").attr("title", "删除");
						$(obj).parent().parent().removeClass("error");
					}
				} */
			$(function() {
				//var productObj = ${fns:toJson(product)};
				$("#tab_header").children("li").click(
				function() {
					$(this).addClass("active").siblings().removeClass("active");
					var table_id = $(this).attr("id").replace("li","tab");
					for (var i = 0; i < tables.length; i++) {
						$("#" + tables[i]).attr("style", "display:none");
						if (tables[i] == table_id) {
							$("#" + table_id).attr("style","display:table;width:100%");
						}

					}
				});
				$("#tab_area_header").children("li").click(
				function() {
					$(this).addClass("active").siblings().removeClass("active");
					var table_id = $(this).attr("id").replace("li","tab");
					for (var i = 0; i < tables_area.length; i++) {
						$("#" + tables_area[i]).attr("style", "display:none");
						if (tables_area[i] == table_id) {
							$("#" + table_id).attr("style","display:table;width:100%");
						}
					}
				});
		var $productImageTable = $("#product_image_tab");
		//console.info(${fn:length(product.productImageRefList)});
		var productImageIndex = ${fn:length(product.productImageRefList)};
		var $addProductImage = $("#addProductImage");
		var $deleteProductImage = $("a.deleteProductImage");
		var $addSpecificationProduct = $("#addSpecificationProduct");
		var $specificationProductTable = $("#specificationProductTable");
		var $specificationGroupIds = $("#specificationGroupSelect :checkbox");
		//console.info("specificationGroupIds.size:"+$specificationGroupIds.size());
		var $deleteSpecificationProduct = $("#deleteSpecificationProduct");
		$.ajax({
		    url: "../specification/specificationJson",
		    type: "GET",
		    dataType: "json",
		    cache: false,
		    success: function(jsonData) {
		        //console.info("jsonData.length:"+jsonData.length);
		        var trHtml = "<tr style=\"display:none;\">" + "<td width=\"60\">" + "&nbsp;</td>";
		        var tdHtmlArray = new Array();
		        $.each(${fns:toJson(specificationGroups)},
		        function(index, value) {
		        	var tdHtml = "<td"
		        	if(!value.selected){
		        		tdHtml += " style=\"display:none;\"";
		        	}
		            tdHtml += " class=\"specification_" + value.id + "\">" + "<select name=\"select_"+value.id+"\" class=\"input-xlarge\" disabled=\"disabled\">";
		            $.each(value.productSpecificationList,
		            function(index_, specification) {
		                tdHtml += "<option value=\"" + specification.id + "\">" + specification.name + "</option>"
		            });
		            tdHtml += "</select></td>";
		            $specificationGroupIds.each(function(index) {
		                if (value.id == $(this).val()) {
		                    tdHtmlArray[index] = tdHtml;
		                    return false;
		                }
		            });
		        });
		        $.each(tdHtmlArray,
		        function(index, value) {
		            trHtml += value;
		        });
		        trHtml += "<td><a href=\"javascript:;\" id=\"deleteSpecificationProduct\">删除</a></td></tr>";
		        //console.info(${fns:toJson(products)});
		       	var firstFlag = true;
		        $.each(${fns:toJson(products)},function(idx,product){
		        	trHtml += "<tr>" + "<td width=\"60\">" + "&nbsp;</td>";
			        var tdHtmlArray = new Array();
			        $.each(${fns:toJson(specificationGroups)},
			        function(index, value) {
			        	var tdHtml = ""
			        	if(!value.selected){
			        		tdHtml += "<td style=\"display:none;\" class=\"specification_" + value.id + "\">" + "<select name=\"select_"+value.id+"\" class=\"input-xlarge\" disabled=\"disabled\">";
			        	}else{
			        		tdHtml += "<td class=\"specification_" + value.id + "\">" + "<select name=\"select_"+value.id+"\" class=\"input-xlarge\">";
			        	}
			            $.each(value.productSpecificationList,
			            function(index_, specification) {
			            	var selectFlag = "";
			            	$.each(product.productSpecificationRefs,function(i,ref){
			            		if(ref.specificationId==specification.id){
			            			selectFlag="selected";
			            			return false;
			            		}
			            	});
			                tdHtml += "<option value=\"" + specification.id + "\""+selectFlag+">" + specification.name + "</option>"
			            });
			            tdHtml += "</select></td>";
			            $specificationGroupIds.each(function(index) {
			                if (value.id == $(this).val()) {
			                    tdHtmlArray[index] = tdHtml;
			                    return false;
			                }
			            });
			        });
			        $.each(tdHtmlArray,
			        function(index, value) {
			            trHtml += value;
			        });
			        if(firstFlag){
			        	trHtml += "<td><a href=\"javascript:;\">--</a></td></tr>";
			        	firstFlag = false;
			        }else{
			        	trHtml += "<td><a href=\"javascript:;\" id=\"deleteSpecificationProduct\">删除</a></td></tr>";
			        }
		        });
		        $("#specificationProductTable").append(trHtml);
		        $("#specificationProductTable").data("specificationGroupsJson", jsonData); //缓存对象
		        change4specificationIds();
		    }
		});
		$specificationProductTable.on("change","select[name^=select]",function(){
			change4specificationIds();
		});
		// 增加商品图片
		$addProductImage.click(function() {
			if (productImageIndex == 0) {
				productImageIndex = $("#productImageTable tr").length + 1;
			}
			var trHtml = "<tr>"
					+ "<td>"
					+ "<input type=\"text\" id=\"productImages_"+productImageIndex+"_id\" name=\"productImageRefList[" + productImageIndex + "].filePath\" class=\"productImageFile\" \/>"
					+ "<input type=\"button\" id=\"productImages_"+productImageIndex+"_button\" value=\"选择文件\"\/>"
					+ "<\/td>"
					+ "<td>"
					+ "<input type=\"text\" name=\"productImageRefList[" + productImageIndex + "].title\" class=\"text\" maxlength=\"200\" \/>"
					+ "<\/td>"
					+ "<td>"
					+ "<input type=\"text\" name=\"productImageRefList[" + productImageIndex + "].orders\" class=\"text productImageOrder\" maxlength=\"9\" style=\"width: 50px;\" \/>"
					+ "<\/td>"
					+ "<td>"
					+ "<a href=\"javascript:;\" class=\"deleteProductImage\">删除<\/a>"
					+ "<\/td>" + "<\/tr>";
			$productImageTable.append(trHtml);
			productImageIndex++;
		});
		// 删除商品图片
		$deleteProductImage.live("click", function() {
			var $this = $(this);
			$.jBox.confirm("确认删除?", "提示", function(v, h, f) {
				if (v === 'ok') {
					$this.closest("tr").remove();
					productImageIndex--;
				}
				return true;
			});
		});
		// 修改商品规格
		$("#specificationGroupsTable").on("click","#specificationGroupSelect :checkbox",function() {
			$specificationGroupIds = $("#specificationGroupSelect :checkbox");
			if ($specificationGroupIds.filter(":checked").size() == 0) {
				$specificationProductTable.find("tr:gt(1)").remove();
				$specificationProductTable.find("tr:eq(0)").find("td:last").hide();
			}
			var $this = $(this);
			if ($this.prop("checked")) {
				$("#specificationProductTable input[name='specificationGroup_"+$this.val()+"'").removeAttr("disabled");
				$specificationProductTable.find("td.specification_" + $this.val()).show();
				$specificationProductTable.find("tr:gt(1) td.specification_" + $this.val()).find("select").attr("disabled", false)
				$specificationProductTable.find("tr:eq(0)").find("td:last").show();
			} else {
				$("#specificationProductTable input[name='specificationGroup_"+$this.val()+"'").attr("disabled","disabled");
				$specificationProductTable.find("td.specification_" + $this.val()).hide().find("select").attr("disabled", "disabled");
			}
			change4specificationIds();
		});
		// 增加规格商品
		$addSpecificationProduct.click(function() {
			$specificationGroupIds = $("#specificationGroupSelect :checkbox");
			if ($specificationGroupIds.filter(":checked").size() == 0) {
				$.jBox.tip("至少选择一个规格");
				return false;
			}
			if ($specificationProductTable.find("tr:gt(1)").size() == 0) {
				//console.info($specificationProductTable.find("tr:eq(1)").html());
				$tr = $specificationProductTable.find("tr:eq(1)").clone().show().appendTo($specificationProductTable);
				//console.info($tr.html());
				$tr.find("td:first").text("当前规格");
				$tr.find("td:last").text("-");
				
			} else {
				$specificationProductTable.find("tr:eq(1)").clone(true).show().appendTo($specificationProductTable);
			}
			//console.info($specificationGroupIds.filter(":checked").size());
			$specificationGroupIds.filter(":checked").each(function(){
				$specificationProductTable.find("tr:gt(1) td.specification_" + $(this).val()).find("select").attr("disabled", false);
			});
			change4specificationIds();
		});
		
		// 删除规格商品
		$deleteSpecificationProduct.live("click", function() {
			var $this = $(this);
			//console.info($this.attr("productId"));
			$.jBox.confirm("您确定要删除吗？","提示",function (v, h, f) {
	            if (v === 'ok') {
	            	$this.closest("tr").remove();
	            	change4specificationIds();
	            }
	            return true;
			});
		});

		//分类变化
		setInterval('txChange()',200);
		
		//校验商品价格
		$("#btnSubmit").click(function(){
			var str = "[";
			$("#product_price_tab").find("tr").next().each(function(){
				str += "[";
				$(this).find("input").each(function(){
					str += "\'"+$(this).val() + "\',";
				})
				str = str.substring(0,str.length-1);
				str += "],";
			});
			str = str.substring(0,str.length-1);
			str += "]";
			var datas = eval("("+str+")");	
			console.info(datas);
			var flag = true;
			$.each(datas,function(i,data){
				if (data[1].trim() == "") {
					$("#errorMsg").html(data[0] + "的售价不能为空！");
					flag = false;
				} else if (data[2].trim() == "") {
					$("#errorMsg").html(data[0] + "的B端供货价不能为空！");
					flag = false;
				} else if (parseInt(data[1]) < parseInt(data[2])) {
					$("#errorMsg").html(data[0] + "的B端供货价不能大于售价！");
					flag = false;
				} 
			});
			return flag;
		});	
		
	});

	function txChange(){
		var value = $("#productCategoryId").val();
		if(productCategoryId!=value){
			productCategoryId = value;
			if(firstFlag){
				firstFlag = false;
				return;
			}
			alterParameterTab(value);
			alter_property_tab(value);
			alter_specification_tab(value);
		}
	}
	
	function change4specificationIds(){
		var $specificationProductTable = $("#specificationProductTable");
		
		$specificationProductTable.find("input[name^='specificationGroup_']").each(function(){
			var $this = $(this);
			if($this.prop("disabled")){
				$this.val("");
				return true;
			}
			var groupId = $this.attr("name").substring(19);
			var ids = '';
			//console.info("select.size():"+$specificationProductTable.find("select[name='select_"+groupId+"']").size());
			$specificationProductTable.find("select[name='select_"+groupId+"']:not(:disabled)").each(function(){
				
				ids+=$(this).val()+",";
			});
			//console.info("ids:"+ids);
			$this.val(ids.substring(0,ids.length-1));
			console.info("value:"+$this.val());
		});
	}
	
	function alterParameterTab(productCategoryId){
		var groups = $.data(document,"parameterGroupsJson");
		if(typeof(groups)=='undefined'||groups==null){
			groups = $.data(document,"parameterGroupsJson",${fns:toJson(parameterGroups)});
		}
		var html='';
		for(var i=0;i<groups.length;i++){
			var group = groups[i];
			if(productCategoryId==group.productCategoryId){
				html+= "<div value=\""+group.id+"\" style=\"display:block;\">"+
				"<div class=\"control-group\">"+
					"<label style=\"font-weight:bold;\" class=\"control-label\">"+group.name+":</label>"+
				"</div>"+
				"<div>";
				var parameterList = group.productParameterList;
				for(var j=0;j<parameterList.length;j++){
					var parameter = parameterList[j];
					html += "<div class=\"control-group\">"+
						"<label class=\"control-label\">"+parameter.name+"</label>"+
						"<div class=\"controls\">"+
								"<input name=\"parameter_"+parameter.id+"\" class=\"input-xlarge \" type=\"text\" value=\"\" maxlength=\"32\">"+
						"</div></div>";
				}
			}
		}
		html+="</div></div>";
		$("#product_parameter_tab").empty().append(html);
	}
	
	function alter_property_tab(productCategoryId){
		$.ajax({
			url:"${ctx}/product/productCategory/queryPropertyGroups?categoryId="+productCategoryId+"&hasDetail="+true,
			type:"GET",
			dataType:"json",
			cache:false,
			success:function(data){
				var html='';
				$.each(data,function(index,group){
					html += "<div class=\"control-group\">"+
						"<label class=\"control-label\">"+group.name+"：</label>"+
						"<div class=\"controls\">"+
						"<select name=\"property_"+group.id+"\" class=\"input-xlarge\">";
					var propertyList = group.productPropertyList;
					for(var j = 0 ; j < propertyList.length ; j++){
						var property = propertyList[j];
						html += "<option value=\""+property.id+"\" htmlEscape=\"false\">"+property.name+"</option>";
					}
					html += "</select></div></div>";
				});
				$("#product_property_tab").empty().append(html);
			}
		});
	}
	
	function alter_specification_tab(productCategoryId){
		$.ajax({
			url:"${ctx}/product/specification/specificationJson?productCategoryId="+productCategoryId,
			type:"GET",
			dataType:"json",
			cache:false,
			success: function(jsonData){
				var groups = jsonData;
				//console.info(groups);
				var specificationGroupsTable = $("#specificationGroupsTable");
				var group_tr_html = "<tr><td><div id=\"specificationGroupSelect\" class=\"specificationGroupSelect\"><ul>";
				var ftrHtml = "<tr><td width=\"60\">&nbsp;</td>";
				var trHtml = "<tr style=\"display:none;\"><td width=\"60\">&nbsp;</td>";
				var tdHtmlArray=new Array();
				var hasFlag  = false;
				var size = 0;
				$.each(groups,function(index,value){
			//if(productCategoryId!=value.productCategoryId){
			//	return true;
			//}
					hasFlag = true;
					ftrHtml += "<td class=\"specification_"+value.id+" hidden\" style=\"display:none;\">"+value.name;
					if(value.remarks!=null && value.remarks!=''){
						ftrHtml += "<span class=\"gray\">["+value.remarks+"]</span>";
					}
					ftrHtml += "<input name=\"specificationGroup_"+value.id+"\" style=\"display:none;\" value=\"\" disabled/></td>";
					group_tr_html += "<li><label><input type=\"checkbox\" name=\"specificationGroupIds\" value=\""+value.id+"\" />"+value.name;
					if(value.remarks!=null&& value.remarks!=''){
						group_tr_html += "<span class=\"gray\">["+value.remarks+"]</span>";
					}
					group_tr_html += "</label></li>"; 
					tdHtml = "<td style=\"display:none;\" class=\"specification_"+value.id+"\">"+
						"<select class=\"input-xlarge\" name=\"select_"+value.id+"\" disabled=\"disabled\">";
					$.each(value.productSpecificationList,function(index_,specification){
						tdHtml += "<option value=\""+specification.id+"\">"+specification.name+"</option>"
					});
					tdHtml += "</select></td>";
					tdHtmlArray[size++]=tdHtml;
			//$("#specificationGroupSelect :checkbox").each(function(){
			//	if(value.id == $(this).val()){
			//		tdHtmlArray[size++]=tdHtml;
			//		return false;
			//	}
			//});
					});
					ftrHtml += "<td style=\"display:none;\">操作</td></tr>";
					group_tr_html +="</ul></div></td></tr>"
					$.each(tdHtmlArray,function(index,value){
						trHtml += value;
					});
					trHtml += "<td><a href=\"javascript:;\" id=\"deleteSpecificationProduct\">删除</a></td></tr>";
					if(hasFlag){
						specificationGroupsTable.show().find("tr:eq(1)").replaceWith(group_tr_html);
						$("#specificationProductTable").show().empty().append(ftrHtml).append(trHtml);
					}else{
						specificationGroupsTable.hide();
						$("#specificationProductTable").hide();
					}
				}
			});
	}
	
	function propertyChange(value) {
		$.ajax({
			url : "${pageContext.request.contextPath  }/product/product_property/list?gourpId=" + value,
			type : "GET",
			dataType : "json",
			success : function(data) {
				var optHtml = "";
				for (var i = 0; i < data.length; i++) {
					var obj = data[i];
					optHtml += "<option value="+obj.id+">" + obj.name
							+ "</option>"
				}
				$("#productPropertyId").empty();
				$("#productPropertyId").append(optHtml);
			}
		});
	}
	function updateArea(){
		$("#update_sales_area_li").addClass("active").siblings().removeClass("active");
		var table_id = $("#update_sales_area_li").attr("id").replace("li","tab");
		for (var i = 0; i < tables_area.length; i++) {
			$("#" + tables_area[i]).attr("style", "display:none");
			if (tables_area[i] == table_id) {
				$("#" + table_id).attr("style","display:table;width:100%");
			}
		}
	}
	
	$("#btnSubmit").live("click", function() {
		var flag = true;
		if($("#productCategoryId").val()==""){
			$("#errorMsg").html("商品分类不能为空");
			flag = false;		
		}else if($("#name").val()==""){
			$("#errorMsg").html("名称不能为空");
			flag = false;		
		}else if($("#fullName").val()==""){
			$("#errorMsg").html("副标题不能为空");
			flag = false;		
		}else if($("#cost").val()==""){
			$("#errorMsg").html("成本价不能为空");
			flag = false;		
		}else if($("#marketPrice").val()==""){
			$("#errorMsg").html("市场价不能为空");
			flag = false;		
		}else if($("#brandId").val()==""){
			$("#errorMsg").html("品牌不能为空");
			flag = false;		
		}else if($("#supplierId").val()==""){
			$("#errorMsg").html("提供商不能为空");
			flag = false;		
		}else if($("#stock").val()==""){
			$("#errorMsg").html("库存不能为空");
			flag = false;		
		}else if($("#editor").val()==""){
			$("#errorMsg").html("商品介绍不能为空");
			flag = false;		
		}
		return flag;
	});
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/product/product/">商品列表</a></li>
		<li class="active"><a
			href="${ctx}/product/product/form?id=${product.id}">商品<shiro:hasPermission
					name="product:product:edit">${not empty product.id?'修改':'添加'}</shiro:hasPermission>
				<shiro:lacksPermission name="product:product:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="product"
		action="${ctx}/product/product/update" method="post"
		class="form-horizontal">
		<form:hidden path="id" />
		<sys:message content="${message}" />
		<span id="errorMsg" style="color: red;"></span>
		<ul class="nav nav-tabs" id="tab_header">
			<li class="active" id="base_info_li"><a>基本信息</a></li>
			<li id="product_introduce_li"><a>商品介绍</a></li>
			<li id="product_image_li"><a>商品图片</a></li>
			<li id="product_parameter_li"><a>商品参数</a></li>
			<li id="product_property_li"><a>商品属性</a></li>
			<li id="product_pecification_li"><a>商品规格</a></li>
			<li id="product_price_li"><a>商品价格</a></li>
			<li id="sales_area_li"><a>销售区域</a></li>
			
		</ul>
		<br />
		<div id="base_info_tab">
			<div class="control-group">
				<label class="control-label">商品分类：</label>
				<div class="controls">
					<sys:treeselect id="productCategory" name="productCategoryId"
						value="${product.productCategoryId}"
						labelName="productCategory.name"
						labelValue="${productCategory.name}" title="商品分类"
						url="/product/productCategory/treeData" module="product"
						notAllowSelectRoot="false" cssClass="input-small required" />
						<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">名称：</label>
				<div class="controls">
					<form:input path="name" htmlEscape="false" maxlength="32"
						class="input-xlarge required" />
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">副标题：</label>
				<div class="controls">
					<form:input path="fullName" htmlEscape="false" maxlength="256"
						class="input-xlarge required" />
						<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">商品编号：</label>
				<div class="controls">
					<form:input path="productNo" htmlEscape="false" maxlength="32"
						class="input-xlarge" />
				</div>
			</div>
			<%-- <div class="control-group">
				<label class="control-label">销售价格：</label>
				<div class="controls">
					<form:input path="price" htmlEscape="false"
						class="input-xlarge number required"/><font color="red">*</font>
				</div>
			</div> --%>
			<%-- <div class="control-group">
				<label class="control-label">是否会员价：</label>
				<div class="controls">
					<form:checkbox path="isMemberPrice" value="1"
						eclass="input-xlarge " />
				</div>
			</div> --%>
			<div class="control-group">
				<label class="control-label">成本价：</label>
				<div class="controls">
					<form:input path="cost" htmlEscape="false"
						class="input-xlarge number required" />
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">市场价：</label>
				<div class="controls">
					<form:input path="marketPrice" htmlEscape="false"
						class="input-xlarge number required" />
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">展示图片：</label>
				<div class="controls">
					<form:input path="image" id="url" htmlEscape="false"
						maxlength="256" class="ke-input-text required" />
					<input type="button" id="image_select_button" value="选择文件" /> <span class="help-inline"><font color="red">*</font> </span><font
						color="red">图片大小(330*330)</font>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">单位：</label>
				<div class="controls">
					<form:input path="unit" htmlEscape="false" maxlength="32"
						class="input-xlarge " />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">重量：</label>
				<div class="controls">
					<form:input path="weight" htmlEscape="false"
						class="input-xlarge  number" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">库存备注：</label>
				<div class="controls">
					<form:input path="stockMemo" htmlEscape="false" maxlength="32"
						class="input-xlarge " />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">赠送积分：</label>
				<div class="controls">
					<form:input path="point" htmlEscape="false" maxlength="11"
						class="input-xlarge  digits" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">品牌：</label>
				<div class="controls">
					<form:select path="brandId" class="input-xlarge required">
						<form:option value="" label="" />
						<form:options items="${brandList}" itemLabel="name" itemValue="id"
							htmlEscape="false" />
					</form:select>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">标签：</label>
				<div class="controls">
					<form:checkbox path="hotSell" htmlEscape="false" maxlength="1"
						value="1" class="input-xlarge " />
					热销
				</div>
				<div class="controls">
					<form:checkbox path="newest" htmlEscape="false" maxlength="1"
						value="1" class="input-xlarge " />
					最新
				</div>
				<div class="controls">
					<form:checkbox path="promotion" htmlEscape="false" maxlength="1"
						value="1" class="input-xlarge " />
					活动促销
				</div>
			</div>
			<!--  <div class="control-group">
			<label class="control-label">最新：</label>
			
		</div>
		<div class="control-group">
			<label class="control-label">活动促销：</label>
		</div>
		-->
			<div class="control-group">
				<label class="control-label">是否为赠品：</label>
				<div class="controls">
					<form:select path="isGift" class="input-xlarge ">
						<form:option value="" label="" />
						<form:options items="${fns:getDictList('is_gift')}"
							itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
				</div>
			</div>
			<%-- <div class="control-group">
				<label class="control-label">是否列出：</label>
				<div class="controls">
					<form:select path="isList" class="input-xlarge ">
						<form:option value="" label="" />
						<form:options items="${fns:getDictList('is_list')}"
							itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">PC是否上架：</label>
				<div class="controls">
					<form:select path="pcIsMarketable" class="input-xlarge ">
						<form:option value="" label="" />
						<form:options items="${fns:getDictList('is_marketable')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
				</div>
			</div>
				<div class="control-group">
				<label class="control-label">APP是否上架：</label>
				<div class="controls">
					<form:select path="appIsMarketable" class="input-xlarge ">
						<form:option value="" label="" />
						<form:options items="${fns:getDictList('is_marketable')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
				</div>
			</div>--%>
			<div class="control-group">
				<label class="control-label">微信是否上架：</label>
				<div class="controls">
					<form:select path="wxIsMarketable" class="input-xlarge ">
						<form:option value="" label="" />
						<form:options items="${fns:getDictList('is_marketable')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
				</div>
			</div>
			<%--
			<div class="control-group">
				<label class="control-label">APP首页是否显示：</label>
				<div class="controls">
					<form:select path="appHomeShow" class="input-xlarge ">
						<form:option value="" label="" />
						<form:options items="${fns:getDictList('mobile_home_display')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">微信首页是否显示：</label>
				<div class="controls">
					<form:select path="wxHomeShow" class="input-xlarge">
						<form:option value="" label="" />
						<form:options items="${fns:getDictList('mobile_home_display')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">是否置顶：</label>
				<div class="controls">
					<form:select path="isTop" class="input-xlarge ">
						<form:option value="" label="" />
						<form:options items="${fns:getDictList('is_top')}"
							itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
				</div>
			</div> --%>
			<div class="control-group">
				<label class="control-label">备注：</label>
				<div class="controls">
					<form:input path="memo" htmlEscape="false" maxlength="32"
						class="input-xlarge " />
				</div>
			</div>
			<!--div class="control-group">
			<label class="control-label">点击数：</label>
			<div class="controls">
				<form:input path="hits" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div -->
			<div class="control-group">
				<label class="control-label">搜索关键词：</label>
				<div class="controls">
					<form:input path="keyword" htmlEscape="false" maxlength="32"
						class="input-xlarge " />
				</div>
			</div>
			<!--div class="control-group">
			<label class="control-label">月点击数：</label>
			<div class="controls">
				<form:input path="monthHits" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div -->
			<!--div class="control-group">
			<label class="control-label">月点击数更新日期：</label>
			<div class="controls">
				<input name="monthHitsDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${product.monthHitsDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div-->
			<!-- div class="control-group">
			<label class="control-label">月销售量：</label>
			<div class="controls">
				<form:input path="monthSales" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div -->
			<!-- div class="control-group">
			<label class="control-label">月销售量更新日期：</label>
			<div class="controls">
				<input name="monthSalesDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${product.monthSalesDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div -->
			<!-- div class="control-group">
			<label class="control-label">销量：</label>
			<div class="controls">
				<form:input path="sales" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div -->
			<!-- <div class="control-group">
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
		-->
			<div class="control-group">
				<label class="control-label">页面标题：</label>
				<div class="controls">
					<form:input path="seoTitle" htmlEscape="false" maxlength="32"
						class="input-xlarge " />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">页面关键词：</label>
				<div class="controls">
					<form:input path="seoKeywords" htmlEscape="false" maxlength="32"
						class="input-xlarge " />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">页面描述：</label>
				<div class="controls">
					<form:input path="seoDescription" htmlEscape="false" maxlength="32"
						class="input-xlarge " />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">库存：</label>
				<div class="controls">
					<form:input path="stock" htmlEscape="false" maxlength="11"
						class="input-xlarge digits number required" /><span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="control-group">
			<label class="control-label">销量：</label>
			<div class="controls">
				<form:input path="sales" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
			<!--<div class="control-group">
			<label class="control-label">已分配库存：</label>
			<div class="controls">
				<form:input path="allocatedStock" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">总评分：</label>
			<div class="controls">
				<form:input path="totalScore" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
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
		-->
			<%-- <div class="control-group">
				<label class="control-label">商户号：</label>
				<div class="controls">
					<form:input path="merchantNo" htmlEscape="false" maxlength="32"
						class="input-xlarge " />
				</div>
			</div> --%>
			<div class="control-group">
				<label class="control-label">供应商：</label>
				<div class="controls">
<%-- 					<form:input path="supplier.companyName" htmlEscape="false" maxlength="11" --%>
<%-- 						class="input-xlarge digits number required" /><span class="help-inline"><font color="red">*</font> </span>  --%>
					<sys:treeselect id="supplier" name="supplierId"
						value="${product.supplierId}" labelName="supplier.companyName"
						labelValue="${supplier.companyName}" title="供应商"
						url="/supplier/supplier/treeData" module="product"
						notAllowSelectRoot="false"  disabled="disabled" cssClass="input-small required" /><span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">备注信息：</label>
				<div class="controls">
					<form:textarea path="remarks" htmlEscape="false" rows="4"
						maxlength="255" class="input-xxlarge " />
				</div>
			</div>
		</div>
		<table id="product_introduce_tab" style="display: none; width: 100%;">
			<tr>
				<td><form:textarea id="editor" path="introduction"
						class="editor" style="width: 100%;" htmlEscape="false" /></td>
			</tr>
		</table>

		<table id="product_image_tab" width="100%" style="display: none;">
			<thead>
				<tr>
					<td colspan="4"><a href="javascript:;" id="addProductImage"
						class="button">添加图片</a></td>
				</tr>
			</thead>
			<tr class="title">
				<td>文件</td>
				<td>标题</td>
				<td>排序</td>
				<td>操作</td>
			</tr>
			<c:forEach items="${product.productImageRefList }"
				var="productImageRef" varStatus="status">
				<tr>
					<td><input type="text" id="productImages_${status.index}_id"
						name="productImageRefList[${status.index}].filePath"
						class="productImageFile" value="${productImageRef.filePath }" />
						<input type="button" id="productImages_${status.index}_button"
						value="选择文件" /> <font
						color="red">图片大小(1680*600)</font></td>
					<td><input type="text"
						name="productImageRefList[${status.index}].title" class="text"
						maxlength="200" value="${productImageRef.title }" /></td>
					<td><input type="text"
						name="productImageRefList[${status.index}].orders"
						class="text productImageOrder" maxlength="9" style="width: 50px;"
						value="${productImageRef.orders }" /></td>
					<td><a href="javascript:;" class="deleteProductImage">删除</a></td>
				</tr>
			</c:forEach>
		</table>
		<div id="product_parameter_tab" style="display: none; width: 100%;">
			<c:forEach items="${product.parameterGroups }" var="parameterGroup"
				varStatus="groupStatus">
				<div class="control-group">
					<label style="font-weight: bold;" class="control-label">${parameterGroup.name}:</label>
				</div>
				<div>
					<c:forEach items="${parameterGroup.productParameterList }"
						var="productParameter" varStatus="status">
						<div class="control-group">
							<label class="control-label">${productParameter.name}</label>
							<div class="controls">
								<form:input path="" name="parameter_${productParameter.id}"
									value="${product.parameterValue[productParameter]}"
									htmlEscape="false" maxlength="32" class="input-xlarge " />
							</div>
						</div>
					</c:forEach>
				</div>
			</c:forEach>
		</div>
		<div id="product_property_tab" style="display: none; width: 100%;">
			<c:forEach items="${propertyGrop }" var="item">
				<div class="control-group">
					<label class="control-label">${item.name }：</label>
					<div class="controls">
						<form:select path="" name="property_${item.id}"
							class="input-xlarge">
							<form:option
								value="${product.propertyRefMap[item.id].propertyId }"
								label="${product.propertyRefMap[item.id].property.name }" />
							<form:options items="${item.productPropertyList}"
								itemLabel="name" itemValue="id" htmlEscape="false" />
						</form:select>
					</div>
				</div>
			</c:forEach>
		</div>
		<div id="product_pecification_tab" style="display: none; width: 100%;">
			<table style="width: 100%;" id="specificationGroupsTable">
				<tr>
					<th>请选择商品规格:</th>
				</tr>
				<tr>
					<td>
						<div id="specificationGroupSelect"
							class="specificationGroupSelect">
							<ul>
								<c:forEach items="${specificationGroups}"
									var="specificationGroup">
									<li><label> <c:choose>
												<c:when test="${specificationGroup.selected}">
													<input type="checkbox" name="specificationGroupIds"
														value="${specificationGroup.id}" checked="checked" />
												</c:when>
												<c:otherwise>
													<input type="checkbox" name="specificationGroupIds"
														value="${specificationGroup.id}" />
												</c:otherwise>
											</c:choose> ${specificationGroup.name} <c:if
												test="${specificationGroup.remarks!=null and specificationGroup.remarks!=''}">
												<span class="gray">[${specificationGroup.remarks}]</span>
											</c:if>
									</label></li>
								</c:forEach>
							</ul>
						</div>
					</td>
				</tr>
				<tr>
					<td><a href="javascript:;" id="addSpecificationProduct"
						class="button">增加规格商品</a></td>
				</tr>
			</table>
			<table id="specificationProductTable" style="width: 100%">
				<tr>
					<td width="60">&nbsp;</td>
					<c:forEach items="${specificationGroups}" var="specificationGroup">
						<c:choose>
							<c:when test="${specificationGroup.selected}">
								<td class="specification_${specificationGroup.id} hidden">${specificationGroup.name}
									<c:if
										test="${specificationGroup.remarks!=null and specificationGroup.remarks!=''}">
										<span class="gray">[${specificationGroup.remarks}]</span>
									</c:if> <input name="specificationGroup_${specificationGroup.id }"
									style="display: none;" value="" />
								</td>
							</c:when>
							<c:otherwise>
								<td class="specification_${specificationGroup.id} hidden"
									style="display: none;">${specificationGroup.name}<c:if
										test="${specificationGroup.remarks!=null and specificationGroup.remarks!=''}">
										<span class="gray">[${specificationGroup.remarks}]</span>
									</c:if> <input name="specificationGroup_${specificationGroup.id }"
									style="display: none;" value="" disabled />
								</td>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<td style="display: none;">操作</td>
				</tr>
			</table>
		</div>
		<!-- 商品价格 -->
		<table id="product_price_tab" width="100%" style="display: none;">
			<!-- <span id="errorMsg" style="color:red;"></span> -->
			<tr class="title">
				<td>会员等级</td>
				<td>售价（元）</td>
				<td>B端供货价（元）</td>
			</tr>
			<c:forEach items="${memRankList}" var="memRank" varStatus="status">
				<tr>
					<td><input type="text" value="${memRank.name}"
						style="width: 80px;" disabled="disabled" /></td>
					<td><input type="text"
						name="productPriceRefList[${status.index}].sellPrice"
						value="${memRank.productPriceRef.sellPrice}" /></td>
					<td><input type="text"
						name="productPriceRefList[${status.index}].bSupplyPrice"
						value="${memRank.productPriceRef.bSupplyPrice}" /></td>
					<input type="hidden"
						name="productPriceRefList[${status.index}].rankId"
						value="${memRank.id}" />
				</tr>
			</c:forEach>
		</table>
		<!-- 销售区域 -->
		<div id="sales_area_tab" style="display: none; width: 100%;">
			<ul class="nav nav-tabs" id="tab_area_header">
				<li class="active" id="update_sales_area_li"><a>编辑区域</a></li>
				<li id="select_sales_area_li"><a>查看区域</a></li>
			</ul>
			<div id="update_sales_area_tab" >
			<table id="treeTable"
				class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th></th>
						<th>区域名称</th>
					</tr>
				</thead>
				<tbody id="treeTableList"></tbody>
			</table>
			</div>
			<div id="select_sales_area_tab" style="display: none; width: 100%;">
			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th style="text-align: center; ">区域名称</th>
						<th style="text-align: center; ">操作</th>
					</tr>
				</thead>
				<tbody >
					<tr>
						<td style="text-align: center; padding-top: 50px; padding-bottom: 50px;">${areaName}</td>
						<td style="text-align: center; padding: 50px;"><a href="javascript:;" onclick="updateArea()"/>编辑</td>
					</tr>
				</tbody>
			</table>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="product:product:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit"
					value="保 存" />&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回"
				onclick="history.go(-1)" />
		</div>
	</form:form>

	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.code}}" pId="{{pid}}"  >
		<c:set var="areaId"  value="${areaLength}"/>
		<c:set var="id"  value="{{row.id}}"/>
			<td><input id="areaId_{{row.code}}" name="areaId"  type="checkbox" areaId="{{row.areaId}}" {{row.checked}} value="{{row.id}}{{row.type}}"  onclick="checkAll('{{row.parentIds}}',{{row.code}},this,{{row.type}})">
			</td>
			<td><a href="${ctx}/sys/area/form?id={{row.id}}">{{row.name}}</a></td>
		</tr>
	</script>
</body>
</html>