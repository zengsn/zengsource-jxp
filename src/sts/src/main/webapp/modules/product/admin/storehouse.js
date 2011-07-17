/*
 * Product management.
 * Copyright(c) 2008, iCanWear.com
 * icanwear@hotmail.com
 * 
 * http://iCanWear.com/license
 */

// Date Record
var Product = Ext.data.Record.create([
	'id', 'name', 'catalog', 'status', 'manager', 
	'viewCount', 'totalCount', 'soldCount', 'orderCount',
	'price', 'currency', 'material', 'shipping', 'usage',
	'imageSingle', 'imageFront', 'imageBack', 'imageLeft', 'imageRight', 'imageAbove', 'imageUnder', 'attachment',
	'createdTime', 'description'
]);

// Data Store
var productStore = new Ext.data.Store({
	// load using HTTP
	url: 'product/manage.jxp',
	//url: '../xdb/products.xml',
	
	// the return will be XML, so lets set up a reader
	reader: new Ext.data.XmlReader({
		id: 'id',
		record: 'product',
		totalRecords: 'totalCount'
	}, Product),
	
	baseParams: {action: 'list'},
	listeners: {
		'beforeload' : function(){
			//this.baseParams.product = Ext.getCmp('search-product').getValue();
		}
	}
});

// row expander
var productExpander = new Ext.grid.RowExpander({
    tpl : new Ext.Template(
    	'<table style="border:0px;" class="product" width="100%"><tr><td>',
        '<p><b>配送:</b> {shipping}</p>',
        '<p><b>材料:</b> {material}</p>',
        '<p><b>说明:</b> {usage}</p>',
        '<p><b>备注:</b> {description}</p>',
        '<p><b>附件:</b> {attachment}</p>',
        '</td><td width="70">',
        '<img src="../upload/images/products/{imageSingle}" width="64" height="64" />',
        '</td><td width="70">',
        '<img src="../upload/images/products/{imageFront}" width="64" height="64" />',
        '</td><td width="70">',
        '<img src="../upload/images/products/{imageBack}" width="64" height="64" />',
        '</td><td width="70">',
        '<img src="../upload/images/products/{imageLeft}" width="64" height="64" />',
        '</td><td width="70">',
        '<img src="../upload/images/products/{imageRight}" width="64" height="64" />',
        '</td><td width="70">',
        '<img src="../upload/images/products/{imageAbove}" width="64" height="64" />',
        '</td><td width="70">',
        '<img src="../upload/images/products/{imageUnder}" width="64" height="64" />',
        '</td></tr></table>'
    )
});
var imageRenderer = function(img) {
	return '<img src="../upload/images/products/'+img+'" width="64" height="64" />';
};
var productSM = new Ext.grid.CheckboxSelectionModel();
var productCM = new Ext.grid.ColumnModel([
	productExpander,
	new Ext.grid.RowNumberer(), 
	productSM, 
{
	id: 'name',
	header: 'Name',
	dataIndex: 'name',
	width: 256
}, {
	id: 'catalog',
	header: 'Catalog',
	dataIndex: 'catalog',
	width: 128
}, {
	id: 'manager',
	header: 'Manger',
	dataIndex: 'manager',
	width: 64
}, {
	header: 'Status',
	align: 'center',
	dataIndex: 'status',
	width: 64
}, {/*
	id: 'currency',
	header: 'Currency',
	dataIndex: 'currency',
	width: 48
}, {*/
	header: 'Price',
	align: 'right',
	dataIndex: 'price',
	width: 64
}, {
	header: 'View',
	align: 'center',
	dataIndex: 'viewCount',
	width: 80
}, {
	header: 'Total',
	align: 'center',
	dataIndex: 'totalCount',
	width: 80
}, {
	header: 'Sold',
	align: 'center',
	dataIndex: 'soldCount',
	width: 80
}, {
	header: 'Orders',
	align: 'center',
	dataIndex: 'orderCount',
	width: 80
}]);

