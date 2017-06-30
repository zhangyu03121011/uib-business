/**
 * 商品列表页面
 */
var pageIndex = 0;
var pageSize = 6;
var isLoad = true;//是否需要加载(到最后一页时,禁止滚动加载)
var isRequest = false;//是否正在请求(正在请求时,禁止滚动加载)
var productCategoryId="";
var productCategoryName="";
var storeId = URL_PARAM.getParam("storeId");
var rankId = URL_PARAM.getParam("rankId");
//var storeId = '43c895d391fc46619a5e366c5c60d9a8'
var pids = new Array();

Array.prototype.indexOf = function(val) { for (var i = 0; i < this.length; i++) { if (this[i] == val) return i; } return -1; };
Array.prototype.remove = function(val) { var index = this.indexOf(val); if (index > -1) { this.splice(index, 1); } };
	
 $(document).ready(function(){
	 //查询前4条商品分类
	 findProductCategoryList();
	 //根据参数查询全部商品或分类查询商品
	 findProductList("","","",0,6,storeId);	
	 
	 
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
        					for(var i=0; i<data.length; i++){
        						appendHtml +=" <div class='pure pure-u-1-4 typeDate'><a href='javascript:void(0)' onclick=findProductList('"+data[i].id+"','"+data[i].name+"','',0,6,'')>"+data[i].name+"</a></div>";
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
	 
//	 //搜索商品信息
//	 touch.on('.iconfont.icon-icon-search', 'touchstart', function(event) {
//		 productCategoryName = $("#searchProduct").val();
//		 if(productCategoryName!=null && productCategoryName!=""){
//			 findProductList("","",productCategoryName,0,6,storeId);
//		 }
//	 });
	 
	 //搜索商品信息
	 touch.on('.iconfont.icon-icon-search', 'touchstart', function(event) {
		 productCategoryName = $("#searchProduct").val();
		 //if(productCategoryName!=null && productCategoryName!=""){
			 findProductList("","",productCategoryName,0,6,storeId);
		// }
		 $("#searchProduct").blur();  
	 });  
	 
	 $("#searchProduct").keypress(function(e) {
		 var self = this;
         if (event.keyCode == "13") {//keyCode=13是回车键
        	 productCategoryName = $("#searchProduct").val();
    		// if(productCategoryName!=null && productCategoryName!=""){
    			 findProductList("","",productCategoryName,0,6,storeId);
    		// } 
    		 e.preventDefault();
    		 self.blur(); 
         }
     });
	 
	 
	 
	 touch.on("#allcategory",'touchstart', function(event) {
		 findProductList("","","",0,6,storeId); 
	 });
	 
	 touch.on("#allCategoryList",'touchstart', function(event) {
		 findProductList("","","",0,6,storeId); 
	 });
	 //添加商品到我的店铺
	 touch.on('.right-text', 'touchstart', function(event) {		
		 if(isNotEmpty(pids)){
			 AJAX.call( rootPath + "/pbyt/product/addStoreProduct?productIds="+pids.join(",")+"&storeId="+storeId+"&rankId="+rankId, "post", "json", "", true, function(result){
				 if(result.status){
					 	Alert.disp_prompt("添加成功！");
					 	//window.location.href = "/page/pbyt/hot-product-manage-page.html?id="+storeId;
					} else {
						Alert.disp_prompt("添加失败！");
					}
				 window.location.href = "/page/pbyt/myStore-page.html?id="+storeId+"&rankId="+rankId+"&flag="+1;
			 });
		 }
	 });

 });
 
//滚动分页
 $(window).scroll(function(){
 	var scrollTop = $(this).scrollTop();
 	var scrollHeight = $(document).height();
 	var windowHeight = $(this).height();
 	if(scrollTop/(scrollHeight-windowHeight) >= 0.95 && isRequest == false && isLoad == true){	 		
 		$(".scrimg").show();
 		pageIndex += pageSize;
 		findProductList(productCategoryId,"",productCategoryName,pageIndex,pageSize,storeId);
 	}
 });
 
//查询前4条商品分类
 function findProductCategoryList(){
	 AJAX.call( rootPath + "/ptyt/prodHome/findProductCategoryList?grade="+1, "post", "json", "", false, function(result){
		 var appendTitle;
		 var data = result.data;
			if(result.status){
				if(!isNull(data)){
					$("#product-title div.typeDate").remove();
					for(var i=0; i<data.length; i++){
							appendTitle ="<div class='swiper-slide typeDate'><a  id='product"+data[i].id+"'  class='' href='javascript:void(0)' onclick=findProductList('"+data[i].id+"','"+data[i].name+"','',0,6,'')>"+data[i].name+"</a></div> ";
							$("#category-title").append(appendTitle);
							smPicSwiper();
					}      					
				}
			}
	 });
 }	
 

//根据参数查询全部商品或分类查询商品
 function findProductList(productId,name,productCategoryName,pageIndex,pageSize,storeId){
	 $("#searchProduct").attr("value",name);
	 productCategoryId =productId; 	 
	 isRequest = true;
	 $(".category-list").hide();
     $(this).removeClass("icon-arrow-up").addClass("icon-arrow-down");
     $("#allcategory").text("全部");
     
     //查询前4条商品分类
     findProductCategoryList();
     $(".hover").removeClass("hover");
     var $tab_id = (productId!=null && productId !="")?$("#product"+productCategoryId):$("#product");
     $tab_id.toggleClass("hover");
	 AJAX.call( rootPath + "/ptyt/prodHome/findProductList1/pageIndex/{0}/pageSize/{1}".format(pageIndex,pageSize), "post", "json", {productCategoryId: productCategoryId, productCategoryName: productCategoryName,storeId:storeId}, false, function(result){
		console.info(result);
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
					 $(".scrimg").hide();
					//$(".endli").show();
				}else{
					$("#no-data").hide();
					for(var i = 0; i<result.data.length; i++){
						appendHtml +="<div class='pure pure-u-1-2' onclick='sel(\""+result.data[i].id+"\")'>";
						appendHtml +="<div class='products-wrap' id='"+result.data[i].id+"'>";
						appendHtml +="<img src='"+result.data[i].image+"'>";
						appendHtml +="<p class='title'>"+result.data[i].fullName+"</p>";
						appendHtml +="<p><span class='price'>¥"+result.data[i].sell_price+"</span><span class='promotion'>¥"+result.data[i].marketPrice+"</span></p>";
						appendHtml +="</div>";
						appendHtml +="</div>";
					}
					$("#productList").append(appendHtml);
					isRequest = false;
					if(result.data.length < pageSize){
		 				isLoad = false;
		 				$(".scrimg").hide();
		 			}
				}
				
			}
			
			
		});
	
 }	

 
 function sel(id){
	 $("#"+id).toggleClass('add-select');
	 if($("#"+id).hasClass("add-select")){
		 pids.push(id);
	 }else{
		 pids.remove(id);
	 }	 
 }
 
 function smPicSwiper(){
	 var smPicSwiper = new Swiper('.swiper-catogory', {
         loop: false,
         slidesPerView: 3.5,
         freeMode: true
     });
 }
 
