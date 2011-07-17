/*
 * Theme management.
 * Copyright(c) 2008, iCanWear.com
 * icanwear@hotmail.com
 * 
 * http://iCanWear.com/license
 */

var Theme = Ext.data.Record.create([
	'id',
	'name',
	'status'
]);

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
var themeStore = new Ext.data.Store({
  // load using HTTP
  url: 'system/theme.jxp',

  // the return will be XML, so lets set up a reader
  reader: new Ext.data.XmlReader({
		id: 'id',
		record: 'theme',
		totalRecords: 'totalCount'
	}, Module),
	
	sortInfo: {
		field: "name", direction: "ASC"
	},
	
	baseParams: {action: 'list'}
});

var themeStatusRender = function(val) {
	if(val==0) return '<font color="Gray">未使用</font>';
	if(val==1) return '<font color="Blue">使用中</font>';
	return val;
};

var themeSM = new Ext.grid.CheckboxSelectionModel();

var themeCM = new Ext.grid.ColumnModel([ 
	new Ext.grid.RowNumberer(),
	themeSM, 
{/*
	id: 'id',
	header: "ID",
	dataIndex: 'id',
	width: 64,
	sortable: true
}, { */
	id: 'name',
	header: "主题",
	dataIndex: 'name',
	width: 128,
	sortable: true
}, {
	id: 'status',
	header: "状态",
	dataIndex: 'status',
	align: 'center',
	width: 64,
	sortable: true,
	renderer: moduleStatusRender
}]);

// create the grid
var themeGrid = new Ext.grid.EditorGridPanel({
	id: 'theme-grid',
	title: '主题管理',
	iconCls: 'icon-be-product',
	
	loadMask: true,
	clicksToEdit:2,
	collapsible: false,
	animCollapse: false,
	autoExpandColumn: 'name',
	
	store: themeStore,
	
	sm: themeSM,
	cm: themeCM,
	//plugins: productExpander,
	//renderTo:'example-grid',
	//width:540,
	//height:200,
	bbar: new Ext.PagingToolbar({
	    pageSize: 25,
	    store: themeStore,
	    displayInfo: true,
	    displayMsg: '第{0}-{1}条 / 共{2}条',
	    emptyMsg: "没有纪录",
	    items:['-', {
	    	text: '修改',
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
	    	text: '激活',
	    	iconCls: 'btn-on',
	    	handler: function() {
	    		alert('To be continued ...');
	    	}
	    }, '-']
	})	
});

var themeTabPanel = new Ext.TabPanel({
	id: 'system-theme-panel',
	plain: true,  //remove the header border
	activeItem: 0,
	defaults: {bodyStyle: 'padding:0px'},
	items:[themeGrid]
});

themeTabPanel.on('show', function(panel) {
	themeStore.load({params:{start:0,limit:25}});
});


// Register Panel
registerPanel(themeTabPanel);

