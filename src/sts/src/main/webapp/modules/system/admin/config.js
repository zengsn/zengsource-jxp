/*
 * Base configuration management.
 * Copyright(c) 2008, iCanWear.com
 * icanwear@hotmail.com
 * 
 * http://iCanWear.com/license
 */

// Record
var Config = Ext.data.Record.create([
   'id', 'key', 'name', 'value', 'group', 'desc'
]);

// create the Data Store
var configStore = new Ext.data.Store({
  // load using HTTP
  //url: '../xdb/baseconfigs.xml',
  url: 'system/config.jxp',
   baseParams: {opt: 'list'}, 

  // the return will be XML, so lets set up a reader
  reader: new Ext.data.XmlReader({
		id: 'id',
		record: 'Config',
		totalRecords: 'totalCount'
	}, Config)
});
var italicRenderer = function(v) {
	return '<i>' + v + '</i>';
};
var configCM = new Ext.grid.ColumnModel([ new Ext.grid.RowNumberer(), {
	id:'id',
	header: "ID",
	dataIndex: 'id',
	width: 80
},{
	header: "名称",
	dataIndex: 'name',
	width: 120,
	editor: new Ext.form.TextField({
		allowBlank: false
	})
},{
	header: "KEY",
	dataIndex: 'key',
	width: 120,
	editor: new Ext.form.TextField({
		allowBlank: false
	})
},{
	header: "VALUE",
	dataIndex: 'value',
	width: 200,
	editor: new Ext.form.TextField({
		allowBlank: false
	})
},{
	header: "分组",
	dataIndex: 'group',
	width: 200,
	editor: new Ext.form.TextField({
		allowBlank: false
	})
},{
	header: "说明",
	dataIndex: 'desc',
	width: 256,
	renderer: italicRenderer,
	editor: new Ext.form.TextField({
		allowBlank: false
	})
}]);
    
// create the grid
var baseConfigGrid = new Ext.grid.EditorGridPanel({
	id: 'system-config-panel',
	title: '基本配置',
	iconCls: 'icon-be-baseconfig',
	
	clicksToEdit:1,
	
	store: configStore,
	
	cm: configCM,
	//renderTo:'example-grid',
	//width:540,
	//height:200
	bbar: new Ext.PagingToolbar({
	    pageSize: 25,
	    store: configStore,
	    displayInfo: true,
	    displayMsg: '第{0}-{1}条 / 共{2}条',
	    emptyMsg: "没有纪录",
	    items:['-', {
	    	text: '添加',
	    	iconCls: 'btn-add',
	    	handler : function(){
		      var cfg = new Config({
		      	id: '',
	          name: '新配置',
	          key: 'GROUP.KEY',
	          value: ' ',
	          group: 'GROUP'
		      });
		      baseConfigGrid.stopEditing();
		      configStore.insert(0, cfg);
		      baseConfigGrid.startEditing(0, 2);
	      }
	    }, '-']
	})	
});

baseConfigGrid.on('show', function(panel) {
	configStore.load({params: {start:0,limit:25}});
});
baseConfigGrid.on('afterEdit', function(o) {
	var grid = o.grid;
	var record = o.record;
	var field = o.field;
	var value = o.value;
	var originalValue = o.originalValue;
	var row = o.row;
	var column = o.column;
	var key = record.data.key;
	if (key=='' || key=='GROUP.KEY') {
		grid.startEditing(row, 3);
		return;
	} 
	var group = record.data.group;
	if (group=='' || group=='GROUP') {
		grid.startEditing(row, 5);
		return;
	}
	var id = record.data.id;
	var params = {opt: 'save'};
	if (id=='') { // new
		params.name = record.data.name;
		params.key = key;
		params.group = group;
		params.value = record.data.value;
		params.description = record.data.desc;
	} else { // edit
		params.id = id;
		params.field = field;
		params.value = value;
	}
	Ext.Ajax.request({
		url: 'system/config.jxp',
		success: function() {
			configStore.load({params: {start:0,limit:25}});
		},
		failure: function() {
		},
		headers: {},
		params: params
	});

});

// Register Panel
registerPanel(baseConfigGrid);

