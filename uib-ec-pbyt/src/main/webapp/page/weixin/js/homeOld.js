var notEnd0 = true;
var notEnd1 = true;
var notEnd2 = true;
var notEnd3 = true;
var isApend = true;

/**
 * 加入购物车
 */
function addCart(productId){
	$.ajax({  
        url : rootPath +"/wechat/member/user/getMemberByUserName",  
        async : false, 
        type : "POST",  
        dataType : "json",  
        success : function(result) {
        	if(result != null){
        		if(result.phone == null || result.phone == ''){
        			Alert.disp_prompt("请先进行手机号绑定");
        		}else{
        			var stock = $("#stock_" + productId).val();
        			if(stock >= 1){
        				AJAX.call( rootPath + "/wechat/cart/addCart?productId=" + productId +"&quantity=1", 
          					  "post", "json", "", true, 
          					  function(result){
          							if(result.status){
          								$("#stock_" + productId).val(stock - 1);
          								Alert.disp_prompt("添加成功");
          							}else{
          								Alert.disp_prompt("添加失败：" + result.msg);
          							}
          				      }, function(){});
        			}else{
        				$("#href_" + productId).removeAttr("onclick");
        				$("#href_" + productId).removeClass("fr");
    					$("#href_" + productId).addClass("fr unav");
        				Alert.disp_prompt("库存不足");
        			}
        		}
        	}else{
    			Alert.disp_prompt("账号不存在");
        	}
        }  
    });
	
}

/**
 * 查询商品
 * @param url
 * @param divshow
 */
function getProduct(url, divshow){
	
	AJAX.call( url, "post", "json", "", true, function(result){
		var data = result.data;
		var appendHtml;
		//删除等待图片
		$("li").remove(".scrimg");
		isApend = true;
		//当查询结果为空时，说明没有下一页了，设置结尾标识
		if(isNull(data)){
			var endli = "<li class='endli'><p calss='name' align='center'>没有更多数据</p></li>";
			if($("#menu-index").val() == 0 && notEnd0){
				notEnd0 = false;
				$("#all-product-ul").append(endli);
			};
			
			if($("#menu-index").val() == 1 && notEnd1){
				notEnd1 = false;
				$("#cosmetics-ul").append(endli);
			};
			if($("#menu-index").val() == 2 && notEnd2){
				notEnd2 = false;
				$("#household-ul").append(endli);
			};
			if($("#menu-index").val() == 3 && notEnd3){
				notEnd3 = false;
				$("#merchandise-ul").append(endli);
			};
		}else{	
			for(var index in data){
				appendHtml= "<li>" +
				"<div class='wbox'>" +
				"<div class='img'><a href='products.html?id="+ data[index].id + "' data-ajax='false'><img src='"+ data[index].image +"'/></a></div>" +
				"<div class='wbox-flex'>" +
				"<a href='products.html?id=" + data[index].id +"' data-ajax='false'>" +
				"<p class='name'>" + data[index].fullName +"</p>" +	
				"</a>" +	
				"<div class='price'>" +
				"<span class='fl'>￥" + data[index].price + "</span>";
				if( data[index].stock > 0){
					appendHtml = appendHtml + "<span class='fr'><input id='stock_"+ data[index].id +"' value='"+ data[index].stock +"' type='hidden'/><a href='javascript:void(0)' onclick='addCart(\""+data[index].id+"\")' id='href_"+data[index].id+"'><em></em></a></span>";
				}else{
					appendHtml = appendHtml + "<span class='fr unav'><a href='javascript:void(0)'><em></em></a></span>" ;
				}
				
				appendHtml = appendHtml + "</div>" + "</div>" + "</div>" +	"</li>"; 
				
				divshow.append(appendHtml);
				
			};
		};
		
	}, function(){});
}

$(document).ready(function(){
	
	$(".tab-bar-item").click(function(){
		$(".tab-bar-item").removeClass("hover");
		$(this).addClass("hover");
		$("#tagContent .tab-group").hide();
		$("#tagContent .tab-group").eq($(this).index()).show();
		$("#menu-index").val($(this).index());
	});
	
	var categoryId = "bad37120c9d04d769176b2589d806078";
	
	//加载所有商品
	getProduct(rootPath + "/wechat/product/findListByPage?page=1",$("#all-product-ul"));
//	//加载化妆品
	getProduct(rootPath + "/wechat/product/findByCategoryAndPage?categoryId=" + categoryId + "&page=1",$("#cosmetics-ul"));
//	//加载家居
	getProduct(rootPath + "/wechat/product/findByCategoryAndPage?categoryId=" + categoryId + "&page=1",$("#household-ul"));
//	//加载百货
	getProduct(rootPath + "/wechat/product/findByCategoryAndPage?categoryId=" + categoryId + "&page=1",$("#merchandise-ul"));
	
	
	$(document).on("scrollstart",function(){
	  var scrollImg = "<li class='scrimg'><p calss='name' align='center'>正在努力加载数据...</p></li>";
//      if($(window).scrollTop()>=$(document).height()-$(window).height()){ 
			//此处是滚动条到底部时候触发的事件，在这里写要加载的数据，或者是拉动滚动条的操作
      	if($("#menu-index").val() == 0 && notEnd0){
      		var page = Number($("#all-product-page").val()) + 1;
  			$("#all-product-page").val(page);
  			if(isApend){
  				$("#all-product-ul").append(scrollImg);
  				isApend = false;
  			}
  			//分页加载商品
  			getProduct(rootPath + "/wechat/product/findListByPage?page=" + page,$("#all-product-ul"));
      	}
      	if($("#menu-index").val() == 1 && notEnd1){
      		var page = Number($("#cosmetics-page").val()) + 1;
  			$("#cosmetics-page").val(page);
  			if(isApend){
  				$("#cosmetics-ul").append(scrollImg);
  				isApend =  false;
  			}
  			getProduct(rootPath + "/wechat/product/findByCategoryAndPage?categoryId=" + categoryId + "&page=" + page,$("#cosmetics-ul"));
      	}
      	if($("#menu-index").val() == 2 && notEnd2){
      		var page = Number($("#household-page").val()) + 1;
  			$("#household-page").val(page);
  			if(isApend){
  				$("#household-ul").append(scrollImg);
  				isApend = false;
  			}
  			getProduct(rootPath + "/wechat/product/findByCategoryAndPage?categoryId=" + categoryId + "&page=" + page,$("#household-ul"));
      	}
      	if($("#menu-index").val() == 3 && notEnd3){
      		var page = Number($("#merchandise-page").val()) + 1;
  			$("#merchandise-page").val(page);
  			if(isApend){
  				$("#merchandise-ul").append(scrollImg);
  				isApend = false;
  			}
  			getProduct(rootPath + "/wechat/product/findByCategoryAndPage?categoryId=" + categoryId + "&page=" + page,$("#merchandise-ul"));
      	}
//      }
	});
});