<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${_SITE_} :: ${_PAGE_.name}</title>
<!-- ExtJS Libarary. -->
<jsp:include page="/ext.jsp" />
<!-- User Libarary. -->
<!-- Style Sheets -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/${_THEME_}/resources/css/tiles.css" />
<link rel="stylesheet" type="text/css" href="${_EXT_URL_}/ux/box/css/box-white.css" />
<link rel="stylesheet" type="text/css" href="${_EXT_URL_}/ux/box/css/box-gray.css" />
<link rel="stylesheet" type="text/css" href="${_EXT_URL_}/ux/box/css/box-steelblue.css" />
<script type="text/javascript">
<!--
//-->
</script>
</head>
<body>
	<div id="doc">
		
		<div id="hd"><!-- header -->
			<tiles:insertAttribute name="header" />
		</div><!-- End header -->
		<div class="x-clear"></div>
		<div id="bd"><!-- body-->
			<tiles:insertAttribute name="body" />
		</div><!-- End body-->
		
		<div id="ft"><!-- footer -->
			<tiles:insertAttribute name="footer" />
		</div><!-- End footer -->
	
	</div>
</body>
</html>