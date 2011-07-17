<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Style of view -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/modules/product/resources/css/view.css" />

<div id="product-view">
				
	<div id="north">
		
	</div>
	
	<div id="west">
		<div id="catalog-list"></div>
	</div>
	
	<div id="center">
	  <div id="loading-mask" style=""></div>
	  <div id="loading">
	    <div class="loading-indicator">
	    	正在加载,请稍候 <img src="${_EXT_URL_}/${_EXT_VERSION_}/resources/images/default/shared/loading-balls.gif" style="margin-right:8px;"/>
	    </div>
	  </div>
		<div id="design-wizard"></div>
	</div>
	
	<div id="east">
		
		<!-- Product Detail Panel -->
		<div id="detail-panel">
			<div id="detail-content">
				<div id="design-steps">
					<li>选择模板<p>选择定制模板或使用空模板。</p></li>
					<li>添加文字<p>添加个性标语并设定文字格式。</p></li>
					<li>添加图片<p>选择或上传个性图片。</p></li>
				</div>
				<div style="text-align:center; valign:middle; padding-top: 36px;">
					<img src="../resources/images/left_arrow-64.gif" />
					<br />
					<p>请点击选择产品查看明细.</p>
				</div>
			</div>
		</div>
		
	</div>
	
	<div id="south"></div>

</div>

<script type="text/javascript" src="../modules/product/blocks/SHELF_CONFIG.js"></script>
<script type="text/javascript" src="../modules/product/blocks/view.js"></script>