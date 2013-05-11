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
		<g:render template="/shared/setupLinks" />
		
		<div class="manageChampRightPanel">
			<div id="setupInstruction" class="instruction">
						<h1>
							This area allows you to add people and set up your organizational hierarchy
						</h1>
						<br/>
						<ul>
							<li>
								<p>
									Except for "People"  the links to the left are ordered by the intended hierarchy. Companies are at the highest level, typically you would only have one.
									A Company then has Businesses, Businesses have Offices, Offices have Departments and Departments have Groups.  Groups are the lowest level
									and this is the level that all all financial data will be recorded. This is done to provide the lowest
									granularity possible for reporting purposes.  There are typically multiple Groups within a Department.  
									
									
								</p>
							</li>
							<br/>
							<li>
								<p>
									
									We've attempted to make the setting up of organizations generic enough so that it 
									will work for most companies.  You may need to create dummy entries if this organizational
									hierarchy does not exactly match your companies needs.  For example, if your company does not
									have the concept of Office, then simply create a dummy office under a business and then create
									the departments under that.
								</p> 
							</li>
							<br/>
							<li>
								<p>
									Charity Champ has the concept of three main charity leadership roles.  At the Business level you
									have a "Charity Leader".  At the Office level there is a "Charity Captain" and at the Department level
									there is "Charity Lieutenant".  At the Group level you can add a leader, but this is typically someone 
									that helps out with fund raising and is not formally involved with the charity.
								</p> 
							</li>
						
						</ul>
											
					</div>				
		</div>
	</div>
<!-- END CONFIGURATION LINKS -->	
</body>
</html>
