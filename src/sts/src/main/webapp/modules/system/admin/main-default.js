/*
 * Default Panels.
 * Copyright(c) 2008, iCanWear.com
 * icanwear@hotmail.com
 * 
 * http://iCanWear.com/license
 */

Ext.namespace('Ext.jxsite');

Ext.jxsite.DEFAULT_EL_SUFFIX = '-auto';
Ext.jxsite.DEFAULT_INSTRUCTION = ''
	+ '<h1>Default Panel How-To:</h1>'
	+ '<p>Add the markup into one DIV which id is MENU_ID+"-panel-auto", i.e.</p>'
	+ '<p>&lt;div id="system-module-panle-auto"&gt;</p>'
	+ '<p>&nbsp;&nbsp;Put content here for the menu ...</p>'
	+ '<p>&lt;/div&gt;</p>';

Ext.jxsite.DefaultPanel = function(config) {
	config = config || {};
	// Must have an ID
	if(!config.id) {
		alert('Null ID of panel!');
		return;
	}
	// Check if there default content
	var eid = config.id + Ext.jxsite.DEFAULT_EL_SUFFIX;
	var isMarkup = !(!Ext.get(eid));
	if(isMarkup) {
		config = Ext.applyIf(config || {}, {
			title: 'Default Panel',
			bodyStyle: 'padding:10px;',
			contentEl: eid
		});
	} else {
		config = Ext.applyIf(config || {}, {
			title: 'Default Panel',
			bodyStyle: 'padding:10px;',
			html: Ext.jxsite.DEFAULT_INSTRUCTION
		});
	}
	Ext.jxsite.DefaultPanel.superclass.constructor.apply(this, arguments);
};
Ext.extend(Ext.jxsite.DefaultPanel, Ext.Panel, {});