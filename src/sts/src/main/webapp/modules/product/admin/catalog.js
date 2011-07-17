/*
 * Product Module Configuration.
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

// Product Catalog Tree
var productCatalogTree = new Ext.tree.TreePanel({
	id: 'productcatalog-tree',
	title: '产品目录树',
	region:'west',
	autoScroll: true,
	collapsible: true,
	width: 320,
	minSize: 240,
	maxSize: 360,
	margins: '5 0 40 5',
	iconCls: 'icon-be-tree',
	bodyStyle: 'padding:2px',
	
	// tree-specific configs:
	lines: true,
	useArrows: true,
	rootVisible: false,
	singleExpand: false,
	preloadChildren: true,
	
	loader: new Ext.tree.TreeLoader({
		dataUrl:'product/catalog.jxp',
		baseParams: {action:'list', type:'json'}
	}),
	
	root: new Ext.tree.AsyncTreeNode(),
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

//productCatalogTree.root.expand(true);

// turn on validation errors beside the field globally
//Ext.form.Field.prototype.msgTarget = 'side';
    
// Product Catalog Edit Form
var productCatalogForm = new Ext.FormPanel({
	region:'center',
	id: 'productcatalog-form',
    title:'新建目录',
	margins: '5 5 5 0',
	//cmargins: '5 5 0 0',
    //labelAlign: 'left',
    labelWidth: 85,
    //width:340,
	//labelPad: 20,
	layoutConfig: {
		//labelSeparator: ':'
	},
	collapsible: false,
	buttonAlign: 'left',
	defaults: {
		//width: 230,
		//msgTarget: 'side'
	},
	iconCls: 'icon-be-baseconfig',
	bodyStyle: 'padding:15px',
    defaultType: 'textfield',
    waitMsgTarget: true,

    // configure how to read the XML Data
    reader : new Ext.data.XmlReader({
        record : 'Catalog',
        success: '@success'
    }, [
        'id', 'name', 'index', 'parent', 'description'
    ]),

    // reusable eror reader class defined at the end of this file
    errorReader: new Ext.form.XmlErrorReader(),

    items: [{
        xtype:'hidden',
        fieldLabel: 'id',
        name: 'id'
    }, new Ext.ux.form.Spinner({
		fieldLabel: '目录位置',
		name: 'index',
        //width: 300,
		strategy: new Ext.ux.form.Spinner.NumberStrategy({
			minValue:'1'//, maxValue:'130'
		})
  	}), {/*
        xtype:'numberfield',
        fieldLabel: '目录位置',
        allowDecimals: false,
        allowNegative: false,
        minValue: 0,
        minText: '最小值为0.',
        emptyText:'0',
        width: 320,
        name: 'index'
    }, {*/
        xtype:'combo',
        fieldLabel: '上级目录',
        hiddenName:'parent',
        store: productCatalogStore,
        valueField:'id',
        displayField:'name',
        typeAhead: true,
        //mode: 'local',
        triggerAction: 'all',
        emptyText:'默认为最高级目录.',
        selectOnFocus:true
    }, {
        xtype:'textfield',
        fieldLabel: '目录名称',
        allowBlank: false,
        blankText: '名称不能为空!', 
        width: 320,
        name: 'name'
    }, {
        //xtype:'htmleditor',
        xtype:'textarea',
        id:'description',
        fieldLabel:'目录描述',
        height:64,
        width: 320
    } ]
});

// simple button add
var reloadProductCatalogBtn = productCatalogForm.addButton({
    text: '重置',
    handler: function(){
    	var catalogId = productCatalogForm.getForm().getValues().id;
    	if(catalogId=='') {
    		productCatalogForm.getForm().reset();
    	} else {
        	productCatalogForm.getForm().load({
	        	method: 'post',
	        	url:'product/catalog.jxp', 
	        	params: { action:'load', id:catalogId},
        		waitMsg:'Loading ... '
        	});
    	}
    }
});

// explicit add
var submit = productCatalogForm.addButton({
    text: '保存',
    disabled:false,
    handler: function(){
        productCatalogForm.getForm().submit({
        	method: 'post',
        	url:'product/catalog.jxp', 
        	params: {action: 'edit'},
        	success: function() {
        		productCatalogStore.load();
        		productCatalogTree.getLoader().load(productCatalogTree.getRootNode());
        	},
        	waitMsg:'Saving ...'
        });
    }
});

var cancel = productCatalogForm.addButton({
    text: '取消',
    disabled:false,
    handler: function(){
        productCatalogForm.getForm().reset();
    }
});

productCatalogForm.on({
    actioncomplete: function(form, action){
        if(action.type == 'load'){
            submit.enable();
        }
    }
});

// Product Catalog Management Layout
var productCatalogLayout = new Ext.Panel({
	id: 'product-catalog-panel',
	title: '产品目录管理',
	layout: 'border',
	//height: 600,
	//width: 500,
	//autoHeight: true,
	iconCls: 'icon-be-tree',
	bodyBorder: true,
	defaults: {
		collapsible: false,
		split: true,
		animFloat: false,
		autoHide: false,
		useSplitTips: true			
	},
    waitMsgTarget: true,
	items: [productCatalogTree, productCatalogForm],
	bbar: [{
		id:'refresh',
		text: '刷新',
		handler: function() {
			var panel = productCatalogTree;
			var treeLoader = panel.getLoader();
			treeLoader.load(panel.getRootNode());
			panel.root.expand(true);
		}
	}, '-', {
		id:'add',
		text: '新建',
		handler: function() {
			productCatalogForm.setTitle('新建目录');
			productCatalogForm.getForm().reset();
		}
	}, '-', {
		id:'edit',
		text: '编辑',
		handler: function() {
			var tree = productCatalogTree;
			var sm = tree.getSelectionModel();
			var node = sm.getSelectedNode();
			if(!node) {
				alert('请选择一个目录进行编辑!');
				return;
			}
			productCatalogForm.setTitle('编辑目录 :: '+node.id);
			productCatalogForm.getForm().load({
	        	method: 'post',
	        	url:'product/catalog.jxp', 
	        	params: { action:'load', id: node.id},
        		waitMsg:'Loading ... '
			});
		}
	}, '-', {
		id:'delete',
		text: '删除',
		handler: function() {
			var panel = productCatalogTree;
			var sm = panel.getSelectionModel();
			var node = sm.getSelectedNode();
			if(!node) {
				alert('请选择一个目录进行操作!');
				return;
			} else {
				alert('Delete node: ' + node.id);
			}
		}
	}]
});
/*
productCatalogTabPanel.on('tabchange', function(panel, tab) {
	var bbar = panel.getBottomToolbar();
	var mc = bbar.items;
	if('product-catalog-tree' == tab.id) {
		mc.get('refresh').enable();
		mc.get('add').enable();
		mc.get('edit').enable();
		mc.get('delete').enable();
	} else if('product-catalog-form' == tab.id) {
		mc.get('refresh').disable();
		mc.get('add').disable();
		mc.get('edit').disable();
		mc.get('delete').disable();
	}
});
*/
// Register Panel
registerPanel(productCatalogLayout);