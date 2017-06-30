/**
 * 购物车列表
 */
//var pageIndex = 0;
//var pageSize = 5;
//var isLoad = true;//是否需要加载(到最后一页时,禁止滚动加载)
//var isRequest = false;//是否正在请求(正在请求时,禁止滚动加载)
var currentQuantity = 0;
$(document).ready(function(){
	//加载列表
	loadData();
	
	$("#edit_cart").toggle(function() {
		$("#edit_cart").html("完成");
		$("#submit_text").html("删除商品");
		$("#submit_text").attr("onclick","del()");
		$(".isNotMarketable").attr("onclick","");
		$("#cart_price_sum").hide();
		
	},function() {
		$("#edit_cart").html("编辑");
		$("#submit_text").html("去结算");
		$("#submit_text").attr("onclick","toSettle()");
		$(".isNotMarketable").attr("onclick","this.checked=!this.checked");
		$("#cart_price_sum").show();
		var obj=$("[type=checkbox]").attr("checked",false);
	});
	
	//滚动分页
//	 $(window).scroll(function(){
//	 	var scrollTop = $(this).scrollTop();
//	 	var scrollHeight = $(document).height();
//	 	var windowHeight = $(this).height();
//	 	if(scrollTop/(scrollHeight-windowHeight) >= 0.95 && isRequest == false && isLoad == true){	 		
//	 		$(".scrimg").show();
//	 		pageIndex += pageSize;
//	 		loadData();
//	 	}
//	 });
	
});

/**
 * 加载购物车列表
 */
function loadData() {
	$("#checkAll").attr("checked",false);
	var divshow = $("#cartDetail");
	//根据用户名查询购物车
	AJAX.call(rootPath + "/wechat/cart/queryCart", "post", "json", "", true, function(data){
		if (isNull(data)){
				$("#cart-wrap").hide();
				$(".flex-row").hide();
				$("#edit_cart").hide();
				$("#cart_empty").show();
		}else{
			var productListArr = [];
			$.each(data,function(i,item) {
				var $flag = 0;
				//拼接商品规格信息
				var specificationHtml = "";
				if(isNotEmpty(item.productSpecificationList)) {
					$.each(item.productSpecificationList,function(i,item) {
						var groupName = item.specificationGroup.name;
						var specificationName = item.name;
						specificationHtml += groupName + " : " + specificationName+"   ";
					});
				}
				//商品已下架或者库存不足
				var contentHtml = "";
				var isMarketable = item.wxIsMarketable != "1";
				var wxIsPriceChanged = item.wxIsPriceChanged;
				var isStock = item.stock <= 0;
				if(isMarketable){
					contentHtml = "对不起，宝贝已下架";
				} else if(isStock) {
					contentHtml = "对不起，宝贝已无库存";
				} else if(wxIsPriceChanged) {
					contentHtml = "对不起，宝贝已失效";
				} 
				if(isMarketable || isStock || wxIsPriceChanged){
					productListArr.push('<div class="flex-row cart">');
					productListArr.push(	'<div class="app-checkbox rdo">');
					productListArr.push(		'<input name="checkbox" class="cartItemCheckbox isNotMarketable" type="checkbox" id="' + item.cartItemId + '" onclick= "this.checked=!this.checked">');
					productListArr.push(		'<label class="iconfont " for="set-default"></label>');
					productListArr.push(	'</div>');
					productListArr.push(	'<div class="cart-img">');
					productListArr.push(		'<img src="'+item.image+'">');
					productListArr.push(	'</div>');
					productListArr.push(	'<div class="flex-item detail has-product-toggle">');
					productListArr.push(		'<div class="has-product">');
					productListArr.push(			'<div class="sell-out">');
					productListArr.push(				'<p class="title">'+item.productName+'</p>');
					productListArr.push(				'<p class="sell-out-title">'+contentHtml+'</p>');
					productListArr.push(			'</div>');
					productListArr.push(		'</div>');
					productListArr.push(	'</div>');
					productListArr.push('</div>');
					$flag = 1;
				  }
				
				 if($flag == 0){
					 	productListArr.push('<div class="flex-row cart">');
						productListArr.push(	'<div class="app-checkbox rdo">');
						productListArr.push(		'<input name="checkbox" class="cartItemCheckbox" type="checkbox" value="'+item.wxIsMarketable+'" id="' + item.cartItemId + '" cartId="'+item.cartId+'"  onclick="initTotAmt()">');
						productListArr.push(		'<label class="iconfont " for="set-default"></label>');
						productListArr.push(		'<input type="hidden" value="' + item.productId + '" id="pid_' + item.cartItemId + '"/>');
						productListArr.push(	'</div>');
						productListArr.push(	'<div class="cart-img" onclick=toProductDetail("'+item.productId+'")>');
						productListArr.push(		'<img src="'+item.image+'">');
						productListArr.push(	'</div>');
						productListArr.push(	'<div class="flex-item detail has-product-toggle">');
					 	//商品信息
					 	productListArr.push(		'<div class="has-product">');
					 	productListArr.push(			'<div class="title" onclick=toProductDetail("'+item.productId+'")>'+item.productName+'</div>');
					 	productListArr.push(				'<p class="capacity" onclick=toProductDetail("'+item.productId+'")>'+specificationHtml+'</p>');
						productListArr.push(				'<div class="price">');
						productListArr.push(					'<span id="price_'+item.cartItemId+'" class="xianjia">'+formatPrice(item.price)+'</span>');
						productListArr.push(					'<span class="yuanjia">'+formatPrice(item.marketPrice)+'</span>');
						productListArr.push(					'<div class="uib-numbox calc" data-numbox-min="1">');
						productListArr.push(						'<button class="uib-btn uib-btn-numbox-minus" type="button" onclick="countMin(this)">-</button>');
						productListArr.push(						'<input class="uib-input-numbox" type="number" value="'+item.quantity+'" id="count_'+ item.cartItemId+'" onclick="getQuantity(this)" onchange="productCountChange(this)" />');
						productListArr.push(						'<button class="uib-btn uib-btn-numbox-plus" type="button" onclick="countAdd(this)">+</button>');
						productListArr.push(					'</div>');
						productListArr.push(				'</div>');
						productListArr.push(			'</div>');
						productListArr.push(		'</div>');
						productListArr.push(	'</div>');
						productListArr.push('</div>');
				 }
			});
//			if(pageIndex == 0){
//				$("#cart-list").empty();
// 			}
			$("#cart-list").append(productListArr.join(""));
//			isRequest = false;
//			if(data.length < pageSize){
// 				isLoad = false;
// 				$(".scrimg .load_tip").text("没有更多数据");
// 			}
		}
	})
}

