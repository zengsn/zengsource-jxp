
Ext.namespace('JXS.ui');

// JXSite UI: Product Form Panel
JXS.ui.ProductFormPanel = function(config, product) {
	// Avoid null
	config = config || {};
	var pid = (new Date()).format('YmdHisu');
	if(product && product.id) pid = product.id; 
	else if(config.id) pid = config.id;
		
	// Create or Edit
	var isEdit = !(!product);
	if(isEdit && product.id) this.pid = product.id;
	
	// Config
	config = Ext.applyIf(config, {
		id: 'product-form-'+pid,
		title: '产品编辑',
		iconCls: 'icon-be-product',
		
		//frame:true,
		region:'center',
		margins: '5 5 5 5',
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
            fieldLabel: '产品图片',
            inputType: 'file',
            name: 'image'
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
            fieldLabel: '图片 - 后',
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
	
	Ext.apply(this, config);
	
    // Constructor
    Ext.app.ProductFormPanel.superclass.constructor.apply(this, arguments);
	
};

Ext.extend(JXS.ui.ProductFormPanel, Ext.form.FormPanel, {
});
