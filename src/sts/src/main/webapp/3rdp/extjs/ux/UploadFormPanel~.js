// Depandencies:
// 1. SwfUpload.js
// 2. SwfUploadPanel.js

Ext.namespace('Ext.ux');

Ext.ux.UploadFormPanel = function(config) {
	config = config || {};
	// Form Panel - Center
	this.form = config.form || {};
	this.form = Ext.apply(this.form || {}, {
		region:'center',
	    margins: '5 0 5 5',
		labelPad: 20,
	    labelWidth: 64,
	    labelAlign: 'right',
		layoutConfig: {
			labelSeparator: ''
		},
		defaults: {
			width: 230,
			msgTarget: 'side'
		},
	    defaultType: 'textfield'
	});
	this.formPanel = new Ext.form.FormPanel(this.form);
	// Upload Panel - East
	this.upload = config.upload || {};
	this.upload = Ext.apply(this.upload || {}, {
	    region:'east',
		floatable: false,
		margins: '5 5 5 0',
		cmargins: '5 5 0 0'
	});
	this.uploadPanel = new Ext.ux.SwfUploadPanel(this.upload);
	
	config = Ext.apply(config || {}, {		
		defaults: {
			collapsible: false,
	        split: true,
			animFloat: false,
			autoHide: false,
			useSplitTips: true,
			bodyStyle: 'padding:0px'
		},
		items: [this.formPanel, this.uploadPanel]
	});
	
	Ext.ux.UploadFormPanel.superclass.constructor.apply(this, arguments);
};

Ext.extend(Ext.ux.UploadFormPanel, Ext.Panel, {
	getFormPanel : function() {
		return this.formPanel;
	},
	getUploadPanel : function() {
		return this.uploadPanel;
	}
});

