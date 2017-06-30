var isApend = true;
var isTop = false;
var notEnd0 = true;
var specialId = URL_PARAM.getParam("specialId");
/**
 * 跳转到产品详情页面
 */
function goProductDetail(productId){
	window.location.href = "product-detail.html?id="+productId;
}
/**
 * 上下滑动分页
 */
function upDownTouch(){
		 var scrollImg = "<li class='scrimg' id ='scrimg'><p calss='name' align='center'>正在努力加载数据...</p></li>";
		  if($("#menu-index").val() == 0 && notEnd0){
	      		var page = Number($("#all-product-page").val()) + 1;
	  			$("#all-product-page").val(page);
	  			if(isApend){
	  				//$("#all-product-ul").append(scrollImg);
	  				$("#bodyEnd").append(scrollImg);
	  				isApend = false;
	  				getSpeciaProduct(rootPath+"/ptyt/special/findProductById?page="+page+"&specialId="+specialId,$("#all-product-ul"));
	  			}
	      }
}

/**
 * 查询所有专题商品
 * @param url
 * @param divshow
 */
function getSpeciaProduct(url, divshow){
	
	AJAX.call( url, "post", "json", "", true, function(result){
		var data = result.data;
		//删除等待图片
		$("li").remove(".scrimg");
		/*$("#scrimg").remove();
		$("#endli").remove();*/
		var appendHtml="";
		isApend = true;
		//当查询结果为空时，说明没有下一页了，设置结尾标识
		if(isNull(data)){
			//var endli = "<li class='endli' id ='endli'><p calss='name' align='center'>没有更多数据</p></li>";
			var endli = "<li id ='endli'><p calss='no-more-data' align='center'>没有更多数据</p></li>";
			if($("#menu-index").val() == 0 && notEnd0){
				notEnd0 = false;
				$("#bodyEnd").append(endli);
			};	
		}else{	
			for(var index in data){
				appendHtml ="<div class='pure pure-u-1-2'>" 
							+	"<a href='#'>"
							+		"<div class='products-wrap'>"
							+			"<img src='"+data[index].productImage+"' onclick=goProductDetail('"+data[index].productId+"') >"
							+			"<p class='title'>"+data[index].fullName+"</p>"
							+			"<p>"
							+				"<span class='price'>¥"+data[index].sellprice+"</span>"
							+				"<span class='promotion'>¥"+data[index].marketPrice+"</span>"
							+			"</p>"
							+		"</div>"
							+	"</a>"
							+"</div>";
				divshow.append(appendHtml);
			};
		};
	}, function(){});
}
function initSpecial(){
	AJAX.call(rootPath+"/ptyt/special/findSpecialById?specialId="+specialId, "post", "json", "", true, function(result){
		if(result.status){
				var appendHtml2="",appendHtml3="";
				appendHtml2="<a href='#'>"
							+	"<img src='"+result.data.mainImage+"'>"
							+"</a>"; 
				appendHtml3="<p>"+result.data.specialArticle+"</p>";
				$("#mainImage").append(appendHtml2);
				$("#context").append(appendHtml3);
				//$("title").text(result.data.specialTitle);
				
				var $body = $('body');
				document.title = result.data.specialTitle;
				var $iframe = $('<iframe src="/favicon.ico"></iframe>');
				$iframe.on('load',function() {
				  setTimeout(function() {
				      $iframe.off('load').remove();
				  }, 0);
				}).appendTo($body);
		}
		
	});
}


$(function(){
	$("#all-product-page").val("1");
	$("#mainImage").empty();
	$("#context").empty();
	$("#all-product-ul").empty();
	
	var winH = window.innerHeight;
    touch.on('.go-top-btn','touchend',function () {
        
         $('body,html').animate({scrollTop:0},300);


        });
	initSpecial();

	 //加载所有专题商品
	getSpeciaProduct(rootPath+"/ptyt/special/findProductById?page=1"+"&specialId="+specialId,$("#all-product-ul"));
	$(document).on('scroll',function(){
		//分页加载
		upDownTouch();
		
		//置顶功能
		var scrollTop=$(document).scrollTop();
        if(scrollTop>winH*0.2){
            $('.go-top-btn').fadeIn();
        }
        else{
            $('.go-top-btn').fadeOut();
        }
	});
})