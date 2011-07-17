// UploadFormPanel.html

Ext.onReady(function() {
	
	var form = {
		xtype: 'form', 
		id: 'form-panel',
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
	};
	
	var upload = {
		title: '上传文件',
		upload_url: 'http://fancing.net/swf-upload/upload_example.php',
		post_params: { uid: 'abc' },
		debug: true,
		single_select: true, // Select only one file from the FileDialog

		// Custom Params
		single_file_select: false, // Set to true if you only want to select one file from the FileDialog.
		confirm_delete: false, // This will prompt for removing files from queue.
		remove_completed: false // Remove file from grid after uploaded.
	};
	
	var demo = new Ext.ux.UploadFormPanel({
		height: 600,
		title: '上传表单',
		form: form,
		upload: upload
	});
	
	demo.render('upload-form-demo');
	
	alert(demo.getFormPanel().id);
	alert(demo.getUploadPanel().id);
	
});