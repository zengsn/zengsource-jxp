/*
 * Product editor.
 * Copyright(c) 2008, iCanWear.com
 * icanwear@hotmail.com
 * 
 * http://iCanWear.com/license
 */

//=============================================================== // 
//TabPanel :: Product Editor Tabs =============================== //
//=============================================================== //
var productCreateForm = new Ext.jxsite.ProductEditFormPanel();
var productEditorTabPanel = new Ext.TabPanel({
	id: 'product-editor-panel',
	//plain: true,  //remove the header border
	activeItem: 0,
	//bodyStyle:'padding:0px',
	//defaults: {bodyStyle: 'padding:0px'},
	items:[ productCreateForm ]
});
productEditorTabPanel.on('show', function(panel) {
	//panel.add(createPanel);
	//panel.activate(createPanel);
});

// Register Panel
registerPanel(productEditorTabPanel);
