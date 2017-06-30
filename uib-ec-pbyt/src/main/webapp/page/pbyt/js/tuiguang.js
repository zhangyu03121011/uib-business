$(function(){
  AJAX.call(rootPath+"/ptyt/recommProduct/queryRecommProductList", "post", "json", "",true,function(result){
	var data=result.data;
	var array=[];
	if(data.length!=0){
		$.each(data,function(i,item){
			var createTime=item.createTime;
			var fullName=item.fullName;
			var viewCount=item.viewCount;
			var image=item.image;
			var num=item.num;
			var pice=item.pice;
			var productId=item.productId;
			if(pice==null){
				pice="0";
			}
			array.push("<div class='me-wrap'><div class='flex-row me-order tuiguang'><div class='flex-item'><span class='tg-shijian'>推广时间："+createTime+"</span></div></div>");
			array.push(" <a class='product-detail flex-row-top' href='./product-detail.html?id="+productId+"'>");	 
		    array.push("<img src='"+image+"' class='product'>");
		    array.push("<div class='flex-item detail'> <p class='title'>"+fullName+"</p>");      
   
		    array.push("<div class='flex-row price-nub'> <span class='flex-item price'>¥"+pice+"</span>");
		    array.push("<span class='flex-item number'></span></div></div></a>");
		    array.push("<div class='flex-row me-pay'><div class='flex-item visit-trade'>浏览量："+viewCount+"</div>");
		    array.push("<div class='flex-item visit-trade'>交易量："+num+"</div></div></div>");               
			})
			if(array){
				array = array.join(" ");
			}
			$("#addressListForm").html(array);
		}else{
			array.push("<div class='me-wrap' id='no-data' style='display: black;'>");
			array.push("   <div class='no-data'>") ;
	        array.push(" 	 <div class='no-data-logo'>"); 
	        array.push("		<span class='iconfont icon-dingdan1'></span></div>");   
	        array.push(" 			<p class='no-data-title'>您还没有相关的推广记录</p>");       
	        array.push("      		<p class='no-data-title-s'>赶快去推广吧</p>")	;	       
	        array.push("    </div>");   
	        array.push(" </div>");   
	        if(array){
				array = array.join(" ");
			}
			$("#addressListForm").html(array);
		}
	}, function(){})	
})