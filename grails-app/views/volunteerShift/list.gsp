
<%@ page import="com.charitychamp.VolunteerShift" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'volunteerShift.label', default: 'VolunteerShift')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-volunteerShift" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-volunteerShift" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="numberOfVolunteers" title="${message(code: 'volunteerShift.numberOfVolunteers.label', default: 'Number Of Volunteers')}" />
					
						<g:sortableColumn property="comments" title="${message(code: 'volunteerShift.comments.label', default: 'Comments')}" />
					
						<th><g:message code="volunteerShift.leader.label" default="Leader" /></th>
					
						<g:sortableColumn property="mealFactor" title="${message(code: 'volunteerShift.mealFactor.label', default: 'Meal Factor')}" />
					
						<g:sortableColumn property="dateOfShift" title="${message(code: 'volunteerShift.dateOfShift.label', default: 'Date Of Shift')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${volunteerShiftInstanceList}" status="i" var="volunteerShiftInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${volunteerShiftInstance.id}">${fieldValue(bean: volunteerShiftInstance, field: "numberOfVolunteers")}</g:link></td>
					
						<td>${fieldValue(bean: volunteerShiftInstance, field: "comments")}</td>
					
						<td>${fieldValue(bean: volunteerShiftInstance, field: "leader")}</td>
					
						<td>${fieldValue(bean: volunteerShiftInstance, field: "mealFactor")}</td>
					
						<td><g:formatDate date="${volunteerShiftInstance.dateOfShift}" /></td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${volunteerShiftInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
