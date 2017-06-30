/**
 * 申请代理
 */
//验证姓名、身份证号是否为空
function validate(id,message){
	var value=$("#"+id).val();
	if(""==value || null==value){
		Alert.disp_prompt(message+"不能为空");
		return false;
	}
	return true;
}

//验证通过,更新会员信息
function update(){
	var flag=false;
	AJAX.call(rootPath+"/memMember/updateMemMember", "post", "json",$("#proxyForm").serialize(), false, function(result){
		if (result.status) {
            flag=true;
        } else {
    	    Alert.disp_prompt(result.msg);
    	}
     });
	return flag;
}


$(document).ready(function(){
	//接收从个人中心页面传过来的参
	if(window.localStorage){
		proxy_phone=localStorage.proxy_phone;
		proxy_approveFlag=localStorage.proxy_approveFlag;
		proxy_username=localStorage.proxy_username;
		proxy_idCard=localStorage.proxy_idCard;
	}
	$("#approveFlag").val(proxy_approveFlag);
	 if(null==proxy_phone || ""==proxy_phone || undefined==proxy_phone || "null"==proxy_phone || "undefined"==proxy_phone){
	 }else{
		//c2消费者申请成为代理 显示之前绑定的手机号
		 $("#phone").val(proxy_phone);
	 }
	 
	 //当会员信息后台审核失败的时候,显示之前申请代理的时候的信息
	 if("2"==proxy_approveFlag){
		 $("#name").val(proxy_username);
		 $("#card").val(proxy_idCard);
	 }
	
	//默认选中协议 
	$("#RadioGroup1_0").prop("checked",true);
	
	//点对话框的同意按钮,选中服务协议
	touch.on('#confirmBtn', 'tap', function(event) {
        var dia=$.dialog({
           title: '平步云天平台会员服务协议',
           content: '<div style="overflow-y:scroll;height:300px;text-align:left"><p>平步云天平台服务协议书，是对平步云天平台及根据本协议注册为平步云天平台会员的电商从业人员和企业之间的权利义务关系之约定。您注册为平步云天平台会员，即视为您同意接受本协议的约束，并愿意按照本协议的约定享受您的权利、履行您的义务。您必须保证，您已仔细阅读、完全理解并接受本协议条款。</p><p><b>第一条 术语定义</b></p><p>1、平步云天平台：是由深圳初云汇科技有限公司（以下简称为"初云汇"）运营的电商销售平台网站，平步云天平台域名为www.pingbyt.cn。</p><p>2、平步云天平台会员：指同意参加平步云天平台合作，遵守本服务协议，完成网站会员在线注册并具备履行平步云天平台合作所需的相应资质和能力的电商从业人员和企业。</p><p>3、推广服务费：平台名称为“津贴”按照本协议书第四条，会员在平步云天平台就网站的产品推广所得到的，依据本协议规定的方式计算和支付的费用。</p><p>4、可提成订单：订单处于已支付状态且已签收。</P><p>5、可提成金额：可提成订单金额总和减去退货订单金额总和。</p><p><b>第二条 平步云天平台会员注册流程</b></p><p>1、您首先在阅读、理解并接受本协议的基础上，进行注册，一旦注册成功，您将被视为平步云天平台会员，享受平步云天平台会员的权利，并履行平步云天平台会员的义务。</p><p>2、您成为平步云天平台会员，应立即登录平步云天平台账户，并详细填写个人信息、联系方式及收款账户信息（请注意：个人信息及相关银行账户信息应与您注册时使用的姓名保持一致），您应真实、准确、完整的提供该信息，如因您提供的信息不完整、错误、与注册信息不一致等原因导致平步云天平台无法联系您或无法向您进行结算的，将由您自行承担相应责任。</p><p>3、平步云天平台若提高/降低推广服务费的支付标准，将以电子邮件、网站公告或站内信的形式告知您，您如对平步云天平台变更后的推广服务费支付标准有异议的，应于收到通知之日起5日内以电子邮件或书面方式提出，您将与平步云天平台自新推广费标准生效之日起解除本协议；如您在收到通知邮件后5日内未进行回复或未提出任何异议的，视为您认同平步云天平台的该次变更，新的推广费标准将于您收到电子邮件之日起5日后或在公告所载生效日期生效；新的推广服务费支付标准生效后，自生效之日起，双方以新的支付标准进行结算。</p><p><b>第三条 网站使用条款</b></p><p>1、您在使用平步云天平台的网站服务时，应遵守国家《计算机信息系统国际联网保密管理规定》、《中华人民共和国计算机信息系统安全保护条例》、《计算机信息网络国际联网安全保护管理办法》等相关法律规定、平步云天平台的相关协议及规则。</P><p>2、您在使用平步云天平台的网站服务时，禁止任何违法违规行为，如：</P><p>2.1 任何干扰或破坏平步云天平台或与平步云天平台相连的服务器和网络及从事其他干扰或破坏平步云天平台服务的活动；</p><p>2.2 通过平步云天平台发布、传播有关宗教、种族或性别等的贬损言辞；</p><p>2.3 通过平步云天平台发布、传播侵犯党和国家利益的言论；骚扰、滥用或威胁其他用户的信息、广告信息；</P><p>2.4 侵犯任何第三方著作权、专利、商标、商业秘密或其他专有权利或名誉权；</p><p>2.5 使用平步云天平台作任何非法用途或通过平步云天平台发布、传播其他任何违反国家相关法律法规的信息。</p><p>3、注册平步云天平台的会员及使用相应服务是免费的，平步云天平台保留修改费用条款的权利，一旦费用条款被修改，将立即在网站上进行公示。</p><p>4、如果您在18周岁以下，您只能在父母或监护人的监护参与下才能使用本站。</p><p><b>第四条 推广服务费及支付</b></p><p>1、自您成功注册为平步云天平台会员之日起，平步云天平台与您建立推广服务合作模式的合作关系。推广服务费的支付条件及比例依据本协议确定，您注册为平步云天平台会员的行为即表示您完全接受本条款之约束。</p><p>2、推广服务费确认和说明：在网站服务期内，平步云天平台按双方合作产生的实际可提成金额向您支付推广服务费，商品、会员等级不同推广服务费比例不同，具体产品的推广费比例根据产品确定。您应明确：平步云天平台有权根据客观情况及自身经营状况变更推广费支付比例，该种变更无须获得您的同意，平步云天平台仅须在发生此种变更时按照本协议第二条第3款以电子邮件、网站公告或站内信的形式通知您。综上：您获得的推广服务费=（商品市场价—会员等级价）/2</p><p>3、数据的传输机制：平步云天平台实时向您传送访问者所发生的购买行为、订单情况等能够证明访问者购买行为的数据，您可登陆您的网站账号查看。您应确认：该实时数据仅作为您推广效果的参考数据，最终数据以双方确认的对账单为准。</p><p>4、数据的核对：</p><p>4.1 平步云天平台实行实时结算，每笔支付成功并签收的订单的推广服务费实时返到您的账户余额，您可以自主选择进行提现操作。</p><p>4.2 发起提现申请前，请核对您的可结算金额，第一次申请提现时请完善提现账户信息；如出现数据误差，您应立即提出，平步云天平台与您及时核对处理。若因银行信息错误导致退费或其他影响当月推广费正常支付的，推广费将累积到下月合并支付。</p><p>4.3 如果您在平步云天平台后台的账户余额中确认可结算金额并发起提现，平步云天平台将据此提现操作与您进行结算。</p><p>4.4 提现申请一经发起，即构成本协议的组成部分，具有与本协议同等的法律效力。</p><p>5、结算：推广费实时结算，支持以下两种结算方式：</p>（1）推广费直接存到您的账户余额；（2）银行卡提现。（每月最后三天不支持该结算方式）。<p>6、会员应明确：如果当月平步云天平台向您支付的推广费超过人民币800元整，则平步云天平台将根据国家税法规定，代扣代缴个人所得税，平步云天平台会在每月月初对需代扣代缴个人所得税的会员发送代扣个税通知单，如您能按要求开具服务费发票则无需扣税，如未开具发票则需从账户余额中扣除税金。</p><p><b>第五条 配送</b></p><p>1、销售方将会把商品（货物）送到您所指定的收货地址，所有在本站上列出的送货时间为参考时间，参考时间的计算是根据库存状况、正常的处理过程和送货时间、送货地点的基础上估计得出的。</p><p>2、因如下情况造成订单延迟或无法配送等，销售方不承担延迟配送的责任：</p><p style="text-indent:2em">2.1用户提供的信息错误、地址不详细等原因导致的；<p><p style="text-indent:2em">2.2货物送达后无人签收，导致无法配送或延迟配送的；<p><p style="text-indent:2em">2.3情势变更因素导致的；<p><p style="text-indent:2em">2.4不可抗力因素导致的，例如：自然灾害、交通戒严、突发战争等。<p><p><b>第六条 平步云天平台的权利和义务</b></p><p>1、平步云天平台保证不会通过您的推广销售非法商品。如果因平步云天平台违反前述内容而造成的一切后果，将由平步云天平台自行承担。<p><p>2、平步云天平台将按照本协议的约定向您支付推广服务费。<p><p>3、您理解：平步云天平台可能会变更与其自身经营相关的政策、规定，如该种变更可能影响到本协议的履行的，平步云天平台将依据本协议约定的时间及方式，提前通知您；如您认为平步云天平台的该种变更影响了双方的合作基础，则您有权选择提前终止本协议。<p><p>4、您通过平步云天平台购买产品，平步云天平台可为您提供产品咨询、订单查询、售后服务等与销售产品等相关的行为及服务。<p><p>5、如果平步云天平台根据某些迹象判定您正在进行或即将进行可能损害平步云天平台利益的活动时，有权立即冻结您的账号，并有权要求您立即停止一切与平步云天平台相关的活动，您应在收到平步云天平台通知之日起，立即停止相关活动并撤销、删除所有与平步云天平台相关的推广链接。<p><p><b>第七条 平步云天平台会员责任和义务：</b></p><p>1、为了服务的顺利、平稳进行，您应尽最大努力保证服务方式的灵活性。</p><p>2、您在提供本协议约定的推广服务时不得有下列任何行为：</p><p>2.1 误导消费者，使其误认您是平步云天平台或初云汇的子公司、分公司、关联公司或代理商等；</p><p>2.2 未经平步云天平台同意，擅自以初云汇或者合作产品供应商的名义制作网页、印制单页或其他物料；</p><p>2.3 将推广产品向客户加价、打折出售，与其他产品捆绑销售或向客户加收费用、套取费用；</p><p>2.4 未经平步云天平台同意，将服务过程中得到的信息向第三者公开或转发；</p><p>2.5 以写入cookie为目的，使用病毒传播或文件、视频内嵌URL连接等形式诱导客户点击；</p><p>2.6 在与本服务运营有关的事项范围内，违反有关法律法规；</p><p>2.7 客观上被视为与犯罪有关的行为；</p><p>2.8 其他明显的非正当行为。</p><p>3、您在签订、履行本协议时获取的平步云天平台各项信息，均为平步云天平台的保密信息，未经平步云天平台同意，您不得泄漏给任何第三方，相关法律法规或国家政府部门要求披露的情况除外。</p><p>4、您应妥善保管平步云天平台赋予您的ID及密码，如因管理不慎发生密码遗失、被盗等情况，您将自行承担相应后果及责任。</p><p>5、平步云天平台提供的广告创意的所有相关权利均归平步云天平台所有，未经平步云天平台书面同意，您不得以任何本协议约定之外的目的进行使用或许可其他第三方使用。</p><p>6、平步云天平台将依据您在网站会员账号中填写的联系人、地址及联系邮箱、电话、传真等与您联络、发送相关通知等，您必须保证前述联系人信息的真实性、有效性、准确性，否则您将承担因此可能产生的所有后果，并自行承担相应责任。</p><p>7、按照诚实守信的原则，遵守本协议要求的平步云天平台会员责任和义务，如您违反平步云天平台会员责任和义务，平步云天平台有权对您违约或违规期间产生的业绩不予结算，并有权视您违约或违规情节轻重，单方提前终止双方之合作。您违反本服务协议之约定，不但构成对平步云天平台的违约，亦有可能构成对平步云天平台合法权益的侵犯，平步云天平台保留索赔或移交司法部门处理的权利。</p><p><b>第八条</b></p><p>本协议的生效，并不代表平步云天平台与您之间存在任何隶属、代理关系，您不得以平步云天平台代理商或平步云天平台分支机构、子公司等名义对外进行宣传，亦不得擅自使用平步云天平台商标、名称或LOGO等。</p><p><b>第九条 免责条款</b></p><p>1、由于自然灾害或不可抗力（包括软件、系统服务设备维护、检修、基础电信业务经营者电信线路服务中止等）等因素无法正常提供服务，双方可免于承担责任。</p><p>2、因市场的不可预测性和各网站经营方式等的不同，您的收入将不可预测，平步云天平台不保证您获得预期的收入。</p><p><b>第十条 保密条款</b></p><p>1. 双方保证一方根据本协议的签订和履行而获知或获准使用的另一方的硬件、软件、程序、密码、商品名、技术、许可证、专利、商标、LOGO、技术知识和经营过程是另一方的合法所有，获得方对此无任何权利或利益，亦不会向任何第三方透露。</p><p>2.双方在合作期间获知的对方的商业秘密、技术秘密等须双方保密的事项，未经对方书面允许，在协议期间及协议终止后不得向任何第三方披露或公开。</p><p>3．您承诺并保证：您将对根据本协议获知的平步云天平台销售数据、销售信息、订单信息、平步云天平台用户购买行为等进行严格保密，不会向任何第三方透露，亦不会向任何与您不相关之雇员、代理人、顾问等进行披露；同时，您有义务告知因履行本协议而必须获知该等信息的雇员、代理人、顾问，该等信息是保密信息，并就您的此类雇员、代理人、顾问的侵权或违约行为向平步云天平台承担连带责任。</p><p><b>第十一条 其它</b></p><p>1、本协议自您成功注册为平步云天平台会员之日起生效，您停止使用平步云天平台或平步云天平台冻结、撤销您的会员账号之日或出现本协议约定情形时，本协议终止。</p><p>2、平步云天平台向您发出通知，以电子邮件形式发送的，自电子邮件成功自发送人处发送之时，视为您收到该通知；以传真形式发送的，自传真完成发送之时，视为您收到该通知；以平步云天平台公告发送的，自公告发布完成时，视为您收到该通知；以快递形式发送的，以快递发出之日起5日后视为您收到该通知。</p><p>3、未尽事宜，双方应平等、友好协商，以合理解决问题的态度积极解决问题。如协商未果，可就该纠纷向深圳市南山区人民法院提请诉讼解决。</p></div>',
           button: ["同意"]
       });

        dia.on("dialog:action",function(e){
        	if(!$('#RadioGroup1_0').is(':checked')){
        		$("#RadioGroup1_0").prop("checked",true);	
        	}
        });

   });
	
	
	uib.init({
        swipeBack: true //启用右滑关闭功能
    });
    //document.getElementById("applyBtn").addEventListener('tap', function() {
	$("#applyBtn").click(function(){
    	//判断是否勾选协议
    	if(!$('#RadioGroup1_0').is(':checked')){
    		Alert.disp_prompt("请先勾选协议");
    		return;
    	}
    
        //判断姓名
    	if(!validate("name","姓名")){
    	    return;
    	}
    	//判断身份证输入是否合法
    	if(validate("card","身份证号")){
    	    var card=$("#card").val();
    	    var flag=validateIdCard(card);
    	    if(1==flag){
    	    	Alert.disp_prompt("非法身份证号");
    	    	return;
    	    }else if(2==flag){
    	    	Alert.disp_prompt("身份证号含非法地区");
    	    	return;
    	    }else if(3==flag){
    	    	Alert.disp_prompt("身份证中含非法生日");
    	    	return;
    	    }
    	}else{
    		return;
    	}
    	//判断手机号是否合法
    	if(!checkPhone()){
    		return;
    	}
    	//判断验证码是否正确
    	if(!validateCode()){
    		return;
    	}
        
    	//验证通过,改变会员身份变为分销商
    	if(!update()){
    		return;
    	}
    	
    	//弹出弹出框
        var btnArray = ['取消', '确定'];
        //uib.confirm('恭喜您成为<span style="color:#f02b2b">宝聚宝</span>电商平台代理商赶快去装修<span style="text-decoration:underline;color:#999">自己的店铺</span>吧', '温馨提示', btnArray, function(e) {
        uib.confirm('您的资料已提交，我们将在3个工作日内对您的资料进行审核，请耐心等待!', '温馨提示', btnArray, function(e){    
            if (e.index == 1) {
                window.location.href = './personal.html';
            } else {
            }
        })
   });
});

