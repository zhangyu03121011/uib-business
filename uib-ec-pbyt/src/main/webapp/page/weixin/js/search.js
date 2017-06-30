//	热门搜索
function skip(){
    var productCategoryName = $("#shousuo").val();
    location.href="searchResult.html?productCategoryName=" + encodeURIComponent(encodeURIComponent(productCategoryName));
}

function clearSeachHis(){
	$.get(rootPath + "/wechat/hotSearch/cookieRemove","",function(){
		//页面刷新
		 window.location.reload();
	});
}
/**
 * 加载页面
 */
$(document).ready(function(){
	//var cmsCategoryNo = URL_PARAM.getParam("cmsCategoryNo");
	$("#shousuo").focus();
	var count =20;
	$.get(rootPath + "/wechat/hotSearch/getHotSearchList?count=" + count,"",function(result){
		var keyword = result.data;
		var appendVar;
		if(null!=keyword){
			//热门搜索
			 $.each(keyword.hotSearchList, function(n, item) {
				 	var productCategoryName = item.keyword;
	            	appendVar = "<a href='searchResult.html?productCategoryName="+ encodeURIComponent(encodeURIComponent(productCategoryName)) +"'>"+item.keyword+"</a>";
					$("#keyword").append(appendVar);
				}); 
			 //历史搜索
			 var lastsearchArray = keyword.lastsearchArray;
			 if(lastsearchArray) {
				 $.each(keyword.lastsearchArray, function(n, item){
					//防止<script>标签注入
					item = item.replace("<script","<script type='text'");
					var appendHtml;
					appendHtml ="<a href='searchResult.html?productCategoryName="+ encodeURIComponent(encodeURIComponent(item)) +"'>"+item+"</a>";
					$("#cache").append(appendHtml);
				});
			 } else {
				 $("#cache").append("暂无搜索历史").css("text-align","center"); 
			 }
				
		}
	});
});


