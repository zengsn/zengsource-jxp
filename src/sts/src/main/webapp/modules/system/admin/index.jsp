<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${_SITE_} :: Backend | v1.02-20080918</title>
<!-- ExtJS Libarary. -->
<jsp:include page="/ext.jsp" />
<!-- User Libarary. -->
<link rel="stylesheet" type="text/css" href="../resources/css/backend.css" />
<link rel="stylesheet" type="text/css" href="../resources/css/iconcls.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/modules/system/admin/index.js"></script>
</head>
<script type="text/javascript">
<!--
//-->
</script>
<body>
	<div id="login-form">
		<form action="main.htm" method="post" id="login">
			<p>帐号：<input type="text" /></p>
			<p>密码：<input type="password" /></p>
			<input type="submit" value="登录" />			
		</form>
	</div>
</body>
</html>