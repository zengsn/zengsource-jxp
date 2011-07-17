/*
 * News entry editor form.
 * Copyright(c) 2008, iCanWear.com
 * icanwear@hotmail.com
 * 
 * http://iCanWear.com/license
 */

Ext.namespace('Ext.jxsite');

//=============================================================== // 
//Form Panel :: News Entry Edit Form ============================ //
//=============================================================== //
Ext.jxsite.NewsEntryEditFormPanel = function(config) {
	config = config || {};
	if (config.entryId) {
		this.entryId = config.entryId;
	} else {
		this.entryId = (new Date()).format('YmdHisu');
	}
	var entryName = '新闻';
	if (config.entryName) {
		entryName = config.entryName;
	} 
	var eid = this.entryId;
	var formPanelId = 'entry-form-'+eid;
	var thisPanel = this; // using in inner function
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
		id: formPanelId,
		//id: 'product-editor-panel~',
		title: entryName,
		iconCls: 'icon-be-product',
		
		//frame:true,
		region:'center',
		margins: '5 5 5 5',
		bodyStyle: 'padding:15px',
		waitMsgTarget: true,
		autoScroll: true,
		
		fileUpload: true,
		labelWidth: 75,
		labelPad: 20,
		layoutConfig: {
			//labelSeparator: ''
		},
		defaults: {
			width: 230,
			msgTarget: 'side'
		},
		defaultType: 'textfield',
		
		// configure how to read the XML Data
        reader : new Ext.data.XmlReader({
            record : 'Entry',
            success: '@success'
        }, [ 'id', 'title', 'content', 'writer', 'status', 'category', 'viewCount' ]),
        
        // reusable eror reader class defined at the end of this file
        errorReader: new Ext.form.XmlErrorReader(),
        
        listeners: {
        	'actioncomplete' : function(form, action){
	            if(action.type == 'load'){
	                alert('Load ... JXS_UI_ProductFormPanel.js - 53');
	                newsCategoryStore.load();
	            }
	        } 
        },

        items: [{
        	inputType: 'hidden',
            name: 'id'
        },{
            fieldLabel: '标题',
            name: 'title',
            listerners: {
            	'change' : function(field, newValue, oldValue) {
        			thisPanel.setTitle(newValue);
            	}
            },
            allowBlank:false
        },{
	        xtype:'combo',
	        fieldLabel: '分类',
	        hiddenName:'category',
	        valueField:'id',
	        displayField:'name',
	        typeAhead: true,
	        //mode: 'local',
	        triggerAction: 'all',
	        store: newsCategoryStore,
	        emptyText:'选择分类...',
	        selectOnFocus:true,
            allowBlank:true
        }, {
            fieldLabel: '作者',
            name: 'writer'
        }, {
	  		//xtype: 'textarea',
        	xtype: 'htmleditor',
            width: 600,
            height: 300,
            fieldLabel: '内容',
            name: 'content',
            allowBlank:false
        }],
				            
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
        }],
        
		buttons: [{
			id: 'btn-save-'+eid,
			text: '保存 (Enter)',
	        handler: function(){
	        	var form = thisPanel;
	        	if(form.getForm().isValid()) {	
	        		form.getForm().waitMsgTarget='entry-form-'+eid;    	
		            form.getForm().submit({
		            	url:'news/edit.jxp', 
		            	params: {action: 'save'},
		            	success: function(form, action) {
		            		//alert(form.isValid());
		            		Ext.getCmp('btn-save-'+eid).disable();
		            		Ext.MessageBox.show({
		            			title:'操作成功:', 
		            			msg: '新闻已成功保存.', 
		            			fn: function() {
									//var tabPanel = Ext.getCmp('product-editor-panel');
									//tabPanel.remove(formPanelId);
									//productStore.load({params:{start:0,limit:25}});
									//form.reset();
								},
								icon: Ext.MessageBox.INFO,
								buttons: Ext.Msg.OK
							});
		            	},
		            	failure: function(form, action) {
		            		Ext.MessageBox.show({
								title:'操作失败',
								msg: '保存失败,请检查信息.',
								buttons: Ext.MessageBox.OK,
								fn: function() {
								},
								icon: Ext.MessageBox.ERROR
							});
		            	},
		            	waitTitle: '请稍候!',
		            	waitMsg:'正在保存...'
		            });
		        }
	        }
		},{
			text: '取消 (Esc)',
			handler: function(){
			}
		}]
	});
	Ext.apply(this, config);
	Ext.jxsite.NewsEntryEditFormPanel.superclass.constructor.apply(this, arguments);
};
Ext.extend(Ext.jxsite.NewsEntryEditFormPanel, Ext.form.FormPanel, {
	loadForm : function(record) {
		this.getForm().load({
	    	method: 'post',
	    	url:'news/manage.jsp', 
	    	params: { action:'load', id: record.data.id},
			waitMsg:'Loading ... '
		});
	}
});