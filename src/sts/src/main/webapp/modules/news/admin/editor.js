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
var newsEntryCreateForm = new Ext.jxsite.NewsEntryEditFormPanel({});
var newsEntryEditorTabPanel = new Ext.TabPanel({
	id: 'news-editor-panel',
	//plain: true,  //remove the header border
	activeItem: 0,
	//bodyStyle:'padding:0px',
	//defaults: {bodyStyle: 'padding:0px'},
	items:[ newsEntryCreateForm ]
});
newsEntryEditorTabPanel.on('show', function(panel) {
	//panel.add(createPanel);
	//panel.activate(createPanel);
});

// Register Panel
registerPanel(newsEntryEditorTabPanel);
