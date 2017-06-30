/**
 * 商品列表页面
 */
var pageIndex = 0;
var pageSize = 6;
var isLoad = true;//是否需要加载(到最后一页时,禁止滚动加载)
var isRequest = false;//是否正在请求(正在请求时,禁止滚动加载)
var productCategoryId="";
var productCategoryName="";
var categoryName="";

 $(document).ready(function(){
	 
	 //查询前4条商品分类
	 findProductCategoryList();
	 //根据参数查询全部商品或分类查询商品
	 findProductList("","","",0,6,"");	
	 //置顶
	 var winH = window.innerHeight;
	    touch.on('.go-top-btn','touchend',function (event) {
	    	 event.preventDefault();
	         $('body,html').animate({scrollTop:0},300);


	        });
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
	 
	    
	    
	 touch.on('#categoryShow', 'touchstart', function(event) {
		
         if ($(this).hasClass('icon-arrow-down')) {
             $(".category-list").show();
             $(this).removeClass("icon-arrow-down").addClass("icon-arrow-up");
             //查询全部商品分类
        	 AJAX.call( rootPath + "/ptyt/prodHome/findProductCategoryList?grade="+1, "post", "json", "", true, function(result){
        		 var appendHtml="";
        		 var appendTitle;
        		 var data = result.data;
        			if(result.status){
        				$("#product-list div.typeDate").remove();
        				$("#product-title div.typeDate").remove();
        				$("#allcategory").text("全部分类")
        				if(!isNull(data)){
//        					for(var index in data){
        					for(var i=0; i<data.length; i++){
        						appendHtml +=" <div class='pure pure-u-1-4 typeDate'><a href='javascript:void(0)' onclick=findProductListBycatagory('"+data[i].id+"','"+data[i].name+"')>"+data[i].name+"</a></div>";
        					}
        					$("#category-list").after(appendHtml);
        				}
        			}
        	 });
         } else {
             $(".category-list").hide();
             $(this).removeClass("icon-arrow-up").addClass("icon-arrow-down");
             $("#allcategory").text("全部");
             findProductCategoryList();
         }
     }); 
	 
	 //搜索商品信息
	 touch.on('.search-btn', 'touchstart', function(event) {
		 productCategoryName = $("#searchProduct").val();
		// categoryName =productCategoryName;
		 findProductListBycatagory("","",productCategoryName);
		 $("#searchProduct").blur();  
	 });  
	 
	 $("#searchProduct").keypress(function(e) {
		 var self = this;
         if (event.keyCode == "13") {//keyCode=13是回车键
        	 productCategoryName = $("#searchProduct").val();
        	// categoryName =productCategoryName;
    		 findProductListBycatagory("","",productCategoryName);
    		 e.preventDefault();
    		 self.blur(); 
         }
     });
         
	 touch.on('#backbtn', 'touchstart', function(event) {
    	 if($("#searchProduct").val()!=null && $("#searchProduct").val()!=""){
    		 $("#searchProduct").val("");
    		 findProductListBycatagory('','','');
    	 }else{
    		 window.history.back(-1); 
    	 }
    	 
     });
	 
	
	/* touch.on('.products-wrap', 'tap', function(event) {
         $(event.currentTarget).toggleClass('add-select');
     });*/
	 
	//滚动分页
	 $(window).scroll(function(){
	 	var scrollTop = $(this).scrollTop();
	 	var scrollHeight = $(document).height();
	 	var windowHeight = $(this).height();
	 	if(scrollTop/(scrollHeight-windowHeight) >= 0.95 && isRequest == false && isLoad == true){	 		
	 		$(".scrimg").show();
	 		pageIndex += pageSize;
	 		findProductList(productCategoryId,categoryName,productCategoryName,pageIndex,pageSize,"");
	 	};
	 });
	 
 });
 
 
 
 //查询商品分类
 function findProductCategoryList(){
	 AJAX.call( rootPath + "/ptyt/prodHome/findProductCategoryList?grade="+1, "post", "json", "", false, function(result){
		 var appendTitle;
		 var data = result.data;
			if(result.status){
				if(!isNull(data)){
					$("#product-title div.typeDate").remove();
					for(var i=0; i<data.length; i++){
							appendTitle ="<div class='swiper-slide typeDate'><a id='product"+data[i].id+"'  class='' href='#'  onclick=findProductListBycatagory('"+data[i].id+"','"+data[i].name+"')>"+data[i].name+"</a></div> ";
							$("#category-title").append(appendTitle);
							smPicSwiper();
					}      					
				}
			}
	 });
 }	
 
 /**
  * 根据分类名称筛选商品
  */
 function findProductListBycatagory(productId,name,productCategoryName1) {
	 $(".scrimg").hide();
	  $(".scrimg .load_tip").text("正在努力加载数据...");	 
	 pageIndex = 0;
	 pageSize = 6;
	 isLoad = true;//是否需要加载(到最后一页时,禁止滚动加载)
	 isRequest = false;//是否正在请求(正在请求时,禁止滚动加载)
	 findProductList(productId,name,productCategoryName1,pageIndex,pageSize);
 }

