 var winH=window.innerHeight;
 var winW=window.innerWidth;
 $('body').css({'height':winH, 'width':winW});


 $.fn.tapClass=function(className){
            var that=this ;
       	touch.on(that.selector,'touchstart' , function(e){
       		
       		$(e.currentTarget).addClass(className);
       		setTimeout(function(){
       			$(e.currentTarget).removeClass(className);
       		},150);
       	});
       	return $(that);
       };
 $.fn.longTapClass=function(className){
      var that=this;
      touch.on(that.selector,'touchstart touchend',function(e){
            if(e.type=='touchstart'){
                  $(e.currentTarget).addClass(className);
            }
            else{
                  $(e.currentTarget).removeClass(className);
            }
            return $(that);
      })
 };

 $.fn.scrollTo =function(options){


        var defaults = {
            toT : 0,    //滚动目标位置
            durTime : 200,  //过渡动画时间
            delay : 1000/60,     //定时器时间
            callback : null
           
        };
        var opts = $.extend(defaults,options),
            timer = null,
            _this = this,
            curTop = $('body')[0].scrollTop,//滚动条当前的位置
            subTop = opts.toT - curTop,    //滚动条目标位置和当前位置的差值
            index = 0,
            dur = Math.round(opts.durTime / opts.delay),
            smoothScroll = function(t){
                index++;
                var per = Math.round(subTop/dur);
                if(index >= dur){
                    _this.scrollTop=t;
                    window.clearInterval(timer);
                    if(opts.callback && typeof opts.callback == 'function'){
                        opts.callback();
                    }
                    return;
                }else{
                   $('body')[0].scrollTop=curTop + index*per;
                    
                }
            };
        timer = window.setInterval(function(){
            smoothScroll(opts.toT);
        }, opts.delay);
        return _this;
    };
    function lunbo(id){

      var $container=$('#'+id);
      var $picBox=$container.find('.picBox');
      var $img=$picBox.find('a');
      var $indicator=$container.find('.indicator');
      var length=$picBox.find('a').length;
      var currentIndex=0;
      var picW=$container.width();
      var boxLeft=0;
      var timer=0;
      var cloneimg= $img.eq(0).clone().appendTo($picBox);
      var duration=200;
      var intervalTime=5000;
      var $index;


      function initIndicator(){
        for(var i=0;i<length;i++){
          $indicator.append('<span>');
         
        };
         $indicator.css({marginLeft:-0.5*$indicator.width()});
        $index=$indicator.find('span');
      }
     
      
   
      $picBox.width(picW*(length+1.01));
      $img.each(function(index, el) {
        $(this).width(picW);
        $(this).height($container.height());
      });
      function setIndex () {
          $index.removeClass('current').eq(currentIndex).addClass('current');
      }
      function next(){
        currentIndex++;
        boxLeft-=picW;       

        $picBox.animate({left:boxLeft}, {duration:duration,complete:check});
        function check(){
          if (currentIndex==length){
            $picBox.css({left:0});
            currentIndex=0;
            boxLeft=0;
            setIndex();
            return;
        }else {
           setIndex();
          return ;
        }

        };
       
      }

      function pre(){
        if(currentIndex==0){
          currentIndex=length;
          boxLeft=-length*picW;
          $picBox.css({left:boxLeft});

        }
        currentIndex--;
        boxLeft+=picW;
        $picBox.animate({left:boxLeft},{duration:duration,complete:setIndex});

      }

      touch.on($container[0],'swipeleft swiperight touchstart touchend' ,function (e) {
         if(e.type=='touchstart'){
          clearInterval(timer);
         }
         if(e.type=='touchend'){
          timer=setInterval(next,intervalTime);
         }
         if(e.type=='swipeleft'){
          
          next();
         };
         if (e.type=='swiperight') {
          
          pre();
         }
      });
       initIndicator();
       setIndex();
       timer=setInterval(next,intervalTime);
    };


