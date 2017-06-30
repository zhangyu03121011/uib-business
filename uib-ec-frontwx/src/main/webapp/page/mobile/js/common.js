var AJAX = {
	call : function (url, type, dataType, data, async, success, error) {
		$.ajax({
			url : url,
			type : type,
			dataType : dataType,
			data : data,
			async : async,
			cache : false,
			success : function(result) {
				success.call(this, result);
			},
			error : function(result) {
				if (error != null) {error.call(this, result);}
			}
		});
	},
	callWithJson : function (url, type, dataType, data, async, success, error) {
		$.ajax({
			url : url,
			type : type,
			dataType : dataType,
			data : data,
			async : async,
			cache : false,
			contentType: "application/json; charset=utf-8",
			success : function(result) {
				success.call(this, result);
			},
			error : function(result) {
				if (error != null) {error.call(this, result);}
			}
		});
	}
};

var DIALOG = {
		alert : function (msg,func) {
			art.dialog({
				id : "Alert",
				title : '嘉宝易汇通',
				lock : true,
				width : 200,
				resize : false,
				okVal : '确定',
				drag : true,
				content : msg,
				esc : true,
				background : "#555",
				callback: func,
				ok : function(param) {
					return func && func.call(this,param);
				}
			});
		},
		epConfirm : function (content, yes, no) {
				return artDialog({
					title : '嘉宝易汇通',
					id : 'Confirm',
					icon : 'question',
					okVal : '确定',
					cancelVal : '取消',
					fixed : true,
					lock : true,
					opacity : .1,
					content : content,
					ok : function(here) {
						return yes.call(this, here);
					},
					cancel : function(here) {
						return no && no.call(this, here);
					}
				});
		},
		epPrompt : function(content, yes, value) {
				value = value || '';
				var input = 0;
				return artDialog({
					title : '嘉宝易汇通',
					id : 'Prompt',
					icon : 'question',
					okVal : '确定',
					cancelVal : '取消',
					fixed : true,
					lock : true,
					opacity : .1,
					content : [ '<div style="margin-bottom:5px;font-size:12px">',
							content, '</div>', '<div>', '<input class="input-text input-normal" value="', value,
							'" />', '</div>' ]
							.join(''),
					init : function() {
						input = this.DOM.content.find('input')[0];
						input.select();
						input.focus();
					},
					ok : function(here) {
						return yes && yes.call(this, input.value, here);
					},
					cancel : true
				});
		},
	};


function getCookie(objName){
     var arrStr = document.cookie.split(";");
     for(var i = 0;i < arrStr.length;i++){
         var temp = arrStr[i].split("=");
         if( $.trim(objName) == $.trim(temp[0])) return temp[1];
    }   
}

function delCookie(name){
    document.cookie = name+"=0;path=/;expires="+(new Date(0)).toGMTString();
}

function addCookieWithNoExpires(name,value){
    var cookieValue = name + "=" + value;
    cookieValue += ";path=/";
//    cookieValue += ";domain=.sf-express.com";
    document.cookie = cookieValue;
}

function addCookie(name,value){
    var cookieValue = name + "=" + value;
    var date = new Date();
    var ms = 7*24*3600*1000;
    date.setTime(date.getTime() + ms);
    cookieValue += ";expires=" + date.toGMTString();
    cookieValue += ";path=/";
//    cookieValue += ";domain=.sf-express.com";
    document.cookie = cookieValue;
}

/*-------------------扩展Date的format方法 ---------------------*/
Date.prototype.format = function (format) {
	var o = {
    	"M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S": this.getMilliseconds()
     };
     if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
     }
     for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
     }
     return format;
};

/**  
 * 转换日期对象为日期字符串(国际化)  
 * @param date 日期对象  
 * @param isFull 是否为完整的日期数据,  
 *               为true时, 格式如"2000-03-05 01:05:04"  
 *               为false时, 格式如 "2000-03-05"  
 * @return 符合要求的日期字符串  
 */  
function getI18nFormatDate(date, isFull) {
        var pattern = "";
        if (isFull == true || isFull == undefined) {
            return date.format('yyyy-MM-dd');
        }(date, pattern);
    }

/**  
 * 转换long值为日期字符串(国际化)  
 * @param l long值  
 * @param isFull 是否为完整的日期数据,  
 *               为true时, 格式如"2000-03-05 01:05:04"  
 *               为false时, 格式如 "2000-03-05" 
 * @return 符合要求的日期字符串  
 */  
function getI18nFormatDateByLong(l, isFull) {
	if(undefined == l || null == l || "null" == l || "" == l){
		return "";
	}
    return getI18nFormatDate(new Date(l), isFull);
}

/**
 * 将一个javaScript字面量对象,转化成一个标准的json字符串.
 * by : 孔卫佳
 * time : 2013-05-24
 * @param javascript字面量对象
 * @return 标准json字符串
 */
