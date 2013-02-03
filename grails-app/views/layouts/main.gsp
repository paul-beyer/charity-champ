<!doctype html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title><g:layoutTitle default="Charity Champ"/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="shortcut icon" href="${resource(dir: 'images', file: 'boxing_glove.ico')}" type="image/x-icon">
		<link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
		<link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'charityChamp.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'dtree.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'jqsimplemenu.css')}" type="text/css">
		
		
		
		<g:layoutHead/>
		<r:require modules="application, jquery, dtree, moneyMask, jquery-ui, jqsimplemenu, home" />
		<r:layoutResources />
	</head>
	<body>
			
		<div id="wrap">
<!-- BEGIN HEADER -->
			<div id="main">
				<a href="#" id="opfeed"><g:img dir="images" file="rsz_mid-ohio-food-bank.png"/></a>
				<a href="#" id="title"></a>
			
			</div>	
<!-- END HEADER -->

<!-- BEGIN NAVIGATION -->		
		<div id="primaryNav">
				<ul id="mainNav" class="jq-menu">
					<li><a href="${createLink(uri: '/')}">Home</a></li>	
				
				</ul>
				
		</div>
<!-- END NAVIGATION -->	
			
			<g:layoutBody/>
		<!-- BEGIN FOOTER -->	
			<div id="primary-foot">
				<div id="history">
				
					<header><h1>Volunteer. Give. Repeat.</h1></header>			
				</div>	
			</div>
		</div>
<!-- END FOOTER -->	
		<div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>
<%--		<g:javascript library="application"/>--%>
		<r:layoutResources />
	</body>
</html>