<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>平步云天</title>



	<script type="text/javascript">
		try {
			var urlhash = window.location.hash;
			if (!urlhash.match("fromapp")) {
				if ((navigator.userAgent
						.match(/(iPhone|iPod|Android|ios|iPad)/i))) {
					window.location = "${pageContext.request.contextPath  }/page/mobile/registered.jsp";
				} else {
					window.location = "${pageContext.request.contextPath  }/f/index";
				}
			}
		} catch (err) {
		}
	</script>