function getQuantity(obj) {
	currentQuantity = $(obj).val();
}

function productCountChange(obj) {
	var count = $(obj).val();
	if(count > 1){
		var quantity = Number(count);
		//去除count_
		var cartItemId = $(obj).attr('id').substr(6);
		AJAX.call(rootPath + "/wechat/cart/update_count?cartItemId=" + cartItemId +"&quantity=" + quantity, "post", "json", "", true, 
				  function(result){
					if(result.status){
						$(obj).val(quantity);
						initTotAmt();
					}else{
						Alert.disp_prompt(result.msg);
						$(obj).val(currentQuantity);
					}
				}, function(){});
	}
}

/**
 * 减商品数量
 * @param obj
 */
function countMin(obj){
	var input = $(obj).next();
	var count = input.val();
	if(count <= 2){
		$(obj).removeClass("count-min");
		$(obj).removeClass("count-min cover");
		$(obj).addClass("count-min cover");
	}else{
		$(obj).removeClass("count-min cover");
		$(obj).removeClass("count-min");
		$(obj).addClass("count-min");
	}
	
	if(count > 1){
		var quantity = Number(count)-Number(1);
		//去除count_
		var cartItemId = $(input).attr('id').substr(6);
		AJAX.call(rootPath + "/wechat/cart/update_count?cartItemId=" + cartItemId +"&quantity=" + quantity, "post", "json", "", true, 
				  function(result){
					if(result.status){
						input.val(quantity);
						initTotAmt();
					}else{
						Alert.disp_prompt(result.msg);
					}
				}, function(){});
	}
}

/**
 * 增加商品数量
 * @param obj
 */
function countAdd(obj){
	var input = $(obj).prev();
	var countMin = $(input).prev();
	var count = input.val();
	if(count >= 99){
		input.val(99);
		Alert.disp_prompt("单件商品不能超过99件");
		return;
	}
	var quantity = Number(count)+Number(1);
	//去除count_
	var cartItemId = $(input).attr('id').substr(6);
	
	AJAX.call(rootPath + "/wechat/cart/update_count?cartItemId=" + cartItemId +"&quantity=" + quantity, "post", "json", "", true, 
		  function(result){
			if(result.status){
				$(countMin).removeClass("count-min cover");
				$(countMin).removeClass("count-min");
				$(countMin).addClass("count-min");
				input.val(quantity);
				initTotAmt();
			}else{
				Alert.disp_prompt(result.msg);
			}
		}, function(){});
}

