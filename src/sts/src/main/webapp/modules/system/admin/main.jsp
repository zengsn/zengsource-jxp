<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${_SITE_} :: Backend | v1.02-20080918</title>
<!-- ExtJS Libarary. -->
<jsp:include page="/ext.jsp" />
<!-- User Libarary. -->
<link rel="stylesheet" type="text/css" href="${_EXT_URL_}/${_EXT_VERSION_}/examples/tree/column-tree.css" />
<link rel="stylesheet" type="text/css" href="${_EXT_URL_}/${_EXT_VERSION_}/examples/ux/treegrid/treegrid.css" />
<script type="text/javascript" src="${_EXT_URL_}/${_EXT_VERSION_}/examples/grid/RowExpander.js"></script>
<script type="text/javascript" src="${_EXT_URL_}/${_EXT_VERSION_}/examples/tree/ColumnNodeUI.js"></script>
<script type="text/javascript" src="${_EXT_URL_}/${_EXT_VERSION_}/examples/ux/treegrid/TreeGridSorter.js"></script>
<script type="text/javascript" src="${_EXT_URL_}/${_EXT_VERSION_}/examples/ux/treegrid/TreeGridColumnResizer.js"></script>
<script type="text/javascript" src="${_EXT_URL_}/${_EXT_VERSION_}/examples/ux/treegrid/TreeGridNodeUI.js"></script>
<script type="text/javascript" src="${_EXT_URL_}/${_EXT_VERSION_}/examples/ux/treegrid/TreeGridLoader.js"></script>
<script type="text/javascript" src="${_EXT_URL_}/${_EXT_VERSION_}/examples/ux/treegrid/TreeGridColumns.js"></script>
<script type="text/javascript" src="${_EXT_URL_}/${_EXT_VERSION_}/examples/ux/treegrid/TreeGrid.js"></script>
<script type="text/javascript" src="${_EXT_URL_}/ux/Spinner.js"></script>
<script type="text/javascript" src="${_EXT_URL_}/ux/SpinnerStrategy.js"></script>
<script type="text/javascript" src="${_EXT_URL_}/ux/Ext.ux.SearchField.js"></script>
<script type="text/javascript" src="${_EXT_URL_}/ux/Ext.MessageBox.js"></script>
<script type="text/javascript" src="../resources/Ext.form.XmlErrorReader.js"></script>
<link rel="stylesheet" type="text/css" href="../resources/css/backend.css" />
<link rel="stylesheet" type="text/css" href="../resources/css/iconcls.css" />
<link rel="stylesheet" type="text/css" href="${_EXT_URL_}/ux/Spinner.css" />
<link rel="stylesheet" type="text/css" href="${_EXT_URL_}/ux/Ext.MessageBox.css" />
<link rel="stylesheet" type="text/css" href="${_EXT_URL_}/ux/box/css/box-white.css" />
<link rel="stylesheet" type="text/css" href="${_EXT_URL_}/ux/box/css/box-gray.css" />
<link rel="stylesheet" type="text/css" href="${_EXT_URL_}/ux/box/css/box-steelblue.css" />
<script type="text/javascript" src="../modules/system/admin/Ext.ux.SelectBox.js"></script>
<script type="text/javascript" src="../modules/system/admin/Ext.app.SearchField.js"></script>
<script type="text/javascript" src="../modules/system/admin/main-variables.js"></script>
</head>
<body>
    <div id="header"><h1>${_SITE_} :: 后台</h1></div>
    <div style="display:none;" class="x-hide-display">
    
        <!-- Welcome Panel -->
        <div id="welcome-div">
            <tiles:insertDefinition name="system-welcome" />
        </div>
        
        <!-- Menu Detail List -->
        <c:forEach var="leafMenu" items="${leafMenuList}">
        	<tiles:insertDefinition name="${leafMenu.id}" />
        </c:forEach>
        <script type="text/javascript" src="../modules/system/admin/main-default.js"></script>
		<script type="text/javascript" src="../modules/system/admin/main.js"></script>
    </div>
    
    <div id="footer"><center>&copy;2008 <a href="">${_SITE_}</a> All Rights Reserved</center></div>
    
</body>
</html>