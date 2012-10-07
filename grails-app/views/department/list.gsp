
<%@ page import="com.charitychamp.Department" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'department.label', default: 'Department')}" />
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
				<div id="list-department" class="content scaffold-list" role="main">
					
					<g:if test="${flash.message}">
					<div class="message" role="status">${flash.message}</div>
					</g:if>
					<table>
						<thead>
							<tr>
							
								<g:sortableColumn property="name" title="${message(code: 'department.name.label', default: 'Name')}" />
							
								<g:sortableColumn property="numberOfEmployees" title="${message(code: 'department.numberOfEmployees.label', default: 'Number Of Employees')}" />
							
								<g:sortableColumn property="dateOfEmployeeCount" title="${message(code: 'department.dateOfEmployeeCount.label', default: 'Date Of Employee Count')}" />
							
								<th><g:message code="department.charityLieutenant.label" default="Charity Lieutenant" /></th>
							
								<th><g:message code="group.office.label" default="Office" /></th>
							
								<th><g:message code="department.departmentHead.label" default="Department Head" /></th>
							
							</tr>
						</thead>
						<tbody>
						<g:each in="${departmentInstanceList}" status="i" var="departmentInstance">
							<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
							
								<td><g:link action="show" id="${departmentInstance.id}">${fieldValue(bean: departmentInstance, field: "name")}</g:link></td>
							
								<td>${fieldValue(bean: departmentInstance, field: "numberOfEmployees")}</td>
							
								<td><g:formatDate date="${departmentInstance.dateOfEmployeeCount}" /></td>
							
								<td>${fieldValue(bean: departmentInstance, field: "charityLieutenant")}</td>
							
								<td>${fieldValue(bean: departmentInstance, field: "office")}</td>
							
								<td>${fieldValue(bean: departmentInstance, field: "departmentHead")}</td>
							
							</tr>
						</g:each>
						</tbody>
					</table>
					<div class="pagination">
						<g:paginate total="${departmentInstanceTotal}" />
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
