/*
 * Product Module Configuration.
 * Copyright(c) 2008, iCanWear.com
 * icanwear@hotmail.com
 * 
 * http://iCanWear.com/license
 */
 
Ext.QuickTips.init();

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
var productCatalogTree = new Ext.tree.ColumnTree({
	el:'product-catalog-tree-ct',
	id: 'product-catalog-tree',
	//width:552,
	//autoHeight:true,
	rootVisible:false,
	autoScroll:true,
	//useArrows: true,
	//singleExpand: true,
	//lines: true,
	//collapseMode:'max',
	animCollapse: true,
	title: '产品目录树',
	iconCls: 'icon-be-baseconfig',
	
	columns:[{
	    header:'目录名',
	    width:240,
	    dataIndex:'catname'
	},  {
	    header:'位置',
	    width:64,
	    dataIndex:'index'
	},{/*
	    header:'上级目录',
	    width:120,
	    dataIndex:'parent'
	},{*/
	    header:'说明',
	    width:360,
	    dataIndex:'description'
	}],
	
	loader: new Ext.tree.TreeLoader({
	    //dataUrl:'../extjs/examples/tree/column-data.json',
	    dataUrl:'product/catalog.jxp',
	    baseParams: {action:'list', type:'json'},
	    uiProviders:{
	        'col': Ext.tree.ColumnNodeUI
	    }
	}),
	
	root: new Ext.tree.AsyncTreeNode({
	    text:'产品目录',
	    expanded: 'true'
	})
});

productCatalogTree.root.expand(true);

// turn on validation errors beside the field globally
Ext.form.Field.prototype.msgTarget = 'side';
    
// Product Catalog Edit Form
var productCatalogForm = new Ext.FormPanel({
	id: 'product-catalog-form',
    //frame: true,
    title:'新建目录',
    labelAlign: 'center',
    labelWidth: 85,
    //width:340,
	iconCls: 'icon-be-baseconfig',
    bodyStyle:'padding:5px',
    //defaultType: 'textfield',
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
    }, {
        xtype:'textfield',
        fieldLabel: '目录名称',
        allowBlank: false,
        emptyText:'',
        blankText: '名称不能为空!', 
        width: 320,
        name: 'name'
    }, new Ext.ux.form.Spinner({
		fieldLabel: '目录位置',
		name: 'index',
        //width: 320,
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

// Product Catalog Management TabPanel
var productCatalogTabPanel = new Ext.TabPanel({
	id: 'product-catalog-panel',
	plain: true,  //remove the header border
	activeItem: 0,
	defaults: {bodyStyle: 'padding:0px'},
	items:[productCatalogTree, productCatalogForm],
	bbar: [{
		id:'refresh',
		text: '刷新',
		handler: function() {
			var panel = productCatalogTabPanel.getActiveTab();
			if(panel.id=='product-catalog-tree') {
				var treeLoader = panel.getLoader();
				treeLoader.load(panel.getRootNode());
				panel.root.expand(true);
			} else {
			}
		}
	}, '-', {
		id:'add',
		text: '新建',
		handler: function() {
			var panel = productCatalogTabPanel.getActiveTab();
			if(panel.id=='product-catalog-tree') {
				productCatalogTabPanel.activate('product-catalog-form');
				productCatalogForm.setTitle('新建目录');
				productCatalogForm.getForm().reset();
			} else {
			}
		}
	}, '-', {
		id:'edit',
		text: '编辑',
		handler: function() {
			var panel = productCatalogTabPanel.getActiveTab();
			if(panel.id=='product-catalog-tree') {
				var sm = panel.getSelectionModel();
				var node = sm.getSelectedNode();
				if(!node) {
					alert('请选择一个目录进行编辑!');
					return;
				}
				productCatalogTabPanel.activate('product-catalog-form');
				productCatalogForm.setTitle('修改目录 :: '+node.id);
				productCatalogForm.getForm().load({
		        	method: 'post',
		        	url:'product/catalog.jxp', 
		        	params: { action:'load', id: node.id},
	        		waitMsg:'Loading ... '
				});
			} else {
			}
		}
	}, '-', {
		id:'delete',
		text: '删除',
		handler: function() {
			var panel = productCatalogTabPanel.getActiveTab();
			if(panel.id=='product-catalog-tree') {
				var sm = panel.getSelectionModel();
				var node = sm.getSelectedNode();
				if(!node) {
					alert('请选择一个目录进行操作!');
					return;
				}
				alert('Delete node: ' + node.id);
			} else {
			}
		}
	}]
});

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

// Register Panel
registerPanel(productCatalogTabPanel);