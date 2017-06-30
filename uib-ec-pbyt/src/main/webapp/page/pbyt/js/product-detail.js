var needGetProduct = false;
var productId="";
var recommendMemberId ="";
var specList="";
var specArray = new Array();
var specHtml = "";
var productUnit = "";
var product;
var imageDomain="";
var stock=0;
var isMarket =0;
var quant=1;
var isNot="";
//当前用户ID
var MemberId;
//商品副标题
var originalName;
//地区
var results ="" ;
var Desc;
var t;
var desc;
var bombbox="";
var link;
var urlShare=window.location.href;
var imgUrl;
var rmemberId;
var sale = 0;
var groupMap = {};
var groupSpecMap = {};
//商品在该区域有货无货
var adressHave=true;
//用户是否选择区域   true为未选择 false为已选择
var adressChenk=true;
//判断是否已关注云天商城  0为未关注
var flag;
//当前用户类型
var usertype;
//分享人用户类型
var  userType;
//当前用户用户类型
var usertype;
var areaIds;
var $fixedPage = $('.spec-choose-content');
var $input = $('#BuyNum');

function preventMove(e){ 
    e.preventDefault();
}

$input.focus(function(){
    $fixedPage.on('touchstart touchmove',preventMove);
});

$input.blur(function(){
    $fixedPage.off('touchstart touchmove',preventMove);
});
$(function(){
	//商品id
	productId = URL_PARAM.getParam("id");
	//分享人等级id
	userType=URL_PARAM.getParam("userType");
	//分享人id
	recommendMemberId = URL_PARAM.getParam("recommendMemberId");
	//刷新访问量
	if(userType !=null && userType!="" &&
			 userType!=undefined && userType!="null"){
	  updateViewCount(URL_PARAM.getParam("recommendMemberId"));
	}
	/*判断用户是否为审核通过的B端*/
    AJAX.call(rootPath+"/memMember/FlagMemMember","post","json", "", false, function(result){
		 var Status=result.status;
		 var data=result.data;
		 var  host="http://"+window.location.host;
		 if(Status){
			  MemberId=data.id;	
			
		      usertype=data.userType;
		      link=location.href.split('?')[0]; 		      
    		  link=host+rootPath+"/pbyt/product/DetilCenter"+"?id="+productId+"&recommendMemberId="+MemberId+"&userType="+ usertype+"&link="+link;
    		  $("#memId").val(MemberId);    		  
		 }else{
			 if("3021"==result.code){
				 $("#addStore").hide();
				 $("#tuiguang").hide();
				 $("#memId").val(MemberId);
				 MemberId=data.id;				 
				 usertype=data.userType;			 				 
					 link=location.href.split('?')[0];					
					 link=host+rootPath+"/pbyt/product/DetilCenter"+"?id="+productId+"&recommendMemberId="+MemberId+"&userType="+ usertype+"&link="+link;			 
			 }else{
				 var dia=$.dialog({
		                content: result.msg,
		                button: ["取消", '确定']
		            });  
			 }			 
		 }
    }, function(){});
    
  
    /*轮播图js*/
	/*var bannerSwiper = new Swiper('.swiper-container',{
		//autoplay : 8000,//可选选项，自动滑动
		loop : true,//可选选项，开启循环
		autoplayDisableOnInteraction:false, //触摸后可继续滑动
		pagination:'.banner-pagination'
	});*/

	/*处理tab*/
	touch.on('.head-text','tap',function(){
		var content = $(this).data('name');
		$(this).addClass('active').siblings().removeClass('active');
		$('.content-'+content).removeClass('hide-content').siblings().addClass('hide-content');

	});
    /*开启规格选择界面*/
	touch.on('#detailSelect','tap',function(){
		$('#productDcolor').toggleClass('open');

	});

	/*关闭数量选择页面*/
	touch.on('#ColorClose','tap',function(){
		$('#productDcolor').toggleClass('open');
	});
    /*地区选择组件*/
	var selectArea = new MobileSelectArea();
	selectArea.init({trigger:'.select-item',value:$('.defaultValue').data('value'),data:rootPath+'/ptyt/addr/findAddressList',level:2,eventName:'click',callback:chooseBoxCallBack });
	
	
	/*处理规格单选*/
	touch.on('.spec span','tap',function(){
		$(this).addClass('selected').siblings().removeClass('selected');
	});

   	//跳转我的购物车
	touch.on('.shop-cart','tap',function(){
		window.location.href = "cart.html";
	});

	/*导航栏开关*/
	$(function() {
        touch.on('.circle', 'touchstart', function() {
            $(".circle").toggleClass('close');
        });
    });
	if(userType !=null && userType!="" && userType!=undefined && userType!="null"){
		    
	       //初始化商品详情信息
	       initData(true,recommendMemberId);
	       //初始化商品参数
	       initProductInfo(recommendMemberId);
	}else{
		
		 initData(true,MemberId);
		 initProductInfo(MemberId);
	}
	//初始化微信分享配置
	//alert(rankId);
	weixinShare();
	
});
/**
 * 验证商品在该地区有货无货
 * @param scroller
 * @param text
 * @param value
 */
