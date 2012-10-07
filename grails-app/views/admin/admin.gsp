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
				
				<div class="manageChampRightPanel" >
					<div id="adminInstruction" class="instruction">
						<h1>
							This area allows you to manage Charity Champ and customize it for your organization
						</h1>
						<br/>
						<ul>
							<li>
								<p>
									If you want to manage and/or set up Charity Champ for the first time, select "Setup Charity Champ".  From here
									you can setup or modify your organizational structure.  You can also add people and assign those people
									to roles.  This will most likely be your starting point when you start the application for the first time.
								</p>
							</li>
							<br/>
							<li>
								<p>
									If you want to set things like, "how many meals a dollar buys", or "goals per employee", you can configure that
									by selecting "Configure".
								</p> 
							</li>
						
						</ul>
											
					</div>
							
				</div>
			</div>
<!-- END CONFIGURATION LINKS -->	
</body>
</html>

	