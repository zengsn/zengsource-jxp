/*
 * Product editor form.
 * Copyright(c) 2008, iCanWear.com
 * icanwear@hotmail.com
 * 
 * http://iCanWear.com/license
 */

Ext.namespace('Ext.jxsite');

//=============================================================== // 
//Form Panel :: Product Edit Form =============================== //
//=============================================================== //
Ext.jxsite.ProductEditFormPanel = function(config) {
	config = config || {};
	if (config.productId) {
		this.productId = config.productId;
	} else {
		this.productId = (new Date()).format('YmdHisu');
	}
	var productName = '创建新产品';
	if (config.productName) {
		productName = config.productName;
	} 
	var pid = this.productId;
	var formPanelId = 'product-form-'+pid;
	var thisPanel = this; // using in inner function
	// Catalog Store
	var ProductCatalog = Ext.data.Record.create(['id', 'name', 'parent', 'description']);
	var productCatalogStore = new Ext.data.Store({
		url: 'product/catalog.jxp',
		baseParams: {
			action: 'list',
			type: 'xml'
		}, 
		reader: new Ext.data.XmlReader({
			id: 'id',
			record: 'Catalog',
			totalRecords: 'totalCount'
		}, ProductCatalog)
	});
	// Config
	config = Ext.applyIf(config, {
		id: formPanelId,
		//id: 'product-editor-panel~',
		title: productName,
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
            record : 'Product',
            success: '@success'
        }, [ 'id', 'name', 'catalog', 'totalCount', 'price', 'currency', 'material', 'shipping', 'usage', 'description' ]),
        
        // reusable eror reader class defined at the end of this file
        errorReader: new Ext.form.XmlErrorReader(),
        
        listeners: {
        	'actioncomplete' : function(form, action){
	            if(action.type == 'load'){
	                alert('Load ... JXS_UI_ProductFormPanel.js - 53');
	            }
	        } 
        },

        items: [{
        	inputType: 'hidden',
            name: 'id'
        },{
            fieldLabel: '产品名称',
            name: 'name',
            listerners: {
            	'change' : function(field, newValue, oldValue) {
        			thisPanel.setTitle(newValue);
            	}
            },
            allowBlank:false
        },{
	        xtype:'combo',
	        fieldLabel: '产品分类',
	        hiddenName:'catalog',
	        valueField:'id',
	        displayField:'name',
	        typeAhead: true,
	        //mode: 'local',
	        triggerAction: 'all',
	        store: productCatalogStore,
	        emptyText:'选择分类...',
	        selectOnFocus:true,
            allowBlank:true
        }, new Ext.ux.form.Spinner({
			fieldLabel: '产品数量',
			name: 'totalCount',
			strategy: new Ext.ux.form.Spinner.NumberStrategy({
				minValue:'1'//, maxValue:'130'
			}),
			value: '100'
	  	}), {
	        xtype:'combo',
	        fieldLabel: '结算货币',
	        hiddenName:'currency',
	        typeAhead: true,
	        mode: 'local',
	        valueField:'text',
	        displayField:'name',
	        triggerAction: 'all',
	        store: new Ext.data.SimpleStore({
		        fields: ['text', 'name'],
		        data : [['￥', '(￥) 人民币'], ['$', '($) 美元']]
		    }),
		    value: '￥',
		    //disabled: true,
	        emptyText:'选择货币...',
	        selectOnFocus:true
	  	}, {
            fieldLabel: '产品价格',
            name: 'price',
            allowBlank:false
        }, {
	  		//xtype: 'textarea',
            //height: 64,
            fieldLabel: '产品材料',
            name: 'material'
        }, {
	        xtype:'combo',
	        fieldLabel: '配送方式',
	        hiddenName:'shipping',
	        typeAhead: true,
	        mode: 'local',
	        valueField:'text',
	        displayField:'name',
	        triggerAction: 'all',
	        store: new Ext.data.SimpleStore({
		        fields: ['text', 'name'],
		        data : [['express', '快递(推荐)'], ['manual', '送货上门'], ['post', '邮政包裹']]
		    }),
		    //value: '￥',
		    //disabled: true,
	        emptyText:'选择配送...',
	        selectOnFocus:true
	  	}, {
            fieldLabel: '产品图片',
            inputType: 'file',
            name: 'imageSingle'
        }, {
            fieldLabel: '图片 - 前',
            inputType: 'file',
            name: 'imageFront'
        }, {
            fieldLabel: '图片 - 后',
            inputType: 'file',
            name: 'imageBack'
        }, {
            fieldLabel: '图片 - 左',
            inputType: 'file',
            name: 'imageLeft'
        }, {
            fieldLabel: '图片 - 右',
            inputType: 'file',
            name: 'imageRight'
        }, {
            fieldLabel: '图片 - 上',
            inputType: 'file',
            name: 'imageAbove'
        }, {
            fieldLabel: '图片 - 下',
            inputType: 'file',
            name: 'imageUnder'
        }, {
            fieldLabel: '附件',
            inputType: 'file',
            name: 'attachment'
        }, {
	  		xtype: 'textarea',
            width: 360,
            height: 200,
            fieldLabel: '使用说明',
            enableFont: false,
            enableLists: false,
            name: 'usage'
        }, {
	  		xtype: 'textarea',
            width: 360,
            height: 64,
            fieldLabel: '备注',
            name: 'description'
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
			id: 'btn-save-'+pid,
			text: '保存 (Enter)',
	        handler: function(){
	        	var form = thisPanel;
	        	if(form.getForm().isValid()) {	
	        		form.getForm().waitMsgTarget='product-form-'+pid;    	
		            form.getForm().submit({
		            	url:'product/edit.jxp', 
		            	params: {action: 'save'},
		            	success: function(form, action) {
		            		//alert(form.isValid());
		            		Ext.getCmp('btn-save-'+pid).disable();
		            		Ext.MessageBox.show({
		            			title:'操作成功:', 
		            			msg: '产品已成功保存.', 
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
	Ext.jxsite.ProductEditFormPanel.superclass.constructor.apply(this, arguments);
};
Ext.extend(Ext.jxsite.ProductEditFormPanel, Ext.form.FormPanel, {
	loadForm : function(record) {
		this.getForm().load({
	    	method: 'post',
	    	url:'product/manage.jxp', 
	    	params: { action:'load', id: record.data.id},
			waitMsg:'Loading ... '
		});
	}
});