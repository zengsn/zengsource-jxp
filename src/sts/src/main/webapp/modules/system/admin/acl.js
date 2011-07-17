// acl.js

/*
 * ACL Configuration.
 * Copyright(c) 2008, iCanWear.com
 * icanwear@hotmail.com
 * 
 * http://iCanWear.com/license
 */

Ext.namespace('Z.JXP.system', 'Z.JXP.system.acl');

/**
 * System -> ACL -> Shared Objects
 */
Z.JXP.system.acl.Shared = {};
Z.JXP.system.acl.Shared.Role = Ext.data.Record.create(['id', 'name', 'description', 'isWrapper', 'users', 'permissons', 'updatedTime']);
Z.JXP.system.acl.Shared.rolesStore = new Ext.data.Store({
	url: './system/acl.jxp',
	//url: '../modules/system/admin/acl-roles.xml',
	reader: new Ext.data.XmlReader({
		id: 'id',
		record: 'role',
		totalRecords: 'totalCount'
	}, Z.JXP.system.acl.Shared.Role),			
	sortInfo: {
		field: "name", direction: "ASC"
	},			
	baseParams: {action: 'listRoles', limit: 25}
});
Z.JXP.system.acl.Shared.AclObject = Ext.data.Record.create(['id', 'name', 'description', 'class', 'owner', 'createdTime']);
Z.JXP.system.acl.Shared.objectsStore = new Ext.data.Store({
	url: './system/acl.jxp',
	//url: '../modules/system/admin/acl-objects.xml',
	reader: new Ext.data.XmlReader({
		id: 'id',
		record: 'object',
		totalRecords: 'totalCount'
	}, Z.JXP.system.acl.Shared.AclObject),			
	sortInfo: {
		field: "name", direction: "ASC"
	},			
	baseParams: {action: 'listObjects', limit: 25}
});
Z.JXP.system.acl.Shared.User = Ext.data.Record.create(['id', 'username', 'status', 'lastLoginTime']);;
Z.JXP.system.acl.Shared.usersStore = new Ext.data.Store({
	url: './system/user.jxp',
	baseParams: {action: 'list', limit: 25},
	reader: new Ext.data.XmlReader({
		id: 'id',
		record: 'user',
		totalRecords: 'totalCount'
	}, Z.JXP.system.acl.Shared.User),			
	sortInfo: {
		field: "lastLoginTime", direction: "ASC"
	}
});
/**
 * System -> ACL -> Role Management Tab -> UsersOfRoleGrid
 */
