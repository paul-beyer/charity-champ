
<%@ page import="com.charitychamp.Business" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'business.label', default: 'Business')}" />
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
				<div id="show-business" class="content scaffold-show" role="main">
					
					<g:if test="${flash.message}">
					<div class="message" role="status">${flash.message}</div>
					</g:if>
					<ol class="property-list business">
					
						<g:if test="${businessInstance?.name}">
						<li class="fieldcontain">
							<span id="name-label" class="property-label"><g:message code="business.name.label" default="Name" /></span>
							
								<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${businessInstance}" field="name"/></span>
							
						</li>
						</g:if>
					
						<g:if test="${businessInstance?.teamNumber}">
						<li class="fieldcontain">
							<span id="teamNumber-label" class="property-label"><g:message code="business.teamNumber.label" default="Team Number" /></span>
							
								<span class="property-value" aria-labelledby="teamNumber-label"><g:fieldValue bean="${businessInstance}" field="teamNumber"/></span>
							
						</li>
						</g:if>
					
											
						<g:if test="${businessInstance?.charityLeader}">
						<li class="fieldcontain">
							<span id="charityLeader-label" class="property-label"><g:message code="business.charityLeader.label" default="Charity Leader" /></span>
							
								<span class="property-value" aria-labelledby="charityLeader-label"><g:link controller="person" action="show" id="${businessInstance?.charityLeader?.id}">${businessInstance?.charityLeader?.encodeAsHTML()}</g:link></span>
							
						</li>
						</g:if>
					
						<g:if test="${businessInstance?.company}">
						<li class="fieldcontain">
							<span id="company-label" class="property-label"><g:message code="business.company.label" default="Company" /></span>
							
								<span class="property-value" aria-labelledby="company-label"><g:link controller="company" action="show" id="${businessInstance?.company?.id}">${businessInstance?.company?.encodeAsHTML()}</g:link></span>
							
						</li>
						</g:if>
					
						<g:if test="${businessInstance?.dateCreated}">
						<li class="fieldcontain">
							<span id="dateCreated-label" class="property-label"><g:message code="business.dateCreated.label" default="Date Created" /></span>
							
								<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${businessInstance?.dateCreated}" /></span>
							
						</li>
						</g:if>
					
						<g:if test="${businessInstance?.leader}">
						<li class="fieldcontain">
							<span id="executive-label" class="property-label"><g:message code="business.executive.label" default="Executive" /></span>
							
								<span class="property-value" aria-labelledby="executive-label"><g:link controller="person" action="show" id="${businessInstance?.leader?.id}">${businessInstance?.leader?.encodeAsHTML()}</g:link></span>
							
						</li>
						</g:if>
					
						<g:if test="${businessInstance?.lastUpdated}">
						<li class="fieldcontain">
							<span id="lastUpdated-label" class="property-label"><g:message code="business.lastUpdated.label" default="Last Updated" /></span>
							
								<span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${businessInstance?.lastUpdated}" /></span>
							
						</li>
						</g:if>
					
					</ol>
					<g:form>
						<fieldset class="buttons">
							<g:hiddenField name="id" value="${businessInstance?.id}" />
							<g:link class="edit" action="edit" id="${businessInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
							<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.business.delete.confirm.message', default: 'Are you sure?')}');" />
						</fieldset>
					</g:form>
				</div>
			</div>
		</div>
	</body>
</html>
