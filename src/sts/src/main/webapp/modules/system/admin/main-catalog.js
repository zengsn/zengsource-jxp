/*
 * Catalog management.
 * Copyright(c) 2008, iCanWear.com
 * icanwear@hotmail.com
 * 
 * http://iCanWear.com/license
 */

//var Catalog = Ext.

// create the Data Store
var catalogStore = new Ext.data.Store({
  // load using HTTP
  url: '../xdb/catalogs.xml',

  // the return will be XML, so lets set up a reader
  reader: new Ext.data.XmlReader({
		id: 'id',
		record: 'Catalog',
		totalRecords: 'totalCount'
	}, [
	   'id',
	   {name: 'name', mapping: 'name'},
	   'parent', 'icon', 'desc'
	])
});

var simpleCatalogStore = new Ext.data.Store({
  // load using HTTP
  url: '../xdb/catalogs.xml',

  // the return will be XML, so lets set up a reader
  reader: new Ext.data.XmlReader({
		id: 'id',
		record: 'Catalog',
		totalRecords: 'totalCount'
	}, [ 'id', 'name' ])
});

var iconRenderer = function(icon) {
	//alert(icon.length);
	return '<img src="../upload/images/catalogs/' + icon + '" title="' + icon + '" width="16" height="16"/>';
};

var catalogSM = new Ext.grid.CheckboxSelectionModel();
var catalogCM = new Ext.grid.ColumnModel([catalogSM, {
	id:'id',
	header: "ID",
	dataIndex: 'id',
	width: 80
},{
	header: "名称",
	dataIndex: 'name',
	width: 160,
	editor: new Ext.form.TextField({
	   allowBlank: false
	})
},{
	header: "上级分类",
	dataIndex: 'parent',
	width: 80,
	editor: new Ext.form.ComboBox({
    store: simpleCatalogStore,
    displayField:'name',
    valueField: 'id',
    typeAhead: true,
    //mode: 'local',
    triggerAction: 'all',
    emptyText:'Select ...',
    selectOnFocus:true,
		lazyRender:true,
		listClass: 'x-combo-list-small'
	})
},{
	header: "图标",
	dataIndex: 'icon',
	width: 64,
	align: 'left',
	renderer: iconRenderer
},{
	header: "说明",
	dataIndex: 'desc',
	width: 256,
	editor: new Ext.form.TextField({
	   allowBlank: false
	})
}]);
    
// create the grid
var catalogGrid = new Ext.grid.EditorGridPanel({
	id: 'catalog-panel',
	title: '商品分类',
	iconCls: 'icon-be-catalog',
	
	clicksToEdit:1,
	
  store: catalogStore,
  
  sm: catalogSM,
  cm: catalogCM,
  //renderTo:'example-grid',
  //width:540,
  //height:200
  bbar: new Ext.PagingToolbar({
    pageSize: 25,
    store: catalogStore,
    displayInfo: true,
    displayMsg: '第{0}-{1}条 / 共{2}条',
    emptyMsg: "没有纪录",
    items:['-', {
    	text: '添加',
    	iconCls: 'btn-add',
    	handler: function() {
    		alert('To be continued ...');
    	}
    }, '-', {
    	text: '删除',
    	iconCls: 'btn-delete',
    	handler: function() {
    		alert('To be continued ...');
    	}
    }, '-']
	})	
});

catalogGrid.on('show', function(panel) {
	catalogStore.load();
});

