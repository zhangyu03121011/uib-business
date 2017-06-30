/***
 * 订单 - 优惠券
 */
var cartId = '';
var pid = '';
var _receiverId = '';
var _memo = '';
var _balanceFlag = '';
var balancePwd = '';

$(document).ready(
				function() {
					cartId = URL_PARAM.getParam('cartId');
					pid = URL_PARAM.getParam('pid');
					_receiverId = URL_PARAM.getParam('receiverId');
					_memo = URL_PARAM.getParam('memo');
					_balanceFlag = URL_PARAM.getParam('balanceFlag');
					balancePwd = URL_PARAM.getParam('balancePwd');

					$.ajax({url : rootPath
										+ "/wechat/member/order/queryCurUserCouponList?cartId=" + cartId
										+ "&pid=" + pid + "&receiverId="+ _receiverId,
								async : false,
								type : "POST",
								success : function(result) {
									if (!result.status) {
										Alert.disp_prompt(result.msg);
									} else {
										var coupon = result.data;

										$("#coupon_count").html("可用优惠券（" + coupon.couponCount + "）");
										$("#uncoupon_count").html("不可用优惠券（" + coupon.unCouponCount + "）");

										var html = '';
										if (!isNull(coupon.couponList)) {
											$.each(coupon.couponList,function(index, item) {
												html += '<div class="pepper-w" onclick=clickCouponItem("'+item.couponCode+'")>';
												html += '<input type="hidden" id="item_price_'+item.couponCode+'" value="'+item.facePrice+'"/>';
												html += '<div class="pepper pepper-blue">';
												html += '<div class="pepper-l">';
												html += '<p class="pepper-l-num">';
												//html += '<i>¥</i><span>'+item.facePrice+'</span><i>.00</i>';
												if(item.facePrice.toString().indexOf('.') > -1){
													var f = item.facePrice.toString().split(".");
													html += '<i>¥</i><span>'+f[0]+'</span><i>.'+f[1]+'</i>';
												}else{
													html += '<span>'+formatPrice(item.facePrice)+'</span>';
												}
												
												if(item.needConsumeBalance > 0){
													html += '(满'+item.needConsumeBalance+'可用)';
												}
												html += '</p>';
												
												html += '<p class="pepper-l-con">使用范围：通用</p>';
												html += '</div>';
												html += '<div class="pepper-r">';
												html += '<span>有效期：</span>';
												html += '<div>';
												html += item.beginDate;
												html += '<p class="pepper-line">~</p>';
												html += item.endDate;
												html += '</div>';
												html += '</div>';
												html += '</div>';
												html += '<div class="pepper-b pepper-blue">';
												html += '<div class="pb-con"></div>';
												html += '<div class="pb-border"></div>';
												html += '</div>	';
												html += '</div>';
											});
											$("#coupon_list_div").append(html);
										}
										
										var html2 = '';
										if (!isNull(result.data.unCouponList)) {
											$.each(coupon.unCouponList,function(index, item) {
												html2 += '<div class="pepper-w">';
												html2 += '<div class="pepper pepper-gary">';
												html2 += '<div class="pepper-l">';
												html2 += '<p class="pepper-l-num">';
												if(item.facePrice.toString().indexOf('.') > -1){
													var f = item.facePrice.toString().split(".");
													html2 += '<i>¥</i><span>'+f[0]+'</span><i>.'+f[1]+'</i>';
												}else{
													html2 += '<span>'+formatPrice(item.facePrice)+'</span>';
												}
												
												if(item.needConsumeBalance > 0){
													html2 += '(满'+item.needConsumeBalance+'可用)';
												}
												html2 += '</p>';
												
												html2 += '<p class="pepper-l-con">使用范围：通用</p>';
												html2 += '</div>';
												html2 += '<div class="pepper-r">';
												html2 += '<span>有效期：</span>';
												html2 += '<div>';
												html2 += item.beginDate;
												html2 += '<p class="pepper-line">~</p>';
												html2 += item.endDate;
												html2 += '</div>';
												html2 += '</div>';
												html2 += '</div>';
												html2 += '<div class="pepper-b pepper-gary">';
												html2 += '<div class="pb-con"></div>';
												html2 += '<div class="pb-border"></div>';
												html2 += '</div>	';
												html2 += '</div>';
											});
											$("#uncoupon_list_div").append(html2);
										}
									}
								}
							});
				});

function clickCouponItem(couponCode){
	var facePrice = $("#item_price_"+couponCode).val();
	window.location.href = "order-confirm.html?cartId="+cartId+"&isInvoice=0&paymentMethod=1&pid="+pid+"&receiverId="+_receiverId+"&couponCode="+couponCode+"&facePrice="+facePrice+"&memo="+_memo+"&balanceFlag="+_balanceFlag+"&balancePwd="+balancePwd;
}
