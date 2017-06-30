var needGetProduct = true;
var productId="";
var oldProductId = "";
var specList="";
var specArray = new Array();
var specHtml = "";
var productUnit = "";
var product;
var imageDomain="";
var stock=0;
var isMarket =0;
var productNumber = 1;
var t;
var desc;
var link;
var urlShare=window.location.href;
var imgUrl;
var rmemberId;

function initData(isInit){
	
	AJAX.call(rootPath +"/wechat/product/getProductDetail", "post", "json", "productId=" + productId, true, function(result){
		if(isNotEmpty(result)){
			
			var appendHtml;
			var price;
			var marketPrice;
			product = result.product;
			t=product.fullName;
			desc=product.seoDescription;
			price = formatPrice(product.price);
			marketPrice = formatPrice(product.marketPrice);
			imageDomain = result.imageDomain;
			specList = result.specList;
			var cartInfo = result.cartInfo;
			var imageList = null;
			var productImg = "";
			if(isNotEmpty(product)){
				imageList = product.productImageRefList;
				stock = product.stock - product.allocatedStock;
				isMarket = product.wxIsMarketable;
				productImg = imageDomain + product.image;
				imgUrl=productImg;
			
			}
			
			 $("#swiperWrapper").empty();
			 $("#productName").empty();
			 $("#price").empty();
			 $("#specName").empty();
			 $("#btnList").empty();
			
			//加载轮播图
			if(isNotEmpty(productImg)){
				appendHtml = "<div class='swiper-slide'>"
			 		+ 	"<img  id='productImg' src='"+ productImg + "'/></div>";
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
					 	    + 	"<h2 id='fullName'>" + product.fullName + "</h2>"
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
			 productUnit = product.unit;
			 appendHtml = specHtml + "x&nbsp;&nbsp;1&nbsp;&nbsp;" + productUnit;
			 $("#specName").html(appendHtml);
			 
			 appendHtml ="";
			 //加载收藏按钮
			 appendHtml = "<a class='collect-btn' href='javascript:void(0)' onclick='collectClick()'>收藏</a>";
			 
			 //加载购物车按钮及数量
			 appendHtml += "<a class='collect-btn' href='cart.html'>";
			 if(isNotEmpty(cartInfo)){
				 if(cartInfo.count > 0){
					 appendHtml += "购物车<b>"+ cartInfo.count +"</b>";
					 $("#cartId").html(appendHtml);
				 }else{
					 appendHtml +="购物车";
				 }
			 }else{
				 appendHtml +="购物车";
			 }
			 appendHtml += "</a>";
			 
			 //加载加入购物车按钮
			 if(stock > 0 && isMarket== 1){
				 appendHtml += "<a class='buy-btn' href='javascript:void(0)' onclick='addCart(productNumber)'>加入购物车</a>";
			 }else{
				 if(isMarket != 1){
					 appendHtml += "<a class='buy-btn none' href='javascript:void(0)'>已下架</a>"; 
				 }else{
					 appendHtml += "<a class='buy-btn none' href='javascript:void(0)'>加入购物车</a>"; 
				 }
			}
				 
			 $("#btnList").append(appendHtml);
			 
			 initSepcInfo(isInit);
			 
		}
	}, function(){});
}

/**
 * 收藏按钮点击事件
 */
function collectClick(){
	$.ajax({  
	    url : rootPath + "/wechat/member/favorite/favoriteProduct?productId="+productId,  
        async : false, 
        type : "POST",  
        success : function(result) {
        	if(result.status){
        		Alert.disp_prompt("添加收藏成功");
        	}else{
        		Alert.disp_prompt(result.msg);
        	}
        }  
    });
	//Alert.disp_prompt("暂不支持此功能");
}
/*
 * 数量只能输入数值
 */
function keyPress() {    
    var keyCode = event.keyCode;    
    if ((keyCode >= 48 && keyCode <= 57))    
   {    
        event.returnValue = true;    
    } else {    
          event.returnValue = false;    
   }    
}    

/**
 * 加载商品规格信息
 */
function initSepcInfo(isInit){
	//加载商品信息
	if(isNotEmpty(product)){
		$("#asideImg").attr("src",imageDomain + product.image);
		$("#asidePrice").text(formatPrice(product.price));
		$("#asideNo").text("商品编号：" + product.productNo);
		
		//加载加入购物车按钮
		$("#asideCart").empty();
		var appendHtml="";
		 if(stock > 0 && isMarket== 1){
			 appendHtml = "<a href='javascript:void(0)' onclick='addCartForSpec()'>加入购物车</a>";
		 }else{
			 if(isMarket != 1){
				 appendHtml +=  "<a href='javascript:void(0)' class='none'>已下架</a>"; 
			 }else{
				 appendHtml +=  "<a href='javascript:void(0)' class='none'>加入购物车</a>"; 
			 }
		 }
		 $("#asideCart").append(appendHtml);
	}
	
	//加载规格组合规格信息
	if(isNotEmpty(specList) && isInit){
        var appendHtml="";
        var currentSpecGroupId="";
        var lastSpecGroupId="";
    	$.each(specList, function(n, value) {
    		currentSpecGroupId = value.specGroupId;
    		//加载规格组
    		if(currentSpecGroupId != lastSpecGroupId){
    			//第一次不打尾
    			if( lastSpecGroupId != ""){
    				appendHtml += "</ul>" + "</div>" + "</div>";
    			}
    			appendHtml += "<div class='specification-box'>";
    			appendHtml += "<div class='fl com-title'>" + value.specGroupName + "</div>";
    			appendHtml += "<div class='parameter-select'>"
							+ 	"<ul id='"+ value.specGroupId +"'>";
    			lastSpecGroupId = currentSpecGroupId;
    		}
			
			if(1 == value.flag ){
				appendHtml += "<li class='hover' onclick='specOnclick(this)' id='" + value.specId + "'>" + value.specName + "</li>";
			}else{
				appendHtml += "<li onclick='specOnclick(this)' id='" + value.specId + "'>" + value.specName + "</li>";
			}
		 }); 
    	
    	//最后一次打尾
    	appendHtml += "</ul>" + "</div>" + "</div>";
    	$("#specList").append(appendHtml);
        
	}
		
}

/**
 * 规格选择事件
 * @param obj
 */
function specOnclick(obj){
	var parentUl = $(obj).parent();
	var childLi = $(parentUl).children('li');
	//当前li亲兄弟节点去除选中边框
//	$(childLi).removeClass('hover');
//	$(obj).addClass('hover');
	$(childLi).attr('class','');
	$(obj).attr('class','hover')
	checkIfUpdateProduct();
}


/**
 * 判断规格是否选择完整
 * 完整则更新产品信息
 */
function checkIfUpdateProduct(){
	specArray = []; 
	needGetProduct = true;
	var ulList = $(".parameter-select").children('ul');
	//循环每一个规格组
	$.each(ulList, function(n, ul) {
		var liList = $(ul).children('li');
		var isChecked = false;
		//循环规格组的规格
		$.each(liList, function(n, li) {
			var liClass= $(li).attr('class');
			if(liClass == 'hover'){
				isChecked = true;
				//选中的规格保存起来
				var specId = $(li).attr('id');
				specArray.push(specId);
				return false;
			}
		});
		if(!isChecked){
			needGetProduct = false;
			return false;
		}
	});
	
	//更新productId
	if(needGetProduct){
		var specIds = specArray.join(',');
		AJAX.call(rootPath +"/wechat/product/queryProductIdBySpecIds", "post", "json", "productId=" + productId +"&specIds=" + specIds, true, function(result){
			if(isNotEmpty(result)){
				//刷新页面商品信息
				if(isNotEmpty(result.productId)){
					productId = result.productId;
					if(oldProductId != productId){
						oldProductId = productId;
						initData(false);
						$("#productNum").val(1);
						productNumber = 1;
					}else{
						$("#asideCart").empty();
						var appendHtml = "<a href='javascript:void(0)' onclick='addCartForSpec()'>加入购物车</a>";
						$("#asideCart").append(appendHtml);
					}
				}else{
					//加载加入购物车按钮置灰
					$("#asideCart").empty();
					var appendHtml="<a href='javascript:void(0)' class='none'>已下架</a>";
					$("#asideCart").append(appendHtml);
				}
			}else{
				//加载加入购物车按钮置灰
				$("#asideCart").empty();
				var appendHtml="<a href='javascript:void(0)' class='none'>已下架</a>";
				$("#asideCart").append(appendHtml);
			}
		}, function(){});
	}
}

/**
 * 加入购物车
 */
function addCart(quant){
	$.get(rootPath + "/wechat/user/ticket/state/",{},function(data){
		if(data == '0'){
			window.location.href="login.html?locationParm=jump";
			return;
		}else{
			if(isNotEmpty(productId)){
				AJAX.call( rootPath + "/wechat/cart/addCart?productId=" + productId +"&quantity=" + quant, 
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

function addCartForSpec(){
	var quant = $("#productNum").val();
	if(needGetProduct){
		addCart(quant);
	}else{
		Alert.disp_prompt("请选择规格");
	}
}

function productInfo(){
	window.location.href = "productInfo.html?productId=" + productId;
}

function countMin(obj){
	var count = $("#productNum").val();
	if(count <= 2){
		$(obj).removeClass("count-min");
		$(obj).removeClass("count-min cover");
		$(obj).addClass("count-min cover");
	}else{
		$(obj).removeClass("count-min cover");
		$(obj).removeClass("count-min");
		$(obj).addClass("count-min");
		
	}
	if(count> 1){
		var quantity = Number(count) - Number(1);
		$("#productNum").val(quantity);
		appendHtml = specHtml + "x&nbsp;&nbsp;" + quantity + "&nbsp;&nbsp;" + productUnit;
		$("#specName").html(appendHtml);
	}
	
}

function countAdd(obj){
	var countMin = $("#productNum").prev();
	var count = $("#productNum").val();
	var quantity = Number(count)+Number(1);
	
	$(countMin).removeClass("count-min cover");
	$(countMin).removeClass("count-min");
	$(countMin).addClass("count-min");
	if(quantity > 99){
		$("#productNum").val(99);
		appendHtml = specHtml + "x&nbsp;&nbsp;" + 99 + "&nbsp;&nbsp;" + productUnit;
		$("#specName").html(appendHtml);
		Alert.disp_prompt("单件商品加入购物车不能超过99件");
	}else{
		$("#productNum").val(quantity);
		productNumber = quantity;
		appendHtml = specHtml + "x&nbsp;&nbsp;" + quantity + productUnit;
		$("#specName").html(appendHtml);
	}
	
}

function weixinShare(){
	var url = location.href.split('#')[0];
	$.get(rootPath + "/wechat/member/user/getWechatConfig?url=" + url,"",function(data){
		wx.config({
			debug: false,
			appId: data.appId,
			nonceStr: data.nonceStr,
			signature: data.signature,
			timestamp: parseInt(data.timestamp),
			jsApiList: [
	            'checkJsApi',
	            'onMenuShareTimeline',
	            'onMenuShareAppMessage',
	            'onMenuShareQQ',
	            'onMenuShareWeibo',
	            'hideMenuItems',
	            'showMenuItems',
	            'hideAllNonBaseMenuItem',
	            'showAllNonBaseMenuItem',
	            'translateVoice',
	            'startRecord',
	            'stopRecord',
	            'onRecordEnd',
	            'playVoice',
	            'pauseVoice',
	            'stopVoice',
	            'uploadVoice',
	            'downloadVoice',
	            'chooseImage',
	            'previewImage',
	            'uploadImage',
	            'downloadImage',
	            'getNetworkType',
	            'openLocation',
	            'getLocation',
	            'hideOptionMenu',
	            'showOptionMenu',
	            'closeWindow',
	            'scanQRCode',
	            'chooseWXPay',
	            'openProductSpecificView',
	            'addCard',
	            'chooseCard',
	            'openCard'
	        ]
		});
	});

	wx.ready(function () {
		//分享给朋友
		wx.onMenuShareAppMessage({
		    title: '商品详情', // 分享标题
		    desc: t, // 分享描述
		    link: link, // 分享链接
		    imgUrl: imgUrl, // 分享图标
		    type: link, // 分享类型,music、video或link，不填默认为link
		    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
		    success: function () { 
		        // 用户确认分享后执行的回调函数
		    	Alert.disp_prompt("分享成功");
		    },
		    cancel: function () {    
		        // 用户取消分享后执行的回调函数
		    	Alert.disp_prompt("分享失败");
		    }
		});
		//分享到朋友圈
		wx.onMenuShareTimeline({
		    title: t, // 分享标题
		    link:link, // 分享链接
		    imgUrl: imgUrl, // 分享图标
		    success: function () { 
		        // 用户确认分享后执行的回调函数
		    	Alert.disp_prompt("分享成功");
		    },
		    cancel: function () { 
		        // 用户取消分享后执行的回调函数
		    	Alert.disp_prompt("分享失败");
		    }
		});
		//分享到QQ
		wx.onMenuShareQQ({
		    title:"商品详情", // 分享标题
		    desc: t, // 分享描述
		    link: link, // 分享链接
		    imgUrl:imgUrl, // 分享图标
		    success: function () { 
		       // 用户确认分享后执行的回调函数
		    	Alert.disp_prompt("分享成功");
		    },
		    cancel: function () { 
		       // 用户取消分享后执行的回调函数
		    	Alert.disp_prompt("分享失败");
		    }
		});		
	});
	
}

function initProductInfo(){
	//var productId = URL_PARAM.getParam("productId");
	AJAX.call( "/f/wechat/product/initProductInfo?productId=" + productId, "post", "json", "", true, function(result){
        if(!isNull(result)){
        	var product = result.product;
            var paramlist = result.paramlist;
            var propList = result.propList;
            $("#tagContent0").append(product.introduction);
            $.each(paramlist, function(n, value) {
				$("#parmBody").append("<tr><td>"+value.name+"</td><td>"+value.parameterValue+"</td></tr>");
			}); 
            
            $.each(propList, function(n, prop) {
				$("#propBody").append("<tr><td>"+prop.groupName+"</td><td>"+prop.propertyName+"</td></tr>");
			}); 
        }
	}, function(){});
}


$(document).ready(function(){
	productId = URL_PARAM.getParam("id");
	
	urlShare = urlShare.substring(0, urlShare.lastIndexOf("/"));
	link =urlShare+"/shareProducts.html?id="+productId;

	
	//查找会员ID
	AJAX.call("/f/wechat/member/user/getMemberByUserName", 'get','json', '', false,
			  function(data){
				if(data != null){
					rmemberId=data.id;
					link =urlShare+"/shareProducts.html?id="+productId+"&rmemberId="+rmemberId;
					return;
				} 
			  }, function(){});
	
	
	
	//侧边导航
	$(function(){
	    var oWinW = $(window).height();
	    var choiceH = oWinW - $('.commodity-cat').outerHeight(true) -    $('.commodity-box').outerHeight(true) - $('.number-box').outerHeight(true) - 30;
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
	//图纹详情
	$(".tab-bar-item").click(function(){
		$(".tab-bar-item").removeClass("hover");
		$(this).addClass("hover");
		$("#tagContent .tab-group").hide();
		$("#tagContent .tab-group").eq($(this).index()).show();
	});
	initData(true);
	weixinShare();
	initProductInfo();
	
	$("#registered").click(function(){
		 window.location.href="/page/weixin/shareRegistered.html?productId="+productId+"&rmemberId="+rmemberId; 
	});
	$("#loginShare").click(function(){	
		 window.location.href="/page/weixin/shareLogin.html?productId="+productId+"&rmemberId="+rmemberId; 
		
	}); 
});