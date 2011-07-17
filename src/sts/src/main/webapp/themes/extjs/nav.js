/*
 * Main console of backend.
 * Copyright(c) 2008, iCanWear.com
 * icanwear@hotmail.com
 * 
 * http://iCanWear.com/license
 */

Ext.onReady(function() {
	
	//Logo panel
	var logoPanel = new Ext.Panel({
		id: 'logo-panel',		
		//title:'Logo',
		region: 'west',
		width: 196,
		split: true,
    	border: false,
		//bodyStyle: 'background: #1E4176 ',
		contentEl: 'logo'
	});	
	
	//Navi Right panel
	var navRightPanel = new Ext.Panel({
		id: 'navright-panel',		
		//title:'Logo',
		region: 'center',
		split: true,
    	border: false,
		//bodyStyle: 'background: url(../images/top_banner.jpg) no-repeat',
		contentEl: 'navright'
	});
	
	// Welcome 
	var location = '当前位置: 首页 | 欢迎来到个性Ｔ恤定制平台iCanWear.com！';
	if(window.location.toString().indexOf('perdesign.jxpl')>-1) {
		location = '当前位置: <a href="perdesign.jxpl">个人定制</a> - 选择模板';
	} else if(window.location.toString().indexOf('perdesign.jxpl')>-1) {
		location = '当前位置: 团队定制';
	} else if(window.location.toString().indexOf('designlab.jxpl')>-1) {
		location = '当前位置: 定制中心';
	} else if(window.location.toString().indexOf('product')>-1) {
		location = '当前位置: 产品选购';
	}
	// Navigation Bar
	var navArray = Ext.get('navlist').query('li');
	var navBBar = new Array(2+navArray.length);
	navBBar[0] = location;
	navBBar[1] = '->';
	var linkArray = new Array();
	for(var i=0;i<navArray.length;i++) {
		navBBar[2+i*2] = '-';		
		var linkDom = navArray[i].childNodes[0];
		
		var btnId = 'btn-'+linkDom.id;
		linkArray[btnId] = linkDom.href;
		
		var isCurrent = window.location.toString().indexOf(linkDom.id)>-1;
		/*
		navBBar[3+i*2] = {
			id: btnId,
			enableToggle: true,
			text: '<a href="'+linkDom.href+'">'+linkDom.innerHTML+'</a>',
			tooltip: linkDom.title,
			iconCls: 'btn-'+linkDom.id,
			pressed: location.indexOf(linkDom.innerHTML)>-1,
			handler: function(btn, e) {
				window.location.replace(linkArray[btn.id]);
			}
		};
		*/
		if(isCurrent) {
			navBBar[3+i*2] = '<span class="nav">' + linkDom.innerHTML + '</span>';
		} else {
			navBBar[3+i*2] = '<a class="nav" href="'+linkDom.href+'">'+linkDom.innerHTML+'</a>';
		}
		
	}
	//Header panel
	var hdPanel = new Ext.Panel({
		id: 'hd-panel',
		layout: 'border',
		height: 119,
		autoScroll: false,
		bodyStyle: 'background: #DFE8F6',
    	border: true,
		items: [ logoPanel, navRightPanel ],
		tbar: [
			'<a href="./login.jxpl">登录</a>', '-', 
			'<a href="./register.jxpl">注册</a>', '-', 
			'<a href="./shopcart.jxpl"><font color="red">购物车</font></a>', '-', 
			'<a href="./help.jxpl">帮助</a>', '->', 
			'<a href="./myaccount.jxpl">我的帐户</a>', '-', 
			'<a href="./ponlinecs.jxpl">在线客服</a>', '-', 
			'客服热线: 010-82376991'
		],
		bbar: navBBar
	});
	
	hdPanel.render('hd');
	
	Ext.get('navlist').remove();
	
});