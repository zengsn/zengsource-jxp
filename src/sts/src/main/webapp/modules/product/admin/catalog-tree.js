
// Product Catalog Tree
var productCatalogTree = new Ext.tree.TreePanel({
	el: 'product-catalog-tree-ct',
	id: 'productcatalog-tree',
	title: '��ƷĿ¼��',
	region:'west',
	autoScroll: true,
	collapsible: true,
	width: 320,
	minSize: 240,
	maxSize: 360,
	margins: '5 0 5 5',
	iconCls: 'icon-be-tree',
	bodyStyle: 'padding:2px',
	
	// tree-specific configs:
	lines: true,
	useArrows: true,
	rootVisible: false,
	singleExpand: true,
	preloadChildren: true,
	
	loader: new Ext.tree.TreeLoader({
		dataUrl:'product/catalog.jxp',
		baseParams: {action:'list', type:'json'},
	}),
	
	root: new Ext.tree.AsyncTreeNode(),
	tools: [{
		id: 'refresh',
		qtip: 'ˢ��Ŀ¼��',
		// hidden:true,
		handler: function(event, toolEl, panel) {
			var treeLoader = panel.getLoader();
			treeLoader.load(panel.getRootNode());
		}
	}]
});