//ZPagingToolbarCN.js

Ext.namespace('Z.ux');

Z.ux.PagingToolbarCN = Ext.extend(Ext.PagingToolbar, {
	initComponent : function() {
		var config = {
			afterPageText: '页&nbsp;/&nbsp;共{0}页',
			beforePageText: '第',
			displayInfo: true,
			displayMsg: '当前显示&nbsp;::&nbsp;第{0}-{1}条&nbsp;/&nbsp;共{2}条',
			emptyMsg: '没有数据',
			firstText: '第一页',
			lastText: '最后一页',
			nextText: '下一页',
			prevText: '前一页',
			refreshText: '刷新'
		};
		
		// apply config
	    Ext.apply(this, Ext.apply(this.initialConfig, config));
	    
	    // call parent
	    Z.ux.PagingToolbarCN.superclass.initComponent.apply(this, arguments);
	}
});