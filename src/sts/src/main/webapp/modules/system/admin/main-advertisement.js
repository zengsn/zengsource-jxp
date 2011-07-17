/*
 * Advertisement management.
 * Copyright(c) 2008, iCanWear.com
 * icanwear@hotmail.com
 * 
 * http://iCanWear.com/license
 */
 
var Advertisement = Ext.data.Record.create([
	'id',
	'title',
	'position',
	'image',
	'status',
	'clicks',
	'vendor',
	'html',
	'maxClicks',
	'description',
	'createdTime',
	'expiringDate'
]);

var AdVendor = Ext.data.Record.create([
	'id',
	'name',
	'type',
	'phone',
	'fax',
	'address',
	'buyAds',
	'buyClicks',
	'description',
	'createdTime'
]);

// create the Data Store
var adStore = new Ext.data.Store({
  // load using HTTP
  url: '../xdb/advertisements.xml',

  // the return will be XML, so lets set up a reader
  reader: new Ext.data.XmlReader({
		id: 'id',
		record: 'ad',
		totalRecords: 'totalCount'
	}, Advertisement),
	
	baseParams: {},
  listeners: {
    'beforeload' : function(){
    	this.baseParams.position = Ext.getCmp('ad-position').getValue();
    }
  }
});
var adVendorStore  = new Ext.data.Store({
  // load using HTTP
  url: '../xdb/ad-vendors.xml',
  // the return will be XML, so lets set up a reader
  reader: new Ext.data.XmlReader({
		id: 'id',
		record: 'vendor',
		totalRecords: 'totalCount'
	}, AdVendor)
});

var adPositionStore = new Ext.data.Store({
  // load using HTTP
  url: '../xdb/ad-positions.xml',

  // the return will be XML, so lets set up a reader
  reader: new Ext.data.XmlReader({
		id: 'id',
		record: 'position',
		totalRecords: 'totalCount'
	}, ['id', 'title'])
	
});