function chooseBoxCallBack(scroller,text,value){
	 areaIds=value;
	 results="";
	for(var i=0 ;i<text.length ; i++){
		results += (" "+text[i]);
	} ;
	
	AJAX.call(rootPath+"/pbyt/product/vilatAdress?productId="+productId+"&areaIds="+areaIds,"post", "json", "",false, function(result){
		var Stutas=result.status;
		if(Stutas){	
			if(stock==0){
				$('.data-input').empty();
				$('.data-input').html(results+"    无货") ;
			}else{
				$('.data-input').empty();
				$('.data-input').html(results+"    有货") ;
				adressHave=false;
			}
		}else{
			$('.data-input').empty();
			$('.data-input').html(results+"    无货") ;
		}
		
	}, function(){});
	$("#adressTo").hide();
	adressChenk=false;
	
}

/***
 * 规格选择事件
 * @param obj
 */
function selectSpec(obj){
	$(obj).addClass('selected').siblings().removeClass('selected');
	if(userType !=null && userType!="" && userType!=undefined && userType!="null"){
			checkIfUpdateProduct(recommendMemberId);
	}else{
		checkIfUpdateProduct(MemberId);
	}
}

/**
 * 初始化数据
 * @param isInit
 */
function initData(isInit,memberId){
	
	AJAX.call(rootPath +"/pbyt/product/getProductDetail?productId=" + productId+"&memberId="+memberId, "post", "json", "", true, function(result){
		if(isNotEmpty(result)){
			var appendHtml;
			var price;
			var marketPrice;
			product = result.product;
			t=product.fullName;
			desc=product.seoDescription;
			price = formatPrice(product.sellPrice);
			marketPrice = formatPrice(product.marketPrice);
			imageDomain = result.imageDomain;
			specList = result.specList;
			sale=result.sales;
			Desc= "售价："+price+"。云天商城,正品保证,微信专享。";
			originalName=product.originalName;
			$("#fullName").text(originalName);
			if(sale==undefined){
				sale=0;
			}
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
			 $("#cart_count").text(cartInfo.count);
			
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
			/*banner轮播图*/
			console.log('in')
			var bannerSwiper = new Swiper('.swiper-container',{
				//autoplay : 8000,//可选选项，自动滑动
				loop : true,//可选选项，开启循环
				autoplayDisableOnInteraction:false, //触摸后可继续滑动
				pagination:'.banner-pagination'
			});
			if(isNotEmpty(product)){
				$("#productName").append(product.fullName);
				$("#price").append(price);
				$("#price2").text("卖家促销 "+price);
				$("#markprice").append(marketPrice);
			}
			initSepcInfo(isInit);
		}
	}, function(){});
	/*	处理数量按钮*/
	touch.on('.buy-quantity .cal','tap',function(){
		var type = $(this).data('cal');
		var num = +$('.num').val();
		if(type == 'add'){
			$('.buy-quantity .num').val(++num);
			$('.buy-quantity .sub').removeClass('disabled');
			if(num==stock || num>stock){
				$('.buy-quantity .add').addClass('disabled');
				num=stock;
				$('.buy-quantity .num').val(num);
			}
		}
		else if(type == 'sub' && num > 1){
			$('.buy-quantity .num').val(--num);
			$('.buy-quantity .add').removeClass('disabled');
			if(num <= 1){
				$('.buy-quantity .sub').addClass('disabled');
				$('.buy-quantity .add').removeClass('disabled');
			}
		}
		else{
			return;
		}

	});
	
}

