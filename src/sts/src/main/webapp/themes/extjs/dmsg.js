

Ext.onReady(function() {
		
	//alert('Next is dynamic message ...');
	//dynamic messages
  var msgs = [{
  	text:"喜迎奥运,为奥运健儿加油,加油T恤正在热卖中 &raquo;", 
  	url:"http://extjs.com/support/training/"
  }, {
  	text:"Introducing Ext GWT 1.0 &raquo;", 
  	url:"http://extjs.com/products/gxt/"
  }, {
  	text:"Ext 2.2 Release Now Available &raquo;", 
  	url:"http://extjs.com/products/extjs/download.php"
  }];
  var msgIndex = 0;
  var msg = Ext.get("dmsg"), msgInner = Ext.get("dmsg-inner"), active = null;
  //msgInner.addClassOnOver("msg-over");
  //msg.on("click", function () {
  //	window.location = active.url;
  //});

  function doUpdate() {
  	//msgInner.update(active.text);
  	var link = '<a href="' + active.url + '" target="_blank">' + active.text + '</a>';
  	msgInner.update(link, true);
  	msg.slideIn("b");
  }
  function showMsg(index) {
	  if (!msgInner.hasClass("msg-over")) {
      active = msgs[index];
      if (msg.isVisible()) {
        msg.slideOut("b", {callback:doUpdate});
      } else {
        doUpdate();
      }
	  }
  }
  
  if(msg && msgInner) {
	  setInterval(function () {
	  	msgIndex = msgs[msgIndex + 1] ? msgIndex + 1 : 0;
	  	showMsg(msgIndex);
	  }, 5000);
	  
	  showMsg(0);	
	}
	
});