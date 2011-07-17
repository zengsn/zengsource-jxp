<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		
	<div id="system-module-details">
		<h2>Ext.layout.Accordion</h2>
		<p>Displays one panel at a time in a stacked layout.  No special config properties are required other 
		than the layout &mdash; all panels added to the container will be converted to accordion panels.</p>
		<p><b>Sample Config:</b></p>
		<pre><code>
		layout: 'accordion',
		items:[{
		    title: 'Panel 1',
		    html: 'Content'
		},{
		    title: 'Panel 2,
		    id: 'panel2',
		    html: 'Content'
		}]
		</code></pre>
		<p>You can easily customize individual accordion panels by adding styles scoped to the panel by class or id.
		For example, to style the panel with id 'panel2' above you could add rules like this:</p>
		<pre><code>
		#panel2 .x-panel-body {
		    background:#ffe;
		    color:#15428B;
		}
		#panel2 .x-panel-header-text {
		    color:#555;
		}
		</code></pre>
		<p><a href="http://extjs.com/deploy/dev/docs/?class=Ext.layout.Accordion" target="_blank">API Reference</a></p>
	</div>
	<script type="text/javascript" src="../modules/system/admin/main-module.js"></script>