$.fn.popMessage=function(options){
  var that=this;
  var defaults={
    title:'默认标题',
    list:['list1','list2','list3'],
    className:'popMessage',
    tapClass:'tap'
  }  ;

  var opt=$.extend(defaults,options);

  var $container=$('<div>');
  $container.addClass(opt.className);
  $container.width(winW);
  $container.height(winH);
  var $ul=$('<ul>').appendTo($container);
  var $title=$('<p>').html(opt.title).appendTo($ul);
  for(var i=0,len=opt.list.length;i<len;i++){
    $('<li>').html(opt.list[i]).appendTo($ul);
  }
  $container.appendTo('body');
  $container.addClass(opt.className);
  $ul.css({top:winH*0.5,marginTop:-$ul.height()*0.5})
 
  $('.'+opt.className+' '+'li').longTapClass(opt.tapClass);
  $('.'+opt.className+' '+'li').on('touchstart touchend',function(e){
    if(e.type=='touchstart'){
    $(that).val($(this).text());}
    if(e.type=='touchend'){
      $container.hide();
      $container.remove();
    }

    return $(that) ;
  })
};

function okCancleDialog(obj){

var defaults={
  fn1:function(){},
  fn2:function(){},
  title:'对话框标题',
  btn1:'确定',
  btn2:'取消',
  focus:2,
  containerClass:'ok-cancle-pop',
  diglogBoxClass:'dialogBox',
  titleClass:'title',
  btnClass:'box'
};

var opt=$.extend(defaults,obj);

var $container=$('<div>').appendTo('body').addClass(opt.containerClass);
var $dialogBox=$('<div>').addClass(opt.diglogBoxClass).appendTo($container);
var $title=$('<div>').html(opt.title).addClass(opt.titleClass).appendTo($dialogBox);
var $btn1=$('<div>').addClass(opt.btnClass).html(opt.btn1).appendTo($dialogBox);
var $btn2=$('<div>').addClass(opt.btnClass).html(opt.btn2).appendTo($dialogBox);
$container.height($(window).height());
$dialogBox.width($(window).width()*0.9);

$dialogBox.css({marginLeft:($(window).width()-$dialogBox.width())/2});
if(opt.focus==1){
  $btn1.addClass('focus');
}
else{
  $btn2.addClass('focus');
}
$container.show();
$($btn1).on('touchend',function(){
  opt.fn1();
  $container.hide();
  $container.remove();
});
$($btn2).on('touchend',function(){
  opt.fn2();
  $container.hide();
  $container.remove();
});

}


$.fn.popMore=function(options){
  var that=this;
  var defaults={
    list:[
    {className:'icon-msg',url:'message.html',title:'消息',dot:true},
    {className:'icon-home',url:'index.html',title:'首页',dot:false}
    ],
    className:'pop-more',
    tapClass:'tap'
    
  };

  var opt=$.extend(defaults,options);

  var $container=$('<div>');
  $container.addClass(opt.className);
  var $triangle = $('<span>');
  $triangle.addClass('triangle');
  $triangle.appendTo($container);

  for(var i=0,len=opt.list.length;i<len;i++){
    var $a = $('<a>');
    var $div = $('<div>').addClass('more-box');
    var $span1 = $('<span>').addClass('icon').addClass(opt.list[i].className);
    var $span2 = $('<span>').addClass('span-title').html(opt.list[i].title);
    var $span3 = $('<span>').addClass('icon').addClass('span-dot');
    $span1.appendTo($div);
    $span2.appendTo($div);
    if(opt.list[i].dot){$span3.appendTo($div);}
    $div.appendTo($a);
    $a.attr('href', opt.list[i].url).appendTo($container);
  }
  $container.appendTo('body');


};

 function DropDown(pClassName, childClassName, iconStart, iconEnd) {
     touch.on('.' + pClassName, 'tap', function(event) {
         var $that = $(this).hasClass('.' + pClassName) ? $(this) : $(this).closest('.' + pClassName);
         if ($that.next().is(':hidden')) {
             $('.' + childClassName).hide();
             $('.' + pClassName).removeClass(iconEnd).addClass(iconStart);
             $that.removeClass(iconStart).addClass(iconEnd);
             $that.next().show();
         } else {
             $that.removeClass(iconEnd).addClass(iconStart);
             $that.next().hide();
         }
     });
 }


$(function() {
    touch.on('#more', 'touchstart', function(event) {
        if (event.type == 'tap') {
            event.preventDefault();
        } else {
            event.stopPropagation();
            if ($('.pop-more').length === 0) {
                $(this).popMore();
            } else {
                $('.pop-more').remove();
            }
        }
    });
    $('body').on('tap', function(e) {
        $('.pop-more').remove();
    });

     //tab
    $('.tab-bar-item').on('touchstart', function(e) {
        $(this).siblings().removeClass('hover');
        $(this).addClass('hover');
        var index = $(this).index();
        $('.tab-bar-item-box').hide();
        $('.tab-bar-item-box:eq('+index+')').show();
    });
});

    