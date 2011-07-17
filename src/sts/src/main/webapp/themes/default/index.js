/*
 * Main console of backend.
 * Copyright(c) 2008, iCanWear.com
 * icanwear@hotmail.com
 * 
 * http://iCanWear.com/license
 */

Ext.onReady(function(){
	
	Ext.QuickTips.init();	
	
	//3 columns border panel of home page
	var homeBP = new Ext.Panel({
		layout: 'border',
		height: 1688,
		autoScroll: false,
    border: false,
		items: [{
			id: 'north-panel',
			region: 'north',
			height: 24,
      border: true,
      split: true,
			contentEl: 'north'
		}, {
			id: 'west-panel',
			//title: '个人定制',
			region: 'west',
			//margins: '5 0 0 5',
      //cmargins: '5 5 0 5',
      iconCls: 'icon-personal-custom',
      width: 196,
      //height: 300,
      minSize: 100,
      maxSize: 300,
      split: true,
      border: false,
			contentEl: 'west'
		}, {
			id: 'center-panel',
			//title: '团队定制',
			region: 'center',
      split: false,
      border: false,
      autoScroll: true,
      iconCls: 'icon-group-custom',
			contentEl: 'center'
		}, {
			id: 'east-panel',
			//title: 'iCanWear.com',
			region: 'east',
			//margins: '5 0 0 5',
      //cmargins: '5 5 0 5',
      iconCls: 'icon-icanwear',
			width: 197,
      split: true,
      border: false,
			contentEl: 'east'
		}, {
			id: 'south-panel',
			region: 'south',
			height: 24,
      border: true,
      split: true,
			html: '系统提示'
		}]
	});	
	homeBP.render('home-bp');
	
	//inner panel
	var innerPanels = Ext.query('div[class="i-inner-panel"]');
	//alert(innerPanels.length);
	for(var i=0; i<innerPanels.length; i++) {
		var pnId = innerPanels[i].id;
		var cnId = pnId.replace('-panel', '-content');	
		var title = Ext.get(pnId).dom.title;
		var panel = new Ext.Panel({
			title: title,
	    autoWidth: true,
	    autoHeight:true,
	    layout:'fit',
	    collapsible:true,
	    animCollapse:true,
	    bodyStyle: {
	    	//background: '#DCDCDC',
	    	padding:'5px'
	    },
	    contentEl: cnId			
		});
		panel.render(pnId);	
		//alert(panel);
	}
	
	//main TabPabel
	var perTitle = Ext.get('personal-panel').dom.title;
	var perPanel = new Ext.Panel({
		id: 'per-tab',
		title: perTitle,
		//title: '<span id="per-title">' + perTitle + '</span>',
    bodyStyle: 'background: #FFA519',
    contentEl: 'personal-panel'
	});
	var grpTitle = Ext.get('group-panel').dom.title;
	var grpPanel = new Ext.Panel({
		id: 'grp-tab',
		title: grpTitle,
		//title: '<span id="grp-title">' + grpTitle + '</span>',
    bodyStyle: 'background-color:#D5EAFD',
    contentEl: 'group-panel'
	});
	var mainPanel = new Ext.TabPanel({
		activeTab: 0,
		//activeItem: 0,
		height: 188,
		items: [perPanel, grpPanel]
	});
	mainPanel.render('main-panel');
	var perTitle = mainPanel.getTabEl(mainPanel.getItem('per-tab'));
	Ext.get(perTitle).on('mouseover', function(){
		mainPanel.activate('per-tab');
	});
	var grpTitle = mainPanel.getTabEl(mainPanel.getItem('grp-tab'));
	Ext.get(grpTitle).on('mouseover', function(){
		mainPanel.activate('grp-tab');
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
	var storeCache = []; // cache stores
	var showArr = Ext.query('div[class="show-view"]');
	for(var i=0;i<showArr.length;i++) {
		var blockEl = Ext.get(showArr[i]); 
		var title = blockEl.dom.title;
		var showId = blockEl.id;
		blockEl.createChild('<div id="inner-' + showId + '"></div>');
		//alert(showId);
		if(!storeCache[showId]) {
			storeCache[showId] = new Ext.data.Store({
			  // load using HTTP
			  url: './xdb/' + showId + '.xml',			
			  // the return will be XML, so lets set up a reader
			  reader: new Ext.data.XmlReader({
					id: 'id',
					record: 'item',
					totalRecords: 'totalCount'
				}, ['id', 'name', 'href', 'image', 'price', 'design'])
			});
			storeCache[showId].load();
		}
		var panel = new Ext.Panel({
      //id:'images-view',
      //frame:true,
      //width:535,
      //height: 300,
      autoWidth: true,
      autoHeight:true,
      autoScroll: true,
      collapsible:true,
    	animCollapse:true,
      layout:'fit',
      title: title,

      items: new Ext.DataView({
        store: storeCache[showId],
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
      })
		});
		panel.render('inner-' + showId);
	}
	
	
	//functionality columns	
	var funcColumnPanel = new Ext.Panel({
		id:'functionalities-panel',
		//title: '后台首页',
	  layout:'column',
	  //bodyStyle:'padding:4px',
	  border: false,
		//iconCls: 'icon-be-home',
		autoScroll: true,
		defaults: {bodyStyle:'padding:15px'},
	  items: [{
	  	xtype: 'panel',
			height: 240,
			id: 'service-center-panel',
	    title: '快捷服务',
	    iconCls: 'icon-customer-service',
			columnWidth: .20,
			contentEl: 'service-center'
		},{
	  	xtype: 'panel',
			height: 240,
			title: 'First-Time Visitors',
			columnWidth: .20,
			contentEl: 'fresh-visitor'
		},{
			height: 240,
			title: 'More about...',
			columnWidth: .20,
			contentEl: 'more-about'
		},{
			height: 240,
			title: 'Product Lines',
			columnWidth: .20,
			contentEl: 'product-lines'
		},{
			height: 240,
			title: 'About Us',
			columnWidth: .199,
			contentEl: 'about-us'
		}]
	});
	funcColumnPanel.render('functionalities');	
	
});