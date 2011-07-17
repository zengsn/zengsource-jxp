/*
 * Block management: 
 * Ext.jxsite.BlockPrototypeGrid - View/Configure block prototype display.
 * Ext.jxsite.BlockInstancePanel - Configure block instance for multiple pages.
 * 
 * Copyright(c) 2008, iCanWear.com
 * icanwear@hotmail.com
 * http://iCanWear.com/license
 */

Ext.namespace('Ext.jxsite');

//=============================================================== // 
//Border Layout Panel :: Block Prototype for Multiple Pages ===== //
//=============================================================== //
Ext.jxsite.BlockPrototypePanel = function(config) {
	config = config || {};
	var prototype = config.prototype;
	var Instance = Ext.data.Record.create(['id', 'name', 'index', 'module', 'colspan', 'rowspan', 'cls', 'style', 'template', 'html', 'description']);
	// Instance Store
	var instanceStore = new Ext.data.Store({
	  url: 'system/block.jxp',
	  reader: new Ext.data.XmlReader({
			id: 'id',
			record: 'instance'
		}, Instance),		
		baseParams: {
			action: 'getInstances',
			id: prototype.id
		}
	});
	var instanceCombo = new Ext.form.ComboBox({
		width:230,
		//value:'所有模块',
		//id:'instance-combo',
		name: 'name',
		fieldLabel: '区块名称',
		emptyText: '选择实例',
		store: instanceStore,
		listClass:'x-combo-list-small',
	    //mode: 'local',
	    triggerAction: 'all',
		displayField: 'name',
		valueField: 'name' 
	});
	var blockPanel = this;
	instanceCombo.on('select', function(combo, record, index) {
		//alert('Not yet! ' + record.data.name);
		blockPanel.loadForm({
			id: record.data.id,
			type: 'instance'
		});
	});
	// Block Instance Property Form
	var propertyFormId = 'system-blkprototype-form-' + prototype.id;
	this.propertyForm = new Ext.form.FormPanel({
		id: propertyFormId,
	    labelAlign: 'side',
	    labelWidth: 75, 
	    title: '区块属性',
	    region:'center',
		margins: '5 0 0 5',
		//collapsible: true,
	    autoScroll: true,
		iconCls: 'icon-be-product',
	    bodyStyle:'padding:15px',
	    //defaults: {width: 230},
		//buttonAlign: 'right',
	    defaultType: 'textfield',
	    waitMsgTarget: true,

	    // configure how to read the XML Data
	    reader : new Ext.data.XmlReader({
	        record : 'instance',
	        success: '@success'
	    }, Instance),
	
	    // reusable eror reader class defined at the end of this file
	    errorReader: new Ext.form.XmlErrorReader(),
	
	    items: [{
	    	xtype: 'hidden',
	        name: 'id' 
	    }, instanceCombo, /*{
	        fieldLabel: '区块名称',
	        name: 'name',
	        width: 230,
	        allowBlank:false 
	    },{ 
	        fieldLabel: '所属模块',
	        name: 'module',
	        width: 230,
	        disabled: true 
	    },*/new Ext.ux.form.Spinner({
			fieldLabel: '区块位置',
			name: 'index',
	        width: 230,
			strategy: new Ext.ux.form.Spinner.NumberStrategy({
				minValue:'1'//, maxValue:'130'
			})
	  	}),new Ext.ux.form.Spinner({
			fieldLabel: '占用列宽',
			name: 'colspan',
	        width: 230,
			strategy: new Ext.ux.form.Spinner.NumberStrategy({
				minValue:'1'//, maxValue:'130'
			})
	  	}),new Ext.ux.form.Spinner({
	        fieldLabel: '占用行高',
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
	    	fieldLabel: 'CSS Class',
	    	name: 'cls',
	    	width: 230
	    }, {
	    	xtype: 'textarea',
	    	fieldLabel: 'CSS Style',
	    	name: 'style',
	    	width: 230,
	    	height: 64
	    }, {
	    	xtype: 'textarea',
	    	fieldLabel: '备注',
	    	name: 'description',
	    	width: 230,
	    	height: 64
	    }, {
	  		xtype:'htmleditor',
	  		hideLabel: true,
	        //fieldLabel: 'HTML',
	        //width: 500,
	  		enableLists: true,
	  		enableLinks: true,
	  		enableSourceEdit: true,
	  		emptyText: '编辑HTML代码',
	        height: 320,
	        name: 'html'
	    }],
	
	    buttons: [{
	        text: '保存',
	        handler: function() {
	        	var fp = Ext.getCmp(propertyFormId);
	        	fp.getForm().submit({
	        		url:'system/block.jxp', 
	        		params: {action: 'save'},
	        		waitMsg:'正在保存...'
	        	});
	        }
	    },{
	        text: '删除'
	    },{
	        text: '取消'
	    }]
	});
	
	// Pages that block displaying on.
	var Page = Ext.data.Record.create([ 'id', 'name', 'instances', 'current', 'description' ]);
	var instancePagesStore = new Ext.data.Store({
		url: 'system/block.jxp',
		reader: new Ext.data.XmlReader({
			id: 'id',
			record: 'page',
			totalRecords: 'totalCount'
		}, Page),
		baseParams: {
			action: 'getPages',
			id: '' //set after instance loaded
		},
		listeners: {
			'beforeload' : function(){
			},
			'load' : function(store, records, options) {
			}
		}
	});
	var allPagesStore = new Ext.data.Store({
		autoLoad: true,
		url: 'system/page.jxp',
		reader: new Ext.data.XmlReader({
			id: 'id',
			record: 'page',
			totalRecords: 'totalCount'
		}, ['id', 'name']),
		baseParams: {action: 'list', type: 'short'}
	});
	var pageRenderer = function(id) {
		if(id) {
			var page = allPagesStore.getById(id);
			if(page) {
				return page.data.name;
			}
		}
		return id;
	};
	var pageSM = new Ext.grid.CheckboxSelectionModel();
	var pageGrid = new Ext.grid.EditorGridPanel({
		id: 'system-prototypepage-grid' + prototype.id,
		//title: '显示页面',
		//iconCls: 'icon-be-product',
		//margins: '5 0 5 0',
		cmargins: '5 5 0 5',
	    region:'center',
		clicksToEdit:1,
	    bodyStyle: 'border-top:0px;',
		collapsible: false,
		animCollapse: false,
		autoExpandColumn: 'current',
		
		store: instancePagesStore,
      
		sm: pageSM,
		cm: new Ext.grid.ColumnModel([ new Ext.grid.RowNumberer(), pageSM,{	
			id: 'name',
			header: '显示页面',
			dataIndex: 'name',
			width: 160,
			renderer: pageRenderer,
			editor: new Ext.form.ComboBox({
				width:128,
				listClass:'x-combo-list-small',
				//value:'所有页面',
				emptyText: '选择页面...',
				store: allPagesStore,
			    //mode: 'local',
			    triggerAction: 'all',
				displayField: 'name',
				valueField: 'id' 
			})
		}, {/*
			id: 'instances',
			header: '当前实例数',
			dataIndex: 'instances',
			width: 120
		}, {*/
			id: 'current',
			header: '当前区块',
			dataIndex: 'current',
			width: 120
		}]),
		
		bbar: new Ext.PagingToolbar({
			pageSize: 25,
			store: instancePagesStore,
			displayInfo: true,
			displayMsg: '第{0}-{1}条 / 共{2}条',
			emptyMsg: "没有纪录",
			items:['-', {
				text: '添加',
				handler : function(){
					var propertyForm = Ext.getCmp(propertyFormId);
					// 检查属性表单
					if(propertyForm) {
						if (propertyForm.getForm().isDirty()) {
							//alert('请先保存所编辑的实例属性!');
							//return;
						}
					}
				    var p = new Page({
				    	name: '请选择...',
				    	instances: 0
				    });
			    	pageGrid.stopEditing();
			    	pageGrid.getStore().insert(0, p);
			    	pageGrid.startEditing(0, 2);
				}
			}, '-', {
				text: '<font color="Red">删除</font>'
			}]
		}),
		
		listeners: {
			'afteredit': function(e) {
				var grid = e.grid;
				var record = e.record;
				var value = e.value;
				var propertyForm = Ext.getCmp(propertyFormId);
				var instanceId = propertyForm.getForm().findField('id').value;
				var page = allPagesStore.getById(value);
				if(page) {
					Ext.Ajax.request({
						url: 'system/block.jxp',
						headers: {},
						params: {
							action: 'display',
							instance: instanceId,
							page: value
						},
						callback: function(options, success, reponse) {
							if(success) {
								alert('操作成功!');
								grid.getStore().load({params:{start:0,limit:25}});
							} else {
								alert('通讯失败!');
							}
						}
					});		
				}
			}
		}
	});
	this.pageGrid = pageGrid;
	
	// Prototype Settings for Multiple Pages
	var Setting = Ext.data.Record.create([
		'id', 'name', 'key', 'value', 'usage'
	]);
	var settingsStore = new Ext.data.Store({
		url: 'system/block.jxp',
		baseParams: {
			action: 'getSettings', 
			id: '' // set after instance loaded
		},
		reader: new Ext.data.XmlReader({
			id: 'id',
			record: 'setting',
			totalRecords: 'totalCount'
		}, Setting),
		listeners: {
			'beforeload' : function(){
				//this.baseParams.module = Ext.getCmp('search-module').getValue();
			},
			'load' : function() {
				//blockPagesStore.load();
			}
		}
	});
	this.settingGrid = new Ext.grid.EditorGridPanel({
		id: 'system-blockins-settings-' + prototype.id,
		//title: '区块内容显示配置',		
	    region:'south',
		iconCls: 'icon-be-product',
		floatable: false,
		//margins: '5 5 5 0',
		cmargins: '0 5 0 0',
		height: 200,
		minSize: 180,
		maxSize: 240,
	    bodyStyle: 'border-top:0px;',
		clicksToEdit:1,
		collapsible: false,
		//animCollapse: false,
		autoExpandColumn: 'usage',
		
		store: settingsStore,
		
		//sm: blockPageSM,
		cm: new Ext.grid.ColumnModel([ new Ext.grid.RowNumberer(),{
			id: 'name',
			header: "显示参数",
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
		}])/*,
		
		bbar: new Ext.PagingToolbar({
			pageSize: 25,
			store: settingsStore,
			displayInfo: true,
			displayMsg: '第{0}-{1}条 / 共{2}条',
			emptyMsg: "没有纪录",
			items:['-', {
				text: '<font color="Red">删除</font>'
			}]
		})*/
	});
	
	config = Ext.apply(config || {}, {
		id:'system-blkprototype-layout-' + prototype.id,
		title: '区块实例',// :: ' + prototype.name,
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
	    items: [this.propertyForm ,{
	        title: '显示配置',
			iconCls: 'icon-be-product',
		    layout:'border',    
	        region:'east',
		    width: 540,
		    minSize: 500,
		    maxSize: 600,
	        collapsible: true,
	        collapsedCls: 'margin: 5px;',
		    bodyBorder: false,
	        margins: '5 5 2 0',
			cmargins: '0 0 0 0',	        
	        items: [this.pageGrid, this.settingGrid]
	    }]
	});

	Ext.jxsite.BlockPrototypePanel.superclass.constructor.apply(this, arguments);
};
Ext.extend(Ext.jxsite.BlockPrototypePanel, Ext.Panel, {
	/**
	 * data.type = [prototype | instance]
	 * data.id = [prototypeId | instanceId]
	 */
	loadForm : function(data) {
		var pageGrid = this.pageGrid;
		var settingGrid = this.settingGrid;
		this.propertyForm.getForm().load({
	    	method: 'post',
	    	url:'system/block.jxp', 
	    	params: { 
	    		action: 'load', 
	    		type: data.type,
	    		id: data.id
	    	},
	    	success: function(form, action) {
	    		//alert(action.result.data.id);
	    		var instanceId = action.result.data.id;
	    		pageGrid.getStore().baseParams.id = instanceId;
	    		pageGrid.getStore().load({params: {start:0,limit:25}});
	    		settingGrid.getStore().baseParams.id = instanceId;
	    		settingGrid.getStore().load({params: {start:0,limit:25}});
	    	},
			waitMsg:'Loading ... '
		});
	}
});

// =============================================================== // 
// Grouping Grid :: Block Prototypes ============================= //
// =============================================================== //
Ext.jxsite.BlockPrototypeGrid = function(config) {
	if (!config) {
		config = {};
	}	
	// BlockPrototype
	var Prototype = Ext.data.Record.create(['id', 'name', 'index', 'singleton', 'instances', 'colspan', 'rowspan', 'module', 'pages',  'description'	]);
	var prototypeStore = new Ext.data.GroupingStore({
		url: 'system/block.jxp',
		baseParams: {action: 'list', type: 'prototype'},
		
		reader: new Ext.data.XmlReader({
			id: 'id',
			record: 'block',
			totalRecords: 'totalCount'
		}, Prototype),
		
		listeners: {
			'beforeload' : function(){
				this.baseParams.module = Ext.getCmp('search-module').getValue();
			}
		},
		
	    sortInfo:{field: 'name', direction: "ASC"},
	    groupField:'module'
	});

	var singletonRenderer = function(singleton) {
		if(singleton) {
			return '<img src="../resources/icons/silk/gif/application.gif" title="一个页面上只允许出现一个!" />';
		} else {
			return '<img src="../resources/icons/silk/gif/application_double.gif" title="一个页面上可以出现多个! />';
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
		prototypeStore.load({params:{start:0,limit:25}});
	});

	var checkboxSM = new Ext.grid.CheckboxSelectionModel();
	checkboxSM.on('selectionchange', function(selMod) {
		var btn = Ext.getCmp('btn-block-prototype-config');
		if(btn) {
			if(selMod.getCount() == 1) {
				//var record = selMod.getSelected();
				//if(record.data.instances <= 1) {
					btn.enable();
					return;
				//}
			} 
			btn.disable();
		}
	});
	var rowNumberSM = new Ext.grid.RowNumberer();

	Ext.jxsite.BlockPrototypeGrid.superclass.constructor.call(this, {
		id: 'system-prototype-grid',
		title: '区块模板',
		iconCls: 'icon-be-product',
		
		//clicksToEdit:1,
		collapsible: false,
		animCollapse: false,
		autoExpandColumn: 'pages',
		
		store: prototypeStore,
        
		sm: checkboxSM,
        cm: new Ext.grid.ColumnModel([rowNumberSM, checkboxSM, {	
        	header: '模板名称',
        	dataIndex: 'name',
        	width: 100
        },{	
        	header: '模块',
        	dataIndex: 'module',
        	width: 100
        },{	
        	header: '实例数',
        	align: 'center',
        	dataIndex: 'instances',
        	width: 64
        },{	
        	header: '允许多个?',
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
        	header: "显示页面",
        	dataIndex: 'pages',
        	width: 160
        },{	
        	header: '备注',
        	dataIndex: 'description',
        	width: 160
        }]),
        
        view: new Ext.grid.GroupingView({
            forceFit:true,
            groupTextTpl: '{text} <font stlye="font-weight:normal;">(共{[values.rs.length]}个)</font>'
        }),
        
        tbar: [ '快速搜索: ', new Ext.ux.SearchField({
            width:160,
			store: prototypeStore,
			restore: function() {
        		prototypeStore.load({pararms:{start:0,limit:25}});
	        },
			paramName: 'q'
        }), '->','按模块浏览: ', ' ', viewByModuleCB ],
       	bbar: new Ext.PagingToolbar({
       		pageSize: 25,
       		store: prototypeStore,
       		displayInfo: true,
       		beforePageText: '第',
       		afterPageText: '页 / 共{0}页',
       		displayMsg: '第{0}-{1}条 / 共{2}条',
       		emptyMsg: "没有纪录",
       		items:['-', {
       			text: '显示',
       			//tooltipType: 'title',
       			tooltip: '在所有页面上显示!',
       			handler: function() {}
       		}, '-', {
       			text: '清除',
       			tooltip: '清除所有显示!',
       			handler: function() {}
       		}, '-', {
       			id: 'btn-block-prototype-config',
       			disabled: true,
       			text: '配置',
       			tooltip: '自定义区块显示',
       			handler: function() {
       				var grid = Ext.getCmp('system-prototype-grid');
					var count = grid.getSelectionModel().getSelections().length;
		    		if(count==0) {
		    			alert('Please select one record!');
		    		}else if(count>1) {
		    			alert('Select too many records!');
		    		}else {
		    			var record = grid.getSelectionModel().getSelected();
		    			if(record.data.instances<0 || record.data.instances>1) {
		    				//alert('This prototype has multiple instances!');
		    				//return; // Cannot happen
		    			}
			    		var tabPanel = Ext.getCmp('system-block-panel');
			    		if(tabPanel) {
			    			var panel = tabPanel.findById('system-blkprototype-layout-' + record.data.id);
			    			if(!panel) {
			    				panel = new Ext.jxsite.BlockPrototypePanel({prototype: record.data});
			    				tabPanel.add(panel);
							}
			    			tabPanel.activate(panel);
			    			// Load data
			    			panel.loadForm({
			    				id: record.data.id,
			    				type: 'prototype'
			    			});
			    		}
			    	}
       			}
       		}]
       	})
	});
};
Ext.extend(Ext.jxsite.BlockPrototypeGrid, Ext.grid.GridPanel, {});

//=============================================================== // 
// TabPanel :: Block Management Tabs ============================ //
//=============================================================== //
// Tab 0 - prototypes grid tab
var prototypeGrid = new Ext.jxsite.BlockPrototypeGrid();
// Block Management TabPanel
var blockTabPanel = new Ext.TabPanel({
	id: 'system-block-panel',
	plain: true,  //remove the header border
	activeItem: 0,
	bodyStyle:'padding:0px',
	defaults: {bodyStyle: 'padding:0px'},
	items:[ prototypeGrid ]
});
blockTabPanel.on('show', function(panel) {
	prototypeGrid.getStore().load({params:{start:0,limit:25}});
});

// Register Panel
registerPanel(blockTabPanel);