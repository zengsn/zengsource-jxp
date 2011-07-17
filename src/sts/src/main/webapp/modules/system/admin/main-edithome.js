/*
 * Edit home page.
 * Copyright(c) 2008, iCanWear.com
 * icanwear@hotmail.com
 * 
 * http://iCanWear.com/license
 */
 
var editHomeForm = {
	xtype: 'form', // since we are not using the default 'panel' xtype, we must specify it
	id: 'edit-home-panel',
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
        //},{
        //   xtype:'htmleditor',
        //    id:'bio',
         //   fieldLabel:'Biography',
        //    height:200,
        //    anchor:'98%'
        }
    ],
    buttons: [{text: 'Save'},{text: 'Cancel'}]
};