Z.JXP.system.acl.UsersOfRoleGrid = Ext.extend(Ext.grid.GridPanel, {
	initComponent : function() {
		var thisGrid = this;
		var UserOfRole = Z.JXP.system.acl.Shared.User;
		var store = new Ext.data.Store({
			url: './system/acl.jxp',
			baseParams: {action: 'getUsersOfRole', limit: 25},
			reader: new Ext.data.XmlReader({
				id: 'id',
				record: 'user',
				totalRecords: 'totalCount'
			}, UserOfRole),			
			sortInfo: {
				field: "lastLoginTime", direction: "ASC"
			}
		});
		var rowEditor = new Z.ux.grid.RowEditorCN();
		rowEditor.on('afteredit', function(rowEditor, object, record, rowIndex) {
			if (!object.id) { // Name not changed
			}
			if (!object.username) { // Name not changed
			}
			if (!record.data.id && !record.data.username) { // both empty
				setTimeout(function() {
					rowEditor.startEditing(rowIndex, 1);
					rowEditor.doFocus(0);
					rowEditor.showTooltip('请输入用户ID或用户名!');
				}, 500);
				return;				
			}
			// Send to server
			Ext.Ajax.request({
				url: './system/acl.jxp',
				params: { 
					action: 'addUserToRole',
					id: store.baseParams.id,
					uid: record.data.id,
					username: record.data.username
				},
				success: function(response, opts) {
					var obj = Ext.decode(response.responseText);
					console.dir(obj);
					Z.ux.Messager.alert('成功', '数据已经成功保存!');
					store.load({params:{start:0, limit:25}}); 
				},
				failure: function(response, opts) {
					console.log('server-side failure with status code ' + response.status);
					Z.ux.Messager.alert('错误', 'server-side failure with status code ' + response.status);
				}
			});
		});
		var sm = new Ext.grid.CheckboxSelectionModel();
		var config = {
			id: 'grid-acl-role-users',
	        //frame:true,
	        store: store,
	        columnLines: true,
	        margins: '0 0 0 0',
	        plugins: [ rowEditor ],
	        cm: new Ext.grid.ColumnModel({
	            defaults: {
	                width: 120,
	                sortable: true
	            },
	            columns: [
	                sm,
	                {
	                    id: 'id',
	                    header: '用户ID',
	                    dataIndex: 'id',
	                    width: 160,
	                    sortable: true,
	                    editor: {
	                        xtype: 'textfield',
	                        allowBlank: true
	                    }
	                },
	                {
	                	id: 'username',
	                    header: '用户名',
	                    dataIndex: 'username',
	                    width: 160,
	                    sortable: true,
	                    renderer: function(val, metadata, record, rowIndex, colIndex, store) {
	                		return record.data.username;
	                	},
	                    editor: {
	                		id: 'combo-username',
                        	xtype: 'combo',
	                        store: Z.JXP.system.acl.Shared.usersStore,
	                        hiddenName: 'id',
	                        valueField: 'id',
	                        displayField:'username',
	                        typeAhead: true,
	                        //mode: 'local',
	                        queryParam: 'q',
	                        hideTrigger:true,
	                        triggerAction: 'all',
	                        emptyText:'选择用户...',
	                        selectOnFocus:true,
	                        allowBlank: true,
	                        tpl: new Ext.XTemplate(
	                        	'<tpl for="."><div class="user-item">',
	                                '<h3>{username}::{id}<span>{added}</span></h3>',
	                            '</div></tpl>'
	                        ),
	                        itemSelector: 'div.user-item'//,
	                        //onSelect: function(combo, record, index) {
	                			//Z.ux.Messager.alert('INFO', rowEditor.rowIndex);
	                		//}
		                }
	                },
	                {
	                    header: '当前状态',
	                    dataIndex: 'status',
	                    width: 120,
	                    sortable: true
	                },
	                {
	                	id: 'lastLoginTime',
	                    header: '最近登录',
	                    dataIndex: 'lastLoginTime',
	                    width: 120,
	                    sortable: true
	                }
	            ]
	        }),
	        autoExpandColumn: 'lastLoginTime',
	        sm: sm,
	        
	        bbar: new Z.ux.PagingToolbarCN({
	            pageSize: 25,
	            displayInfo: true,
	            store: store,

	            plugins: new Ext.ux.ProgressBarPager(),
	            
	            items: ['-', {
	            	tooltip: '增加',
	            	icon: JXP_WEB_CONTEXT + '/resources/icons/silk/gif/add.gif',
	            	handler: function() {
		                var u = new UserOfRole({
		                    username: '',
		                    status: 1
		                });
		                rowEditor.stopEditing();
		                store.insert(0, u);
		                thisGrid.getView().refresh();
		                thisGrid.getSelectionModel().selectRow(0);
		                rowEditor.startEditing(0);
		            }
	            }, '-', {
	            	tooltip: '删除',
	            	icon: JXP_WEB_CONTEXT + '/resources/icons/silk/gif/delete.gif',
	            	handler: function() {
	            		rowEditor.stopEditing();
		                var selections = sm.getSelections();
		                if (sm.getCount() > 0) {
		                	Ext.MessageBox.confirm('操作确认', '确认删除所选记录?', function(btn) {
		                		//alert(btn);
		                		if (btn = 'yes') {
		    		                var idArray = new Array();
		    		                for(var i = 0, r; r = selections[i]; i++){
		    		                	idArray[i] = r.data.id;
		    		                	store.remove(r);
		    		                }
		    		    			// Send to server
		    		    			Ext.Ajax.request({
		    		    				url: './system/acl.jxp',
		    		    				params: { 
		    		    					action: 'removeUserFromRole',
		    		    					id: idArray.toString()
		    		    				},
		    		    				success: function(response, opts) {
		    		    					var obj = Ext.decode(response.responseText);
		    		    					console.dir(obj);
		    		    					//ZMessager.alert('成功', obj);
		    		    				},
		    		    				failure: function(response, opts) {
		    		    					console.log('server-side failure with status code ' + response.status);
		    		    					//ZMessager.alert('失败', response.status);
		    		    				}
		    		    			});
		                		}
		                	});
		                }		                
		            }
	            }]
	        })
		};
		
		// apply config
	    Ext.apply(this, Ext.apply(this.initialConfig, config));	    
	    // call parent
	    Z.JXP.system.acl.UsersOfRoleGrid.superclass.initComponent.apply(this, arguments);
	    
	}, //~initComponent
	
	loadUsers : function(role) {
		this.getStore().baseParams.id = role;
		this.getStore().load({params: {start: 0, limit: 25}});
	}
});
/**
 * System -> ACL -> Role Management Tab -> PermissionsOfRoleWindow
 */
/**
 * System -> ACL -> Role Management Tab
 */
