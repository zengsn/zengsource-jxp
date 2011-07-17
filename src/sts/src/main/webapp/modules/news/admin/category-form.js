/*
 * News editor form.
 * Copyright(c) 2008, iCanWear.com
 * icanwear@hotmail.com
 * 
 * http://iCanWear.com/license
 */

Ext.namespace('Ext.jxsite');

//=============================================================== // 
//Form Panel :: News Category Edit Form ========================= //
//=============================================================== //
Ext.jxsite.NewsCategoryEditFormPanel = function(config) {
	config = config || {};
	// Catalog Store
	var NewsCategory = Ext.data.Record.create(['id', 'name', 'parent', 'index']);
	var newsCategoryStore = new Ext.data.Store({
		url: 'news/category.jsp',
		baseParams: {
			action: 'list',
			type: 'xml'
		}, 
		reader: new Ext.data.XmlReader({
			id: 'id',
			record: 'Category',
			totalRecords: 'totalCount'
		}, NewsCategory)
	});
	// Config
	config = Ext.applyIf(config, {
		region:'center',
		id: 'newscategory-form',
	    //title:'新建目录',
		margins: '5 5 5 5',
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
	        record : 'Category',
	        success: '@success'
	    }, [
	        'id', 'name', 'index', 'parent'
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
	        store: newsCategoryStore,
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
	    }]
	});
	Ext.apply(this, config);
	Ext.jxsite.NewsCategoryEditFormPanel.superclass.constructor.apply(this, arguments);
};
Ext.extend(Ext.jxsite.NewsCategoryEditFormPanel, Ext.form.FormPanel, {
	loadForm : function(data) {
		this.getForm().load({
	    	method: 'post',
	    	url:'news/category.jsp', 
	    	params: { action:'load', id: data.categoryId}//,
			//waitMsg:'Loading ... '
		});
	},
	resetForm : function() {
		this.getForm().reset();
	},
	submitForm : function(callback) {
		this.getForm().submit({
	    	method: 'post',
	    	url:'news/category.jsp', 
        	params: {action: 'edit'},
	    	success: function() {
	    		//galleryCatalogStore.load();
	    		//galleryCatalogTreePanel.getLoader().load(galleryCatalogTreePanel.getRootNode());
	    		alert('保存成功');
	    		// Callback
	    		callback.success();
	    	}//,
	    	//waitMsg:'Saving ...'
		});
	}
});

//=============================================================== // 
//Window+Form :: Category Edit Window with Form ================= //
//=============================================================== //
Ext.jxsite.NewsCategoryEditWindow = function(config) {
	config = config || {};
	// Inner form
	this.formItem = new Ext.jxsite.NewsCategoryEditFormPanel({categoryId: this.categoryId});
	var thisFormItem = this.formItem; // for inner reference
	var thisWindow = this; // for inner reference
	// Config
	config = Ext.applyIf(config, {
	    id: 'edit-newscategory-window',
	    layout:'border',
	    width:500,
	    height:280,
	    title: '编辑分类',
		iconCls: 'icon-be-baseconfig',
	    closeAction:'hide',
	    plain: false,
	    
	    items: this.formItem,

	    buttons: [{
		    text: '重置',
		    handler: function(){
		    	var categoryId = this.formItem.getForm().getValues().id;
		    	if(categoryId=='') {
		    		thisFormItem.resetForm();
		    	} else {
		    		thisFormItem.loadForm({categoryId: categoryId});
		    	}
		    }
	    }, {
		    text: '保存',
		    disabled:false,
		    handler: function(){
	    		thisFormItem.submitForm({
	    			success: function() {
	    				thisWindow.hide();
	    			}
	    		});
		    }
	    },{
		    text: '取消',
		    disabled:false,
		    handler: function(){
	    		thisFormItem.resetForm();
	    		thisWindow.hide();
		    }
	    }]
	});
	Ext.apply(this, config);
	Ext.jxsite.NewsCategoryEditWindow.superclass.constructor.apply(this, arguments);
};
Ext.extend(Ext.jxsite.NewsCategoryEditWindow, Ext.Window, {
	loadForm : function(categoryId) {
		return this.formItem.loadForm(categoryId);
	}
});