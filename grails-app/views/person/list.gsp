
<%@ page import="com.charitychamp.Person" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'person.label', default: 'Person')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-person" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-person" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="userId" title="${message(code: 'person.userId.label', default: 'User Id')}" />
					
						<g:sortableColumn property="firstName" title="${message(code: 'person.firstName.label', default: 'First Name')}" />
					
						<g:sortableColumn property="lastName" title="${message(code: 'person.lastName.label', default: 'Last Name')}" />
					
						<g:sortableColumn property="phoneNumber" title="${message(code: 'person.phoneNumber.label', default: 'Phone Number')}" />
					
						<g:sortableColumn property="altPhoneNumber" title="${message(code: 'person.altPhoneNumber.label', default: 'Alt Phone Number')}" />
					
						<g:sortableColumn property="email" title="${message(code: 'person.email.label', default: 'Email')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${personInstanceList}" status="i" var="personInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${personInstance.id}">${fieldValue(bean: personInstance, field: "userId")}</g:link></td>
					
						<td>${fieldValue(bean: personInstance, field: "firstName")}</td>
					
						<td>${fieldValue(bean: personInstance, field: "lastName")}</td>
					
						<td>${fieldValue(bean: personInstance, field: "phoneNumber")}</td>
					
						<td>${fieldValue(bean: personInstance, field: "altPhoneNumber")}</td>
					
						<td>${fieldValue(bean: personInstance, field: "email")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${personInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
