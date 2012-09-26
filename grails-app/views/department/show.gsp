
<%@ page import="com.charitychamp.Department" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'department.label', default: 'Department')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-department" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-department" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list department">
			
				<g:if test="${departmentInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="department.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${departmentInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${departmentInstance?.numberOfEmployees}">
				<li class="fieldcontain">
					<span id="numberOfEmployees-label" class="property-label"><g:message code="department.numberOfEmployees.label" default="Number Of Employees" /></span>
					
						<span class="property-value" aria-labelledby="numberOfEmployees-label"><g:fieldValue bean="${departmentInstance}" field="numberOfEmployees"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${departmentInstance?.dateOfEmployeeCount}">
				<li class="fieldcontain">
					<span id="dateOfEmployeeCount-label" class="property-label"><g:message code="department.dateOfEmployeeCount.label" default="Date Of Employee Count" /></span>
					
						<span class="property-value" aria-labelledby="dateOfEmployeeCount-label"><g:formatDate date="${departmentInstance?.dateOfEmployeeCount}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${departmentInstance?.charityLieutenant}">
				<li class="fieldcontain">
					<span id="charityLieutenant-label" class="property-label"><g:message code="department.charityLieutenant.label" default="Charity Lieutenant" /></span>
					
						<span class="property-value" aria-labelledby="charityLieutenant-label"><g:link controller="person" action="show" id="${departmentInstance?.charityLieutenant?.id}">${departmentInstance?.charityLieutenant?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${departmentInstance?.departmentHead}">
				<li class="fieldcontain">
					<span id="departmentHead-label" class="property-label"><g:message code="department.departmentHead.label" default="Department Head" /></span>
					
						<span class="property-value" aria-labelledby="departmentHead-label"><g:link controller="person" action="show" id="${departmentInstance?.departmentHead?.id}">${departmentInstance?.departmentHead?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${departmentInstance?.groups}">
				<li class="fieldcontain">
					<span id="groups-label" class="property-label"><g:message code="department.groups.label" default="Groups" /></span>
					
						<g:each in="${departmentInstance.groups}" var="g">
						<span class="property-value" aria-labelledby="groups-label"><g:link controller="group" action="show" id="${g.id}">${g?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${departmentInstance?.id}" />
					<g:link class="edit" action="edit" id="${departmentInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
