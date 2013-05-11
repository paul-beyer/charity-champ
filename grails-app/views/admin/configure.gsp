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
			<g:render template="/shared/configureLinks" />
			
			<div class="manageChampRightPanel">
				<div id="configureInstruction" class="instruction">
						<h1>
							Configuration
						</h1>
						<br/>
						<ul>
							<li>
								<p>
									This area allows you to configure global settings for your organization.
								</p>
							</li>
							
						
						</ul>
											
					</div>
			</div>
		</div>
<!-- END CONFIGURATION LINKS -->	
</body>
</html>
