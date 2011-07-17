/*
 * Products management.
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
var productCM = new Ext.grid.ColumnModel([ productExpander, productSM, {
	id:'id',
	header: "ID",
	dataIndex: 'id',
	width: 48
},{
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

var editProduct = function(id) {
	var idHash = idHash = id;
	if(!id) {
		idHash = (new Date()).format('Y-m-d H:i:s');
	}
	var title = '创建新商品 (' + idHash + ')';
	//create product form
	var productForm = new Ext.form.FormPanel({
		id: 'product-form-' + idHash,
    labelWidth: 75,
    title: title,
    bodyStyle:'padding:15px',
    closable: true,
    //width: 350,
		labelPad: 20,
		layoutConfig: {
			labelSeparator: ''
		},
    defaults: {
			width: 230,
			msgTarget: 'side'
		},
    defaultType: 'textfield',
    items: [{
        fieldLabel: 'First Name',
        name: 'first',
        allowBlank:false
    },{
        fieldLabel: 'Last Name',
        name: 'last'
    },{
        fieldLabel: 'Company',
        name: 'company'
    },{
        fieldLabel: 'Email',
        name: 'email',
        vtype:'email'
    }],
    buttons: [{text: 'Save'},{text: 'Cancel'}]
	});
	var productTabPanel = Ext.getCmp('product-panel');
	if(!productTabPanel.findById(productForm.id)) {
		productTabPanel.add(productForm);
	}
	productTabPanel.activate(productForm.id);
};
// create the grid
var productGrid = new Ext.grid.EditorGridPanel({
	id: 'product-grid',
	title: '商品列表',
	iconCls: 'icon-be-product',
	
	clicksToEdit:1,
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
	  store: simpleCatalogStore,
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
	    	editProduct(null);
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

var productTabPanel = new Ext.TabPanel({
	id: 'product-panel',
	plain: true,  //remove the header border
	activeItem: 0,
	defaults: {bodyStyle: 'padding:0px'},
	items:[productGrid]
});

productTabPanel.on('show', function(panel) {
	productStore.load();
});

