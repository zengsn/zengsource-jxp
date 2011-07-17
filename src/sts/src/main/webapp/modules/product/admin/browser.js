/*
 * Product Browser.
 * Copyright(c) 2008, iCanWear.com
 * icanwear@hotmail.com
 * 
 * http://iCanWear.com/license
 */
 
var ProductCatalog = Ext.data.Record.create([
	'id', 'name', 'parent', 'description'
]);

var productCatalogStore = new Ext.data.Store({
	// load using HTTP
	url: 'product/catalog.jxp',
	baseParams: {
		action: 'list',
		type: 'xml'
	}, 

	// the return will be XML, so lets set up a reader
	reader: new Ext.data.XmlReader({
		id: 'id',
		record: 'Catalog',
		totalRecords: 'totalCount'
	}, ProductCatalog)
});
var ProductRecord = Ext.data.Record.create([
	'id', 'name', 'catalog', 'status', 'manager', 
	'viewCount', 'totalCount', 'soldCount', 'orderCount',
	'price', 'currency', 'material', 'shipping', 'usage',
	'imageSingle', 'imageFront', 'imageBack', 'imageLeft', 'imageRight', 'imageAbove', 'imageUnder', 'attachment',
	'createdTime', 'description'
]);
var productXmlStore = new Ext.data.Store({
	// load using HTTP
	url: 'product/manage.jxp',
	//url: '../xdb/products.xml',
	
	// the return will be XML, so lets set up a reader
	reader: new Ext.data.XmlReader({
		id: 'id',
		record: 'product',
		totalRecords: 'totalCount'
	}, ProductRecord),
	
	baseParams: {action: 'list'},
	listeners: {
		'beforeload' : function(){
			//this.baseParams.product = Ext.getCmp('search-product').getValue();
		}
	}
});

// =============================================================== // 
// Inner Panel: Product Catalog Tree Panel ======================= //
// =============================================================== //
var productCatalogTreePanel = new Ext.tree.TreePanel({
	id: 'product-catalog-tree',
	title: '产品目录',
    region:'north',
    height: 360,
	minSize: 360,
	margins: '0 0 0 0',
	cmargins: '0 0 5 0',
	autoScroll: true,
    collapsible: false,
    animCollapse: true,
	bodyStyle: 'padding:2px',
	iconCls: 'icon-be-tree',
	
	// tree-specific configs:
	lines: true,
	useArrows: true,
	rootVisible: false,
	singleExpand: false,
	preloadChildren: true,
	
	loader: new Ext.tree.TreeLoader({
		dataUrl:'product/catalog.jxp',
		baseAttrs: {description: 'None'},
		baseParams: {action:'list', type:'json'}
	}),
	
	root: new Ext.tree.AsyncTreeNode(),
    
    tbar: [new Ext.form.TextField({
		width: 230,
		emptyText:'查找目录 ... Not work yet',
		listeners:{
			render: function(f){
				f.el.on('keydown', filterTree, f, {buffer: 350});
			}
		}
	})],
    
    bbar: [{/*
        iconCls: 'icon-refresh',
		tooltip: 'Refresh',
        handler: function(){ 
        	var panel = productCatalogTreePanel;
        	panel.getLoader().load(panel.getRootNode()); 
        }
    }, '-', {*/
        iconCls: 'icon-expand-all',
		tooltip: 'Expand All',
        handler: function(){ productCatalogTreePanel.root.expand(true); }
    }, '-', {
        iconCls: 'icon-collapse-all',
        tooltip: 'Collapse All',
        handler: function(){ productCatalogTreePanel.root.collapse(true); }
    }, '-', {
		id: 'btn-delete-product-catalog',
		text: '删除',
		iconCls: 'btn-delete',
		disabled: true,
		handler: function() {}
	}],
    
	tools: [{
		id: 'refresh',
		qtip: '刷新目录树',
		// hidden:true,
		handler: function(event, toolEl, panel) {
			var treeLoader = panel.getLoader();
			treeLoader.load(panel.getRootNode());
		}
	}]
});
var filter = new Ext.tree.TreeFilter(productCatalogTreePanel, {
	clearBlank: true,
	autoClear: true
});
var hiddenCatalogs = [];
function filterTree(e){
	var tree = productCatalogTreePanel;
	var text = e.target.value;
	Ext.each(hiddenCatalogs, function(n){
		n.ui.show();
	});
	if(!text){
		filter.clear();
		return;
	}
	tree.expandAll();
	
	var re = new RegExp('^' + Ext.escapeRe(text), 'i');
	filter.filterBy(function(n){
		return re.test(n.text);
	});
	
	// hide empty packages that weren't filtered
	hiddenCatalogs = [];
	tree.root.cascade(function(n){
		if(n.ui.ctNode.offsetHeight < 3){
			n.ui.hide();
			hiddenCatalogs.push(n);
		}
	});
};
productCatalogTreePanel.getSelectionModel().on('selectionchange', function(selMod, node) {
	if(!node) { // Selection clear		
		return;
	}
	Ext.getCmp('btn-edit-product-catalog').enable();
	Ext.getCmp('btn-delete-product-catalog').enable();
	var catalogDetails = ''
		+ '<div id="product-catalog-id" style="display:none;">'+node.id+'</div>'
		+ '<h1>'+node.text+' ('+node.attributes.index+')</h1>'
		+ '<p style="color: Green;">['+node.attributes.parentName+']</p>'
		+ '<p>'+node.attributes.description+'</p>'
		+ '<p>......................................</p>'
		+ '<p>目录操作: <a href="">编辑</a> 或 <a href="">删除</a>。</p>';
	var productCatalogDetailEl = Ext.get('product-catalog-detail');
	productCatalogDetailEl.update(catalogDetails).slideIn('t', {stopFx:true,duration:.2});
	// Update View
	Ext.getCmp('gallery-view-panel').setTitle(node.text);
	productXmlStore.baseParams.catalog=node.id;
	productXmlStore.load({params:{start:0,limit:25}});
});

