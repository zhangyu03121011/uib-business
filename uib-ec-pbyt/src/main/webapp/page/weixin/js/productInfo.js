/**
 * 初始化页面
 */
function initData(){
	var productId = URL_PARAM.getParam("productId");
	AJAX.call( "/f/wechat/product/initProductInfo?productId=" + productId, "post", "json", "", true, function(result){
        if(!isNull(result)){
        	var product = result.product;
            var paramlist = result.paramlist;
            var propList = result.propList;
            $("#tagContent0").append(product.introduction);
            $.each(paramlist, function(n, value) {
				$("#parmBody").append("<tr><td>"+value.name+"</td><td>"+value.parameterValue+"</td></tr>");
			}); 
            
            $.each(propList, function(n, prop) {
				$("#propBody").append("<tr><td>"+prop.groupName+"</td><td>"+prop.propertyName+"</td></tr>");
			}); 
        }
	}, function(){});
}

$(document).ready(function(){
	
	$(".tab-bar-item").click(function(){
		$(".tab-bar-item").removeClass("hover");
		$(this).addClass("hover");
		$("#tagContent .tab-group").hide();
		$("#tagContent .tab-group").eq($(this).index()).show();
	});
	
	initData();
	
	
});