/**
 * 校验手机号码
 */
function checkPhone(){
	var phone=$("#phone").val();
	if(""!=phone && null!=phone){
		 var valResult=VALIDATOR.isValidMobel(phone);
		 if(valResult){
		     return true;
		  }else{
			  Alert.disp_prompt("请输入合法的手机号");
			  return false;
		  }
	 }else{
         Alert.disp_prompt("手机号不能为空");
		 return false;
	 }
}

/**
 * 发送短信验证码
 */
function receiveCodeClick(obj){
	var phone=$("#phone").val();
	if(!checkPhone()){
		return;
	}
	curCount = 120;
	var btn = $(obj);
	btn.removeAttr('onclick');
	AJAX.call(rootPath+"/phone/sendCode?phone="+phone+"&type=1","post", "json", "", false,function(data){
	    if(data.status){
		    sendMessage(btn);
		}else{
		    btn.attr('onclick','receiveCodeClick(this)');
		}
	});
}

//timer处理函数
function SetRemainTime(btn) {
    if (curCount == 0) {   
        window.clearInterval(InterValObj);//停止计时器
        btn.attr('onclick','receiveCodeClick(this)');
        btn.text("重新发送");
        btn.removeAttr('disabled');
    }
    else {
        curCount--;
        btn.text(curCount+"秒后重新获取");
    }
}

