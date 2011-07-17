// Depandencies:
// 1.
// 2.

Ext.namespace('Ext.ux');

Ext.ux.TreeFormPanel = function(config) {
	
	config = config || {};
	
	// Tree
	config.tree = config.tree || {};
	config.tree = Ext.applyIf(config.tree || {}, {
		id: 'tree-panel',
		title: 'Tree',
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
		
		//loader: this.treeLoader,
		
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
	this.treePanel = new Ext.tree.TreePanel(config.tree);
	
	// Form	
	config.form = Ext.applyIf(config.form || {}, {
		title: 'Form',
		collapsible: false,
		region:'center',
		margins: '5 5 5 0',
		cmargins: '5 5 0 0',
		bodyStyle: 'padding:15px'
	});
	this.formPanel = new Ext.form.FormPanel(config.form);
	
	config = Ext.applyIf(config, {
		id: 'tree-form-panel',
		title: 'Tree with Form',
		layout: 'border',
		height: 600,
		//width: 500,
		//autoHeight: true,
		bodyBorder: false,
		defaults: {
			collapsible: false,
			split: true,
			animFloat: false,
			autoHide: false,
			useSplitTips: true			
		},
	    waitMsgTarget: true,
		items: [this.treePanel, this.formPanel]
	});
	
	Ext.apply(this, config);
	
	Ext.ux.TreeFormPanel.superclass.constructor.apply(this, arguments);
	
};

Ext.extend(Ext.ux.TreeFormPanel, Ext.Panel, {
	getFormPanel : function() {
		return this.formPanel;
	},
	
	getTreePanel : function() {
		return this.uploadPanel;
	}
});