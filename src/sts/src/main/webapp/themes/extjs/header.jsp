<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

			<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/${_THEME_}/resources/css/header.css" />
			
			<!-- Top Bar ->
			<div id="hd-top">				
				<div id="search">搜索:
					<input type="text" width="200" />
					<button title="Search">Search</button>
				</div>
			</div><- End Top Bar -->
			
			<!-- header inner content -->
			<div id="hd-inner-el">
			
				<div id="logo">
					<center><a href="<%=request.getContextPath()%>/index.${_PAGE_URL_SUFFIX_}" title="SeaTurtleTech.com"><span>SeaTurtleTech.com</span></a></center>
				</div>
				<div id="navi">
					<ul>
					<li><a href="<%=request.getContextPath()%>/index.${_PAGE_URL_SUFFIX_}" id="navi-home" title="首页" class="home">首页</a></li>
					<li><a href="<%=request.getContextPath()%>/modules/product/index.${_PAGE_URL_SUFFIX_}" id="navi-product" title="产品方案" class="">产品方案</a></li>
					<li><a href="<%=request.getContextPath()%>/modules/support/index.${_PAGE_URL_SUFFIX_}" id="navi-support" title="技术支持" class="">技术支持</a></li>
					<li><a href="<%=request.getContextPath()%>/modules/demo/index.${_PAGE_URL_SUFFIX_}" id="navi-demo" title="在线演示" class="">在线演示</a></li>
					<li><a href="<%=request.getContextPath()%>/modules/customer/index.${_PAGE_URL_SUFFIX_}" id="navi-customer" title="成功案例" class="">成功案例</a></li>
					<li><a href="<%=request.getContextPath()%>/modules/contactus/index.${_PAGE_URL_SUFFIX_}" id="navi-contactus" title="联系我们" class="">联系我们</a></li>
					<li><a href="<%=request.getContextPath()%>/modules/aboutus/index.${_PAGE_URL_SUFFIX_}" id="navi-aboutus" title="关于我们" class="last">关于我们</a></li>
					</ul>
				</div>
				
			</div><!-- End header inner content -->
			<div id="hd-inner"></div>
			<div class="x-clear" ></div>
			<!-- Bottom Bar ->
			<div id="hd-bottom">
				<span>海龟科技 - 首页</span>
			</div><- End Bottom Bar -->
			<script type="text/javascript">
			<!--
			var url = window.location.toString();
			var e;
			if ( !(!url.match('/modules/product/')) ) {
				e = Ext.get('navi-product');
				//alert(e.dom);
			} else if ( !(!url.match('/modules/support/')) ) {
				e = Ext.get('navi-support');
			} else if ( !(!url.match('/modules/demo/')) ) {
				e = Ext.get('navi-demo');
			} else if ( !(!url.match('/modules/customer/')) ) {
				e = Ext.get('navi-customer');
			} else if ( !(!url.match('/modules/contactus/')) ) {
				e = Ext.get('navi-contactus');
			} else if ( !(!url.match('/modules/aboutus/')) ) {
				e = Ext.get('navi-aboutus');
			} else {
				e = Ext.get('navi-home');
			}
			if (e) e.addClass('selected');			
			//-->
			</script>
			<script type="text/javascript">
			<!--
			Ext.onReady(function() {
				var hdPanel = new Ext.Panel({
					border: true,
					frame:true,
					height: 120,
					layout: 'border',
					//bodyStyle: 'border: 1px #99BBE8 solid;',
					defaults: {frame:true, width:1000},
					items: [{
						region: 'center',
						contentEl: 'logo', 
						bodyStyle: 'background:#FFFFFF;'
					}, {
						region: 'south',
						height: 36,
						minSize:36,
						maxSize:36,
						split: true,
						contentEl: 'navi'
					}]
				});
				hdPanel.render('hd-inner');	
			});		
			//-->
			</script>