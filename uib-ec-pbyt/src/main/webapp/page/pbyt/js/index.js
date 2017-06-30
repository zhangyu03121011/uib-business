var isApend = true;
var isTop = false;
var notEnd0 = true;
var productNumber = 0;

/**
 * 跳转到详情页面
 */
function goThemDetail(id){
	window.location.href = "theme_detail.html?specialId="+id;
}

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
	  				$("#all-product-ul").append(scrollImg);
	  				isApend = false;
	  				getSpeciaProduct(rootPath+"/ptyt/special/findSpecialByPage?page="+page,$("#all-product-ul"));
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
		var productList = data.productList;
		var specialList = data.specialList;
		//删除等待图片
		$("li").remove(".scrimg");
		/*$("#scrimg").remove();
		$("#endli").remove();*/
		isApend = true;
		//当查询结果为空时，说明没有下一页了，设置结尾标识
		if(isNull(specialList)){
			var endli = "<li class='endli' id ='endli'><p calss='name' align='center'>没有更多数据</p></li>";
			if($("#menu-index").val() == 0 && notEnd0){
				notEnd0 = false;
				$("#all-product-ul").append(endli);
			};	
		}else{	
			for(var index in specialList){
				var appendHtml="";
				productNumber =0;
				appendHtml ="<div class='big-img-container'>"
		            		+	"<a href='theme_detail.html?specialId="+specialList[index].specialId+"'>"
		            		+		"<img src='"+specialList[index].mainImage+"' class='big-img' />"
		            		+		"<Icon class='indicator' type='caret-up' /> "
		            		+	"</a>"
		            		+"</div>"
		            		+"<div class='slide-img-container swiper-sm-container '>"
		            		+	"<div class='product-box swiper-wrapper'>";
				for(var item in productList){
					if(specialList[index].specialId==productList[item].specialId){
						
						if(productNumber<=6){
							appendHtml+="<div class='product-info-box swiper-slide'>"
			        			+			"<img src='"+productList[item].productImage+"' class='pic' onclick=goProductDetail('"+productList[item].id+"') />"
			        			+			"<div class='title'>"+productList[item].originalName+"</div>"
			        			+			"<div class='price'>"
			        			+				"<span class='market-price'>¥"+productList[item].sellprice+"</span>"
			        			+				"<span class='discount-price'>¥"+productList[item].marketPrice+"</span>"
			        			+			"</div>"
			        			+		"</div>";
						}
						productNumber = productNumber+1;
						
					}
				}
				if(productNumber>6){
					appendHtml+=	"<div class='product-info-box swiper-slide ' onclick=goThemDetail('"+specialList[index].specialId+"')>"
				      +			"<div class='pic-last last-box'>"
				      +				"<div class='more-cn'>查看全部</div>"
				      +				"<div class='middle-line'></div>"
				      +				"<div class='more-en'>see more</div>"
			      	  +			"</div>"
			      	  +		"</div>"
					  +	"</div>"
					  +"</div>";
				}
				
				divshow.append(appendHtml);
			};
			swiperProductImg();
		};
	}, function(){});
}

//首页广告
function findAdvertisementImage(){
	var adPositionId ="index_banner";
	AJAX.call(rootPath +"/ptyt/prodHome/findAdvertisementImage?adPositionId="+adPositionId, "post", "json", "", true, function(result){
		 if(!isNull(result)){
			var AdvertisementImageList = result.data;
			var i =0;
			var appendVar="";
            $.each(AdvertisementImageList, function(n, value) {
            	i = n+1;
            	appendVar ="<div class='swiper-slide'>"
            			  +		"<a href='"+value.wx_url+"'>"
            			  +			"<img src='" + value.image + "' />"
            			  +		"</a>"
            			  +	"</div>";
//            	appendVar = "<li>" 
//            			  +		"<a href='"+value.wx_url+"' data-ajax='false'>" 
//            			  +			"<img src='" + value.image + "' alt='" + i + "' style='width:100%;'/>" 
//            			  +		"</a>" 
//            			  +	"</li>";
				$("#images_url").append(appendVar);
			}); 
            swiperImg();
		 }
	});
	}

function swiperImg(){
	/*banner轮播图*/
	var bannerSwiper = new Swiper('.swiper-container',{
		autoplay : 3000,//可选选项，自动滑动
		loop : true,//可选选项，开启循环
		autoplayDisableOnInteraction:false, //触摸后可继续滑动
		pagination:'.banner-pagination'
	});
}

function swiperProductImg(){
	/*局部滑动选择，使用swiper插件*/
	var smPicSwiper = new Swiper('.swiper-sm-container',{
		
		loop : false,
		slidesPerView:3.5,
		freeMode:true
	})
}

$(function(){
	$("#all-product-page").val("1");
	$("#all-product-ul").empty();
	var winH = window.innerHeight;
    touch.on('.go-top-btn','touchend',function () {
        
         $('body,html').animate({scrollTop:0},300);


        });
	
	findAdvertisementImage();

	 //加载所有专题商品
	getSpeciaProduct(rootPath+"/ptyt/special/findSpecialByPage?page=1",$("#all-product-ul"));
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