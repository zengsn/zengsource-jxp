Ext.namespace('Ext.app');

// Custom Product Form
Ext.app.ProductFormPanel = function(config, product) {
	// Avoid null
	config = config || {};
	var pid = (new Date()).format('YmdHisu');
	if(product && product.id) pid = product.id; 
	else if(config.id) pid = config.id;
		
	// Create or Edit
	var isEdit = !(!product);
	if(isEdit && product.id) this.pid = product.id;
	
	// Product Form
	this.editForm = new Ext.form.FormPanel({
		id: 'product-form-'+pid,
		title: '产品编辑',
		iconCls: 'icon-be-product',
		
		//frame:true,
		region:'center',
		margins: '5 0 5 5',
		bodyStyle: 'padding:15px',
		waitMsgTarget: true,
		
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
        }, [ 'name', 'catalog', 'number', 'material', 'shipping', 'usage', 'description' ]),
        
        // reusable eror reader class defined at the end of this file
        errorReader: new Ext.form.XmlErrorReader(),
        
        listeners: {
        	'actioncomplete' : function(form, action){
	            if(action.type == 'load'){
	                alert('Load ...');
	            }
	        } 
        },

        items: [{
        	inputType: 'hidden',
            name: 'id'
        },{
            fieldLabel: '产品名称',
            name: 'name',
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
	        selectOnFocus:true
        }, new Ext.ux.form.Spinner({
			fieldLabel: '产品数量',
			name: 'totalCount',
			strategy: new Ext.ux.form.Spinner.NumberStrategy({
				minValue:'1'//, maxValue:'130'
			})
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
	        emptyText:'选择分类...',
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
	  		xtype: 'htmleditor',
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
			text: '保存 (Enter)'
		},{
			text: '取消 (Esc)'
		}]
    });
    
    // File Grid
    this.fileStore = new Ext.data.Store({
		// load using HTTP
		url: '../xdb/files.xml',
		
		// the return will be XML, so lets set up a reader
		reader: new Ext.data.XmlReader({
			id: 'id',
			record: 'file',
			totalRecords: 'totalCount'
		}, ['id', 'name', 'filename', 'filesize']),
		baseParams: {action: 'list', pid: 'abc'},
		listeners: {
			//'load' : function(store, records, options) {}
		}
	});
	
	this.fileGrid = new Ext.grid.GridPanel({
		id: 'product-file-grid-'+pid,
		title: '上传附件',
		iconCls: 'icon-be-product',
        
		//frame: true,
		region:'east',
		margins: '5 5 5 0',
		cmargins: '5 5 0 0',
		width: 320,
		minSize: 240,
		maxSize: 360,
		
        viewConfig: {
            forceFit:true
        },
		
        store: this.fileStore,
        
        columns: [
            //{header: "Name", width: 120, dataIndex: 'name', sortable: true},
            {header: "Filename", width: 180, dataIndex: 'filename', sortable: true},
            {header: "File Size", width: 115, dataIndex: 'filesize', sortable: true}
        ],
        bbar: [{
        	id: 'btn-upload-'+pid,
        	text: '上传文件',
        	disabled: !isEdit,
        	handler: function() {
        		if(!this.win) {
		            this.win = new Ext.Window({
		                id:'product-upload-win-'+pid,
		                layout:'fit',
		                width:500,
		                height:100,
		                title: '上传附件',
		                closeAction:'hide',
		                plain: true,
		                
		                items: new Ext.form.FormPanel({
					        labelWidth: 75, 
					        //frame:true,
					        //title: 'Simple Form',
					        bodyStyle:'padding:5px 5px 0',
					        width: 350,
					        defaults: {width: 230},
					        defaultType: 'textfield',
					
					        items: [{
				                fieldLabel: '选择文件',
				                name: 'file',
				                inputType: 'file',
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
				            		Ext.getCmp('product-upload-win-'+pid).hide();
				            	}
				            }],
		
			                buttons: [{
			                    text:'上传 (Enter)'
			                },{
			                    text: '取消 (Esc)',
			                    handler: function(){
			                        Ext.getCmp('product-upload-win-'+pid).hide();
			                    }
			                }]
		                })
		            });
        			
        		}
        		this.win.show('btn-upload-'+pid);
        	}
        }]
    });
	
	// Config
	config = Ext.applyIf(config, {
		id: 'product-form-panel-'+pid,
		title: isEdit?'编辑::'+product.name:'新产品::'+pid,
		iconCls: 'icon-be-product',
		layout: 'border',
		closable: true,
		//height: 600,
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
		items: [this.editForm, this.fileGrid]
	});
	Ext.apply(this, config);
	
    // Constructor
    Ext.app.ProductFormPanel.superclass.constructor.apply(this, arguments);
};

Ext.extend(Ext.app.ProductFormPanel, Ext.Panel, {
	getFileStore : function() {
		return this.fileStore;
	},
	
	loadForm : function() {
		this.editForm.getForm().load({
			url:'../xdb/product.xml',
			waitMsg:'Loading'
		});
	}
});