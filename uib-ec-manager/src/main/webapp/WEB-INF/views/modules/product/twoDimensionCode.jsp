<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>二维码</title>
<style type="text/css">
div {
	text-align: center;
}
</style>
<script type="text/javascript">
	function printdiv(printpage) {
		var headstr = "<html><head><title></title></head><body>";
		var footstr = "</body>";
		var newstr = document.all.item(printpage).innerHTML;
		var oldstr = document.body.innerHTML;
		document.body.innerHTML = headstr + newstr + footstr;
		window.print();
		document.body.innerHTML = oldstr;
		return false;
	}
</script>
</script>
</head>
<body>
	<div id="div_print">
		<img alt="" width="600" height="600" src="${ctx}/product/product/produceImg?url=${url}">
	</div>
	<div>
		<input name="b_print" type="button" class="ipt" onClick="printdiv('div_print');" value="打印 ">
	</div>
</body>
</html>