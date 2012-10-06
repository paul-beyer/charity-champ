<%@ page import="com.charitychamp.Activity" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'activity.label', default: 'Activity')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	<body>
	<!-- BEGIN CONFIGURATION LINKS -->		
	<div class="manageChampLayout">	
		<g:render template="manageLinks" />
		
		<div class="manageChampRightPanel">
			Hi this is the people edit screen				
		</div>
	</div>
<!-- END CONFIGURATION LINKS -->	
</body>
</html>
