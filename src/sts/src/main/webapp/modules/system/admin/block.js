/*
 * Block management.
 * Copyright(c) 2008, iCanWear.com
 * icanwear@hotmail.com
 * 
 * http://iCanWear.com/license
 */

var Block = Ext.data.Record.create([
	'id', 'name', 'index', 'singleton', 'colspan', 'rowspan', 'module', 'pages',  'description'
]);

// create the Data Store
var blockStore = new Ext.data.Store({
	// load using HTTP
	url: 'system/block.jxp',
	
	// the return will be XML, so lets set up a reader
	reader: new Ext.data.XmlReader({
		id: 'id',
		record: 'block',
		totalRecords: 'totalCount'
	}, Block),
	
	baseParams: {action: 'list'},
	listeners: {
		'beforeload' : function(){
			this.baseParams.module = Ext.getCmp('search-module').getValue();
		}
	}
});

var Page4Block = Ext.data.Record.create([
	'id', 'name', 'instances', 'current', 'description'
]);
var pageStore4Block = new Ext.data.Store({
	// load using HTTP
	url: 'system/page.jxp',
	
	// the return will be XML, so lets set up a reader
	reader: new Ext.data.XmlReader({
		id: 'id',
		record: 'page',
		totalRecords: 'totalCount'
	}, Page4Block),
	baseParams: {action: 'list'},
	listeners: {
		'load' : function(store, records, options) {
		}
	}
});
	
var Setting = Ext.data.Record.create([
	'id', 'name', 'key', 'value', 'usage'
]);

var singletonRenderer = function(singleton) {
	if(singleton) {
		return '<img src="../resources/icons/silk/gif/application.gif" title="一个页面上只允许出现一个!" />';
	} else {
		return '<img src="../resources/icons/silk/gif/application_double.gif" title="一个页面上可以出现多个! />';
	}
};
var blockSM = new Ext.grid.CheckboxSelectionModel();
var blockCM = new Ext.grid.ColumnModel([
	new Ext.grid.RowNumberer(), 
	blockSM, 
{	
	header: "区块名称",
	dataIndex: 'name',
	width: 120
},{	
	header: "模块",
	dataIndex: 'module',
	width: 120
},{	
	header: "多个?",
	align: 'center',
	dataIndex: 'singleton',
	renderer: singletonRenderer,
	width: 64
},{	/*
	header: "列宽",
	dataIndex: 'colspan',
	width: 64
},{	
	header: "行高",
	dataIndex: 'rowspan',
	width: 64
},{	*/
	id: 'pages',
	header: "显示",
	dataIndex: 'pages',
	width: 160
},{	
	header: "备注",
	dataIndex: 'description',
	width: 160
}]);

// create the Data Store
var moduleSelectListStore = new Ext.data.Store({
  // load using HTTP
  url: 'system/module.jxp',

  // the return will be XML, so lets set up a reader
  reader: new Ext.data.XmlReader({
		id: 'id',
		record: 'module'
	}, Module),
	
	baseParams: {action: 'selectList'}
});
// Add Page to block
var addDisplayPageCB = new Ext.form.ComboBox({
	id: 'add-display-page',
	width:128,
	listClass:'x-combo-list-small',
	//value:'所有页面',
	id:'search-module',
	emptyText: '选择页面...',
	store: new Ext.data.Store({
		url: 'system/page.jxp',
		reader: new Ext.data.XmlReader({
			id: 'id',
			record: 'page',
			totalRecords: 'totalCount'
		}, ['id', 'name']),
		baseParams: {action: 'list', type: 'short'}
	}),
    //mode: 'local',
    triggerAction: 'all',
	displayField: 'name',
	valueField: 'id' 
});
// View by module
var viewByModuleCB = new Ext.form.ComboBox({
	width:128,
	listClass:'x-combo-list-small',
	//value:'所有模块',
	id:'search-module',
	emptyText: '所有模块',
	store: moduleSelectListStore,
    //mode: 'local',
    triggerAction: 'all',
	displayField: 'name',
	valueField: 'id' 
});
viewByModuleCB.on('select', function(combo, record, index) {
	blockStore.load({params:{start:0,limit:25}});
});

