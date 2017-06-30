var notEnd0 = true;
var notEnd1 = true;
var notEnd2 = true;
var isApend = true;
var down = true;
var orderparam="";
var isTop = false;

/**
 * 加入购物车
 */
function addCart(productId){
	$.get(rootPath + "/wechat/user/ticket/state/",{},function(data){
		if(data == '0'){
			window.location.href="login.html?locationParm=jump";
			return;
		}else{
			if(isNotEmpty(productId)){
				AJAX.call( rootPath + "/wechat/cart/addCart?productId=" + productId +"&quantity=1", 
						  "post", "json", "", true, 
						  function(result){
								if(result.status){
									Alert.disp_prompt("添加成功");
								}else{
									Alert.disp_prompt("添加失败：" + result.msg);
								}
					      }, function(){});
			}
		}
	});
}

/**
 * 置顶功能
 */

function goTop(){
	isTop = true;
	$('html,body').animate({scrollTop:0},1000);//回到顶端
	$('#returnTop').empty();
}

function showTop(){
	var appendTop= "<div class='quick-top' id ='quickTop'>"
				 +		"<a href='#' onclick='goTop()'>"
				 +			"<i></i>"
				 +		"</a>"
			 	 +	"</div>";
	$("#returnTop").append(appendTop);
}

function checkTop(){
	var viewHeight = $(window).height();
	var scrollHeight = $(document).scrollTop();
    if(scrollHeight <= viewHeight){
	   $("#returnTop").hide();
    }else{
	   $("#returnTop").show();
   }
}

/**
 * 查询商品
 * @param url
 * @param divshow
 */
function getProduct(url, divshow){
	
	AJAX.call( url, "post", "json", "", true, function(result){
		var data = result.data.products;
		var appendHtml;
		//删除等待图片
		$("li").remove(".scrimg");
		isApend = true;
		$("title").text(result.data.name);
		//当查询结果为空时，说明没有下一页了，设置结尾标识
		if(isNull(data)){
			var endli = "<li class='endli'><p calss='name' align='center'>没有更多数据</p></li>";
			if($("#menu-index").val() == 0 && notEnd0){
				notEnd0 = false;
				$("#all-product-ul").append(endli);
			};
			
			if($("#menu-index").val() == 1 && notEnd1){
				notEnd1 = false;
				$("#price-ul").append(endli);
			};
			if($("#menu-index").val() == 2 && notEnd2){
				notEnd2 = false;
				$("#sales-ul").append(endli);
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
				"<span class='fl'>" + formatPrice(data[index].price) + "</span>"+
				"<span class='del'>" + formatPrice(data[index].marketPrice) + "</span>";
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

function changeArrow(target,selectVar){
	var firstVar = "#first" + selectVar;
	if($(firstVar).val() == "true"){
		$(firstVar).val("false");
		$(target).addClass("arrow-down");
		down = true;
		
	}else{
		if($(target).attr("class")=="undefined"){
			$(target).addClass("arrow-down");
			down = true;
		}else{
			if($(target).is(".arrow-down")){
				$(target).removeClass("arrow-down");
				$(target).addClass("arrow-up");
				down = false;
			}else{
				$(target).removeClass("arrow-up");
				$(target).addClass("arrow-down");
				down = true;
			}
		}
		
	}
}


$(document).ready(function(){
	var categoryId = URL_PARAM.getParam("productCategoryId");
	var level = URL_PARAM.getParam("level");
	$(".tab-bar-item").click(function(){
		$(".tab-bar-item").removeClass("hover");
		$(this).addClass("hover");
		$("#tagContent .tab-group").hide();
		$("#tagContent .tab-group").eq($(this).index()).show();
		
		var lastIndex = $("#menu-index").val();
		var curIndex = $(this).index();
		$("#menu-index").val($(this).index());
		
		if(curIndex == 1 && (curIndex == lastIndex || $("#firstPriceSelect").val() == "true")){
			changeArrow("#price-href","PriceSelect");
			$("#price-ul").empty();
			$("#price-page").val(1);
			notEnd1 = true;
			orderparam = 'price';
			//getProduct(rootPath + "/wechat/product/findByOrderAndPage?orderparam=" + orderparam + "&page=1&down="+down,$("#price-ul"));
			getProduct(rootPath + "/wechat/product/findProductListCategoryAndPage?orderparam=" + orderparam + "&page=1&down="+down+"&categoryId="+categoryId+"&level="+level,$("#price-ul"));
		}
		
		if(curIndex == 2 && (curIndex == lastIndex || $("#firstSalesSelect").val() == "true")){
			changeArrow("#sales-href","SalesSelect");
			$("#sales-ul").empty();
			$("#sales-page").val(1);
			notEnd2 = true;
			orderparam = 'sales';
			//getProduct(rootPath + "/wechat/product/findByOrderAndPage?orderparam=" + orderparam + "&page=1&down="+down,$("#sales-ul"));
			getProduct(rootPath + "/wechat/product/findProductListCategoryAndPage?orderparam=" + orderparam + "&page=1&down="+down+"&categoryId="+categoryId+"&level="+level,$("#sales-ul"));
		}
		
	});
	
	
	var orderparam="date";
	//加载最新
	getProduct(rootPath + "/wechat/product/findProductListCategoryAndPage?orderparam=" + orderparam + "&page=1&down=true"+"&categoryId="+categoryId+"&level="+level,$("#all-product-ul"));

	$(document).on("scrollstart",function(){
		if(isTop){
			isTop = false;
		}else{
			var scrollImg = "<li class='scrimg'><p calss='name' align='center'>正在努力加载数据...</p></li>";
			if($("#menu-index").val() == 0 && notEnd0){
				var page = Number($("#all-product-page").val()) + 1;
				$("#all-product-page").val(page);
				if(isApend){
					$("#all-product-ul").append(scrollImg);
					isApend = false;
				}
				//分页加载商品
				orderparam="date";
				//加载最新
				getProduct(rootPath + "/wechat/product/findProductListCategoryAndPage?orderparam=" + orderparam + "&page="+page+"&down="+down+"&categoryId="+categoryId+"&level="+level,$("#all-product-ul"));
			}
	      	if($("#menu-index").val() == 1 && notEnd1){
	      		var page = Number($("#price-page").val()) + 1;
	  			$("#price-page").val(page);
	  			if(isApend){
	  				$("#price-ul").append(scrollImg);
	  				isApend =  false;
	  			}
	  			orderparam="price";
	  			//加载价格
	  			getProduct(rootPath + "/wechat/product/findProductListCategoryAndPage?orderparam=" + orderparam + "&page="+page+"&down="+down+"&categoryId="+categoryId+"&level="+level,$("#price-ul"));
	      	}
	      	if($("#menu-index").val() == 2 && notEnd2){
	      		var page = Number($("#sales-page").val()) + 1;
	  			$("#sales-page").val(page);
	  			if(isApend){
	  				$("#sales-ul").append(scrollImg);
	  				isApend = false;
	  			}
	  			orderparam="sales";
	  			//加载销量
	  			getProduct(rootPath + "/wechat/product/findProductListCategoryAndPage?orderparam=" + orderparam + "&page="+page+"&down="+down+"&categoryId="+categoryId+"&level="+level,$("#sales-ul"));
	      	}
	      	//判断这个标签下面是否有这个div
			  if($("#returnTop").has("div").length < 1){
				  showTop();
			  }
			  checkTop();
		}
		
	});
});