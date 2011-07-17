// user.js

/*
 * User management.
 * Copyright(c) 2008, iCanWear.com
 * icanwear@hotmail.com
 * 
 * http://iCanWear.com/license
 */

Ext.namespace('JXP.ui');

// ///////////////////////////////////////////////////////// //
// UserMgmtPanel: Border Layout with TreeGrid and FormPanel. //
// ///////////////////////////////////////////////////////// //
JXP.ui.UserMgmtPanel = Ext.extend(Ext.Panel, {
	// default config
	
	initComponent : function() {
		// hard coded - cannot be changed from outside
		var config = {};
		// apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        // call parent
        JXP.ui.UserMgmtPanel.superclass.initComponent.apply(this, arguments);
		
	},//END-initComponent
	
	onRender : function() {
		JXP.ui.UserMgmtPanel.superclass.onRender.apply(this, arguments);
	}// END-onRender()
});

Ext.reg('usermgmtpanel', JXP.ui.UserMgmtPanel);

// Initialize Panel
var userMgmtPanel = new JXP.ui.UserMgmtPanel();

// Register Panel
registerPanel(moduleTabPanel);