var productGrid = new Ext.grid.GridPanel({
	id: 'product-grid',
    title: 'Product List',
	iconCls: 'icon-be-product',
    collapsible: true,
    animCollapse: false,
    plugins: productExpander,
	
	sm: productSM,
	cm: productCM,
    store: productStore,
    autoExpandColumn: 'name',
	
    viewConfig: {
        //forceFit:true
    },
	/*			            
    keys: [{
    	key : Ext.EventObject.ENTER,
    	fn : function() {
    		alert('Enter ...');
    	}
    }, {
    	key : Ext.EventObject.ESC,
    	fn : function() {
    		alert('ESC ...');
    	}
    }],*/
    
    tbar: ['Search: ', /*new Ext.ux.SelectBox({
        width:90,
        value:'All ...',
        id:'search-product',
        listClass:'x-combo-list-small',
        store: new Ext.data.SimpleStore({
            fields: ['text'],
            expandData: true,
            data : ['Name', 'Catalog', 'Create Time', 'Update Time']
        }),
        displayField: 'text'
    }), */' ', new Ext.app.SearchField({
        width:240,
		store: productStore,
		paramName: 'q'
    })],
    
    bbar: new Ext.PagingToolbar({
        pageSize: 25,
        store: productStore,
        displayInfo: true,
        displayMsg: '{0} - {1} / {2}',
        emptyMsg: "No record!",
        items:[ '-', {
        	text: '查看 (V)',
        	disabled: false,
        	handler: function() {
        		var now = new Date();
        		var id = now.format('YmdHisu');
        		var tabPanel = Ext.getCmp('product-storehouse-panel');
        		var panel = new Ext.app.ProductFormPanel();
        		tabPanel.add(panel);
        		tabPanel.activate(panel);
        		//panel.getFileStore().load();
        	}
        }, '-', {/*
        	text: '创建 (C)',
        	disabled: true,
        	handler: function() {
        		var now = new Date();
        		var id = now.format('YmdHisu');
        		var tabPanel = Ext.getCmp('product-storehouse-panel');
        		var panel = new Ext.app.ProductFormPanel();
        		tabPanel.add(panel);
        		tabPanel.activate(panel);
        		//panel.getFileStore().load();
        	}
        }, '-', {*/
        	text: '创建 (C)',
        	handler: function() {
        		var now = new Date();
        		var id = now.format('YmdHisu');
        		var tabPanel = Ext.getCmp('product-storehouse-panel');
        		var panel = new Ext.Panel({
        			id: 'product-tab-'+id,
        			title: '新建 :: '+id,
        			layout: 'border',
					iconCls: 'icon-be-product',
					closable: true,
        			bodyBorder: false,
					defaults: {
						collapsible: false,
						split: true,
						animFloat: false,
						autoHide: false,
						useSplitTips: true			
					},
					items: [new JXS_UI_ProductFormPanel({}, {id:id})]
        		});
        		tabPanel.add(panel);
        		tabPanel.activate(panel);
        		//panel.getFileStore().load();
        	}
        }, '-', {
        	text: '编辑 (E)',
        	handler: function() {
	    		var count = productGrid.getSelectionModel().getSelections().length;
	    		if(count==0) {
	    			alert('Please select one record!');
	    		}else if(count>1) {
	    			alert('Select too many records!');
	    		}else {
	    			var record = productGrid.getSelectionModel().getSelected();
	    			var tabPanel = Ext.getCmp('product-storehouse-panel');
	        		var panel = new Ext.Panel({
	        			id: 'product-tab-'+record.data.id,
	        			title: '编辑 :: '+record.data.name,
	        			layout: 'border',
						iconCls: 'icon-be-product',
						closable: true,
	        			bodyBorder: false,
						defaults: {
							collapsible: false,
							split: true,
							animFloat: false,
							autoHide: false,
							useSplitTips: true			
						},
						items: [new JXS_UI_ProductFormPanel({}, record.data)]
	        		});
	        		tabPanel.add(panel);
	        		tabPanel.activate(panel);
	        		Ext.getCmp('product-form-'+record.data.id).getForm().load({
			        	method: 'post',
			        	url:'product/manage.jxp', 
			        	params: { action:'load', id: record.data.id},
		        		waitMsg:'Loading ... '
	        		});
	        		//Ext.getCmp('product-form-'+record.data.id).getForm().loadRecord(record);
		    	}
        	}
        }, '-', {
        	text: '删除 (D)',
        	handler: function() {
	    		var count = productGrid.getSelectionModel().getSelections().length;
	    		if(count==0) {
	    			alert('Please select one record!');
	    		}else {
        			var recordArr = new Array();
	    			var selections =  productGrid.getSelectionModel().getSelections();
	    			for(var i=0; i<count; i++) {
	    				recordArr[i] = selections[i].data.id;
	    			}
	    			alert(recordArr);
		    	}
        	}
        }]
    })
});

// Product Management TabPanel
var productTabPanel = new Ext.TabPanel({
	id: 'product-storehouse-panel',
	plain: true,  //remove the header border
	activeItem: 0,
	bodyStyle:'padding:0px',
	defaults: {bodyStyle: 'padding:0px'},
	items:[ productGrid ]
});
productTabPanel.on('show', function(panel) {
	productStore.load({params:{start:0,limit:25}});
});

// Register Panel
registerPanel(productTabPanel);