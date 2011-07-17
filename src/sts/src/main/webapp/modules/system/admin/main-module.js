/*
 * Module management.
 * Copyright(c) 2008, iCanWear.com
 * icanwear@hotmail.com
 * 
 * http://iCanWear.com/license
 */

var Module = Ext.data.Record.create([
	'id',
	'name',
	'index',
	'status',
	'configFile',
	'createdTime',
	'updatedTime'
]);

// create the Data Store
var moduleStore = new Ext.data.Store({
  // load using HTTP
  url: 'system/module.jxp',

  // the return will be XML, so lets set up a reader
  reader: new Ext.data.XmlReader({
		id: 'id',
		record: 'module',
		totalRecords: 'totalCount'
	}, Module),
	
	sortInfo: {
		field: "index", direction: "ASC"
	},
	
	baseParams: {action: 'listModules'}
});

var moduleIdRender = function(id) {
	return '<img src="../modules/'+id+'/resources/images/'+id+'_32_32.gif" />'
		+ '<p>'+id+'</p>';
};
var moduleStatusRender = function(val) {
	if(val==0) return '<font color="Gray">未初始化</font>';
	if(val==1) return '<font color="Green">已初始化</font>';
	if(val==2) return '<font color="Blue">已激活</font>';
	if(val==3) return '<font color="Black">已关闭</font>';
	return val;
};

var moduleSM = new Ext.grid.CheckboxSelectionModel();

var productCM = new Ext.grid.ColumnModel([ moduleSM, {
	id: 'id',
	header: "ID",
	dataIndex: 'id',
	width: 64,
	sortable: true,
	renderer: moduleIdRender
}, {
	id: 'name',
	header: "模块名",
	dataIndex: 'name',
	width: 128,
	sortable: true,
	editor: new Ext.form.TextField({
	   allowBlank: false
	})
}, {
	id: 'status',
	header: "状态",
	dataIndex: 'status',
	align: 'center',
	width: 64,
	sortable: true,
	renderer: moduleStatusRender
}, {
	id: 'index',
	header: "顺序",
	dataIndex: 'index',
	align: 'center',
	sortable: true,
	width: 64
}, {
	id: 'configFile',
	header: "配置文件",
	dataIndex: 'configFile',
	sortable: true,
	width: 128
}, {
	id: 'createdTime',
	header: "创建时间",
	dataIndex: 'createdTime',
	sortable: true,
	width: 128
}, {
	id: 'updatedTime',
	header: "最后修改",
	dataIndex: 'updatedTime',
	sortable: true,
	width: 128
}]);

// create the grid
var moduleGrid = new Ext.grid.EditorGridPanel({
	id: 'module-grid',
	title: '模块管理',
	iconCls: 'icon-be-product',
	
	loadMask: true,
	clicksToEdit:2,
  collapsible: false,
  animCollapse: false,
  autoExpandColumn: 'updatedTime',
	
  store: moduleStore,
  
  sm: moduleSM,
  cm: productCM,
  //plugins: productExpander,
  //renderTo:'example-grid',
  //width:540,
  //height:200,
  bbar: new Ext.PagingToolbar({
    pageSize: 25,
    store: moduleStore,
    displayInfo: true,
    displayMsg: '第{0}-{1}条 / 共{2}条',
    emptyMsg: "没有纪录",
    items:['-', {
    	text: '修改配置',
    	iconCls: 'btn-modify',
    	handler: function() {
    		var count = moduleGrid.getSelectionModel().getSelections().length;
    		if(count==0) {
    			alert('Please select one record!');
    		}else if(count>1) {
    			alert('Select too many records!');
    		}else {
    			var record = moduleGrid.getSelectionModel().getSelected();
	    		//editProduct(record.data.id);
	    		alert('Not yet!');
	    	}
    	}
    }, '-', {
    	text: '刷新配置',
    	iconCls: 'btn-refresh',
    	handler: function() {
    		var records = moduleGrid.getSelectionModel().getSelections();
    		var count = records.length;
    		if(count==0) {
    			alert('Please select one record at least!');
    		}else {
    			var moduleIdArr = new Array(count);
    			for(var i=0;i<count;i++) {
    				moduleIdArr[i]=records[i].data.id;
    			}
    			var moduleId = moduleIdArr.toString();
    			Ext.MessageBox.confirm('操作确认', '确定从配置文件重新加载模块?', function(btn) {
    				if('yes'==btn) {
		    			Ext.Ajax.request({
								url: 'system/module.jxp',
								headers: {},
								params: {
									action: 'reloadModule',
									moduleId: moduleId
								},
								callback: function(options, success, reponse) {
									if(success) {
										alert('操作成功!');
									} else {
										alert('通讯失败!');
									}
								}
							});
    				}
    			});
	    	}
    	}
    }, '-', {
    	text: '激活',
    	iconCls: 'btn-on',
    	handler: function() {
    		alert('To be continued ...');
    	}
    }, '-', {
    	text: '关闭',
    	iconCls: 'btn-off',
    	handler: function() {
    		alert('To be continued ...');
    	}
    }, '-']
	})	
});

var moduleTabPanel = new Ext.TabPanel({
	id: 'system-module-panel',
	plain: true,  //remove the header border
	activeItem: 0,
	defaults: {bodyStyle: 'padding:0px'},
	items:[moduleGrid]
});

moduleTabPanel.on('show', function(panel) {
	moduleStore.load();
});


// Register Panel
registerPanel(moduleTabPanel);

