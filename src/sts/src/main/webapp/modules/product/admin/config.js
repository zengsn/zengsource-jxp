/*
 * Product Module Configuration.
 * Copyright(c) 2008, iCanWear.com
 * icanwear@hotmail.com
 * 
 * http://iCanWear.com/license
 */

// var NameNValue = ... // see main-variables.js 

var productConfigStore = new Ext.data.Store({
	// load using HTTP
	url: 'system/config.jxp',
	baseParams: {
		opt: 'list',
		group: 'product'
	}, 

  // the return will be XML, so lets set up a reader
  reader: new Ext.data.XmlReader({
		id: 'id',
		record: 'Config',
		totalRecords: 'totalCount'
	}, NameNValue)
});


var productConfigCM = new Ext.grid.ColumnModel([ new Ext.grid.RowNumberer(), {
	header: "配置项",
	dataIndex: 'name',
	width: 320
},{
	id: 'value',
	header: "配置值",
	dataIndex: 'value',
	width: 360,
	editor: new Ext.form.TextField({
		allowBlank: false
	})
}]);

var productConfigGrid = new Ext.grid.EditorGridPanel({
	id:'product-config-panel',
	title: '模块配置',
	iconCls: 'icon-be-baseconfig',
	autoExpandColumn: 'value',
	viewConfig : {
		//forceFit:true,
		//scrollOffset:2 // the grid will never have scrollbars
	},
	store: productConfigStore,
  
  cm: productConfigCM,
  tools: [{
		id: 'refresh',
		qtip: '模块配置',
		// hidden:true,
		handler: function(event, toolEl, panel) {
			productConfigStore.load();
		}
	}]
});
productConfigGrid.on('show', function(panel) {
	productConfigStore.load();
});

// Register Panel
registerPanel(productConfigGrid);