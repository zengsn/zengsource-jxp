
Ext.onReady(function() {	
	
	// File Record
	var FileInfo = Ext.data.Record.create([
		'filename', 'size', 'status'
	]);
	
	// create the Data Store
    var store = new Ext.data.Store({
        // load using HTTP
        url: 'multi-files.xml',

        // the return will be XML, so lets set up a reader
        reader: new Ext.data.XmlReader({
			record: 'file',
			id: 'filename',
			totalRecords: 'totalCount'
		}, FileInfo)
    });
    
    // File Field
    var ff = new Ext.form.Field({
    	inputType: 'file',
    	fieldLabel: '上传图片',
    	name: 'images'
    });
    ff.on('blur', function() {
    	alert('abc');
    });

    // create the grid
    var grid = new Ext.grid.GridPanel({
        store: store,
        columns: [
            {id: 'filename', header: "Filename", width: 120, dataIndex: 'filename', sortable: true},
            {header: "Size", width: 180, dataIndex: 'size', sortable: true},
            {header: "Status", width: 115, dataIndex: 'status', sortable: true}
        ],
        autoExpandColumn: 'filename',
        renderTo:'multi-files',
        title: 'Multi-Files Uploader',
        width:540,
        height:200,
        
        tbar: [ff, '-']
    });

    store.load();
	
});