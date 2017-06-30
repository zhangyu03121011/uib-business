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
						Alert.disp_prompt("修改数量失败：" + result.msg);
					}
				}, function(){});
	}
}

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
					Alert.disp_prompt("修改数量失败：" + result.msg);
				}
			}, function(){});
	
}

function mult(num1,num2){
	return (Number(num1)*Number(num2)).toFixed(2);
}

/**
 * 初始化总价
 */
function initTotAmt(){
	$("#totalPrice").empty();
	var amount = 0 ;
	var c = 0;
	var inputList=document.getElementsByTagName("input");
	for(var i=0;i<inputList.length;i++){	
		if(inputList[i].type == "checkbox" && inputList[i].checked == true && inputList[i].id != "checkAll"&&inputList[i].value=="1" && inputList[i].id != undefined && inputList[i].id != 'undefined'){
			var itemId = inputList[i].id;
			var curPrice = $("#price_" + itemId).html().replace(/￥/g,'');
			var curCount = Number($("#count_" + itemId).val());
			var curtotalAmt = mult(curPrice,curCount);
			amount = (Number(curtotalAmt) + Number(amount)).toFixed(2);
			c += curCount;
		}
    }
	var appendHtml = "<font>总价：</font>￥" + amount;
	$("#totalPrice").append(appendHtml);
	$("#sel_count_p").html('去结算('+c+')');
	handlerCheckAll();
}

//全选
function checkAll()
{
	var obj=document.getElementsByTagName("input");
    if(document.getElementById("checkAll").checked==true){
        for(var i=0;i<obj.length;i++){
            obj[i].checked=true;
        }
    }else{
        for(var i=0;i<obj.length;i++){
            obj[i].checked=false;
        }
    }
    initTotAmt();
	    
}

function handlerCheckAll(){
	var obj=document.getElementsByTagName("input");
	var isAll = true;
	  for(var i=0;i<obj.length;i++){
		  if(obj[i].checked==false && ($("#pid_"+obj[i].id).val() != undefined && $("#pid_"+obj[i].id).val() != 'undefined')){
			  isAll = false;
			  break;
		  }
	  }
	  document.getElementById("checkAll").checked = isAll ? true : false;
}

