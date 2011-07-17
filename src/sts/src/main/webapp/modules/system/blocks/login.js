
Ext.onReady(function() {
	var loginFormPanel = new Ext.form.FormPanel({
		//id: 'form-panel',
    labelWidth: 75,
    title: 'Form Layout',
    bodyStyle:'padding:15px',
    width: 350,
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
	});
	loginFormPanel.render('login-formpanel');
	
});