function json2String(obj){
	if(typeof(obj) =='undefined' || typeof(obj) =='function'){
		return '';
	}
	if(typeof(obj) =='number' || typeof(obj) =='string' || typeof(obj) =='boolean'){
		return '"'+obj+'"';
	}
	if(typeof(obj) =='object'){
		if(obj instanceof Array){
			var reStr = '[';
			for(i in obj){
				reStr = reStr + json2String(obj[i])+',';
			}
			reStr = reStr + ']';
            reStr = reStr .replace(/,]/,']');
			return reStr;
		} 
		if(obj instanceof Date){
			return '"'+obj+'",';
		}
		if(obj instanceof Object){
			var reStr = '{';
			for(i in obj){
				reStr = reStr + '"' + i + '":' + json2String(obj[i]) + ',';
			}
            reStr = reStr + '}';
            reStr = reStr .replace(/,}/,'}');
			return reStr;
		}
	}
	return '';
}

function subString(str, len, hasDot) {
	if(null==str || str==undefined){
		return '';
	}
    var newLength = 0; 
    var newStr = ""; 
    var chineseRegex = /[^\x00-\xff]/g; 
    var singleChar = ""; 
    var strLength = str.replace(chineseRegex,"**").length; 
    for(var i = 0;i < strLength;i++) { 
        singleChar = str.charAt(i).toString(); 
        if(singleChar.match(chineseRegex) != null){ 
            newLength += 2; 
        } else { 
            newLength++; 
        } 
        if(newLength > len) { 
            break; 
        } 
        newStr += singleChar; 
    } 
    if(hasDot && strLength > len) { 
        newStr += "..."; 
    } 
    return newStr; 
} 

/**
 * 判断对象是否为空
 */
function isNotEmpty(object) {
	if(null != object && '' != object && undefined != object){
		return true;
	} else {
		return false;
	}
}

/**
 * 去除字符串中所有空格
 * @param str
 * @param is_global
 * @returns
 */
function Trim(str,is_global) {
	var result;
	result = str.replace(/(^\s+)|(\s+$)/g,"");
	if(is_global.toLowerCase()=="g")
	result = result.replace(/\s/g,"");
	return result;
} 

/**
 * 空判断
 * @param value
 * @returns {Boolean}
 */
function isNull(value){
	if(undefined == value || null == value || "" == value){
		return true;
	}
	return false;
}

/**
 * 格式化空串
 * @param value
 * @returns {String}
 */
function formatNull(value){
	if(isNull(value)){
		value = "";
	}
	return value;
}

/**
 * 格式化价格显示
 * @param value
 * @returns {String}
 */
function formatPrice(value){
	if(!isNull(value)){
		return "￥" + value.toFixed(2);
	}else{
		return formatNull(value);
	}
}

/**
 * 格式化手机号码显示
 * @param value
 * @returns {String}
 */
function formatPhone(value){
	if(!isNull(value)){
		var reg = /(\d{3})\d{4}(\d{4})/;
		return " " + value.replace(reg,"$1****$2");
	}else{
		return formatNull(value);
	}
}

/**
 * 格式化订单状态
 * @param value
 * @returns {String}
 */
function formatOrderStatus(value){
	if(!isNull(value)){
		switch (value) {
		case "0":
			return "未确认";
		case "1":
			return "已确认";
		case "2":
			return "已完成";
		case "3":
			return "已取消";
		case "4":
			return "待付款";
		case "5":
			return "待发货";
		case "6":
			return "待收货";
		case "7":
			return "已签收";
		case "8":
			return "退货中";
		case "9":
			return "已退货";
		default:
			return "";
		}
	}else{
		return formatNull(value);
	}
}

var VALIDATOR = {
		isValidUsername : function (username){
			return (/^[a-zA-Z][a-zA-Z0-9_]{5,17}$/).test(username);
		},
		isValidMobel : function (mobile) {  
			return (/^[0-9]\d{10}$/g.test(mobile));
		},
		isValidTelephone : function (tel){  
			return (/^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,5})?$/).test(tel);
		},
		isValidEmail : function (email) {
			var reg = /^([a-zA-Z0-9]*[-_.]?[a-zA-Z0-9]+)+@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\.][A-Za-z]{2,3}([\.][A-Za-z]{2})?$/;
			return (reg.test(email) && email.length<=100);
		}
};

/**
 * 项目路径
 */
var rootPath = "/f";

/**
 * 获取路径参
 */
var URL_PARAM = {
		getParam : function(key) {
			var svalue = window.location.search.match(new RegExp("[\?\&]" + key
					+ "=([^\&]*)(\&?)", "i"));
			return svalue ? svalue[1] : svalue;
		}
	};

/**
 * 获取openId参数
 * @returns
 */
function getOpenidByUrl() {
	var openId = URL_PARAM.getParam("openId");
	if (isNotEmpty(openId)){
		return openId;
	} else {
		return "";
	}
}
