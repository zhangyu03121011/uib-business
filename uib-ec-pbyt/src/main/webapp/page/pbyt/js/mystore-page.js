var obj = {};
obj.id = "";
obj.image = "";
obj.logo = "";
obj.name = "";
obj.explain = "";
obj.title ="";// 分享标题
obj.desc = "";// 分享描述
obj.imgUrl = "";// 分享图片
//obj.merchantId = "51680a8183764a5e99bcf819e7a855e4";
obj.merchantId = URL_PARAM.getParam("merchantNo");
obj.rankId=URL_PARAM.getParam("rankId");
obj.link = location.href.split('?')[0];



function isNull(value){
	if(value == "" || value == null)
		return true;
	return false;
}

obj.initData = function(){
	$.get("/f/store/queryStore", {merchantNo:obj.merchantId}, function(result){
		if(!isNull(result)){
			obj.id = result.id;
			obj.merchantId = result.merchantId;
			if(!isNull(result.image)){
				$("#bannerDiv").addClass("has-data-toggle").removeClass("no-data-toggle");
				$("#image").attr("src", result.image);
				obj.image = result.image;
			}
			if(!isNull(result.logo)){
				$("#storeInfo").addClass("has-data-toggle").removeClass("no-data-toggle");
				$("#logo").attr("src", result.logo);
				obj.logo = result.logo;
			}
			if(result.name!=null || result.name!=""){
				obj.title =result.name;
				$("#pageTitle").text(result.name);
			}else{
				obj.title="我的店铺";
				$("#pageTitle").text("我的店铺");
			}
			obj.desc = result.explain;
			obj.imgUrl =result.logo;

			$("#name").text(result.name);
			$("#explain").text(result.explain);
			obj.name = result.name;
			obj.explain = result.explain;
			
			obj.queryProduct();
			obj.querySpecial();
		}
		
		if(window.localStorage){
			if(obj.name==null){
				obj.name="";
			}
			if(obj.explain==null){
				obj.explain="";
			}
		    localStorage.id = obj.id;
			localStorage.logo=obj.logo;
			localStorage.name=obj.name;
			localStorage.image=obj.image;
			localStorage.explain=obj.explain;
		}
	})
	
}

obj.queryProduct = function(){
	$.get("/f/store/queryProduct/pageIndex/0/pageSize/4", {storeId: obj.id,rankId:obj.rankId}, function(result){
		if(result.length != 0){
			//obj.rankId = result[0].rankId;
			var str = "";
			$("#productInfo").addClass("has-data-toggle").removeClass("no-data-toggle");
			for(var i=0; i<result.length; i++){
				str += "<div class='product-box' onclick='obj.productDetails(\""+result[i].id+"\")'>";
				str += "	<img src='"+result[i].image+"'>";
				str += "	<p class='title-p'>"+result[i].fullName+"</p>";
				str += "	<p class='price-part'>";
				str += "    	<span class='price'>¥"+result[i].price+"</span>";
				str += "        <span class='market-price'>¥"+result[i].marketPrice+"</span>";
				str += "	</p>";
				str += "</div>";
			}
			$("#productData").empty();
			$("#productData").append(str);
		}
	})
}


obj.querySpecial = function(){
	$.get("/f/store/querySpecial1", {merchantNo:obj.merchantId}, function(result){
		if(result.length != 0){
			var str = "";
			$("#specialInfo").addClass("has-data-toggle").removeClass("no-data-toggle");
			for(var i=0; i<result.length; i++){
				str += "<div class='detail-body' onclick='obj.specialDetails(\""+result[i].id+"\")'>";
				str += "    <div class='pic'>";
				str += "        <img src='"+result[i].showImage+"'>";
				str += "    </div>";
				str += "    <div class='title'>";
				str += "        <h3 class='title-text'>"+result[i].specialTitle+"</h3>";
			//	str += "        <p class='more'>MORE>></p>";
				str += "    </div>";
				str += "    <div class='tips'>"+result[i].specialArticle+"</div>";
				str += "</div>";
			}
			$("#specialData").empty();
			$("#specialData").append(str);
		}
	})
}


