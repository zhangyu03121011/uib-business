$(function(){
	AJAX.call(rootPath+"/pbyt/recommend/init", "post", "json", "", true, function(result){
		   var data=result.data;
		   var Status=result.status;
		   var array=[];
		if(Status){			
			if(data){
				$.each(data,function(i,item){
					var size=data.length;
					var memberName=item.memberName;
					var avater=item.avater;
					var creatTime=item.creatTime;
					var orderStatus=item.orderStatus;
					var sellPice=item.sellPice;
					var image=item.image;
					var allPice=item.allPice;
					var fullName=item.fullName;
					var quantity=item.quantity;
					var goods=new Array();
					goods=item.goods;
					var allQuantity=item.allQuantity;
					var commission=item.commission;			
					array.push("<div class='order'>");
					array.push("<div class='custom flex-row'><img src='"+avater+"' class='portait'>");
					array.push("<div class='flex-item custom-right'>");
					array.push("<p class='name'>"+memberName+"</p><p class='date'>"+creatTime+"</p></div></div>");
					array.push("<div class='product-state flex-row'><div class='flex-item'><span class='product'>共"+allQuantity+"件商品</span></div><div class='flex-item align-right'><span class='state'>"+orderStatus+"</span></div></div>");
					if(goods.length>0 && goods!=null){
						for(var i=0;i<goods.length;i++){					
							array.push("<div class='product-detail flex-row-top'><img src='"+goods[i].image+"' class='product'>");
							array.push("<div class='flex-item detail'>");
							array.push("<p class='title'>"+goods[i].fullName+"</p><div class='flex-row price-nub'>");
							array.push("<span class='flex-item price'>¥"+goods[i].sellPice+"</span><span class='flex-item number'>X"+goods[i].quantity+"</span></div></div> </div>");
						}					
					}else{
						array.push("<div class='product-detail flex-row-top'><img src='"+goods.image+"' class='product'>");
						array.push("<div class='flex-item detail'>");
						array.push("<p class='title'>"+goods.fullName+"</p><div class='flex-row price-nub'>");
						array.push("<span class='flex-item price'>¥"+goods.sellPice+"</span><span class='flex-item number'>X"+goods.quantity+"</span></div></div> </div>");
					}
					
					array.push("<div class='flex-row payment-comm'><div class='flex-item payment'> 实付款："+allPice+"</div><div class='flex-item commission'>佣金：<span>￥"+commission+"</span></div></div></div>");		
				})
				if(array){
					array = array.join(" ");
				}
				$("#addressListForm").html(array);
			}
		}else{
			array.push("<div class='me-wrap' id='no-data' style='display: black;'>");
			array.push("   <div class='no-data'>") ;
	        array.push(" 	 <div class='no-data-logo'>"); 
	        array.push("		<span class='iconfont icon-dingdan1'></span></div>");   
	        array.push(" 			<p class='no-data-title'>您还没有相关的销售订单</p>");       
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