Z.JXP.system.acl.RolesGridTab = Ext.extend(Ext.grid.GridPanel, {
	initComponent : function() {
		var thisRolesGridTab = this;
		var NEW_ROLE_NAME = '新角色';
		var NEW_ROLE_DESC = '新创建角色';
		var Role = Z.JXP.system.acl.Shared.Role;
		var rolesStore = Z.JXP.system.acl.Shared.rolesStore;
		var User = Z.JXP.system.acl.Shared.User;
		var usersStore = Z.JXP.system.acl.Shared.usersStore;
		var rolesSM = new Ext.grid.CheckboxSelectionModel();
		rolesSM.on('selectionchange', function(sm) {});
		var roleRowEditor = new Ext.ux.grid.RowEditor({
	        saveText: '保存',
	        cancelText: '取消',
	        commitChangesText: '有修改未保存!',
	        errorText: '注意'
	    });
		roleRowEditor.on('afteredit', function(rowEditor, object, record, rowIndex) {
			if (!object.name) { // Name not changed
				//if (record.data.name = NEW_ROLE_NAME) { // New role name not changed
					//setTimeout(function() {
						//rowEditor.startEditing(rowIndex, 1);
						//rowEditor.doFocus(0);
						//rowEditor.showTooltip('请修改角色名称!');
					//}, 500);
					//return;
				//} 
			}
			if (!object.description) { 
			}
			// Send to server
			Ext.Ajax.request({
				url: './system/acl.jxp',
				params: { 
					action: 'saveRole',
					id: record.data.id,
					name: object.name,
					description: object.description
				},
				success: function(response, opts) {
					var obj = Ext.decode(response.responseText);
					console.dir(obj);
					Z.ux.Messager.alert('操作成功', '数据已经成功保存!');
					rolesStore.load({params:{start:0, limit:25}}); 
				},
				failure: function(response, opts) {
					console.log('server-side failure with status code ' + response.status);
					alert('server-side failure with status code ' + response.status);
				}
			});
		});
		var searchRenderer = function(val) {
			var q = rolesStore.baseParams.q;
			if (q) { val = val.replace(q, '<span class="search-highlight">'+q+'</span>'); }
			return val;
		};
		var config = {
			id: 'tab-roles',
			title: '权限组',
            margins: '0 0 0 0',
	        enableDD: true,
	        store: rolesStore,
            iconCls: 'icon-acl-role',
	        plugins: [ roleRowEditor ],
	        columns: [
	            rolesSM, 
	            {
	            	id:'name',
	            	header: "名称", 
	            	width: 160, 
	            	sortable: true, 
	            	dataIndex: 'name',
	            	renderer: searchRenderer,
	                editor: {
	                    xtype: 'textfield',
	                    allowBlank: false
	                }
	            },
	            {
	            	id:'description',
	            	header: "描述", 
	            	width: 120, 
	            	sortable: true, 
	            	dataIndex: 'description',
	            	renderer: searchRenderer,
	                editor: {
	                    xtype: 'textfield',
	                    allowBlank: true
	                }
	            },
	            {id:'isWrapper',header: "是否用户?", width: 75, sortable: true, dataIndex: 'isWrapper'},
	            {id:'users',header: "关联用户", width: 200, sortable: true, dataIndex: 'users'},
	            {id:'permissons',header: "包含权限", width: 200, sortable: true, dataIndex: 'permissons'},
	            {id: 'updatedTime', header: "更新时间", width: 120, sortable: true, dataIndex: 'updatedTime'}
	        ],
	        sm: rolesSM,
	        stripeRows: true,
	        autoExpandColumn: 'updatedTime',
	        
	        listeners: {
		    	'rowclick': function(grid, rowIndex, evn) {
			    	//alert('click rowclick');
			    }
		    },
		    
		    tbar: [
				'搜索: ', ' ',
                new Ext.app.SearchField({
	                width:120,
					store: rolesStore,
					paramName: 'q'
	            }), '->',
	            new Ext.form.Checkbox({
	            	boxLabel: '显示用户',
	            	checked: false,
	            	name: 'wrapper',
	            	handler: function(chb, checked) {
		            	alert('Not yet ... ');
		            }
	            })
            ],

	        bbar: new Z.ux.PagingToolbarCN({
	            pageSize: 25,
	            displayInfo: true,
	            store: rolesStore,

	            plugins: new Ext.ux.ProgressBarPager(),
	            
	            items: ['-', {
	            	tooltip: '增加',
	            	icon: JXP_WEB_CONTEXT + '/resources/icons/silk/gif/add.gif',
	            	handler: function() {
		                var r = new Role({
		                    name: '',
		                    description: '',
		                    isWrapper: 0
		                });
		                roleRowEditor.stopEditing();
		                rolesStore.insert(0, r);
		                thisRolesGridTab.getView().refresh();
		                thisRolesGridTab.getSelectionModel().selectRow(0);
		                roleRowEditor.startEditing(0);
		            }
	            }, '-', {
	            	tooltip: '删除',
	            	icon: JXP_WEB_CONTEXT + '/resources/icons/silk/gif/delete.gif',
	            	handler: function() {
	            		roleRowEditor.stopEditing();
		                var selections = rolesSM.getSelections();
		                if (rolesSM.getCount() > 0) {
		                	Ext.MessageBox.confirm('操作确认', '确认删除所选权限组?', function(btn) {
		                		alert(btn);
		                		if (btn = 'yes') {
		    		                var idArray = new Array();
		    		                for(var i = 0, r; r = selections[i]; i++){
		    		                	idArray[i] = r.data.id;
		    		                	rolesStore.remove(r);
		    		                }
		    		    			// Send to server
		    		    			Ext.Ajax.request({
		    		    				url: './system/acl.jxp',
		    		    				params: { 
		    		    					action: 'deleteRole',
		    		    					id: idArray.toString()
		    		    				},
		    		    				success: function(response, opts) {
		    		    					var obj = Ext.decode(response.responseText);
		    		    					console.dir(obj);
		    		    				},
		    		    				failure: function(response, opts) {
		    		    					console.log('server-side failure with status code ' + response.status);
		    		    				}
		    		    			});
		                		}
		                	});
		                }		                
		            }
	            }, '-', {
	            	id: 'btn-acl-role-users',
	            	tooltip: '关联用户',
	            	icon: JXP_WEB_CONTEXT + '/resources/icons/silk/gif/user_add.gif',
	            	handler: function(btn) {
		            	if (rolesSM.getCount() == 1) {
		            		thisRolesGridTab.usersWindow.items.first().loadUsers(rolesSM.getSelected().data.id);
		            		thisRolesGridTab.usersWindow.show('btn-acl-role-users', function() {
		            			//btn.disable();
		            		});
		            		btn.disable();
	        			} else {
	        				ZMessager.alert('错误', '请选择一个角色进行操作!');
	        			}
		            }
	            }, '-', {
	            	id: 'btn-acl-role-permissions',
	            	tooltip: '设置权限',
	            	icon: JXP_WEB_CONTEXT + '/resources/icons/silk/gif/cog_add.gif',
	            	handler: function() {
	            		if (rolesSM.getCount() == 1) {	            			
	            			thisRolesGridTab.permissionsWindow.rolePermissonsStore.baseParams.role=rolesSM.getSelected().data.id;
	            			thisRolesGridTab.permissionsWindow.rolePermissonsStore.load({params: {start:0,limit:25}});
		            		thisRolesGridTab.permissionsWindow.show('btn-acl-role-permissons', function() {
		            			//btn.disable();
		            		});
	            		} else {
	        				ZMessager.alert('错误', '请选择一个角色进行操作!');
	            		}
		            }
	            }]
	        })
		};
		
		// Add users
		var usersWindow = new Ext.Window({
			id: 'win-acl-role-users',
			title: '关联用户',
            layout:'fit',		
	        width:600,
	        height:400,
            //plain: true,
            border: false,
            closeAction:'hide',
	        iconCls:'icon-acl-role-users',

            items: new Z.JXP.system.acl.UsersOfRoleGrid(),

            buttons: [{
                text: '关闭',
                handler: function(){
            		usersWindow.hide();
            		Ext.getCmp('btn-acl-role-users').enable();
            		thisRolesGridTab.getStore().load({params:{start:0, limit:25}}); 
                }
            }]			
		});
		this.usersWindow = usersWindow;
		// Add persmissions
		//this.permissionsWindow = Z.JXP.system.acl.PermissionsOfRoleWindow();
		
		// apply config
	    Ext.apply(this, Ext.apply(this.initialConfig, config));
	    
	    // call parent
	    Z.JXP.system.acl.RolesGridTab.superclass.initComponent.apply(this, arguments);
	}
});

