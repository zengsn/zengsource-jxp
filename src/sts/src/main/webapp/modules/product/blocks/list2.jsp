<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

		<div id="product-list2">
			<h4>产品列表</h4>
			<ul>
				<li>Java ExtJS Platform</li>
				<li>网站门户</li>
				<li>电子商务</li>
			</ul>
		</div>
		<div id="product-list2-panel"></div>

		<link rel="stylesheet" type="text/css" href="${_EXT_URL_}/${_EXT_VERSION_}/examples/panel/css/bubble.css" />
		<script type="text/javascript" src="${_EXT_URL_}/${_EXT_VERSION_}/examples/panel/BubblePanel.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/modules/product/blocks/list2.js"></script>