obj.productDetails = function(id){
	location.href = "product-detail.html?id=" + id;
}
obj.specialDetails = function(id){
	location.href = "theme_detail.html?specialId=" + id;
}


obj.editBanner = function(){
	if(window.localStorage){
	    localStorage.id = obj.id;
		localStorage.logo=obj.logo;
		localStorage.name=obj.name;
		localStorage.image=obj.image;
		localStorage.explain=obj.explain;
	}
	location.href = "store-banner-edit.html";
}


obj.editStore = function(){
	if(window.localStorage){
	    localStorage.id = obj.id;
		localStorage.logo=obj.logo;
		localStorage.name=obj.name;
		localStorage.image=obj.image;
		localStorage.explain=obj.explain;
	}
	location.href = "shop-inf.html";
}

obj.editProduct = function(){
	location.href = "hot-product-manage-page.html?id="+ obj.id+"&rankId="+obj.rankId;
}
obj.addProduct= function(){
	location.href = "products_add.html?storeId="+ obj.id +"&name="+obj.name+"&rankId="+obj.rankId;
}
obj.addZhuanti = function(){
	location.href = "add-zhuanti.html?merchantNo="+ obj.merchantId+"&name="+obj.name+"&rankId="+obj.rankId;
}
obj.productMore = function(){
	location.href = "store_product_list.html?id="+ obj.id+"&rankId="+obj.rankId;
}
obj.addBanner = function(){
	location.href = "store-banner-edit.html"
}
obj.specialMore = function(){
	location.href = "zhuanti-manage-page.html?merchantNo="+obj.merchantId+"&rankId="+obj.rankId;
}
obj.editSpecial = function(){
	location.href = "zhuanti-manage-page.html?merchantNo="+obj.merchantId+"&rankId="+obj.rankId;
}


//微信分享我的店铺
function weixinShare(){
	var url = location.href.split('#')[0];
	url = url.replace(/\&/g, '%26');  
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
	        ],		 
		});
	});
	
	wx.ready(function () {
		//alert(obj.imgUrl);
		//分享到微信好友
		wx.onMenuShareAppMessage({
		    title:obj.title , // 分享标题
		    desc: obj.desc, // 分享描述
		    link: obj.link+"?merchantNo="+obj.merchantId+"&rankId="+obj.rankId, // 分享链接
		    imgUrl: obj.imgUrl, // 分享图标
		    type: '', // 分享类型,music、video或link，不填默认为link
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
		    title: obj.title, // 分享标题
		    link: obj.link+"?merchantNo="+obj.merchantId+"&rankId="+obj.rankId, // 分享链接
		    imgUrl: obj.imgUrl, // 分享图标
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
	
	wx.error(function (res) {
		//alert(res.errMsg);
	});
}


$(function(){
	var winH = window.innerHeight;
    touch.on('.go-top-btn','touchend',function (event) {
    	event.preventDefault();
         $('body,html').animate({scrollTop:0},300);


        });
	flag = URL_PARAM.getParam("flag");
	if(flag==1){
		/*编辑按钮*/
		touch.on('.right-text','tap',function(){
			$('.mystore-page').toggleClass('edit');
			$(this).html($(this).html()=='编辑'? '完成' :'编辑');
		})
		//添加专题
		$("#add-now-zhuanti").click(obj.addZhuanti);
		$("#add-now-banner").click(obj.addBanner);
		$("#add-now-product").click(obj.addProduct);
	}
	//查询会员等级
	 AJAX.call(rootPath+"/memMember/FlagMemMember","post","json", "", true, function(result){
		 var Status=result.status;
		 var data=result.data;
		 if(Status){			
			 obj.rankId=data.rankId;  				      		    
		 }
    }, function(){});
	
	
	obj.initData();
	
	//微信分享我的店铺
	weixinShare();
	
	$("#editBanner").click(obj.editBanner);
	$("#editStore1, #editStore2").click(obj.editStore);
	$("#editProduct1, #editProduct2").click(obj.editProduct);
	$("#editSpecial").click(obj.editSpecial);
	
	//更多商品
	$("#productMore").click(obj.productMore);
	$("#specialMore").click(obj.specialMore);
	
	$(document).on('scroll',function(){
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
