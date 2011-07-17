Ext.onReady(function() {
	Ext.QuickTips.init();
    
    // create some portlet tools using built in Ext tool ids
    var tools = [{
        id:'gear',
        handler: function(){
            Ext.Msg.alert('Message', 'The Settings tool was clicked.');
        }
    },{
        id:'close',
        handler: function(e, target, panel){
            panel.ownerCt.remove(panel, true);
        }
    }];

    var productPanel = new Ext.Panel({
    	title: '我们的产品特性',
    	frame: true,
        layout:'fit',
        items:[{
            xtype: 'grouptabpanel',
    		tabWidth: 130,
    		activeGroup: 0,
    		items: [{
    			mainItem: 0,
    			items: [{
    				title: '选择我们',
                    layout: 'fit',
                    iconCls: 'x-icon-tickets',
                    tabTip: '选择我们',
                    style: 'padding: 10px;',
    				items: [ new Ext.Panel({
                        border: false,
    					contentEl: 'whychooseus'
    				})]
    			}, {
    				title: '成功案例',
                    layout: 'fit',
                    iconCls: 'x-icon-tickets',
                    tabTip: '成功案例',
                    style: 'padding: 10px;',
    				items: [ new Ext.Panel({
                        border: false,
    					contentEl: 'caseshow'
    				})]
    			}, 
    			{
    				title: 'Tickets',
                    layout: 'fit',
                    iconCls: 'x-icon-tickets',
                    tabTip: 'Tickets tabtip',
                    style: 'padding: 10px;',
    				items: [new SampleGrid([0,1,2,3,4])]
    			}, {
    				title: 'Subscriptions',
                    iconCls: 'x-icon-subscriptions',
                    tabTip: 'Subscriptions tabtip',
                    style: 'padding: 10px;',
					layout: 'fit',
    				items: [{
						xtype: 'tabpanel',
						activeTab: 1,
						items: [{
							title: 'Nested Tabs',
							html: Ext.example.shortBogusMarkup
						}]	
					}]	
    			}, {
    				title: 'Users',
                    iconCls: 'x-icon-users',
                    tabTip: 'Users tabtip',
                    style: 'padding: 10px;',
    				html: Ext.example.shortBogusMarkup			
    			}]
            }, {
                expanded: true,
                items: [{
    				title: '产品功能',
                    layout: 'fit',
                    iconCls: 'x-icon-tickets',
                    tabTip: '产品功能',
                    style: 'padding: 10px;',
    				items: [ new Ext.Panel({
                        border: false,
    					contentEl: 'features'
    				})]
    			}, {
    				title: '模块化',
                    layout: 'fit',
                    iconCls: 'x-icon-tickets',
                    tabTip: '模块化',
                    style: 'padding: 10px;',
    				items: [ new Ext.Panel({
                        border: false,
    					contentEl: 'modular'
    				})]
    			}, {
                    title: 'Configuration',
                    iconCls: 'x-icon-configuration',
                    tabTip: 'Configuration tabtip',
                    style: 'padding: 10px;',
                    html: Ext.example.shortBogusMarkup 
                }, {
                    title: 'Email Templates',
                    iconCls: 'x-icon-templates',
                    tabTip: 'Templates tabtip',
                    style: 'padding: 10px;',
                    html: Ext.example.shortBogusMarkup
                }]
            }, {
                expanded: true,
                items: [{
    				title: '产品性能',
                    layout: 'fit',
                    iconCls: 'x-icon-tickets',
                    tabTip: '产品性能',
                    style: 'padding: 10px;',
    				items: [ new Ext.Panel({
                        border: false,
    					contentEl: 'performance'
    				})]
    			}, {
    				title: '扩展性',
                    layout: 'fit',
                    iconCls: 'x-icon-tickets',
                    tabTip: '扩展性',
                    style: 'padding: 10px;',
    				items: [ new Ext.Panel({
                        border: false,
    					contentEl: 'scability'
    				})]
    			}, {
                    title: 'Configuration',
                    iconCls: 'x-icon-configuration',
                    tabTip: 'Configuration tabtip',
                    style: 'padding: 10px;',
                    html: Ext.example.shortBogusMarkup 
                }, {
                    title: 'Email Templates',
                    iconCls: 'x-icon-templates',
                    tabTip: 'Templates tabtip',
                    style: 'padding: 10px;',
                    html: Ext.example.shortBogusMarkup
                }]
            }, {
                expanded: true,
                items: [{
    				title: '系统集成',
                    layout: 'fit',
                    iconCls: 'x-icon-tickets',
                    tabTip: '系统集成',
                    style: 'padding: 10px;',
    				items: [ new Ext.Panel({
                        border: false,
    					contentEl: 'integration'
    				})]
    			}, {
    				title: 'SOAP',
                    layout: 'fit',
                    iconCls: 'x-icon-tickets',
                    tabTip: 'SOAP',
                    style: 'padding: 10px;',
    				items: [ new Ext.Panel({
                        border: false,
    					contentEl: 'soap'
    				})]
    			}, {
    				title: 'Porlet',
                    layout: 'fit',
                    iconCls: 'x-icon-tickets',
                    tabTip: 'porlet',
                    style: 'padding: 10px;',
    				items: [ new Ext.Panel({
                        border: false,
    					contentEl: 'porlet'
    				})]
    			}]
            }, {
                expanded: true,
                items: [{
    				title: '系统定制',
                    layout: 'fit',
                    iconCls: 'x-icon-tickets',
                    tabTip: '系统定制',
                    style: 'padding: 10px;',
    				items: [ new Ext.Panel({
                        border: false,
    					contentEl: 'customization'
    				})]
    			}, {
    				title: '模块定制',
                    layout: 'fit',
                    iconCls: 'x-icon-tickets',
                    tabTip: '模块定制',
                    style: 'padding: 10px;',
    				items: [ new Ext.Panel({
                        border: false,
    					contentEl: 'modules'
    				})]
    			}, {
    				title: '主题美化',
                    layout: 'fit',
                    iconCls: 'x-icon-tickets',
                    tabTip: '主题美化',
                    style: 'padding: 10px;',
    				items: [ new Ext.Panel({
                        border: false,
    					contentEl: 'themes'
    				})]
    			}]
            }, {
                expanded: true,
                items: [{
    				title: '后台管理',
                    layout: 'fit',
                    iconCls: 'x-icon-tickets',
                    tabTip: '后台管理',
                    style: 'padding: 10px;',
    				items: [ new Ext.Panel({
                        border: false,
    					contentEl: 'backend'
    				})]
    			}, {
    				title: '管理模块',
                    layout: 'fit',
                    iconCls: 'x-icon-tickets',
                    tabTip: '模块定制',
                    style: 'padding: 10px;',
    				items: [ new Ext.Panel({
                        border: false,
    					contentEl: 'admin-module'
    				})]
    			}, {
    				title: '管理页面',
                    layout: 'fit',
                    iconCls: 'x-icon-tickets',
                    tabTip: '主题美化',
                    style: 'padding: 10px;',
    				items: [ new Ext.Panel({
                        border: false,
    					contentEl: 'admin-page'
    				})]
    			}, {
    				title: '管理区块',
                    layout: 'fit',
                    iconCls: 'x-icon-tickets',
                    tabTip: '管理区块',
                    style: 'padding: 10px;',
    				items: [ new Ext.Panel({
                        border: false,
    					contentEl: 'admin-block'
    				})]
    			}]
            }, {
                expanded: true,
                items: [{
    				title: '模块展示',
                    layout: 'fit',
                    iconCls: 'x-icon-tickets',
                    tabTip: '后台管理',
                    style: 'padding: 10px;',
    				items: [ new Ext.Panel({
                        border: false,
    					contentEl: 'show-modules'
    				})]
    			}, {
    				title: '产品模块',
                    layout: 'fit',
                    iconCls: 'x-icon-tickets',
                    tabTip: '产品模块',
                    style: 'padding: 10px;',
    				items: [ new Ext.Panel({
                        border: false,
    					contentEl: 'product-module'
    				})]
    			}, {
    				title: '销售模块',
                    layout: 'fit',
                    iconCls: 'x-icon-tickets',
                    tabTip: '主题美化',
                    style: 'padding: 10px;',
    				items: [ new Ext.Panel({
                        border: false,
    					contentEl: 'sales-module'
    				})]
    			}, {
    				title: '客户模块',
                    layout: 'fit',
                    iconCls: 'x-icon-tickets',
                    tabTip: '客户模块',
                    style: 'padding: 10px;',
    				items: [ new Ext.Panel({
                        border: false,
    					contentEl: 'customer-module'
    				})]
    			}, {
    				title: '静态模块',
                    layout: 'fit',
                    iconCls: 'x-icon-tickets',
                    tabTip: '静态模块',
                    style: 'padding: 10px;',
    				items: [ new Ext.Panel({
                        border: false,
    					contentEl: 'static-module'
    				})]
    			}, {
    				title: '统计模块',
                    layout: 'fit',
                    iconCls: 'x-icon-tickets',
                    tabTip: '统计模块',
                    style: 'padding: 10px;',
    				items: [ new Ext.Panel({
                        border: false,
    					contentEl: 'statstics-module'
    				})]
    			}, {
    				title: '模块开发',
                    layout: 'fit',
                    iconCls: 'x-icon-tickets',
                    tabTip: '模块开发',
                    style: 'padding: 10px;',
    				items: [ new Ext.Panel({
                        border: false,
    					contentEl: 'develop-module'
    				})]
    			}]
            }, {
                expanded: true,
                items: [{
    				title: '报价',
                    layout: 'fit',
                    iconCls: 'x-icon-tickets',
                    tabTip: '报价',
                    style: 'padding: 10px;',
    				items: [ new Ext.Panel({
                        border: false,
    					contentEl: 'price'
    				})]
    			}, {
    				title: '联系我们',
                    layout: 'fit',
                    iconCls: 'x-icon-tickets',
                    tabTip: '产品模块',
                    style: 'padding: 10px;',
    				items: [ new Ext.Panel({
                        border: false,
    					contentEl: 'contactus'
    				})]
    			}]
            }]
		}]
    });
    
    productPanel.render('product-panel');
});
