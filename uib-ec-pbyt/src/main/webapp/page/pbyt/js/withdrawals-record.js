/**
 *提现记录 
*/
var notEnd0 = true;
$(document).ready(function(){
	upDownTouch();
	//滚动条滚动到底部自动加载数据
	/* $(window).scroll(function () {
	        var scrollTop = $(this).scrollTop();
	        var scrollHeight = $(document).height();
	        var windowHeight = $(this).height();
	        if (scrollTop + windowHeight == scrollHeight) {
	        	upDownTouch();
	        }
	 });*/
	document.addEventListener("touchmove", upDownTouch, false);
});
/**
 * 上下滑动分页
 */
function upDownTouch(){
		// var scrollImg = "<li class='scrimg'><p calss='name' align='center'>正在努力加载数据...</p></li>";
	       var scrollImg = "<li class='scrimg'><p class='no-more-data'>正在努力加载数据...</p></li>";
	      if(notEnd0){
	      		var page = Number($("#all-withdrawal-page").val()) + 1;
	  			$("#all-withdrawal-page").val(page);
	  			$("#details").append(scrollImg);
	  			getWithdrawalDetail(rootPath+"/draRecord/queryDraRecord?applyStatus=2&page="+page,$("#details"));
	  			
	      }
}


function getWithdrawalDetail(url, divshow){	
	AJAX.call( url, "post", "json", "", true, function(result){
		var data = result.data;
		var appendHtml;
		var array=[];
		//删除等待图片
		$("li").remove(".scrimg");
		//当查询结果为空时，说明没有下一页了，设置结尾标识
		if(isNull(data)){
			array.push("<div class='me-wrap' id='no-data' style='display: black;'>");
			array.push("   <div class='no-data'>") ;
	        array.push(" 	 <div class='no-data-logo'>"); 
	        array.push("		<span class='iconfont icon-dingdan1'></span></div>");   
	        array.push(" 			<p class='no-data-title'>您还没有相关的提现记录</p>");       
	        array.push("    </div>");   
	        array.push(" </div>");   
	        if(array){
				array = array.join(" ");
			}
	        divshow.html(array);		
			//var endli = "<li class='endli'><p calss='name' align='center'>没有更多数据</p></li>";
			var endli = "<p class='no-more-data'>没有更多数据</p>";
			if(notEnd0){
				notEnd0 = false;
				divshow.append(endli);
			};	
		}else{
			 $.each(data, function(n, value) {
	    		 var amount=formatPrice(value.applyAmount);
	    		 appendHtml="<li>"+
			                    "<div class='information'>"+
				                    "<p class='name'>"+value.applyUserName+"</p>"+
				                    "<p class='date'>"+value.strApplyDate+"</p>"+
			                    "</div>"+
			                    "<span>-"+amount.substring(1, amount.length)+"</span>"+
		                    "</li>";
	    		 divshow.append(appendHtml);
	    	});
		}
	});
}