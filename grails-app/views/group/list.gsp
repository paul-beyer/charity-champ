
<%@ page import="com.charitychamp.Group" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'group.label', default: 'Group')}" />
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
				<div id="list-group" class="content scaffold-list" role="main">
					
					<g:if test="${flash.message}">
					<div class="message" role="status">${flash.message}</div>
					</g:if>
					<table>
						<thead>
							<tr>
							
								<g:sortableColumn property="name" title="${message(code: 'group.name.label', default: 'Name')}" />
							
								<th><g:message code="group.leader.label" default="Leader" /></th>
							
								<g:sortableColumn property="dateCreated" title="${message(code: 'group.dateCreated.label', default: 'Date Created')}" />
							
								<th><g:message code="group.department.label" default="Department" /></th>
							
								<g:sortableColumn property="lastUpdated" title="${message(code: 'group.lastUpdated.label', default: 'Last Updated')}" />
							
							</tr>
						</thead>
						<tbody>
						<g:each in="${groupInstanceList}" status="i" var="groupInstance">
							<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
							
								<td><g:link action="show" id="${groupInstance.id}">${fieldValue(bean: groupInstance, field: "name")}</g:link></td>
							
								<td>${fieldValue(bean: groupInstance, field: "leader")}</td>
							
								<td><g:formatDate date="${groupInstance.dateCreated}" /></td>
							
								<td>${fieldValue(bean: groupInstance, field: "department")}</td>
							
								<td><g:formatDate date="${groupInstance.lastUpdated}" /></td>
							
							</tr>
						</g:each>
						</tbody>
					</table>
					<div class="pagination">
						<g:paginate total="${groupInstanceTotal}" />
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
