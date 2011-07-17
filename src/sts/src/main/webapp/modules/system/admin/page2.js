/*
 * Page management.
 * Copyright(c) 2008, iCanWear.com
 * icanwear@hotmail.com
 * 
 * http://iCanWear.com/license
 */
Ext.ns('Ext.jxsite');
// ============================================================== // 
// Border Layout Panel :: Page Configure Form with Block Grid === //
// ============================================================== //
Ext.jxsite.PageFormPanel = function(config) {
	config = config || {};
	var page = config.page;
	
	var pageForm = new Ext.form.FormPanel({
		id: 'system-page-form-'+page.id,
	    labelAlign: 'side',
	    labelWidth: 75,
	    //frame:true,
		iconCls: 'icon-be-product',
	    title: '页面属性',// :: '+page.name,
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
	        fieldLabel:'页面名称',
	        listeners: {
		    	'change': function(field, newValue, oldValue) {
			    	var tab = Ext.getCmp('system-page-layout-'+page.id);
			    	if(tab) {
			    		tab.setTitle(newValue);
			    	}
			    }
		    }
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
	    	height: 64,
	        name:'style',
	        fieldLabel:'自定义'
	    },{
	    	xtype: 'textarea',
	    	height: 128,
	        name:'description',
	        fieldLabel:'备注'
	    }],
	
	    buttons: [{
	        text: '保存',
	        handler: function() {
	    		pageForm.getForm().submit({
	    			url: 'system/page.jxp',
	    			params: {action: 'save'},
	    			method: 'post',
	    			success: function(form, action) {
	    				var result = action.result;
	    				alert('页面 [' + result.data.name + '] 保存成功!')
	    			},
	    			failure: function(form, action) {
	    				
	    			}
	    		});
		    }
	    },{
	        text: '重置'
	    },{
	        text: '取消'
	    }]
	});
	this.pageForm = pageForm; // Public Property
	
	// Page blocks
	var Instance = Ext.data.Record.create([ 
	  'id', 'name', 'index', 'prototype', 'colspan', 'rowspan', 'module', 'pages',  'description'
	]);
	var pageBlockStore = new Ext.data.Store({
		url: 'system/page.jxp',
		reader: new Ext.data.XmlReader({
			id: 'id',
			record: 'instance',
			totalRecords: 'totalCount'
		}, Instance),		
		baseParams: {
			action: 'getInstances',
			id: page.id
		}
	});

	var allPrototypesStore = new Ext.data.Store({
		autoLoad: true,
		url: 'system/block.jxp',
		baseParams: {action: 'list', type: 'prototype'},
		reader: new Ext.data.XmlReader({
			id: 'id',
			record: 'block',
			totalRecords: 'totalCount'
		}, ['id', 'name'])
	});
	var prototypeRenderer = function(id) {
		if(id) {
			var prototype = allPrototypesStore.getById(id);
			if(prototype) {
				return prototype.data.name;
			}
		}
		return id;
	};
	var pageBlockSM = new Ext.grid.CheckboxSelectionModel();
	var pageBlockCM = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(), 
		pageBlockSM, 
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
		id: 'prototype',
		header: "模板",
		dataIndex: 'prototype',
		width: 120,
		renderer: prototypeRenderer,
		editor: new Ext.form.ComboBox({
			//width:128,
			listClass:'x-combo-list-small',
			//value:'所有页面',
			emptyText: '选择模板...',
			store: allPrototypesStore,
		    //mode: 'local',
		    triggerAction: 'all',
			displayField: 'name',
			valueField: 'id',
			allowBlank: false
		})
	},{	
		header: "顺序",
		dataIndex: 'index',
		width: 64,
		editor: new Ext.ux.form.Spinner({
			name: 'index',
			strategy: new Ext.ux.form.Spinner.NumberStrategy({
				minValue:'1'//, maxValue:'130'
			})
	  	})
	},{	
		header: "列宽",
		dataIndex: 'colspan',
		width: 64,
		editor: new Ext.ux.form.Spinner({
			name: 'colspan',
			strategy: new Ext.ux.form.Spinner.NumberStrategy({
				minValue:'1'//, maxValue:'130'
			})
	  	})
	},{	
		header: "行高",
		dataIndex: 'rowspan',
		width: 64,
		editor: new Ext.ux.form.Spinner({
			name: 'rowspan',
			strategy: new Ext.ux.form.Spinner.NumberStrategy({
				minValue:'1'//, maxValue:'130'
			})
	  	})
	}]);
	var pageBlockGrid = new Ext.grid.EditorGridPanel({
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
		autoExpandColumn: 'name',
		
		store: pageBlockStore,
		
		sm: pageBlockSM,
		cm: pageBlockCM,
		
		bbar: new Ext.PagingToolbar({
			pageSize: 25,
			store: pageBlockStore,
			displayInfo: true,
			displayMsg: '第{0}-{1}条 / 共{2}条',
			emptyMsg: "没有纪录",
			items:['-', {
				text: '增加',
				handler: function() {
					var ins = new Instance({
						name: '新增区块',
						prototype: '',
						index: 1,
						colspan: 1,
						rowspan: 1
					});
					pageBlockGrid.stopEditing();
					pageBlockGrid.getStore().insert(0, ins);
					pageBlockGrid.startEditing(0, 3);
				}
			}, '-', {
				text: '配置',
				handler: function() {
					var count = pageBlockGrid.getSelectionModel().getSelections().length;
		    		if(count==0) {
		    			alert('Please select one record!');
		    		}else if(count>1) {
		    			alert('Select too many records!');
		    		}else {
		    			var record = pageBlockGrid.getSelectionModel().getSelected();
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
			    	}
				}
			}, '-', {
				id: 'btn-page-block-delete',
				text: '删除',
				handler: function() {
					var count = pageBlockSM.getSelections().length;
		    		if(count==0) {
		    			alert('Please select one record!');
		    		}else if(count>=1) {
						Ext.MessageBox.confirm('操作确认', '确认删除所选区块?', function(btn, text) {
							//alert(btn);
							if (btn == 'yes') {
				    			var instanceIdArr = new Array(count);
				    			for (var i=0; i<count; i++) {
				    				instanceIdArr[i] = pageBlockSM.getSelections()[i].data.id;
				    			}
				    			//alert(instanceIdArr);
				    			//return;
					    		// Basic request
					    		Ext.Ajax.request({
					    			url: 'system/page.jxp',
					    			success: function() {
					    				pageBlockStore.load({params: {start:0,limit:25}});
					    			},
					    			//failure: otherFn,
					    			headers: {
					    			//'my-header': 'foo'
					    			},
					    			params: { 
					    				action: 'removeInstance',
					    				id: page.id,
					    				instance: instanceIdArr.toString()
					    			}
					    		});
							}
						});
					}
				}//~handler
			}]
		})
	});
	this.pageBlockGrid = pageBlockGrid;
	this.pageBlockGrid.on('afteredit', function(ev) {
		var record = ev.record;
		var field = ev.field;
		var value = ev.value;
		var action = 'configInstance';
		if ('prototype' == field) {
			if (record.data.id) {
				alert('模板不能修改!');
				return;
			}
			action = 'addInstance';
		}
		// Basic request
		Ext.Ajax.request({
			url: 'system/page.jxp',
			success: function() {
				pageBlockStore.load({params: {start:0,limit:25}});
			},
			//failure: otherFn,
			headers: {
			//'my-header': 'foo'
			},
			params: { 
				action: action,
				id: page.id,
				instance: record.data.id,
				field: field,
				value: value
			}
		});
	});
	
	config = Ext.apply(config || {}, {
		id:'system-page-layout-'+page.id,
		title: page.name,
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
	    items: [this.pageForm, this.pageBlockGrid]
	});
	Ext.jxsite.PageFormPanel.superclass.constructor.apply(this, arguments);
};
Ext.extend(Ext.jxsite.PageFormPanel, Ext.Panel, {
	loadForm : function(data) {
		var pageBlockGrid = this.pageBlockGrid;
		this.pageForm.getForm().load({
	    	method: 'post',
	    	url:'system/page.jxp', 
	    	params: { 
	    		action: 'load', 
	    		id: data.id
	    	},
	    	success: function(f, a) {
	    		pageBlockGrid.getStore().load({params: {start:0,limit:25}});
	    	},
			waitMsg:'Loading ... '
		});
	}
});

