// user.js

/*
 * User management.
 * Copyright(c) 2008, iCanWear.com
 * icanwear@hotmail.com
 * 
 * http://iCanWear.com/license
 */

Ext.namespace('JXP.ui');

// ///////////////////////////////////////////////////////// //
// UserMgmtPanel: Border Layout with TreeGrid and FormPanel. //
// ///////////////////////////////////////////////////////// //
JXP.ui.UserMgmtPanel = Ext.extend(Ext.Panel, {
	// default config
	
	initComponent : function() {
		var thisUserMgmtPanel = this;
		// hard coded - cannot be changed from outside
		var User = Ext.data.Record.create(['id', 'username', 'lastLoginTime', 'status']);
		var userListStore = new Ext.data.Store({
		  url: './system/user.jxp',
		  reader: new Ext.data.XmlReader({
				id: 'id',
				record: 'user',
				totalRecords: 'totalCount'
			}, User),			
			sortInfo: {
				field: "lastLoginTime", direction: "ASC"
			},			
			baseParams: {action: 'list', limit: 25}
		});
		var searchRenderer = function(val) {
			var q = userListStore.baseParams.q;
			if (q) {
				val = val.replace(q, '<span style="color:blue;">'+q+'</span>');
			}
			return val;
		};
		var statusRenderer = function(status) {
			return '' + status + '';
		};
		var usersSM = new Ext.grid.CheckboxSelectionModel();
		usersSM.on('selectionchange', function(sm) {
			var count = sm.getCount();
			if (count == 1) { // select one
				thisUserMgmtPanel.editOneUser(sm.getSelected());
			} else if (count > 1) { // select multiple
				thisUserMgmtPanel.editMultipleUsers(sm.getSelections());
			} else { // select none
			}
		});
	    var westGrid = new Ext.grid.GridPanel({
	        title: '用户列表',
	        region: 'west',
	        split: true,
            width: 400,
            minSize: 360,
            maxSize: 450,
            collapsible: true,
            margins: '0 0 0 0',
	        enableDD: true,
	        store: userListStore,
	        columns: [
	            usersSM,
	            {id:'username',header: "用户名", width: 160, sortable: true, renderer: searchRenderer, dataIndex: 'username'},
	            {header: "状态", width: 75, sortable: true, renderer: statusRenderer, dataIndex: 'status'},
	            {header: "上次登录", width: 85, sortable: true, renderer: Ext.util.Format.dateRenderer('m/d/Y'), dataIndex: 'lastLoginTime'}
	        ],
	        sm: usersSM,
	        stripeRows: true,
	        autoExpandColumn: 'username',

	        //plugins: new Ext.ux.PanelResizer({
	        //    minHeight: 100
	        //}),
	        
	        listeners: {
		    	'rowclick': function(grid, rowIndex, evn) {
			    	//alert('click rowclick');
			    }
		    },
		    
		    tbar: [
				'搜索: ', ' ',
                new Ext.app.SearchField({
	                width:120,
					store: userListStore,
					paramName: 'q'
	            }), '->',
	            new Ext.form.Checkbox({
	            	boxLabel: '仅激活',
	            	checked: false,
	            	name: 'disabled',
	            	handler: function(chb, checked) {
		            	alert('Not yet ... ');
		            }
	            })
            ],

	        bbar: new Z.ux.PagingToolbarCN({
	            pageSize: 10,
	            displayInfo: true,
	            store: userListStore,

	            plugins: new Ext.ux.ProgressBarPager()
	        })
	    });
		var centerFormPanel = new Ext.form.FormPanel({
	        title: '创建用户',
	        region: 'center',
	        labelWidth: 75, 
	        waitMsgTarget: true,
	        buttonAlign: 'center',
	        bodyStyle:'padding:15px 15px 0',
	        defaults: {width: 230},
	        defaultType: 'textfield',
	        
	        reader : new Ext.data.XmlReader({
	            record : 'user',
	            success: '@success'
	        }, [
	            'id', 'username', 'enabled', 'email'
	        ]),

	        errorReader: new Ext.form.XmlErrorReader(),
	        
	        listeners: {
	        	'actioncomplete': function(form, action){
	        		//alert(action.type);
		            if(action.type == 'load'){
		            	// Set enabled
		            	var radioGrpField = form.findField('u-enabled');
		            	var enabled = action.result.data.enabled;
		            	var trueId = 'enabled-rd-' + enabled;
		            	radioGrpField.setValue([false, false]);
		            	radioGrpField.setValue(trueId, true);

	        			centerFormPanel.fireEvent('beforeediting', centerFormPanel);
		            }
		        },
		        /** before editing, prepare the buttons' state. */
		        'beforeediting': thisUserMgmtPanel.onBeforeEditing,
	        },

	        items: [{
	        	id: 'u-id',
	        	name: 'id',
	        	xtype: 'hidden'
	        }, {
	        	id: 'u-username',
                name: 'username',
                fieldLabel: '用户名',
                allowBlank: false,
	        	listeners: {
		        	'blur': function(field) {
			        	if (centerFormPanel.getForm().isDirty()) {Ext.getCmp('btn-u-save').enable();}
			        	else {Ext.getCmp('btn-u-save').disnable();}
			        }
		        }
            }, {
            	id: 'u-email',
                name: 'email',
                fieldLabel: 'Email',
                vtype:'email',
	        	listeners: {
		        	'blur': function(field) {
			        	if (centerFormPanel.getForm().isDirty()) {Ext.getCmp('btn-u-save').enable();}
			        	else {Ext.getCmp('btn-u-save').disnable();}
			        }
		        }
            }, {
            	id: 'u-enabled',
                xtype: 'radiogroup',
                fieldLabel: '用户状态',
                itemCls: 'x-check-group-alt',
                //columns: 1,
                items: [
                    {id: 'enabled-rd-1', boxLabel: '启用', name: 'enabled', inputValue: 1, checked: true},
                    {id: 'enabled-rd-2', boxLabel: '关闭', name: 'enabled', inputValue: 2}
                ],
	        	listeners: {
		        	'blur': function(field) {
			        	if (centerFormPanel.getForm().isDirty()) {Ext.getCmp('btn-u-save').enable();}
			        	else {Ext.getCmp('btn-u-save').disnable();}
			        }
		        }
            }],
	        buttons: [{
	            text: '创建',
	            id: 'btn-u-add',
	            disabled: true,
	            handler: function(btn) {
        			Ext.getCmp('btn-u-add').disable();
	        		Ext.getCmp('btn-u-save').enable();
	        		thisUserMgmtPanel.clearForm('创建新用户');
	        	}	            
	        }, {
	            text: '保存',
	            id: 'btn-u-save',
	            disabled: true,
	            handler: function(btn) {
	        		centerFormPanel.getForm().submit({
	        			url:'./system/user.jxp',
	        			params: {action: 'save'},
	        			waitMsg:'请稍候...',
	        			success: function(form, action) {
        			       Ext.Msg.alert('操作成功', '用户信息已经保存!');
        			       Ext.getCmp('btn-u-save').disable();
        			       //Ext.getCmp('btn-u-delete').enable();
        			       thisUserMgmtPanel.userListStore.load({params:{start:0, limit:25}});
        			    },
        			    failure: function(form, action) {
        			        switch (action.failureType) {
        			            case Ext.form.Action.CLIENT_INVALID:
        			                Ext.Msg.alert('Failure', 'Form fields may not be submitted with invalid values');
        			                break;
        			            case Ext.form.Action.CONNECT_FAILURE:
        			                Ext.Msg.alert('Failure', 'Ajax communication failed');
        			                break;
        			            case Ext.form.Action.SERVER_INVALID:
        			               Ext.Msg.alert('Failure', action.result.msg);
        			       }
        			    }
	        		});
	        	}
	        }, {
	        	text: '清空',
	            id: 'btn-u-clear',
	            disabled: true,
	            handler: function(btn) {
	        		thisUserMgmtPanel.clearForm();
	        	}
	        }]
		});
		var config = {
			id: 'system-user-panel',
			layout: 'border',
			border: false,
			items: [westGrid, centerFormPanel]
		};
		
		// save store
		this.userListStore = userListStore;
		this.userGrid = westGrid;
		this.userForm = centerFormPanel;
		
		// apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        
        // call parent
        JXP.ui.UserMgmtPanel.superclass.initComponent.apply(this, arguments);
        
	},//END-initComponent
	
	onRender : function() {
		JXP.ui.UserMgmtPanel.superclass.onRender.apply(this, arguments);
	},// END-onRender()
	
	editOneUser: function(userRecord) {
		var form = this.userForm.getForm();
		form.load({
			url:'./system/user.jxp', 
			params: {action: 'load', id: userRecord.data.id},
			waitMsg:'Loading...'
		});
	},// END-editOneUser
	
	editMultipleUsers: function(userRecordArray) {
		var form = this.userForm.getForm();
    	var newTitle = '';
    	var ids = ''
    	for (var i=0; i<userRecordArray.length; i++) {
    		ids += userRecordArray[i].data.id + ',';
    		newTitle += userRecordArray[i].data.username + ',';
    	}
    	this.clearForm('编辑: ' + newTitle);
    	form.findField('u-id').setValue(ids);
    	var radioGrpField = form.findField('u-enabled');
    	radioGrpField.setValue([false, false]);

    	this.userForm.fireEvent('beforeediting', this.userForm, true);
	},// END-editMultipleUsers
	clearForm: function(newTitle) {
		this.userForm.getForm().setValues({
			'u-id': '',
			'u-username': '',
			'u-email': '',
			'u-enabled': false
		});
		if (newTitle) {
			this.userForm.setTitle(newTitle);
		}
	},
	onBeforeEditing: function(form, isMultiple) {
		// some fields are not allowed to edit 
		if (isMultiple) {
			form.getForm().findField('u-username').disable();
	    	form.getForm().findField('u-email').disable();
		} else {
        	// Set Title
        	var textField = form.getForm().findField('u-username');
        	form.setTitle('编辑: ' + textField.getValue());
        	textField.disable(); // not allow to edit
        	form.getForm().findField('u-email').enable();
        	form.getForm().findField('u-enabled').enable();
		}
		// enable or disable buttons
		Ext.getCmp('btn-u-add').enable();
		Ext.getCmp('btn-u-save').disable();
	}
});

Ext.reg('usermgmtpanel', JXP.ui.UserMgmtPanel);

// Initialize Panel
var userMgmtPanel = new JXP.ui.UserMgmtPanel();
userMgmtPanel.on('show', function(panel) {
	userMgmtPanel.userListStore.load({params:{start:0, limit:25}});
});

// Register Panel
registerPanel(userMgmtPanel);