/*
 * Product Browser.
 * Copyright(c) 2008, iCanWear.com
 * icanwear@hotmail.com
 * 
 * http://iCanWear.com/license
 */
 
var NewsCategory = Ext.data.Record.create([
	'id', 'name', 'parent', 'index'
]);

var newsCategoryStore = new Ext.data.Store({
	// load using HTTP
	url: 'news/category.jsp',
	baseParams: {
		action: 'list',
		type: 'xml'
	}, 

	// the return will be XML, so lets set up a reader
	reader: new Ext.data.XmlReader({
		id: 'id',
		record: 'Catalog',
		totalRecords: 'totalCount'
	}, NewsCategory)
});
var NewsEntryRecord = Ext.data.Record.create([
	'id', 'title', 'content', 'writer', 'status', 'category', 'viewCount',
	'createdTime', 'description'
]);
var newsEntryXmlStore = new Ext.data.Store({
	// load using HTTP
	url: 'news/manage.jsp',
	//url: '../xdb/products.xml',
	
	// the return will be XML, so lets set up a reader
	reader: new Ext.data.XmlReader({
		id: 'id',
		record: 'Entry',
		totalRecords: 'totalCount'
	}, NewsEntryRecord),
	
	baseParams: {action: 'list'},
	listeners: {
		'beforeload' : function(){
			//this.baseParams.product = Ext.getCmp('search-product').getValue();
		}
	}
});

// =============================================================== // 
// Inner Panel: News Category Tree Panel ========================= //
// =============================================================== //
var newsCategoryTreePanel = new Ext.tree.TreePanel({
	id: 'news-category-tree',
	title: '新闻分类',
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
		dataUrl:'news/category.jsp',
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
        handler: function(){ newsCategoryTreePanel.root.expand(true); }
    }, '-', {
        iconCls: 'icon-collapse-all',
        tooltip: 'Collapse All',
        handler: function(){ newsCategoryTreePanel.root.collapse(true); }
    }, '-', {
		id: 'btn-delete-newscategory',
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
var filter = new Ext.tree.TreeFilter(newsCategoryTreePanel, {
	clearBlank: true,
	autoClear: true
});
var hiddenCatalogs = [];
function filterTree(e){
	var tree = newsCategoryTreePanel;
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
newsCategoryTreePanel.getSelectionModel().on('selectionchange', function(selMod, node) {
	if(!node) { // Selection clear		
		return;
	}
	Ext.getCmp('btn-edit-newscategory').enable();
	Ext.getCmp('btn-delete-newscategory').enable();
	var categoryDetails = ''
		+ '<div id="newscategory-id" style="display:none;">'+node.id+'</div>'
		+ '<h1>'+node.text+' ('+node.attributes.index+')</h1>'
		+ '<p style="color: Green;">['+node.attributes.parentName+']</p>'
		+ '<p>'+node.attributes.description+'</p>'
		+ '<p>......................................</p>'
		+ '<p>目录操作: <a href="">编辑</a> 或 <a href="">删除</a>。</p>';
	var newsCategoryDetailEl = Ext.get('newscategory-detail');
	newsCategoryDetailEl.update(categoryDetails).slideIn('t', {stopFx:true,duration:.2});
	// Update View
	Ext.getCmp('newscategory-view-panel').setTitle(node.text);
	newsEntryXmlStore.baseParams.catalog=node.id;
	newsEntryXmlStore.load({params:{start:0,limit:25}});
});

// =============================================================== // 
// Inner Panel: News Grid Panel ================================== //
// =============================================================== //
var newsEntrySelMod = new Ext.grid.CheckboxSelectionModel();
var newsEntryColMod = new Ext.grid.ColumnModel([
	new Ext.grid.RowNumberer(), 
	newsEntrySelMod, 
{
	id: 'title',
	header: '标题',
	dataIndex: 'title',
	width: 256
}, {
	id: 'category',
	header: '分类',
	dataIndex: 'category',
	width: 128
}, {
	id: 'writer',
	header: '作者',
	dataIndex: 'writer',
	width: 64
}, {
	header: '状态',
	align: 'center',
	dataIndex: 'status',
	width: 64
}, {
	header: '阅读',
	align: 'center',
	dataIndex: 'viewCount',
	width: 80
}, {
	id: 'createdTime',
	header: '发布时间',
	dataIndex: 'createdTime'
}]);

var newsEntryGridPanel = new Ext.grid.GridPanel({
	id: 'newsentry-grid-panel',
    title: '新闻列表',
    region:'north',
    height: 360,
	minSize: 150,
	margins: '0 0 0 0',
	cmargins: '0 0 5 0',
    collapsible: false,
    animCollapse: true,
	bodyStyle: 'padding:0px',
	iconCls: 'icon-be-product',
	
	sm: newsEntrySelMod,
	cm: newsEntryColMod,
    store: newsEntryXmlStore,
    autoExpandColumn: 'createdTime',
	
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
					newsEntryXmlStore.baseParams.q=value;
					newsEntryXmlStore.load({params:{start:0,limit:25}});
				}, f, {buffer: 350});
			}
		}
	})],
    
    bbar: new Ext.PagingToolbar({
        pageSize: 25,
        store: newsEntryXmlStore,
        displayInfo: true,
        displayMsg: '{0} - {1} / {2}',
        emptyMsg: "No record!",
        items:[ '-', {
			id: 'btn-delete-newsentry',
			text: '删除',
			iconCls: 'btn-delete',
			disabled: true,
			handler: function() {}
		}]
    })
});
newsEntrySelMod.on('rowselect', function(selMod, rowIndex, record) {
	var count = selMod.getCount();
	var details = '';
	if(count>1) { // Multiple select - display operations
		details = "Multiple select";
		Ext.getCmp('btn-edit-newsentry').disable();
		Ext.getCmp('btn-delete-newsentry').enable();
	} else if(count==1) { // Single select - display details, enable edit button
		//alert(record.data.imageSingle);
		details = '<h1>' + record.data.title + '<h1>'
			+ '<p>' + record.data.writer + ', ' + record.data.createdTime + '</p>'
			+ '<hr />'
			+ '<p>' + record.data.content + '</p>';
		
		Ext.getCmp('btn-edit-newsentry').enable();
		Ext.getCmp('btn-delete-newsentry').enable();
	} else {
	}
	var newsEntryDetailEl = Ext.get('newsentry-detail');
	newsEntryDetailEl.update(details).slideIn('t', {stopFx:true,duration:.2});
});

