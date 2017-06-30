window.res = null;
var i = 0;
var content = $(".body-content");
var paddingValue = content.css('padding-top');
var header = $('.top-bar-container');
function fixedWatch(el) {
    if (document.activeElement.nodeName == 'INPUT') {
        el.css('position', 'static');
        content.css('paddingTop', 0);
    } else {
        el.css('position', 'fixed');
        content.css('paddingTop', paddingValue);
        if (window.res) {
            clearInterval(window.res);
            window.res = null;
        }
    }
}

$('input').focus(function() {
    if (!window.res) {
        fixedWatch(header);
        window.res = setInterval(function() {
            fixedWatch(header);
        }, 500);
    }
});
