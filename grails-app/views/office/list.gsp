
<%@ page import="com.charitychamp.Office" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'office.label', default: 'Office')}" />
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
				<div id="list-office" class="content scaffold-list" role="main">
					
					<g:if test="${flash.message}">
					<div class="message" role="status">${flash.message}</div>
					</g:if>
					<table>
						<thead>
							<tr>
							
								<g:sortableColumn property="name" title="${message(code: 'office.name.label', default: 'Name')}" />
							
								<th><g:message code="office.business.label" default="Business" /></th>
							
								<th><g:message code="office.charityCaptain.label" default="Charity Captain" /></th>
							
								<g:sortableColumn property="dateCreated" title="${message(code: 'office.dateCreated.label', default: 'Date Created')}" />
							
								<g:sortableColumn property="lastUpdated" title="${message(code: 'office.lastUpdated.label', default: 'Last Updated')}" />
							
								<th><g:message code="office.officer.label" default="Officer" /></th>
							
							</tr>
						</thead>
						<tbody>
						<g:each in="${officeInstanceList}" status="i" var="officeInstance">
							<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
							
								<td><g:link action="show" id="${officeInstance.id}">${fieldValue(bean: officeInstance, field: "name")}</g:link></td>
							
								<td>${fieldValue(bean: officeInstance, field: "business")}</td>
							
								<td>${fieldValue(bean: officeInstance, field: "charityCaptain")}</td>
							
								<td><g:formatDate date="${officeInstance.dateCreated}" /></td>
							
								<td><g:formatDate date="${officeInstance.lastUpdated}" /></td>
							
								<td>${fieldValue(bean: officeInstance, field: "leader")}</td>
							
							</tr>
						</g:each>
						</tbody>
					</table>
					<div class="pagination">
						<g:paginate total="${officeInstanceTotal}" />
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
