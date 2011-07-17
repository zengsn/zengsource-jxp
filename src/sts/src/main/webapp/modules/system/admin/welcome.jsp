<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		
		<div id="module-list" style="padding:8px;">
			<h2>模块列表</h2>
			<hr />
			<c:set var="count" value="1" scope="page"></c:set>
			<c:forEach var="module" items="${modules}">
			<ul>
				<c:out value="${count}" />.&nbsp;
				<c:set var="count" value="${count+1}" scope="page"></c:set>
				<a href="">${module.name}</a>
				<li>状态: ${module.status}</li>
			</ul>
			</c:forEach>
		</div>
		<script type="text/javascript" src="../modules/system/admin/welcome.js"></script>