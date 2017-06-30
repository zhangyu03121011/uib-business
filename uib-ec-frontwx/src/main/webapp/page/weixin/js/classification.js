$(document).ready(function(){
	//加载所有商品
	getFistClassification(rootPath + "/wechat/productCategory/getCategoryByMeridAndParentId",$("#first_classification"));
	
/*	$("#searchBox").on("click",function() {
		window.location.href="searchResult.html";
	}); 
	*/
	/**
	 * 查询一级分类
	 * @param url
	 * @param divshow
	 */
	function getFistClassification(url, divshow){
		
		AJAX.call( url, "post", "json", "parentId=0", true, function(result){
			var data = result.data;
			var appendHtml = [];
			if(!isNull(data)){
				appendHtml.push('<li><a href="productList.html">全部分类</a></li>');
				$.each(data,function(i,item){
					appendHtml.push("<li id="+item.id+"><a href='javascript:void(0)' onclick=getSecondClassificationByCategoryId('"+item.id+"','"+item.name+"')>"+item.name+"</a></li>");
				});
				var html = appendHtml.join("");
				divshow.html(html);
			}
		}, function(){});
	}
});

//查询分类二级目录
function getSecondClassificationByCategoryId(id,name) {
	//添加选中样式
	$("#first_classification li").removeClass("hover");
	$("#"+id).addClass("hover");
	var url = rootPath + "/wechat/productCategory/query/subcategory";
	AJAX.call( url, "post", "json", "parentId="+id, true, function(result){
		var data = result.data;
		$("#second_classification").html("");
		var appendHtml = [];
		//当查询结果为空时，说明没有下一页了，设置结尾标识
		if(!isNull(data)){
			$("#second_classification").append('<div class="list-title" onclick=toProductList("'+id+'","'+1+'")>'+name+'&nbsp;&gt;</div>');
			$.each(data,function(i,item){
				appendHtml.push('<div class="mal-title" onclick=toProductList("'+item.id+'","'+2+'")><b></b>'+item.name+'</div>');
				appendHtml.push('<div id="three_classification'+item.id+'"></div>');
				$("#second_classification").append(appendHtml.join(""));
				appendHtml=[];
				if(item.subCategorys) {
					getThreeClassificationByCategoryId(item.subCategorys,item.id);
				}
			});
		}
	}, function(){});
}

//查询分类三级目录
function getThreeClassificationByCategoryId(result,id) {
		var data = result;
		var appendHtml = [];
		//当查询结果为空时，说明没有下一页了，设置结尾标识
		if(!isNull(data)){
			var html = '<div class="list-cp">';
			html += '<ul>';
			$.each(data,function(i,item){
				 html +=	'<li>';
				 html +=   	'<a href=productList.html?productCategoryId='+item.id+'>';
				 html +=            '<img src="'+item.imagePath+'"/>';
				 html +=             '<p>'+item.name+'</p>';
				 html +=         '</a>';
				 html +=      '</li>';
			});
			html +=  '</ul>';
			html +='</div>';
			appendHtml.push(html);
			$("#three_classification"+id).html(appendHtml.join());
		}
}

function toProductList(productCategoryId,level) {
	window.location.href = "productList.html?productCategoryId="+productCategoryId+"&level="+level;
}

function hideOrShow(id) {
	$("#three_classification"+id).toggle(500);
}