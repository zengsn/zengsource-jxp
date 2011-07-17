// Auto create panels

Ext.onReady(function() {
	
	//inner panel
	var innerPanels = Ext.query('div[class="i-inner-panel"]');
	//alert(innerPanels.length);
	for(var i=0; i<innerPanels.length; i++) {
		var pnId = innerPanels[i].id;
		var cnId = pnId.replace('-panel', '-content');	
		var title = Ext.get(pnId).dom.title;
		var panel = new Ext.Panel({
		    frame: true,
			title: title,
		    layout:'fit',
		    autoWidth: true,
		    autoHeight:true,
		    collapsible:true,
		    animCollapse:true,
		    bodyStyle: {
		    	//background: '#DCDCDC',
		    	padding:'5px'
		    },
		    contentEl: cnId			
		});
		panel.render(pnId);	
		//alert(panel);
	}
	
});