// ============================================================== // 
// Grouping Grid :: Page Grid =================================== //
// ============================================================== //
Ext.jxsite.PageGrid = function(config) {
	if (!config) {
		config = {};
	}	
	var Page = Ext.data.Record.create([ 'id', 'name', 'module', 'mindex', 'current', 'url', 'description' ]);
	var pageStore = new Ext.data.GroupingStore({
		url: 'system/page.jxp',
		baseParams: {action: 'list'},		
		reader: new Ext.data.XmlReader({
			id: 'id',
			record: 'page',
			totalRecords: 'totalCount'
		}, Page),		
		listeners: {
			'beforeload' : function(){
			}
		},		
	    sortInfo:{field: 'url', direction: "ASC"},
	    groupField:'module'
	});

	var singletonRenderer = function(singleton) {
		if(singleton) {
			return '<img src="../resources/icons/silk/gif/application.gif" title="!" />';
		} else {
			return '<img src="../resources/icons/silk/gif/application_double.gif" title="! />';
		}
	};

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
		pageStore.load({params:{start:0,limit:25}});
	});

	var pageSM = new Ext.grid.CheckboxSelectionModel();
	pageSM.on('selectionchange', function(selMod) {
		/*var btn = Ext.getCmp('btn-block-prototype-config');
		if(btn) {
			if(selMod.getCount() == 1) {
				var record = selMod.getSelected();
				if(record.data.instances <= 1) {
					btn.enable();
					return;
				}
			} 
			btn.disable();
		}*/
	});
	var pageCM = new Ext.grid.ColumnModel([	new Ext.grid.RowNumberer(),	pageSM, {
		id: 'name',
		header: "页面",
		dataIndex: 'name',
		width: 120
    },{	
    	header: '模块',
    	dataIndex: 'module',
    	width: 100
	},{
		id: 'url',
		header: "地址",
		dataIndex: 'url',
		width: 120
	},{
		id: 'current',
		header: "当前区块",
		dataIndex: 'current',
		width: 120
	},{
		id: 'description',
		header: "说明",
		dataIndex: 'description',
		width: 120
	}]);

	Ext.jxsite.PageGrid.superclass.constructor.call(this, {
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
        
        view: new Ext.grid.GroupingView({
            forceFit:true,
            groupTextTpl: '{text} <font stlye="font-weight:normal;">(共{[values.rs.length]}个)</font>'
        }),
        
        tbar: [ '快速搜索: ', new Ext.ux.SearchField({
            width:160,
			store: pageStore,
			restore: function() {
        		pageStore.load({pararms:{start:0,limit:25}});
	        },
			paramName: 'q'
        }), '->','按模块浏览: ', ' ', viewByModuleCB ],
		
		bbar: new Ext.PagingToolbar({
			pageSize: 25,
			store: pageStore,
			displayInfo: true,
			displayMsg: '第{0}-{1}条 / 共{2}条',
			emptyMsg: "没有纪录",
			items:['-', {
				text: '配置',
				handler: function() {
					var count = pageSM.getSelections().length;
		    		if(count==0) {
		    			alert('Please select one record!');
		    		}else if(count>1) {
		    			alert('Select too many records!');
		    		}else {
		    			var record = pageSM.getSelected();
			    		var tabPanel = Ext.getCmp('system-page-panel');
			    		if(tabPanel) {
							var panel = tabPanel.findById('sytem-page-layout-'+record.data.id);
							if(!panel) {		
								//create the panel
								panel = new Ext.jxsite.PageFormPanel({page: record.data});
								tabPanel.add(panel);
							}
							tabPanel.activate(panel.id);
							panel.loadForm(record.data);
			    		} else {
			    			alert('ERROR: Null TabPanel!');
			    			return;
			    		}			    		
			    		/**/
			    	}
				}
			}, '-', {
				text: '显示'
			}, '-', {
				text: '不显示'
			}]
		})
	});
};
Ext.extend(Ext.jxsite.PageGrid, Ext.grid.GridPanel, {});

//=============================================================== // 
// TabPanel :: Page Management Tabs ============================= //
//=============================================================== //
// Tab 0 - pages grid tab
var pageGrid = new Ext.jxsite.PageGrid();
// Page Management TabPanel
var pageTabPanel = new Ext.TabPanel({
	id: 'system-page-panel',
	plain: true,  //remove the header border
	activeItem: 0,
	bodyStyle:'padding:0px',
	defaults: {bodyStyle: 'padding:0px'},
	items:[ pageGrid ]
});
pageTabPanel.on('show', function(panel) {
	pageGrid.getStore().load({params:{start:0,limit:25}});
});

// Register Panel
registerPanel(pageTabPanel);