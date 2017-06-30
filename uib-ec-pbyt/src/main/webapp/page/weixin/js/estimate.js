var notEnd0 = true;
var notEnd1 = true;
var notEnd2 = true;
var isApend = true;
var productId;


/**
 * 初始化页面
 */
function initData(){
	productId = URL_PARAM.getParam("productId");
	AJAX.call( "/f/wechat/comment/userEstimateByPage?productId=" + productId, "post", "json", "", true, function(result){
        if(!isNull(result)){
        	var avgCore = result.avgCore;
            var hpList = result.hpList;
            var zpList = result.zpList;
            var cpList = result.cpList;
            $("#hp-href").text("好评（" + avgCore.hp + "）");
            $("#zp-href").text("中评（" + avgCore.zp + "）");
            $("#cp-href").text("差评（" + avgCore.cp + "）");
    		var scoreHtml = "<div class='left'>" + avgCore.zongp + "</div>"
    					  + "<div class='com'>"
    					  +		"<p>好评率：" + avgCore.hpRate + "%</p>"
    					  +		"<p>" + avgCore.all + "人评价</p>"
    					  + "</div>"
    					  + "<div class='right'>"
    					  + 	"<div class='starBar pr fl'>"
    					  +			"<div class='starBarMask' style='" + avgCore.scorePercent + "'></div>"
    					  + 	"</div>"
    					  + "</div>"
    		$("#score-div").append(scoreHtml);
    		appendComment("#hp-div",hpList);
    		appendComment("#zp-div",zpList);
    		appendComment("#cp-div",cpList);
        }
	}, function(){});
}

/**
 * 追加评论
 * @param target 追加对象
 * @param data   评论数据
 */
function appendComment(target,data){
    var appendHtml;
    if(!isNull(data)){
    	for(var index in data){
    		appendHtml= "<div class='com-eval'>"
    				  + 	"<div class='score-time'>"
    				  + 		"<span>" + data[index].createTime+ "</span>"
    				  +			"<font class='fl'>" + data[index].userName + "</font>"
    				  + 		"<div class='starBar pr fl'>"
    				  + 			"<div class='starBarMask' style='"+ data[index].scorePercent +"'/>"
    				  + 		"</div>"
    				  + 	"</div>"
    				  + 	"<div class='score-con'>" + data[index].content + "</div>"
    				  + "</div>"
    		
    		$(target).append(appendHtml);
    	};
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
	
	initData();
	
	//页面滑动加载数据
	$(document).on("scrollstart",function(){
		var scrollImg = "<div class='scrimg'><p calss='name' align='center'>正在努力加载数据...</p></div>";
		var endDiv = "<div class='endDiv'><p calss='name' align='center'>没有更多数据</p></div>";
	  	//好评翻页
		if($("#menu-index").val() == 0 && notEnd0){
	  		var page = Number($("#hp-page").val()) + 1;
			$("#hp-page").val(page);
			if(isApend){
				$("#hp-div").append(scrollImg);
				isApend = false;
			}
			//分页加载商品
			AJAX.call( "/f/wechat/comment/queryCommentByPage?productId=" + productId + "&page=" + page + "&flag=3", "post", "json", "", true, function(result){
				isApend = true;
				$("div").remove(".scrimg");
				if(isNull(result)){
					notEnd0 = false;
					$("#hp-div").append(endDiv);
				}else{
					appendComment("#hp-div",result);
				}
			}, function(){});
	  	}
		
		//中评翻页
		if($("#menu-index").val() == 1 && notEnd1){
	  		var page = Number($("#zp-page").val()) + 1;
			$("#zp-page").val(page);
			if(isApend){
				$("#zp-div").append(scrollImg);
				isApend = false;
			}
			//分页加载商品
			AJAX.call( "/f/wechat/comment/queryCommentByPage?productId=" + productId + "&page=" + page + "&flag=2", "post", "json", "", true, function(result){
				isApend = true;
				$("div").remove(".scrimg");
				if(isNull(result)){
					notEnd1 = false;
					$("#zp-div").append(endDiv);
				}else{
					appendComment("#zp-div",result);
				}
			}, function(){});
	  	}
		
		//差评翻页
		if($("#menu-index").val() == 2 && notEnd2){
	  		var page = Number($("#cp-page").val()) + 1;
			$("#cp-page").val(page);
			if(isApend){
				$("#cp-div").append(scrollImg);
				isApend = false;
			}
			//分页加载商品
			AJAX.call( "/f/wechat/comment/queryCommentByPage?productId=" + productId + "&page=" + page + "&flag=1", "post", "json", "", true, function(result){
				isApend = true;
				$("div").remove(".scrimg");
				if(isNull(result)){
					notEnd2 = false;
					$("#cp-div").append(endDiv);
				}else{
					appendComment("#cp-div",result);
				}
			}, function(){});
	  	}
	});
	
});