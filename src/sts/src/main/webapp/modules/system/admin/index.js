/*
 * Main console of backend.
 * Copyright(c) 2008, iCanWear.com
 * icanwear@hotmail.com
 * 
 * http://iCanWear.com/license
 */

// We are adding these custom layouts to a namespace that does not
// exist by default in Ext, so we have to add the namespace first:
Ext.ns('Ext.ux.layout');

/*
 * ================  CenterLayout  =======================
 */
/**
 * @class Ext.ux.layout.CenterLayout
 * @extends Ext.layout.FitLayout
 * <p>This is a very simple layout style used to center contents within a container.  This layout works within
 * nested containers and can also be used as expected as a Viewport layout to center the page layout.</p>
 * <p>As a subclass of FitLayout, CenterLayout expects to have a single child panel of the container that uses 
 * the layout.  The layout does not require any config options, although the child panel contained within the
 * layout must provide a fixed or percentage width.  The child panel's height will fit to the container by
 * default, but you can specify <tt>autoHeight:true</tt> to allow it to autosize based on its content height.  
 * Example usage:</p> 
 * <pre><code>
// The content panel is centered in the container
var p = new Ext.Panel({
    title: 'Center Layout',
    layout: 'ux.center',
    items: [{
        title: 'Centered Content',
        width: '75%',
        html: 'Some content'
    }]
});

// If you leave the title blank and specify no border
// you'll create a non-visual, structural panel just
// for centering the contents in the main container.
var p = new Ext.Panel({
    layout: 'ux.center',
    border: false,
    items: [{
        title: 'Centered Content',
        width: 300,
        autoHeight: true,
        html: 'Some content'
    }]
});
</code></pre>
 */
Ext.ux.layout.CenterLayout = Ext.extend(Ext.layout.FitLayout, {
	// private
    setItemSize : function(item, size){
        this.container.addClass('ux-layout-center');
        item.addClass('ux-layout-center-item');
        if(item && size.height > 0){
            if(item.width){
                size.width = item.width;
            }
            item.setSize(size);
        }
    }
});
Ext.Container.LAYOUTS['ux.center'] = Ext.ux.layout.CenterLayout;

 
Ext.onReady(function(){
	
	var form = Ext.get('login');
	var actionUrl = form.dom.action;
	//var method = form.getMethod();
	form.remove();
	
	Ext.QuickTips.init();	
	
	// turn on validation errors beside the field globally
  Ext.form.Field.prototype.msgTarget = 'side';
  
  var loginForm = new Ext.FormPanel({
    width:360,
    frame: true,
    labelWidth: 48,
  	autoHeight: true,
    labelAlign: 'right',
    waitMsgTarget: true,
    iconCls: 'icon-login',
    title:'SeaTurtleTECH.com',
    defaults: {width: 240},
    defaultType: 'textfield',
    bodyStyle:'padding:5px 5px 0',
    //standardSubmit: true,
    //url: 'system/login.jsp',

    // configure how to read the XML Data
    reader : new Ext.data.XmlReader({
      record : 'adminaccount',
      success: '@success'
    }, [
      'username', 'password'
    ]),

    // reusable eror reader class defined at the end of this file
    errorReader: new Ext.form.XmlErrorReader(),

    items: [{
      name: 'username',
      fieldLabel: '帐号',
      allowBlank:true
    },{
      name: 'password',
      inputType: 'password',
      fieldLabel: '密码',
      allowBlank:true
    }]
  });

  // explicit add
  var submit = loginForm.addButton({
    text: '登录后台',
    //disabled:true,
    iconCls: 'btn-login',
    handler: function(){
      loginForm.getForm().submit({
      	url:'index.jxp', 
      	waitMsg:'Please wait...',
      	success: function() {
    	  alert('Login successfullly !');
      		window.location.replace('./main.jxp');
      	}
      });
    }
  });

  // simple button add
  loginForm.addButton({
  	text: '忘记密码', 
  	iconCls: 'btn-forget',
  	handler: function(){
    	loginForm.getForm().load({url:'xml-form.xml', waitMsg:'Loading'});
  	}
  });
	
	new Ext.Viewport({
		layout: 'border',
		title: 'Ext Layout Browser',
		border: false,
		items: [{
			region: 'center',
		  layout:'ux.center',
	    items: {
	        //title: 'Centered Panel: 75% of container width and fit height',
	        layout: 'ux.center',
	        //autoScroll: true,
	        height: '100%',
	        width: '75%',
	        border: false,
	        bodyStyle: 'padding:200px 200px 0 0;',
	        items: [loginForm]
	    }
		}],
    renderTo: Ext.getBody()
  });


  loginForm.on({
    actioncomplete: function(form, action){
      if(action.type == 'load'){
          submit.enable();
      }
    }
  });
  
});

// A reusable error reader class for XML forms
Ext.form.XmlErrorReader = function(){
    Ext.form.XmlErrorReader.superclass.constructor.call(this, {
            record : 'field',
            success: '@success'
        }, [
            'id', 'msg'
        ]
    );
};
Ext.extend(Ext.form.XmlErrorReader, Ext.data.XmlReader);