$("#BuyNum").blur(function(){
	var buyNum=$("#BuyNum").val();
	if(buyNum>stock){
		var dia=$.dialog({
            content: '库存不足！',
            button: ["取消", '确定']
        });
		
	}
})

/**
 * 加载商品规格信息
 */
function initSepcInfo(isInit){
	//加载商品信息
	if(isNotEmpty(product)){
		$("#asideImg").attr("src",imageDomain + product.image);
		$("#asidePrice").text(formatPrice(product.sellPrice));
		$("#stock").text("剩余"+stock+"件");
		$("#sales").text("销量"+sale+"笔");
		$("#stock2").text("库存"+stock+"件");
		
		$("#asideCart").empty();
		var appendHtml="";
		 if(stock > 0 && isMarket== 1){
			 appendHtml = "<button class='submit-btn' onclick='submitSpec()'>提交</button>";
			 isNot="0";
		 }else{
			 if(isMarket != 1){
				 appendHtml = "<button class='submit-btn'>已售罄</button>";
				 isNot="1";
			 }else{
				 appendHtml = "<button class='submit-btn' onclick='submitSpec()'>提交</button>";
			 }
		 }
		 $("#asideCart").append(appendHtml);
	}
	//加载规格组合规格信息
	if(isNotEmpty(specList) && isInit){
        var appendHtml = "";
        var groupIds = [];
    	$.each(specList, function(n, value) {
    		var $_flag = true; 
    		for(var i = 0;i<groupIds.length;i++){
    			if(value.specGroupId == groupIds[i]){
    				$_flag = false;
    				break;
    			}
    		}
    		if($_flag){
    			groupMap[value.specGroupId] = value;
    			groupIds.push(value.specGroupId);
    		}
		 });
    	for(var i = 0;i<groupIds.length;i++){
    		appendHtml += "<div class='spec-choose'>";
    		appendHtml += "<h3 class='title'>"+groupMap[groupIds[i]].specGroupName+"</h3><div class='spec' id='"+groupMap[groupIds[i]].specGroupId+"'>";
    		$.each(specList, function(n, value) {
    			groupSpecMap[value.specId] = value;
        		if(value.specGroupId == groupIds[i]){
        			appendHtml += "<span id='"+value.specId+"' onclick='selectSpec(this)'>"+value.specName+"</span>";
        		}
        	});
    		appendHtml += "</div></div>";
    	}
    	
    	$("#specList").append(appendHtml);
	}
		
}

/**
 * 判断规格是否选择完整
 * 完整则更新产品信息
 */
function checkIfUpdateProduct(MemberId){
	specArray = []; 
	needGetProduct = false;
	$.each($(".spec"), function(n, p){
		if(n == 0){
//			specHtml = groupMap[$(p).attr('id')].specGroupName;
			specHtml = "";
		}
		$.each($(p).children("span"), function(i, span){
			var clazz = $(span).attr('class');
			if(clazz == 'selected'){
				specHtml += "&nbsp;&nbsp;"+groupSpecMap[$(span).attr('id')].specName;
				specArray.push($(span).attr('id'));
			}
		});
	});
	if(specArray.length < $(".spec").length){
		needGetProduct = false;
		return false;
	}
	needGetProduct = true;
	
	//更新productId
	AJAX.call(rootPath +"/wechat/product/queryProductIdBySpecIds", "post", "json", "productId=" + productId +"&specIds=" + specArray.join(',')+"&memberId="+MemberId, true, function(result){
		if(isNotEmpty(result)){
			//刷新页面商品信息
			if(isNotEmpty(result.productId)){
				productId = result.productId;
				//alert(result.Stock);
				//alert(result.allStock);
				stock=result.Stock-result.allStock;
				isMarket=result.wxIsMarketable;
				$("#stock2").text("库存"+stock+"件");
				var price = result.sellPrice;
				$("#price2").text("卖家促销   ¥"+price);
				$("#price").empty();
				$("#price").text(result.sellPrice);
				$("#stock").text("剩余"+stock+"件");
				$("#productName").empty();
				$("#productName").html(result.fullName);
				sale=result.sales;
				if(sale==undefined){
					sale=0;
				}
				$("#sales").text("销量"+sale+"笔");
				$('.data-input').empty();
				if(stock==0){
					$('.data-input').html(results+"    无货") ;
				}else{
					$('.data-input').html(results+"    有货") ;
					adressHave=false;
				}
				if(stock>0 && isMarket== 1){
					$("#asideCart").empty();
					var appendHtml = "<button class='submit-btn' onclick='submitSpec()'>提交</button>";
					$("#asideCart").append(appendHtml);	
					isNot="0";
				}else{
					//加载加入购物车按钮置灰
					if(isMarket!=1){
						$("#asideCart").empty();
						var appendHtml = "<button class='submit-btn'>已下架</button>";
						isNot="1";
						$("#asideCart").append(appendHtml);											
					}else{
						if(stock==0){
							$("#asideCart").empty();
							var appendHtml = "<button class='submit-btn'>已售罄</button>";
							isNot="1";
							$("#asideCart").append(appendHtml);	
						}
					}					
				}
			}else{
				//加载加入购物车按钮置灰
				$("#asideCart").empty();
				var appendHtml = "<button class='submit-btn'>已下架</button>";
				isNot="1";
				$("#asideCart").append(appendHtml);
			}
		}else{
			//加载加入购物车按钮置灰
			$("#asideCart").empty();
			var appendHtml = "<button class='submit-btn'>已下架</button>";
			isNot="1";
			$("#asideCart").append(appendHtml);
		}
	}, function(){});
}