//根据参数查询全部商品或分类查询商品
 function findProductList(productId,name,productCategoryName1,pageIndex,pageSize,storeId){
	 console.info(pageIndex+"==="+pageSize);
	 categoryName =name;
	 productCategoryName=productCategoryName1;
	 if(name!=null&&name !=""){
		 $("#searchProduct").val(name);
	 }else if(productCategoryName!=null&&productCategoryName !=""){
		 $("#searchProduct").val(productCategoryName);
	 }else{
		 $("#searchProduct").val("");
	 }
	//$("#searchProduct").val(productCategoryName);
	// $("#searchProduct").attr("value",name);
	 productCategoryId =productId; 	 
	 $(".category-list").hide();
     $(this).removeClass("icon-arrow-up").addClass("icon-arrow-down");
     $("#allcategory").text("全部");
     
     //查询前4条商品分类
     findProductCategoryList();
//     $(".hover").removeClass("hover");
//     var $tab_id = (productId!=null && productId !="")?$("#product"+productCategoryId):$("#product");
     //$tab_id.toggleClass("hover");
	 AJAX.call( rootPath + "/ptyt/prodHome/findProductList/pageIndex/{0}/pageSize/{1}".format(pageIndex,pageSize), "post", "json", {productCategoryId: productCategoryId, productCategoryName: productCategoryName,storeId:''}, false, function(result){
		 	var appendHtml="";
			if(result.status){
				if(pageIndex == 0){
					$("#productList").empty();
	 			}
				if(result.data.length == 0){
					var pro = $("#productList").html();
					if(pro==null || pro.length==0){
						$("#no-data").show();
					}
					//$(".endli").show();
//					$(".scrimg").hide();					
				}else{
					$("#no-data").hide();
					for(var i = 0; i<result.data.length; i++){
						appendHtml +="<div class='pure pure-u-1-2' onclick='productDetail(\""+result.data[i].id+"\")'>";
						appendHtml +="<div class='products-wrap'>";
						appendHtml +=" <img src='"+result.data[i].image+"'>";
						appendHtml +="<p class='title'>"+result.data[i].fullName+"</p>";
						appendHtml +="<p><span class='price'>¥"+result.data[i].sell_price+"</span><span class='promotion'>¥"+result.data[i].marketPrice+"</span></p>";
						appendHtml +=" </div>";
						appendHtml +=" </div>";
					}
					$("#productList").append(appendHtml);
					
					if(pageIndex != 0 && result.data.length <= pageSize){
		 				isLoad = false;
		 				$(".scrimg .load_tip").text("没有更多数据");
		 			}
				}
			}
			
		});
 }	

 function productDetail(id){
	 location.href = "/page/pbyt/product-detail.html?id="+id;
 }

 
 function smPicSwiper(){
	 var smPicSwiper = new Swiper('.swiper-catogory', {
         loop: false,
         slidesPerView: 3.5,
         freeMode: true
     });
 }