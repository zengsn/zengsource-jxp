<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="${_EXT_URL_}/${_EXT_VERSION_}/resources/css/xtheme-lightgray1.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/index.css" />

<div id="index-inner">

	<!-- solutions carousel -->
	<div id="carousel-wrapper" class="x-hidden~">
		<link rel="stylesheet" type="text/css" href="${_EXT_URL_}/core-3.0/examples/carousel/carousel.css" />
		<script src="${_EXT_URL_}/core-3.0/src/util/TaskMgr.js"></script>
		<script src="${_EXT_URL_}/core-3.0/examples/carousel/carousel.js"></script>
		<link rel="stylesheet" type="text/css" href="${_EXT_URL_}/${_EXT_VERSION_}/examples/panel/css/bubble.css" />
		<script type="text/javascript" src="${_EXT_URL_}/${_EXT_VERSION_}/examples/panel/BubblePanel.js"></script>
		<script src="./index-carousel.js"></script>
		<div id="focus-carousel" style="overflow: hidden;">
			<div id="carousel-navigation-buttons">
				<img id="nav-prev" src="./resources/icons/silk/gif/control_rewind_blue.gif">
				<img id="nav-play" src="./resources/icons/silk/gif/control_play_blue.gif" class="nav-hide">
				<img id="nav-pause" src="./resources/icons/silk/gif/control_pause_blue.gif">
				<img id="nav-next" src="./resources/icons/silk/gif/control_fastforward_blue.gif">
			</div>	
			<div class="carousel-item">
				<img src="./resources/images/solutions/01_JavaExtPlatform.png" />
				<div>
					<h4><a href="./">Java ExtJS Platform (JXP)</a></h4>
				    <p>基于Java和ExtJS构建的互联网开发平台,可以快速稳定地解决所有互联网需求!</p>
			    </div>
			</div>   
			<div class="carousel-item">
				<img src="./resources/images/solutions/02_WebPortal.png" />
				<div>
					<h4><a href="./">公司网站</a></h4>
				    <p>为自己的公司量身定制一个网站门户(Enterprise Portal)是您公司成功的第一步!</p>
			    </div>
			</div>   
			<div class="carousel-item">
				<img src="./resources/images/solutions/03_eCommence.png" />
				<div>
					<h4><a href="./">电子商务</a></h4>
				    <p>Java+ExtJS = Platform</p>
			    </div>
			</div>   
			<div class="carousel-item">
				<img src="./resources/images/solutions/04_Customization-src.png" />
				<div>
					<h4><a href="./">定制开发</a></h4>
				    <p>Java+ExtJS = Platform</p>
			    </div>
			</div>   
		</div>
		<div id="">
		</div>
		<div id="carousel-navigation">
		    <img id="carousel-navigation-arrow" src="./resources/images/nav-arrow.gif">
		    <div id="carousel-navigation-shortcuts">
		        <img width="64" height="64" src="./resources/images/solutions/01_JavaExtPlatform.png" class="active" />
		        <img width="64" height="64" src="./resources/images/solutions/02_WebPortal.png" />
		        <img width="64" height="64" src="./resources/images/solutions/03_eCommence.png" />
		        <img width="64" height="64" src="./resources/images/solutions/04_Customization-src.png" class="last-item" />
		    </div>                
		</div>
	</div>
    <div id="carousel-panel"></div>
	<!-- end focus carousel -->

</div>

