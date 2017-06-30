/* 首页频道图片效果 */
$(document).ready(function(){

$(".channer_img div").hover(function() {
	$(this).animate({"top": "-110px"}, 400, "swing");
},function() {
	$(this).stop(true,false).animate({"top": "0px"}, 400, "swing");
});

});