// row expander
var adExpander = new Ext.grid.RowExpander({
  tpl : new Ext.Template(
  	'<img src="../upload/advertisements/{image}" />',
  	'<div>{html}</div>',
    '<p>说明: {description}</p><br>'
  )
});
var adStatusRenderer = function(v) {
	if(v==0) {
		return '<font color="green"><b>新的</b></font>';
	} else if(v==1) {
		return '<font color="orange"><b>未投放</b></font>';
	} else if(v==2) {
		return '<font color="blue"><b>投放中</b></font>';
	} else if(v==3) {
		return '<font color="gray"><b>已过期</b></font>';
	} else {
		return v;
	}
};
var adSM = new Ext.grid.CheckboxSelectionModel();
var adCM = new Ext.grid.ColumnModel([ adExpander, adSM, {
	id:'id',
	header: "ID",
	dataIndex: 'id',
	width: 48
},{
	header: "标题",
	dataIndex: 'title',
	width: 160,
	editor: new Ext.form.TextField({
	   allowBlank: false
	})
},{
	header: "position",
	dataIndex: 'position',
	width: 120,
	editor: new Ext.form.ComboBox({
    store: adPositionStore,
    displayField:'title',
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
	header: "状态",
	dataIndex: 'status',
	width: 64,
	renderer: adStatusRenderer,
	editor: new Ext.form.TextField({
	   allowBlank: false
	})
},{
	header: "广告商",
	dataIndex: 'vendor',
	width: 128
},{
	header: "当前点击",
	dataIndex: 'clicks',
	width: 64
},{
	header: "最大点击",
	dataIndex: 'maxClicks',
	width: 64
},{
	header: "创建时间",
	dataIndex: 'createdTime',
	width: 72
},{
	header: "过期时间",
	dataIndex: 'expiringDate',
	width: 72
}]);

// create the grid
var advertisementGrid = new Ext.grid.EditorGridPanel({
	id: 'ad-config-panel',
	title: '广告列表',
	iconCls: 'icon-be-advertisement',
	
	clicksToEdit:1,
  collapsible: false,
  animCollapse: false,
	
  store: adStore,
  
  sm: adSM,
  cm: adCM,
  plugins: adExpander,
  //renderTo:'example-grid',
  //width:540,
  //height:200,
  tbar: [ '搜索: ', ' ', new Ext.ux.SelectBox({
	  width:128,
	  listClass:'x-combo-list-small',
	  //value:'Starts with',
	  id:'ad-position',
	  emptyText: '选择投放位置...',
	  store: adPositionStore,
	  displayField: 'title',
	  valueField: 'id' 
	}), ' ', new Ext.app.SearchField({
    width:240,
		store: adStore,
		paramName: 'q'
	})],
  bbar: new Ext.PagingToolbar({
    pageSize: 25,
    store: adStore,
    displayInfo: true,
    displayMsg: '第{0}-{1}条 / 共{2}条',
    emptyMsg: "没有纪录",
    items:['-', {
    	text: '添加',
    	iconCls: 'btn-add',
    	handler: function() {
    		alert('To be continued ...');
    	}
    },'-', {
    	text: '编辑',
    	iconCls: 'btn-modify',
    	handler: function() {
    		alert('To be continued ...');
    	}
    }, '-', {
    	text: '删除',
    	iconCls: 'btn-delete',
    	handler: function() {
    		alert('To be continued ...');
    	}
    }, '-', {
    	text: '投放',
    	iconCls: 'btn-on',
    	handler: function() {
    		alert('To be continued ...');
    	}
    }, '-', {
    	text: '停止',
    	iconCls: 'btn-off',
    	handler: function() {
    		alert('To be continued ...');
    	}
    }, '-']
	})	
});

advertisementGrid.on('show', function(panel) {
	adStore.load();
});

// Advertisement Vendor Grid Form ////////////////////
var adVendorCM = new Ext.grid.ColumnModel([{
	id:'id',
	header: "ID",
	dataIndex: 'id',
	width: 48
},{
	header: "名称",
	dataIndex: 'name',
	width: 160
},{
	header: "类型",
	dataIndex: 'type',
	width: 64
},{
	header: "地址",
	dataIndex: 'address',
	width: 160
},{
	header: "地址",
	dataIndex: 'address',
	width: 160
},{
	id: 'createdTime',
	header: "创建时间",
	dataIndex: 'createdTime',
	width: 72
}]);
var adVendorGridForm = new Ext.FormPanel({
  id: 'ad-vendor-panel',
  //frame: true,
  labelAlign: 'left',
  title: '广告客户',
  iconCls: 'icon-be-advertisement',
  bodyStyle:'padding:5px',
  //width: 750,
  //layout: 'fit',	
  autoScroll: true,
  items: [{
    xtype: 'grid',
    height: 350,
    title:'客户列表',
    border: true,
    ds: adVendorStore,
    cm: adVendorCM,
    autoExpandColumn: 'createdTime',
    sm: new Ext.grid.RowSelectionModel({
      singleSelect: true,
      listeners: {
        rowselect: function(sm, row, rec) {
        	Ext.getCmp("ad-vendor-panel").getForm().loadRecord(rec);
        }
      }
    }),
  	listeners: {
    	render: function(g) {
    		g.getSelectionModel().selectRow(0);
    	},
    	delay: 10 // Allow rows to be rendered.
    },
    bbar: new Ext.PagingToolbar({
	    pageSize: 25,
	    store: adVendorStore,
	    displayInfo: true,
	    displayMsg: '第{0}-{1}条 / 共{2}条',
	    emptyMsg: "没有纪录",
	    items:['-', {
	    	text: '添加',
	    	iconCls: 'btn-add',
	    	handler: function() {
	    		alert('To be continued ...');
	    	}
	    },'-', {
	    	text: '编辑',
	    	iconCls: 'btn-modify',
	    	handler: function() {
	    		alert('To be continued ...');
	    	}
	    }, '-', {
	    	text: '删除',
	    	iconCls: 'btn-delete',
	    	handler: function() {
	    		alert('To be continued ...');
	    	}
	    }, '-', {
	    	text: '投放',
	    	iconCls: 'btn-on',
	    	handler: function() {
	    		alert('To be continued ...');
	    	}
	    }, '-', {
	    	text: '停止',
	    	iconCls: 'btn-off',
	    	handler: function() {
	    		alert('To be continued ...');
	    	}
	    }, '-']
		})
  },{
    layout: 'column',
    border: false,
    labelWidth: 48,
	  items: [{
	  	columnWidth: 0.3,
	    xtype: 'fieldset',
	    title:'客户信息',
	    border: true,
	    height: 200,
    	defaults: {width: 160},	
	    defaultType: 'textfield',
	    //autoHeight: true,
	    bodyStyle: Ext.isIE ? 'padding:0 0 5px 15px;' : 'padding:10px 15px;',
	    style: {
	      "margin-left": "10px", // when you add custom margin in IE 6...
	      "margin-right": Ext.isIE6 ? (Ext.isStrict ? "-10px" : "-13px") : "0"  // you have to adjust for it somewhere else
	    },
	    items: [{
	      fieldLabel: '名称',
	      name: 'name'
	    },{
	    	xtype: 'combo',
	      name: 'type',
        width: 143,
	      fieldLabel: '类型',
        store: new Ext.data.SimpleStore({
	        fields: ['type'],
	        data : [['公司'], ['个人']]
		    }),
        displayField:'type',
        typeAhead: true,
        mode: 'local',
        triggerAction: 'all',
        emptyText:'选择类型...',
        selectOnFocus:true
	    },{
	      xtype: 'textarea',
	      fieldLabel: '备注',
	      name: 'description'
	    }]
	  }, {
	  	columnWidth: 0.3,
	    xtype: 'fieldset',
	    title:'联系方式',
	    border: true,
	    height: 200,
	    //labelWidth: 90,
	    defaults: {width: 160},	
	    defaultType: 'textfield',
	    autoHeight: false,
	    bodyStyle: Ext.isIE ? 'padding:0 0 5px 15px;' : 'padding:10px 15px;',
	    style: {
	      "margin-left": "10px", // when you add custom margin in IE 6...
	      "margin-right": Ext.isIE6 ? (Ext.isStrict ? "-10px" : "-13px") : "0"  // you have to adjust for it somewhere else
	    },
	    items: [{
	      fieldLabel: '地址',
	      name: 'address'
	    },{
	      fieldLabel: '电话',
	      name: 'phone'
	    },{
	      fieldLabel: '传真',
	      name: 'fax'
	    }]
	  }, {
	  	columnWidth: 0.3,
	    xtype: 'fieldset',
	    title:'广告信息',
	    border: true,
	    height: 200,
	    //labelWidth: 90,
	    defaults: {width: 160},	
	    defaultType: 'textfield',
	    autoHeight: false,
	    bodyStyle: Ext.isIE ? 'padding:0 0 5px 15px;' : 'padding:10px 15px;',
	    style: {
	      "margin-left": "10px", // when you add custom margin in IE 6...
	      "margin-right": Ext.isIE6 ? (Ext.isStrict ? "-10px" : "-13px") : "0"  // you have to adjust for it somewhere else
	    },
	    items: [{
	      fieldLabel: '广告',
	      name: 'buyAds'
	    },{
	      fieldLabel: '点击数',
	      name: 'buyClicks'
	    },{
	    	disabled: true,
	      fieldLabel: '创建',
	      name: 'createdTime'
	    }]
	  }]
	}],
  buttons: [{text: '保存'},{text: '取消'}]
});

adVendorGridForm.on('show', function(panel) {
	adVendorStore.load();
});

