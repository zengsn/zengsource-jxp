/*
 * Home of backend.
 * Copyright(c) 2008, iCanWear.com
 * icanwear@hotmail.com
 * 
 * http://iCanWear.com/license
 */

var welcomPanel = new Ext.Panel({
	id:'system-welcome-panel',
	title: '后台首页',
	autoScroll: true,
	iconCls: 'icon-be-home',
	defaults: {bodyStyle:'padding:15px'},
  contentEl: 'welcome-div',
	tools: [{
		id: 'refresh',
		qtip: '刷新',
    // hidden:true,
		handler: function(event, toolEl, panel) {
			alert('');
		}
	}],
	bbar: [ {
		text: '配置后台首页',
		iconCls: 'icon-be-baseconfig'
	}, '->', '<b>Session</b>: '+(new Date()).format('Y-m-d H:i:s')]
});

// Register Panel
registerPanel(welcomPanel);