function del(){
	var bool =window.confirm('是否删除商品？');	
	var inputList=document.getElementsByTagName("input");
	//var productIds = "";
	var cartItemIds ="";
	for(var i=0;i<inputList.length;i++){
		if(inputList[i].type == "checkbox" && inputList[i].checked == true && inputList[i].id != "checkAll"){
			if(inputList[i].id != 'undefined'){
				cartItemIds += inputList[i].id + ",";
				//productIds += $("#pid_"+inputList[i].id).val() +",";
        	}
		}
	}
	if (cartItemIds !=""&&bool){
		//AJAX.call(rootPath + "/wechat/cart/delete_by_Id?productIds=" + productIds +"&cartItemIds=" + cartItemIds, "post", "json", "", true, 
		AJAX.call(rootPath + "/wechat/cart/deleteCartById?cartItemIds=" + cartItemIds, "post", "json", "", true, 
				  function(result){
					if(result.status){
//						Alert.disp_prompt("删除成功");
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

$(document).ready(function(){
	var divshow = $("#cartDetail");
	var emptyUrl = "cart-empty.html";
	//根据用户名查询购物车
	AJAX.call(rootPath + "/wechat/cart/queryCart", "post", "json", "", true, function(data){
		if (isNull(data)){
			window.location.href = emptyUrl;
		}else{
			var appendHtml = "";
			for(var index in data){
				$("#cartId").val(data[index].cartId);
				
				var isMarket="";
				var isStock= "";
				var product ="";
				var $flag = 0;
				 if(data[index].wxIsMarketable=="0"){
					 isMarket="<a href='products.html?id="+ data[index].productId + "'><span>商品已下架</span></a>" ;
					 product="<div class='shelf-commodity'></div>";
					 $flag = 1;
				  }else{
					  if(data[index].stock==0){
						 isStock="<a href='products.html?id="+ data[index].productId + "'><span>商品无库存</span></a>" ;
						// product="<div class='shelf-commodity'></div>";
						 $flag = 1;
					  }
				  }
				 var $temp = "";
				 if($flag == 0){
					 $temp = "<input type='checkbox' value='"+data[index].wxIsMarketable+"' id='" + data[index].cartItemId + "' checked='checked' onclick='initTotAmt()'/>"
					 			+ "<label for='" + data[index].cartItemId + "'></label>"
					 			+ "<input type='hidden' value='" + data[index].productId + "' id='pid_" + data[index].cartItemId + "'/>";
				 }
				appendHtml= "<li id='" + data[index].cartItemId+data[index].productId+ "'>" 
						  + 	"<div class='wbox'>"
						  +			"<div class='checkboxFive'>"
						  +	$temp
						 // +				"<input type='checkbox' value='"+data[index].wxIsMarketable+"' id='" + data[index].cartItemId + "' checked='checked' onclick='initTotAmt()'/>"
						//  +				"<label for='" + data[index].cartItemId + "'></label>"
						//  +				"<input type='hidden' value='" + data[index].productId + "' id='pid_" + data[index].cartItemId + "'/>"   
						  +			"</div>"
						  +			"&nbsp;&nbsp;&nbsp;&nbsp;"
						  +			"<div class='img'><a href='products.html?id="+ data[index].productId + "'><img src='"+ data[index].image +"'/></a>"+isMarket+isStock+"</div>"
						  +			"<div class='wbox-flex'>"
						  +				"<a href='products.html?id="+ data[index].productId +"'>"
						  +					"<p class='name'>"+ data[index].productName +"</p>"
						  +				"</a>"
						  +				"<div class='price'> <span class='fl' id='price_" + data[index].cartItemId + "'>" + formatPrice(data[index].price) +"</span><span class='del'>" + formatPrice(data[index].marketPrice) +"</span></div>"
						  +                " <div class='operating-box'>"
						  +					"<span class='fl'>"
						  +						"<div class='sn-count'>"
						  +							"<a href='javascript:void(0)' class='count-min cover' onclick='countMin(this)'></a>"
						  +							"<input type='text' class='input-reset count-num' maxlength='2' value='" + data[index].quantity + "' id='count_" + data[index].cartItemId + "'>"
						  +							"<a href='javascript:void(0)' class='count-add' onclick='countAdd(this)'></a>  "
						  +						"</div>"
						  +					"</span>"
						  +					" <span class='del-btn'><a href='javascript:void(0)' onclick=delProduct('"+data[index].cartItemId+"','"+data[index].productId+"')><img src='images/del.png'/></a></span>"
						  +				"</div>"
						  +			"</div>"
						  +		"</div>"
						  + product
						  +	"</li>";						 
				divshow.append(appendHtml);
			};
			initTotAmt();
		}
	}, function(){});
});


function delProduct(id,productid){
	var bool =window.confirm('是否删除商品？');	
	if(isNotEmpty(id)&&bool) {
		//AJAX.call(rootPath+"/wechat/cart/delete_by_Id?cartItemIds="+id+"&productIds="+productid, "get", "json", "", true, function(result){
		AJAX.call(rootPath+"/wechat/cart/deleteCartById?cartItemIds="+id, "get", "json", "", true, function(result){
			if (result && result.code == null) {
				Alert.disp_prompt("删除成功");				
				$("#"+id+productid).remove();
				initTotAmt();
			}else{
				Alert.disp_prompt("删除失败");
			}
		}, function(){});
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
	var obj=document.getElementsByTagName("input");
    for(var i=0;i<obj.length;i++){
        if(obj[i].checked&&obj[i].value=="1"){
        	if(obj[i].id != 'undefined'){
        		if($("#pid_"+obj[i].id).val() == undefined || $("#pid_"+obj[i].id).val() == 'undefined'){
        			continue;
        		}
        		pid += $("#pid_"+obj[i].id).val() +",";
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
		window.location.href = "order-confirm.html?cartId="+cartId+"&isInvoice=0&paymentMethod=1&pid="+pid;
	}
}

