<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<div id="dt" style="${_PAGE_.style}" class="${_PAGE_.cls}">
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
		<c:set var="cellCount" value="0" scope="page" />
		<c:set var="rowCount" value="0" scope="page" />
		<c:forEach var="block" items="${_PAGE_.sortedBlockInstances}">
		<c:if test="${!empty block && !empty block.prototype.id}">
			<c:if test="${cellCount==0}">
			<tr>
			</c:if>
			<c:set var="cellCount" value="${cellCount+block.colspan}" scope="page" />
			<c:set var="rowCount" value="${rowCount+block.rowspan-1}" scope="page" />
				<td colspan="${block.colspan}" 
					rowspan="${block.rowspan}" 
					style="${block.style}"
					class="${block.cls}">
					<c:choose>
					<c:when test="${block.prototype.type=='static'}">
					${block.html} 
					</c:when>
					<c:when test="${block.prototype.type=='page'}">
					<c:import url="${block.prototype.pageUrl}.jsp"></c:import> 
					</c:when>
					<c:when test="${!empty block.prototype.template}">
					<tiles:insertDefinition name="${block.prototype.template}" />
					</c:when>
					<c:otherwise>
					<p>Prototype: ${block.prototype.id}, Null template!</p>
					</c:otherwise>
					</c:choose>					
				</td>				
			<c:choose>
			<c:when test="${cellCount==_PAGE_.baseCells}">
			</tr> 
			</c:when>
			<c:when test="${cellCount%_PAGE_.columns==0}">
			<c:set var="cellCount" value="${cellCount+rowCount}" scope="page" />
			<c:set var="rowCount" value="0" scope="page" />
			</tr>
			<tr>
			</c:when>
			<c:otherwise>
			</c:otherwise>
			</c:choose>
		</c:if>
		</c:forEach>
		</tbody>
	</table>
</div>