/***
 * 提交选择的商品规格
 */
function submitSpec(){
	validateSpecList();
	if(!needGetProduct){
		Alert.disp_prompt("请选择商品规格");
	}else{
		quant = $('.buy-quantity .num').val();
		if(specList.length==0){
    		$("#selectspec").html("已选:"+" "+"数量×"+quant);
		}else{
			
			$("#selectspec").html("已选:"+" "+specHtml+" "+"数量×"+quant);
		}
		$('#productDcolor').toggleClass('open');
	}
}
/**
 * 判断规格是否为空
 * 若为空则可以直接加入购物车
 */
function validateSpecList(){	
	if(specList.length==0){		    			
    		needGetProduct=true; 
    		
	}
}
/**
 *弹框 
 */
function Bombbox(){
	if(bombbox=="1"){				
			var dia=$.dialog({
				content: '加入购物车成功',
				button: ["取消", '确定']
			});						
	}else if(bombbox=="2"){		
			var dia=$.dialog({
				content: '请打开微信关注平步云天公众账号，即可购买该商品。申请成为代理还可轻松赚钱。',
				button: ["取消", '确定']
			});
				
	}else if(bombbox=="3"){		
             var dia=$.dialog({
                content: '该商品已添加到“我的店铺”！',
                button: ["取消", '确定']
            });
        
	}else if(bombbox=="4"){		
             var dia=$.dialog({
                content: '加入失败！请重试！',
                button: ["取消", '确定']
            });    
	}else if(bombbox=="5"){
		var dia=$.dialog({
            content: '该商品已在您的店铺中，请勿重复添加。',
            button: ["取消", '确定']
        });    
	}
	
}

function shopValid(){
	if(isNot=="1"){
		var dia=$.dialog({
            content: '该商品已售罄！',
            button: ["取消", '确定']
        });
		return true;
	}
}
/**
 * 验证是否登录
 */
function validateMem(){
	//验证是否登录
	var valiMem = false;
	AJAX.call(rootPath+"/memMember/queryMemMember","post","json", "", false, function(result){
		var Status=result.status;
		if(!Status){
			bombbox="2";
			valiMem=false;
		}else{
			valiMem=true;
		}	
	}, function(){})
	return valiMem;
}
/***
 * 加入购物车
 */
function addCartForSpec(){
	if(adressChenk){
		var dia=$.dialog({
            content: '请选择配送地区！',
            button: ["取消", '确定']
        });
		return false;
	}
	if(adressHave){
		var dia=$.dialog({
            content: '无货，此商品不支持配送至该地区！',
            button: ["取消", '确定']
        });
		return false;
	}
	if(!validateMem()){
		Bombbox();	
		return false;
	}
	validateSpecList();
	if(!needGetProduct){
		Alert.disp_prompt("请选择商品规格");
		return false;
	}
	if(stock==0){
		isNot="1";
	}	
	if(shopValid()){
		return false;
	}else{
		 quant = $('.buy-quantity .num').val();
		AJAX.call( rootPath + "/wechat/cart/addCart?productId=" + productId +"&quantity=" + quant+"&specificationIds=" + specArray.join(',')+"&recommendMemberId="+recommendMemberId+"&areaIds="+areaIds, 
				  				"post", "json", "", false, 
		  function(result){
				if(result.status){
					bombbox="1";
				}else{
					 var dia=$.dialog({
			                content: result.msg,
			                button: ["取消", '确定']
			            });  
					
				}
	      }, function(){});
	}
	Bombbox();	
}

