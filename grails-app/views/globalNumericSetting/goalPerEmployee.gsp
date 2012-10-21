
<%@ page import="com.charitychamp.GlobalNumericSetting" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'globalNumericSetting.label', default: 'GlobalNumericSetting')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="manageChampLayout">	
				<g:render template="/shared/configureLinks" />
		
		<div class="manageChampRightPanel">
				<div class="nav" role="navigation">
					<ul>
						
						<li><g:link class="create" action="createEmployeeGoal"><g:message code="default.new.label" args="['Amt Per Employee Goal']" /></g:link></li>
					</ul>
				</div>
				<g:render template="commonList"/>
			</div>
		</div>
	</body>
</html>