/**
 * System -> ACL -> Class Management Tab
 */
Z.JXP.system.acl.ClassesGridTab = Ext.extend(Ext.grid.GridPanel, {
	initComponent : function() {
		var thisClassesGridTab = this;
		var NEW_CLASS_NAME = 'com.package.ClassName';
		var NEW_CLASS_DESC = '新创建控制类';
		var AclClass = Ext.data.Record.create(['id', 'name', 'description', 'objectCount', 'updatedTime']);
		var classesStore = new Ext.data.Store({
			url: './system/acl.jxp',
			//url: '../modules/system/admin/acl-classes.xml',
			reader: new Ext.data.XmlReader({
				id: 'id',
				record: 'class',
				totalRecords: 'totalCount'
			}, AclClass),			
			sortInfo: {
				field: "name", direction: "ASC"
			},			
			baseParams: {action: 'listClasses', limit: 25}
		});
		var classesSM = new Ext.grid.CheckboxSelectionModel();
		classesSM.on('selectionchange', function(sm) {});
		var classRowEditor = new Ext.ux.grid.RowEditor({
	        saveText: '保存',
	        cancelText: '取消',
	        commitChangesText: '有修改未保存!',
	        errorText: '注意'
	    });
		classRowEditor.on('afteredit', function(rowEditor, object, record, rowIndex) {
			if (!object.name) { // Name not changed
				if (record.data.name = NEW_CLASS_NAME) { // New class name not changed
					setTimeout(function() {
						rowEditor.startEditing(rowIndex, 1);
						rowEditor.doFocus(0);
						rowEditor.showTooltip('请修改类名!');
					}, 500);
					return;
				} 
			}
			if (!object.description) {
				if (record.data.description = NEW_CLASS_DESC) { // New class desc not changed
					setTimeout(function() {
						rowEditor.startEditing(rowIndex, 2);
						rowEditor.doFocus(1);
						rowEditor.showTooltip('请修改类描述!');
					}, 500);
					return;
				}
			}
			// Send to server
			Ext.Ajax.request({
				url: './system/acl.jxp',
				params: { 
					action: 'saveClass',
					id: record.data.id,
					name: object.name,
					description: object.description
				},
				success: function(response, opts) {
					var obj = Ext.decode(response.responseText);
					console.dir(obj);
					ZMessager.alert('操作成功', '数据已经成功保存!');
					classesStore.load({params:{start:0, limit:25}}); 
				},
				failure: function(response, opts) {
					console.log('server-side failure with status code ' + response.status);
					ZMessager.alert('错误', 'server-side failure with status code ' + response.status);
				}
			});
		});
		var searchRenderer = function(val) {
			var q = classesStore.baseParams.q;
			if (q) { val = val.replace(q, '<span class="search-highlight">'+q+'</span>'); }
			return val;
		};
		var config = {
			id: 'tab-classes',
			title: '控制类',
            margins: '0 0 0 0',
	        enableDD: true,
	        store: classesStore,
            iconCls: 'icon-acl-class',
	        plugins: [ classRowEditor ],
	        columns: [
	            classesSM, 
	            {
	            	id:'name',
	            	header: "名称", 
	            	width: 240, 
	            	sortable: true, 
	            	dataIndex: 'name',
	            	renderer: searchRenderer,
	                editor: {
	                    xtype: 'textfield',
	                    allowBlank: false
	                }
	            },
	            {
	            	id:'description',
	            	header: "描述", 
	            	width: 160, 
	            	sortable: true, 
	            	dataIndex: 'description',
	            	renderer: searchRenderer,
	                editor: {
	                    xtype: 'textfield',
	                    allowBlank: false
	                }
	            },
	            {header: '对象数', width: 60, sortable: true, dataIndex: 'objectCount'},
	            {id: 'updatedTime', header: '更新时间', width: 120, sortable: true, dataIndex: 'updatedTime'}
	        ],
	        sm: classesSM,
	        stripeRows: true,
	        autoExpandColumn: 'updatedTime',
	        
	        listeners: {
		    	'rowclick': function(grid, rowIndex, evn) {
			    	//alert('click rowclick');
			    }
		    },
		    
		    tbar: [
				'搜索: ', ' ',
                new Ext.app.SearchField({
	                width:120,
					store: classesStore,
					paramName: 'q'
	            })
            ],

	        bbar: new Z.ux.PagingToolbarCN({
	            pageSize: 25,
	            displayInfo: true,
	            store: classesStore,

	            plugins: new Ext.ux.ProgressBarPager(),
	            
	            items: ['-', {
	            	tooltip: '增加',
	            	icon: JXP_WEB_CONTEXT + '/resources/icons/silk/gif/add.gif',
	            	handler: function() {
		                var c = new AclClass({
		                    name: NEW_CLASS_NAME,
		                    description: NEW_CLASS_DESC,
		                    objectCount: 0
		                });
		                classRowEditor.stopEditing();
		                classesStore.insert(0, c);
		                thisClassesGridTab.getView().refresh();
		                thisClassesGridTab.getSelectionModel().selectRow(0);
		                classRowEditor.startEditing(0);
		            }
	            }, '-', {
	            	tooltip: '删除',
	            	icon: JXP_WEB_CONTEXT + '/resources/icons/silk/gif/delete.gif',
	            	handler: function() {
	            		classRowEditor.stopEditing();
		                var selections = classesSM.getSelections();
		                if (classesSM.getCount() > 0) {
		                	Ext.MessageBox.confirm('操作确认', '确认删除所选控制类?', function(btn) {
		                		//alert(btn);
		                		if (btn = 'yes') {
		    		                var idArray = new Array();
		    		                for(var i = 0, r; r = selections[i]; i++){
		    		                	idArray[i] = r.data.id;
		    		                	classesStore.remove(r);
		    		                }
		    		    			// Send to server
		    		    			Ext.Ajax.request({
		    		    				url: './system/acl.jxp',
		    		    				params: { 
		    		    					action: 'deleteClass',
		    		    					id: idArray.toString()
		    		    				},
		    		    				success: function(response, opts) {
		    		    					var obj = Ext.decode(response.responseText);
		    		    					console.dir(obj);
		    		    					ZMessager.alert('操作成功', '控制类成功删除!');
		    		    				},
		    		    				failure: function(response, opts) {
		    		    					console.log('server-side failure with status code ' + response.status);
		    		    					ZMessager.alert('错误', '服务器错误码:' + response.status);
		    		    				}
		    		    			});
		                		}
		                	});
		                }		                
		            }
	            }]
	        })
		};
		// Public Properties
		//this.classesStore = classesStore;
		
		// apply config
	    Ext.apply(this, Ext.apply(this.initialConfig, config));
	    
	    // call parent
	    Z.JXP.system.acl.ClassesGridTab.superclass.initComponent.apply(this, arguments);
	    
	}//~ END initComponent()

});