/**
 * 初始化商品参数
 */
function initProductInfo(memberId){
	AJAX.call( "/f/pbyt/product/initProductInfo?productId=" + productId+"&memberId="+memberId, "post", "json", "", true, function(result){
        if(!isNull(result)){
        	var product = result.product;
            var paramlist = result.paramlist;
            var propList = result.propList;
            $("#tagContent0").append(product.introduction);
            $.each(paramlist, function(n, value) {
				$("#parmBody").append("<tr><td class='table-spec'>"+value.name+"</td><td class='detail'>"+value.parameterValue+"</td></tr>");
			}); 
        }
	}, function(){});
}

/***
 * 刷新推广记录浏览量
 * @param recommUserId
 */
function updateViewCount(recommUserId){
	if(!isNull(recommUserId)){
		AJAX.call(rootPath+"/ptyt/recommProduct/updateViewCount?productId=" + productId+"&recommMemberId="+recommendMemberId, "post", "json","", true, function(result){
	        if(!isNull(result)){
	        	if(!result.status){
	        		Alert.disp_prompt(result.msg);
	        	}
	        }
		}, function(){});
	}
}

function replaceAll(str , replaceKey , replaceVal){
	  var reg = new RegExp(replaceKey , 'g');//g就是代表全部
	  return str.replace(reg , replaceVal || '');
	}
/**
 * 微信分享
 */
function weixinShare(){
	var url;
	if(userType !=null && userType!="" && userType!=undefined && userType!="null"){
		 url = location.href.split('#')[0];
		 url=replaceAll(url,'&', '%26');		
	}else{		
		url = location.href.split('#')[0];
	}
	$.get(rootPath + "/wechat/member/user/getWechatConfig?url="+url,"",function(data){		
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
	        ],		 
		});
	});
	
	wx.ready(function () {
		
		//分享到微信好友
		wx.onMenuShareAppMessage({
		    title: t, // 分享标题
		    desc:Desc, // 分享描述
		    link: link, // 分享链接
		    imgUrl: imgUrl, // 分享图标
		    type: link, // 分享类型,music、video或link，不填默认为link
		    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
		  
		    success: function () { 
		    	
		        insertRecommProduct();		    		    	
		    	$('.share-tips').addClass('hide');
		    	Alert.disp_prompt("分享成功");
		    },
		    cancel: function () {    
		        // 用户取消分享后执行的回调函数
		    	$('.share-tips').addClass('hide');
		    	Alert.disp_prompt("分享失败");
		    	},
		
		});
		
		//分享到朋友圈
		wx.onMenuShareTimeline({
			title: t, // 分享标题
			link:link, // 分享链接
		    imgUrl: imgUrl, // 分享图标
		    success: function () { 
		        insertRecommProduct();
		        $('.share-tips').addClass('hide');
		    	Alert.disp_prompt("分享成功");
		    },
		    cancel: function () { 
		        // 用户取消分享后执行的回调函数
		    	$('.share-tips').addClass('hide');
		    	Alert.disp_prompt("分享失败");
		    }			
		});	
	});
	
	wx.error(function (res) {
		
	});
}

//新增推广记录
function insertRecommProduct(){
	AJAX.call(rootPath+"/ptyt/recommProduct/insertRecommProduct","post", "json","productId=" + productId+"&userType="+usertype, true, function(result){
		var status=result.status;
		if(status){
			//alert("推广记录新增成功");
		}else{
			//alert("推广记录新增失败");
		}
	}, function(){})
}

/**
 * 加入我的店铺
 */
touch.on('#addStore','tap',function(){
	AJAX.call(rootPath+"/pbyt/product/addStoreProduct","post", "json", "productIds=" + productId , false, function(result){
		var Status=result.status;
		if(Status){
		  var code=result.code;
		  if(code=='0'){
			  bombbox="5";
		  }else{
		    bombbox="3"; 
		  }	
		}else{
			bombbox="4";	
			var dia=$.dialog({
	            content: result.Msg,
	            button: ["取消", '确定']
	        });
		}
	}, function(){})
	Bombbox();
});




