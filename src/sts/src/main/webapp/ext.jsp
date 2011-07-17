<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" type="text/css" href="${_EXT_URL_}/${_EXT_VERSION_}/resources/css/ext-all.css" />
<%-- Includes z-ux --%>
<link rel="stylesheet" type="text/css" href="${_EXT_URL_}/z-ux/css/z-messager.css"/>
<%-- Include UX --%>
<%-- if (not empty _FORM_MULTI_SELECT_) --%>
<link rel="stylesheet" type="text/css" href="${_EXT_URL_}/z-ux/css/MultiSelect.css"/>
<%-- if (not empty _GRID_ROW_EDITOR_) --%>
<link rel="stylesheet" type="text/css" href="${_EXT_URL_}/${_EXT_VERSION_}/examples/ux/css/RowEditor.css" />
<%-- ExtJS LIBs --%>
<script type="text/javascript" src="${_EXT_URL_}/${_EXT_VERSION_}/adapter/ext/ext-base.js"></script>
<c:choose>
	<%-- This is for production mode. --%>
	<c:when test="${empty _EXT_SOURCE_}">
<script type="text/javascript" src="${_EXT_URL_}/${_EXT_VERSION_}/ext-all.js"></script>
	</c:when>
	<%-- This is for development mode. --%>
	<c:otherwise>
		<c:forEach var="include" items="${_EXT_SOURCE_ALL_}">
<script type="text/javascript" src="${_EXT_URL_}/${_EXT_VERSION_}/${_EXT_SOURCE_}/${include}"></script>
		</c:forEach>
	</c:otherwise>
</c:choose>
<%-- Includes z-ux --%>
<script type="text/javascript" src="${_EXT_URL_}/z-ux/ZMessager.js"></script>
<script type="text/javascript" src="${_EXT_URL_}/z-ux/ZPagingToolbarCN.js"></script>
<%-- Include UX --%>
<%-- if (not empty _FORM_MULTI_SELECT_) --%>
<script type="text/javascript" src="${_EXT_URL_}/${_EXT_VERSION_}/examples/ux/MultiSelect.js"></script> 
<script type="text/javascript" src="${_EXT_URL_}/z-ux/form/ZItemSelector.js"></script> 
<%-- if(not empty _PAGING_PROGRESS_BAR_) --%>
<script type="text/javascript" src="${_EXT_URL_}/${_EXT_VERSION_}/examples/ux/ProgressBarPager.js"></script> 
<script type="text/javascript" src="${_EXT_URL_}/${_EXT_VERSION_}/examples/ux/PanelResizer.js"></script> 
<script type="text/javascript" src="${_EXT_URL_}/${_EXT_VERSION_}/examples/ux/PagingMemoryProxy.js"></script>
<%-- if (not empty _GRID_ROW_EDITOR_) --%> 
<script type="text/javascript" src="${_EXT_URL_}/${_EXT_VERSION_}/examples/ux/RowEditor.js"></script> 
<script type="text/javascript" src="${_EXT_URL_}/z-ux/grid/ZRowEditorCN.js"></script>
<script type="text/javascript">
<!--
Ext.QuickTips.init();
Ext.form.Field.prototype.msgTarget = 'side';
Ext.BLANK_IMAGE_URL='${_EXT_URL_}/${_EXT_VERSION_}/resources/images/default/s.gif';
String.prototype.ellipse = function(maxLength){
    if(this.length > maxLength){
        return this.substr(0, maxLength-3) + '...';
    }
    return this;
};

// Constants
var JXP_EXT_URL='${_EXT_URL_}';
var JXP_EXT_VERSION='${_EXT_VERSION_}';
var JXP_EXT_ROOT = JXP_EXT_URL + '/' + JXP_EXT_VERSION;
var JXP_WEB_CONTEXT='<%=request.getContextPath()%>';
//-->
</script>