// create the grid
var blockGrid = new Ext.grid.EditorGridPanel({
	id: 'system-block-grid',
	title: '区块列表',
	iconCls: 'icon-be-product',
	
	clicksToEdit:1,
	collapsible: false,
	animCollapse: false,
	autoExpandColumn: 'pages',
	
	store: blockStore,
	
	sm: blockSM,
	cm: blockCM,
	//plugins: productExpander,
	//renderTo:'example-grid',
	//width:540,
	//height:200,
	tbar: [ 
		'增加显示页面:', 
		addDisplayPageCB, '-', {
			text: '增加',
			handler: function() {
				//var page = Ext.getCmp('add-display-page').getValue();
				alert(addDisplayPageCB.getValue());
			}
		}, '-', '->','按模块浏览: ', ' ', viewByModuleCB
	],
	bbar: new Ext.PagingToolbar({
		pageSize: 25,
		store: blockStore,
		displayInfo: true,
		beforePageText: '第',
		afterPageText: '页 / 共{0}页',
		displayMsg: '第{0}-{1}条 / 共{2}条',
		emptyMsg: "没有纪录",
		items:['-', {
			text: '配置',
			handler: function() {
				var count = blockGrid.getSelectionModel().getSelections().length;
	    		if(count==0) {
	    			alert('Please select one record!');
	    		}else if(count>1) {
	    			alert('Select too many records!');
	    		}else {
	    			var record = blockGrid.getSelectionModel().getSelected();
		    		var tabPanel = Ext.getCmp('system-block-panel');
		    		if(tabPanel) {
		    			var panel = tabPanel.findById('system-block-page-grid');
		    			if(!panel) {
		    				panel = CreateBlockPagesGrid(record.data);
		    				tabPanel.add(panel);
						}
		    			panel.setTitle('区块配置 :: '+record.data.name);
		    			//panel.disabled=false;
						tabPanel.activate(panel);
						pageStore4Block.baseParams.prototype = record.data.id;
						pageStore4Block.load({params: {start:0, limit:25}});
		    		} else {
		    			alert('ERROR: Null TabPanel!');
		    		}
		    	}
			}
		}, '-', {
			text: '所有页面显示'
		}, '-', {
			text: '取消所有显示'
		}]
	})	
});