/**
 * System -> ACL -> Object Management Tab
 */
Z.JXP.system.acl.ObjectsGridTab = Ext.extend(Ext.grid.GridPanel, {
	initComponent : function() {
		var thisObjectsGridTab = this;
		var NEW_OBJECT_NAME = '新对象名称';
		var NEW_OBJECT_DESC = '新创建对象';
		var AclObject = Z.JXP.system.acl.Shared.AclObject;
		var objectsStore = Z.JXP.system.acl.Shared.objectsStore;
		var objectsSM = new Ext.grid.CheckboxSelectionModel();
		objectsSM.on('selectionchange', function(sm) {});
		var objectRowEditor = new Ext.ux.grid.RowEditor({
	        saveText: '保存',
	        cancelText: '取消',
	        commitChangesText: '有修改未保存!',
	        errorText: '注意'
	    });
		objectRowEditor.on('afteredit', function(rowEditor, object, record, rowIndex) {
			if (!object.name) { // Name not changed
				if (record.data.name = NEW_CLASS_NAME) { // New object name not changed
					setTimeout(function() {
						rowEditor.startEditing(rowIndex, 1);
						rowEditor.doFocus(0);
						rowEditor.showTooltip('请修改类名!');
					}, 500);
					return;
				} 
			}
			if (!object.description) {
			}
			// Send to server
			Ext.Ajax.request({
				url: './system/acl.jxp',
				params: { 
					action: 'saveObject',
					id: record.data.id,
					name: object.name,
					description: object.description
				},
				success: function(response, opts) {
					var obj = Ext.decode(response.responseText);
					console.dir(obj);
					ZMessager.alert('操作成功', '数据已经成功保存!');
					objectsStore.load({params:{start:0, limit:25}}); 
				},
				failure: function(response, opts) {
					console.log('server-side failure with status code ' + response.status);
					ZMessager.alert('错误', 'server-side failure with status code ' + response.status);
				}
			});
		});
		var searchRenderer = function(val) {
			var q = objectsStore.baseParams.q;
			if (q) { val = val.replace(q, '<span class="search-highlight">'+q+'</span>'); }
			return val;
		};
		var config = {
			id: 'tab-objects',
			title: '控制对象',
            margins: '0 0 0 0',
	        enableDD: true,
	        store: objectsStore,
            iconCls: 'icon-acl-object',
	        plugins: [ objectRowEditor ],
	        columns: [
	            objectsSM,
	            {
	            	id:'name',
	            	header: "对象", 
	            	width: 160, 
	            	sortable: true, 
	            	dataIndex: 'name',
	            	renderer: searchRenderer,
	                editor: {
	                    xtype: 'textfield',
	                    allowBlank: false
	                }
	            },
	            {
	            	id:'description',
	            	header: "描述", 
	            	width: 160, 
	            	sortable: true, 
	            	dataIndex: 'description',
	            	renderer: searchRenderer,
	                editor: {
	                    xtype: 'textfield',
	                    allowBlank: false
	                }
	            },
	            {id:'class',header: "类", width: 160, sortable: true, dataIndex: 'class'},
	            {id:'owner',header: "角色", width: 160, sortable: true, dataIndex: 'owner'},
	            {id:'createdTime',header: "创建时间", width: 160, sortable: true, dataIndex: 'createdTime'}
	        ],
	        sm: objectsSM,
	        stripeRows: true,
	        autoExpandColumn: 'createdTime',
	        
	        listeners: {
		    	'rowclick': function(grid, rowIndex, evn) {
			    	//alert('click rowclick');
			    }
		    },
		    
		    tbar: [
				'搜索: ', ' ',
                new Ext.app.SearchField({
	                width:120,
					store: objectsStore,
					paramName: 'q'
	            })
            ],

	        bbar: new Z.ux.PagingToolbarCN({
	            pageSize: 25,
	            displayInfo: true,
	            store: objectsStore,

	            plugins: new Ext.ux.ProgressBarPager(),
	            
	            items: ['-']
	        })
		};
		// Public Properties
		//this.objectsStore = objectsStore;
		
		// apply config
	    Ext.apply(this, Ext.apply(this.initialConfig, config));
	    
	    // call parent
	    Z.JXP.system.acl.ObjectsGridTab.superclass.initComponent.apply(this, arguments);
	    
	}//~ END initComponent()
});

