var notEnd0 = true;
var isApend = true;
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
				"<span class='fl'>￥" + data[index].price + "</span>"+
				"<span class='del'>￥" + data[index].marketPrice + "</span>";
				if( data[index].stock > 0){
					appendHtml = appendHtml + "<span class='fr'><input id='stock_"+ data[index].id +"' value='"+ data[index].stock +"' type='hidden'/><a href='javascript:void(0)' onclick='addCart(\""+data[index].id + "\")' id='href_"+data[index].id+"'><em></em></a></span>";
				}else{
					appendHtml = appendHtml + "<span class='fr unav'><a href='javascript:void(0)'><em></em></a></span>" ;
				}
				
				appendHtml = appendHtml + "</div>" + "</div>" + "</div>" +	"</li>"; 
				
				divshow.append(appendHtml);
				
			};
		};
		
	}, function(){});
}
//首页广告
function findAdvertisementImage(){
	var adPositionId ="index_banner";
	AJAX.call(rootPath +"/wechat/product/findAdvertisementImage?adPositionId="+adPositionId, "post", "json", "", true, function(result){
		 if(!isNull(result)){
			var AdvertisementImageList = result.data;
			var i =0;
			var appendVar;
            $.each(AdvertisementImageList, function(n, value) {
            	i = n+1;
            	appendVar = "<li>" 
            			  +		"<a href='"+value.wx_url+"' data-ajax='false'>" 
            			  +			"<img src='" + value.image + "' alt='" + i + "' style='width:100%;'/>" 
            			  +		"</a>" 
            			  +	"</li>";
				$("#images_url").append(appendVar);
			}); 
            swiperImg();
		 }
	});
	}

function swiperImg(){
	new Swipe(document.getElementById('banner_box'), {
		speed:500,
		auto:3000,
		callback: function(){
			var lis = $(this.element).next("ol").children();
			lis.removeClass("on").eq(this.index).addClass("on");
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

$(document).ready(function(){
	
	$(".tab-bar-item").click(function(){
		$(".tab-bar-item").removeClass("hover");
		$(this).addClass("hover");
		$("#tagContent .tab-group").hide();
		$("#tagContent .tab-group").eq($(this).index()).show();
		$("#menu-index").val($(this).index());
	});
	
	findAdvertisementImage();
	
	//加载所有商品
	getProduct(rootPath + "/wechat/product/findHomeProduct?page=1",$("#all-product-ul"));

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
		  			getProduct(rootPath + "/wechat/product/findHomeProduct?page=" + page,$("#all-product-ul"));
		      }
			  //判断这个标签下面是否有这个div
			  if($("#returnTop").has("div").length < 1){
				  showTop();
			  }
			  checkTop();
		}
	});
});