//倒计时
function sendMessage(btn) {
	//设置button效果，开始计时	
	btn.text(curCount+"秒后重新获取");
	btn.attr('disabled','true');
    InterValObj = window.setInterval(function(){SetRemainTime(btn);}, 1000); //启动计时器，1秒执行一次
}

/**
 * 检验验证码
 */
function validateCode(){
	var flag=false;
	var code=$("#code").val();
	if(""==code || null==code){
		Alert.disp_prompt("验证码不能为空");
		return flag;
	}
	AJAX.call(rootPath+"/phone/checkMessCode", "post", "json", "messCode=" + code + "&type=1", false, function(result){
		if (result.status) {
            flag=true;
        } else {
    	    Alert.disp_prompt(result.msg);
    	}
     });
	return flag;
}


//验证身份证是否合法的js
function validateIdCard(obj){
 var aCity={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"};
  var iSum = 0;
 //var info = "";
 var strIDno = obj;
 var idCardLength = strIDno.length;
 if(!/^\d{17}(\d|x)$/i.test(strIDno)&&!/^\d{15}$/i.test(strIDno))
        return 1; //非法身份证号

 if(aCity[parseInt(strIDno.substr(0,2))]==null)
 return 2;// 非法地区

  // 15位身份证转换为18位
 if (idCardLength==15){
    sBirthday = "19" + strIDno.substr(6,2) + "-" + Number(strIDno.substr(8,2)) + "-" + Number(strIDno.substr(10,2));
  var d = new Date(sBirthday.replace(/-/g,"/"))
  var dd = d.getFullYear().toString() + "-" + (d.getMonth()+1) + "-" + d.getDate();
  if(sBirthday != dd)
                return 3; //非法生日
              strIDno=strIDno.substring(0,6)+"19"+strIDno.substring(6,15);
              strIDno=strIDno+GetVerifyBit(strIDno);
 }

       // 判断是否大于2078年，小于1900年
       var year =strIDno.substring(6,10);
       if (year<1900 || year>2078 )
           return 3;//非法生日

    //18位身份证处理

   //在后面的运算中x相当于数字10,所以转换成a
    strIDno = strIDno.replace(/x$/i,"a");

  sBirthday=strIDno.substr(6,4)+"-"+Number(strIDno.substr(10,2))+"-"+Number(strIDno.substr(12,2));
  var d = new Date(sBirthday.replace(/-/g,"/"))
  if(sBirthday!=(d.getFullYear()+"-"+ (d.getMonth()+1) + "-" + d.getDate()))
                return 3; //非法生日
    // 身份证编码规范验证
  for(var i = 17;i>=0;i --)
   iSum += (Math.pow(2,i) % 11) * parseInt(strIDno.charAt(17 - i),11);
  if(strIDno.substring(strIDno.length-1, strIDno.length)=="a"){
	  
  }else{
	  if(iSum%11!=1)
          return 1;// 非法身份证号
  }
  

   // 判断是否屏蔽身份证
    var words = new Array();
    words = new Array("11111119111111111","12121219121212121");

    for(var k=0;k<words.length;k++){
        if (strIDno.indexOf(words[k])!=-1){
            return 1;
        }
    }
   return 0;
}
