Ext.onReady(function(){
	
	var contextPath = '';
	var contextPathEl = Ext.get('contextpath');
	if (contextPathEl) {
		contextPath = contextPathEl.dom.innerHTML;
	}

    // create the Data Store
    var store = new Ext.data.Store({
        // load using HTTP
        url: 'modules/news/list.jsp',

        // the return will be XML, so lets set up a reader
        reader: new Ext.data.XmlReader({
               // records will have an "Item" tag
               record: 'Entry',
               id: 'id',
               totalRecords: 'totalCount'
           }, [
               'id', 'title', 'date', 'viewCount'
           ])
    });
    
    var entryLinkRenderer = function(id) {
    	return '<a href="' + contextPath + '/modules/news/entry.jsp?id=' 
    		+ id + '" target="_blank">查看</a>';
    }

    // create the grid
    var grid = new Ext.grid.GridPanel({
    	title: '公司动态',
        store: store,
        frame: true,
        columns: [
            {header: "日期", width: 64, dataIndex: 'date', sortable: true},
            {id: 'title', header: "标题", width: 180, dataIndex: 'title', sortable: true},
            //{header: "阅读", width: 32, dataIndex: 'viewCount', sortable: true},
            {header: "查看", width: 48, dataIndex: 'id', renderer: entryLinkRenderer}
        ],
        //renderTo:'latest-news-panel',
        autoExpandColumn: 'title',
        width:440,
        height:200
    });
    grid.render('latest-news-panel');

    store.load();
    
    Ext.get("latest-news-panel").boxWrap().addClass('x-box-gray');
});