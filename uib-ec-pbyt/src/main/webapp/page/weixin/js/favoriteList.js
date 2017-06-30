/***
 * 商品收藏
 */

$(document).ready(function(){
	$.ajax({  
	    url : rootPath + "/wechat/member/favorite/myFavorite",  
        async : false, 
        type : "POST",  
        success : function(result) {
        	if(result.status){
        		var _html = '';
        		_html += ' <ul>';
        		$.each(result.data, function(index, item){
        			_html += '<li>';
        			_html += '<div class="wbox">';
        			_html += '<div class="img"><a href="products.html?id='+ item.productId + '" data-ajax="false"><img src="'+item.image+'"/></a></div>';
        			_html += '<div class="wbox-flex">';
        			_html += '<a href="products.html?id='+ item.productId + '" data-ajax="false">';
        			_html += '<p class="collect-name">'+item.productName+'</p></a>';
        			_html += '<div class="collect-price"><span class="fl">'+formatPrice(item.price)+'</span><font class="del">'+formatPrice(item.marketPrice)+'</font></div>';
        			_html += '<div class="collect-btn-box"><a class="jion-cat" href="javascript:void(0)" onclick=addCart("'+item.productId+'")>加入购物车</a><a class="cancel-coll" href="javascript:void(0)" onclick=cancelFavorite("'+item.id+'")>取消收藏</a></div>';
    				_html += '</div>';
        			_html += '</div>';
        			_html += '</li>';
        			
        		});
        		_html += '</ul>';
        		$("#favorite_div").append(_html);
        	}else{
        		Alert.disp_prompt(result.msg);
        	}
        }  
    });
});

/***
 * 加入购物车
 * @param productId
 */
function addCart(productId){
	$.get(rootPath + "/wechat/user/ticket/state/",{},function(data){
		if(data == '0'){
			window.location.href="login.html?locationParm=jump";
			return;
		}else{
			if(isNotEmpty(productId)){
				AJAX.call( rootPath + "/wechat/cart/addCart?productId=" + productId +"&quantity=1", 
						  "post", "json", "", true, 
						  function(result){
								if(result.status){
									Alert.disp_prompt("添加成功");
								}else{
									Alert.disp_prompt("添加失败：" + result.msg);
								}
					      }, function(){});
			}
		}
	});
}

/***
 * 取消收藏
 * @param favoriteId
 */
function cancelFavorite(favoriteId){
	if(confirm("确定取消收藏吗？")){
		$.get(rootPath + "/wechat/user/ticket/state/",{},function(data){
			if(data == '0'){
				window.location.href="login.html?locationParm=jump";
				return;
			}else{
				if(isNotEmpty(favoriteId)){
					AJAX.call( rootPath + "/wechat/member/favorite/delete?ids=" + favoriteId, 
							  "post", "json", "", true, 
							  function(result){
									if(result.status){
										Alert.disp_prompt("取消收藏成功");
										
						        		setTimeout(function(){
						        			location.href = "favoriteList.html";
						        		},2000);
									}else{
										Alert.disp_prompt("取消收藏失败：" + result.msg);
									}
						      }, function(){});
				}
			}
		});
	}
}