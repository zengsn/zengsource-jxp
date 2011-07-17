// Product List

Ext.onReady(function() {
	
	// Catalog Tree
	var catalogTreePanel = new Ext.tree.TreePanel({
		id: 'catalog-tree-panel',
		title: '产品目录',
		region:'west',
		split: true,
		width: 360,
		minSize: 320,
		frame: true,
		autoScroll: true,
		iconCls: 'icon-be-tree',
		
		// tree-specific configs:
		lines: true,
		useArrows: true,
		rootVisible: false,
		singleExpand: true,
		preloadChildren: true,
		
		loader: new Ext.tree.TreeLoader({
		  dataUrl:'product/catalog-tree.jxp'
		}),
		
		root: new Ext.tree.AsyncTreeNode(),
		tools: [{
			id: 'refresh',
			qtip: '刷新管理树',
			// hidden:true,
			handler: function(event, toolEl, panel) {
				var treeLoader = panel.getLoader();
				treeLoader.load(panel.getRootNode());
			}
		}]
	});
    
	// Assign the changeLayout function to be called on tree node click.
	catalogTreePanel.on('click', function(n, e){
		e.stopEvent();
		var sn = this.selModel.selNode || {}; // selNode is null on initial selection
		if(n.leaf && n.id != sn.id){  // ignore clicks on folders and currently selected node 
			//Ext.getCmp('content-panel').layout.setActiveItem(n.id + '-panel');
			//if(n.id=='catalog') {
					
			//}  		
			//if(!detailEl){
			//	var bd = Ext.getCmp('details-panel').body;
			//	bd.update('').setStyle('background','#fff');
			//	detailEl = bd.createChild(); //create default empty div
			//}
			//detailEl.hide().update(Ext.getDom(n.id+'-details').innerHTML).slideIn('l', {stopFx:true,duration:.2});
		} else if(n.id != sn.id){ //enable title expandable
			//treePanel.selectPath(n.getPath());
			n.expand(true, true);//expand node
		}
	});
	
	var Product = Ext.data.Record.create([
		'id', 'name', 'description'
	]);
	// Product Store
	var productStore = new Ext.data.Store({
		// load using HTTP
		url: 'product/list.jxp',
		
		// the return will be XML, so lets set up a reader
		reader: new Ext.data.XmlReader({
			id: 'id',
			record: 'product',
			totalRecords: 'totalCount'
		}, Product),
		baseParams: {
			catalog: '',
			action: 'xml'
		},
		listeners: {
			//'load' : function(store, records, options) {
			//	for(var i=0;i<records.length;i++) {
			//		pageArr[records[i].data.id] = i;
			//	}
			//}
		}
	});
	
	//show products views
	var showTpl = new Ext.XTemplate(
		'<tpl for=".">',
			'<div class="thumb-wrap" id="{id}">',
			'<div class="thumb"><img src="./upload/images/products/{image}" title="{name}"></div>',
		  '<a href="{href}"><span class="x-editable">{shortName}</span></a>',
		  '<span class="x-editable">{price}</span>',
		  '<span><a href="{design}" class="go-design">去定制</a></span>',
		  '</div>',
	    '</tpl>',
	    '<div class="x-clear"></div>'
	);
	
	// Product View	
	var productViewPanel = new Ext.Panel({
		id:'product-view',
		frame:true,
		//width:535,
		//height: 300,
		layout:'fit',
		region:'center',
		autoWidth: true,
		autoHeight:true,
		autoScroll: true,
		collapsible:true,
		animCollapse:true,
		title: '产品列表',
		
		items: new Ext.DataView({
			store: productStore,
			tpl: showTpl,
			autoHeight:true,
			multiSelect: true,
			overClass:'x-view-over',
			itemSelector:'div.thumb-wrap',
			emptyText: 'No images to display',
			
			//plugins: [
			    //new Ext.DataView.DragSelector(),
			    //new Ext.DataView.LabelEditor({dataIndex: 'name'})
			//],
			
			prepareData: function(data){
			    data.shortName = Ext.util.Format.ellipsis(data.name, 15);
			    //data.sizeString = Ext.util.Format.fileSize(data.size);
			    //data.dateString = data.lastmod.format("m/d/Y g:i a");
			    return data;
			},
			
			listeners: {
				selectionchange: {
					fn: function(dv,nodes){
						var l = nodes.length;
						var s = l != 1 ? 's' : '';
						//panel.setTitle('Simple DataView ('+l+' item'+s+' selected)');
					}
				}
			}
		}),
		
		bbar: new Ext.PagingToolbar({
		    pageSize: 25,
		    store: productStore,
		    displayInfo: true,
		    displayMsg: '第{0}-{1}条 / 共{2}条',
		    emptyMsg: "没有纪录",
		    items:['-']
		})	
	});
	
	// Product List Layout	
	var productListLayout = new Ext.Panel({
		id:'product-list-layout',
		//title: '产品列表',
	    layout:'border',   
	    width: 950,
	    height: 500,
	    frame: true, 
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
	    items: [catalogTreePanel, productViewPanel]
	});
	
	productListLayout.render('product-list');
	
});