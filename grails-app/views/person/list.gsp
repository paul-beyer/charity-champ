
<%@ page import="com.charitychamp.Person" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'person.label', default: 'Person')}" />
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
				<div id="list-person" class="content scaffold-list" role="main">
					<br/>
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
								
								<g:sortableColumn property="personTitle" title="${message(code: 'person.personTitle.label', default: 'Title')}" />
							
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
								
								<td>${fieldValue(bean: personInstance, field: "personTitle")}</td>
							
							</tr>
						</g:each>
						</tbody>
					</table>
					<div class="pagination">
						<g:paginate total="${personInstanceTotal}" />
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
