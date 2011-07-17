//ZRowEditorCN.js

Ext.namespace('Z.ux', 'Z.ux.grid');

Z.ux.grid.RowEditorCN = Ext.extend(Ext.ux.grid.RowEditor, {
	initComponent : function() {
		var config = {
	        saveText: '保存',
	        cancelText: '取消',
	        commitChangesText: '有修改未保存!',
	        errorText: '注意'
		};
	
		// apply config
	    Ext.apply(this, Ext.apply(this.initialConfig, config));
	    
	    // call parent
	    Z.ux.grid.RowEditorCN.superclass.initComponent.apply(this, arguments);
	}
});

var ZRowEditorCN = Z.ux.grid.RowEditorCN;