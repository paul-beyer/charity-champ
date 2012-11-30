
<%@ page import="com.charitychamp.Department" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'department.label', default: 'Department')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="manageChampLayout">	
				<g:render template="/shared/setupLinks" />
		
			<div class="manageChampRightPanel">
				<div class="nav" role="navigation">
					<ul>
						
						<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
						<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
					</ul>
				</div>
				<div id="show-department" class="content scaffold-show" role="main">
				
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
					
						<g:if test="${departmentInstance?.dateCreated}">
						<li class="fieldcontain">
							<span id="dateCreated-label" class="property-label"><g:message code="department.dateCreated.label" default="Date Created" /></span>
							
								<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${departmentInstance?.dateCreated}" /></span>
							
						</li>
						</g:if>
					
						<g:if test="${departmentInstance?.leader}">
						<li class="fieldcontain">
							<span id="departmentHead-label" class="property-label"><g:message code="department.departmentHead.label" default="Department Head" /></span>
							
								<span class="property-value" aria-labelledby="departmentHead-label"><g:link controller="person" action="show" id="${departmentInstance?.leader?.id}">${departmentInstance?.leader?.encodeAsHTML()}</g:link></span>
							
						</li>
						</g:if>
					
						<g:if test="${departmentInstance?.lastUpdated}">
						<li class="fieldcontain">
							<span id="lastUpdated-label" class="property-label"><g:message code="department.lastUpdated.label" default="Last Updated" /></span>
							
								<span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${departmentInstance?.lastUpdated}" /></span>
							
						</li>
						</g:if>
					
						<g:if test="${departmentInstance?.office}">
						<li class="fieldcontain">
							<span id="office-label" class="property-label"><g:message code="department.office.label" default="Office" /></span>
							
								<span class="property-value" aria-labelledby="office-label"><g:link controller="office" action="show" id="${departmentInstance?.office?.id}">${departmentInstance?.office?.encodeAsHTML()}</g:link></span>
							
						</li>
						</g:if>
					
					</ol>
					<g:form>
						<fieldset class="buttons">
							<g:hiddenField name="id" value="${departmentInstance?.id}" />
							<g:link class="edit" action="edit" id="${departmentInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
							<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.department.delete.confirm.message', default: 'Are you sure?')}');" />
						</fieldset>
					</g:form>
				</div>
			</div>
		</div>
	</body>
</html>