// =============================================================== // 
// Menu Panel: News Manager -> Border Layout Panel ============ //
// =============================================================== //
var newsCategoryWindow = new Ext.jxsite.NewsCategoryEditWindow();
newsCategoryWindow.on('hide', function(win) {
    Ext.getCmp('btn-add-newscategory').toggle(false);
    Ext.getCmp('btn-edit-newscategory').toggle(false);
	newsCategoryStore.load();
	newsCategoryTreePanel.getLoader().load(newsCategoryTreePanel.getRootNode());
});
var newsManagePanel = new Ext.Panel({
	id:'news-manage-panel',
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
    	items: [ newsCategoryTreePanel,{
    		id: 'newscategory-view-panel',
	        //title: '目录明细',
			region: 'center',
			autoScroll: true,
			bodyStyle: 'padding:15px',
			iconCls: 'icon-be-product',
			bbar: [{
				text: '创建分类',
				iconCls: 'btn-add',
				enableToggle: true,
				id: 'btn-add-newscategory',
				toggleHandler: function(btn, state) {
					if(state) {
						newsCategoryWindow.show(btn.getId());
					} else {
						newsCategoryWindow.hide(btn.getId());
					}
				}
			},{
				text: '修改分类',
				iconCls: 'btn-add',
				disabled: true,
				enableToggle: true,
				id: 'btn-edit-newscategory',
				toggleHandler: function(btn, state) {
					if(state) {
						newsCategoryWindow.show(btn.getId());
						var categoryId = Ext.get('newscategory-id').dom.innerHTML;
						if(!categoryId) {
							alert('请选择分类！');
							return;
						}
						newsCategoryWindow.loadForm({categoryId: categoryId});
					} else {
						newsCategoryWindow.hide('btn-add-newscategory');
					}
				}
			}],
			contentEl: 'newscategory-detail-panel'
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
    	items: [ newsEntryGridPanel,{
	        //title: '产品明细',
			region: 'center',
			autoScroll: true,
			bodyStyle: 'padding:15px',
			iconCls: 'icon-be-product',
			bbar: [{
				id: 'btn-add-newsentry',
				text: '创建新闻',
				iconCls: 'btn-add',
				handler: function() {
					// Show node
	    			var backendTree = Ext.getCmp('tree-panel');
	    			backendTree.selectPath('/backend/news/news-editor');
	    			// Get TabPanel
		    		var tabPanel = Ext.getCmp('news-editor-panel');
		    		tabPanel.activate(0);
				}
			},{
				id: 'btn-edit-newsentry',
				text: '编辑新闻',
				iconCls: 'btn-edit',
				disabled: true,
				handler: function() {
		    		var count = newsEntryGridPanel.getSelectionModel().getSelections().length;
		    		if(count==0) {
		    			alert('Please select one record!');
		    		}else if(count>1) {
		    			alert('Select too many records!');
		    		}else {
		    			var record = newsEntryGridPanel.getSelectionModel().getSelected();
		    			//alert(record.data.id);
		    			// Show node
		    			var backendTree = Ext.getCmp('tree-panel');
		    			backendTree.selectPath('/backend/news/news-editor');
		    			// Get TabPanel
		    			var tabPanel = Ext.getCmp('news-editor-panel');
		        		var panel = new Ext.jxsite.NewsEntryEditFormPanel({
		        			closable: true,
							newsEntryId: record.data.id,
							newsEntryName: record.data.name
						});
		        		tabPanel.add(panel);
		        		tabPanel.activate(panel);
		        		panel.loadForm(record);
			    	}
				}
			}],
			contentEl: 'newsentry-detail-panel'
	    }]
	}]
});

newsManagePanel.on('show', function(panel) {
	newsEntryXmlStore.load({params:{start:0,limit:25}});
});

// Register Panel
registerPanel(newsManagePanel);