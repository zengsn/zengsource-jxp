/*
 * Product editor.
 * Copyright(c) 2008, iCanWear.com
 * icanwear@hotmail.com
 * 
 * http://iCanWear.com/license
 */		
var id = (new Date()).format('YmdHisu');
var createPanel = new Ext.Panel({
	id: 'product-edit-'+id,
	title: '新建产品',
	layout: 'border',
	iconCls: 'icon-be-product',
	closable: false,
	bodyBorder: false,
	defaults: {
		collapsible: false,
		split: true,
		animFloat: false,
		autoHide: false,
		useSplitTips: true			
	},
	items: [new JXS_UI_ProductFormPanel({}, {id:id})]
});
	
// Product Editor TabPanel
var productEditorTabPanel = new Ext.TabPanel({
	id: 'product-editor-panel',
	plain: true,  //remove the header border
	activeItem: 0,
	bodyStyle:'padding:0px',
	defaults: {bodyStyle: 'padding:0px'},
	items:[ new Ext.Panel({
		id: 'product-editor-start-panel',
		title: '产品编辑说明',
		iconCls: 'icon-be-product',
		contentEl: 'product-editor-start'
	}), createPanel]
});
productEditorTabPanel.on('show', function(panel) {
	//panel.add(createPanel);
	//panel.activate(createPanel);
});

// Register Panel
registerPanel(productEditorTabPanel);