/**
 * System -> ACL -> Entry Management Tab
 */
Z.JXP.system.acl.EntriesGridTab = Ext.extend(Ext.grid.GridPanel, {
	initComponent : function() {
		var thisEntriesGridTab = this;
		var NEW_ENTRY_OBJ = '对象名';
		var NEW_ENTRY_SID = '角色ID';
		var AclEntry = Ext.data.Record.create(['id', 'object', 'role', 'mask', 'createdTime']);
		var entriesStore = new Ext.data.Store({
			url: './system/acl.jxp',
			//url: '../modules/system/admin/acl-entries.xml',
			reader: new Ext.data.XmlReader({
				id: 'id',
				record: 'entry',
				totalRecords: 'totalCount'
			}, AclEntry),			
			sortInfo: {
				field: "object", direction: "ASC"
			},			
			baseParams: {action: 'listEntries', limit: 25}
		});
		var entriesSM = new Ext.grid.CheckboxSelectionModel();
		entriesSM.on('selectionchange', function(sm) {});
		var entryRowEditor = new Ext.ux.grid.RowEditor({
	        saveText: '保存',
	        cancelText: '取消',
	        commitChangesText: '有修改未保存!',
	        errorText: '注意'
	    });
		entryRowEditor.on('afteredit', function(rowEditor, object, record, rowIndex) {
			if (!object.object) { // Name not changed
				if (record.data.object = NEW_ENTRY_OBJ) { 
					setTimeout(function() {
						rowEditor.startEditing(rowIndex, 1);
						rowEditor.doFocus(0);
						rowEditor.showTooltip('请选择一个对象!');
					}, 500);
					return;
				} 
			}
			if (!object.role) { // Role not changed
				if (record.data.role = NEW_ENTRY_SID) { 
					setTimeout(function() {
						rowEditor.startEditing(rowIndex, 2);
						rowEditor.doFocus(1);
						rowEditor.showTooltip('请选择一个角色!');
					}, 500);
					return;
				} 
			}
			// Send to server
			Ext.Ajax.request({
				url: './system/acl.jxp',
				params: { 
					action: 'saveEntry',
					id: record.data.id,
					object: object.object,
					rloe: object.role,
					mask: object.mask
				},
				success: function(response, opts) {
					var obj = Ext.decode(response.responseText);
					console.dir(obj);
					ZMessager.alert('操作成功', '数据已经成功保存!');
					entriesStore.load({params:{start:0, limit:25}}); 
				},
				failure: function(response, opts) {
					console.log('server-side failure with status code ' + response.status);
					ZMessager.alert('错误', 'server-side failure with status code ' + response.status);
				}
			});
		});
		var searchRenderer = function(val) {
			var q = entriesStore.baseParams.q;
			if (q) { val = val.replace(q, '<span class="search-highlight">'+q+'</span>'); }
			return val;
		};
		var config = {
			id: 'tab-entries',
			title: '权限控制',
            margins: '0 0 0 0',
	        enableDD: true,
	        store: entriesStore,
            iconCls: 'icon-acl-entry',
	        plugins: [ entryRowEditor ],
	        columns: [
	            entriesSM,
	            {
	            	id:'object',
	            	header: "控制对象", 
	            	width: 160, 
	            	sortable: true, 
	            	dataIndex: 'object',
	            	renderer: searchRenderer,
	                editor: {
	                    xtype: 'combo',
                        hiddenName:'object',
                        store: Z.JXP.system.acl.Shared.objectsStore,
                        valueField:'id',
                        displayField:'name',
                        typeAhead: true,
                        //mode: 'local',
                        triggerAction: 'all',
                        emptyText:'选择对象...',
                        selectOnFocus:true,
	                    allowBlank: false
	                }
	            },
	            {
	            	id:'role',
	            	header: "用户角色", 
	            	width: 160, 
	            	sortable: true, 
	            	dataIndex: 'role',
	            	renderer: searchRenderer,
	                editor: {
	                    xtype: 'combo',
	                    hiddenName:'role',
	                    store: Z.JXP.system.acl.Shared.rolesStore,
	                    valueField:'id',
	                    displayField:'name',
	                    typeAhead: true,
	                    //mode: 'local',
	                    triggerAction: 'all',
	                    emptyText:'选择角色...',
	                    selectOnFocus:true,
	                    allowBlank: false
	                }
	            },
	            {
	            	id:'mask',
	            	header: "权限码", 
	            	width: 160, 
	            	sortable: true, 
	            	dataIndex: 'mask',
	                editor: {
	                    xtype: 'textfield',
	                    allowBlank: false
	                }
	            },
	            {id:'createdTime',header: "创建时间", width: 160, sortable: true, dataIndex: 'createdTime'}
	        ],
	        sm: entriesSM,
	        stripeRows: true,
	        autoExpandColumn: 'createdTime',
	        
	        listeners: {
		    	'rowclick': function(grid, rowIndex, evn) {
			    	//alert('click rowclick');
			    }
		    },
		    
		    tbar: [
				'搜索: ', ' ',
                new Ext.app.SearchField({
	                width:120,
					store: entriesStore,
					paramName: 'q'
	            })
            ],

	        bbar: new Z.ux.PagingToolbarCN({
	            pageSize: 25,
	            displayInfo: true,
	            store: entriesStore,

	            plugins: new Ext.ux.ProgressBarPager(),
	            
	            items: ['-', {
	            	tooltip: '增加',
	            	icon: JXP_WEB_CONTEXT + '/resources/icons/silk/gif/add.gif',
	            	handler: function() {
		                var e = new AclEntry({
		                    object: NEW_ENTRY_OBJ,
		                    role: NEW_ENTRY_SID,
		                    mask: 0
		                });
		                entryRowEditor.stopEditing();
		                entriesStore.insert(0, e);
		                thisEntriesGridTab.getView().refresh();
		                thisEntriesGridTab.getSelectionModel().selectRow(0);
		                entryRowEditor.startEditing(0);
		            }
	            }, '-', {
	            	tooltip: '编辑',
	            	icon: '../resources/icons/silk/gif/pencil.gif'
	            }, '-', {
	            	tooltip: '删除',
	            	icon: JXP_WEB_CONTEXT + '/resources/icons/silk/gif/delete.gif',
	            	handler: function() {
	            		entryRowEditor.stopEditing();
		                var selections = entriesSM.getSelections();
		                if (entriesSM.getCount() > 0) {
		                	Ext.MessageBox.confirm('操作确认', '确认删除所选权限设置?', function(btn) {
		                		//alert(btn);
		                		if (btn = 'yes') {
		    		                var idArray = new Array();
		    		                for(var i = 0, r; r = selections[i]; i++){
		    		                	idArray[i] = r.data.id;
		    		                	entriesStore.remove(r);
		    		                }
		    		    			// Send to server
		    		    			Ext.Ajax.request({
		    		    				url: './system/acl.jxp',
		    		    				params: { 
		    		    					action: 'deleteEntry',
		    		    					id: idArray.toString()
		    		    				},
		    		    				success: function(response, opts) {
		    		    					var obj = Ext.decode(response.responseText);
		    		    					console.dir(obj);
		    		    					ZMessager.alert('操作成功', '控制类成功删除!');
		    		    				},
		    		    				failure: function(response, opts) {
		    		    					console.log('server-side failure with status code ' + response.status);
		    		    					ZMessager.alert('错误', '服务器错误码:' + response.status);
		    		    				}
		    		    			});
		                		}
		                	});
		                }		                
		            }
	            }]
	        })
		};
		// Public Properties
		//this.objectsStore = objectsStore;
		
		// apply config
	    Ext.apply(this, Ext.apply(this.initialConfig, config));
	    
	    // call parent
	    Z.JXP.system.acl.EntriesGridTab.superclass.initComponent.apply(this, arguments);
	    
	}//~ END initComponent()
});

