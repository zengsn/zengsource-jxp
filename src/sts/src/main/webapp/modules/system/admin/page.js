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
var Page = Ext.data.Record.create([
	'id', 'name', 'url', 'description'
]);
var Setting = Ext.data.Record.create([
	'id', 'name', 'key', 'value', 'usage'
]);
var pageStore = new Ext.data.Store({
	// load using HTTP
	url: 'system/page.jxp',
	
	// the return will be XML, so lets set up a reader
	reader: new Ext.data.XmlReader({
		id: 'id',
		record: 'page',
		totalRecords: 'totalCount'
	}, Page),
	baseParams: {action: 'list'},
	listeners: {
		'load' : function(store, records, options) {
		}
	}
});
	
// create the Data Store
var blockSelectedStore = new Ext.data.Store({
	// load using HTTP
	url: 'system/block.jxp',
	
	// the return will be XML, so lets set up a reader
	reader: new Ext.data.XmlReader({
		id: 'id',
		record: 'block',
		totalRecords: 'totalCount'
	}, Block),
	
	baseParams: {action: 'list'}
});
		
var blockInsSettingsStore = new Ext.data.Store({
	// load using HTTP
	url: 'system/block.jxp',
	
	// the return will be XML, so lets set up a reader
	reader: new Ext.data.XmlReader({
		id: 'id',
		record: 'setting',
		totalRecords: 'totalCount'
	}, Setting),
	baseParams: {action: 'listSettings', id: ''},
	listeners: {
		'beforeload' : function(){
			//this.baseParams.module = Ext.getCmp('search-module').getValue();
		},
		'load' : function() {
			//blockPagesStore.load();
		}
	}
});

//var pageStore = // see block.js	

var pageSM = new Ext.grid.CheckboxSelectionModel();
var pageCM = new Ext.grid.ColumnModel([
	new Ext.grid.RowNumberer(), 
	pageSM, 
{	
	id: 'name',
	header: "页面",
	dataIndex: 'name',
	width: 120
},{	
	id: 'url',
	header: "地址",
	dataIndex: 'url',
	width: 120
},{	
	id: 'description',
	header: "说明",
	dataIndex: 'description',
	width: 120,
	editor: new Ext.form.TextField({
		allowBlank: true
	})
}]);

// Page Grid
var pageGridPanel = new Ext.grid.EditorGridPanel({
	id: 'system-page-grid',
	title: '页面列表',
	
    //region:'center',
	iconCls: 'icon-be-product',
	floatable: false,
	margins: '5 0 5 5',
	cmargins: '5 5 0 0',
	bodyStyle: 'padding:0px',
	
	clicksToEdit:1,
	collapsible: false,
	//animCollapse: false,
	autoExpandColumn: 'description',
	
	store: pageStore,
	
	sm: pageSM,
	cm: pageCM,
	
	bbar: new Ext.PagingToolbar({
		pageSize: 25,
		store: pageStore,
		displayInfo: true,
		displayMsg: '第{0}-{1}条 / 共{2}条',
		emptyMsg: "没有纪录",
		items:['-', {
			text: '配置',
			handler: function() {
				var count = pageGridPanel.getSelectionModel().getSelections().length;
	    		if(count==0) {
	    			alert('Please select one record!');
	    		}else if(count>1) {
	    			alert('Select too many records!');
	    		}else {
	    			var record = pageGridPanel.getSelectionModel().getSelected();
		    		var tabPanel = Ext.getCmp('system-page-panel');
		    		if(tabPanel) {
						var panel = tabPanel.findById('sytem-page-layout-'+record.data.id);
						if(!panel) {		
							//create the panel
							panel = CreatePageFormLayout(record.data);
							tabPanel.add(panel);
						}
						tabPanel.activate(panel.id);
		    		} else {
		    			alert('ERROR: Null TabPanel!');
		    			return;
		    		}
		    		
					Ext.getCmp('system-page-form-'+record.data.id).getForm().load({
			        	method: 'post',
			        	url:'system/page.jxp', 
			        	params: { 
			        		action: 'load', 
			        		id: record.data.id
			        	},
			        	success: function(f, a) {
			        		blockSelectedStore.load({params: {start:0,limit:25}});
			        	},
			    		waitMsg:'Loading ... '
			    	});
		    	}
			}
		}, '-', {
			text: '显示'
		}, '-', {
			text: '不显示'
		}]
	})
});

