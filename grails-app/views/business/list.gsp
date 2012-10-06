
<%@ page import="com.charitychamp.Business" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'business.label', default: 'Business')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="manageChampLayout">	
				<g:render template="/shared/setupLinks" />
		
			<div class="manageChampRightPanel">
				<div class="nav" role="navigation">
					<ul>
						
						<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
					</ul>
				</div>
				<div id="list-business" class="content scaffold-list" role="main">
					
					<g:if test="${flash.message}">
					<div class="message" role="status">${flash.message}</div>
					</g:if>
					<table>
						<thead>
							<tr>
							
								<g:sortableColumn property="name" title="${message(code: 'business.name.label', default: 'Name')}" />
							
								<g:sortableColumn property="teamNumber" title="${message(code: 'business.teamNumber.label', default: 'Team Number')}" />
							
								<th><g:message code="business.charityLeader.label" default="Charity Leader" /></th>
							
								<th><g:message code="business.company.label" default="Company" /></th>
							
								<g:sortableColumn property="dateCreated" title="${message(code: 'business.dateCreated.label', default: 'Date Created')}" />
							
								<th><g:message code="business.executive.label" default="Executive" /></th>
							
							</tr>
						</thead>
						<tbody>
						<g:each in="${businessInstanceList}" status="i" var="businessInstance">
							<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
							
								<td><g:link action="show" id="${businessInstance.id}">${fieldValue(bean: businessInstance, field: "name")}</g:link></td>
							
								<td>${fieldValue(bean: businessInstance, field: "teamNumber")}</td>
							
								<td>${fieldValue(bean: businessInstance, field: "charityLeader")}</td>
							
								<td>${fieldValue(bean: businessInstance, field: "company")}</td>
							
								<td><g:formatDate date="${businessInstance.dateCreated}" /></td>
							
								<td>${fieldValue(bean: businessInstance, field: "executive")}</td>
							
							</tr>
						</g:each>
						</tbody>
					</table>
					<div class="pagination">
						<g:paginate total="${businessInstanceTotal}" />
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
