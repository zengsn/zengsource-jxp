// TreeForm.html


Ext.onReady(function() {
	
	var treeLoader = new Ext.tree.TreeLoader({
      //dataUrl:'../xdb/main-tree.json'
      dataUrl:'../admin/system/tree.htm'
    });	
	
	var form = {
		xtype: 'form', 
		id: 'form-panel',
		labelWidth: 75,
		title: 'Form Layout',
		bodyStyle:'padding:15px',
		width: 400,
		labelPad: 20,
		layoutConfig: {
			labelSeparator: ''
		},
		defaults: {
			width: 230,
			msgTarget: 'side'
		},
		defaultType: 'textfield',
		items: [{
			fieldLabel: 'First Name',
			name: 'first',
			allowBlank:false
		},{
			fieldLabel: 'Last Name',
			name: 'last'
		},{
			fieldLabel: 'Company',
			name: 'company'
		},{
			fieldLabel: 'Email',
			name: 'email',
			vtype:'email'
		}],
		buttons: [{text: 'Save'},{text: 'Cancel'}]
	};
	
	var treeForm = new Ext.ux.TreeForm({
		tree: {
			loader: treeLoader
		},
		form: form
	});
	
	treeForm.render('tree-form-demo');
	
	
});