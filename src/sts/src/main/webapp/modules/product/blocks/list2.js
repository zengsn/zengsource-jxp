Ext.onReady(function() {
	// bubble panel
	//var cp = new Ext.ux.BubblePanel({
	var cp = new Ext.Panel({
		title: '产品列表',
		renderTo: 'product-list2-panel',
		padding: 0,
		width: 280,
		height: 300,
		autoHeight: true,
		bodyStyle: 'padding-left: 8px;color: #0d2a59',
		contentEl: 'product-list2'
    });
});