function CreatePageFormLayout(page) {
	if(!page) page = {};
	if(!page.id) page.id = 'Unknown';
	
	// Page Form ///////////////////////////////////////////	
	
	var pageForm = new Ext.form.FormPanel({
		id: 'system-page-form-'+page.id,
	    labelAlign: 'side',
	    labelWidth: 75,
	    //frame:true,
		iconCls: 'icon-be-product',
	    title: '页面 :: '+page.name,
	    region:'west',
		margins: '5 0 0 5',
		bodyStyle:'padding:10px',
		width: 360,
        minSize: 320,
        maxSize: 400,
		collapsible: true,
	    autoScroll: true,
		defaults: {
	        width: 230,
			collapsible: false,
	        split: true,
			animFloat: false,
			autoHide: false,
			useSplitTips: true
		},
	    
	    defaultType: 'textfield',
	    waitMsgTarget: true,

	    // configure how to read the XML Data
	    reader : new Ext.data.XmlReader({
	        record : 'Page',
	        success: '@success'
	    }, [
	        'id', 'name', 'columns', 'cls', 'style', 'description'
	    ]),
	
	    // reusable eror reader class defined at the end of this file
	    errorReader: new Ext.form.XmlErrorReader(),
	    
	    //width: 600,
	    items: [{
	        name:'id',
	        xtype: 'hidden'
	    },{
	        name:'name',
	        fieldLabel:'页面名称'
	    },new Ext.ux.form.Spinner({
			fieldLabel: '分栏列数',
			name: 'columns',
			strategy: new Ext.ux.form.Spinner.NumberStrategy({
				minValue:'1'//, maxValue:'130'
			})
	  	}),{
	        name:'cls',
	        fieldLabel:'CSS类'
	    },{
	    	xtype: 'textarea',
	    	height: 48,
	        name:'style',
	        fieldLabel:'自定义'
	    },{
	    	xtype: 'textarea',
	    	height: 48,
	        name:'description',
	        fieldLabel:'备注'
	    }],
	
	    buttons: [{
	        text: '保存'
	    },{
	        text: '重置'
	    },{
	        text: '取消'
	    }]
	});		
	
	// Block Selecting Grid ////////////////////////////////
	blockSelectedStore.baseParams.page = page.id; // send id
	var blockSelectingSM = new Ext.grid.CheckboxSelectionModel();
	var blockSelectingCM = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(), 
		blockSelectingSM, 
	{	
		id: 'name',
		header: "区块",
		dataIndex: 'name',
		width: 120,
		editor: new Ext.form.TextField({
			name: 'name',
			allowBlank: false
		})
	},{	
		header: "顺序",
		dataIndex: 'index',
		width: 120,
		editor: new Ext.ux.form.Spinner({
			name: 'index',
			strategy: new Ext.ux.form.Spinner.NumberStrategy({
				minValue:'1'//, maxValue:'130'
			})
	  	})
	},{	
		header: "列宽",
		dataIndex: 'colspan',
		width: 120,
		editor: new Ext.ux.form.Spinner({
			name: 'colspan',
			strategy: new Ext.ux.form.Spinner.NumberStrategy({
				minValue:'1'//, maxValue:'130'
			})
	  	})
	},{	
		header: "行高",
		dataIndex: 'rowspan',
		width: 120,
		editor: new Ext.ux.form.Spinner({
			name: 'rowspan',
			strategy: new Ext.ux.form.Spinner.NumberStrategy({
				minValue:'1'//, maxValue:'130'
			})
	  	})
	}]);
	var blockSelectingGrid = new Ext.grid.EditorGridPanel({
		id: 'system-page-block-'+page.id,
		title: '区块显示',
		
	    region:'center',
		iconCls: 'icon-be-product',
		floatable: false,
		margins: '5 5 5 0',
		cmargins: '5 5 0 0',
		bodyStyle: 'padding:0px',
		
		clicksToEdit:1,
		collapsible: false,
		//animCollapse: false,
		//autoExpandColumn: 'name',
		
		store: blockSelectedStore,
		
		sm: blockSelectingSM,
		cm: blockSelectingCM,
		
		bbar: new Ext.PagingToolbar({
			pageSize: 25,
			store: blockSelectedStore,
			displayInfo: true,
			displayMsg: '第{0}-{1}条 / 共{2}条',
			emptyMsg: "没有纪录",
			items:['-', {
				text: '配置',
				handler: function() {
					var count = blockSelectingGrid.getSelectionModel().getSelections().length;
		    		if(count==0) {
		    			alert('Please select one record!');
		    		}else if(count>1) {
		    			alert('Select too many records!');
		    		}else {
		    			var record = blockSelectingGrid.getSelectionModel().getSelected();
			    		var tabPanel = Ext.getCmp('system-page-panel');
			    		if(tabPanel) {
							var panel = tabPanel.findById('system-blockins-layout-'+record.data.id);
							if(!panel) {
								//create the panel
								panel = CreateBlockInsFormLayout(page, record.data);
								tabPanel.add(panel);
							}
							tabPanel.activate(panel.id);					    	
			    		} else {
			    			alert('ERROR: Null TabPanel!');
			    			return;
			    		}
		    		
		    			blockInsSettingsStore.baseParams.id = record.data.id;
		    			blockInsSettingsStore.baseParams.type = 'instance';
						Ext.getCmp('system-blockins-form-'+record.data.id).getForm().load({
				        	method: 'post',
				        	url:'system/block.jxp', 
				        	params: { 
				        		action: 'load', 
				        		id: record.data.id
				        	},
				        	success: function(f, a) {
				        		blockInsSettingsStore.load({params: {start:0,limit:25}});
				        	},
				    		waitMsg:'Loading ... '
				    	});
			    	}
				}
			}/*, '-', {
				text: '<font color="Blue">前移</font>'
			}, '-', {
				text: '<font color="Blue">后移</font>'
			}, '-', {
				text: '<font color="Orange">加宽</font>'
			}, '-', {
				text: '<font color="Orange">缩窄</font>'
			}, '-', {
				text: '<font color="Green">加高</font>'
			}, '-', {
				text: '<font color="Green">降低</font>'
			}*/]
		})
	});
	
	blockSelectingGrid.on('afteredit', function(ev) {
		var record = ev.record;
		var field = ev.field;
		var value = ev.value;
		// Basic request
		Ext.Ajax.request({
			url: 'system/page.jxp',
			success: function() {
				blockSelectedStore.load({params: {start:0,limit:25}});
			},
			//failure: otherFn,
			headers: {
			//'my-header': 'foo'
			},
			params: { 
				action: 'assignBlock',
				id: page.id,
				block: record.data.id,
				field: field,
				value: value
			}
		});
	});
	
	var pageFormLayout = new Ext.Panel({
		id:'system-page-layout-'+page.id,
		title: '配置页面 :: '+page.name,
	    layout:'border',    
	    disabled: false,
	    closable: true,
	    bodyBorder: true,
	    autoScroll: true,
		iconCls: 'icon-be-product',
		defaults: {
			//collapsible: false,
	        split: true,
	        autoScroll: true,
			animFloat: false,
			autoHide: false,
			useSplitTips: true
		},
	    items: [pageForm, blockSelectingGrid]
	});
	
	return pageFormLayout;
};

