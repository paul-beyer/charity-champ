
<%@ page import="com.charitychamp.Group" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'group.label', default: 'Group')}" />
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
				<div id="show-group" class="content scaffold-show" role="main">
					
					<g:if test="${flash.message}">
					<div class="message" role="status">${flash.message}</div>
					</g:if>
					<ol class="property-list group">
					
						<g:if test="${groupInstance?.name}">
						<li class="fieldcontain">
							<span id="name-label" class="property-label"><g:message code="group.name.label" default="Name" /></span>
							
								<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${groupInstance}" field="name"/></span>
							
						</li>
						</g:if>
					
						<g:if test="${groupInstance?.leader}">
						<li class="fieldcontain">
							<span id="leader-label" class="property-label"><g:message code="group.leader.label" default="Leader" /></span>
							
								<span class="property-value" aria-labelledby="leader-label"><g:link controller="person" action="show" id="${groupInstance?.leader?.id}">${groupInstance?.leader?.encodeAsHTML()}</g:link></span>
							
						</li>
						</g:if>
					
						
					
						<g:if test="${groupInstance?.dateCreated}">
						<li class="fieldcontain">
							<span id="dateCreated-label" class="property-label"><g:message code="group.dateCreated.label" default="Date Created" /></span>
							
								<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${groupInstance?.dateCreated}" /></span>
							
						</li>
						</g:if>
					
						<g:if test="${groupInstance?.department}">
						<li class="fieldcontain">
							<span id="department-label" class="property-label"><g:message code="group.department.label" default="Department" /></span>
							
								<span class="property-value" aria-labelledby="department-label"><g:link controller="department" action="show" id="${groupInstance?.department?.id}">${groupInstance?.department?.encodeAsHTML()}</g:link></span>
							
						</li>
						</g:if>
					
						<g:if test="${groupInstance?.lastUpdated}">
						<li class="fieldcontain">
							<span id="lastUpdated-label" class="property-label"><g:message code="group.lastUpdated.label" default="Last Updated" /></span>
							
								<span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${groupInstance?.lastUpdated}" /></span>
							
						</li>
						</g:if>
					
					</ol>
					<g:form>
						<fieldset class="buttons">
							<g:hiddenField name="id" value="${groupInstance?.id}" />
							<g:link class="edit" action="edit" id="${groupInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
							<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
						</fieldset>
					</g:form>
				</div>
			</div>
		</div>
	</body>
</html>