Z.JXP.system.AclCfgTabPanel = Ext.extend(Ext.TabPanel, {
	initComponent : function() {
		var thisAclCfgTabPanel = this;
		// Tab 1: Role
		var rolesGridTab = new Z.JXP.system.acl.RolesGridTab();
		rolesGridTab.on('activate', function(panel) {//move to parent show
			//panel.getStore().load({params:{start:0, limit:25}}); 
		});
		// Tab 2: Class
		var classesGridTab = new Z.JXP.system.acl.ClassesGridTab();	
		classesGridTab.on('activate', function(panel) {
			panel.getStore().load({params:{start:0, limit:25}}); 
		});	
		// Tab 3: Object
		var objectsGridTab = new Z.JXP.system.acl.ObjectsGridTab();
		objectsGridTab.on('activate', function(panel) {
			panel.getStore().load({params:{start:0, limit:25}}); 
		});	
		// Tab 4: Entry
		var entriesGridTab = new Z.JXP.system.acl.EntriesGridTab();
		entriesGridTab.on('activate', function(panel) {
			panel.getStore().load({params:{start:0, limit:25}}); 
		});	
		
		// Config
		var config = {
			id: 'system-acl-panel',
			plain:true,
			activeTab: 0,
		    defaults:{autoScroll: true},
			items: [rolesGridTab, classesGridTab, objectsGridTab, entriesGridTab]
		};
		
		// save component
		this.rolesGridTab = rolesGridTab;
		this.classesGridTab = classesGridTab;
		this.objectsGridTab = objectsGridTab;
		this.entriesGridTab = entriesGridTab;
		
		// apply config
	    Ext.apply(this, Ext.apply(this.initialConfig, config));
	    
	    // call parent
	    Z.JXP.system.AclCfgTabPanel.superclass.initComponent.apply(this, arguments);
	},//END-initComponent
	
	onRender : function() {
		Z.JXP.system.AclCfgTabPanel.superclass.onRender.apply(this, arguments);
	}// END-onRender()
});

Ext.reg('z_jxp_system_aclcfgtabpanel', Z.JXP.system.AclCfgTabPanel);

//Initialize Panel
var zJxpSystemAclCfgTabPanel = new Z.JXP.system.AclCfgTabPanel();
zJxpSystemAclCfgTabPanel.on('show', function(panel) {
	panel.rolesGridTab.getStore().load({params:{start:0, limit:25}}); 
});

//Register Panel
registerPanel(zJxpSystemAclCfgTabPanel);