function CreateBlockInsFormLayout(page, block) {
	if(!page) {
		alert("Null Page!");
		return;
	}
	if(!block) {
		alert("Null Block!");
		return;
	}
	
	// Block Config Form
	var blockInsForm = new Ext.form.FormPanel({
		id: 'system-blockins-form-'+block.id,
	    labelAlign: 'top',
	    labelWidth: 75, 
	    title: '区块属性',
	    region:'west',
		margins: '5 0 0 5',
	    width: 540,
	    minSize: 500,
	    maxSize: 600,
		collapsible: true,
	    autoScroll: true,
	    autoScroll: true,
		iconCls: 'icon-be-product',
	    bodyStyle:'padding:15px',
	    //defaults: {width: 230},
		//buttonAlign: 'right',
	    defaultType: 'textfield',
	    waitMsgTarget: true,

	    // configure how to read the XML Data
	    reader : new Ext.data.XmlReader({
	        record : 'Block',
	        success: '@success'
	    }, [
	        'id', 'name', 'index', 'module', 'colspan', 'rowspan', 'template', 'description'
	    ]),
	
	    // reusable eror reader class defined at the end of this file
	    errorReader: new Ext.form.XmlErrorReader(),
	
	    items: [{
	    	xtype: 'hidden',
	        name: 'id' 
	    },{
	        fieldLabel: '区块名称',
	        name: 'name',
	        width: 230,
	        allowBlank:false /*
	    },{ 
	        fieldLabel: '所属模块',
	        name: 'module',
	        width: 230,
	        disabled: true */
	    },new Ext.ux.form.Spinner({
			fieldLabel: '位置',
			name: 'index',
	        width: 230,
			strategy: new Ext.ux.form.Spinner.NumberStrategy({
				minValue:'1'//, maxValue:'130'
			})
	  	}),new Ext.ux.form.Spinner({
			fieldLabel: '列宽',
			name: 'colspan',
	        width: 230,
			strategy: new Ext.ux.form.Spinner.NumberStrategy({
				minValue:'1'//, maxValue:'130'
			})
	  	}),new Ext.ux.form.Spinner({
	        fieldLabel: '行高',
	        name: 'rowspan',
	        width: 230,
			strategy: new Ext.ux.form.Spinner.NumberStrategy({
				minValue:'1'//, maxValue:'130'
			})
	  	}),{
	        fieldLabel: '区块模板',
	        name: 'template',
	        width: 230,
	        disabled: true,
	        allowBlank:false 
	    }, {
	  		xtype:'htmleditor',
	        fieldLabel: 'HTML',
	        //width: 500,
	        height: 320,
	        name: 'html'
	    }],
	
	    buttons: [{
	        text: '保存',
	        handler: function() {
	        	var fp = Ext.getCmp('system-blockins-form-'+block.id);
	        	fp.getForm().submit({
	        		url:'system/block.jxp', 
	        		params: {action: 'save'},
	        		waitMsg:'正在保存...'
	        	});
	        }
	    },{
	        text: '取消'
	    }]
	});
	
	// Block Setting Grid //////////////////////
	
	var blockSettingCM = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
	{	
		id: 'name',
		header: "配置名",
		dataIndex: 'name',
		width: 120
	},{	
		id: 'value',
		header: "配置值",
		dataIndex: 'value',
		width: 120,
		editor: new Ext.form.TextField({
		   allowBlank: true
		})
	},{	
		id: 'usage',
		header: "使用说明",
		dataIndex: 'usage',
		width: 120
	}]);
	
	var blockSettingGrid = new Ext.grid.EditorGridPanel({
		id: 'system-blockins-settings-'+block.id,
		title: '区块内容显示配置',
		
	    region:'center',
		iconCls: 'icon-be-product',
		floatable: false,
		margins: '5 5 5 0',
		cmargins: '5 5 0 0',
		//height: 400,
        //minSize: 360,
        //maxSize: 420,
		bodyStyle: 'padding:0px',
		clicksToEdit:1,
		collapsible: false,
		//animCollapse: false,
		autoExpandColumn: 'usage',
		
		store: blockInsSettingsStore,
		
		//sm: blockPageSM,
		cm: blockSettingCM,
		
		bbar: new Ext.PagingToolbar({
			pageSize: 25,
			store: blockInsSettingsStore,
			displayInfo: true,
			displayMsg: '第{0}-{1}条 / 共{2}条',
			emptyMsg: "没有纪录",
			items:['-', {
				text: '<font color="Red">删除</font>'
			}]
		})
	});		
	
	var blockInsFormLayout = new Ext.Panel({
		id:'system-blockins-layout-'+block.id,
		title: '配置区块 :: '+block.name,
	    layout:'border',    
	    disabled: false,
	    closable: true,
	    bodyBorder: true,
	    autoScroll: true,
		iconCls: 'icon-be-product',
		defaults: {
			//collapsible: false,
	        split: true,
	        autoScroll: true,
			animFloat: false,
			autoHide: false,
			useSplitTips: true
		},
	    items: [blockInsForm, blockSettingGrid]
	});
	
	Ext.getCmp('system-blockins-layout-'+block.id).on('activate', function(){
		blockInsSettingsStore.load();
	});
	
	return blockInsFormLayout;
};

// Page Management TabPanel
var pageTabPanel = new Ext.TabPanel({
	id: 'system-page-panel',
	plain: true,  //remove the header border
	activeItem: 0,
	bodyStyle:'padding:0px',
	defaults: {bodyStyle: 'padding:0px'},
	items:[pageGridPanel]
});
pageTabPanel.on('show', function(panel) {
	pageStore.load({params:{start:0,limit:25}});
});

// Register Panel
registerPanel(pageTabPanel);