/**
 * 总价
 * @param num1
 * @param num2
 * @returns
 */
function mult(num1,num2){
	return (Number(num1)*Number(num2)).toFixed(2);
}

/**
 * 初始化总价
 */
function initTotAmt(){
	$("#total_price").empty();
	var amount = 0 ;
	var c = 0;
	var inputList=$(".cartItemCheckbox[name=checkbox]");
	for(var i=0;i<inputList.length;i++){	
		if(inputList[i].type == "checkbox" && inputList[i].checked == true){
			var itemId = inputList[i].id;
			var curPrice = $("#price_" + itemId).html().replace(/¥/g,'');
			var curCount = Number($("#count_" + itemId).val());
			var curtotalAmt = mult(curPrice,curCount);
			amount = (Number(curtotalAmt) + Number(amount)).toFixed(2);
			c += curCount;
		}
    }
	$("#total_price").html(formatPrice(amount));
	$("#total_sum").html(c);
	handlerCheckAll();
}

/**
 * 全部选择购物车
 */
function checkAll() {
	var obj=$(".cartItemCheckbox[name=checkbox][onclick!='this.checked=!this.checked']");
    if(document.getElementById("checkAll").checked==true){
        for(var i=0;i<obj.length;i++){
            obj[i].checked=true;
        }
    }else{
    	$("#checkAll").attr("checked",false);
        for(var i=0;i<obj.length;i++){
            obj[i].checked=false;
        }
    }
    initTotAmt();
}

/**
 * 检查是否已全选
 */
function handlerCheckAll(){
	var obj=$(".cartItemCheckbox[name=checkbox]");
	var isAll = true;
	  for(var i=0;i<obj.length;i++){
		  if(obj[i].checked==false && ($("#pid_"+obj[i].id).val() != undefined && $("#pid_"+obj[i].id).val() != 'undefined')){
			  isAll = false;
			  break;
		  }
	  }
	  document.getElementById("checkAll").checked = isAll ? true : false;
}

/**
 * 删除购物车商品
 */
function del(){
	var bool =window.confirm('是否删除商品？');	
	var inputList=$(".cartItemCheckbox[name=checkbox]");
	var cartItemIds ="";
	for(var i=0;i<inputList.length;i++){
		if(inputList[i].type == "checkbox" && inputList[i].checked == true && inputList[i].id != "checkAll"){
			if(inputList[i].id != 'undefined'){
				cartItemIds += inputList[i].id + ",";
        	}
		}
	}
	if (cartItemIds !=""&&bool){
		AJAX.call(rootPath + "/wechat/cart/deleteCartById?cartItemIds=" + cartItemIds, "post", "json", "", true, 
				  function(result){
					if(result.status){
						location.reload(true);
					}else{
						Alert.disp_prompt("删除失败：" + result.msg);
					}
				}, function(){});
	}else{
		if(bool)
			{
			Alert.disp_prompt("请选择商品");
			}
	}
}

/***
 * 去结算 
 * 1.验证账号是否已绑定手机号
 * 2.验证账号是否已实名认证
 * 3.是否有收货地址
 */
function toSettle(){
	var cartId = $("#cartId").val();
	var isOk = true;
	var msg = "";
	var pid = "";
	var obj=$(".cartItemCheckbox[name=checkbox]");
	var cartId = "";
    for(var i=0;i<obj.length;i++){
        if(obj[i].checked&&obj[i].value=="1"){
        	if(obj[i].id != 'undefined'){
        		if($("#pid_"+obj[i].id).val() == undefined || $("#pid_"+obj[i].id).val() == 'undefined'){
        			continue;
        		}
        		pid += $("#pid_"+obj[i].id).val() +",";
        		cartId = obj[i].cartId;
        	}
        }
    }
    if(pid != ''){
    	pid = pid.substring(0, pid.length - 1);
    }else{
    	Alert.disp_prompt("请选择商品进行结算");
    	return ;
    }
	if(!isOk){
		Alert.disp_prompt(msg);
		return ;
	}else{
		window.location.href = "cart-submit.html?cartId="+obj.eq(0).attr("cartId")+"&isInvoice=0&paymentMethod=1&pid="+pid;
	}
}

/**
 * 跳转到商品详情页面
 */
function toProductDetail(productId){
	window.location.href = "product-detail.html?id="+productId;
}

