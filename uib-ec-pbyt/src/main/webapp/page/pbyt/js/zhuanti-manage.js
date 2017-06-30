var isApend = true;
var isTop = false;
var notEnd0 = true;
var obj={};

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
	  				getSpeciaList(rootPath+"/ptyt/special/findSpecialUserId?page="+page,$("#all-product-ul"));
	  			}
	      }
}

obj.addZhuanti = function(){
	location.href = "add-zhuanti.html?merchantNo="+obj.merchantNo+"&rankId="+obj.rankId;
}
obj.manageZhuanti = function(){
	location.href = "zhuanti-manage.html?merchantNo="+obj.merchantNo+"&rankId="+obj.rankId;
}

/**
 * 查询我的专题信息
 * @param url
 * @param divshow
 */
function getSpeciaList(url, divshow){
	
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
			var endli = "<li class='endli' id ='endli'><p calss='name' align='center'>没有更多数据</p></li>";
			if($("#menu-index").val() == 0 && notEnd0){
				notEnd0 = false;
				$("#all-product-ul").append(endli);
			};	
		}else{	
			for(var index in data){
				
				appendHtml ="<div class='detail-body'>"
						   +	"<a class='edit-label'>X</a>"
						   +	"<div class='pic'>"
						   +		"<img src='"+data[index].showImage+"'>"
						   +	"</div>"
					   	   +	"<div class='title'>"
					   	   +		"<h3 class='title-text'>"+data[index].specialTitle+"</h3>"
			   	   		   +		"<p class='more'>MORE>></p>"
			   	   		   +	"</div>"
			   	   		   +	"<div class='tips'>"+data[index].specialArticle+"</div>"
			   	   		   +"</div>";
				divshow.append(appendHtml);
			};
		};
	}, function(){});
}
$(function(){
	obj.merchantNo = URL_PARAM.getParam("merchantNo");
	obj.rankId = URL_PARAM.getParam("rankId");
	$("#all-product-ul").empty();

	 //加载我的专题信息
	getSpeciaList(rootPath+"/ptyt/special/findSpecialUserId?page=1",$("#all-product-ul"));
	$(document).on('scroll',function(){
		//分页加载
		upDownTouch();
	});
	
	$("#addZhuanti").click(obj.addZhuanti);
	$("#manageZhuanti").click(obj.manageZhuanti);
})