// =============================================================== // 
// Inner Panel: Product Grid Panel =============================== //
// =============================================================== //
var productSelMod = new Ext.grid.CheckboxSelectionModel();
var productColMod = new Ext.grid.ColumnModel([
	new Ext.grid.RowNumberer(), 
	productSelMod, 
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

var productGridPanel = new Ext.grid.GridPanel({
	id: 'product-grid-panel',
    title: 'Product List',
    region:'north',
    height: 360,
	minSize: 150,
	margins: '0 0 0 0',
	cmargins: '0 0 5 0',
    collapsible: false,
    animCollapse: true,
	bodyStyle: 'padding:0px',
	iconCls: 'icon-be-product',
	
	sm: productSelMod,
	cm: productColMod,
    store: productXmlStore,
    autoExpandColumn: 'name',
	
    viewConfig: {
        forceFit:true
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
    
    tbar: [ new Ext.form.TextField({
		width: 230,
		emptyText:'查找产品 ... ',
		listeners:{
			render: function(f){
				f.el.on('keydown', function(e) {
					var value = e.target.value;
					productXmlStore.baseParams.q=value;
					productXmlStore.load({params:{start:0,limit:25}});
				}, f, {buffer: 350});
			}
		}
	})],
    
    bbar: new Ext.PagingToolbar({
        pageSize: 25,
        store: productXmlStore,
        displayInfo: true,
        displayMsg: '{0} - {1} / {2}',
        emptyMsg: "No record!",
        items:[ '-', {
			id: 'btn-delete-product',
			text: '删除',
			iconCls: 'btn-delete',
			disabled: true,
			handler: function() {}
		}]
    })
});
productSelMod.on('rowselect', function(selMod, rowIndex, record) {
	var count = selMod.getCount();
	var details = '';
	if(count>1) { // Multiple select - display operations
		details = "Multiple select";
		Ext.getCmp('btn-edit-product').disable();
		Ext.getCmp('btn-delete-product').enable();
	} else if(count==1) { // Single select - display details, enable edit button
		//alert(record.data.imageSingle);
		details = '<h1>' + record.data.name + '<h1>'
			+ '<p>' + record.data.usage + '</p>'
			+ '<img width="64" height="64" src="../upload/images/product/' + record.data.imageSingle + '" />'
			+ '<img width="64" height="64" src="../upload/images/product/' + record.data.imageFront + '" />'
			+ '<img width="64" height="64" src="../upload/images/product/' + record.data.imageBack + '" />'
			+ '<img width="64" height="64" src="../upload/images/product/' + record.data.imageLeft + '" />'
			+ '<img width="64" height="64" src="../upload/images/product/' + record.data.imageRight + '" />'
			+ '<img width="64" height="64" src="../upload/images/product/' + record.data.imageAbove + '" />'
			+ '<img width="64" height="64" src="../upload/images/product/' + record.data.imageUnder + '" />';
		
		Ext.getCmp('btn-edit-product').enable();
		Ext.getCmp('btn-delete-product').enable();
	} else {
	}
	var productDetailEl = Ext.get('product-detail');
	productDetailEl.update(details).slideIn('t', {stopFx:true,duration:.2});
});

// =============================================================== // 
// Menu Panel: Product Browser -> Border Layout Panel ============ //
// =============================================================== //
var productCatalogWindow = new Ext.jxsite.CatalogEditWindow();
productCatalogWindow.on('hide', function(win) {
    Ext.getCmp('btn-add-product-catalog').toggle(false);
    Ext.getCmp('btn-edit-product-catalog').toggle(false);
	productCatalogStore.load();
	productCatalogTreePanel.getLoader().load(productCatalogTreePanel.getRootNode());
});
var productBrowserPanel = new Ext.Panel({
	id:'product-browser-panel',
	//title: 'Border Layout',
    layout:'border',
    bodyBorder: false,
	defaults: {
		//collapsible: true,
        split: true,
		animFloat: false,
		autoHide: false,
		useSplitTips: true
	},
    items: [ {
		//title: 'Main Content',
		collapsible: false,
        region:'west',
	    width: 240,
	    minSize: 180,
	    maxSize: 280,
		floatable: false,
		margins: '0 0 0 0',
		cmargins: '0 5 0 0',
		
		layout:'border',
	    bodyBorder: false,
		defaults: {
			//collapsible: true,
	        split: true,
			animFloat: false,
			autoHide: false,
			useSplitTips: true
		},
    	items: [ productCatalogTreePanel,{
	        //title: '目录明细',
			region: 'center',
			autoScroll: true,
			bodyStyle: 'padding:15px',
			iconCls: 'icon-be-product',
			bbar: [{
				text: '创建目录',
				iconCls: 'btn-add',
				enableToggle: true,
				id: 'btn-add-product-catalog',
				toggleHandler: function(btn, state) {
					if(state) {
						productCatalogWindow.show(btn.getId());
					} else {
						productCatalogWindow.hide(btn.getId());
					}
				}
			},{
				text: '编辑目录',
				iconCls: 'btn-add',
				disabled: true,
				enableToggle: true,
				id: 'btn-edit-product-catalog',
				toggleHandler: function(btn, state) {
					if(state) {
						productCatalogWindow.show(btn.getId());
						var catalogId = Ext.get('product-catalog-id').dom.innerHTML;
						if(!catalogId) {
							alert('请选择目录！');
							return;
						}
						productCatalogWindow.loadForm({catalogId: catalogId});
					} else {
						editCatalogWindow.hide('btn-add-gallery-catalog');
					}
				}
			}],
			contentEl: 'product-catalog-detail-panel'
	    }]
	}, {
		//title: 'Main Content',
		collapsible: false,
        region:'center',
		margins: '0 0 0 0',
		bodyStyle: 'padding:0px',
		layout:'border',
	    bodyBorder: false,
		defaults: {
			//collapsible: true,
	        split: true,
			animFloat: false,
			autoHide: false,
			useSplitTips: true
		},
    	items: [ productGridPanel,{
	        //title: '产品明细',
			region: 'center',
			autoScroll: true,
			bodyStyle: 'padding:15px',
			iconCls: 'icon-be-product',
			bbar: [{
				text: '创建产品',
				iconCls: 'btn-add',
				handler: function() {
					// Show node
	    			var backendTree = Ext.getCmp('tree-panel');
	    			backendTree.selectPath('/backend/product/product-editor');
	    			// Get TabPanel
		    		var tabPanel = Ext.getCmp('product-editor-panel');
		    		tabPanel.activate(0);
				}
			},{
				id: 'btn-edit-product',
				text: '编辑产品',
				iconCls: 'btn-edit',
				disabled: true,
				handler: function() {
		    		var count = productGridPanel.getSelectionModel().getSelections().length;
		    		if(count==0) {
		    			alert('Please select one record!');
		    		}else if(count>1) {
		    			alert('Select too many records!');
		    		}else {
		    			var record = productGridPanel.getSelectionModel().getSelected();
		    			//alert(record.data.id);
		    			// Show node
		    			var backendTree = Ext.getCmp('tree-panel');
		    			backendTree.selectPath('/backend/product/product-editor');
		    			// Get TabPanel
		    			var tabPanel = Ext.getCmp('product-editor-panel');
		        		var panel = new Ext.jxsite.ProductEditFormPanel({
		        			closable: true,
							productId: record.data.id,
							productName: record.data.name
						});
		        		tabPanel.add(panel);
		        		tabPanel.activate(panel);
		        		panel.loadForm(record);
			    	}
				}
			}],
			contentEl: 'product-detail-panel'
	    }]
	}]
});

productBrowserPanel.on('show', function(panel) {
	productXmlStore.load({params:{start:0,limit:25}});
});

// Register Panel
registerPanel(productBrowserPanel);