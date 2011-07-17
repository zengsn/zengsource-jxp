// Depandencies:
// 1.
// 2.

Ext.namespace('Ext.ux');

Ext.ux.SelectGridsPanel = function(config) {
	
	config = config || {};
	
	// Same CM
	this.cm = config.cm;
	
	this.grid1 = config.grid1;
	this.grid1 = Ext.applyIf(grid1, {
		xtype: 'grid',
		
	});
	
	config = Ext.applyIf(config, {
		id: 'select-grids-panel',
		title: 'Select Grids Panel',
		layout: 'border',
		//height: 600,
		//width: 500,
		//autoHeight: true,
		bodyBorder: false,
		defaults: {
			collapsible: true,
			split: true,
			animFloat: false,
			autoHide: false,
			useSplitTips: true			
		},
		items: [this.form, this.uploadPanel]
	});
	
	Ext.apply(this, config);
	
	Ext.ux.SelectGridsPanel.superclass.constructor.apply(this, arguments);
	
};

Ext.extend(Ext.ux.SelectGridsPanel, Ext.Panel, {
});

// Demo
Ext.onReady(function() {
	
	Ext.QuickTips.init();
	
	var Block = Ext.data.Record.create([
		'id', 'name'
	]);
	
	// create the Data Store
	var blockStore1 = new Ext.data.Store({
		// load using HTTP
		url: 'blocks1.xml',
		
		// the return will be XML, so lets set up a reader
		reader: new Ext.data.XmlReader({
			id: 'id',
			record: 'block',
			totalRecords: 'totalCount'
		}, Block),
		
		baseParams: {action: 'list'},
		listeners: {}
	});
	
	//var blockSM = new Ext.grid.CheckboxSelectionModel();
	var blockCM = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(), 
		//blockSM, 
	{	
		header: "Çø¿éÃû³Æ",
		dataIndex: 'name',
		width: 120
	}]);
	
});