<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%-- <c:set var="ctx" value="${pageContext.request.contextPath}/${fns:getFrontPath()}" /> --%>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static" />
<c:set var="ctxBase" value="${pageContext.request.contextPath}/f" />
<script type="text/javascript" src="${ctxStatic}/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/jquery-powerSwitch-min.js"></script>
<link href="${ctxStatic}/css/common.css" rel="stylesheet" type="text/css">
<link href="${ctxStatic}/css/user.css" rel="stylesheet" type="text/css" />
<link href="${ctxStatic}/css/orders.css" rel="stylesheet" type="text/css" />
<%-- <script type="text/javascript" src="${ctxStatic}/js/select.js"></script> --%>