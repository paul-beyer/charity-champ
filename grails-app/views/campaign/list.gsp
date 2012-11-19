
<%@ page import="com.charitychamp.Campaign" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'campaign.label', default: 'Campaign')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
			<div class="manageChampLayout">	
				<g:render template="/shared/configureLinks" />
		
			<div class="manageChampRightPanel">
				<div class="nav" role="navigation">
					<ul>
						
						<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
					</ul>
				</div>
				<div id="list-campaign" class="content scaffold-list" role="main">
					
					<g:if test="${flash.message}">
					<div class="message" role="status">${flash.message}</div>
					</g:if>
					<table>
						<thead>
							<tr>
							
								<g:sortableColumn property="name" title="${message(code: 'campaign.name.label', default: 'Name')}" />
								
								<g:sortableColumn property="startDate" title="${message(code: 'campaign.startDate.label', default: 'Start Date')}" />
							
								<g:sortableColumn property="endDate" title="${message(code: 'campaign.endDate.label', default: 'End Date')}" />
																				
							</tr>
						</thead>
						<tbody>
						<g:each in="${campaignInstanceList}" status="i" var="campaignInstance">
							<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
							
								<td><g:link action="show" id="${campaignInstance.id}">${fieldValue(bean: campaignInstance, field: "name")}</g:link></td>
								
								<td><g:formatDate date="${campaignInstance.startDate}" /></td>
							
								<td><g:formatDate date="${campaignInstance.endDate}" /></td>
													
							</tr>
						</g:each>
						</tbody>
					</table>
					<div class="pagination">
						<g:paginate total="${campaignInstanceTotal}" />
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
