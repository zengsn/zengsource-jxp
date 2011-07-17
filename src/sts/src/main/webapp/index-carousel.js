Ext.onReady(function() {
	// bubble panel
	//var cp = new Ext.ux.BubblePanel({
	var cp = new Ext.Panel({
		renderTo: 'carousel-panel',
		padding: 0,
		width: 708,
		frame: true,
		title: '关怀企业信息化应用',
		autoHeight: true,
		items: [{
			frame: true,
			xtype: 'panel',
			contentEl: 'carousel-wrapper'
		}]
    });
	// product carousel
    var carousel = new Ext.ux.Carousel('focus-carousel', {
        itemSelector: 'div.carousel-item',
        interval: 7,
        autoPlay: true,
        transitionType: 'fade',
        hideNavigation: true,
        freezeOnHover: true
    });

    var shortcuts = Ext.query('#carousel-navigation-shortcuts > img');
    
    Ext.fly('carousel-navigation-shortcuts').on('click', function() {
        var index = shortcuts.indexOf(this.dom);
        carousel.pause();
        Ext.fly('nav-pause').radioClass('nav-hide');
        carousel.setSlide(index);
    }, null, {delegate: 'img'});
    
    carousel.on('change', function(slide, index) {
        var shortcut = Ext.get(shortcuts[index]),
        arrow = Ext.get('carousel-navigation-arrow');
            
        shortcut.radioClass('active');
        var left = shortcut.getOffsetsTo('carousel-navigation')[0] + (shortcut.getWidth()/2) - (arrow.getWidth()/2);
        arrow.setLeft(left + 'px');
    });
    
    Ext.fly('carousel-navigation-buttons').on('click', function() {
        switch(this.dom.id) {
            case 'nav-play':
                carousel.play();
                this.radioClass('nav-hide');
            break;
            case 'nav-pause':
                carousel.pause();
                this.radioClass('nav-hide');
            break;
            case 'nav-next':
                carousel.pause().next();
                Ext.fly('nav-pause').radioClass('nav-hide');
            break;
            case 'nav-prev':
                carousel.pause().prev();
                Ext.fly('nav-pause').radioClass('nav-hide');
            break;
        }
    }, null, {delegate: 'img'});
    
    if(Ext.isIE6) {
        // force reflow of arrow in ie6
        Ext.fly('carousel-navigation-arrow').setStyle('display', 'none').setStyle('display', 'block');
    }
});
