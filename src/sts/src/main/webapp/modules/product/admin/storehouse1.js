/*
 * Product Module Configuration.
 * Copyright(c) 2008, iCanWear.com
 * icanwear@hotmail.com
 * 
 * http://iCanWear.com/license
 */

var Product = Ext.data.Record.create([
   'id',
   'name',
   'catalog',
   'frontImage',
   'backImage',
   'status',
   'totalViews',
   'currentOrders',
   'totalSales',
   'quantity',
   'lastOrder',
   'lastSale',
   'createdTime',
   'lastUpdatedTime',
   'lastComments',
   'totalComments',
   'material',
   'delivery',
   'pleaseNote',
   'description'
]);

// create the Data Store
var productStore = new Ext.data.Store({
	// load using HTTP
	url: '../xdb/products.xml',
	
	// the return will be XML, so lets set up a reader
	reader: new Ext.data.XmlReader({
		id: 'id',
		record: 'product',
		totalRecords: 'totalCount'
	}, Product),
	
	baseParams: {},
	listeners: {
		'beforeload' : function(){
			this.baseParams.catalog = Ext.getCmp('search-catalog').getValue();
		}
	}
});

// row expander
var productExpander = new Ext.grid.RowExpander({
	tpl : new Ext.Template(
		//'<hr class="expander" size="1"/>',
		'<img src="../upload/images/products/{frontImage}" class="front" width="160" height="160"/>',
		'<img src="../upload/images/products/{backImage}" class="back" width="160" height="160"/>',
		'<p><b>产品材质:</b> {material}</p><br>',
		'<p><b>产品描述:</b> {description}</p><br>',
		'<p><b>物流配送:</b> {delivery}</p><br>',
		'<p><b>最新订单:</b> {lastOrder}</p><br>',
		'<p><b>最新成交:</b> {lastSale}</p><br>',
		'<p><b>最新评论:</b> {lastComments} [共{totalComments}条]</p>'
	)
});
var statusRenderer = function(v) {
	if(v==0) {
		return '<font color="green"><b>新的</b></font>';
	} else if(v==1) {
		return '<font color="blue"><b>上架</b></font>';
	} else if(v==2) {
		return '<font color="gray"><b>下架</b></font>';
	} else {
		return v;
	}
};
var productSM = new Ext.grid.CheckboxSelectionModel();
var productCM = new Ext.grid.ColumnModel([ 

	new Ext.grid.RowNumberer(), 
	productExpander, productSM, 
{/*
	id:'id',
	header: "ID",
	dataIndex: 'id',
	width: 48
},{*/
	header: "名称",
	dataIndex: 'name',
	width: 160,
	editor: new Ext.form.TextField({
	   allowBlank: false
	})
},{
	header: "分类",
	dataIndex: 'catalog',
	width: 100,
	editor: new Ext.form.ComboBox({
    store: productCatalogStore,
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
	header: "状态",
	dataIndex: 'status',
	width: 48,
	renderer: statusRenderer,
	editor: new Ext.form.TextField({
	   allowBlank: false
	})
},{
	header: "浏览",
	dataIndex: 'totalViews',
	width: 48
},{
	header: "订单",
	dataIndex: 'currentOrders',
	width: 48
},{
	header: "已售",
	dataIndex: 'totalSales',
	width: 48
},{
	header: "库存",
	dataIndex: 'quantity',
	width: 48
//},{
//	header: "注意事项",
//	dataIndex: 'pleaseNote',
//	width: 120
},{
	header: "创建时间",
	dataIndex: 'createdTime',
	width: 72
},{
	id: 'lastUpdatedTime',
	header: "最后更新",
	dataIndex: 'lastUpdatedTime',
	width: 72
}]);

// Form Items
var formItems = [{
	xtype: 'textfield',
    fieldLabel: '产品名称',
    name: 'name',
    allowBlank:false
},{
	xtype: 'textfield',
    fieldLabel: '产品经理',
    name: 'manager'
},{
	xtype: 'combo',
    fieldLabel: '产品分类',
    hiddenName:'parent',
    store: productCatalogStore,
    valueField:'id',
    displayField:'name',
    typeAhead: true,
    //mode: 'local',
    triggerAction: 'all',
    emptyText:'请选择 ...',
    selectOnFocus:true,
    name: 'catalog'
},{
	xtype: 'textfield',
    fieldLabel: '产品单价',
    name: 'price'
},{
	xtype: 'combo',
    fieldLabel: '货币单位',
    store: new Ext.data.SimpleStore({
        fields: ['abbr', 'currency'],
        data : [['人民币', '￥'], ['美元', '$']]
    }),
    displayField:'abbr',
    valueField:'currency',
    typeAhead: true,
    mode: 'local',
    triggerAction: 'all',
    emptyText:'请选择 ...',
    selectOnFocus:true,
    name: 'currency'
},{
	xtype: 'combo',
    fieldLabel: '产品材质',
    store: new Ext.data.SimpleStore({
        fields: ['abbr', 'material'],
        data : [
        	['尼龙', 'Nylon'], 
        	['纯棉', 'Purified Cotton'], 
        	['其它', 'Others']
        ]
    }),
    displayField:'abbr',
    valueField:'material',
    typeAhead: true,
    mode: 'local',
    triggerAction: 'all',
    emptyText:'请选择 ...',
    selectOnFocus:true,
    name: 'material'
},{
	xtype: 'combo',
    fieldLabel: '配送方式',
    store: new Ext.data.SimpleStore({
        fields: ['abbr', 'shipping'],
        data : [
        	['送货上门', 'Deliver'], 
        	['普通包裹', 'Post'], 
        	['快递物流', 'Express']
        ]
    }),
    displayField:'abbr',
    valueField:'shipping',
    typeAhead: true,
    mode: 'local',
    triggerAction: 'all',
    emptyText:'请选择 ...',
    selectOnFocus:true,
    name: 'shipping'
},{
	xtype: 'textarea',
    fieldLabel: '使用说明',
    height: 64,
    name: 'usage'
},{
	xtype: 'textarea',
    fieldLabel: '备注说明',
    height: 64,
    name: 'description'
},{ 
	//xtype: 'textarea',
	inputType: 'file',
    fieldLabel: '产品图片',
    name: 'images'
}];

// Edit Button Handler
var editProduct = function(id) {
	var idHash = id;
	if(!id) {
		idHash = (new Date()).format('Y-m-d H:i:s');
	}
	var title = '修改产品 :: ' + idHash;
	
	var productTabPanel = Ext.getCmp('product-storehouse-panel');
	if(!productTabPanel) {
		var productEditLayout = productTabPanel.findById('product-edit-layout-'+idHash);
		if(!productEditLayout) {		
			//create product form	
			productEditLayout = CREATE_PRODUCT_EDIT_LAYOUT({
				id: 'product-edit-layout-'+idHash,
				title: title, 
				closable: true,
				iconCls: 'icon-be-product',
				form: {id:'product-form-' + idHash},
				upload: {id:'product-edit-upload'}
			});
			productTabPanel.add(productEditLayout);
		}
		productTabPanel.activate(productEditLayout.id);
	} else {
		alert('ERROR: Null Product TabPanel!');
	}
};

// create the grid
var productGrid = new Ext.grid.EditorGridPanel({
	id: 'product-grid',
	title: '产品列表',
	iconCls: 'icon-be-product',
	
	clicksToEdit:2,
	collapsible: false,
	animCollapse: false,
	autoExpandColumn: 'lastUpdatedTime',
	
	store: productStore,
	
	sm: productSM,
	cm: productCM,
	plugins: productExpander,
	//renderTo:'example-grid',
	//width:540,
	//height:200,
	tbar: [ '搜索: ', ' ', new Ext.ux.SelectBox({
		width:128,
		listClass:'x-combo-list-small',
		//value:'Starts with',
		id:'search-catalog',
		emptyText: '选择分类...',
		store: productCatalogStore,
		displayField: 'name',
		valueField: 'id' 
	}), ' ', new Ext.app.SearchField({
		width:240,
		store: productStore,
		paramName: 'q'
	})],
	bbar: new Ext.PagingToolbar({
		pageSize: 25,
		store: productStore,
		displayInfo: true,
		displayMsg: '第{0}-{1}条 / 共{2}条',
		emptyMsg: "没有纪录",
		items:['-', {
	    	text: '添加',
	    	iconCls: 'btn-add',
	    	handler: function() {
		    	var productTabPanel = Ext.getCmp('product-storehouse-panel');
				productTabPanel.activate('product-form-add');
	    	}
	    },'-', {
	    	text: '修改',
	    	iconCls: 'btn-modify',
	    	handler: function() {
	    		var count = productGrid.getSelectionModel().getSelections().length;
	    		if(count==0) {
	    			alert('Please select one record!');
	    		}else if(count>1) {
	    			alert('Select too many records!');
	    		}else {
	    			var record = productGrid.getSelectionModel().getSelected();
		    		editProduct(record.data.id);
		    	}
	    	}
	    }, '-', {
	    	text: '删除',
	    	iconCls: 'btn-delete',
	    	handler: function() {
	    		var records = productGrid.getSelectionModel().getSelections();
	    		var count = records.length;
	    		if(count==0) {
	    			alert('Please select one record at least!');
	    		}else {
	    			alert(records);
		    	}
	    	}
	    }, '-', {
	    	text: '上架',
	    	iconCls: 'btn-on',
	    	handler: function() {
	    		alert('To be continued ...');
	    	}
	    }, '-', {
	    	text: '下架',
	    	iconCls: 'btn-off',
	    	handler: function() {
	    		alert('To be continued ...');
	    	}
	    }, '-']
	})	
});

function CREATE_PRODUCT_EDIT_LAYOUT(config) {
	config = config || {};
	config = Ext.applyIf(config || {}, {
		id: 'product-form-layout',
		title: '产品编辑'
	});
	config.form = config.form || {};
	config.form = Ext.applyIf(config.form || {}, {
		xtype: 'form', 
		id: 'product-form-add',
		title: '产品属性',
		iconCls: 'icon-be-product',
	    bodyStyle:'padding:15px',
		labelWidth: 75,
		labelPad: 20,
		layoutConfig: {
			labelSeparator: ':'
		},
		defaults: {
			width: 230,
			msgTarget: 'side'
		},
		defaultType: 'textfield',
		items: [{
			xtype: 'textfield',
		    fieldLabel: '产品名称',
		    name: 'name',
		    allowBlank:false
		},{
			xtype: 'textfield',
		    fieldLabel: '产品经理',
		    name: 'manager'
		},{
			xtype: 'combo',
		    fieldLabel: '产品分类',
		    hiddenName:'parent',
		    store: productCatalogStore,
		    valueField:'id',
		    displayField:'name',
		    typeAhead: true,
		    //mode: 'local',
		    triggerAction: 'all',
		    emptyText:'请选择 ...',
		    selectOnFocus:true,
		    name: 'catalog'
		},{
			xtype: 'textfield',
		    fieldLabel: '产品单价',
		    name: 'price'
		},{
			xtype: 'combo',
		    fieldLabel: '货币单位',
		    store: new Ext.data.SimpleStore({
		        fields: ['abbr', 'currency'],
		        data : [['人民币', '￥'], ['美元', '$']]
		    }),
		    displayField:'abbr',
		    valueField:'currency',
		    typeAhead: true,
		    mode: 'local',
		    triggerAction: 'all',
		    emptyText:'请选择 ...',
		    selectOnFocus:true,
		    name: 'currency'
		},{
			xtype: 'combo',
		    fieldLabel: '产品材质',
		    store: new Ext.data.SimpleStore({
		        fields: ['abbr', 'material'],
		        data : [
		        	['尼龙', 'Nylon'], 
		        	['纯棉', 'Purified Cotton'], 
		        	['其它', 'Others']
		        ]
		    }),
		    displayField:'abbr',
		    valueField:'material',
		    typeAhead: true,
		    mode: 'local',
		    triggerAction: 'all',
		    emptyText:'请选择 ...',
		    selectOnFocus:true,
		    name: 'material'
		},{
			xtype: 'combo',
		    fieldLabel: '配送方式',
		    store: new Ext.data.SimpleStore({
		        fields: ['abbr', 'shipping'],
		        data : [
		        	['送货上门', 'Deliver'], 
		        	['普通包裹', 'Post'], 
		        	['快递物流', 'Express']
		        ]
		    }),
		    displayField:'abbr',
		    valueField:'shipping',
		    typeAhead: true,
		    mode: 'local',
		    triggerAction: 'all',
		    emptyText:'请选择 ...',
		    selectOnFocus:true,
		    name: 'shipping'
		},{
			xtype: 'textarea',
		    fieldLabel: '使用说明',
		    height: 64,
		    name: 'usage'
		},{
			xtype: 'textarea',
		    fieldLabel: '备注说明',
		    height: 64,
		    name: 'description'
		}],
	    buttons: [{
	    	text: '加载',
	    	handler: function(btn) {
	    		alert('Not yet ...');
	    	}
	    },{
	    	text: '保存',
	    	handler: function(btn) {
	    		alert('Not yet ...');
	    	}
	    },{
	    	text: '取消',
	    	handler: function(btn) {
	    		alert('Not yet ...');
	    	}
	    }]
	});
	config.upload = config.upload || {};
	config.upload = Ext.applyIf(config.upload || {}, {
		id: 'upload-form',
	    width: 320,
	    minSize: 240,
	    maxSize: 360,
	    title: '上传图片',
		iconCls: 'icon-be-product',
	
		// Uploader Params				
		upload_url: 'http://fancing.net/extux/upload_example.php',
		//post_params: { PHPSESSIDX: PHPSESSIDX },
		debug: true,
		//flash_url: "../extux/swfupload_f9.swf",
		//single_select: true, // Select only one file from the FileDialog
		
		// Custom Params
		single_file_select: false, // Set to true if you only want to select one file from the FileDialog.
		confirm_delete: false, // This will prompt for removing files from queue.
		remove_completed: false // Remove file from grid after uploaded.		
	});
	var productLayoutTemp = new Ext.ux.UploadFormPanel(config);
	
	/*
	var uploader = productLayout.getUploadPanel();
	
	uploader.on('swfUploadLoaded', function() { 
		this.addPostParam( 'Post1', 'example1' );
	});
	
	uploader.on('fileUploadComplete', function(panel, file, response) {
	});
	
	uploader.on('queueUploadComplete', function() {
		if ( Ext.isGecko ) {
			//console.log("Files Finished");
		} else {
			alert("Files Finished");
		}
	});
	*/
	
	return productLayoutTemp;
};//~

var productLayout = CREATE_PRODUCT_EDIT_LAYOUT({
	id: 'product-add-layout',
	title: '创建新产品',
	iconCls: 'icon-be-product',
	form: {id:'product-add-form'},
	upload: {id:'product-add-upload'}
});

// Product Storehouse TabPanel
var productStorehouseTabPanel = new Ext.TabPanel({
	id: 'product-storehouse-panel',
	plain: true,  //remove the header border
	activeItem: 0,
	bodyStyle:'padding:0px',
	defaults: {bodyStyle: 'padding:0px'},
	items:[productGrid, productLayout]
});
productStorehouseTabPanel.on('show', function(panel) {
	productStore.load({params:{start:0,limit:25}});
});


// Register Panel
registerPanel(productStorehouseTabPanel);