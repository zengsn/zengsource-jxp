
/**
  * ContentPanelArray holds all the content panels used in the main page.
  * Every new implemented module should register penal to this array.
  */
var ContentPanelArray = new Array();

/**
  * Facility method to register panel.
  */
function registerPanel(extPanel) {
	var length = ContentPanelArray.length;
	ContentPanelArray[length] = extPanel;
};

var NameNValue = Ext.data.Record.create([
	'id', 'name', 'value'
]);
/*
var ProductCatalog = Ext.data.Record.create([
	'id', 'name', 'parent', 'description'
]);

var productCatalogStore = new Ext.data.Store({
	// load using HTTP
	url: 'product/catalog.jxp',
	baseParams: {
		action: 'list',
		type: 'xml'
	}, 

	// the return will be XML, so lets set up a reader
	reader: new Ext.data.XmlReader({
		id: 'id',
		record: 'Catalog',
		totalRecords: 'totalCount'
	}, ProductCatalog)
});
*/