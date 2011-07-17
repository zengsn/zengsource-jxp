// Depandencies:
// 1.
// 2.

Ext.namespace('Ext.ux');

Ext.ux.UploadFormPanel = function(config) {
	
	config = config || {};
	
	this.form = Ext.applyIf(config.form || {}, {
		xtype: 'form', 
		title: 'Form',
		collapsible: false,
		region:'center',
		margins: '5 0 5 5',
		bodyStyle: 'padding:15px'
	});
	this.upload = Ext.applyIf(config.upload || {}, {
		title: 'Uploader',
		region:'east',
		floatable: false,
		margins: '5 5 5 0',
		cmargins: '5 5 0 0',
		width: 320,
		minSize: 240,
		maxSize: 360,
		flash_url: "swfupload_f9.swf"
	});
	this.uploadPanel = new Ext.ux.SwfUploadPanel(this.upload);
	
	config = Ext.applyIf(config, {
		id: 'upload-form-panel',
		title: 'Upload Form Panel',
		layout: 'border',
		//height: 600,
		//width: 500,
		//autoHeight: true,
		bodyBorder: false,
		defaults: {
			collapsible: true,
			split: true,
			animFloat: false,
			autoHide: false,
			useSplitTips: true			
		},
		items: [this.form, this.uploadPanel]
	});
	
	Ext.apply(this, config);
	
	Ext.ux.UploadFormPanel.superclass.constructor.apply(this, arguments);
	
};

Ext.extend(Ext.ux.UploadFormPanel, Ext.Panel, {
	getFormPanel : function() {
		return Ext.getCmp(this.form.id);
	},
	
	getUploadPanel : function() {
		return this.uploadPanel;
	}
});