function CreateBlockPagesGrid(block) {
	
	// Block Page Grid ////////////////////////////////
	var blockPageSM = new Ext.grid.CheckboxSelectionModel({
		listeners: {
			'selectionchange' : function(selMod) {
				var count = selMod.getCount();
				var btn1 = Ext.getCmp('btn-block-customdisplay');
				if(btn1) {
					//alert('btn1-'+count);
					if(count==1) {
						btn1.enable();
					} else {
						btn1.disable();
					}
				}
				var btn2 = Ext.getCmp('btn-block-add2page');
				if(btn2) {
					//alert('btn2');
					btn2.enable();
				}
			}
		}
	});
	var instancesRenderer = function(num) {
		if(!num || num==0) {
			return '不显示';
		} else {
			return num;
		}
	};
	var blockPageCM = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(), 
		blockPageSM, 
	{	
		id: 'name',
		header: '页面',
		sortable: true,
		dataIndex: 'name',
		width: 120
	}, {
		id: 'instances',
		sortable: true,
		header: '显示多个',
		dataIndex: 'instances',
		renderer: instancesRenderer,
		editor: new Ext.form.NumberField({
            allowBlank: false,
            allowNegative: false,
            minValue: 0
        }),
		width: 64
	}, {
		id: 'current',
		header: '当前显示区块',
		dataIndex: 'current',
		width: 120/*
	}, {
		id: 'description',
		header: '说明',
		dataIndex: 'description',
		width: 120*/
	}]);	
	/* var addPageCB = new Ext.form.ComboBox({
		width:128,
		listClass:'x-combo-list-small',
		//value:'所有模块',
		id:'search-module',
		emptyText: '选择页面...',
		store: pageStore,
	    //mode: 'local',
	    triggerAction: 'all',
		displayField: 'name',
		valueField: 'id' 
	});
	addPageCB.on('select', function(combo, record, index) {
		alert('');
	}); */
	var blockPageGrid = new Ext.grid.EditorGridPanel({
		id: 'system-block-page-grid',
		title: '请选择区块配置...',
		
	    //region:'center',
		iconCls: 'icon-be-product',
		floatable: false,
		margins: '5 5 0 0',
		cmargins: '5 5 0 0',
		bodyStyle: 'padding:0px',
		defaultSortable: true,
		
		//disabled: true,
	    closable: true,
		clicksToEdit: 1,
		collapsible: false,
		//animCollapse: false,
		autoExpandColumn: 'current',
		
		store: pageStore4Block,
		
		sm: blockPageSM,
		cm: blockPageCM,
		
		/*tbar: [ '显示页面: ', ' ', addPageCB, ' ', '-', {
			text: '<font color="Blue">添加</font>'
		}],*/
		
		bbar: new Ext.PagingToolbar({
			pageSize: 25,
			store: pageStore4Block,
			displayInfo: true,
			displayMsg: '第{0}-{1}条 / 共{2}条',
			emptyMsg: "没有纪录",
			items:['-', {
				id: 'btn-block-customdisplay',
				disabled: true,
				text: '自定义显示',
				handler: function() {
					var btn = Ext.getCmp('btn-block-save');
					if(btn) { // Enable Save Button 
						btn.enable();
					}
				}
			}, '-', {
				id: 'btn-block-add2page',
				text: '添加到页面',
				disabled: true,
				handler: function() {
		    		var records = blockPageGrid.getSelectionModel().getSelections();
		    		var count = records.length;
		    		if(count==0) {
		    			Ext.MessageBox.confirm('操作确认', '确定不显示区块？', function(btn) {
		    				if('yes'!=btn) {
		    					return;
		    				}
		    			});
		    		}
	    			var pageIdArr = new Array(count);
	    			for(var i=0;i<count;i++) {
	    				pageIdArr[i]=records[i].data.id;
	    			}
	    			var pageId = pageIdArr.toString();
	    			Ext.Ajax.request({
						url: 'system/block.jxp',
						headers: {},
						params: {
							action: 'addPages',
							block: block.id,
							page: pageId
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
			}]//, '-', '<font color="Red"> &lt;&lt;- 注意: 修改后请点击保存!</font>']
		})
	});
	
	blockPageGrid.on('beforeclose', function(panel) {
		
	});
	
	blockPageGrid.on('afteredit', function(e) {
		var originalValue = e.originalValue;
		var value = e.value;
		var grid = e.grid;
		var row = e.row;
		var record = e.record;
		// 不能随意减少多个显示中的一个
		if (originalValue > 1 && value >0 && value < originalValue) {
			alert('请到"页面管理" - "配置"中删除指定的区块!');
			return;
		}	
		// 显示数为0表示不显示
		if (value == 0) {
			//blockPageSM.deselectRow(row);
			alert('删除...');
			return;
		}
		// 显示为1
		if (value == 1) {
			//blockPageSM.selectRow(row, true);
			//return;
		}	
		// 检查Singleton
		if (value > 1 && block.singleton) {
			alert('只能添加一个本区块到在同一页面!');
			return;
		}			
		// 增加显示数
		Ext.Ajax.request({
			url: 'system/block.jxp',
			method: 'post',
			params: { 
				action: 'addInstance',
				block: block.id,
				page: record.data.id,
				number: value
			},
			callback: function(options, success, reponse) {
				if(success) {
					alert('保存成功!');
				} else {
					alert('通讯失败!');
				}
			}
		});
	});
	
	return blockPageGrid;

};

// Block Management TabPanel
var blockTabPanel = new Ext.TabPanel({
	id: 'system-block-panel',
	plain: true,  //remove the header border
	activeItem: 0,
	bodyStyle:'padding:0px',
	defaults: {bodyStyle: 'padding:0px'},
	items:[blockGrid]
});
blockTabPanel.on('show', function(panel) {
	blockStore.load({params:{start:0,limit:25}});
});

// Register Panel
registerPanel(blockTabPanel);