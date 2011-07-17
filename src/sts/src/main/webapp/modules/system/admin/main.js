/*
 * Main console of backend.
 * Copyright(c) 2008, iCanWear.com
 * icanwear@hotmail.com
 * 
 * http://iCanWear.com/license
 */

//
// This is the main layout definition.
//
Ext.onReady(function(){
	
	Ext.QuickTips.init();
	
	// This is an inner body element within the Details panel created to provide a "slide in" effect
	// on the panel body without affecting the body's box itself.  This element is created on
	// initial use and cached in this var for subsequent access.
	var detailEl;
	
	// This is the main content center region that will contain each example layout panel.
	// It will be implemented as a CardLayout since it will contain multiple panels with
	// only one being visible at any given time.
	var contentPanel = {
		id: 'content-panel',
		region: 'center', // this is what makes this panel into a region within the containing layout
		layout: 'card',
		margins: '2 5 5 0',
		activeItem: 0,
		border: false,
		items: ContentPanelArray
	};
    
	// Go ahead and create the TreePanel now so that we can use it below
	var treePanel = new Ext.tree.TreePanel({
		id: 'tree-panel',
		title: '后台管理',
		region:'north',
		split: true,
		height: 360,
		minSize: 150,
		//frame: true,
		autoScroll: true,
		iconCls: 'icon-be-tree',
		
		// tree-specific configs:
		lines: true,
		useArrows: true,
		rootVisible: false,
		singleExpand: true,
		preloadChildren: true,
		
		loader: new Ext.tree.TreeLoader({
		  //dataUrl:'../xdb/main-tree.json'
		  dataUrl:'system/tree.jxp'
		}),
		
		root: new Ext.tree.AsyncTreeNode({id: 'backend'}),
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
	treePanel.on('click', function(n, e){
		e.stopEvent();
		var sn = this.selModel.selNode || {}; // selNode is null on initial selection
		if(n.leaf && n.id != sn.id){  // ignore clicks on folders and currently selected node 
		} else if(n.id != sn.id){ //enable title expandable
		}
	});
	treePanel.getSelectionModel().on('selectionchange', function(selMod, n) {
		//alert(n.getPath());
		if(n.leaf) {
			var contentPanel = Ext.getCmp('content-panel');
			var panelId = n.id + '-panel';
			var panel = contentPanel.findById(panelId);
			if(!panel) { // No panel, use default panel
				panel = new Ext.jxsite.DefaultPanel({
					id: panelId,
					title: n.text
				});
				contentPanel.add(panel);
			}				
			//Ext.getCmp('content-panel').layout.setActiveItem(n.id + '-panel');
			contentPanel.layout.setActiveItem(panelId);
			if(n.id=='catalog') {
					
			}  		
			if(!detailEl){
				var bd = Ext.getCmp('details-panel').body;
				bd.update('').setStyle('background','#fff');
				detailEl = bd.createChild(); //create default empty div
			}
			var detailDom = Ext.getDom(n.id+'-details');
			var detailMarkup = '请添加内容到 <div id="' + n.id + '-details"></div>中显示自定义说明。';
			if (detailDom) {
				detailMarkup = detailDom.innerHTML;
			}
			detailEl.hide().update(detailMarkup).slideIn('l', {stopFx:true,duration:.2});
		} else { //enable title expandable
			//treePanel.selectPath(n.getPath());
			n.expand(true, true);//expand node
		}
	});
    
	// This is the Details panel that contains the description for each example layout.
	var detailsPanel = {
		id: 'details-panel',
	    //title: 'Details',
	    region: 'center',
	    bodyStyle: 'padding-bottom:15px;background:#fff;',
		autoScroll: true,
		html: '<p class="details-info">When you select a layout from the tree, additional details will display here.</p>',
		
		bbar: [{
			text: '注销',
			iconCls: 'btn-logout',
			handler: function() {
				window.location.replace('./index.jxp');
			}
		//}, '-', {
		//	text: '帮助',
		//	iconCls: 'btn-help'
		}, '->', {
			text: '返回',
			iconCls: 'btn-be-home',
			handler: function() {
				Ext.getCmp('content-panel').layout.setActiveItem('system-welcome-panel');
				if(!detailEl){
		  		var bd = Ext.getCmp('details-panel').body;
		  		bd.update('').setStyle('background','#fff');
	  			detailEl = bd.createChild(); //create default empty div
	  		}
	  		var defaultDetails = '<p class="details-info">When you select a layout from the tree, additional details will display here.</p>';
	  		detailEl.hide().update(defaultDetails).slideIn('l', {stopFx:true,duration:.2});
			}
		//}, '-', {
		//	text: '网站',
		//	iconCls: 'btn-home'
		}]
	};
	
	// Finally, build the main layout once all the pieces are ready.  This is also a good
	// example of putting together a full-screen BorderLayout within a Viewport.
	new Ext.Viewport({
		layout: 'border',
		title: 'Ext Layout Browser',
		items: [{
			xtype: 'box',
			region: 'north',
			applyTo: 'header',
			split:true,
			height: 30
		},{
			layout: 'border',
			id: 'layout-browser',
			region:'west',
			border: false,
			split:true,
			margins: '2 0 5 5',
			width: 218,
			minSize: 120,
			maxSize: 240,
			items: [treePanel, detailsPanel]
		}, contentPanel, { 
	        region: 'south',
	        //title: 'Information',
	        //collapsible: true,
	        //html: Ext.get('footer').dom.innerHTML,
	        contentEl: 'footer',
	        //split: true,
	        height: 32,
	        minHeight: 32
		}],
		renderTo: Ext.getBody()
	});
});
