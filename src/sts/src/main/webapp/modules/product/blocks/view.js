/*
 * Product Module - Shelf.
 * Copyright(c) 2008, iCanWear.com
 * icanwear@hotmail.com
 * 
 * http://iCanWear.com/license
 */

Ext.onReady(function(){
	
	Ext.QuickTips.init();	
	
	var viewHeight = 638;
	
	// =============================================================== // 
	// 3 columns border panel of product ============================= //
	// =============================================================== //
	var shelfPanel = new Ext.Panel({
		layout: 'border',
		height: viewHeight+2,
		autoScroll: false,
    	border: false,
    	//frame: true,
		items: [{/*
			id: 'north-panel',
			region: 'north',
			height: 24,
			border: true,
			split: true,
			contentEl: 'north'
		}, {*/
			id: 'west-panel',
			region: 'west',
			//title: 'West',
			width: 125,
		    split: true,
		    border: false,
			contentEl: 'west'
		}, {
			id: 'center-panel',
			//title: '团队定制',
			region: 'center',
			split: true,
			border: false,
			autoScroll: true,
			iconCls: 'icon-group-custom',
			contentEl: 'center'
		}, {
			id: 'east-panel',
			region: 'east',
			//title: 'East',
			width: 320,
			iconCls: 'icon-icanwear',
			split: true,
			border: false,
			contentEl: 'east' /*
		}, {
			id: 'south-panel',
			region: 'south',
			height: 24,
			border: true,
			split: true,
			contentEl: 'south' */
		}]
	});	
	shelfPanel.render('product-view');	
	
	// =============================================================== // 
	// Catalog Data Store ============================================ //
	// =============================================================== //
	var catalogStore = new Ext.data.Store({
	  // load using HTTP
	  url: '../xdb/subcatalog-01.xml',
	
	  // the return will be XML, so lets set up a reader
	  reader: new Ext.data.XmlReader({
			id: 'id',
			record: 'Catalog',
			totalRecords: 'totalCount'
		}, [
		   'id',
		   {name: 'name', mapping: 'name'},
		   {name: 'image', mapping: 'icon'},
		   'parent', 'banner', 'iconCls', 'desc'
		])
	});
	// =============================================================== // 
	// Catalogs View of West Panel =================================== //
	// =============================================================== // 
	var catalogTpl = new Ext.XTemplate(
		'<tpl for=".">',
			'<div class="thumb-wrap" id="{id}">',
			'<div class="thumb"><img src="../upload/images/catalogs/{image}" title="{name}"/></div>',
		  '<span class="x-editable">{shortName}</span>',
		  '</div>',
    '</tpl>',
    '<div class="x-clear"></div>'
	);
	var cacheData = [];
	var cacheIndex = 0;
	var catalogView = new Ext.DataView({
	    store: catalogStore,
	    tpl: catalogTpl,
	    autoHeight:true,
	    multiSelect: false,
	    singleSelect: true,
	    overClass:'x-view-over',
	    itemSelector:'div.thumb-wrap',
	    emptyText: 'No images to display',
	
	    //plugins: [
	        //new Ext.DataView.DragSelector(),
	        //new Ext.DataView.LabelEditor({dataIndex: 'name'})
	    //],
	
	    prepareData: function(data){
	    	cacheData[cacheIndex++] = data;
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
	    			var record = dv.getRecord(nodes[0]);
	    			if(record) {
		    			var tabPanel = Ext.getCmp('card-0');
		    			if(tabPanel) {
			    			tabPanel.activate('cat-' + record.data.id);
			    		}
		    		}
	    			//alert(record.data.name);
	    			//panel.setTitle('Simple DataView ('+l+' item'+s+' selected)');
	    		}
	    	}
	    }
	});  
	catalogStore.on('load', function() {
		catalogView.select(0);
		//alert('COUNT: ' + catalogStore.getCount());
		runAfterCatalogLoaded();
	});
	catalogStore.load();
	// =============================================================== // 
	// Catalog List Panel ============================================ //
	// =============================================================== // 
	var catalogListPanel = new Ext.Panel({
		id: 'catalog-panel',
		//region: 'west',
		width: 125,
		height:viewHeight,
		minSize: 160,
		maxSize: 300,
		split: true,
		border: true,
		frame: true,
		iconCls: 'icon-cat',
		//autoWidth: true,
		//autoHeight:true,
		autoScroll: true,
		//collapsible:true,
		//animCollapse:true,
		layout:'fit',
		title: '选择类型',
		
		items: [catalogView]
    
	});//~end-catalogPanel
	catalogListPanel.render('catalog-list');
	// =============================================================== // 
	// Product Details Panel ========================================= //
	// =============================================================== //
	var detailPanel = new Ext.Panel({
		title: '如何定制个性T恤?',
	    autoWidth: true,
	    //autoHeight:true,
	    height: viewHeight,
	    layout:'fit',
	    autoScroll: true,
	    frame: true,
	    iconCls: 'icon-info',
	    //collapsible:true,
	    //animCollapse:true,
	    bodyStyle: {
	    	background: '#FFFFFF',
	    	padding:'5px'
	    },
	    //tools: [{
	    //	id: 'refresh',
	    //	qtip: '重置',
	    //	handler: function(event, toolEl, panel) {
	    //  }
	    //}],
	    contentEl: 'detail-content'			
	});
	detailPanel.render('detail-panel');
	detailPanel.body.on('click', function(evt, node, o) {
		//evt.stopEvent();
		//alert(node);
	});	
	
	/////////////////////////////////////////////////////////////////////
	// RUN AFTER THE CATALOG STORE LOAD !!! /////////////////////////////
	/////////////////////////////////////////////////////////////////////
	function runAfterCatalogLoaded() {		
		//setTimeout(function(){ //loading mask
	    Ext.get('loading').remove();
	    Ext.get('loading-mask').remove();
	    //Ext.get('loading-mask').fadeOut({remove:true});
	    //}, 250);	
		// =============================================================== // 
		// Create all Subcatalog Panels and Cache them by ID ============= //
		// =============================================================== // 
		var cacheTpls = [];
		var cacheViews = [];
		var cacheStores = [];
		var cachePanels = [];
		//alert('COUNT: ' + catalogStore.getTotalCount());
		for(var i=0;i<catalogStore.getCount();i++) {
			//for(var i=0;i<5;i++) {
			var record = catalogStore.getAt(i);
			var id = record.data.id;
			var tabId = 'cat-' + record.data.id;
			var tabTitle = record.data.name;
			var tabIconCls = record.data.iconCls;
			var catBanner = '../upload/images/catalogs/' + record.data.banner;
			var catDesc = record.data.desc;
			if(!cacheTpls[tabId]) { // create template
				cacheTpls[tabId] = new Ext.XTemplate(
					'<div class="cat-banner"><img src="' + catBanner + '" /></div>',
					'<p>' + catDesc + '</p>',
					'<tpl for=".">',
						'<div class="thumb-wrap" id="{id}">',
						'<div class="thumb"><img id="{id}" src="../upload/images/products/{image}" title="{name}" />&nbsp;</div>',
					  '<div><span class="x-editable">{shortName}</span></div>',
					  '<div><span class="x-editable">{price}</span></div>',
					  '<center><div class="popular" style="width:{popular*16}px"></div><center>',
					  //'<span><a href="{design}" class="go-design">去定制</a></span>',
					  '</div>',
			    '</tpl>',
			    '<div class="x-clear"></div>'
				);	
			}//~end-template
			var tpl = cacheTpls[tabId];
			if(!cacheStores[tabId]) { // create store
				cacheStores[tabId] = new Ext.data.Store({
				  // load using HTTP
				  url: '../xdb/show-catalog-' + id + '.xml',
				
				  // the return will be XML, so lets set up a reader
				  reader: new Ext.data.XmlReader({
						id: 'id',
						record: 'item',
						totalRecords: 'totalCount'
					}, ['id', 'name', 'href', 'image', 'price', 'discount', 'popular', 'design'])
				});  			
			}//~end-store
			var store = cacheStores[tabId];
			if(!cacheViews[tabId]) { //create view
				cacheViews[tabId] = new Ext.DataView({
				    tpl: tpl,
				    store: store,
				    autoHeight:true,
				    multiSelect: false,
				    singleSelect: true,
				    overClass:'x-view-over',
				    itemSelector:'div.thumb-wrap',
				    emptyText: 'No images to display',
				
				    //plugins: [
				        //new Ext.DataView.DragSelector(),
				        //new Ext.DataView.LabelEditor({dataIndex: 'name'})
				    //],
				
				    prepareData: function(data){
				        data.shortName = Ext.util.Format.ellipsis(data.name, 12);
				        //data.sizeString = Ext.util.Format.fileSize(data.size);
				        //data.dateString = data.lastmod.format("m/d/Y g:i a");
				        return data;
				    },
				    
				    listeners: {
				    	selectionchange: {
				    		fn: function(dv,nodes){
				    			var l = nodes.length;
				    			var s = l != 1 ? 's' : '';
				    			var record = dv.getRecord(nodes[0]);
				    			var bd = detailPanel.body;
				    			bd.update('');
				    			var detailEl = bd.createChild()
				    			//var detailEl = Ext.get('detail-content');
				    			//detailEl.update('');
				    			var html = '<img src="../upload/images/products/' + record.data.image + '" width="300"/>'
				    			         + '<center><a href="' + record.data.href + '" target="_blank">'
				    			         + '<p class="product-name">' + record.data.name 
				    			         + '</p></a></center>'
				    			         + '<center><p class="product-price">' + record.data.price 
				    			         + '</p></center>'
				    			         + '<center><div id="btn-' + record.data.id + '"></div></center>' 
				    			         + '<hr />'
				    			         + '<div id="info">'
				    			         + '<p># 材质：100%精梳全棉</p>'
				    			         + '<p># 颜色：白色,插肩部分是黑色</p>'
				    			         + '<p># 最大可印区域：前幅/后幅：29cm×20cm(A4大小)</p>'
				    			         + '<p># 工艺：热转印</p>'
				    			         + '</div><div id="discount">'
				    			         + '<p>' + record.data.discount + '</p>'
				    			         + '</div>';
				    			detailEl.update(html).slideIn('l', {stopFx:true,duration:.2});
				    			detailPanel.setTitle(record.data.name + ' (库存量: 32件)');
				    			new Ext.Button({
				    				renderTo: 'btn-' + record.data.id,
				    				text: '马上定制',
				    				handler: function() {
				    					window.location.replace('./'+record.data.design);
				    				}
				    			});
				    			//var tabPanel = Ext.getCmp('card-0');
				    			//tabPanel.activate('cat-' + record.data.id);
				    			//alert(record.data.name);
				    			//panel.setTitle('Simple DataView ('+l+' item'+s+' selected)');
				    		}//~fn
				    	}//~selectionchange
				    }//~listeners
				});//~DataView
			}//~end-view
			var dataView = cacheViews[tabId];
			if(!cachePanels[tabId]) { // create panel
				cachePanels[tabId] = new Ext.Panel({
					id: tabId,
					title: tabTitle,
		    		layout:'fit',
		    		//frame: true,
		   			iconCls: tabIconCls,
		   			autoScroll: true,
					items: dataView
				});//~new-panel
			}//~end-panel
		}//~end-for
		//alert(cacheTpls);
		//alert(cacheStores);
		//alert(cachePanels);
		
		//All T-Shirts Panel tab with catalog
		var tshirtCatalogPanel = new Ext.TabPanel({
			id: 'card-0',
			plain: false,
			border: false,
			activeItem: 0,
			frame: true,
			defaults: {bodyStyle: 'padding:5px'},
			items: [
				cachePanels['cat-2'], 
				cachePanels['cat-3'], 
				cachePanels['cat-4'], 
				cachePanels['cat-5'], 
				cachePanels['cat-6']
			]
		});
		
		var cardNav = function(incr){
			var l = Ext.getCmp('card-wizard-panel').getLayout();
			var i = l.activeItem.id.split('card-')[1];
			var next = parseInt(i) + incr;
			l.setActiveItem(next);
			Ext.getCmp('card-prev').setDisabled(next==0);
			Ext.getCmp('card-next').setDisabled(next==2);
		};
		
		var designWizard = new Ext.Panel({
			id:'card-wizard-panel',
			//title: 'Card Layout (Wizard)',
			layout:'card',
			activeItem: 0,
			height: viewHeight,
			region: 'center',
		    split: true,
		    border: true,
		    autoScroll: true,
		    iconCls: 'icon-group-custom',
			bodyStyle: 'padding:0px',
			defaults: {border:false},
			/*
			bbar: ['->', {
				id: 'card-prev',
				text: '&laquo; Previous', 
				handler: cardNav.createDelegate(this, [-1]),
				disabled: true
			},{
				id: 'card-next',
				text: 'Next &raquo;', 
				handler: cardNav.createDelegate(this, [1])
			}],
			*/
			items: [ tshirtCatalogPanel,{
				id: 'card-1',
				title: '个性定制',
				html: '<p>Step 2 of 3</p><p>Almost there.  Please click the "Next" button to continue...</p>'
			},{
				id: 'card-2',
				title: '结算支付',
				html: '<h1>Congratulations!</h1><p>Step 3 of 3 - Complete</p>'
			}]
		});	
		
		tshirtCatalogPanel.on('tabchange', function(p, tab) {
			var id = tab.id;
			cacheStores[id].load();
			id = id.replace('cat-', '');
			catalogView.select(id);
		});
		
		designWizard.render('design-wizard');
	
	}//~end-runAfterCatalogLoaded ///////////////////////////////////////
	
});