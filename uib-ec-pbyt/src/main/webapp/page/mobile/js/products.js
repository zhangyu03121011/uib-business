var productId="";
var specHtml = "";

/*
 * 已注册用户验证
 */
function userVerify(){
	location.href = "/page/weixin/login.html";
}

/*
 * 注册后立即购买
 */
function goShopping(){
	location.href = "/page/weixin/registered.html";
}

function initData(){
	
	AJAX.call(rootPath +"/wechat/product/getProductDetail", "post", "json", "productId=" + productId, true, function(result){
		if(isNotEmpty(result)){
			var appendHtml = "";
			var price;
			var marketPrice;
			var product = result.product;
			price = formatPrice(product.price);
			marketPrice = formatPrice(product.marketPrice);
			var imageDomain = result.imageDomain;
			var specList = result.specList;
			var imageList = null;
			var productImg = "";
			if(isNotEmpty(product)){
				imageList = product.productImageRefList;
				productImg = imageDomain + product.image;
			}
			
			 $("#swiperWrapper").empty();
			 $("#productName").empty();
			 $("#price").empty();
			 $("#specName").empty();
			 $("#btnList").empty();
			
			//加载轮播图
			if(isNotEmpty(productImg)){
				appendHtml = "<div class='swiper-slide'>"
			 		+ 	"<img src='"+ productImg + "'/></div>";
			}
			if(isNotEmpty(imageList)){
				 $.each(imageList, function(n, value) {
					 appendHtml += "<div class='swiper-slide'>"
						 		+ 	"<img src='"+ value.source + "'/></div>";
				 }); 
				 
			}
			if(isNotEmpty(appendHtml)){
				$("#swiperWrapper").append(appendHtml);
			}
			
			var swiper =  new Swiper ('.swiper-container', {
				pagination: '.swiper-pagination',
				paginationClickable: true,
				spaceBetween: 30,
			});
			
			if(isNotEmpty(product)){
				 //加载商品名称和描述
				 appendHtml = "<a href='productInfo.html?productId=" + productId + "'>"
					 	    + 	"<h2>" + product.fullName + "</h2>"
					 	    + 	"<p>" + product.seoDescription+"</p>"
					 	    + "</a>";
				 $("#productName").append(appendHtml);
				 
				 //加载价格
				 appendHtml =  price + "<del>" + marketPrice+"</del>";
				 $("#price").append(appendHtml);
			}
			 
			//加载规格
			 specHtml="<span>已选</span >";
			 $.each(specList, function(n, value) {
				 if(1 == value.flag){
					 specHtml += value.specName + "&nbsp;&nbsp;";
				 }
			 }); 
			 var productUnit = product.unit;
			 appendHtml = specHtml + "x&nbsp;&nbsp;1&nbsp;&nbsp;" + productUnit;
			 $("#specName").html(appendHtml);
			 
			 
		}
	}, function(){});
}

function productInfo(){
	window.location.href = "productInfo.html?productId=" + productId;
}


$(document).ready(function(){
	productId = URL_PARAM.getParam("id");
	
	//侧边导航
	$(function(){
	    var oWinW = $(window).height();
	    var choiceH = oWinW - $('.commodity-cat').outerHeight(true) - $('.commodity-box').outerHeight(true) - $('.number-box').outerHeight(true) - 30;
		$('.choice-box').css('max-height', choiceH);
		$('.specification a').on('click', function(e){
		    e.preventDefault();
			$('.slide-mask').show();
			$('.slide-wrapper').addClass('moved');
		});
		
		$('.slide-mask').on('click', function(){
			$('.slide-mask').hide();
			$('.slide-wrapper').removeClass('moved');
		});
	});
	
	initData();
});